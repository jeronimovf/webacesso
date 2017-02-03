package br.jus.trt23.webacesso.flows;

import br.jus.trt23.nucleo.flows.AbstractFlow;
import br.jus.trt23.webacesso.controllers.SistemaController;
import br.jus.trt23.webacesso.entities.Sistema;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
@FlowScoped("sistema")
public class SistemaFlow extends AbstractFlow<Sistema>{
    @Override
    public SistemaController getController(){
        if(controller instanceof SistemaController){
            return (SistemaController) controller;
        }
        return null;
    }
    
    
}
