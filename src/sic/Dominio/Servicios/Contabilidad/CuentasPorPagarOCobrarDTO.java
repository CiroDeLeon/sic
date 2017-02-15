/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Contabilidad;

/**
 *
 * @author Usuario1
 */
public class CuentasPorPagarOCobrarDTO {
   private static String informe;

    /**
     * @return the informe
     */
    public String getInforme() {
        return CuentasPorPagarOCobrarDTO.informe;
    }

    /**
     * @param aInforme the informe to set
     */
    public static void setInforme(String aInforme) {
        informe = aInforme;
    }
   private long id;
   private String descripcion;
   private String nodocumento;
   private double valor;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the nodocumento
     */
    public String getNodocumento() {
        return nodocumento;
    }

    /**
     * @param nodocumento the nodocumento to set
     */
    public void setNodocumento(String nodocumento) {
        this.nodocumento = nodocumento;
    }

    
}
