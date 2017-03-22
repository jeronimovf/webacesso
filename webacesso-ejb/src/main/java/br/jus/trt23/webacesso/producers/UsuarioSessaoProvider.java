/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.webacesso.producers;

import br.jus.trt23.webacesso.util.UsuarioSessao;
import java.io.Serializable;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author j129-9
 */
public class UsuarioSessaoProvider implements Serializable {
    @ManagedProperty(value="#{usuarioSessao}")
    @Getter
    @Setter
    private UsuarioSessao usuarioSessao;

    @Produces
    @Default
    public UsuarioSessao createUsuarioSessao() {
        if (usuarioSessao == null) {
            usuarioSessao = new UsuarioSessao();
        }
        return usuarioSessao;
    }

}
