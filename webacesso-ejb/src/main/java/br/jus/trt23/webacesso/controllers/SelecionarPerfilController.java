package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Acesso;
import br.jus.trt23.webacesso.entities.DelegarGestor;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Usuario;
import br.jus.trt23.webacesso.sessions.AcessoIntegracaoFacade;
import br.jus.trt23.webacesso.sessions.DelegarGestorFacade;
import br.jus.trt23.webacesso.sessions.FuncionalidadeFacade;
import br.jus.trt23.webacesso.sessions.RecursoFacade;
import br.jus.trt23.webacesso.util.ConfiguracaoAplicacao;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Dependent
public class SelecionarPerfilController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private AcessoIntegracaoFacade acessoFacade;

    @Inject
    private UsuarioIntegracaoController usuarioIntegracaoFacade;


    @Inject
    private FuncionalidadeFacade funcionalidadeFacade;

    @Inject
    private RecursoFacade recursoFacade;

    @Inject
    private DelegarGestorFacade delegarGestorFacade;

    @Inject
    private UsuarioSessao usuarioSessao;

    @Inject
    private ConfiguracaoAplicacao configuracaoAplicacao;

    private List<Acesso> listaAcesso;

    @PostConstruct
    public void init() {
        Usuario usuario = usuarioIntegracaoFacade.buscarUsuario(usuarioSessao.getLogin());
        Sistema sistema = new Sistema(configuracaoAplicacao.getCodigoSistema());

        List<Usuario> listaUsuario = usuarioIntegracaoFacade.obterListaUsuarioSistema(usuario);
        listaAcesso = acessoFacade.listarAcessos(sistema, listaUsuario);

        // VERIFICAR SE ESTE USUÁRIO POSSUÍ DELEGAÇÃO DE COMPETÊNCIA.
        // SE SIM - CONSULTAR O PERFIL DE SEU GESTOR
        // SOMAR A LISTA DE ACESSO ESSES PERFIS
        List<DelegarGestor> listaDelegarGestor = delegarGestorFacade.listarDelagacoes(sistema, null, usuario, new java.util.Date());
        if (listaDelegarGestor != null) {
            List<Acesso> listaAcessoDelegacoes = new ArrayList<>();
            List<Usuario> listaUsuarioGestor = new ArrayList<>();
            listaUsuarioGestor.add(new Usuario(Usuario.GESTOR));
            for (DelegarGestor delegacao : listaDelegarGestor) {
                if (delegacao.getUsuarioGestor() != null && delegacao.getUsuarioGestor().getGestor() != null
                        && delegacao.getUsuarioGestor().getGestor().equals(TipoSimNao.S)) {
                    listaAcessoDelegacoes = acessoFacade.listarAcessos(sistema, listaUsuarioGestor);
                    if (listaAcessoDelegacoes != null) {
                        for (Acesso a : listaAcessoDelegacoes) {
                            a.setUsuarioGestor(delegacao.getUsuarioGestor());
                        }
                    }
                }
            }
            listaAcesso.addAll(listaAcessoDelegacoes);
        }

        if (listaAcesso != null && listaAcesso.size() == 1) {
            obterFuncionalidades(listaAcesso.get(0));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("menu.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(SelecionarPerfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (listaAcesso == null || listaAcesso.isEmpty()) {
            MensagemUtil.error("Usuário sem acesso a este sistema.");
        }
    }

    public String selecionarPerfil(final Acesso acesso) throws IOException {
        if (acesso != null) {
            usuarioSessao.setMultipluPerfil(true);
            obterFuncionalidades(acesso);
        }
        return "menu";
    }

    private void obterFuncionalidades(final Acesso acesso) {
        List<Funcionalidade> listaFuncionalidade = funcionalidadeFacade.listarFuncionalidadeAcesso(acesso.getPerfil());
        if (listaFuncionalidade != null) {
            // Unidade
            acessoFacade.edit(acesso);
            if (acesso.getUsuarioGestor() != null && acesso.getUsuarioGestor().getUnidade() != null) {
                //por que precisa desse refresh
                //unidadeIntegracaoFacade.refresh(acesso.getUsuarioGestor().getUnidade());
                usuarioSessao.setUnidade(acesso.getUsuarioGestor().getUnidade().getSigla());
                usuarioSessao.setCodigoUnidade(acesso.getUsuarioGestor().getUnidade().getId());
            } else if (acesso.getUnidade() != null) {
                usuarioSessao.setUnidade(acesso.getUnidade().getSigla());
                usuarioSessao.setCodigoUnidade(acesso.getUnidade().getId());
            }

            //Perfil			
            if (acesso.getPerfil() != null) {
                usuarioSessao.setPerfil(acesso.getPerfil().getDescricao());
            }
            // Funcionalidades
            usuarioSessao.setListaFuncionalidades(new ArrayList<>());
            for (Funcionalidade f : listaFuncionalidade) {
                usuarioSessao.getListaFuncionalidades().add(f);
                // Recursos
                List<Recurso> listaRecurso = recursoFacade.listarRecursos(acesso.getPerfil(), f);
                if (listaRecurso != null) {
                    usuarioSessao.putRecursosPermitidos(f, listaRecurso);
                }
                // Url Principal
                if (f.getListaUrl() != null) {
                    for (FuncionalidadeUrl url : f.getListaUrl()) {
                        if (url.getPrincipalUrl().equals(TipoSimNao.S)) {
                            f.setUrlPrincipal(url.getUrl());
                            break;
                        }
                    }
                }
            }
        }
    }

    public List<Acesso> getListaAcesso() {
        return listaAcesso;
    }
}
