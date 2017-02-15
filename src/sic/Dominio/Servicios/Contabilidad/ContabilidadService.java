/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Contabilidad;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Cta_T.CtaT_Service;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Aplicacion.Servicios.FechaService;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.DocumentoService;
import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Infraestructura.JDBC.Impl.Mysql.ContabilidadDAO;
import sic.Dominio.InterfacesDAO.IContabilidadDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class ContabilidadService {
    // La variable suma es la suma 
    // cuando se llama al metodo transformar
    private double suma=0;
    private IContabilidadDAO dao;   
    public  ContabilidadService() {
        dao=new ContabilidadDAO();
    }
    public  IContabilidadDAO getDao() {
        return dao;
    }    
    public  Vector<EstadoFinancieroDTO> ObtenerEstadoDeResultados_(Date fechacorte,Date fechainicio,int digitos,boolean contabilidad_normal,Subcontabilidad sub){
        Iterator<Cta_PUC> it=new PucService().getDao().ObtenerCuentasDeResultado(digitos).iterator();
        Vector<EstadoFinancieroDTO> lista=new Vector<EstadoFinancieroDTO>();                
        EstadoFinancieroDTO.fechacorte=fechacorte;
        EstadoFinancieroDTO.fechainicio=fechainicio;
        EstadoFinancieroDTO.sumaingresos=this.getDao().ObtenerSaldo("4", fechainicio, fechacorte, contabilidad_normal, sub);
        EstadoFinancieroDTO.sumagastos=this.getDao().ObtenerSaldo("5", fechainicio, fechacorte, contabilidad_normal, sub);
        EstadoFinancieroDTO.sumacostos=this.getDao().ObtenerSaldo("6", fechainicio, fechacorte, contabilidad_normal, sub);
        EstadoFinancieroDTO.sumacostos_produccion=this.getDao().ObtenerSaldo("7", fechainicio, fechacorte, contabilidad_normal, sub);        
        
        EstadoFinancieroDTO.utilidad=EstadoFinancieroDTO.sumaingresos-EstadoFinancieroDTO.sumagastos-EstadoFinancieroDTO.sumacostos-EstadoFinancieroDTO.sumacostos_produccion;
        if(contabilidad_normal){
               EstadoFinancieroDTO.modo="NORMA LOCAL";
            }else{
               EstadoFinancieroDTO.modo="NORMA INTERNACIONAL";
            }
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            EstadoFinancieroDTO dto=new EstadoFinancieroDTO();     
            dto.setCodigocta(cta.getId().toString());
            int n=cta.getId().toString().length();
            String sal="";
            for(int i=1;i<=n/2;i++){
                sal+="   ";
            }
            if(n<3){
               dto.setDenominacion(sal+cta.getDenominacion().toUpperCase());    
            }else{
               dto.setDenominacion(sal+cta.getDenominacion());
            }
            
            dto.setSaldo(this.getDao().ObtenerSaldo(dto.codigocta,fechainicio,fechacorte,contabilidad_normal,sub));            
            if(dto.getSaldo()!=0){
               lista.add(dto);
            }
        }
        return lista;
    }
    public  Vector<EstadoFinancieroDTO> ObtenerBalance_(Date fechacorte,int digitos,boolean contabilidad_normal,Subcontabilidad sub){
        Iterator<Cta_PUC> it=new PucService().getDao().ObtenerCuentasDeBalance(digitos).iterator();
        Vector<EstadoFinancieroDTO> lista=new Vector<EstadoFinancieroDTO>();        
        Date fechainicio=(new FechaService()).getDao().ObtenerMenorFecha();
        
        EstadoFinancieroDTO.sumaactivos=this.getDao().ObtenerSaldo("1",fechacorte,contabilidad_normal,sub);
        EstadoFinancieroDTO.sumapasivos=this.getDao().ObtenerSaldo("2",fechacorte,contabilidad_normal,sub);
        EstadoFinancieroDTO.sumapatrimonio=this.getDao().ObtenerSaldo("3",fechacorte,contabilidad_normal,sub);
        EstadoFinancieroDTO.fechacorte=fechacorte;
        EstadoFinancieroDTO.fechainicio=fechainicio;
        EstadoFinancieroDTO.utilidad=getDao().ObtenerSaldo("4",fechacorte,contabilidad_normal,sub)-getDao().ObtenerSaldo("5",fechacorte,contabilidad_normal,sub)-getDao().ObtenerSaldo("6",fechacorte,contabilidad_normal,sub)-getDao().ObtenerSaldo("7",fechacorte,contabilidad_normal,sub);
        if(contabilidad_normal){
               EstadoFinancieroDTO.modo="NORMA LOCAL";
            }else{
               EstadoFinancieroDTO.modo="NORMA INTERNACIONAL";
            }
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            EstadoFinancieroDTO dto=new EstadoFinancieroDTO();            
            dto.setCodigocta(cta.getId().toString());
            int n=cta.getId().toString().length();
            String sal="";
            for(int i=1;i<=n/2;i++){
                sal+="   ";
            }
            if(n<3){
               dto.setDenominacion(sal+cta.getDenominacion().toUpperCase());    
            }else{
               dto.setDenominacion(sal+cta.getDenominacion());
            }
            dto.setSaldo(this.getDao().ObtenerSaldo(dto.codigocta,fechacorte,contabilidad_normal,sub));            
            if(dto.getSaldo()!=0){
               lista.add(dto);
            }
        }
        return lista;
    }       
    public  Vector<ReporteDeSaldoCtaTDTO> ObtenerSaldos(Object idCtat,Date fechacorte,boolean contabilidad_normal,Subcontabilidad sub){
        PucService ps=new PucService();
        CtaT_Service ts=new CtaT_Service();
        Iterator<Cta_PUC>it=ps.getDao().ObtenerPUC(idCtat).iterator();
        Vector<ReporteDeSaldoCtaTDTO> vr=new Vector<ReporteDeSaldoCtaTDTO>();
        ReporteDeSaldoCtaTDTO.fechacorte=fechacorte;
        ReporteDeSaldoCtaTDTO.idctat=idCtat;
        ReporteDeSaldoCtaTDTO.titulo=ts.getDaot().ObtenerCtaT(idCtat).getDescripcion();
        if(sub!=null){
           ReporteDeSaldoCtaTDTO.subcontabilidad=sub.getId()+"->"+sub.getDescripcion();
        }else{
           ReporteDeSaldoCtaTDTO.subcontabilidad="0->Ninguna"; 
        }
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            ReporteDeSaldoCtaTDTO rs=new ReporteDeSaldoCtaTDTO();
            rs.setSaldo(this.getDao().ObtenerSaldo(cta.getId().toString(),fechacorte,contabilidad_normal,sub));            
               String aux=cta.getId().toString().substring(0,8);            
               rs.setCodigocta(aux);
               System.out.println(aux);
               rs.setDenominacion(ps.ObtenerCtaPuc(aux).getDenominacion());            
               if(contabilidad_normal)
               rs.setModo("NORMA LOCAL");
               else
               rs.setModo("NORMA INTERNACIONAL");    
               vr.add(rs);
            
        }
        return vr;
    }
    public  Vector<EstractoCtaDTO> ObtenerEstractoPorCta(Object idCtaI,Object idCtaF,Date fechainicio,Date fechacorte,boolean contabilidad_normal,Subcontabilidad sub){
        PucService ps=new PucService();
        Iterator <Cta_PUC> it=ps.ObtenerAuxiliaresT(idCtaI,idCtaF).iterator();
        Vector<EstractoCtaDTO> vr=new Vector<EstractoCtaDTO>();
        EstractoCtaDTO.cta=idCtaI.toString();
        EstractoCtaDTO.denominacioncta=ps.ObtenerCtaPuc(idCtaI).getDenominacion();
        EstractoCtaDTO.ctab=idCtaF.toString();
        EstractoCtaDTO.denominacionctab=ps.ObtenerCtaPuc(idCtaF).getDenominacion();
        EstractoCtaDTO.fechainicio=fechainicio;
        EstractoCtaDTO.fechacorte=fechacorte;
        if(sub!=null){
           EstractoCtaDTO.subcontabilidad=sub.getId()+"->"+sub.getDescripcion();
        }else{
           EstractoCtaDTO.subcontabilidad="0->ninguna";    
        }
        PucService pucs=new PucService();
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            EstractoCtaDTO ec=new EstractoCtaDTO();
            Cta_PUC madre=null;
            if(cta.getId().toString().length()>=4){
               madre=pucs.getDao().ObtenerCtaPuc(cta.getId().toString().substring(0,4));
            }
            ec.setCodigocta(cta.getId().toString());
            ec.setDenominacion(cta.getDenominacion());
            ec.setSaldoanterior(this.getDao().ObtenerSaldo(cta.getId().toString(),new java.util.Date(fechainicio.getTime()-86400000),contabilidad_normal,sub));
            ec.setDebito(this.getDao().ObtenerDebito(cta.getId().toString(),fechainicio, fechacorte,contabilidad_normal,sub));
            ec.setCredito(this.getDao().ObtenerCredito(cta.getId().toString(),fechainicio, fechacorte,contabilidad_normal,sub));
            double saldo=this.getDao().ObtenerSaldo(cta.getId().toString(),fechacorte,contabilidad_normal,sub);
            if((cta.isDebito() && madre!=null && madre.isCredito()) || (cta.isCredito() && madre!=null && madre.isDebito())){
                saldo=saldo*-1;
            }
            ec.setSaldo(saldo);               
            if(contabilidad_normal)
            ec.setModo("NORMA LOCAL");
            else
            ec.setModo("NORMA INTERNACIONAL");    
            if(ec.getSaldoanterior()!=0 || ec.getDebito()!=0 || ec.getCredito()!=0 || ec.getSaldo()!=0){
               vr.add(ec);    
            }
        }
        return vr;
    } 
    public  Vector<AnexoDTO> ObtenerAnexos(int clase,boolean detallado,Date fechainicio,Date fechacorte,boolean ClaseDebito,boolean contabilidad_normal,Subcontabilidad sub){
        PucService ps=new PucService();
        Iterator<Cta_PUC> it=ps.getDao().ObtenerPUC(clase,8).iterator();
        AnexoDTO.fechainicio=fechainicio;
        AnexoDTO.fechacorte=fechacorte;
        AnexoDTO.clase=clase;
        if(sub!=null){
        AnexoDTO.subcontabilidad=sub.getId()+"->"+sub.getDescripcion();
        }else{
        AnexoDTO.subcontabilidad="0->ninguna";    
        }                
        AnexoDTO.denominacionclase=ps.ObtenerCtaPuc(""+clase).getDenominacion();
        Vector<AnexoDTO> lista=new Vector<AnexoDTO>();
        this.suma=0;
        while(it.hasNext()){
            Cta_PUC aux=it.next();
            AnexoDTO a=new AnexoDTO();
                   a.setCodigocta(aux.getId().toString());
                   a.setDenominacion(aux.getDenominacion());
                   a.setParcial(0);
                   a.setSaldo(this.getDao().ObtenerSaldo(aux.getId().toString(), fechainicio, fechacorte,contabilidad_normal,sub));                   
                   if(contabilidad_normal){
                     a.setModo("NORMA LOCAL");  
                   }else{
                     a.setModo("NORMA INTERNACIONAL");    
                   }
                   
                   if(a.getSaldo()!=0){
                      if((ClaseDebito && aux.getTipoCta().equals("Debito")) || (ClaseDebito==false && aux.getTipoCta().equals("Credito"))){
                       
                      }else{
                         double s=a.getSaldo(); 
                         a.setSaldo(s*-1);                            
                      } 
                      suma+=a.getSaldo();
                      lista.add(a);            
                      if(detallado){                                             
                         Iterator<Cta_PUC> it2=ps.InspeccionarAuxiliar("",aux.getId()).iterator(); 
                         while(it2.hasNext()){
                            Cta_PUC cta=it2.next();
                            System.out.println(cta.getId()+" faro");
                            AnexoDTO an=new AnexoDTO();
                            an.setCodigocta(cta.getId().toString());
                            an.setDenominacion(cta.getDenominacion());
                            an.setParcial(this.getDao().ObtenerSaldo(cta.getId().toString(), fechainicio, fechacorte,contabilidad_normal,sub));
                            an.setSaldo(0);
                            if(contabilidad_normal){
                               an.setModo("NORMA LOCAL");  
                              }else{
                               an.setModo("NORMA INTERNACIONAL");    
                              }
                            if(an.getParcial()!=0){
                               if((ClaseDebito && cta.getTipoCta().equals("Debito")) || (ClaseDebito==false && cta.getTipoCta().equals("Credito"))){
                       
                               }else{          
                                  double s=an.getParcial(); 
                                  an.setParcial(s*-1);                            
                               }  
                               lista.add(an);
                            }
                          }
                      }
                   }
        }
        return lista;
    }
    public boolean TrasladarDeMuchosAuxiliaresAUnAuxiliarT(Date fecha,Usuario u,TipoDocumentoContable tdc,String Aux,Date fecha_inicial,Date fecha_final,String Aux_T,String actor,boolean isIVA,boolean isRetencion,double porcentaje,boolean contabilidad_normal){
       Iterator<Cta_PUC> it=new PucService().getDao().InspeccionarAuxiliar("",Aux).iterator();
       DocumentoService ds=new DocumentoService();
       Documento d=new Documento();
       d.setAbreviatura(tdc.getAbreviatura());
       d.setTdocumento(tdc.getDescripcion());
       d.setUsuario(u);
       d.setFechaContable(fecha);
       d.setCreador(actor);            
       Vector<Asiento> asientos=new Vector<Asiento>();
       double suma=0;
       while(it.hasNext()){
           Cta_PUC aux_t=it.next();
           double saldo=this.getDao().ObtenerSaldo(aux_t.getId().toString(),fecha_inicial,fecha_final,contabilidad_normal,null);
           if(saldo!=0){
               Asiento a=new Asiento();
               a.setCtaPuc(aux_t);
               if(aux_t.isDebito()){
                  a.setCredito(saldo);
               }               
               if(aux_t.isCredito()){
                   a.setDebito(saldo);
               }
               suma=suma+saldo;
               a.setDetalle("traslado de aux a auxiliar t");
               if(isIVA){
                   double base=saldo*100/porcentaje;
                   a.setBaseIVA(base*-1);
               }
               if(isRetencion){
                   double base=saldo*100/porcentaje;
                   a.setBaseRTF(base*-1);
               }
               asientos.add(a);               
           }
       }
      Asiento a=new Asiento();
      Cta_PUC a_t=new PucService().ObtenerCtaPuc(Aux_T);
               a.setCtaPuc(a_t);
               if(a_t.isDebito()){
                  a.setDebito(suma);
               }               
               if(a_t.isCredito()){
                   a.setCredito(suma);
               }
               if(isIVA){
                   double base=suma*100/porcentaje;
                   a.setBaseIVA(base);
               }
               if(isRetencion){
                   double base=suma*100/porcentaje;
                   a.setBaseRTF(base);
               }
               a.setDetalle("traslado de aux a auxiliar t");
               asientos.add(a);               
               d.setAsientos(asientos);
               if(ds.Guardar(d)){
                   return true;
               }else{
                   return false;
               }
    }    
    public double getSuma() {
        return suma;
    }
    public Vector<BalanceDePruebaDTO> ObtenerBalanceDePrueba(Date fecha_inicial,Date fecha_final,boolean contabilidad_normal,Subcontabilidad sub,int digitos,boolean todos){
        PucService ps=new PucService();
        int d=digitos;
        if(todos==true){
            d++;
        }
        Vector <Cta_PUC> cuentas=ps.getDao().ObtenerCompletoPUC(d);
        Iterator<Cta_PUC> it=cuentas.iterator();
        Vector<BalanceDePruebaDTO> lista=new Vector<BalanceDePruebaDTO>();
        ContabilidadService cs=new ContabilidadService();
        Date anterior=new java.util.Date(fecha_inicial.getYear(),fecha_inicial.getMonth(),fecha_inicial.getDate()-1,23,59,59);
        Date menor=new FechaService().getDao().ObtenerMenorFecha();
        System.out.print(anterior.toLocaleString());
        double sd=0;double sdd=0;
        double sc=0;double scc=0;
        while(it.hasNext()){
           Cta_PUC cta=it.next();           
           BalanceDePruebaDTO bp=new BalanceDePruebaDTO();
           if(contabilidad_normal==true){
              bp.setModo("Norma Local");
           }else{
              bp.setModo("Norma Internacional"); 
           }
           if(sub!=null){
              bp.setSubcontabilidad(sub.getDescripcion());
           }else{
              bp.setSubcontabilidad("");
           }
           bp.setFecha_inicial(fecha_inicial);
           bp.setFecha_final(fecha_final);
           bp.setId_cuenta(cta.getId().toString());
           bp.setDenominacion(cta.getDenominacion());           
           double saldo_anterior=cs.getDao().ObtenerSaldo(cta.getId().toString(),menor,anterior, contabilidad_normal, sub);
           double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),fecha_final,contabilidad_normal, sub);
           bp.setSaldo_anterior(saldo_anterior);
           bp.setDebito(cs.getDao().ObtenerDebito(cta.getId().toString(),fecha_inicial, fecha_final, contabilidad_normal, sub));
           bp.setCredito(cs.getDao().ObtenerCredito(cta.getId().toString(),fecha_inicial, fecha_final, contabilidad_normal, sub));
           bp.setSaldo_actual(saldo);
           System.out.print(bp.getId_cuenta()+"   "+bp.getDebito()+"  "+bp.getCredito());           
           if(saldo!=0 || saldo_anterior!=0 || bp.getDebito()!=0 || bp.getCredito()!=0){
              lista.add(bp);                     
           }   
           sdd+=bp.getDebito();
           scc+=bp.getCredito();
           if((cta.getId().toString().length()==digitos && todos==false) || (cta.getId().toString().length()>digitos && todos==true)){
               System.out.print("XXXXXX");
               sd+=bp.getDebito();
               sc+=bp.getCredito();
           }
            System.out.println();
        } 
          System.out.println();
        System.out.println(sdd+" "+scc);
        BalanceDePruebaDTO.sdebito=sd;
        BalanceDePruebaDTO.scredito=sc;
        return lista;               
    }   
    public Vector<ComprobanteDiarioDTO> ObtenerComprobanteDiario(Date fecha_inicial,Date fecha_final,boolean contabilidad_normal,Subcontabilidad sub,int digitos){
        DocumentoService ds=new DocumentoService();
        PucService ps=new PucService();
        String modo;
        Iterator<sic.Dominio.Core.Documento.otros.TipoDocumentoContable> it;
        Vector<ComprobanteDiarioDTO> lista=new Vector<ComprobanteDiarioDTO>();
        if(contabilidad_normal==true){
           it=ds.getDao().ObtenerTiposDeDocumentos("LOCAL").iterator();
           modo="Norma Local";
        }else{
           it=ds.getDao().ObtenerTiposDeDocumentos("INTERNACIONAL").iterator(); 
           modo="Norma Internacional";
        }
        System.out.println(modo);
        while(it.hasNext()){
            TipoDocumentoContable tdc=it.next();
            Iterator<Cta_PUC> it2=ps.getDao().ObtenerCompletoPUC(digitos).iterator();
            double sd=0;double sc=0;
            int sw=0;
            while(it2.hasNext()){
                Cta_PUC cta=it2.next();
                double debito=this.getDao().ObtenerDebitoDeDocumento(tdc.getDescripcion(),cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub);
                double credito=this.getDao().ObtenerCreditoDeDocumento(tdc.getDescripcion(),cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub);
                if(debito!=0 || credito!=0){
                   sw=1;
                   ComprobanteDiarioDTO dto=new ComprobanteDiarioDTO();
                   dto.setFecha_inicial(fecha_inicial);
                   dto.setFecha_final(fecha_final);
                   dto.setId_cuenta(cta.getId().toString());
                   dto.setDenominacion(cta.getDenominacion());
                   dto.setDebito(debito);
                   dto.setCredito(credito);                   
                   dto.setModo(modo);
                   dto.setTipo_documento(tdc.getDescripcion());
                   if(sub!=null){
                      dto.setSubcontabilidad(sub.getDescripcion());
                   }else{
                      dto.setSubcontabilidad("Ninguna"); 
                   }
                   lista.add(dto);                   
                   sd+=dto.getDebito();
                   sc+=dto.getCredito();
                }
                if(it2.hasNext()==false && sw==1){
                   ComprobanteDiarioDTO dto=new ComprobanteDiarioDTO();
                   dto.setFecha_inicial(fecha_inicial);
                   dto.setFecha_final(fecha_final);
                   dto.setId_cuenta("");
                   dto.setDenominacion("                  Sumas Iguales ----->");
                   dto.setDebito(sd);
                   dto.setCredito(sc);                   
                   dto.setModo(modo);
                   dto.setTipo_documento(tdc.getDescripcion());
                   if(sub!=null){
                      dto.setSubcontabilidad(sub.getDescripcion());
                   }else{
                      dto.setSubcontabilidad("Ninguna"); 
                   }
                   lista.add(dto);
                }
            }
        }
        return lista;
    }
    public Vector<ComprobanteDiarioDTO> ObtenerComprobanteDiarioColumnario(Date fecha_inicial,Date fecha_final,boolean contabilidad_normal,Subcontabilidad sub,int digitos){
        DocumentoService ds=new DocumentoService();
        PucService ps=new PucService();
        String modo;
        Iterator<Cta_PUC> it=ps.getDao().ObtenerCompletoPUC(digitos).iterator();
        Vector<ComprobanteDiarioDTO> lista=new Vector<ComprobanteDiarioDTO>();                
        while(it.hasNext()){
            Cta_PUC cta=it.next();            
            System.out.println(cta.getId());
            Iterator<sic.Dominio.Core.Documento.otros.TipoDocumentoContable> it2;
            double sd=0;double sc=0;
            int sw=0;
            if(contabilidad_normal==true){
              it2=ds.getDao().ObtenerTiposDeDocumentos("LOCAL").iterator();
              modo="Norma Local";
            }else{
              it2=ds.getDao().ObtenerTiposDeDocumentos("INTERNACIONAL").iterator(); 
              modo="Norma Internacional";
            }
            if(cta.getId().toString().length()<=digitos){
               double debito=this.getDao().ObtenerDebito(cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub);
               double credito=this.getDao().ObtenerCredito(cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub); 
               ComprobanteDiarioDTO dto=new ComprobanteDiarioDTO();
                   dto.setFecha_inicial(fecha_inicial);
                   dto.setFecha_final(fecha_final);
                   dto.setId_cuenta(cta.getId().toString());
                   dto.setDenominacion(cta.getDenominacion());
                   dto.setDebito(debito);
                   dto.setCredito(credito);                   
                   dto.setModo(modo);
                   dto.setTipo_documento("");
                   if(sub!=null){
                      dto.setSubcontabilidad(sub.getDescripcion());
                   }else{
                      dto.setSubcontabilidad("Ninguna"); 
                   }
                   if(debito!=0 || credito!=0){
                      lista.add(dto);
                   }
            }
            if(cta.getId().toString().length()==digitos){    
            while(it2.hasNext()){
                TipoDocumentoContable tdc=it2.next();
                double debito=this.getDao().ObtenerDebitoDeDocumento(tdc.getDescripcion(),cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub);
                double credito=this.getDao().ObtenerCreditoDeDocumento(tdc.getDescripcion(),cta.getId().toString(), fecha_inicial, fecha_final, contabilidad_normal, sub);
                if(debito!=0 || credito!=0){
                   sw=1;
                   ComprobanteDiarioDTO dto=new ComprobanteDiarioDTO();
                   dto.setFecha_inicial(fecha_inicial);
                   dto.setFecha_final(fecha_final);
                   dto.setId_cuenta(cta.getId().toString());
                   dto.setDenominacion(cta.getDenominacion());
                   dto.setDebito(debito);
                   dto.setCredito(credito);                   
                   dto.setModo(modo);
                   if(dto.getId_cuenta().length()==digitos){
                      dto.setTipo_documento(tdc.getDescripcion());
                   }else{
                      dto.setTipo_documento(""); 
                   }
                   if(sub!=null){
                      dto.setSubcontabilidad(sub.getDescripcion());
                   }else{
                      dto.setSubcontabilidad("Ninguna"); 
                   }
                   lista.add(dto);                   
                   sd+=dto.getDebito();
                   sc+=dto.getCredito();
                }                      
            }            
            }
        }
        return lista;
    }
    public Vector<sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO> VerCuentasPorPagarOCobrar(boolean por_pagar){
        sic.Dominio.Core.Cta_T.CtaT_Service service =new sic.Dominio.Core.Cta_T.CtaT_Service();
        Iterator<sic.Dominio.Core.Cta_T.Cta_T> it=service.getDaot().ObtenerCtasT("").iterator();
        Vector<sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO> lista=new Vector<sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO>();
        if(por_pagar){
            sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO.setInforme("Cuentas x Pagar");
        }else{
            sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO.setInforme("Cuentas x Cobrar");
        }
        while(it.hasNext()){
            Cta_T cta=it.next();
            sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO dto=new sic.Dominio.Servicios.Contabilidad.CuentasPorPagarOCobrarDTO();
            long id=Long.parseLong(cta.getId().toString());
            dto.setId(id);
            if(cta.getDescripcion().split("-").length==2){               
               dto.setDescripcion(cta.getDescripcion().split("-")[0]);
               dto.setNodocumento(cta.getDescripcion().split("-")[1]);
            }else{
               dto.setDescripcion(cta.getDescripcion());
               dto.setNodocumento("");
            }
            double estado=this.dao.ObtenerEstadoDeCuenta(id);            
            if(por_pagar && estado<0){               
               dto.setValor(estado*-1); 
               lista.add(dto);
            }
            if(!por_pagar && estado>0){
               dto.setValor(estado);  
               lista.add(dto);
            }
            
        }
        return lista;
    }
}
