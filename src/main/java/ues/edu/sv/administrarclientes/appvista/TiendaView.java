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
import ues.edu.sv.administrarclientes.entidades.Cliente;
import ues.edu.sv.administrarclientes.entidades.Direccion;
import ues.edu.sv.administrarclientes.entidades.Pais;
import ues.edu.sv.administrarclientes.entidades.Tienda;
import ues.edu.sv.administrarclientes.controlador.ClienteFacade;
import ues.edu.sv.administrarclientes.controlador.DireccionFacade;
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.controlador.TiendaFacade;
import ues.edu.sv.administrarclientes.controlador.TipoFacade;
import ues.edu.sv.administrarclientes.entidades.Ciudad;
import ues.edu.sv.administrarclientes.entidades.Tipo;

/**
 *
 * @author Juan
 */
@Named
@ViewScoped
public class TiendaView implements Serializable {

    // Controladores
    @Inject
    private PaisFacade paisFacade;
    @Inject
    private CiudadFacade ciudadFacade;
    @Inject
    private DireccionFacade direccionFacade;
    @Inject
    private TiendaFacade tiendaFacade;

    // Listas de los datos
    private List<Pais> paises;
    private List<Tienda> tiendas;

    // Selecciones de la vista
    private Pais selectedPais;
    private Ciudad selectedCiudad;
    private Direccion selectedDireccion;

    // temporal
    private Tienda tempTienda;

    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        tiendas = new ArrayList<>();
        limpiarVista();

        // Cargar las listas
        paises = paisFacade.findAll();
        tiendas = tiendaFacade.findAll();
        updateTable();
    }

    public void clearTempTienda() {
        tempTienda = new Tienda();
    }

    public void limpiarVista() {
        clearTempTienda();
        // Seleciones se instancias
        selectedPais = new Pais();
        selectedCiudad = new Ciudad();
        selectedDireccion = new Direccion();
    }

    public void updateTable() {
        tiendas = tiendaFacade.findAll();
    }

    public Tienda getTempTienda() {
        return tempTienda;
    }

    public void setTempTienda(Tienda tempTienda) {
        this.tempTienda = tempTienda;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
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

    public void onRowSelect() {
        selectedDireccion = tempTienda.getDireccionId();
        selectedCiudad = tempTienda.getDireccionId().getCiudadId();
        selectedPais = tempTienda.getDireccionId().getCiudadId().getPaisId();
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

    public void buscar() {
        //System.out.println("Buscando cliente " + tempCliente.getNombres());
    }

    public void registrar() {
        // Se crea
        tempTienda.setFechaCreacion(new Date());
        tempTienda.setDireccionId(selectedDireccion);
        tempTienda = tiendaFacade.createObId(tempTienda);
        
        // Se actualiza la tabla principal
        updateTable();
    }

    public void eliminar() {
        // Se obtiene el id del cliente para eliminar
        BigDecimal tiendaId = tempTienda.getTiendaId();
        if (tiendaId != null) {
            tiendaFacade.remove(tempTienda);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public void editar() {
        // Se obtiene el id del cliente para eliminar
        BigDecimal tiendaId = tempTienda.getTiendaId();

        if (tiendaId != null) {
            // Se guardan relaciones externas
            tempTienda.setDireccionId(selectedDireccion);
            // Se edita
            tiendaFacade.edit(tempTienda);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        Tienda tienda = (Tienda) value;
        return tienda.getNombre().toLowerCase().contains(filterText)
                || tienda.getTelefonoTXT().toLowerCase().contains(filterText);
    }

}
