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
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Model
public class AutenticacaoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioIntegracaoController usuarioIntegracaoController;

    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String senha;

    @Inject
    private AcessoIntegracaoFacade acessoIntegracaoFacade;

    @Inject
    private UsuarioSessao usuarioSessao;

    @Inject
    private ConfiguracaoAplicacao configuracaoAplicacao;

    public void autenticarCAS() throws IOException {
        usuarioSessao.setLogin(getUsuarioCAS());
        if (usuarioSessao.getLogin() != null && !usuarioSessao.getLogin().equals("")) {
            autenticar();
        }
    }

    @Transactional
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
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "[{0}] {1} | {2} autenticado com sucesso.", new Object[]{ip(), usuario.getLogin(), usuario.getNome()});
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
                    usuarioSessao.setIpEndereco(ip());
                    if (usuario.getUnidade() != null) {
                        usuarioSessao.setCodigoUnidade(usuario.getUnidade().getId());
                        usuarioSessao.setUnidade(usuario.getUnidade().getSigla());
                    }
                    if (usuario.getTipoMagistrado() != null) {
                        usuarioSessao.setTipoMagistrado(usuario.getTipoMagistrado());
                    }
                    //FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } else {
                    MensagemUtil.error("Usuário sem acesso a este sistema.");
                }
            } else {
                MensagemUtil.info("Usuário não cadastrado.");
            }
        } else {
            MensagemUtil.warning("Usuário ou senha inválidos.");
        }
    }

    public String getUsuarioCAS() {
        Principal casPrincipal = (Principal) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (casPrincipal != null && !casPrincipal.getName().equals("")) {
            return casPrincipal.getName();
        }
        return null;
    }

    public String ip() {
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

    public AutenticacaoController() {
    }

}
