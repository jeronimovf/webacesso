//
// This file was generated by the JPA Modeler
//
package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = Constantes.SCHEMA)
public class Papel extends EntidadeGenerica<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(
            schema = Constantes.SCHEMA, sequenceName = "papel_seq", name = "id"
    )
    protected Long id;
    
    @ManyToMany(targetEntity = Flow.class, mappedBy = "papeis", cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Flow> flows;

    @ManyToOne(targetEntity = Sistema.class)
    private Sistema sistema;

    @Column(unique = true, nullable = false)
    @Basic
    private String nome;

    @OneToMany(targetEntity = PapelRecurso.class, mappedBy = "papel", cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<PapelRecurso> papelRecursos;

    @ManyToMany(targetEntity = Usuario.class, cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Usuario> usuarios;

    public Papel() {
        flows = new HashSet();
        papelRecursos = new HashSet();
        usuarios = new HashSet();
    }
    
    public void addFlows(Flow flow){
        flows.add(flow);
        flow.getPapeis().add(this);
    }

    public void addUsuarios(Usuario usuario){
        usuarios.add(usuario);
        usuario.getPapeis().add(this);
    }
    
    public void addPapelRecursos(PapelRecurso papelRecurso){
        papelRecursos.add(papelRecurso);
        papelRecurso.setPapel(this);
    }    
    
    @Override
    public String getNomeNatural() {
        return "Papel";
    }
}
