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
public class EstadoFinancieroDTO extends ReporteSaldoDTO{    
    public static Date fechacorte;
    public static Date fechainicio;
    public static double sumaactivos;
    
    
    public static double sumapasivos;
    
    
    public static double sumapatrimonio;
    
    
    
    
    
    public static double sumaingresos;
    public static double sumagastos;
    public static double sumacostos;
    public static double sumacostos_produccion;
    
    public static double utilidad;
    
    
    public static String modo;
    public static String subcontabilidad;

    /**
     * @return the sumaingresos
     */
    public  double getSumaingresos() {
        return EstadoFinancieroDTO.sumaingresos;
    }

    /**
     * @return the sumagastos
     */
    public  double getSumagastos() {
        return EstadoFinancieroDTO.sumagastos;
    }

    /**
     * @return the sumacostos
     */
    public double getSumacostos() {
        return EstadoFinancieroDTO.sumacostos;
    }

    /**
     * @return the sumacostos_produccion
     */
    public double getSumacostos_produccion() {
        return EstadoFinancieroDTO.sumacostos_produccion;
    }
    public EstadoFinancieroDTO() {
    }

    public EstadoFinancieroDTO(String codigocta, String denominacion, double saldo) {
        this.codigocta = codigocta;
        this.denominacion = denominacion;
        this.saldo = saldo;
    }    
    public double getUtilidad(){
        return EstadoFinancieroDTO.utilidad;
    }
    public void setUtilidad(double utilidad){
        EstadoFinancieroDTO.utilidad=utilidad;
    }
    public String getSubcontabilidad(){
        return EstadoFinancieroDTO.subcontabilidad;
    }
    /**
     * @return the fechacorte
     */
    public Date getFechacorte() {
        return EstadoFinancieroDTO.fechacorte;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechacorte(Date fechacorte) {
        EstadoFinancieroDTO.fechacorte = fechacorte;
    }
      /**
     * @return the fechacorte
     */
    public Date getFechainicio() {
        return EstadoFinancieroDTO.fechainicio;
    }

    /**
     * @param fechacorte the fechacorte to set
     */
    public void setFechainicio(Date fechainicio) {
        EstadoFinancieroDTO.fechainicio = fechainicio;
    }

    /**
     * @return the sumaactivos
     */
    public double getSumaactivos() {
        return EstadoFinancieroDTO.sumaactivos;
    }

    /**
     * @param sumaactivos the sumaactivos to set
     */
    public void setSumaactivos(double sumaactivos) {
        EstadoFinancieroDTO.sumaactivos = sumaactivos;
    }

    /**
     * @return the sumapasivos
     */
    public double getSumapasivos() {
        return EstadoFinancieroDTO.sumapasivos;
    }

    /**
     * @param sumapasivos the sumapasivos to set
     */
    public void setSumapasivos(double sumapasivos) {
        EstadoFinancieroDTO.sumapasivos = sumapasivos;
    }

    /**
     * @return the sumapatrimonio
     */
    public double getSumapatrimonio() {
        return EstadoFinancieroDTO.sumapatrimonio;
    }

    /**
     * @param sumapatrimonio the sumapatrimonio to set
     */
    public void setSumapatrimonio(double sumapatrimonio) {
        EstadoFinancieroDTO.sumapatrimonio = sumapatrimonio;
    }

   
   

    /**
     * @return the modo
     */
    public String getModo() {
        return EstadoFinancieroDTO.modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(String modo) {
        EstadoFinancieroDTO.modo = modo;
    }
}
