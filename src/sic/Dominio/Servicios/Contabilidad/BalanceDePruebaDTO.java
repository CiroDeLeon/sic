/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Servicios.Contabilidad;

import java.util.Date;

/**
 *
 * @author Usuario1
 */
public class BalanceDePruebaDTO extends sic.Aplicacion.Servicios.Reportes.ReporteContable{    
    private Date fecha_inicial;
    private Date fecha_final;
    private String id_cuenta;
    private String denominacion;
    private double saldo_anterior;
    private double saldo_actual; 
    private double debito;
    private double credito;
    private String modo;
    private String subcontabilidad;
    public static double sdebito;
    public static double scredito;
  

    /**
     * @return the fecha_final
     */
    public Date getFecha_final() {
        return fecha_final;
    }

    /**
     * @param fecha_final the fecha_final to set
     */
    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    /**
     * @return the id_cuenta
     */
    public String getId_cuenta() {
        return id_cuenta;
    }

    /**
     * @param id_cuenta the id_cuenta to set
     */
    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
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
     * @return the saldo_anterior
     */
    public double getSaldo_anterior() {
        return saldo_anterior;
    }

    /**
     * @param saldo_anterior the saldo_anterior to set
     */
    public void setSaldo_anterior(double saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    /**
     * @return the saldo_actual
     */
    public double getSaldo_actual() {
        return saldo_actual;
    }

    /**
     * @param saldo_actual the saldo_actual to set
     */
    public void setSaldo_actual(double saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    /**
     * @return the fecha_inicial
     */
    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    /**
     * @param fecha_inicial the fecha_inicial to set
     */
    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
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

    /**
     * @return the subcontabilidad
     */
    public String getSubcontabilidad() {
        return subcontabilidad;
    }

    /**
     * @param subcontabilidad the subcontabilidad to set
     */
    public void setSubcontabilidad(String subcontabilidad) {
        this.subcontabilidad = subcontabilidad;
    }

    /**
     * @return the sdebito
     */
    public double getSdebito() {
        return BalanceDePruebaDTO.sdebito;
    }

    /**
     * @return the scredito
     */
    public double getScredito() {
        return BalanceDePruebaDTO.scredito;
    }
}
