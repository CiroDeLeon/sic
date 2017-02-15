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
public class CupoCreditoPorDefectoSIC implements ICupoDeCreditoPorDefecto{

    @Override
    public double getCupoDeCreditoPorDefecto() {
       return 10000000000000.0;
    }
    
}
