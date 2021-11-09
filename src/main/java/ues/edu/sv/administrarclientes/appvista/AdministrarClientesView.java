/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.edu.sv.administrarclientes.appvista;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ues.edu.sv.administrarclientes.controlador.CiudadFacade;
import ues.edu.sv.administrarclientes.controlador.ClienteFacade;
import ues.edu.sv.administrarclientes.controlador.DireccionFacade;
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.controlador.TiendaFacade;
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
@ViewScoped
public class AdministrarClientesView implements Serializable{
    
    // Controladores
    @Inject
    private PaisFacade paisFacade;
    @Inject
    private CiudadFacade ciudadFacade;
    @Inject
    private DireccionFacade direccionFacade;
    @Inject
    private TiendaFacade tiendaFacade;
    @Inject
    private ClienteFacade clienteFacade;
    @Inject
    private TipoFacade tipoFacade;
    
    // Listas de los datos
    private List<Pais> paises;
    private List<Cliente> clientes;
    private List<Tipo> tipos;
    private List<Tienda> tiendas;
    
    // Selecciones de la vista
    private Pais selectedPais;
    private Ciudad selectedCiudad;
    private Direccion selectedDireccion;
    private Tienda selectedTienda;
    
    // Cliente temporal
    private Cliente tempCliente;
    
    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        clientes = new ArrayList<>();
        tipos = new ArrayList<>();
        tiendas = new ArrayList<>();
        limpiarVista();
        
        // Cargar las listas
        paises = paisFacade.findAll();
        clientes = clienteFacade.findAll();
        tipos = tipoFacade.findAll();
        tiendas = tiendaFacade.findAll();
    }
    
    public void clearTempCliente(){
        tempCliente = new Cliente();
        tempCliente.setTiendaId(new Tienda());
        tempCliente.setDireccionId(new Direccion());
        tempCliente.setTipoList(new ArrayList<Tipo>()); //NO SE CREAR AQUI
    }
    
    public void limpiarVista(){
        clearTempCliente();
        // Seleciones se instancias
        selectedPais = new Pais();
        selectedCiudad = new Ciudad();
        selectedDireccion = new Direccion();
        selectedTienda = new Tienda();
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

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
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

    public Tienda getSelectedTienda() {
        return selectedTienda;
    }

    public void setSelectedTienda(Tienda selectedTienda) {
        this.selectedTienda = selectedTienda;
    }
    
    public void onRowSelect(){
        System.out.println(tempCliente.getNombres());
        selectedDireccion = tempCliente.getDireccionId();
        selectedCiudad = tempCliente.getDireccionId().getCiudadId();
        selectedPais = tempCliente.getDireccionId().getCiudadId().getPaisId();
        // Se cargan las tiendas de esa direccion
        tiendas = selectedDireccion.getTiendaList();
        selectedTienda = tempCliente.getTiendaId();
        
    }
    
    public void onTipoSelect(){
        System.out.println(tempCliente.getTipoList());
    }
    
    public void onPaisSelect(){
        BigDecimal paisId = selectedPais.getPaisId();
        // Se verfica que no este null
        if(paisId != null){
            selectedPais = paisFacade.find(paisId);
        }
    }
    
    public void onCiudadSelect(){
        BigDecimal ciudadId = selectedCiudad.getCiudadId();
        // Se verfica que no este null
        if(ciudadId != null){
            selectedCiudad = ciudadFacade.find(ciudadId);
        }
    }
    
    public void onDireccionSelect(){
        BigDecimal direccionId = selectedDireccion.getDireccionId();
        // Se verfica que no este null
        if(direccionId != null){
            selectedDireccion = direccionFacade.find(direccionId);
            tiendas = selectedDireccion.getTiendaList();
        }
    }
    
    public void buscar(){
        System.out.println("Buscando cliente " + tempCliente.getNombres());
    }
    
    public void registrar(){
        System.out.println("Buscando cliente " + tempCliente.getNombres());
    }
    
    public void eliminar(){
        System.out.println("Buscando cliente " + tempCliente.getNombres());
    }
    
    public void editar(){
        System.out.println("Buscando cliente " + tempCliente.getNombres());
    }
    
}
