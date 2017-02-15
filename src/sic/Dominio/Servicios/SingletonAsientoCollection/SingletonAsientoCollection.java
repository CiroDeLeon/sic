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
public class SingletonAsientoCollection {
    static SingletonAsientoCollection instancia=null;
    AsientoCollection  collection;

    private SingletonAsientoCollection() {
        collection=new AsientoCollection();
    }
    public static SingletonAsientoCollection getInstance(){
        if(SingletonAsientoCollection.instancia==null){
            SingletonAsientoCollection.instancia=new SingletonAsientoCollection();            
        }
        return SingletonAsientoCollection.instancia;
    }
    public Vector <Asiento> getAsientos() {
        return collection.getAsientos();
    }

    /**
     * @param asientos the asientos to set
     */
    public void setAsientos(Vector <Asiento> asientos) {
        this.collection.setAsientos(asientos);
    }        
}
