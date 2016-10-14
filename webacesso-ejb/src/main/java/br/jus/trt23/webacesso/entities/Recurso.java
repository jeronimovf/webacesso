package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RECURSO", schema = Constantes.SCHEMA)
@SequenceGenerator(name = "ID", sequenceName = "SEQ_RECURSO", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class Recurso extends EntidadeGenericaComId {

    @JoinColumn(name = "FK_FUNCIONALIDADE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Funcionalidade funcionalidade;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(schema = Constantes.SCHEMA, name = "PERFIL_RECURSO", joinColumns = {
        @JoinColumn(name = "FK_RECURSO")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PERFIL")})
    public Perfil perfil;

    @Override
    public String getNomeNatural() {
        return "Recurso";
    }

}
