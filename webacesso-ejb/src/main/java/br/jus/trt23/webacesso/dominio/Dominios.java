package br.jus.trt23.webacesso.dominio;

import br.jus.trt23.nucleo.enums.TipoSimNao;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named(value="br.jus.trt23.webacesso.dominio.dominios")
@ApplicationScoped
public class Dominios implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public TipoSimNao[] listaTipoSimNao()
	{
		return TipoSimNao.values();
	}
}
