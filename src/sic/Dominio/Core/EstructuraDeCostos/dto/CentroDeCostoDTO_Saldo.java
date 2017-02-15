/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos.dto;

import java.text.NumberFormat;

/**
 *
 * @author Usuario1
 */
public class CentroDeCostoDTO_Saldo extends sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO{
    private double value;

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    private String des(){
        String a="";
        for(int i=0;i<this.descripcion.length();i++){
            if(this.descripcion.charAt(i)=='-')
                return a;
            a=a+this.descripcion.charAt(i);
        }
        return a;
    }

    @Override
    public String toString() {
       return "<>"+this.getId_cuenta_t()+"<>"+this.des()+"<>"+NumberFormat.getInstance().format(value)+"<>";
    }

}
