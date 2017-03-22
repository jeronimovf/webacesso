package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SISTEMA",schema = Constantes.SCHEMA)
@SequenceGenerator(name = "ID", sequenceName = "SEQ_SISTEMA", allocationSize = 1)
@Getter
@Setter
public class Sistema extends EntidadeGenericaComId {

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "SIGLA")
    private String sigla;

    @Column(name = "URL")
    private String url;

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

    public Sistema(final Long id) {
        this();
        setId(id);
    }

    public Sistema() {
    }

    

    @Override
    public String getNomeNatural() {
        return "Sistema";
    }
}
