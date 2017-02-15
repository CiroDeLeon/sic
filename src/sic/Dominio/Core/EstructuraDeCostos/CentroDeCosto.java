/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos;

/**
 *
 * @author Usuario1
 */
public class CentroDeCosto {
    private long id;
    private String descripcion;
    private EstructuraDeCostos estructura;
    private CentroDeCosto Padre;

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
     * @return the estructura
     */
    public EstructuraDeCostos getEstructura() {
        return estructura;
    }

    /**
     * @param estructura the estructura to set
     */
    public void setEstructura(EstructuraDeCostos estructura) {
        this.estructura = estructura;
    }

    /**
     * @return the Padre
     */
    public CentroDeCosto getPadre() {
        return Padre;
    }

    /**
     * @param Padre the Padre to set
     */
    public void setPadre(CentroDeCosto Padre) {
        this.Padre = Padre;
    }

  
}
