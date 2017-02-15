/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Servicios.Facturacion.SaldoFactura;

/**
 *
 * @author FANNY BURGOS
 */
public interface IFacturacionDAO {
    Vector <SaldoFactura> ObtenerSaldos(Object idUsuario,String busqueda);
    double ObtenerSaldoFactura(int numeracionfactura);
}
