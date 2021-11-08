/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.edu.sv.administrarclientes.controlador;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ues.edu.sv.administrarclientes.entidades.Tienda;

/**
 *
 * @author Juan
 */
@Stateless
public class TiendaFacade extends AbstractFacade<Tienda> {

    @PersistenceContext(unitName = "AdministrarClientes_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TiendaFacade() {
        super(Tienda.class);
    }
    
}
