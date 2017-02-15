/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Core.Activo_Fijo.ActivoFijo;

/**
 *
 * @author Usuario1
 */
public interface IActivoFijoDAO {
   void Persistir(ActivoFijo a);
   void Modificar(long id,ActivoFijo a);
   Vector<ActivoFijo> Listar(String busqueda);       
   ActivoFijo ObtenerUltimo();
   ActivoFijo Obtener(long id);
   ActivoFijo Obtener(Object id_cuenta_t);
}
