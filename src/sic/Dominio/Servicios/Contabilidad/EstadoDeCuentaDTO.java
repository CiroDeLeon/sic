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
public class EstadoDeCuentaDTO {
   private long iddocumento;
   private java.util.Date fecha_contable;
   private String auxiliar;
   public static String cuenta_t;
   private String soporte;
   private String detalle;
   private double debito;
   private double credito;

    /**
     * @return the iddocumento
     */
    public long getIddocumento() {
        return iddocumento;
    }

    /**
     * @param iddocumento the iddocumento to set
     */
    public void setIddocumento(long iddocumento) {
        this.iddocumento = iddocumento;
    }

    /**
     * @return the fecha_contable
     */
    public java.util.Date getFecha_contable() {
        return fecha_contable;
    }

    /**
     * @param fecha_contable the fecha_contable to set
     */
    public void setFecha_contable(java.util.Date fecha_contable) {
        this.fecha_contable = fecha_contable;
    }

    /**
     * @return the auxiliar
     */
    public String getAuxiliar() {
        return auxiliar;
    }

    /**
     * @param auxiliar the auxiliar to set
     */
    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    /**
     * @return the cuenta_t
     */
    public String getCuenta_t() {
        return sic.Dominio.Servicios.Contabilidad.EstadoDeCuentaDTO.cuenta_t;
    }

    /**
     * @param cuenta_t the cuenta_t to set
     */
    public void setCuenta_t(String cuenta_t) {
        sic.Dominio.Servicios.Contabilidad.EstadoDeCuentaDTO.cuenta_t = cuenta_t;
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
}
