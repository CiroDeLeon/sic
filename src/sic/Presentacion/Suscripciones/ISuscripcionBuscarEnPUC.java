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
public interface ISuscripcionBuscarEnPUC extends ISuscripcion{
   public void EventoDeSeleccionCtaEnPUC();
   public void setCta_PUC(Cta_PUC cta);    
}
