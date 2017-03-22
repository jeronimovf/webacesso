package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Acesso;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class AcessoIntegracaoFacade extends AbstractFacadeComId<Acesso> {
    public AcessoIntegracaoFacade() {
        super(Acesso.class);
    }

    @Override
    public Acesso find(Object id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Acesso> cq = cb.createQuery(Acesso.class);
        Root<Acesso> c = cq.from(Acesso.class);
        cq.select(c).where(cb.equal(c.get("id"), id));

        return getEntityManager().createQuery(cq).getSingleResult();
    }
    
    

    @SuppressWarnings("unchecked")
    public List<Acesso> listarAcessos(final Sistema sistema, final Usuario usuario) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Acesso> c = cq.from(Acesso.class);
        List<Predicate> predicates = new ArrayList<>();
        if (sistema != null && sistema.getId() != null) {
            predicates.add(cb.equal(c.get("sistema"), sistema));
        }
        if (usuario != null && usuario.getLogin() != null) {
            predicates.add(cb.equal(c.get("usuario"), usuario));
        }
        cq.select(c).where(predicates.toArray(new Predicate[]{}));

        return getEntityManager().createQuery(cq).getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<Acesso> listarAcessos(final Sistema sistema, final List<Usuario> listaUsuario) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Acesso> c = cq.from(Acesso.class);
        List<Predicate> predicates = new ArrayList<>();
        if (sistema != null && sistema.getId() != null) {
            predicates.add(cb.equal(c.get("sistema"), sistema));
        }
        if (listaUsuario != null && !listaUsuario.isEmpty()) {
            predicates.add(cb.upper(c.get("usuario")).in(listaUsuario));
        }
        cq.select(c).where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Acesso> listarCalculista(Usuario usuario) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Acesso> c = cq.from(Acesso.class);
        Join<Acesso, Perfil> p = c.join("perfil");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(p.<Long>get("id"), 28L));
        if (null != usuario) {
            predicates.add(cb.equal(c.get("usuario"), usuario));
        }
        cq.select(c).where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));

        return getEntityManager().createQuery(cq).getResultList();
    }


    @Override
    public List<Acesso> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
