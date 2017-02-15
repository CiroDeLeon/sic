/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.Activo_Fijo.ActivoFijo;

/**
 *
 * @author Usuario1
 */
public interface ISuscripcionBuscarActivoFijo extends ISuscripcion{
   public void EventoDeSeleccionDeActivoFijo();
   public void setActivoFijo(ActivoFijo af);     
}
