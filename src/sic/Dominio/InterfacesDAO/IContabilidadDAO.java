/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Date;
import java.util.Vector;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Servicios.Contabilidad.AsientoContableDTO;
import sic.Dominio.Servicios.Contabilidad.EstadoFinancieroDTO;
import sic.Dominio.Servicios.Contabilidad.AsientoPorFechaDTO;
import sic.Dominio.Servicios.Contabilidad.ComprobanteDiarioDTO;
import sic.Dominio.Servicios.Contabilidad.ResumenAuxDTO;

/**
 *
 * @author FANNY BURGOS
 */
public interface IContabilidadDAO {
    // Obtiene Asientos de Un Auxiliar
    Vector <AsientoContableDTO> ObtenerAsientos(String aux,Date fechai,Date fechaf,boolean contabilidad_normal,Subcontabilidad sub);   
    Vector <ResumenAuxDTO> ObtenerResumen(String aux,Date fechainicio,Date fechacorte,boolean contabilidad_normal,Subcontabilidad sub);       
    double ObtenerSaldo(String aux,Date fecha,boolean contabilidad_normal,Subcontabilidad sub);
    double ObtenerSaldo(String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub);
    double ObtenerExistencia(String aux,Date fecha,boolean contabilidad_normal,Subcontabilidad sub);
    boolean isDebito(String cta);
    double ObtenerDebito(String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    double ObtenerCredito(String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    double ObtenerEntradas(String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    double ObtenerSalidas(String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    Vector <AsientoPorFechaDTO> ObtenerMovimientos(Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub);    
    boolean EstaCuadradoElSistema();
    double ObtenerDebitoDeDocumento(String td,String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    double ObtenerCreditoDeDocumento(String td,String aux,Date fechainicio,Date fechafin,boolean contabilidad_normal,Subcontabilidad sub); 
    Vector<sic.Dominio.Servicios.Contabilidad.EstadoDeCuentaDTO> ObtenerInformeEstadoDeCuenta(long idcuenta_t);
    double ObtenerEstadoDeCuenta(long idcuenta_t);
}
