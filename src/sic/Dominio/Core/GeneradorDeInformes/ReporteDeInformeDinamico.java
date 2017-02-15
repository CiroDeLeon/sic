/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.GeneradorDeInformes;

import java.util.Date;

/**
 *
 * @author Usuario1
 */
public class ReporteDeInformeDinamico extends sic.Aplicacion.Servicios.Reportes.ReporteContable{
    public static String titulo;
   private String consepto; 
   private Date fecha_inicial;
   private Date fecha_final;
   private String columna_1="";
   private String valor_1="";
   public static String total_columna_1="";
   private String columna_2="";
   private String valor_2="";
   public static String total_columna_2="";
   private String columna_3="";
   private String valor_3="";
   public static String total_columna_3="";
   private String columna_4="";
   private String valor_4="";
   public static String total_columna_4="";
   private String columna_5="";
   private String valor_5="";
   public static String total_columna_5="";
   private String columna_6="";
   private String valor_6="";
   public static String total_columna_6="";
   private String columna_7="";
   private String valor_7="";
   public static String total_columna_7="";
   
   public String getTitulo(){
       return ReporteDeInformeDinamico.titulo;
   }
    /**
     * @return the columna_1
     */
    public String getColumna_1() {
        return columna_1;
    }

    /**
     * @param columna_1 the columna_1 to set
     */
    public void setColumna_1(String columna_1) {
        this.columna_1 = columna_1;
    }

    /**
     * @return the valor_1
     */
    public String getValor_1() {
        return valor_1;
    }

    /**
     * @param valor_1 the valor_1 to set
     */
    public void setValor_1(String valor_1) {
        this.valor_1 = valor_1;
    }

    /**
     * @return the total_columna_1
     */
    public String getTotal_columna_1() {
        return total_columna_1;
    }

    /**
     * @param total_columna_1 the total_columna_1 to set
     */
    public void setTotal_columna_1(String total_columna_1) {
        this.total_columna_1 = total_columna_1;
    }

    /**
     * @return the columna_2
     */
    public String getColumna_2() {
        return columna_2;
    }

    /**
     * @param columna_2 the columna_2 to set
     */
    public void setColumna_2(String columna_2) {
        this.columna_2 = columna_2;
    }

    /**
     * @return the valor_2
     */
    public String getValor_2() {
        return valor_2;
    }

    /**
     * @param valor_2 the valor_2 to set
     */
    public void setValor_2(String valor_2) {
        this.valor_2 = valor_2;
    }

    /**
     * @return the total_columna_2
     */
    public String getTotal_columna_2() {
        return total_columna_2;
    }

    /**
     * @param total_columna_2 the total_columna_2 to set
     */
    public void setTotal_columna_2(String total_columna_2) {
        this.total_columna_2 = total_columna_2;
    }

    /**
     * @return the columna_3
     */
    public String getColumna_3() {
        return columna_3;
    }

    /**
     * @param columna_3 the columna_3 to set
     */
    public void setColumna_3(String columna_3) {
        this.columna_3 = columna_3;
    }

    /**
     * @return the valor_3
     */
    public String getValor_3() {
        return valor_3;
    }

    /**
     * @param valor_3 the valor_3 to set
     */
    public void setValor_3(String valor_3) {
        this.valor_3 = valor_3;
    }

    /**
     * @return the total_columna_3
     */
    public String getTotal_columna_3() {
        return total_columna_3;
    }

    /**
     * @param total_columna_3 the total_columna_3 to set
     */
    public void setTotal_columna_3(String total_columna_3) {
        this.total_columna_3 = total_columna_3;
    }

    /**
     * @return the columna_4
     */
    public String getColumna_4() {
        return columna_4;
    }

    /**
     * @param columna_4 the columna_4 to set
     */
    public void setColumna_4(String columna_4) {
        this.columna_4 = columna_4;
    }

    /**
     * @return the valor_4
     */
    public String getValor_4() {
        return valor_4;
    }

    /**
     * @param valor_4 the valor_4 to set
     */
    public void setValor_4(String valor_4) {
        this.valor_4 = valor_4;
    }

    /**
     * @return the total_columna_4
     */
    public String getTotal_columna_4() {
        return total_columna_4;
    }

    /**
     * @param total_columna_4 the total_columna_4 to set
     */
    public void setTotal_columna_4(String total_columna_4) {
        this.total_columna_4 = total_columna_4;
    }

    /**
     * @return the columna_5
     */
    public String getColumna_5() {
        return columna_5;
    }

    /**
     * @param columna_5 the columna_5 to set
     */
    public void setColumna_5(String columna_5) {
        this.columna_5 = columna_5;
    }

    /**
     * @return the valor_5
     */
    public String getValor_5() {
        return valor_5;
    }

    /**
     * @param valor_5 the valor_5 to set
     */
    public void setValor_5(String valor_5) {
        this.valor_5 = valor_5;
    }

    /**
     * @return the total_columna_5
     */
    public String getTotal_columna_5() {
        return total_columna_5;
    }

    /**
     * @param total_columna_5 the total_columna_5 to set
     */
    public void setTotal_columna_5(String total_columna_5) {
        this.total_columna_5 = total_columna_5;
    }

    /**
     * @return the columna_6
     */
    public String getColumna_6() {
        return columna_6;
    }

    /**
     * @param columna_6 the columna_6 to set
     */
    public void setColumna_6(String columna_6) {
        this.columna_6 = columna_6;
    }

    /**
     * @return the valor_6
     */
    public String getValor_6() {
        return valor_6;
    }

    /**
     * @param valor_6 the valor_6 to set
     */
    public void setValor_6(String valor_6) {
        this.valor_6 = valor_6;
    }

    /**
     * @return the total_columna_6
     */
    public String getTotal_columna_6() {
        return total_columna_6;
    }

    /**
     * @param total_columna_6 the total_columna_6 to set
     */
    public void setTotal_columna_6(String total_columna_6) {
        this.total_columna_6 = total_columna_6;
    }

    /**
     * @return the columna_7
     */
    public String getColumna_7() {
        return columna_7;
    }

    /**
     * @param columna_7 the columna_7 to set
     */
    public void setColumna_7(String columna_7) {
        this.columna_7 = columna_7;
    }

    /**
     * @return the valor_7
     */
    public String getValor_7() {
        return valor_7;
    }

    /**
     * @param valor_7 the valor_7 to set
     */
    public void setValor_7(String valor_7) {
        this.valor_7 = valor_7;
    }

    /**
     * @return the total_columna_7
     */
    public String getTotal_columna_7() {
        return total_columna_7;
    }

    /**
     * @param total_columna_7 the total_columna_7 to set
     */
    public void setTotal_columna_7(String total_columna_7) {
        this.total_columna_7 = total_columna_7;
    }

   

    /**
     * @return the consepto
     */
    public String getConsepto() {
        return consepto;
    }

    /**
     * @param consepto the consepto to set
     */
    public void setConsepto(String consepto) {
        this.consepto = consepto;
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
}
