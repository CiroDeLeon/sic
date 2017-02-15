/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;

/**
 *
 * @author Usuario1
 */
public interface ISuscripcion_BuscarSubcontabilidad extends ISuscripcion{
    public void EventoDeSeleccionDeSubcontabilidad();
    public void setSubcontabilidad(Subcontabilidad subcontabilidad);
}
