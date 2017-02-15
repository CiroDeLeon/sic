/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Servicios.Dian.Municipio;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarMunicipio extends ISuscripcion{
    public void EventoDeSeleccionMunicipio();
   public void setMunicipio(Municipio m);
}
