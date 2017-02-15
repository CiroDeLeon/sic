/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.GestionDeInformes;

import sic.Dominio.Core.GeneradorDeInformes.Informe;

/**
 *
 * @author Usuario1
 */
public interface ISuscripcionBuscarInforme extends sic.Presentacion.Suscripciones.ISuscripcion{
    public void EventoDeSeleccionDeInforme();
    public void setInforme(Informe informe);    
}
