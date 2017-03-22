package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.nucleo.util.Util;
import br.jus.trt23.webacesso.entities.TipoUsuario;
import br.jus.trt23.webacesso.entities.UsuarioExterno;
import br.jus.trt23.webacesso.sessions.TipoUsuarioFacade;
import br.jus.trt23.webacesso.sessions.UsuarioExternoFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UsuarioExternoAction extends CrudAction<UsuarioExterno> implements Serializable 
{	
	private static final long serialVersionUID = 1L;	
	
	@Inject
	private UsuarioSessao  usuarioSessao;
	
	@Inject
	private TipoUsuarioFacade tipoUsuarioFacade;
	
	@Inject
	private UsuarioExternoFacade usuarioExternoFacade;
	
	private List<TipoUsuario> listaTipoUsuario;
	
	private List<UsuarioExterno> listaUsuarioExterno;
	
	private UsuarioExterno usuarioExterno;
			
	public UsuarioExternoAction()
	{
		super();
	}
	
	@PostConstruct
	public void carregar()
	{
		try
		{
			habilitaNovo = false;
			listaTipoUsuario = tipoUsuarioFacade.findAll();
			listaUsuarioExterno = usuarioExternoFacade.listarUsuario();
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}

	public void editar(final UsuarioExterno usuarioExternoSelecionado)
	{		
		usuarioExterno = usuarioExternoSelecionado;
		habilitaNovo = true;
	}
	
	public void excluir(final UsuarioExterno usuarioExternoSelecionado)
	{		
		try
		{
			usuarioExterno = usuarioExternoSelecionado;
			if(usuarioExterno != null)
			{
				usuarioExterno.setDataExcluido( new Date() );
				usuarioExterno.setUsuarioExcluir( usuarioSessao.getLogin() );
				usuarioExternoFacade.remove(usuarioExterno);
				MensagemUtil.info("Usuário externo excluído com sucesso.");		
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
			if(usuarioExterno != null)
			{
				if(usuarioExterno.getId() != null)
				{
					usuarioExterno.setCpf( Util.limparStringCaracteresLetras(usuarioExterno.getCpf()) );
					usuarioExternoFacade.edit(usuarioExterno);
					MensagemUtil.info("Usuário externo alterado com sucesso.");
				}
				else
				{					
					usuarioExterno.setCpf( Util.limparStringCaracteresLetras(usuarioExterno.getCpf()) );
					usuarioExterno.setDataCadastro( new Date() );
					usuarioExterno.setUsuarioCadastro( usuarioSessao.getLogin() );
					usuarioExternoFacade.create(usuarioExterno);
					MensagemUtil.info("Usuário externo cadastrado com sucesso.");
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
		usuarioExterno = new UsuarioExterno();
		habilitaNovo = false;
	}		

	public UsuarioExterno getUsuarioExterno()
	{
		return usuarioExterno;
	}

	public void setUsuarioExterno(UsuarioExterno usuarioExterno)
	{
		this.usuarioExterno = usuarioExterno;
	}
	
	public List<TipoUsuario> listaTipoUsuario() 
	{
		 return listaTipoUsuario;
	}

	@Override
	public List<UsuarioExterno> getLista()
	{
		return listaUsuarioExterno;
	}

	@Override
	public void pesquisar()
	{
		
	}
}