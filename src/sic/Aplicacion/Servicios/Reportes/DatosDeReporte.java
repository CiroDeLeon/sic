/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Reportes;

import sic.Dominio.DomainObject;

/**
 *
 * @author FANNY BURGOS
 */
public class DatosDeReporte extends DomainObject{    
    private long nit;
    private String razonsocial;
    private String direccion;
    private String telefono;
    private String publicidad;
    private String regimen;
    
   

    public DatosDeReporte() {
    }


    public DatosDeReporte(long nit, String razonsocial, String direccion, String telefono, String publicidad, String regimen) {
        this.nit = nit;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.publicidad = publicidad;
        this.regimen = regimen;
    }

    /**
     * @return the nit
     */
    public long getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(long nit) {
        this.nit = nit;
    }

    /**
     * @return the razonsocial
     */
    public String getRazonsocial() {
        return razonsocial;
    }

    /**
     * @param razonsocial the razonsocial to set
     */
    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the publicidad
     */
    public String getPublicidad() {
        return publicidad;
    }

    /**
     * @param publicidad the publicidad to set
     */
    public void setPublicidad(String publicidad) {
        this.publicidad = publicidad;
    }

    /**
     * @return the regimen
     */
    public String getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the regimen to set
     */
    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }
}
