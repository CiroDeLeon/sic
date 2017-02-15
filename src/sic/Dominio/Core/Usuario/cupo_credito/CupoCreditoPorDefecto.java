/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Usuario.cupo_credito;

/**
 *
 * @author Usuario1
 */
public class CupoCreditoPorDefecto {
    private static ICupoDeCreditoPorDefecto obj=null;
    private CupoCreditoPorDefecto(){                
    }
    public static ICupoDeCreditoPorDefecto getInstance(){
        if(obj!=null){
           return obj;
        }else{
           obj=new sic.Dominio.Core.Usuario.cupo_credito.CupoCreditoPorDefectoSIC();
           return obj;
        }
    }
    public static void setInterface(ICupoDeCreditoPorDefecto o){
        obj=o;
    }
}
