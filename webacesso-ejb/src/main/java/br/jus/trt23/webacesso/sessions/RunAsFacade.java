/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.sessions.AbstractFacade;
import br.jus.trt23.webacesso.entities.RunAs;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author j129-9
 */
@Stateless
public class RunAsFacade extends AbstractFacade<RunAs> {
    
    @Inject
    private EntityManager em;

    public RunAsFacade() {
        super(RunAs.class);
    }    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<RunAs> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
