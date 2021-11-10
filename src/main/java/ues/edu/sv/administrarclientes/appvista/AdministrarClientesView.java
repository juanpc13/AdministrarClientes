/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.edu.sv.administrarclientes.appvista;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class AdministrarClientesView implements Serializable {

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
        tipos = tipoFacade.findAll();
        tiendas = tiendaFacade.findAll();
        updateTable();
    }

    public void clearTempCliente() {
        tempCliente = new Cliente();
        tempCliente.setTiendaId(new Tienda());
        tempCliente.setDireccionId(new Direccion());
        tempCliente.setTipoList(new ArrayList<Tipo>()); //NO SE CREAR AQUI
    }

    public void limpiarVista() {
        clearTempCliente();
        // Seleciones se instancias
        selectedPais = new Pais();
        selectedCiudad = new Ciudad();
        selectedDireccion = new Direccion();
        selectedTienda = new Tienda();
    }

    public void updateTable() {
        clientes = clienteFacade.findAll();
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

    public void onRowSelect() {
        System.out.println(tempCliente.getNombres());
        selectedDireccion = tempCliente.getDireccionId();
        selectedCiudad = tempCliente.getDireccionId().getCiudadId();
        selectedPais = tempCliente.getDireccionId().getCiudadId().getPaisId();
        // Se cargan las tiendas de esa direccion
        //tiendas = selectedDireccion.getTiendaList();
        selectedTienda = tempCliente.getTiendaId();

    }

    public void onTipoSelect() {
        System.out.println(tempCliente.getTipoList());
    }

    public void onPaisSelect() {
        BigDecimal paisId = selectedPais.getPaisId();
        // Se verfica que no este null
        if (paisId != null) {
            selectedPais = paisFacade.find(paisId);
        }
        // Se limpia la ciudad y direccion
        selectedCiudad = new Ciudad();
        selectedDireccion = new Direccion();
    }

    public void onCiudadSelect() {
        BigDecimal ciudadId = selectedCiudad.getCiudadId();
        // Se verfica que no este null
        if (ciudadId != null) {
            selectedCiudad = ciudadFacade.find(ciudadId);
        }
        // Se limpia la direccion
        selectedDireccion = new Direccion();
    }

    public void onDireccionSelect() {
        BigDecimal direccionId = selectedDireccion.getDireccionId();
        // Se verfica que no este null
        if (direccionId != null) {
            selectedDireccion = direccionFacade.find(direccionId);
            //tiendas = selectedDireccion.getTiendaList();
        }
    }

    public void onTiendaSelect() {
        BigDecimal tiendaId = selectedTienda.getTiendaId();
        // Se verfica que no este null
        if (tiendaId != null) {
            selectedTienda = tiendaFacade.find(tiendaId);
        }
    }

    public void buscar() {
        System.out.println("Buscando cliente " + tempCliente.getNombres());
    }

    public void registrar() {
        // Se obtiene la lista del TIPO de cliente y se le retira del objeto
        // Se retira porque es una entidad o table separada
        // Primero debe persistir el cliente y luego el tipo de Cliente
        List<Tipo> tipoList = tempCliente.getTipoList();
        tempCliente.setTipoList(null);

        // Se crea al cliente
        tempCliente.setActivo(1);
        tempCliente.setTiendaId(selectedTienda);
        tempCliente.setFechaCreacion(new Date());
        tempCliente.setDireccionId(selectedDireccion);
        tempCliente = clienteFacade.createObId(tempCliente);

        // Se crean los tipos acorde al cliente
        for (Tipo tipo : tipoList) {
            tempCliente.getTipoList().add(tipo);
        }

        // Se edita para los tipos que posee
        clienteFacade.edit(tempCliente);

        // Se actualiza la tabla principal
        updateTable();
    }

    public void eliminar() {
        // Se obtiene el id del cliente para eliminar
        BigDecimal clienteId = tempCliente.getClienteId();
        if (clienteId != null) {
            clienteFacade.remove(tempCliente);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public void editar() {
        // Se obtiene el id del cliente para eliminar
        BigDecimal clienteId = tempCliente.getClienteId();

        if (clienteId != null) {
            // Se guardan relaciones externas
            tempCliente.setTiendaId(selectedTienda);
            tempCliente.setDireccionId(selectedDireccion);
            // Se edita
            clienteFacade.edit(tempCliente);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        Cliente cliente = (Cliente) value;
        return cliente.getNombres().toLowerCase().contains(filterText)
                || cliente.getApellidos().toLowerCase().contains(filterText)
                || cliente.getTiendaId().getNombre().toLowerCase().contains(filterText);
    }

}
