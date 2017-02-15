/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Date;
import java.util.Vector;
import sic.Dominio.Servicios.Dian.Actividad;
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Servicios.Dian.RetencionDian;
import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Servicios.Dian.exogena.Formato1001Abreviado;
import sic.Dominio.Servicios.Dian.exogena.helper.Formato1001Helper;

/**
 *
 * @author FANNY BURGOS
 */
public interface IDianDAO {
   Vector <RetencionDian> ObtenerTablaDeRetenciones(int año);
   RetencionDian ObtenerRetencionDianPorId(Object id);
   RetencionDian AgregarRegistroEnTablaDeRetenciones(RetencionDian rd);
   RetencionDian ModificarRegistroEnTablaDeRetenciones(Object id,RetencionDian rd);
   RetencionDian ObtenerRetencionDian(Object idAuxiliar);
   Actividad PersistirActividad(Actividad actividad);
   Vector<Actividad> ObtenerActividades(String busqueda);
   Actividad ObtenerActividad(int idactividad);
   Municipio PersistirMunicipio(Municipio municipio);
   Municipio ObtenerMunicipio(Object idMunicipio);
   Vector<Municipio> ObtenerMunicipios(String busqueda);
   TipoDocumento PersistirTipoDocumento(TipoDocumento tipodocumento);
   TipoDocumento ObtenerTipoDocumento(Object idTipoDocumento);
   TipoDocumento ObtenerTipoDocumento(String abreviatura);
   Vector<TipoDocumento> ObtenerTipoDocumentos(String busqueda);   
   Vector<Formato1001Abreviado> ObtenerFormato(Date fecha_inicial,Date fecha_final);
   Vector<Formato1001Helper> ObtenerFormato1001Helper(Date fecha_inicial,Date fecha_final);
}
