package br.jus.trt23.webacesso.util;

import br.jus.trt23.webacesso.entities.Acesso;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.enums.TipoMagistrado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class UsuarioSessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String login;
    
    @Getter
    @Setter
    private String matricula;
    
    @Getter
    @Setter
    private String cpf;
    
    @Getter
    @Setter
    private String matriculaCompleta;
    
    @Getter
    @Setter
    private String nome;
    
    @Getter
    @Setter
    private String email;
    
    @Getter
    @Setter
    private Long codigoUnidade;
    
    @Getter
    @Setter
    private String unidade;
    
    @Getter
    @Setter
    private String siglaCargoComissionado;
    
    @Getter
    @Setter
    private String nomeCargoComissionado;
    
    @Getter
    @Setter
    private Long numeroCargoComissionado;
    
    @Getter
    @Setter
    private Long titularCargoComissionado;
    
    @Getter
    @Setter
    private TipoMagistrado tipoMagistrado;
    
    @Getter
    @Setter
    private String ipEndereco;
    
    @Getter
    @Setter
    private Acesso acessoAtivo;

    @Getter
    @Setter
    private String perfil;
    
    @Getter
    @Setter
    private boolean multipluPerfil;
    
    @Getter
    @Setter
    private List<Funcionalidade> listaFuncionalidades;

    private Map<String, Recurso> mapaRecursosPermitido;
    @Getter
    @Setter    
    private Map<Funcionalidade, List<Recurso>> mapaListaRecurso;

    public UsuarioSessao() {
        super();
        mapaListaRecurso = new HashMap<>();
    }

    public boolean isCJ() {
        return siglaCargoComissionado != null && siglaCargoComissionado.startsWith("CJ");
    }

    public boolean isUsuarioLogado() {
        return (this.getLogin() != null && !this.getLogin().isEmpty());
    }

    public boolean isUsuarioPossuiUnidade() {
        return (this.isUsuarioLogado() && this.getUnidade() != null && !this.getUnidade().isEmpty());
    }

    public boolean isPerfilSelecionado() {
        return (this.isUsuarioLogado() && this.getPerfil() != null && !this.getPerfil().isEmpty());
    }

    public boolean isUsuarioLoginUsuarioLogado(final String login) {
        if (login != null) {
            return getLogin().equals(login);
        }
        return false;
    }

    public List<Recurso> getRecursosPermitidos(final Funcionalidade funcionalidade) {
        if (funcionalidade != null && mapaListaRecurso != null) {
            return mapaListaRecurso.get(funcionalidade);
        }
        return null;
    }

    public void putRecursosPermitidos(final Funcionalidade funcionalidade, final List<Recurso> listaRecurso) {
        if (funcionalidade != null && listaRecurso != null && mapaListaRecurso != null) {
            mapaListaRecurso.put(funcionalidade, listaRecurso);
        }
    }

    public boolean hasPemission(final String recurso) {
        if (recurso != null && mapaRecursosPermitido != null) {
            return mapaRecursosPermitido.containsKey(recurso);
        }
        return false;
    }

    public void putMapaRecursosPermitido(final Recurso recurso) {
        if (recurso != null && mapaRecursosPermitido != null) {
            mapaRecursosPermitido.put(recurso.getDescricao(), recurso);
        }
    }

    public void carregar() {
        mapaRecursosPermitido = new HashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UsuarioSessao)) {
            return false;
        }
        UsuarioSessao other = (UsuarioSessao) obj;
        if (login == null) {
            if (other.getLogin() != null) {
                return false;
            }
        } else if (!login.equals(other.getLogin())) {
            return false;
        }
        return true;
    }
}
