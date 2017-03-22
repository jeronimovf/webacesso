package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Unidade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class UnidadeIntegracaoFacade extends AbstractFacadeComId<Unidade> {
    public UnidadeIntegracaoFacade() {
        super(Unidade.class);
    }

    @SuppressWarnings("unchecked")
    public List<Unidade> listarUnidade() {
        Map<String, String> orderBy = new HashMap<>();
        orderBy.put("descricao", "ASC");
        return findAll(orderBy);
    }

    @SuppressWarnings("unchecked")
    public List<Unidade> listarUnidadeLike(final String query) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Unidade> c = cq.from(Unidade.class);
        cq.select(c).where(
                cb.like(cb.lower(c.get("descricao")), query.toLowerCase())).
                orderBy(cb.asc(c.get("descricao")));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Unidade> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
