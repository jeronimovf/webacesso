package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.sessions.FuncionalidadeFacade;
import br.jus.trt23.webacesso.sessions.PerfilFacade;
import br.jus.trt23.webacesso.sessions.RecursoFacade;
import br.jus.trt23.webacesso.sessions.SistemaFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PerfilAction extends CrudAction<Perfil> implements Serializable 
{
	private static final long serialVersionUID = 1L;	
	
	@Inject
	private SistemaFacade sistemaFacade;
	
	@Inject
	private PerfilFacade perfilFacade;
	
	@Inject
	private FuncionalidadeFacade funcionalidadeFacade;
	
	@Inject
	private RecursoFacade recursoDAO;
	
	private List<Sistema> listaSistema;
	
	private List<Perfil> listaPerfil;
	
	private List<Funcionalidade> listaFuncionalidade;
	
	private List<Funcionalidade> listaFuncionalidadeSelecionada;
	
	private List<Recurso> listaRecurso;
	
	private List<Recurso> listaRecursoSelecionada;
	
	private Perfil perfil;
	
	private Sistema sistemaSelecionado;
	
	private UsuarioSessao  usuarioSessao;
	
			
	public PerfilAction()
	{
		super();
		setHabilitaPesquisa(true);
	}
	
	@PostConstruct
	public void carregar()
	{
		try
		{
			habilitaNovo = false;
			listaSistema = sistemaFacade.listarSistema();
			listaPerfil = perfilFacade.listarPerfil(sistemaSelecionado);
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}
	
	public void carregarFuncionalidades()
	{
		if(perfil != null && perfil.getSistema() != null)
		{
			listaFuncionalidade = funcionalidadeFacade.listarFuncionalidade(perfil.getSistema());
		}
	}
	
	public void carregarRecursos()
	{
		if(perfil != null && perfil.getId() != null && listaFuncionalidadeSelecionada != null)
		{
			listaRecurso = recursoDAO.listarRecursos(listaFuncionalidadeSelecionada);
		}
		else if(perfil != null && perfil.getId() == null)
		{
			listaRecurso = recursoDAO.listarRecursos(new ArrayList<Funcionalidade>(perfil.getListaFuncionalidade()));
		}
	}
	
	public void pesquisar()
	{
		listaPerfil = perfilFacade.listarPerfil(sistemaSelecionado);
	}

	public void editar(final Perfil perfilSelecionado)
	{		
		perfil = perfilSelecionado;
		//perfilFacade.refresh(perfil);
		carregarFuncionalidades();
		listaFuncionalidadeSelecionada = new ArrayList<Funcionalidade>(perfil.getListaFuncionalidade());
		carregarRecursos();
		listaRecursoSelecionada = new ArrayList<Recurso>(perfil.getListaRecurso());
		habilitaNovo = true;
	}
	
	public void excluir(final Perfil perfilSelecionado)
	{		
		try
		{
			perfil = perfilSelecionado;
			if(perfil != null)
			{
				perfil.setDataExcluido( new Date() );
				perfil.setUsuarioExcluir( usuarioSessao.getLogin() );
				perfilFacade.remove(perfil);
				MensagemUtil.info("Perfil excluído com sucesso.");		
				carregar();
			}
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}
	
	public void salvar()
	{
		try
		{
			if(perfil != null)
			{
				if(perfil.getId() != null)
				{
					perfil.setListaFuncionalidade(new HashSet<Funcionalidade>(listaFuncionalidadeSelecionada));
					perfil.setListaRecurso(new HashSet<Recurso>(listaRecursoSelecionada));
					perfilFacade.edit(perfil);
					MensagemUtil.info("Perfil alterado com sucesso.");
				}
				else
				{									
					perfil.setDataCadastro( new Date() );
					perfil.setUsuarioCadastro( usuarioSessao.getLogin() );
					perfilFacade.create(perfil);
					MensagemUtil.info("Perfil cadastrado com sucesso.");
				}		
				listaFuncionalidade = null;
				listaFuncionalidadeSelecionada = null;
				listaRecurso = null;
				listaRecursoSelecionada = null;
				perfil = new Perfil();
				carregar();
			}
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}		
	}
	
	public void cancelar()
	{
		perfil = new Perfil();
		listaFuncionalidade = null;
		listaRecurso = null;
		habilitaNovo = false;
	}		

	public Perfil getPerfil()
	{
		return perfil;
	}

	public void setPerfil(Perfil perfil)
	{
		this.perfil = perfil;
	}
	
	public Sistema getSistemaSelecionado()
	{
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado)
	{
		this.sistemaSelecionado = sistemaSelecionado;
	}

	public List<Sistema> getListaSistema() 
	{
		 return listaSistema;
	}
	
	public List<Funcionalidade> getListaFuncionalidade() 
	{
		 return listaFuncionalidade;
	}
	
	public List<Funcionalidade> getListaFuncionalidadeSelecionada()
	{
		return listaFuncionalidadeSelecionada;
	}
	
	public void setListaFuncionalidadeSelecionada(List<Funcionalidade> listaFuncionalidadeSelecionada)
	{
		this.listaFuncionalidadeSelecionada = listaFuncionalidadeSelecionada;
	}

	public List<Recurso> getListaRecurso()
	{
		return listaRecurso;
	}

	public List<Recurso> getListaRecursoSelecionada()
	{
		return listaRecursoSelecionada;
	}

	public void setListaRecursoSelecionada(List<Recurso> listaRecursoSelecionada)
	{
		this.listaRecursoSelecionada = listaRecursoSelecionada;
	}

	@Override
	public List<Perfil> getLista()
	{
		return listaPerfil;
	}
}