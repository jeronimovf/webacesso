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
@Table(name="PERMISSAO",schema = Constantes.SCHEMA)
@SequenceGenerator(name="ID", sequenceName = "SEQ_PERMISSAO", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class Permissao extends EntidadeGenericaComId
{

	@JoinColumn(name="LOGIN")
	@ManyToOne(fetch=FetchType.LAZY)
	private Usuario usuario;
	
	@JoinColumn(name="FK_PERFIL")
	@ManyToOne(fetch=FetchType.LAZY)
	private Perfil perfil;
	
	@JoinColumn(name="FK_UNIDADE")
	@ManyToOne(fetch=FetchType.LAZY)
	private Unidade unidade;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_EXCLUIDO")
	private Date dataExcluido;
	
	@Column(name="USUARIO_CADASTRO")
	private String usuarioCadastro;
	
	@Column(name="USUARIO_EXCLUIR")
	private String usuarioExcluir;

    @Override
    public String getNomeNatural() {
        return "Permissão";
    }
}