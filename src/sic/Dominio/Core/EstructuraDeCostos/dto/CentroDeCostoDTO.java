/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos.dto;

/**
 *
 * @author Usuario1
 */
public class CentroDeCostoDTO {
    protected long   id_cuenta_t;
    protected String descripcion;

    /**
     * @return the id_cuenta_t
     */
    public long getId_cuenta_t() {
        return id_cuenta_t;
    }

    /**
     * @param id_cuenta_t the id_cuenta_t to set
     */
    public void setId_cuenta_t(long id_cuenta_t) {
        this.id_cuenta_t = id_cuenta_t;
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
    
}
