package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.sessions.SistemaFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class SistemaAction extends CrudAction<Sistema> implements Serializable 
{	
	private static final long serialVersionUID = 1L;	
	
	@Inject
	private UsuarioSessao  usuarioSessao;
	
	@Inject
	private SistemaFacade sistemaFacade;
		
	private List<Sistema> listaSistema;
		
	private Sistema sistema;
			
	public SistemaAction()
	{
		super();
	}
	
	@PostConstruct
	public void carregar()
	{
		try
		{
			habilitaNovo = false;
			listaSistema = sistemaFacade.listarSistema();
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}

	public void editar(final Sistema sistemaSelecionado)
	{		
		sistema = sistemaSelecionado;
		habilitaNovo = true;
	}
	
	public void excluir(final Sistema sistemaSelecionado)
	{		
		try
		{
			sistema = sistemaSelecionado;
			if(sistema != null)
			{
				sistema.setDataExcluido( new Date() );
				sistema.setUsuarioExcluir( usuarioSessao.getLogin() );
				sistemaFacade.remove(sistema);
				MensagemUtil.info("Sistema excluído com sucesso.");		
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
			if(sistema != null)
			{
				if(sistema.getId() != null)
				{
					sistemaFacade.edit(sistema);
					MensagemUtil.info("Sistema alterado com sucesso.");
				}
				else
				{					
					sistema.setDataCadastro( new Date() );
					sistema.setUsuarioCadastro( usuarioSessao.getLogin() );
					sistemaFacade.edit(sistema);
					MensagemUtil.info("Sistema cadastrado com sucesso.");
				}				
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
		sistema = new Sistema();
		habilitaNovo = false;
	}		

	public Sistema getSistema()
	{
		return sistema;
	}

	public void setSistema(Sistema sistema)
	{
		this.sistema = sistema;
	}

	@Override
	public List<Sistema> getLista()
	{
		return listaSistema;
	}

	@Override
	public void pesquisar()
	{
		
	}
}