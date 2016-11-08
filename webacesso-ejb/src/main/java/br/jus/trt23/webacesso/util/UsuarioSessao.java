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
public class UsuarioSessao implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String matricula;
	private String cpf;
	private String matriculaCompleta;
	private String nome;
	private String email;
	private Long codigoUnidade;
	private String unidade;	
	
	private String siglaCargoComissionado;
	private String nomeCargoComissionado;
	private Long numeroCargoComissionado;
	private Long titularCargoComissionado;
	private TipoMagistrado tipoMagistrado;
        @Getter
        @Setter
        private Acesso acessoAtivo;
	
	private String perfil;
	private boolean multipluPerfil;
	private List<Funcionalidade> listaFuncionalidades;
	
	private Map<String, Recurso> mapaRecursosPermitido;
	private Map<Funcionalidade, List<Recurso>> mapaListaRecurso;
	
	public UsuarioSessao()
	{
		super();
		mapaListaRecurso = new HashMap<>();
	}
	
	public String getLogin() 
	{
		return login;
	}
	
	public void setLogin(String login) 
	{
		this.login = login;
	}
	
	public String getMatricula() 
	{
		return matricula;
	}
	
	public void setMatricula(String matricula) 
	{
		this.matricula = matricula;
	}
	
	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getMatriculaCompleta()
	{
		return matriculaCompleta;
	}

	public void setMatriculaCompleta(String matriculaCompleta)
	{
		this.matriculaCompleta = matriculaCompleta;
	}

	public String getNome() 
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public Long getCodigoUnidade()
	{
		return codigoUnidade;
	}

	public void setCodigoUnidade(Long codigoUnidade)
	{
		this.codigoUnidade = codigoUnidade;
	}

	public String getUnidade() 
	{
		return unidade;
	}
	
	public void setUnidade(String unidade) 
	{
		this.unidade = unidade;
	}
	
	public String getSiglaCargoComissionado()
	{
		return siglaCargoComissionado;
	}

	public void setSiglaCargoComissionado(String siglaCargoComissionado)
	{
		this.siglaCargoComissionado = siglaCargoComissionado;
	}
	
	public boolean isCJ()
	{
		return siglaCargoComissionado != null && siglaCargoComissionado.startsWith("CJ");
	}

	public String getNomeCargoComissionado()
	{
		return nomeCargoComissionado;
	}

	public void setNomeCargoComissionado(String nomeCargoComissionado)
	{
		this.nomeCargoComissionado = nomeCargoComissionado;
	}

	public Long getNumeroCargoComissionado()
	{
		return numeroCargoComissionado;
	}

	public void setNumeroCargoComissionado(Long numeroCargoComissionado)
	{
		this.numeroCargoComissionado = numeroCargoComissionado;
	}

	public Long getTitularCargoComissionado()
	{
		return titularCargoComissionado;
	}

	public void setTitularCargoComissionado(Long titularCargoComissionado)
	{
		this.titularCargoComissionado = titularCargoComissionado;
	}

	public TipoMagistrado getTipoMagistrado()
	{
		return tipoMagistrado;
	}

	public void setTipoMagistrado(TipoMagistrado tipoMagistrado)
	{
		this.tipoMagistrado = tipoMagistrado;
	}

	public String getPerfil()
	{
		return perfil;
	}

	public void setPerfil(String perfil)
	{
		this.perfil = perfil;
	}

	public boolean isMultipluPerfil()
	{
		return multipluPerfil;
	}

	public void setMultipluPerfil(boolean multipluPerfil)
	{
		this.multipluPerfil = multipluPerfil;
	}

	public List<Funcionalidade> getListaFuncionalidades()
	{
		return listaFuncionalidades;
	}

	public void setListaFuncionalidades(List<Funcionalidade> listaFuncionalidades)
	{
		this.listaFuncionalidades = listaFuncionalidades;
	}
	
	public boolean isUsuarioLogado()
	{
		return (this.getLogin() != null && !this.getLogin().isEmpty());
	}
	
	public boolean isUsuarioPossuiUnidade()
	{
		return (this.isUsuarioLogado() && this.getUnidade() != null && !this.getUnidade().isEmpty());
	}
	
	public boolean isPerfilSelecionado()
	{
		return (this.isUsuarioLogado() && this.getPerfil() != null && !this.getPerfil().isEmpty());
	}
	
	public boolean isUsuarioLoginUsuarioLogado(final String login)
	{
		if(login != null)
		{
			return getLogin().equals(login);
		}
		return false;
	}
	
	public List<Recurso> getRecursosPermitidos(final Funcionalidade funcionalidade)
	{
		if(funcionalidade != null && mapaListaRecurso != null)
		{
			return mapaListaRecurso.get(funcionalidade);			
		}
		return null;
	}
	
	public void putRecursosPermitidos(final Funcionalidade funcionalidade, final List<Recurso> listaRecurso)
	{
		if(funcionalidade != null && listaRecurso != null && mapaListaRecurso != null)
		{
			mapaListaRecurso.put(funcionalidade, listaRecurso);
		}
	}
	
	public boolean hasPemission(final String recurso)
	{
		if(recurso != null && mapaRecursosPermitido != null)
		{
			return mapaRecursosPermitido.containsKey(recurso);
		}
		return false;
	}

	public void putMapaRecursosPermitido(final Recurso recurso)
	{
		if(recurso != null && mapaRecursosPermitido != null)
		{
			mapaRecursosPermitido.put(recurso.getDescricao(), recurso);
		}
	}

	public void carregar()
	{
		mapaRecursosPermitido = new HashMap<>();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UsuarioSessao))
			return false;
		UsuarioSessao other = (UsuarioSessao) obj;
		if (login == null)
		{
			if (other.getLogin() != null)
				return false;
		}
		else if (!login.equals(other.getLogin()))
			return false;
		return true;
	}
}