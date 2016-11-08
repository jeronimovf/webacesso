package br.jus.trt23.webacesso.filter;

import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

@Model
public class AutorizacaoListener implements PhaseListener {
 

    @Inject
    UsuarioSessao usuarioSessao;

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean paginaSemPermissao = (currentPage.lastIndexOf("index.xhtml") > -1) || (currentPage.lastIndexOf("menu.xhtml") > -1)
                || (currentPage.lastIndexOf("selecionarPerfil.xhtml") > -1) || (currentPage.lastIndexOf("erro.xhtml") > -1) || (currentPage.lastIndexOf("sessaoExpirada.xhtml") > -1);
        boolean paginaLogin = (currentPage.lastIndexOf("index.xhtml") > -1);

        if (paginaLogin) {
            // Página de login
        } else if (paginaSemPermissao && usuarioSessao != null && usuarioSessao.isUsuarioLogado()) {
            // Página não precisa de permissão
        } else if (!paginaSemPermissao && usuarioSessao != null && usuarioSessao.isUsuarioLogado()) {
            // Verificar Permissão
            boolean hasPermission = false;
            if (usuarioSessao.getListaFuncionalidades() != null) {
                List<FuncionalidadeUrl> lFUrl;
                Stream<FuncionalidadeUrl> sFUrl;
                List<FuncionalidadeUrl> lFUrlParaPagina;
                for (Funcionalidade funcionalidade : usuarioSessao.getListaFuncionalidades()) {
                    if (funcionalidade.getListaUrl() != null) {
                        lFUrl = new ArrayList<>(funcionalidade.getListaUrl());
                        sFUrl = lFUrl.stream();
                        lFUrlParaPagina = sFUrl.filter(f -> f.getViewId().equalsIgnoreCase(currentPage.substring(1))).collect(Collectors.toList());
                        if (lFUrlParaPagina.size() == 1) {
                            hasPermission = true;
                            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Acesso permitido: {0} | {1}", new Object[]{currentPage, usuarioSessao.getLogin()});
                            if (lFUrl.get(0).getPrincipalUrl() != null && lFUrl.get(0).getPrincipalUrl().equals(TipoSimNao.S)) {
                                // Recursos Permitidos
                                usuarioSessao.carregar();
                                List<Recurso> listaRecursosPermitidos = usuarioSessao.getRecursosPermitidos(funcionalidade);
                                if (listaRecursosPermitidos != null) {
                                    for (Recurso recurso : listaRecursosPermitidos) {
                                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Recurso: {0}", new Object[]{recurso.getDescricao()});                                        
                                        System.out.println("Recurso: " + recurso.getDescricao());
                                        usuarioSessao.putMapaRecursosPermitido(recurso);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }

            if (!hasPermission) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Acesso negado: {0} | {1}", new Object[]{currentPage, usuarioSessao.getLogin()});                
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "ACESSO NEGADO"));
                nh.handleNavigation(facesContext, null, "/erro.xhtml?faces-redirect=true");
            }
        } else if (paginaSemPermissao && (usuarioSessao == null || !usuarioSessao.isUsuarioLogado())) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "É NECESSÁRIO SE AUTENTICAR"));
            nh.handleNavigation(facesContext, null, "login");
        } else {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "SESSÃO EXPIRADA"));
            nh.handleNavigation(facesContext, null, "login");
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
