package br.jus.trt23.webacesso.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.inject.Model;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Model
public class UtilController implements Serializable {
    @Inject
    private ConfiguracaoAplicacao configuracaoAplicacao;

    public String getApplicationUri() {
        try {
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(), null, ext.getRequestServerName(), ext.getRequestServerPort(), ext.getRequestContextPath(), null, null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            throw new FacesException(e);
        }
    }

    public void redirect(final String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        //usuarioSessao = null;
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        redirect(configuracaoAplicacao.getUrlLogoutCAS());
    }

    public void intranet() throws IOException {
        if (configuracaoAplicacao.getUrlIntranet() != null) {
            redirect(configuracaoAplicacao.getUrlIntranet());
        } else {
            redirect("https://intranet.trt23.jus.br/");
        }
    }
}
