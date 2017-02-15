/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Servicios.Dian.TipoDocumento;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarTipoDocumento extends ISuscripcion{
   public void EventoDeSeleccionTipoDocumento();
   public void setTipoDocumento(TipoDocumento td);
}
