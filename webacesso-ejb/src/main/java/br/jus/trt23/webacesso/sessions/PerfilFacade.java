package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Sistema;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class PerfilFacade extends AbstractFacadeComId<Perfil> {
    public PerfilFacade() {
        super(Perfil.class);
    }


    @SuppressWarnings("unchecked")
    public List<Perfil> listarPerfil(final Sistema sistema) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Perfil> c = cq.from(Perfil.class);
        List<Predicate> predicates = new ArrayList<>();

        if (null != sistema) {
            predicates.add(cb.equal(c.get("sistema"), sistema));
        }
        predicates.add(cb.lessThan(c.get("dataCadastro"), cb.currentDate()));
        predicates.add(            cb.or(
                    cb.isNull((c.get("dataExcluido"))),
                    cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
            ));
        cq.select(c).where(
            cb.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(cb.asc(c.get("descricao"))).distinct(true);
        
        return getEntityManager().createQuery(cq).getResultList();        
    }

    @Override
    public List<Perfil> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
