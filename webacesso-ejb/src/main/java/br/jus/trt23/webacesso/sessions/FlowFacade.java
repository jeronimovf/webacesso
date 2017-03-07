/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.sessions.AbstractFacade;
import br.jus.trt23.webacesso.entities.Flow;
import br.jus.trt23.webacesso.entities.Papel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author j129-9
 */
@Stateless
public class FlowFacade extends AbstractFacade<Flow> {

    @Inject
    private EntityManager em;

    public FlowFacade() {
        super(Flow.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Flow> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //retorna os flows que ainda não foram associados ao papel
    public Set<Flow> flowsParaOPapelDiferenca(Papel papel) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Flow> cq = cb.createQuery(Flow.class);
        Root<Flow> c = cq.from(Flow.class);
        cq.select(c).where(
                cb.and(
                        cb.isEmpty(c.get("papeis")),
                        cb.equal(c.get("sistema"), papel.getSistema())
                )
        );

        Set<Flow> flows = new HashSet(getEntityManager().createQuery(cq).getResultList());
        return flows;
    }

}
