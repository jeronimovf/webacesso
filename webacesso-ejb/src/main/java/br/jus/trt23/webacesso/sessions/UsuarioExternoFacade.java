package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.UsuarioExterno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class UsuarioExternoFacade extends AbstractFacadeComId<UsuarioExterno> {
    public UsuarioExternoFacade() {
        super(UsuarioExterno.class);
    }

    @SuppressWarnings("unchecked")
    public List<UsuarioExterno> listarUsuario() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UsuarioExterno> cq = cb.createQuery(UsuarioExterno.class);
        Root<UsuarioExterno> c = cq.from(UsuarioExterno.class);
        cq.select(c).where(
                cb.lessThan(c.get("dataCadastro"), cb.currentDate()),
                cb.or(
                        cb.isNull((c.get("dataExcluido"))),
                        cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
                )
        ).orderBy(cb.asc(c.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<UsuarioExterno> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
