package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.UsuarioSistema;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class UsuarioSistemaFacade extends AbstractFacadeComId<UsuarioSistema> {
    public UsuarioSistemaFacade() {
        super(UsuarioSistema.class);
    }

    @Override
    public List<UsuarioSistema> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
