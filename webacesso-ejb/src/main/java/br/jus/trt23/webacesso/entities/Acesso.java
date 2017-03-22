package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="VW_ACESSO",schema = Constantes.SCHEMA)
@Getter
@Setter
@RequiredArgsConstructor
public class Acesso extends EntidadeGenericaComId
{
	@Id
	@JoinColumn(name="LOGIN")
	@ManyToOne
	private Usuario usuario;
	
	@Id
	@JoinColumn(name="SISTEMA")
	@ManyToOne
	private Sistema sistema;
	
	@Id
	@JoinColumn(name="PERFIL")
	@ManyToOne
	private Perfil perfil;
	
	@Id
	@JoinColumn(name="UNIDADE")
	@ManyToOne
	private Unidade unidade;
	
	@Transient
	private List<Funcionalidade> listaFuncionalidade;
	
	// Para preencher os dados do usuário delegado
	// Se este campo não for nulo quer dizer que usuário logado possuí delegação de gestor cadastrada
	@Transient
	private Usuario usuarioGestor;

    @Override
    public String getNomeNatural() {
        return "Acesso";
    }
}