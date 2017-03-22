package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "br.jus.trt23.webacesso.entity.integracao.Unidade")
@Table(name = "VW_UNIDADE", schema = Constantes.SCHEMA)
@Getter
@Setter
@AttributeOverride(column = @Column(name = "ID_UNIDADE"), name = "id")
public class Unidade extends EntidadeGenericaComId {

    @Column(name = "ID_UNIDADE_SUPERIOR")
    private Long idSuperior;

    @Column(name = "SIGLA")
    private String sigla;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "EMAIL")
    private String email;

    public Unidade() {
    }

    public Unidade(final Long codigoUnidade) {
        this();
        this.id = codigoUnidade;
    }

    @Override
    public String getNomeNatural() {
        return "Unidade";
    }
}
