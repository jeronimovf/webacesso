package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.constants.Constantes;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ConfiguracaoAplicacao implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long getCodigoSistema() {
        String codigo = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.CODIGO_SISTEMA);
        if (codigo != null) {
            return new Long(codigo);
        }
        throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro CODIGO_SISTEMA.");
    }

    public String getUrlLogoutCAS() {
        String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.CAS_LOGOUT_URL);
        if (url != null) {
            return url;
        }
        throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro CAS_LOGOUT_URL.");
    }

    public String getUrlIntranet() {
        String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.INTRANET_URL);
        if (url != null) {
            return url;
        }
        throw new RuntimeException("Favor configurar o arquivo web.xml com o parâmetro INTRANET_URL.");
    }

    public boolean isAmbienteProducao() {
        return isAmbiente("P");
    }

    public boolean isAmbienteDesenvolvimento() {
        return isAmbiente("D");
    }

    public boolean isAmbienteHomologacao() {
        return isAmbiente("H");
    }

    private boolean isAmbiente(final String ambiente) {
        String ambienteConfigurado = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constantes.AMBIENTE);
        if (null != ambiente && null != ambienteConfigurado) {
            return ambiente.equals(ambienteConfigurado);
        }
        return false;
    }

}
