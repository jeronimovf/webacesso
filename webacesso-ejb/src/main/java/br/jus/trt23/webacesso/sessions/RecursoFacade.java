package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Recurso;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class RecursoFacade extends AbstractFacadeComId<Recurso> {
    public RecursoFacade() {
        super(Recurso.class);
    }

    @SuppressWarnings("unchecked")
    public List<Recurso> listarRecursos(final Funcionalidade funcionalidade) {
        if (funcionalidade != null) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<Recurso> c = cq.from(Recurso.class);
            cq.select(c).where(
                    cb.equal(c.get("funcionalidade"), funcionalidade)
            );

            return getEntityManager().createQuery(cq).getResultList();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Recurso> listarRecursos(final Perfil perfil, final Funcionalidade funcionalidade) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Recurso> c = cq.from(Recurso.class);

        if (perfil != null) {
            predicates.add(cb.equal(c.get("perfil"), perfil));
        }
        if (funcionalidade != null) {
            predicates.add(cb.equal(c.get("funcionalidade"), funcionalidade));
        }
        cq.select(c).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recurso> listarRecursos(final List<Funcionalidade> listaFuncionalidade) {
        if (listaFuncionalidade != null && !listaFuncionalidade.isEmpty()) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<Recurso> c = cq.from(Recurso.class);
            Join<Recurso,Funcionalidade> f = c.join("funcionalidade");
            cq.select(c).where(c.get("funcionalidade").in(listaFuncionalidade));

            return getEntityManager().createQuery(cq).getResultList();            
        }
        return null;
    }

    @Override
    public List<Recurso> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
