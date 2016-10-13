package br.jus.trt23.webacesso.sessions;

import br.jus.trt23.nucleo.session.AbstractFacadeComId;
import br.jus.trt23.webacesso.entities.TipoUsuario;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class TipoUsuarioFacade extends AbstractFacadeComId<TipoUsuario> 
{
    public TipoUsuarioFacade() {
        super(TipoUsuario.class);
    }

    @Override
    public List<TipoUsuario> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}