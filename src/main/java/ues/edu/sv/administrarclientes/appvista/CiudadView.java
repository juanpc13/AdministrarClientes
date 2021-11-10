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
import ues.edu.sv.administrarclientes.controlador.PaisFacade;
import ues.edu.sv.administrarclientes.entidades.Ciudad;
import ues.edu.sv.administrarclientes.entidades.Pais;

/**
 *
 * @author Juan
 */
@Named
@ViewScoped
public class CiudadView implements Serializable {

    // Controladores
    @Inject
    private PaisFacade paisFacade;
    @Inject
    private CiudadFacade ciudadFacade;
    
    // Listas de los datos
    private List<Pais> paises;
    private List<Ciudad> ciudades;

    // Selecciones temporal
    private Ciudad tempCiudad;

    // Selected
    private Pais selectedPais;

    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        paises = new ArrayList<>();
        ciudades = new ArrayList<>();
        limpiarVista();

        // Se cargan las listas
        paises = paisFacade.findAll();
        
        // Se actualiza la tabla
        updateTable();
    }

    public void clearTempCiudad() {
        tempCiudad = new Ciudad();
    }

    public void limpiarVista() {
        clearTempCiudad();
        selectedPais =  new Pais();
    }

    public void updateTable() {
        ciudades = ciudadFacade.findAll();
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Ciudad getTempCiudad() {
        return tempCiudad;
    }

    public void setTempCiudad(Ciudad tempCiudad) {
        this.tempCiudad = tempCiudad;
    }

    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }
    
    public Boolean getMarcador() {
        return tempCiudad.getActivo() == 1; // Conversion del activo de tipo
    }

    public void setMarcador(Boolean marcador) {
        int value = marcador ? 1 : 0;
        tempCiudad.setActivo(value);
    }

    public void onRowSelect() {
        selectedPais = tempCiudad.getPaisId();
    }

    public void buscar() {
        //System.out.println("Buscando cliente " + tempCliente.getNombres());
    }

    public void registrar() {
        tempCiudad.setPaisId(selectedPais);
        tempCiudad.setFechaCreacion(new Date());
        tempCiudad = ciudadFacade.createObId(tempCiudad);
        //clearTempTipo();
        updateTable();
    }

    public void eliminar() {
        // Se obtiene el id para eliminar
        BigDecimal direccionId = tempCiudad.getCiudadId();
        if (direccionId != null) {
            ciudadFacade.remove(tempCiudad);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public void editar() {
        // Se obtiene el id para editar
        BigDecimal direccionId = tempCiudad.getCiudadId();

        if (direccionId != null) {
            // Se edita
            tempCiudad.setPaisId(selectedPais);
            ciudadFacade.edit(tempCiudad);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        Ciudad ciudad = (Ciudad) value;
        return ciudad.getCiudad().toLowerCase().contains(filterText)
                || ciudad.getPaisId().getPais().toLowerCase().contains(filterText);
    }
    
    public void onPaisSelect() {
        BigDecimal paisId = selectedPais.getPaisId();
        // Se verfica que no este null
        if (paisId != null) {
            selectedPais = paisFacade.find(paisId);
        }
    }

}
