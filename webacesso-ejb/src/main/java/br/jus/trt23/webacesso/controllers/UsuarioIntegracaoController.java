package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.Usuario;
import br.jus.trt23.webacesso.enums.TipoServidor;
import br.jus.trt23.webacesso.sessions.UsuarioIntegracaoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UsuarioIntegracaoController implements Serializable{

    @Inject
    private UsuarioIntegracaoFacade usuarioIntegracaoFacade;

    public Usuario buscarUsuario(final String login) {
        return usuarioIntegracaoFacade.buscarUsuario(login);
    }

    public List<Usuario> listarUsuarioUnidade(final Unidade unidade) {
        if (unidade != null) {
            return usuarioIntegracaoFacade.listarUsuarioUnidade(unidade);
        }
        return null;
    }

    public List<Usuario> listarUsuarioLike(final String query) {
        return usuarioIntegracaoFacade.listarUsuarioLike(query);
    }

    public List<Usuario> listarUsuarioMagistrado(final String filtro) {
        return usuarioIntegracaoFacade.listarUsuarioJuizLike(filtro);
    }

    public List<Usuario> listarUsuarioServidor(final String filtro) {
        return usuarioIntegracaoFacade.listarUsuarioServidorLike(filtro);
    }

    public List<Usuario> listarUsuario() {
        return usuarioIntegracaoFacade.findAll();
    }

    /**
     * Obtém a lista de usuários de sistema da qual o perfil do usuário se
     * encaixa
     *
     * @param usuario
     * @return List<Usuario>
     */
    public List<Usuario> obterListaUsuarioSistema(final Usuario usuario) {
        List<Usuario> listaUsuario = new ArrayList<>();
        listaUsuario.add(usuario);
        listaUsuario.add(new Usuario(Usuario.PUBLICO));
        // Possuí FC/CJ e este cargo é o de titular da unidade				
        if (usuario.isTitularUnidadeCargoComissionado() && (usuario.getGestor() == null || usuario.getGestor().equals(TipoSimNao.N))) {
            listaUsuario.add(new Usuario(Usuario.CHEFIA));
        }
        if (usuario.getGestor() != null && usuario.getGestor().equals(TipoSimNao.S)) {
            listaUsuario.add(new Usuario(Usuario.GESTOR));
        }
        if (usuario.getTipoServidor() != null) {
            if (usuario.getTipoServidor().equals(TipoServidor.V)) {
                listaUsuario.add(new Usuario(Usuario.SERVIDOR_VARA));
            } else if (usuario.getTipoServidor().equals(TipoServidor.G)) {
                listaUsuario.add(new Usuario(Usuario.SERVIDOR_GABINETE));
            }

            if (usuario.getTipoServidor().equals(TipoServidor.A) || usuario.getTipoServidor().equals(TipoServidor.V) || usuario.getTipoServidor().equals(TipoServidor.G)) {
                listaUsuario.add(new Usuario(Usuario.SERVIDOR));
            }
        }
        if (usuario.getTipoMagistrado() != null) {
            switch (usuario.getTipoMagistrado()) {
                case S:
                    listaUsuario.add(new Usuario(Usuario.JUIZ_SUBSTITUTO));
                    break;
                case T:
                    listaUsuario.add(new Usuario(Usuario.JUIZ_TITULAR));
                    break;
                case D:
                    listaUsuario.add(new Usuario(Usuario.DESEMBARGADOR));
                    break;
                default:
                    break;
            }
        }
        return listaUsuario;
    }
}
