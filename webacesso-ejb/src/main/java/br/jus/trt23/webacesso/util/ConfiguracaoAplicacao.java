package br.jus.trt23.webacesso.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.jus.trt23.nucleo.constantes.Constantes;

@Named
@ApplicationScoped
public class ConfiguracaoAplicacao implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Long getCodigoSistema()
	{
		String codigo =  FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.CODIGO_SISTEMA);
		if(codigo != null)
		{
			return new Long(codigo);
		}
		throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro CODIGO_SISTEMA.");
	}
	
	public String getUrlLogoutCAS()
	{
		String url =  FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.CAS_LOGOUT_URL);
		if(url != null)
		{
			return url;
		}
		throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro CAS_LOGOUT_URL.");
	}
	
	public String getUrlIntranet()
	{
		String url =  FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.INTRANET_URL);
		if(url != null)
		{
			return url;
		}
		throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro INTRANET_URL.");
	}
	
	public boolean isAmbienteProducao()
	{
		String ambiente =  FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.AMBIENTE_PRODUCAO);
		if(ambiente != null && ambiente.equals("S"))
		{
			return true;
		}
		return false;
	}
}