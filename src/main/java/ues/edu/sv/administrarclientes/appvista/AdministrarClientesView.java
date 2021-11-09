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
import ues.edu.sv.administrarclientes.controlador.ClienteFacade;
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.controlador.TipoFacade;
import ues.edu.sv.administrarclientes.entidades.Ciudad;
import ues.edu.sv.administrarclientes.entidades.Cliente;
import ues.edu.sv.administrarclientes.entidades.Direccion;
import ues.edu.sv.administrarclientes.entidades.Pais;
import ues.edu.sv.administrarclientes.entidades.Tienda;
import ues.edu.sv.administrarclientes.entidades.Tipo;

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
    @Inject
    private ClienteFacade clienteFacade;
    @Inject
    private TipoFacade tipoFacade;
    
    // Listas de los datos
    private List<Pais> paises;
    private List<Cliente> clientes;
    private List<Tipo> tipos;
    
    // Selecciones de la vista
    private Pais selectedPais;
    private Ciudad selectedCiudad;
    private Direccion selectedDireccion;
    
    // Cliente temporal
    private Cliente tempCliente;
    
    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        clientes = new ArrayList<>();
        tipos = new ArrayList<>();
        clearTempClient();
        
        // Seleciones se instancias
        selectedPais = new Pais();
        selectedCiudad = new Ciudad();
        selectedDireccion = new Direccion();
        
        // Cargar las listas
        paises = paisFacade.findAll();
        clientes = clienteFacade.findAll();
        tipos = tipoFacade.findAll();
    }
    
    public void clearTempClient(){
        tempCliente = new Cliente();
        tempCliente.setTiendaId(new Tienda());
        tempCliente.setDireccionId(new Direccion());
        tempCliente.setTipoList(new ArrayList<Tipo>()); //NO SE CREAR AQUI
    }
    
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public Pais getSelectedPais() {
        return selectedPais;
    }
    

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }

    public Ciudad getSelectedCiudad() {
        return selectedCiudad;
    }

    public void setSelectedCiudad(Ciudad selectedCiudad) {
        this.selectedCiudad = selectedCiudad;
    }

    public Direccion getSelectedDireccion() {
        return selectedDireccion;
    }

    public void setSelectedDireccion(Direccion selectedDireccion) {
        this.selectedDireccion = selectedDireccion;
    }

    public Cliente getTempCliente() {
        return tempCliente;
    }

    public void setTempCliente(Cliente tempCliente) {
        this.tempCliente = tempCliente;
    }
    
    public void onRowSelect(){
        System.out.println(tempCliente.getNombres());
        selectedPais = tempCliente.getDireccionId().getCiudadId().getPaisId();
    }
    
    public void onTipoSelect(){
        System.out.println(tempCliente.getTipoList());
    }
    
    public void onPaisSelect(){
        System.out.println(selectedPais.getPaisId());
    }
    
    public void onCiudadSelect(){
        System.out.println(tempCliente.getTipoList());
    }
    
}
