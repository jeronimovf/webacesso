package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.FuncionalidadeUrl;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class FuncionalidadeUrlFacade extends AbstractFacadeComId<FuncionalidadeUrl> {

    public FuncionalidadeUrlFacade() {
        super(FuncionalidadeUrl.class);
    }

    @Override
    public List<FuncionalidadeUrl> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
