package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.nucleo.util.Util;
import br.jus.trt23.webacesso.entities.DelegarGestor;
import br.jus.trt23.webacesso.entities.Sistema;
import br.jus.trt23.webacesso.entities.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class DelegarGestorFacade extends AbstractFacadeComId<DelegarGestor> {

    public DelegarGestorFacade() {
        super(DelegarGestor.class);
    }


    @SuppressWarnings("unchecked")
    public List<DelegarGestor> listarDelagacoes(final Sistema sistema, final Usuario usuarioGestor, final Usuario usuarioDelegado, final Date data) {
        Date dataReferencia = null;
        if (data == null) {
            //TODO:a data capturada deve ser a do servidor
            dataReferencia = new Date();
        } else {
            dataReferencia = data;
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DelegarGestor> c = cq.from(DelegarGestor.class);
        Join<DelegarGestor, Usuario> g = c.join("usuarioGestor");
        Join<DelegarGestor, Usuario> d = c.join("usuarioDelegado");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(c.get("sistema"), sistema));

        if (usuarioGestor != null && usuarioGestor.getLogin() != null) {
            predicates.add(cb.equal(c.get("usuarioGestor"), usuarioGestor));
        }
        // Delegado
        if (usuarioDelegado != null && usuarioDelegado.getLogin() != null) {
            predicates.add(cb.equal(c.get("usuarioDelegado"), usuarioDelegado));
        }
        // Cadastro/Exclusão
        predicates.add(cb.lessThan(c.get("dataCadastro"), dataReferencia));
        predicates.add(cb.or(
                cb.isNull(c.get("dataExcluido")),
                cb.greaterThan(c.get("dataExcluido"), dataReferencia)
        ));

        cq.select(c).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        cq.orderBy(cb.asc(g.get("nome")), cb.asc(d.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Verifica se para o período passado por parâmetro, o delegado já está como
     * delegado de algum outro gestor.
     *
     * @param sistema
     * @param usuarioDelegado
     * @param dataInicio
     * @param dataFim
     * @return DelegarGestor
     */
    public DelegarGestor buscarDelegadoGestor(final Sistema sistema, final Usuario usuarioDelegado, final Date dataInicio, final Date dataFim) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DelegarGestor> cq = cb.createQuery(DelegarGestor.class);
        Root<DelegarGestor> c = cq.from(DelegarGestor.class);
        Join<DelegarGestor, Usuario> g = c.join("gestor");

        Date dataFimCheck = (Date)dataFim.clone();

        List<Predicate> predicates = new ArrayList<>();

        if (dataFimCheck == null) {
            //TODO:a data capturada deve ser a do servidor a da
            Date dataAtual = Util.dataUltimoMinutoSegundo(java.sql.Date.valueOf(getDateOnServer()));
            if (dataAtual.after(dataInicio)) {
                dataFimCheck = dataAtual;
            } else {
                dataFimCheck = dataInicio;
            }
        }

        predicates.add(cb.equal(c.get("sistema"), sistema));
        predicates.add(cb.equal(c.get("usuarioDelegado"), usuarioDelegado));

        predicates.add(
            cb.or(
                cb.between(c.get("dataCadastro"), dataInicio, dataFimCheck),
                cb.between(c.get("dataExcluido"), dataInicio, dataFimCheck),
                cb.between(
                    c.<Date>get("dataInicio"), c.<Date>get("dataCadastro"), cb.<Date>selectCase().
                        when(cb.isNull(c.get("dataExcluido")),
                            cb.currentDate()
                        ).
                        otherwise(c.get("dataExcluido"))
                ),
                cb.between(
                    c.<Date>get("dataFim"), c.<Date>get("dataCadastro"), cb.<Date>selectCase().
                        when(cb.isNull(c.get("dataExcluido")),
                            cb.currentDate()
                        ).
                        otherwise(c.get("dataExcluido"))
                )                
            )
        );

        cq.select(c).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<DelegarGestor> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
