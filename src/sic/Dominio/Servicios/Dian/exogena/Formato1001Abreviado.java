/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Servicios.Dian.exogena;

/**
 *
 * @author Usuario1
 */
public class Formato1001Abreviado {
    private Object id_cuenta_t;
    private String descripcion;
    private String id_cuenta;
    private double pagado;

    /**
     * @return the id_cuenta_t
     */
    public Object getId_cuenta_t() {
        return id_cuenta_t;
    }

    /**
     * @param id_cuenta_t the id_cuenta_t to set
     */
    public void setId_cuenta_t(Object id_cuenta_t) {
        this.id_cuenta_t = id_cuenta_t;
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
     * @return the pagado
     */
    public double getPagado() {
        return pagado;
    }

    /**
     * @param pagado the pagado to set
     */
    public void setPagado(double pagado) {
        this.pagado = pagado;
    }
}
