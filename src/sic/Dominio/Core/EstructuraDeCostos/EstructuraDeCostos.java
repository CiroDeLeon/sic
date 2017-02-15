/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos;

import java.util.Vector;

/**
 *
 * @author Usuario1
 */
public class EstructuraDeCostos {
    private long id;
    private String descripcion;
    private Vector<CentroDeCosto> centros_de_costos;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the centros_de_costos
     */
    public Vector<CentroDeCosto> getCentros_de_costos() {
        return centros_de_costos;
    }

    /**
     * @param centros_de_costos the centros_de_costos to set
     */
    public void setCentros_de_costos(Vector<CentroDeCosto> centros_de_costos) {
        this.centros_de_costos = centros_de_costos;
    }
}
