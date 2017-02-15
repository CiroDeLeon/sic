/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Documento;

import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import sic.Aplicacion.Servicios.Reportes.ReporteService;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.InterfacesDAO.IDocumentoDAO;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;
import sic.Infraestructura.JDBC.Impl.Mysql.DocumentoDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class DocumentoService {
   private IDocumentoDAO dao;
   
   protected String mensaje="";
   public DocumentoService() {
        dao=new DocumentoDAO();        
    }
   public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal_NormaInternacional(){
       return getDao().ObtenerTiposDeDocumentosContablesNormaLocal_NormaInternacional();
   }
   public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaInternacional(){
       return getDao().ObtenerTiposDeDocumentosContablesNormaInternacional();
   }
   public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal(){
       return getDao().ObtenerTiposDeDocumentosContablesNormaLocal();
   }
   public boolean Guardar(Documento d){
       long id=getDao().ObtenerIdUltimoDocumento()+1;
       d.setId(new Long(id).toString());
       int n=getDao().ObtenerNumeracion(d.getTdocumento());
       if(n!=0){          
          d.setNumeracion(n+1);
       }else{
          d.setNumeracion(1); 
       }
        if(getDao().PersistirDocumento(d)!=null){
            this.mensaje="Ingresado con Exito";
            Iterator <Asiento> it=d.getAsientos().iterator();
            int sw=0;
            while(it.hasNext()){
                Asiento a=it.next();
                a.setDocumento(d);
                dao.PersistirAsiento(a);
                if(a==null){
                    sw=1;
                }
            }
            if(sw==1){
                this.mensaje="Se Creo Con Errores";
            }
            return true;
        }
       return false;
   }
   public String ObtenerMensaje(){
       return this.getMensaje();
   }
   public IDocumentoDAO getDao() {
        return dao;
    }
   public boolean HaySumasIguales(Vector<Asiento> asientos){
       Iterator <Asiento> it=asientos.iterator();
       double sumadebito=0;
       double sumacredito=0;
       while(it.hasNext()){
           Asiento a=it.next();
           sumadebito+=a.getDebito();
           sumacredito+=a.getCredito();
       }
       if(sumadebito==sumacredito){
           return true;   
       }
       return false;
   } 
   public double ObtenerSumaDebitos(Vector<Asiento> asientos){
       Iterator <Asiento> it=asientos.iterator();
       double sumadebito=0;       
       while(it.hasNext()){
           Asiento a=it.next();
           sumadebito+=a.getDebito();           
       }
       return sumadebito;       
   }
   public double ObtenerSumaCreditos(Vector<Asiento> asientos){
       Iterator <Asiento> it=asientos.iterator();
       double sumacredito=0;       
       while(it.hasNext()){
           Asiento a=it.next();
           sumacredito+=a.getCredito();           
       }
       return sumacredito; 
   }   
   public Documento ObtenerDocumento(String tipodocumento, int numeracion){
      Documento d=dao.ObtenerDocumento(tipodocumento, numeracion);
      if(d!=null)
          d.setAsientos(dao.ObtenerAsientos(d.getId()));
      return d;
   }
   public Documento ObtenerDocumento(Object idDocumento){
      Documento d=dao.ObtenerDocumento(idDocumento);
      if(d!=null)
          d.setAsientos(dao.ObtenerAsientos(d.getId()));
      return d;
   }
   public boolean Modificar(Documento d){
       if(dao.ModificarDocumento(d.getId(), d)){
       Iterator <Asiento> it=d.getAsientos().iterator();       
       while(it.hasNext()){           
           Asiento a=it.next();
           a.setDocumento(d);
           if(a.getId().equals("0")==false){
           if(dao.ModificarAsiento(a.getId(), a)==false){
               this.mensaje="Error al Modificar un Asiento";
               return false;
           }
           }else{
               a.setDocumento(d);
               if(dao.PersistirAsiento(a)==null){
                   this.mensaje="Error al Modificar un Asiento,No se Pudo Anexar";
                   return false;
               }
           }
       }
       this.mensaje="Moficado con Exito";
       return true;
       }
       return false;
   }
   public void OrdenarDocumentos(String tipodocumento){
       Vector<Documento> lista=dao.ObtenerDocumentos(tipodocumento);
       Collections.sort(lista);
       Iterator<Documento>it=lista.iterator();
       System.out.println(lista.size());
       int i=1;
       while(it.hasNext()){
           Documento d=it.next();
           System.out.println(""+d.getFechaContable());
           d.setNumeracion(i);
           this.dao.ModificarDocumento(d.getId(),d);
           i++;           
       }
   }
   public String ObtenerUltimoId(String tipo_de_documento){
       int ultima_numeracion=this.getDao().ObtenerNumeracion(tipo_de_documento);
       return ""+this.getDao().ObtenerDocumento(tipo_de_documento, ultima_numeracion).getId();
   }
   public void Imprimir(String tipo_documento,Date fecha_inicial,Date fecha_final){
       Iterator<Integer> it=dao.ObtenerColeccionNumeracion(tipo_documento, fecha_inicial, fecha_final).iterator();
       while(it.hasNext()){           
           ReporteService.Imprimir("DocumentoContable.jasper",dao.ObtenerReporteDocumento(tipo_documento,it.next().intValue()));
       }
   }
   //   state=0 suma de debitos
   //   state=1 suma de creditos
   //   state=2 saldo 
   
   public Vector<Asiento> ObtenerAsientosDeTraslado(Object auxiliar,Date fecha_inicial,Date fecha_final,int state,boolean contabilidad_normal){
       ContabilidadService cs=new ContabilidadService();
       PucService ps=new PucService();
       Iterator <Cta_PUC> it=ps.InspeccionarAuxiliar("",auxiliar).iterator();
       Vector<Asiento> asientos=new Vector<Asiento>(); 
       while(it.hasNext()){
           Cta_PUC cta=it.next();
           Asiento a=new Asiento();
           a.setCtaPuc(cta);
           a.setDetalle("");
           double valor=0;
           if(state==0){
               valor=cs.getDao().ObtenerDebito(cta.getId().toString(),fecha_inicial,fecha_final,contabilidad_normal,null);
           }
           if(state==1){
               valor=cs.getDao().ObtenerCredito(cta.getId().toString(),fecha_inicial,fecha_final,contabilidad_normal,null);
           }
           if(state==2){
               valor=cs.getDao().ObtenerSaldo(cta.getId().toString(),fecha_inicial, fecha_final,contabilidad_normal,null);
           }
           if(cta.isDebito()){
               a.setCredito(valor);
           }
           if(cta.isCredito()){
               a.setDebito(valor);
           }
           if(a.getDebito()!=0 || a.getCredito()!=0)
              asientos.add(a);
       }
       return asientos;
   }
   public void GenerarCierre(Date fecha_corte,Usuario u,boolean contabilidad_normal){       
       Documento d=new Documento();       
       d.setActivo(true);
       d.setFechaContable(fecha_corte);
       d.setUsuario(u);
       if(contabilidad_normal==false){
           d.setAbreviatura("ZN");
           d.setTdocumento("Cierre Niif");
           d.setNorma_internacional(true);
           d.setNorma_local(false);
       }else{
           d.setAbreviatura("ZL");
           d.setTdocumento("Cierre Local");
           d.setNorma_internacional(false);
           d.setNorma_local(true);
       }
       Vector<Asiento> asientos=this.ObtenerAsientosDeCierre(4,fecha_corte,contabilidad_normal);
       asientos.addAll(this.ObtenerAsientosDeCierre(5, fecha_corte,contabilidad_normal));
       asientos.addAll(this.ObtenerAsientosDeCierre(6, fecha_corte,contabilidad_normal));
       asientos.addAll(this.ObtenerAsientosDeCierre(7, fecha_corte,contabilidad_normal));
       d.setAsientos(asientos);       
       this.Guardar(d);
   }    
   private Vector<Asiento> ObtenerAsientosDeCierre(int clase,Date fecha_corte,boolean contabilidad_normal){
       Vector<Asiento> lista=new Vector();
       PucService ps=new PucService();
       ContabilidadService cs=new ContabilidadService();       
       Iterator <Cta_PUC> it=ps.getDao().ObtenerPUC(clase,8).iterator();       
       while(it.hasNext()){
           Cta_PUC auxiliar=it.next();
           Iterator <Cta_PUC> it2=ps.InspeccionarAuxiliar("",auxiliar.getId()).iterator();
           while(it2.hasNext()){
               Cta_PUC cta=it2.next();
               double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),fecha_corte,contabilidad_normal,null);
               Asiento a=new Asiento();
               a.setCtaPuc(cta);
               if(saldo!=0){
                   if(cta.isCredito()){
                       a.setDebito(saldo);
                   }
                   if(cta.isDebito()){
                       a.setCredito(saldo);
                   }
                   a.setDetalle("CIERRE ANUAL");
                   lista.add(a);
               }               
           }
       }       
       return lista;
   }
    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
