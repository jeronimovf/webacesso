package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Permissao;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class PermissaoFacade extends AbstractFacadeComId<Permissao> {
    public PermissaoFacade() {
        super(Permissao.class);
    }

    @SuppressWarnings("unchecked")
    public List<Permissao> listarPermissao(final Sistema sistema, final Perfil perfil, final Usuario usuario, final Unidade unidade) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Permissao> c = cq.from(Permissao.class);
        Join<Permissao, Unidade> un = c.join("unidade", JoinType.LEFT);
        Join<Permissao, Perfil> p = c.join("perfil", JoinType.LEFT);
        Join<Perfil, Sistema> s = p.join("sistema", JoinType.LEFT);
        Join<Permissao, Usuario> u = c.join("usuario", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (null != perfil) {
            predicates.add(cb.equal(c.get("perfil"), perfil));
        }

        if (null != usuario) {
            predicates.add(cb.equal(c.get("usuario"), perfil));
        }

        if (null != sistema) {
            predicates.add(cb.equal(p.get("sistema"), perfil));
        }

        if (null != unidade) {
            predicates.add(cb.equal(c.get("unidade"), perfil));
        }
        predicates.add(cb.lessThan(c.get("dataCadastro"), cb.currentDate()));
        predicates.add(cb.or(
                cb.isNull((c.get("dataExcluido"))),
                cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
        ));

        cq.select(c).where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(cb.asc(u.get("nome")), cb.asc(s.get("descricao")), cb.asc(p.get("descricao")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Permissao> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
