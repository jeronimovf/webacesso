package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PERFIL",schema = Constantes.SCHEMA)
@SequenceGenerator(name="ID", sequenceName = "SEQ_PERFIL", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class Perfil extends EntidadeGenericaComId
{

	@JoinColumn(name="FK_SISTEMA")
	@ManyToOne
	private Sistema sistema;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="SIGLA")
	private String sigla;
	
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
	
	@ManyToMany(cascade = CascadeType.MERGE )
	@JoinTable(schema = Constantes.SCHEMA, name = "PERFIL_FUNCIONALIDADE", joinColumns = {@JoinColumn(name = "FK_PERFIL") }, inverseJoinColumns = { @JoinColumn(name = "FK_FUNCIONALIDADE") })
	public Set<Funcionalidade> listaFuncionalidade; 
	
	@ManyToMany(cascade = CascadeType.MERGE )
	@JoinTable(schema = Constantes.SCHEMA,name = "PERFIL_RECURSO", joinColumns = {@JoinColumn(name = "FK_PERFIL") }, inverseJoinColumns = { @JoinColumn(name = "FK_RECURSO") })
	public Set<Recurso> listaRecurso;

    @Override
    public String getNomeNatural() {
        return "Perfil";
    }

}