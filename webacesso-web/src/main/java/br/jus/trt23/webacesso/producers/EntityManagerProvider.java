package br.jus.trt23.webacesso.producers;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author j129-9
 */
@ApplicationScoped
public class EntityManagerProvider implements Serializable {
    @PersistenceUnit(unitName = "webAcessoPU" )
    private EntityManagerFactory entityManagerQCFactory;
    
    @Produces
    @Default
    private EntityManager createEntityManager(){
        return entityManagerQCFactory.createEntityManager();
    }    
    
    public void closeEntityManager(@Disposes EntityManager manager) {
        if (manager.isOpen()) {
            manager.close();
        }
    }    
}
