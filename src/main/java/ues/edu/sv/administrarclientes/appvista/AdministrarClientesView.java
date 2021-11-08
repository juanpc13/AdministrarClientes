/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.edu.sv.administrarclientes.appvista;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.entidades.Cliente;
import ues.edu.sv.administrarclientes.entidades.Pais;

/**
 *
 * @author Juan
 */
@Named
@RequestScoped
public class AdministrarClientesView {
    
    // Controladores
    @Inject
    private PaisFacade paisFacade;
    
    // Listas de los datos
    private List<Pais> paises;
    
    // Selecciones de la vista
    private Pais selectedPais;
    
    // Cliente temporal
    private Cliente tempCliente;
    
    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        tempCliente = new Cliente();
        
        paises = paisFacade.findAll();
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }

    public Cliente getTempCliente() {
        return tempCliente;
    }

    public void setTempCliente(Cliente tempCliente) {
        this.tempCliente = tempCliente;
    }
    
    
    
}
