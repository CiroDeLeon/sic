/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.Suscripciones;

import sic.Dominio.Servicios.Facturacion.SaldoFactura;

/**
 *
 * @author FANNY BURGOS
 */
public interface ISuscripcionBuscarFacturaDeUsuario extends ISuscripcion{
   public void EventoDeSeleccionSaldoFactura();
   public void setSaldoFactura(SaldoFactura sa);    
}
