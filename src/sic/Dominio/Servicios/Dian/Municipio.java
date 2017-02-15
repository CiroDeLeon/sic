/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Dian;

import sic.Dominio.DomainObject;

/**
 *
 * @author FANNY BURGOS
 */
public class Municipio extends DomainObject{
    private String descripcion;
    private int iddpto;
    private String descripciondpto;
    private int idpais;
    private String descripcionpais;

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
     * @return the iddpto
     */
    public int getIddpto() {
        return iddpto;
    }

    /**
     * @param iddpto the iddpto to set
     */
    public void setIddpto(int iddpto) {
        this.iddpto = iddpto;
    }

    /**
     * @return the descripciondpto
     */
    public String getDescripciondpto() {
        return descripciondpto;
    }

    /**
     * @param descripciondpto the descripciondpto to set
     */
    public void setDescripciondpto(String descripciondpto) {
        this.descripciondpto = descripciondpto;
    }

    /**
     * @return the idpais
     */
    public int getIdpais() {
        return idpais;
    }

    /**
     * @param idpais the idpais to set
     */
    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    /**
     * @return the descripcionpais
     */
    public String getDescripcionpais() {
        return descripcionpais;
    }

    /**
     * @param descripcionpais the descripcionpais to set
     */
    public void setDescripcionpais(String descripcionpais) {
        this.descripcionpais = descripcionpais;
    }
    @Override
    public String toString(){
        return (this.descripcion+" , "+this.descripciondpto+" - "+this.descripcionpais).toUpperCase();
    }
}
