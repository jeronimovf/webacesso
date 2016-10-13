package br.jus.trt23.webacesso.filter;

import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

@Model
public class AutorizacaoListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean paginaSemPermissao = (currentPage.lastIndexOf("index.xhtml") > -1) || (currentPage.lastIndexOf("menu.xhtml") > -1)
                || (currentPage.lastIndexOf("selecionarPerfil.xhtml") > -1) || (currentPage.lastIndexOf("erro.xhtml") > -1) || (currentPage.lastIndexOf("sessaoExpirada.xhtml") > -1);
        boolean paginaLogin = (currentPage.lastIndexOf("index.xhtml") > -1);

        UsuarioSessao usuarioSessao = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{usuarioSessao}", UsuarioSessao.class);

        if (paginaLogin) {
            // Página de login
        } else if (paginaSemPermissao && usuarioSessao != null && usuarioSessao.isUsuarioLogado()) {
            // Página não precisa de permissão
        } else if (!paginaSemPermissao && usuarioSessao != null && usuarioSessao.isUsuarioLogado()) {
            // Verificar Permissão
            boolean hasPermission = false;
            if (usuarioSessao.getListaFuncionalidades() != null) {
                for (Funcionalidade funcionalidade : usuarioSessao.getListaFuncionalidades()) {
                    if (funcionalidade.getListaUrl() != null) {
                        for (FuncionalidadeUrl funcinalidadeUrl : funcionalidade.getListaUrl()) {
                            if (currentPage.equalsIgnoreCase("/" + funcinalidadeUrl.getViewId())) {
                                hasPermission = true;
                                System.out.println("Acesso permitido: " + currentPage + " | " + usuarioSessao.getLogin());

                                if (funcinalidadeUrl.getPrincipalUrl() != null && funcinalidadeUrl.getPrincipalUrl().equals(TipoSimNao.S)) {
                                    // Recursos Permitidos
                                    usuarioSessao.carregar();
                                    List<Recurso> listaRecursosPermitidos = usuarioSessao.getRecursosPermitidos(funcionalidade);
                                    if (listaRecursosPermitidos != null) {
                                        for (Recurso recurso : listaRecursosPermitidos) {
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
            }
            if (!hasPermission) {
                System.out.println("Acesso negado: " + currentPage + " | " + usuarioSessao.getLogin());
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "ACESSO NEGADO"));
                nh.handleNavigation(facesContext, null, "erro");
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
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
