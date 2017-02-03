package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.controllers.AbstractController;
import br.jus.trt23.webacesso.entities.Papel;
import br.jus.trt23.webacesso.entities.Sistema;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@Dependent
@Getter
@Setter
public class PapelController extends AbstractController<Papel> {
    public PapelController() {
        super(Papel.class);
    }

    @Override
    protected String getMessagePrefix() {
        return "Papel";
    }

    public Papel administradorNovo(Sistema sistema) {
        Papel administrador = new Papel();
        //TODO: buscar o label do arquivo de internacionalização;
        administrador.setNome("Administrador");
        administrador.setVigenciaIgual(sistema);
        return administrador;
    }
}
