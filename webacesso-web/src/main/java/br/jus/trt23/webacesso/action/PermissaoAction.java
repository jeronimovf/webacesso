package br.jus.trt23.webacesso.action;

import br.jus.trt23.nucleo.action.CrudAction;
import br.jus.trt23.nucleo.util.MensagemUtil;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Permissao;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.Usuario;
import br.jus.trt23.webacesso.sessions.PerfilFacade;
import br.jus.trt23.webacesso.sessions.PermissaoFacade;
import br.jus.trt23.webacesso.sessions.SistemaFacade;
import br.jus.trt23.webacesso.sessions.UnidadeIntegracaoFacade;
import br.jus.trt23.webacesso.sessions.UsuarioIntegracaoFacade;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PermissaoAction extends CrudAction<Permissao> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioSessao  usuarioSessao;
	
	@Inject
	private UsuarioIntegracaoFacade usuarioIntegracaoFacade;
	
	@Inject
	private PerfilFacade perfilFacade;
	
	@Inject
	private SistemaFacade sistemaFacade;
	
	@Inject
	private UnidadeIntegracaoFacade unidadeIntegracaoFacade;
	
	@Inject
	private PermissaoFacade permissaoFacade;

	private Permissao permissao;
	
	private Sistema sistemaSelecionado;
	
	private Perfil perfilSelecionado;
	
	private Usuario usuarioSelecionado;
	
	private String usuarioDigitado;
	
	private List<Permissao> listaPermissao;
	
	private List<Sistema> listaSistema;
	
	private List<Perfil> listaPerfil;
	
	private List<Unidade> listaUnidade;
	
	private List<Unidade> listaUnidadeSelecionada;
	
	public PermissaoAction()
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
			listaUnidade = unidadeIntegracaoFacade.listarUnidade();
			listaPermissao = permissaoFacade.listarPermissao(null, null, null, null);		
		}
		catch(Exception e)
		{
			MensagemUtil.error(e.getMessage());
		}
	}
	
	public void novo()
	{
		habilitaNovo = true;
		sistemaSelecionado = null;
		listaPerfil = null;
	}

	public List<Usuario> autoCompleteUsuario(final String query) 
	{
		return usuarioIntegracaoFacade.listarUsuarioLike(query);
    }
	
	public void carregarPerfil()
	{
		if(sistemaSelecionado != null)
		{
			listaPerfil = perfilFacade.listarPerfil(sistemaSelecionado);
		}
	}

	@Override
	public void pesquisar()
	{
		listaPermissao = permissaoFacade.listarPermissao(sistemaSelecionado, perfilSelecionado, usuarioSelecionado, null);		
	}

	public void editar(final Permissao permissaoSelecionada)
	{		
		permissao = permissaoSelecionada;
		//permissaoFacade.refresh(permissao);	
		sistemaSelecionado = permissao.getPerfil().getSistema();
		carregarPerfil();
		habilitaNovo = true;
	}
	
	public void excluir(final Permissao permissaoSelecionada)
	{		
		try
		{
			permissao = permissaoSelecionada;
			if(permissao != null)
			{
				permissao.setDataExcluido( new Date() );
				permissao.setUsuarioExcluir( usuarioSessao.getLogin() );
				permissaoFacade.remove(permissao);
				MensagemUtil.info("Permissão excluída com sucesso.");		
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
			if(permissao != null)
			{
				if(permissao.getId() != null)
				{
					permissaoFacade.edit(permissao);
					MensagemUtil.info("Permissão alterada com sucesso.");
				}
				else
				{									
					if(listaUnidadeSelecionada != null && !listaUnidadeSelecionada.isEmpty())
					{
						for(Unidade uni : listaUnidadeSelecionada)
						{
							Permissao permissaoUnidade = new Permissao();
							permissaoUnidade.setUsuario(permissao.getUsuario());
							permissaoUnidade.setPerfil(permissao.getPerfil());
							permissaoUnidade.setUnidade(uni);
							permissaoUnidade.setDataCadastro( new Date() );
							permissaoUnidade.setUsuarioCadastro( usuarioSessao.getLogin() );
							permissaoFacade.create(permissaoUnidade);							
						}
					}
					else
					{
						permissao.setDataCadastro( new Date() );
						permissao.setUsuarioCadastro( usuarioSessao.getLogin() );
						permissaoFacade.create(permissao);
					}					
					MensagemUtil.info("Permissão cadastrada com sucesso.");
				}		
				permissao = new Permissao();
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
		permissao = new Permissao();
		habilitaNovo = false;
	}	

	public Permissao getPermissao()
	{
		return permissao;
	}

	public void setPermissao(Permissao permissao)
	{
		this.permissao = permissao;
	}

	public Sistema getSistemaSelecionado()
	{
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado)
	{
		this.sistemaSelecionado = sistemaSelecionado;
	}

	public Perfil getPerfilSelecionado()
	{
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado)
	{
		this.perfilSelecionado = perfilSelecionado;
	}

	public Usuario getUsuarioSelecionado()
	{
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado)
	{
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getUsuarioDigitado()
	{
		return usuarioDigitado;
	}

	public void setUsuarioDigitado(String usuarioDigitado)
	{
		this.usuarioDigitado = usuarioDigitado;
	}

	public List<Sistema> getListaSistema()
	{
		return listaSistema;
	}

	public List<Perfil> getListaPerfil()
	{
		return listaPerfil;
	}
	
	public List<Unidade> getListaUnidade()
	{
		return listaUnidade;
	}

	public List<Unidade> getListaUnidadeSelecionada()
	{
		return listaUnidadeSelecionada;
	}

	public void setListaUnidadeSelecionada(List<Unidade> listaUnidadeSelecionada)
	{
		this.listaUnidadeSelecionada = listaUnidadeSelecionada;
	}

	@Override
	public List<Permissao> getLista()
	{		
		return listaPermissao;
	}
}