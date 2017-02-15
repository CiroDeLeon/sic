/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.Usuario.Usuario;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarUsuario extends ISuscripcion{
   public void EventoDeSeleccionUsuario();
   public void setUsuario(Usuario u);
}
