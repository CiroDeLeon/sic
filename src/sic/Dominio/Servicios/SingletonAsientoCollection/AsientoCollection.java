/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.SingletonAsientoCollection;

import java.util.Vector;
import sic.Dominio.Core.Documento.Asiento;

/**
 *
 * @author FANNY BURGOS
 */
public class AsientoCollection {
 private Vector <Asiento> asientos;


    public AsientoCollection() {
        asientos=new Vector <Asiento>();
    }

    /**
     * @return the asientos
     */
    public Vector <Asiento> getAsientos() {
        System.out.println(""+asientos.size());
        return asientos;
    }

    /**
     * @param asientos the asientos to set
     */
    public void setAsientos(Vector <Asiento> asientos) {
        System.out.println(""+asientos.size());
        this.asientos = asientos;
    }
}
