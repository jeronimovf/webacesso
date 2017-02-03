package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.controllers.AbstractController;
import br.jus.trt23.webacesso.entities.Flow;
import br.jus.trt23.webacesso.entities.Papel;
import br.jus.trt23.webacesso.sessions.FlowFacade;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named(value = "flowWeba2Controller")
@Dependent
@Getter
@Setter
public class FlowController extends AbstractController<Flow> {
    public FlowController() {
        super(Flow.class);
    }

    @Override
    protected String getMessagePrefix() {
        return "Flow";
    }
    
    public Set<Flow> flowsParaOPapelDiferenca(Papel papel) {
        FlowFacade facade = (FlowFacade) getFacade();
        return facade.flowsParaOPapelDiferenca(papel);
    }    
}
