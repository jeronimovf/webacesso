package br.jus.trt23.webacesso.converters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.jus.trt23.nucleo.jsf.util.JsfUtil;
import br.jus.trt23.webacesso.entities.Acesso;
import br.jus.trt23.webacesso.sessions.AcessoIntegracaoFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author j129-9
 */

@Named
@Dependent
@FacesConverter(forClass = Acesso.class)
public class AcessoConverter implements Converter {

    @Inject
    private AcessoIntegracaoFacade facade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {        
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        Acesso acesso = this.facade.find(getKey(value));
        return acesso;
    }

    java.lang.Long getKey(String value) {
        java.lang.Long key;
        key = Long.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Long value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Acesso) {
            Acesso o = (Acesso) object;
            return getStringKey(o.getId());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acesso.class.getName()});
            return null;
        }
    }
}
