package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Sistema;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class SistemaFacade extends AbstractFacadeComId<Sistema> {

    public SistemaFacade() {
        super(Sistema.class);
    }

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public List<Sistema> listarSistema() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Sistema> c = cq.from(Sistema.class);
        cq.select(c).where(
                cb.lessThan(c.get("dataCadastro"), cb.currentDate()),
                cb.or(
                        cb.isNull((c.get("dataExcluido"))),
                        cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
                )
        ).orderBy(cb.asc(c.get("descricao"))).distinct(true);

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Sistema> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
