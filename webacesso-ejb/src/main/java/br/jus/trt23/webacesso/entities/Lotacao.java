//
// This file was generated by the JPA Modeler
//
package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

@Entity
@Getter
@Setter
@Table(schema = Constantes.SCHEMA,name = "VW_UNIDADE")
public class Lotacao extends EntidadeGenerica<Long>{   
    @Id
    private Long id;
    private String nome;
    private String sigla;
    @Email
    private String email;
    @OneToMany(mappedBy = "lotacao")
    private Set<Usuario> usuarios;
    @ManyToOne
    private Lotacao lotacaoPai;
    @OneToMany(mappedBy = "lotacaoPai")
    private Set<Lotacao> lotacoesSubordinadas;

    public Lotacao() {
        usuarios = new HashSet();
        lotacoesSubordinadas = new HashSet();        
    }
    
    @Override
    public String getNomeNatural() {
        return "Lotação";
    }
}