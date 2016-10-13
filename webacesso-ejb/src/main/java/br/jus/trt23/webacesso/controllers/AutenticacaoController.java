package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.ldap.Ldap;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.nucleo.util.Util;
import br.jus.trt23.webacesso.entities.Acesso;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Usuario;
import br.jus.trt23.webacesso.sessions.AcessoIntegracaoFacade;
import br.jus.trt23.webacesso.util.ConfiguracaoAplicacao;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ConversationScoped
public class AutenticacaoController implements Serializable {
    @Inject
    Conversation conversation;
    
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;

    @Inject
    private UsuarioIntegracaoController usuarioIntegracaoController;

    @Inject
    private AcessoIntegracaoFacade acessoIntegracaoFacade;

    @Inject
    private UsuarioSessao usuarioSessao;

    @Inject
    private ConfiguracaoAplicacao configuracaoAplicacao;

    @PostConstruct
    public void init() {
        if (usuarioSessao != null && usuarioSessao.isUsuarioLogado()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("selecionarPerfil.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AutenticacaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void autenticarCAS() throws IOException {
        usuarioSessao.setLogin(getUsuarioCAS());
        if (usuarioSessao.getLogin() != null && !usuarioSessao.getLogin().equals("")) {
            autenticar();
        }
    }

    public void autenticar() throws IOException {
        boolean autenticado;
        if (usuarioSessao.getLogin() != null && !usuarioSessao.getLogin().equals("")) {
            autenticado = true;
            login = usuarioSessao.getLogin();
        } else {
            autenticado = new Ldap().isUsuarioAutenticado(login, senha);
        }
        if (autenticado) {
            Usuario usuario = usuarioIntegracaoController.buscarUsuario(login);
            if (usuario != null) {
                System.out.println("[" + ip() + "] " + usuario.getLogin() + " | " + usuario.getNome() + " autenticado com sucesso.");
                List<Usuario> listaUsuario = usuarioIntegracaoController.obterListaUsuarioSistema(usuario);
                List<Acesso> listaAcesso = acessoIntegracaoFacade.listarAcessos(new Sistema(configuracaoAplicacao.getCodigoSistema()), listaUsuario);
                if (usuario.getDataDesligamento() != null && usuario.getDataDesligamento().before(Util.trunc(new java.util.Date()))) {
                    MensagemUtil.error("Usuário foi desligado.");
                    return;
                }
                if (listaAcesso != null && !listaAcesso.isEmpty()) {
                    // Usuário
                    usuarioSessao.setLogin(usuario.getLogin());
                    usuarioSessao.setNome(usuario.getNome());
                    usuarioSessao.setEmail(usuario.getEmail());
                    usuarioSessao.setMatricula(usuario.getMatricula());
                    usuarioSessao.setCpf(usuario.getCpf());
                    usuarioSessao.setMatriculaCompleta(usuario.getMatriculaCompleta());
                    usuarioSessao.setSiglaCargoComissionado(usuario.getSiglaCargoComissionado());
                    usuarioSessao.setNomeCargoComissionado(usuario.getNomeCargoComissionado());
                    usuarioSessao.setNumeroCargoComissionado(usuario.getNumeroCargoComissionado());
                    usuarioSessao.setTitularCargoComissionado(usuario.getTitularUnidadeCargoComissionado());
                    if (usuario.getUnidade() != null) {
                        usuarioSessao.setCodigoUnidade(usuario.getUnidade().getId());
                        usuarioSessao.setUnidade(usuario.getUnidade().getSigla());
                    }
                    if (usuario.getTipoMagistrado() != null) {
                        usuarioSessao.setTipoMagistrado(usuario.getTipoMagistrado());
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect("selecionarPerfil.xhtml");
                } else {
                    MensagemUtil.error("Usuário sem acesso a este sistema.");
                }
            } else {
                MensagemUtil.info("Usuário não cadastrado.");
            }
        } else {
            MensagemUtil.warning("Usuário ou senha inválidos.");
        }
        conversation.end();
    }

    private String getUsuarioCAS() {
        Principal casPrincipal = (Principal) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (casPrincipal != null && !casPrincipal.getName().equals("")) {
            return casPrincipal.getName();
        }
        return null;
    }

    private String ip() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipCliente = request.getHeader("X-FORWARDED-FOR");
        if (ipCliente == null) {
            ipCliente = request.getRemoteAddr();
        }
        return ipCliente;
    }

    public void limpar() {
        login = null;
        senha = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
