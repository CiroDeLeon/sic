/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Suscripciones;

import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;

/**
 *
 * @author Usuario1
 */
public interface ISuscripcionBuscarCentroDeCosto extends ISuscripcion{
    public void EventoDeSeleccionDeCentroDeCosto();
    public void setCentroDeCosto(CentroDeCosto obj);
}
