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
public class ReporteDeSaldoCtaTDTO extends ReporteSaldoDTO{
    public static Date fechacorte;
    public static Object idctat;
    public static String titulo;
    public static String subcontabilidad;
    private String modo;
    
    /**
     * @return the fechacorte
     */
    public Date getFechacorte() {
        return ReporteDeSaldoCtaTDTO.fechacorte;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechacorte(Date fechacorte) {
        ReporteDeSaldoCtaTDTO.fechacorte = fechacorte;
    }

    /**
     * @return the idctat
     */
    public Object getIdctat() {
        return ReporteDeSaldoCtaTDTO.idctat;
    }

    /**
     * @param idctat the idctat to set
     */
    public void setIdctat(Object idctat) {
        ReporteDeSaldoCtaTDTO.idctat = idctat;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return ReporteDeSaldoCtaTDTO.titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        ReporteDeSaldoCtaTDTO.titulo = titulo;
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

    public String getSubcontabilidad() {
        return ReporteDeSaldoCtaTDTO.subcontabilidad;
    }

}
