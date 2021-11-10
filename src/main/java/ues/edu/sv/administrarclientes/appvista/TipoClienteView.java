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
import ues.edu.sv.administrarclientes.controlador.TipoFacade;
import ues.edu.sv.administrarclientes.entidades.Tipo;

/**
 *
 * @author Juan
 */
@Named
@ViewScoped
public class TipoClienteView implements Serializable {

    // Controladores
    @Inject
    private TipoFacade tipoFacade;

    // Listas de los datos
    private List<Tipo> tipos;

    // Seleccion temporal
    private Tipo tempTipo;

    @PostConstruct
    public void init() {
        // Inicializando como new las variables
        tipos = new ArrayList<>();
        limpiarVista();

        // Cargar las listas
        tipos = tipoFacade.findAll();
        updateTable();
    }

    public void clearTempTipo() {
        tempTipo = new Tipo();
    }

    public void limpiarVista() {
        clearTempTipo();
    }

    public void updateTable() {
        tipos = tipoFacade.findAll();
    }

    public Tipo getTempTipo() {
        return tempTipo;
    }

    public void setTempTipo(Tipo tempTipo) {
        this.tempTipo = tempTipo;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public Boolean getMarcador() {
        return tempTipo.getActivo() == 1; // Conversion del activo de tipo
    }

    public void setMarcador(Boolean marcador) {
        int value = marcador ? 1 : 0;
        tempTipo.setActivo(value);
    }

    public void onRowSelect() {

    }

    public void buscar() {
        //System.out.println("Buscando cliente " + tempCliente.getNombres());
    }

    public void registrar() {
        tempTipo.setFechaCreacion(new Date());
        tipoFacade.create(tempTipo);
        // Se actualiza la tabla principal
        clearTempTipo();
        updateTable();
    }

    public void eliminar() {
        // Se obtiene el id para eliminar
        BigDecimal tipoId = tempTipo.getTipoId();
        if (tipoId != null) {
            tipoFacade.remove(tempTipo);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public void editar() {
        // Se obtiene el id para editar
        BigDecimal tipoId = tempTipo.getTipoId();

        if (tipoId != null) {
            // Se edita
            tipoFacade.edit(tempTipo);
        }
        // Se actualiza la tabla principal
        updateTable();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        Tipo tipo = (Tipo) value;
        return tipo.getNombre().toLowerCase().contains(filterText);
    }

}
