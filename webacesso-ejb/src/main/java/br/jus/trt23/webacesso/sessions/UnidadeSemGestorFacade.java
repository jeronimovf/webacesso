package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.UnidadeSemGestor;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

@Stateless
public class UnidadeSemGestorFacade extends AbstractFacadeComId<UnidadeSemGestor> {
    public UnidadeSemGestorFacade() {
        super(UnidadeSemGestor.class);
    }

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public List<UnidadeSemGestor> listarUnidadeSemGestor() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<UnidadeSemGestor> c = cq.from(UnidadeSemGestor.class);
        Join<UnidadeSemGestor, Unidade> un = c.join("unidade");
        Join<UnidadeSemGestor, Usuario> usr = c.join("usuario");
        cq.select(c).where(
                cb.lessThan(c.get("dataCadastro"), cb.currentDate()),
                cb.or(
                        cb.isNull((c.get("dataExcluido"))),
                        cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
                )
        ).orderBy(cb.asc(un.get("descricao")), cb.asc(usr.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    public UnidadeSemGestor obterUnidadeSemGestor(final Unidade unidade) {
        if (unidade != null) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<UnidadeSemGestor> cq = cb.createQuery(UnidadeSemGestor.class);
            Root<UnidadeSemGestor> c = cq.from(UnidadeSemGestor.class);
            cq.select(c).where(
                    cb.equal(c.get("unidade"), unidade),
                    cb.lessThan(c.get("dataCadastro"), cb.currentDate()),
                    cb.or(
                            cb.isNull((c.get("dataExcluido"))),
                            cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
                    )
            );

            return getEntityManager().createQuery(cq).getSingleResult();
        }
        return null;
    }

    @Override
    public List<UnidadeSemGestor> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
