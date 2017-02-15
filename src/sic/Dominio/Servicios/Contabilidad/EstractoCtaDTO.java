/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Contabilidad;

import java.util.Date;

/**
 *
 * @author FANNY BURGOS
 */
public class EstractoCtaDTO extends ReporteSaldoDTO{
    public static String cta;
    public static String denominacioncta;
    public static String ctab;
    public static String denominacionctab;
    public static Date fechainicio;
    public static Date fechacorte;
    public static String subcontabilidad;
    private double saldoanterior;
    private double debito;
    private double credito;
    private String modo;
    
    /**
     * @return the fechainicio
     */
    public Date getFechainicio() {
        return EstractoCtaDTO.fechainicio;
    }

    /**
     * @param fechainicio the fechainicio to set
     */
    public void setFechainicio(Date fechainicio) {
        EstractoCtaDTO.fechainicio = fechainicio;
    }

    /**
     * @return the fechacorte
     */
    public Date getFechacorte() {
        return EstractoCtaDTO.fechacorte;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechacorte(Date fechacorte) {
        EstractoCtaDTO.fechacorte = fechacorte;
    }

    /**
     * @return the saldoanterior
     */
    public double getSaldoanterior() {
        return saldoanterior;
    }

    /**
     * @param saldoanterior the saldoanterior to set
     */
    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    /**
     * @return the sumadebito
     */
    public double getDebito() {
        return debito;
    }

    /**
     * @param sumadebito the sumadebito to set
     */
    public void setDebito(double debito) {
        this.debito = debito;
    }

    /**
     * @return the sumacredito
     */
    public double getCredito() {
        return credito;
    }

    /**
     * @param sumacredito the sumacredito to set
     */
    public void setCredito(double sumacredito) {
        this.credito = sumacredito;
    }

    /**
     * @return the cta
     */
    public String getCta() {
        return EstractoCtaDTO.cta;
    }

    /**
     * @param cta the cta to set
     */
    public void setCta(String cta) {
        EstractoCtaDTO.cta = cta;
    }

    /**
     * @return the denominacioncta
     */
    public String getDenominacioncta() {
        return EstractoCtaDTO.denominacioncta;
    }

    /**
     * @param denominacioncta the denominacioncta to set
     */
    public void setDenominacioncta(String denominacioncta) {
        EstractoCtaDTO.denominacioncta = denominacioncta;
    }
    public String getSubcta(){
        return this.codigocta.substring(0,6);
    }
    public String getAux(){
        return this.codigocta.substring(6,8);
    }
    public String getCtab() {
        return EstractoCtaDTO.ctab;
    }

    /**
     * @param cta the cta to set
     */
    public void setCtab(String ctab) {
        EstractoCtaDTO.ctab = ctab;
    }

    /**
     * @return the denominacioncta
     */
    public String getDenominacionctab() {
        return EstractoCtaDTO.denominacionctab;
    }

    /**
     * @param denominacioncta the denominacioncta to set
     */
    public void setDenominacionctab(String denominacionctab) {
        EstractoCtaDTO.denominacionctab = denominacionctab;
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
        return EstractoCtaDTO.subcontabilidad;
    }
}
