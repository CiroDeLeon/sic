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
public class ResumenAuxDTO extends ReporteContable{
    public static String codaux;
    public static String denominacionaux;
    public static Date fechacorte;
    public static Date fechainicio;
    public static String subcontabilidad;
    private String ctat;
    private String denominacion;
    private double saldo;
    private double existencia;
    private double baseiva;
    private double iva;
    private double basertf;
    private double rtf;
    private String modo;
    
 /**
     * @return the fechacorte
     */
    public Date getFechainicio() {
        return ResumenAuxDTO.fechainicio;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechainicio(Date fechainicio) {
        ResumenAuxDTO.fechainicio = fechainicio;
    }
    /**
     * @return the codaux
     */
    public String getCodaux() {
        return ResumenAuxDTO.codaux;
    }

    /**
     * @param codaux the codaux to set
     */
    public void setCodaux(String codaux) {
        ResumenAuxDTO.codaux = codaux;
    }

    /**
     * @return the denominacionaux
     */
    public String getDenominacionaux() {
        return ResumenAuxDTO.denominacionaux;
    }

    /**
     * @param denominacionaux the denominacionaux to set
     */
    public void setDenominacionaux(String denominacionaux) {
        ResumenAuxDTO.denominacionaux = denominacionaux;
    }

    /**
     * @return the fechacorte
     */
    public Date getFechacorte() {
        return ResumenAuxDTO.fechacorte;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechacorte(Date fechacorte) {
        ResumenAuxDTO.fechacorte = fechacorte;
    }

    /**
     * @return the ctat
     */
    public String getCtat() {
        return ctat;
    }

    /**
     * @param ctat the ctat to set
     */
    public void setCtat(String ctat) {
        this.ctat = ctat;
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

    /**
     * @return the existencia
     */
    public double getExistencia() {
        return existencia;
    }

    /**
     * @param existencia the existencia to set
     */
    public void setExistencia(double existencia) {
        this.existencia = existencia;
    }

    /**
     * @return the baseiva
     */
    public double getBaseiva() {
        return baseiva;
    }

    /**
     * @param baseiva the baseiva to set
     */
    public void setBaseiva(double baseiva) {
        this.baseiva = baseiva;
    }

    /**
     * @return the iva
     */
    public double getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     * @return the basertf
     */
    public double getBasertf() {
        return basertf;
    }

    /**
     * @param basertf the basertf to set
     */
    public void setBasertf(double basertf) {
        this.basertf = basertf;
    }

    /**
     * @return the rtf
     */
    public double getRtf() {
        return rtf;
    }

    /**
     * @param rtf the rtf to set
     */
    public void setRtf(double rtf) {
        this.rtf = rtf;
    }

    /**
     * @return the modo
     */
    public String getModo() {
        return modo;
    }
     public String getSubcontabilidad() {
        return ResumenAuxDTO.subcontabilidad;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(String modo) {
        this.modo = modo;
    }
}
