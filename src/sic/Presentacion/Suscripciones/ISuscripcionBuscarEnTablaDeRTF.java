/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Servicios.Dian.RetencionDian;



/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarEnTablaDeRTF extends ISuscripcion{
void EventoSeleccionDeTablaRTF();    
void setRetenciondian(RetencionDian rd);
}
