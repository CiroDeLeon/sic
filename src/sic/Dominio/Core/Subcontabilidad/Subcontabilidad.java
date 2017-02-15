/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Subcontabilidad;

/**
 *
 * @author Usuario1
 */
public class Subcontabilidad {
    private long id;
    private String descripcion;
    private Subcontabilidad padre;
    private sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos estructura_de_costos;

    @Override
    public String toString() {
        return id+"-"+descripcion;
    }

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
     * @return the padre
     */
    public Subcontabilidad getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Subcontabilidad padre) {
        this.padre = padre;
    }

    /**
     * @return the estructura_de_costos
     */
    public sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos getEstructura_de_costos() {
        return estructura_de_costos;
    }

    /**
     * @param estructura_de_costos the estructura_de_costos to set
     */
    public void setEstructura_de_costos(sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos estructura_de_costos) {
        this.estructura_de_costos = estructura_de_costos;
    }
}
