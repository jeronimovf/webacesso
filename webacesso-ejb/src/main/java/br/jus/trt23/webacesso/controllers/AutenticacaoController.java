package br.jus.trt23.webacesso.controllers;

import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Model
public class AutenticacaoController implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Inject
//    private UsuarioIntegracaoController usuarioIntegracaoController;

    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String senha;

    @Inject
    private UsuarioController usuarioSessao;

    @Inject
    private ConfiguracaoAplicacao configuracaoAplicacao;

//    public void autenticarCAS() throws IOException {
//        usuarioSessao.setLogin(getUsuarioCAS());
//        if (usuarioSessao.getLogin() != null && !usuarioSessao.getLogin().equals("")) {
//            autenticar();
//        }
//    }
//
//    @Transactional
//    public void autenticar() throws IOException {
//        boolean autenticado;
//        if (usuarioSessao.getLogin() != null && !usuarioSessao.getLogin().equals("")) {
//            autenticado = true;
//            login = usuarioSessao.getLogin();
//        } else {
//            autenticado = new Ldap().isUsuarioAutenticado(login, senha);
//        }
//        if (autenticado) {
//            Usuario usuario = usuarioIntegracaoController.buscarUsuario(login);
//            if (usuario != null) {
//                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "[{0}] {1} | {2} autenticado com sucesso.", new Object[]{ip(), usuario.getLogin(), usuario.getNome()});
//                List<Usuario> listaUsuario = usuarioIntegracaoController.obterListaUsuarioSistema(usuario);
//                List<Acesso> listaAcesso = acessoIntegracaoFacade.listarAcessos(new Sistema(configuracaoAplicacao.getCodigoSistema()), listaUsuario);
//                if (usuario.getDataDesligamento() != null && usuario.getDataDesligamento().before(Tempo.trunc(new java.util.Date()))) {
//                    Mensagem.error("Usuário foi desligado.");
//                    return;
//                }
//                if (listaAcesso != null && !listaAcesso.isEmpty()) {
//                    // Usuário
//                    usuarioSessao.setLogin(usuario.getLogin());
//                    usuarioSessao.setNome(usuario.getNome());
//                    usuarioSessao.setEmail(usuario.getEmail());
//                    usuarioSessao.setMatricula(usuario.getMatricula());
//                    usuarioSessao.setCpf(usuario.getCpf());
//                    usuarioSessao.setMatriculaCompleta(usuario.getMatriculaCompleta());
//                    usuarioSessao.setSiglaCargoComissionado(usuario.getSiglaCargoComissionado());
//                    usuarioSessao.setNomeCargoComissionado(usuario.getNomeCargoComissionado());
//                    usuarioSessao.setNumeroCargoComissionado(usuario.getNumeroCargoComissionado());
//                    usuarioSessao.setTitularCargoComissionado(usuario.getTitularUnidadeCargoComissionado());
//                    if (usuario.getUnidade() != null) {
//                        usuarioSessao.setCodigoUnidade(usuario.getUnidade().getId());
//                        usuarioSessao.setUnidade(usuario.getUnidade().getSigla());
//                    }
//                    if (usuario.getTipoMagistrado() != null) {
//                        usuarioSessao.setTipoMagistrado(usuario.getTipoMagistrado());
//                    }
//                    //FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
//                } else {
//                    Mensagem.error("Usuário sem acesso a este sistema.");
//                }
//            } else {
//                Mensagem.info("Usuário não cadastrado.");
//            }
//        } else {
//            Mensagem.warning("Usuário ou senha inválidos.");
//        }
//    }

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

    public AutenticacaoController() {
    }

}
