package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VIEWID",schema = Constantes.SCHEMA)
@SequenceGenerator(name = "ID", sequenceName = "SEQ_VIEWID", allocationSize = 1,schema=Constantes.SCHEMA)
@Getter
@Setter
@RequiredArgsConstructor
public class ViewId extends EntidadeGenericaComId {

    @ManyToOne
    private Flow flow;

    private String url;
    
    private String shortName;

    @Override
    public String getNomeNatural() {
        return "ViewId";
    }

    @Override
    public String toString() {
        return getShortName();
    }
    
    
}
