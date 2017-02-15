/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.Documento.herramientas;

import java.util.Vector;
import sic.Dominio.Core.Documento.Asiento;
import sic.Presentacion.Suscripciones.ISuscripcion;

/**
 *
 * @author Usuario1
 */
public interface YoUsoTrasladoMultiple extends ISuscripcion{
    public void AsignarAsientosDeAuxiliar(Vector<Asiento> asientos);
}
