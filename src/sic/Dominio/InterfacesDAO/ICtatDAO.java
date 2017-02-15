/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Core.Cta_T.Cta_T;

/**
 *
 * @author FANNY BURGOS
 */
public interface ICtatDAO {
   Cta_T PersistirCtaT(Cta_T t);    
   Cta_T ObtenerCtaT(String descripcion);
   Vector<Cta_T> ObtenerCtasT(String busqueda);
   Cta_T ObtenerCtaT(Object idctat);
   Cta_T ModificarCtaT(Object id,Cta_T cta);
   long ObtenerIdUltimaCtaT();
}
