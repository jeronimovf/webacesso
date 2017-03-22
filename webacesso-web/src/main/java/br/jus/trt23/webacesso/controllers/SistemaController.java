package br.jus.trt23.webacesso.controllers;

import br.jus.trt23.nucleo.controllers.AbstractController;
import br.jus.trt23.nucleo.handlers.CollectionExchanger;
import br.jus.trt23.nucleo.handlers.Jsf;
import br.jus.trt23.webacesso.entities.Flow;
import br.jus.trt23.webacesso.entities.Papel;
import br.jus.trt23.webacesso.entities.Recurso;
import br.jus.trt23.webacesso.entities.Sistema;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.datatable.DataTable;

@Named
@Dependent
public class SistemaController extends AbstractController<Sistema> {

    @Getter
    private CollectionExchanger<Set> ceFlow;

    @Inject
    private PapelController papelController;

    @Inject
    private FlowController flowWeba2Controller;

    @Inject
    private RecursoController recursoController;

    @Getter
    @Setter
    private Papel papelNovo;

    @Getter
    @Setter
    private Flow flowNovo;

    @Getter
    @Setter
    private Flow papelFlowNovo;

    @Getter
    @Setter
    private Recurso recursoNovo;

    public SistemaController() {
        super(Sistema.class);
    }

    @Override
    protected String getMessagePrefix() {
        return "Sistema";
    }

    public String preparePapelEdit() {
        Object obj = Jsf.findComponent("papelDT");
        if (obj instanceof DataTable) {
            DataTable dt = (DataTable) obj;
            setPapelNovo((Papel) dt.getRowData());
            ceFlow = new CollectionExchanger<>(
                    flowWeba2Controller.flowsParaOPapelDiferenca(papelNovo),
                    new HashSet(papelNovo.getFlows()));

        }
        return "PapelEdit";
    }

    public String preparePapelFlowEdit() {
        Object obj = Jsf.findComponent("papelFlowDT");
        if (obj instanceof DataTable) {
            DataTable dt = (DataTable) obj;
            setPapelFlowNovo((Flow) dt.getRowData());
        }
        return "PapelFlowEdit";
    }

    public String prepareFlowEdit() {
        Object obj = Jsf.findComponent("flowDT");
        if (obj instanceof DataTable) {
            DataTable dt = (DataTable) obj;
            setFlowNovo((Flow) dt.getRowData());
        }
        return "FlowEdit";
    }

    public String prepareRecursoEdit() {
        Object obj = Jsf.findComponent("recursoDT");
        if (obj instanceof DataTable) {
            DataTable dt = (DataTable) obj;
            setRecursoNovo((Recurso) dt.getRowData());
        }
        return "RecursoEdit";
    }

    public String preparePapelNovo() {
        setPapelNovo(new Papel());
        return "PapelNovo";
    }

    public String prepareFlowNovo() {
        setFlowNovo(new Flow());
        return "FlowNovo";
    }

    public String preparePapelFlowNovo() {
        setPapelFlowNovo(new Flow());
        return "PapelFlowNovo";
    }

    public String prepareRecursoNovo() {
        setRecursoNovo(new Recurso());
        return "RecursoNovo";
    }

    public String saveOrCreatePapel() {
        String msg;
        try {
            selected.addPapeis(papelNovo);
            saveOrCreate();
            msg = papelController.getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "Edit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String saveOrCreateFlow() {
        String msg;
        try {
            selected.addFlows(flowNovo);
            saveOrCreate();
            msg = flowWeba2Controller.getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "Edit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String saveOrCreateRecurso() {
        String msg;
        try {
            selected.addRecursos(recursoNovo);
            saveOrCreate();
            msg = recursoController.getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "Edit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String saveOrCreatePapelFlow() {
        String msg;
        try {
            papelNovo.addFlows(papelFlowNovo);
            saveOrCreate();
            msg = recursoController.getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "PapelEdit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    @Override
    public String beforeCreate() {
        selected.addPapeis(papelController.administradorNovo(selected));
        return super.beforeCreate();
    }
    
    private void consistePapelFlow(){
        //Set<Flow> destinationDisjoint = ceFlow.getDestinationCollection().
        for(Flow f : (Set<Flow>)ceFlow.getDestinationCollection()){
            f.addPapeis(papelNovo);
        }
    }
}
