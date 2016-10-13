package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UNIDADE_SEM_GESTOR",schema = Constantes.SCHEMA)
@SequenceGenerator(name="ID", sequenceName = "SEQ_UNIDADE_SEM_GESTOR", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class UnidadeSemGestor extends EntidadeGenericaComId
{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UNIDADE")
	private Unidade unidade;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LOGIN")
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_EXCLUIDO")
	private Date dataExcluido;
	
	@JoinColumn(name="USUARIO_CADASTRO")
	@ManyToOne(fetch = FetchType.LAZY)  
	private Usuario usuarioCadastro;
	
	@JoinColumn(name="USUARIO_EXCLUIR")
	@ManyToOne(fetch = FetchType.LAZY)  
	private Usuario usuarioExcluir;

    @Override
    public String getNomeNatural() {
        return "Unidade sem gestor";
    }
}