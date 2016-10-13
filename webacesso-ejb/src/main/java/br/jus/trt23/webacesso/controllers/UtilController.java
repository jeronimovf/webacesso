package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.util.ConfiguracaoAplicacao;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.inject.Model;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Model
public class UtilController implements Serializable {

    @Getter
    @Setter
    @Inject
    private UsuarioSessao usuarioSessao;

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
        usuarioSessao = null;
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

    public String selecionarPerfil() throws IOException {
        usuarioSessao.setUnidade(null);
        usuarioSessao.setPerfil(null);
        usuarioSessao.setListaFuncionalidades(null);
        return "selecionarPerfil";
    }

    public List<Funcionalidade> getListaFuncionalidades() {
        return usuarioSessao.getListaFuncionalidades();
    }
    
    public Boolean contemFuncionalidade(final String nome){
        if(null == usuarioSessao.getListaFuncionalidades()){
            return false;
        }
        Stream<Funcionalidade> stream =usuarioSessao.getListaFuncionalidades().stream();
        List<Funcionalidade> funcionalidades = 
                stream.filter(
                        f -> f.getDescricao().equalsIgnoreCase(nome)
                ).collect(Collectors.toList());
        
        return funcionalidades.size() == 1;
    }
}
