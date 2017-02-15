/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.GeneradorDeInformes;

/**
 *
 * @author Usuario1
 */
public class Informe {
    private long id;
    private String descripcion;
    private int numero_de_fechas;
    private boolean horizontal;

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
     * @return the numero_de_fechas
     */
    public int getNumero_de_fechas() {
        return numero_de_fechas;
    }

    /**
     * @param numero_de_fechas the numero_de_fechas to set
     */
    public void setNumero_de_fechas(int numero_de_fechas) {
        this.numero_de_fechas = numero_de_fechas;
    }

    /**
     * @return the horizontal
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * @param horizontal the horizontal to set
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
    
}
