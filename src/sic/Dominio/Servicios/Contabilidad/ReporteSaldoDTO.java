/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Contabilidad;

import sic.Aplicacion.Servicios.Reportes.ReporteContable;

/**
 *
 * @author FANNY BURGOS
 */
public class ReporteSaldoDTO extends ReporteContable{
    protected String codigocta;
    protected String denominacion;
    protected double saldo;    

    /**
     * @return the codigocta
     */
    public String getCodigocta() {
        return codigocta;
    }

    /**
     * @param codigocta the codigocta to set
     */
    public void setCodigocta(String codigocta) {
        this.codigocta = codigocta;
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
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
