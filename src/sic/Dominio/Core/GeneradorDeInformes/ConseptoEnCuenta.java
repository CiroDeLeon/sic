/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.GeneradorDeInformes;

/**
 *
 * @author Usuario1
 */
public class ConseptoEnCuenta {
    private long id;
    private String id_cuenta;
    private Consepto consepto;
    private boolean suma;
    private String tipo;

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
     * @return the consepto
     */
    public Consepto getConsepto() {
        return consepto;
    }

    /**
     * @param consepto the consepto to set
     */
    public void setConsepto(Consepto consepto) {
        this.consepto = consepto;
    }

    /**
     * @return the suma
     */
    public boolean isSuma() {
        return suma;
    }

    /**
     * @param suma the suma to set
     */
    public void setSuma(boolean suma) {
        this.suma = suma;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
