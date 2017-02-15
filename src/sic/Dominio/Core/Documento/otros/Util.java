/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Documento.otros;

import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Documento.Asiento;

/**
 *
 * @author Usuario1
 */
public class Util {
    public static double ObtenerSumaDebitos(Vector<Asiento> lista){
       Iterator<Asiento> it=lista.iterator();
       double suma=0;
       while(it.hasNext()){
           Asiento a=it.next();
           suma+=a.getDebito();
       }
       return suma;
   }
   public static double ObtenerSumaCreditos(Vector<Asiento> lista){
       Iterator<Asiento> it=lista.iterator();
       double suma=0;
       while(it.hasNext()){
           Asiento a=it.next();
           suma+=a.getCredito();
       }
       return suma;
   }
}
