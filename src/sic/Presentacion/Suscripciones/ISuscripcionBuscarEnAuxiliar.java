/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.PUC.Cta_PUC;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarEnAuxiliar extends ISuscripcion{
   public void EventoDeSeleccionAuxiliar();
   public void setCtaAuxiliar(Cta_PUC cta);        
}
