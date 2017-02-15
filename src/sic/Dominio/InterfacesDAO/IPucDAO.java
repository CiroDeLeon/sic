/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.PUC.Cta_PUC;

/**
 *
 * @author FANNY BURGOS
 */
public interface IPucDAO {
   Cta_PUC PersistirCtaPuc(Cta_PUC cta);    
   Cta_PUC ObtenerCtaPuc(Object id);
   Vector <Cta_PUC> ObtenerPUC(String busqueda);
   Vector <Cta_PUC> InspeccionarAuxiliar(String busqueda,Object idAuxiliar);
   Vector <Cta_PUC> ObtenerCuentas(Object idctai,Object idctaf);
   Cta_PUC ModificarCtaPuc(Object newid,Cta_PUC cta);
   // Obtiene Cta_puc de una CLASE CONTABLE PUC
   // asignando cuantos digitos deseo Obtener
   // 4 digitos a nivel de cta
   // 6 digitos a nivel de subcuenta
   // 8 digitos a nivel de auxiliar
   Vector <Cta_PUC> ObtenerPUC(int clase,int digito);
   Vector <Cta_PUC> ObtenerCompletoPUC(int digito);
   Vector <Cta_PUC> ObtenerCuentasDeBalance(int digito);
   Vector <Cta_PUC> ObtenerCuentasDeResultado(int digito);
   Vector <Cta_PUC> ObtenerPUC(Object idCtaT);
   boolean ModificarEnPuc(Object idCtaT,Cta_T t);
}
