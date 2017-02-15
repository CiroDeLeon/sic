/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Date;
import java.util.Vector;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.Reportes.DocumentoRep;
import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;

/**
 *
 * @author FANNY BURGOS
 */
public interface IDocumentoDAO {
 Documento PersistirDocumento(Documento documento);    
 long ObtenerIdUltimoDocumento();
 Asiento PersistirAsiento(Asiento asiento);
 int ObtenerNumeracion(String tipodocumento,int resolucion);
 int ObtenerNumeracion(String tipodocumento);
 boolean AnularDocumento(Object idDocumento,String anulador,String razon);
 boolean ModificarDocumento(Object idDocumento,Documento documento);
 boolean ModificarAsiento(Object idAsiento,Asiento asiento);
 Vector <TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal_NormaInternacional();
 Vector <TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaInternacional();
 Vector <TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal();
 Vector <DocumentoRep> ObtenerReporteDocumento(String tipodocumento,int numeracion);
 Documento ObtenerDocumento(String tipodocumento,int numeracion);
 Documento ObtenerDocumento(Object idDocumento);
 Documento ObtenerUltimoDocumentoActivo(String tipo_documento);
 Vector <Asiento> ObtenerAsientos(Object idDocumento);
 Documento ObtenerDocumentoDescuadrado();
 Vector<Documento> ObtenerDocumentos(String tipodocumento);
 boolean EliminarAsientos(Object idDocumento);
 Vector<Integer> ObtenerColeccionNumeracion(String tipodocumento,Date fecha_inicial,Date fecha_final);
 boolean ExisteDocumentoConSubcontabilidad(long idSubcontabilidad);
 boolean ExisteAsientoConCentroDeCosto(long idCentroDeCosto);
 // modo {LOCAL , INTERNACIONAL}
 Vector <TipoDocumentoContable> ObtenerTiposDeDocumentos(String modo);
}
