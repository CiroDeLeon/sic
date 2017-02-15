/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Contabilidad;

import java.util.Date;
import sic.Aplicacion.Servicios.Reportes.ReporteContable;

/**
 *
 * @author FANNY BURGOS
 */
public class AsientoPorFechaDTO extends ReporteContable{
    public static  Date fechainicio;
    public static  Date fechacorte;
    public static  String subcontabilidad;
    private Date fecha;
    private Object idcta;
    private String denominacion;
    private double debito;
    private double credito;
    private String soporte;
    private String modo;

    /**
     * @return the fechainicio
     */
    public Date getFechainicio() {
        return AsientoPorFechaDTO.fechainicio;
    }

    /**
     * @param fechainicio the fechainicio to set
     */
    public void setFechainicio(Date fechainicio) {
        AsientoPorFechaDTO.fechainicio = fechainicio;
    }

    /**
     * @return the fechacorte
     */
    public Date getFechacorte() {
        return AsientoPorFechaDTO.fechacorte;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechacorte(Date fechacorte) {
        AsientoPorFechaDTO.fechacorte = fechacorte;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the idcta
     */
    public Object getIdcta() {
        return idcta;
    }

    /**
     * @param idcta the idcta to set
     */
    public void setIdcta(Object idcta) {
        this.idcta = idcta;
    }

    /**
     * @return the denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * @param denominacion the denominacion to set
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * @return the debito
     */
    public double getDebito() {
        return debito;
    }

    /**
     * @param debito the debito to set
     */
    public void setDebito(double debito) {
        this.debito = debito;
    }

    /**
     * @return the credito
     */
    public double getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(double credito) {
        this.credito = credito;
    }

    /**
     * @return the documento
     */
    public String getSoporte() {
        return soporte;
    }

    /**
     * @param documento the documento to set
     */
    public void setSoporte(String documento) {
        this.soporte = documento;
    }

    /**
     * @return the modo
     */
    public String getModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(String modo) {
        this.modo = modo;
    }
    public String getSubcontabilidad(){
        return AsientoPorFechaDTO.subcontabilidad;
    }
}
