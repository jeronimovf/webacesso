package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.sessions.FuncionalidadeFacade;
import br.jus.trt23.webacesso.sessions.FuncionalidadeUrlFacade;
import br.jus.trt23.webacesso.sessions.RecursoFacade;
import br.jus.trt23.webacesso.sessions.SistemaFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class FuncionalidadeAction extends CrudAction<Funcionalidade> implements Serializable 
{
	@Inject
	private FuncionalidadeFacade funcionalidadeFacade;
	
	@Inject
	private UsuarioSessao  usuarioSessao;
		
	@Inject
	private RecursoFacade recursoFacade;
	
	@Inject
	private FuncionalidadeUrlFacade funcionalidadeUrlFacade;
	
	@Inject
	private SistemaFacade sistemaFacade;
	
	private List<Funcionalidade> listaFuncionalidade;
	
	private List<Perfil> listaPerfil;
	
	private List<Sistema> listaSistema;
	
	private Funcionalidade funcionalidade;
	
	private String descricaoRecurso;

	private Recurso recursoSelecionado;
	
	private Perfil perfilSelecionado;
	
	private Sistema sistemaSelecionado;
	
	private String url;
	
	private String viewId;
	
	private TipoSimNao principalUrl;
			
	public FuncionalidadeAction()
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
			listaFuncionalidade = funcionalidadeFacade.listarFuncionalidade(null);
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}
	
        @Override
	public void pesquisar()
	{
		listaFuncionalidade = funcionalidadeFacade.listarFuncionalidade(sistemaSelecionado);
	}

        @Override
	public void editar(final Funcionalidade funcionalidadeSelecionada)
	{		
            funcionalidade = funcionalidadeSelecionada;
            habilitaNovo = true;
	}
	
	public void excluir(final Funcionalidade funcionalidadeSelecionada)
	{		
		try
		{
			funcionalidade = funcionalidadeSelecionada;
			if(funcionalidade != null)
			{
				funcionalidade.setDataExcluido( new Date() );
				funcionalidade.setUsuarioExcluir( usuarioSessao.getLogin() );
				funcionalidadeFacade.remove(funcionalidade);
				MensagemUtil.info("Funcionalidade excluída com sucesso.");		
				carregar();
			}
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}
	
	public void adicionarRecurso()
	{		
		if(descricaoRecurso != null && !descricaoRecurso.trim().equals(""))
		{
			recursoSelecionado = new Recurso();
			recursoSelecionado.setFuncionalidade(funcionalidade);
			recursoSelecionado.setDescricao(descricaoRecurso);
			if(funcionalidade.getListaRecurso() == null)
			{
				funcionalidade.setListaRecurso(new LinkedHashSet<Recurso>());
			}
			funcionalidade.getListaRecurso().add(recursoSelecionado);
			descricaoRecurso = "";			
		}
		else
		{
			MensagemUtil.warning("É necessário informar a descrição do recurso.");
		}
	}
	
	public void adicionarUrl()
	{		
		if(url != null && !url.trim().equals("") && viewId != null && !viewId.trim().equals("") && principalUrl != null)
		{
			FuncionalidadeUrl funcionalidadeUrl = new FuncionalidadeUrl();
			
			funcionalidadeUrl.setUrl(url);
			funcionalidadeUrl.setViewId(viewId);
			funcionalidadeUrl.setPrincipalUrl(principalUrl);
			funcionalidadeUrl.setFuncionalidade(funcionalidade);			
			
			if(funcionalidade.getListaUrl() == null)
			{
				funcionalidade.setListaUrl(new LinkedHashSet<FuncionalidadeUrl>());
			}
			
			for(FuncionalidadeUrl url : funcionalidade.getListaUrl())
			{
				if(url.getPrincipalUrl().equals(TipoSimNao.S) && funcionalidadeUrl.getPrincipalUrl().equals(TipoSimNao.S))
				{
					MensagemUtil.warning("Funcionalidade só pode ter uma url principal.");
					return;
				}
			}				
			funcionalidade.getListaUrl().add(funcionalidadeUrl);	
			url = null;
			viewId = null;
			principalUrl = null;
		}
		else
		{
			MensagemUtil.warning("É necessário informar os valores referente à URL.");
		}
	}
	
	public void removerRecurso(final Recurso rec)
	{
		if(rec != null && funcionalidade.getListaRecurso() != null)
		{
			if(rec.getId() != null)
			{
				//recursoFacade.refresh(rec);
			}
			funcionalidade.getListaRecurso().remove(rec);
			if(rec.getId() != null)
			{
				recursoFacade.remove(rec);
			}
		}
	}
	
	public void removerUrl(final FuncionalidadeUrl funcionalidadeUrl)
	{
		if(funcionalidadeUrl != null && funcionalidade.getListaUrl() != null)
		{
			if(funcionalidadeUrl.getId() != null)
			{
				//funcionalidadeUrlFacade.refresh(funcionalidadeUrl);
			}
			funcionalidade.getListaUrl().remove(funcionalidadeUrl);
			if(funcionalidadeUrl.getId() != null)
			{
				funcionalidadeUrlFacade.remove(funcionalidadeUrl);
			}
		}
	}
	
	public void salvar()
	{
		try
		{
			if(funcionalidade != null)
			{
				if(funcionalidade.getId() != null)
				{					
					funcionalidadeFacade.edit(funcionalidade);
					MensagemUtil.info("Funcionalidade alterada com sucesso.");
				}
				else
				{					
					funcionalidade.setDataCadastro( new Date() );
					funcionalidade.setUsuarioCadastro( usuarioSessao.getLogin() );
					funcionalidadeFacade.create(funcionalidade);
					MensagemUtil.info("Funcionalidade cadastrada com sucesso.");
				}		
				funcionalidade = new Funcionalidade();
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
		funcionalidade = new Funcionalidade();
		habilitaNovo = false;
	}		

	public Funcionalidade getFuncionalidade()
	{
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade)
	{
		this.funcionalidade = funcionalidade;
	}

	public String getDescricaoRecurso()
	{
		return descricaoRecurso;
	}

	public void setDescricaoRecurso(String descricaoRecurso)
	{
		this.descricaoRecurso = descricaoRecurso;
	}

	public Recurso getRecursoSelecionado()
	{
		return recursoSelecionado;
	}

	public void setRecursoSelecionado(Recurso recursoSelecionado)
	{
		this.recursoSelecionado = recursoSelecionado;
	}

	public Perfil getPerfilSelecionado()
	{
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado)
	{
		this.perfilSelecionado = perfilSelecionado;
	}

	public Sistema getSistemaSelecionado()
	{
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado)
	{
		this.sistemaSelecionado = sistemaSelecionado;
	}
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getViewId()
	{
		return viewId;
	}

	public void setViewId(String viewId)
	{
		this.viewId = viewId;
	}

	public TipoSimNao getPrincipalUrl()
	{
		return principalUrl;
	}

	public void setPrincipalUrl(TipoSimNao principalUrl)
	{
		this.principalUrl = principalUrl;
	}

	public List<Sistema> getListaSistema()
	{
		return listaSistema;
	}

	public List<Perfil> getListaPerfil()
	{
		return listaPerfil;
	}

	@Override
	public List<Funcionalidade> getLista()
	{
		return listaFuncionalidade;
	}
}