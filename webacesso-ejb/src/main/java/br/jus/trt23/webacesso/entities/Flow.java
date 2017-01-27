package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FLOW",schema = Constantes.SCHEMA)
@SequenceGenerator(name = "ID", sequenceName = "SEQ_FLOW", allocationSize = 1,schema=Constantes.SCHEMA)
@Getter
@Setter
@RequiredArgsConstructor
public class Flow extends EntidadeGenericaComId {
    private String flowId;

    private Integer sequencia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_EXCLUIDO")
    private Date dataExcluido;

    @Column(name = "USUARIO_CADASTRO")
    private String usuarioCadastro;

    @Column(name = "USUARIO_EXCLUIR")
    private String usuarioExcluir;

    @OneToMany(mappedBy = "flow")
    public Set<ViewId> viewIds;
    
    @ManyToMany(mappedBy = "flows")
    public Set<Funcionalidade> funcionalidades;

    @Override
    public String getNomeNatural() {
        return "Flow";
    }

    @Override
    public String toString() {
        return getFlowId();
    }
    
    
}
