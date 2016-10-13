package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacade;
import br.jus.trt23.webacesso.entities.Unidade;
import br.jus.trt23.webacesso.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class UsuarioIntegracaoFacade extends AbstractFacade<Usuario>{

    public UsuarioIntegracaoFacade() {
        super(Usuario.class);
    }

    public Usuario buscarUsuario(final String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c).where(
                cb.equal(cb.lower(c.get("login")), login.toLowerCase())
        );

        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuarioUnidade(final Unidade unidade) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c).where(
                cb.equal(c.get("unidade"), unidade)
        );

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuarioLike(final String query) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c).where(
                cb.or(
                        cb.like(cb.lower(c.get("login")), "%" + query.toLowerCase() + "%"),
                        cb.like(cb.lower(c.get("nome")), "%" + query.toLowerCase() + "%")
                )
        ).orderBy(cb.asc(c.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuarioServidorLike(final String query) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c).where(
                cb.or(
                        cb.like(cb.lower(c.get("login")), "%" + query.toLowerCase() + "%"),
                        cb.like(cb.lower(c.get("nome")), "%" + query.toLowerCase() + "%")
                ),
                cb.isNotNull(c.get("tipoServidor"))
        ).orderBy(cb.asc(c.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuarioJuizLike(final String query) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c).where(
                cb.or(
                        cb.like(cb.lower(c.get("login")), "%" + query.toLowerCase() + "%"),
                        cb.like(cb.lower(c.get("nome")), "%" + query.toLowerCase() + "%")
                ),
                cb.isNotNull(c.get("tipoMagistrado"))
        ).orderBy(cb.asc(c.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Usuario> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> c = cq.from(Usuario.class);
        cq.select(c);

        return getEntityManager().createQuery(cq).getResultList();
        
    }

    @Override
    public List<Usuario> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
