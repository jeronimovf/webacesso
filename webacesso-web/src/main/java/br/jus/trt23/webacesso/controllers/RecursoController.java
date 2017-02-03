package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.controllers.AbstractController;
import br.jus.trt23.webacesso.entities.Recurso;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named()
@Dependent
@Getter
@Setter
public class RecursoController extends AbstractController<Recurso> {
    public RecursoController() {
        super(Recurso.class);
    }

    @Override
    protected String getMessagePrefix() {
        return "Recurso";
    }
}
