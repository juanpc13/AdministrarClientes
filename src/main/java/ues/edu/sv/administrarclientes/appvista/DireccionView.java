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
import ues.edu.sv.administrarclientes.controlador.DireccionFacade;
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.entidades.Ciudad;
import ues.edu.sv.administrarclientes.entidades.Direccion;
import ues.edu.sv.administrarclientes.entidades.Pais;

/**
 *
 * @author Juan
 */
@Named
@ViewScoped
public class DireccionView implements Serializable {

    // Controladores
    @Inject
    private PaisFacade paisFacade;
    @Inject
    private CiudadFacade ciudadFacade;
    @Inject
    private DireccionFacade direccionFacade;

    // Listas de los datos
    private List<Pais> paises;
    private List<Direccion> direcciones;

    // Selecciones temporal
    private Direccion tempDireccion;

    // Selected
    private Pais selectedPais;
    private Ciudad selectedCiudad;

    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        direcciones = new ArrayList<>();
        limpiarVista();

        // Se cargan las listas
        paises = paisFacade.findAll();
        
        // Se actualiza la tabla
        updateTable();
    }

    public void clearTempDireccion() {
        tempDireccion = new Direccion();
    }

    public void limpiarVista() {
        clearTempDireccion();
        selectedPais =  new Pais();
        selectedCiudad = new Ciudad();
    }

    public void updateTable() {
        direcciones = direccionFacade.findAll();
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public Direccion getTempDireccion() {
        return tempDireccion;
    }

    public void setTempDireccion(Direccion tempDireccion) {
        this.tempDireccion = tempDireccion;
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

    public void onRowSelect() {
        selectedCiudad = tempDireccion.getCiudadId();
        selectedPais = tempDireccion.getCiudadId().getPaisId();
    }

    public void buscar() {
        //System.out.println("Buscando cliente " + tempCliente.getNombres());
    }

    public void registrar() {
        tempDireccion.setCiudadId(selectedCiudad);
        tempDireccion.setFechaCreacion(new Date());
        direccionFacade.create(tempDireccion);
        
        //clearTempTipo();
        updateTable();
    }

    public void eliminar() {
        // Se obtiene el id para eliminar
        BigDecimal direccionId = tempDireccion.getDireccionId();
        if (direccionId != null) {
            direccionFacade.remove(tempDireccion);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public void editar() {
        // Se obtiene el id para editar
        BigDecimal direccionId = tempDireccion.getDireccionId();

        if (direccionId != null) {
            // Se edita
            tempDireccion.setCiudadId(selectedCiudad);
            direccionFacade.edit(tempDireccion);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        Direccion direccion = (Direccion) value;
        return direccion.getDireccion().toLowerCase().contains(filterText)
                || direccion.getCiudadId().getCiudad().toLowerCase().contains(filterText)
                || direccion.getCiudadId().getPaisId().getPais().toLowerCase().contains(filterText);
    }
    
    public void onPaisSelect() {
        BigDecimal paisId = selectedPais.getPaisId();
        // Se verfica que no este null
        if (paisId != null) {
            selectedPais = paisFacade.find(paisId);
        }
        // Se limpia la ciudad
        selectedCiudad = new Ciudad();
    }
    
    public void onCiudadSelect() {
        BigDecimal ciudadId = selectedCiudad.getCiudadId();
        // Se verfica que no este null
        if (ciudadId != null) {
            selectedCiudad = ciudadFacade.find(ciudadId);
        }
    }

}
