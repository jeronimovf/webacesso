package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.UnidadeSemGestor;
import br.jus.trt23.webacesso.entities.Usuario;
import br.jus.trt23.webacesso.sessions.UnidadeIntegracaoFacade;
import br.jus.trt23.webacesso.sessions.UnidadeSemGestorFacade;
import br.jus.trt23.webacesso.sessions.UsuarioIntegracaoFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UnidadeSemGestorAction extends CrudAction<UnidadeSemGestor> implements Serializable 
{	
	private static final long serialVersionUID = 1L;	
	
	@Inject
	private UsuarioSessao  usuarioSessao;
	
	@Inject
	private UnidadeSemGestorFacade unidadeSemGestorFacade;
	
	@Inject
	private UnidadeIntegracaoFacade unidadeIntegracaoFacade;
	
	@Inject
	private UsuarioIntegracaoFacade usuarioIntegracaoFacade;
	
	private UnidadeSemGestor unidadeSemGestor;
	
	private List<UnidadeSemGestor> listaUnidadeSemGestor;
	
	private List<Unidade> listaUnidade;
	
	public UnidadeSemGestorAction()
	{
		super();		
	}
	
	@PostConstruct
	public void carregar()
	{
		try
		{
			habilitaNovo = false;
			listaUnidadeSemGestor = unidadeSemGestorFacade.listarUnidadeSemGestor();
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}		
	}
	
	public List<Unidade> autoCompleteUnidade(final String filtro) 
	{
		return unidadeIntegracaoFacade.listarUnidadeLike(filtro);
    }
	
	public List<Usuario> autoCompleteUsuario(final String filtro) 
	{
		return usuarioIntegracaoFacade.listarUsuarioLike(filtro);
    }
	
	public void pesquisar()
	{
		
	}

	public void editar(final UnidadeSemGestor unidadeSemGestorSelecionado)
	{		
		unidadeSemGestor = unidadeSemGestorSelecionado;
		habilitaNovo = true;
	}
	
	public void excluir(final UnidadeSemGestor unidadeSemGestorSelecionado)
	{		
		try
		{
			unidadeSemGestor = unidadeSemGestorSelecionado;
			if(unidadeSemGestor != null)
			{
				unidadeSemGestor.setDataExcluido( new Date() );
				unidadeSemGestor.setUsuarioExcluir( new Usuario(usuarioSessao.getLogin()) );
				unidadeSemGestorFacade.remove(unidadeSemGestor);
				MensagemUtil.info("Unidade removida com sucesso.");	
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
			if(unidadeSemGestor != null)
			{
				if(unidadeSemGestor.getId() != null)
				{
					unidadeSemGestorFacade.edit(unidadeSemGestor);
					MensagemUtil.info("Unidade alterada com sucesso.");
				}
				else
				{					
					UnidadeSemGestor consulta = unidadeSemGestorFacade.obterUnidadeSemGestor(unidadeSemGestor.getUnidade());
					if(consulta != null && consulta.getId() != null)
					{
						MensagemUtil.warning("Unidade já foi vinculada.");
					}
					else
					{
						unidadeSemGestor.setDataCadastro( new Date() );
						unidadeSemGestor.setUsuarioCadastro( new Usuario(usuarioSessao.getLogin()) );
						unidadeSemGestorFacade.create(unidadeSemGestor);
						MensagemUtil.info("Unidade vinculada com sucesso.");
					}
				}	
				unidadeSemGestor = new UnidadeSemGestor();
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
		unidadeSemGestor = new UnidadeSemGestor();
		habilitaNovo = false;
		carregar();
	}		

	public UnidadeSemGestor getUnidadeSemGestor()
	{
		return unidadeSemGestor;
	}

	public void setUnidadeSemGestor(UnidadeSemGestor unidadeSemGestor)
	{
		this.unidadeSemGestor = unidadeSemGestor;
	}
	
	@Override
	public List<UnidadeSemGestor> getLista()
	{
		return listaUnidadeSemGestor;
	}

	public List<Unidade> getListaUnidade()
	{
		return listaUnidade;
	}	
}