/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Aplicacion.Servicios.Renovacion.Renovacion;

/**
 *
 * @author FANNY BURGOS
 */
public interface IRenovacionDAO {
   Renovacion PersistirRenovacion(Renovacion r);    
   Renovacion ModificarRenovacion(Renovacion r);
   Vector <Renovacion> ObtenerRenovaciones();
   Renovacion ObtenerRenovacion(int año,int mes);
}
