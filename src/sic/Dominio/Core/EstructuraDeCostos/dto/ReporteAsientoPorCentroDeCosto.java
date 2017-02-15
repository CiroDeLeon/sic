/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos.dto;

import java.util.Date;

/**
 *
 * @author Usuario1
 */
public class ReporteAsientoPorCentroDeCosto {
    private long idcc;
    private String descripcion_cc;
    private long id_subcontabilidad;
    private String descripcion_subcontabilidad;
    private long id_cuenta_t;    
    private String descripcion_cuenta_t;        
    private java.sql.Timestamp fecha_inicial;
    private java.sql.Timestamp fecha_final;
    
    private java.sql.Timestamp fecha_contable;
    private String soporte;
    private String detalle;
    private double debito;
    private double credito;    
    private double entradas;
    private double salidas;

    /**
     * @return the idcc
     */
    public long getIdcc() {
        return idcc;
    }

    /**
     * @param idcc the idcc to set
     */
    public void setIdcc(long idcc) {
        this.idcc = idcc;
    }

    /**
     * @return the descripcion_cc
     */
    public String getDescripcion_cc() {
        return descripcion_cc;
    }

    /**
     * @param descripcion_cc the descripcion_cc to set
     */
    public void setDescripcion_cc(String descripcion_cc) {
        this.descripcion_cc = descripcion_cc;
    }

    /**
     * @return the id_subcontabilidad
     */
    public long getId_subcontabilidad() {
        return id_subcontabilidad;
    }

    /**
     * @param id_subcontabilidad the id_subcontabilidad to set
     */
    public void setId_subcontabilidad(long id_subcontabilidad) {
        this.id_subcontabilidad = id_subcontabilidad;
    }

    /**
     * @return the descripcion_subcontabilidad
     */
    public String getDescripcion_subcontabilidad() {
        return descripcion_subcontabilidad;
    }

    /**
     * @param descripcion_subcontabilidad the descripcion_subcontabilidad to set
     */
    public void setDescripcion_subcontabilidad(String descripcion_subcontabilidad) {
        this.descripcion_subcontabilidad = descripcion_subcontabilidad;
    }

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
     * @return the descripcion_cuenta_t
     */
    public String getDescripcion_cuenta_t() {
        return descripcion_cuenta_t;
    }

    /**
     * @param descripcion_cuenta_t the descripcion_cuenta_t to set
     */
    public void setDescripcion_cuenta_t(String descripcion_cuenta_t) {
        this.descripcion_cuenta_t = descripcion_cuenta_t;
    }

  

  

    /**
     * @return the soporte
     */
    public String getSoporte() {
        return soporte;
    }

    /**
     * @param soporte the soporte to set
     */
    public void setSoporte(String soporte) {
        this.soporte = soporte;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
     * @return the entradas
     */
    public double getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(double entradas) {
        this.entradas = entradas;
    }

    /**
     * @return the salidas
     */
    public double getSalidas() {
        return salidas;
    }

    /**
     * @param salidas the salidas to set
     */
    public void setSalidas(double salidas) {
        this.salidas = salidas;
    }

    /**
     * @return the fecha_inicial
     */
    public java.sql.Timestamp getFecha_inicial() {
        return fecha_inicial;
    }

    /**
     * @param fecha_inicial the fecha_inicial to set
     */
    public void setFecha_inicial(java.sql.Timestamp fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    /**
     * @return the fecha_final
     */
    public java.sql.Timestamp getFecha_final() {
        return fecha_final;
    }

    /**
     * @param fecha_final the fecha_final to set
     */
    public void setFecha_final(java.sql.Timestamp fecha_final) {
        this.fecha_final = fecha_final;
    }

    /**
     * @return the fecha_contable
     */
    public java.sql.Timestamp getFecha_contable() {
        return fecha_contable;
    }

    /**
     * @param fecha_contable the fecha_contable to set
     */
    public void setFecha_contable(java.sql.Timestamp fecha_contable) {
        this.fecha_contable = fecha_contable;
    }
    
}
