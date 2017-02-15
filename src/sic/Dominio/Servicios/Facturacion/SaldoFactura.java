/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Facturacion;

/**
 *
 * @author FANNY BURGOS
 */
public class SaldoFactura {
    private String nit="";
    private String usuario="";
    private int factura;
    private double saldo;

    /**
     * @return the factura
     */
    public int getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(int factura) {
        this.factura = factura;
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
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
