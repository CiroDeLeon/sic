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
public class Consepto {
    private long id;
    private Informe informe;
    private String descripcion;
    private String descripcion_idioma_1;
    private String descripcion_idioma_2;
    private String descripcion_idioma_3;
    private int posicion;

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
     * @return the informe
     */
    public Informe getInforme() {
        return informe;
    }

    /**
     * @param informe the informe to set
     */
    public void setInforme(Informe informe) {
        this.informe = informe;
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
     * @return the descripcion_idioma_1
     */
    public String getDescripcion_idioma_1() {
        return descripcion_idioma_1;
    }

    /**
     * @param descripcion_idioma_1 the descripcion_idioma_1 to set
     */
    public void setDescripcion_idioma_1(String descripcion_idioma_1) {
        this.descripcion_idioma_1 = descripcion_idioma_1;
    }

    /**
     * @return the descripcion_idioma_2
     */
    public String getDescripcion_idioma_2() {
        return descripcion_idioma_2;
    }

    /**
     * @param descripcion_idioma_2 the descripcion_idioma_2 to set
     */
    public void setDescripcion_idioma_2(String descripcion_idioma_2) {
        this.descripcion_idioma_2 = descripcion_idioma_2;
    }

    /**
     * @return the descripcion_idioma_3
     */
    public String getDescripcion_idioma_3() {
        return descripcion_idioma_3;
    }

    /**
     * @param descripcion_idioma_3 the descripcion_idioma_3 to set
     */
    public void setDescripcion_idioma_3(String descripcion_idioma_3) {
        this.descripcion_idioma_3 = descripcion_idioma_3;
    }

    /**
     * @return the posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
