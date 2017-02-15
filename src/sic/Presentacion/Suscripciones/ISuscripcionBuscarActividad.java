/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Servicios.Dian.Actividad;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarActividad extends ISuscripcion{
    void setActividad(Actividad a);
    void EventoAlSeleccionarActividad();
}
