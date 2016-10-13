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
@Table(name="USUARIO_EXTERNO",schema = Constantes.SCHEMA)
@SequenceGenerator(name="ID", sequenceName = "SEQ_USUARIO_EXTERNO", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class UsuarioExterno extends EntidadeGenericaComId
{
	@JoinColumn(name="FK_TIPO_USUARIO")
	@ManyToOne(fetch=FetchType.LAZY)
	private TipoUsuario tipoUsuario;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="MATRICULA")
	private String matricula;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CPF")
	private String cpf;
	
	@Column(name="EMAIL")
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;
	
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
        return "Usuário exerno";
    }
}