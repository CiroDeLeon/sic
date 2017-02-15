/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Dian;



/**
 *
 * @author FANNY BURGOS
 */
public class RetencionDian{    
    private Object id;
    private int año;
    private String descripcion="";
    private double base;
    private double porcentage;
    private Object auxiliar_retencion="";
    private Object auxiliar_anticipo_retencion="";
    private Object auxiliar_autoretencion="";
    private Object auxiliar_anticipo_autoretencion="";
    

    @Override
    public String toString() {
        return ""+descripcion+">>"+base+">>"+porcentage;
    }

    /**
     * @return the año
     */
    public int getAño() {
        return año;
    }

    /**
     * @param año the año to set
     */
    public void setAño(int año) {
        this.año = año;
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
     * @return the base
     */
    public double getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * @return the porcentage
     */
    public double getPorcentage() {
        return porcentage;
    }

    /**
     * @param porcentage the porcentage to set
     */
    public void setPorcentage(double porcentage) {
        this.porcentage = porcentage;
    }

    /**
     * @return the idAuxiliar
     */
    public Object getAuxiliar_retencion() {
        return auxiliar_retencion;
    }

    /**
     * @param idAuxiliar the idAuxiliar to set
     */
    public void setAuxiliar_retencion(Object idAuxiliar) {
        this.auxiliar_retencion = idAuxiliar;
    }

    /**
     * @return the id
     */
    public Object getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }
    public double obtenerValorRetencion(double base,double porcentage,boolean isAutoretenedor){
        if(isAutoretenedor){
            return 0;
        }
        if(base>this.base){
            return Math.round(base*(porcentage/100));
        }else{
            return 0;
        }
    }

    /**
     * @return the auxiliar_anticipo_retencion
     */
    public Object getAuxiliar_anticipo_retencion() {
        return auxiliar_anticipo_retencion;
    }

    /**
     * @param auxiliar_anticipo_retencion the auxiliar_anticipo_retencion to set
     */
    public void setAuxiliar_anticipo_retencion(Object auxiliar_anticipo_retencion) {
        this.auxiliar_anticipo_retencion = auxiliar_anticipo_retencion;
    }

    /**
     * @return the auxiliar_autoretencion
     */
    public Object getAuxiliar_autoretencion() {
        return auxiliar_autoretencion;
    }

    /**
     * @param auxiliar_autoretencion the auxiliar_autoretencion to set
     */
    public void setAuxiliar_autoretencion(Object auxiliar_autoretencion) {
        this.auxiliar_autoretencion = auxiliar_autoretencion;
    }

    /**
     * @return the auxiliar_anticipo_autoretencion
     */
    public Object getAuxiliar_anticipo_autoretencion() {
        return auxiliar_anticipo_autoretencion;
    }

    /**
     * @param auxiliar_anticipo_autoretencion the auxiliar_anticipo_autoretencion to set
     */
    public void setAuxiliar_anticipo_autoretencion(Object auxiliar_anticipo_autoretencion) {
        this.auxiliar_anticipo_autoretencion = auxiliar_anticipo_autoretencion;
    }
}
