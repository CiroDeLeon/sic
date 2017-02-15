/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Suscripciones;

/**
 *
 * @author Usuario1
 */
public interface ISuscripcionBuscarEstructuraDeCostos extends sic.Presentacion.Suscripciones.ISuscripcion{
   public void EventoDeSeleccionEstructuraDeCostos();
   public void setEstructuraDeCostos(sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos obj); 
}
