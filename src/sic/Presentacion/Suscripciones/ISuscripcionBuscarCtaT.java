/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.Cta_T.Cta_T;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarCtaT extends ISuscripcion{
   public void EventoDeSeleccionCtaT();
   public void setCta_T(Cta_T cta);    
}
