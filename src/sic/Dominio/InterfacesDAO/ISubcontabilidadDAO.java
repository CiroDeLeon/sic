/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;

/**
 *
 * @author Usuario1
 */
public interface ISubcontabilidadDAO {
    void Persistir(Subcontabilidad sub);
    void Modificar(Subcontabilidad sub);
    Subcontabilidad Obtener(long id);
    Vector<Subcontabilidad> Buscar(String busqueda);
    Vector<Subcontabilidad> ObtenerHijos(long id);
}
