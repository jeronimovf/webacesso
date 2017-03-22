package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.webacesso.constants.Constantes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TIPO_USUARIO",schema = Constantes.SCHEMA)
@Getter
@Setter
@RequiredArgsConstructor
public class TipoUsuario extends EntidadeGenericaComId
{
	@Column(name="DESCRICAO")
	private String descricao;


    @Override
    public String getNomeNatural() {
        return "Tipo usuário";
    }
}