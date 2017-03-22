package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.constants.Constantes;
import br.jus.trt23.webacesso.enums.TipoMagistrado;
import br.jus.trt23.webacesso.enums.TipoServidor;
import br.jus.trt23.webacesso.enums.TipoUsuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="VW_USUARIO",schema = Constantes.SCHEMA)
@Getter
@Setter
@RequiredArgsConstructor
public class Usuario extends EntidadeGenerica
{
	public static final String PUBLICO = "PUBLICO";
	public static final String CHEFIA = "CHEFIA";	
	public static final String SERVIDOR_VARA = "VARA";
	public static final String SERVIDOR_GABINETE = "GABINETE";
	public static final String JUIZ_SUBSTITUTO = "JUIZ_SUBSTITUTO";
	public static final String JUIZ_TITULAR = "JUIZ_TITULAR";	
	public static final String DESEMBARGADOR = "DESEMBARGADOR";
	public static final String GESTOR = "GESTOR";
	public static final String SERVIDOR = "SERVIDOR";

        @Id
        @Column(name="LOGIN")
	private String login;
	
	@Column(name="MATRICULA")
	private String matricula;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CPF")
	private String cpf;
	
	@Column(name="MATRICULA_TRT")
	private String matriculaCompleta;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="EMAIL_EXTERNO")
	private String emailExterno;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;
	
	@JoinColumn(name="ID_UNIDADE", referencedColumnName="ID_UNIDADE")
	@ManyToOne
	private Unidade unidade;
	
	@Column(name="COD_COMISSIONADO")
	private String siglaCargoComissionado;
	
	@Column(name="NOME_COM")
	private String nomeCargoComissionado;
	
	@Column(name="NUM_VAGA_COM")
	private Long numeroCargoComissionado;
	
	@Column(name="TITULAR_COM")
	private Long titularUnidadeCargoComissionado;
	
	@Column(name="TIPO_USUARIO")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@Column(name="TIPO_MAGISTRADO")
	@Enumerated(EnumType.STRING)
	private TipoMagistrado tipoMagistrado;
	
	@Column(name="TIPO_SERVIDOR")
	@Enumerated(EnumType.STRING)
	private TipoServidor tipoServidor;
	
	@Column(name="GESTOR")
	@Enumerated(EnumType.STRING)
	private TipoSimNao gestor;
	
	@Column(name="SITUACAO_FUNCIONAL")
	private String descricaoSituacaoFuncional;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DESLIGAMENTO")
	private Date dataDesligamento;
	
	public Usuario(final String login)
	{
		this();
		this.login = login;
	}
	
	public Usuario(final String matricula, final String login, final String nome)
	{
		this();
		this.matricula = matricula;
		this.login = login;
		this.nome = nome;
	}
	
	// Campo que define titular da unidade o TITULAR_COM da tabela SRH2.QFC_OCUP_COM
	public boolean isTitularUnidadeCargoComissionado()
	{
		return this.getTitularUnidadeCargoComissionado() != null && this.getTitularUnidadeCargoComissionado().equals(new Long(1));
	}
	
	public String getNomeLogin()
	{
		return (login != null) ? nome + " [" + login + "]": nome;
	}

	
	public String getLoginNome()
	{
		return (login != null) ? login + " - " + nome : nome;
	}
	

    @Override
    public String getNomeNatural() {
        return "Usuario";
    }

    @Override
    public Serializable getId() {
        return getLogin().toUpperCase();
    }
}