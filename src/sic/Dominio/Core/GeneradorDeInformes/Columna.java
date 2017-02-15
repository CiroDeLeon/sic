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
public class Columna {
    private long id;
    private Informe informe;
    private String descripcion;
    private String tipo;
    private boolean totalizado; 
    private int posicion;
    private boolean local;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the totalizado
     */
    public boolean isTotalizado() {
        return totalizado;
    }

    /**
     * @param totalizado the totalizado to set
     */
    public void setTotalizado(boolean totalizado) {
        this.totalizado = totalizado;
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

    /**
     * @return the local
     */
    public boolean isLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(boolean local) {
        this.local = local;
    }
}
