package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.constants.Constantes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="FUNCIONALIDADE_URL",schema = Constantes.SCHEMA)
@SequenceGenerator(name="ID",sequenceName = "SEQ_FUNCIONALIDADE_URL", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class FuncionalidadeUrl extends EntidadeGenericaComId
{

	@JoinColumn(name="FK_FUNCIONALIDADE")
	@ManyToOne
	private Funcionalidade funcionalidade;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PRINCIPAL_URL")
	private TipoSimNao principalUrl;
	
	@Column(name="VIEW_ID")
	private String viewId;
	
	@Column(name="URL")
	private String url;

    @Override
    public String getNomeNatural() {
        return "Funcionalidade URL"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return getViewId().concat(" principal[" + getPrincipalUrl().getDescricao() + "]"); //To change body of generated methods, choose Tools | Templates.
    }
    
    

	
}