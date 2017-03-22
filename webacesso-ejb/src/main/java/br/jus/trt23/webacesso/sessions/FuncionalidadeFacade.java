package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.Funcionalidade;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import br.jus.trt23.webacesso.entities.Perfil;
import br.jus.trt23.webacesso.entities.Sistema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class FuncionalidadeFacade extends AbstractFacadeComId<Funcionalidade> {
    public FuncionalidadeFacade() {
        super(Funcionalidade.class);
    }

    @SuppressWarnings("unchecked")
    public List<Funcionalidade> listarFuncionalidade(final Sistema sistema) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Funcionalidade> c = cq.from(Funcionalidade.class);
        Join<Funcionalidade, FuncionalidadeUrl> u = c.join("listaUrl");
        Join<Funcionalidade, Sistema> s = c.join("sistema");
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(null != sistema){
            predicates.add(cb.equal(c.get("sistema"), sistema));
        }
        predicates.add(cb.lessThan(c.<Date>get("dataCadastro"), cb.currentDate()));
        predicates.add(
            cb.or(
                    cb.isNull((c.get("dataExcluido"))),
                    cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
            )
        );
        
        cq.select(c).where(cb.and(predicates.toArray(
                new Predicate[predicates.size()]))
        ).orderBy(cb.asc(c.get("descricao"))).distinct(true);
        
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Funcionalidade> listarFuncionalidadeAcesso(final Perfil perfil) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Funcionalidade> c = cq.from(Funcionalidade.class);
        Join<Funcionalidade, Perfil> p = c.join("listaPerfil", JoinType.LEFT);
        cq.select(c).where(
            cb.equal(p, perfil),
            cb.lessThan(c.get("dataCadastro"), cb.currentDate()),
            cb.or(
                    cb.isNull((c.get("dataExcluido"))),
                    cb.greaterThan(c.get("dataExcluido"), cb.currentDate())
            )
        ).orderBy(cb.asc(c.get("descricao"))).distinct(true);
        
        return getEntityManager().createQuery(cq).getResultList();        
    }


    @Override
    public List<Funcionalidade> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
