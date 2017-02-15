/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.GeneradorDeInformes;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;

/**
 *
 * @author Usuario1
 */
public class InformeService {
    private sic.Dominio.InterfacesDAO.Interface_InformeDao dao;    
    public InformeService() {
        dao=new sic.Infraestructura.JDBC.Impl.Mysql.InformeDAO();
    }
    public String ObtenerCuentas(long idConsepto){
        Iterator<ConseptoEnCuenta> it=dao.ObtenerCuentas(idConsepto).iterator();
        String sal="";
        while(it.hasNext()){
            ConseptoEnCuenta c=it.next();
            if(c.isSuma()){
               sal=sal+"+"+c.getId_cuenta()+",";
            }else{
               sal=sal+"-"+c.getId_cuenta()+","; 
            }
        }
        if(sal.length()>0){
           return sal.substring(0,sal.length()-1);
        }else{
           return "";
        }
    }
    public void AsignarConseptosEnCuenta(Consepto con,Vector <ConseptoEnCuenta> cuentas){
        Iterator<ConseptoEnCuenta> it=cuentas.iterator();
        while(it.hasNext()){
            ConseptoEnCuenta c=it.next();
            c.setId(0);
            c.setConsepto(con);            
            dao.PersistirConseptoEnCuenta(c);
        }
    }
    public Vector<ReporteDeInformeDinamico> ObtenerReporte(Informe i,Date fecha_inicial,Date fecha_corte,int idioma){
       Locale local = new Locale("co");
       NumberFormat nf=NumberFormat.getInstance(local);
       Iterator<Consepto> it=this.getDao().ObtenerConseptos(i.getId()).iterator();
       Vector<ReporteDeInformeDinamico> lista=new Vector<ReporteDeInformeDinamico>();
       ReporteDeInformeDinamico.titulo=i.getDescripcion();       
       Vector<Columna> columnas=dao.ObtenerColumnas(i.getId());
       while(it.hasNext()){
           Consepto c=it.next();
           ReporteDeInformeDinamico rep=new ReporteDeInformeDinamico();
           rep.setFecha_final(fecha_corte);
           rep.setFecha_inicial(fecha_inicial);
           if(idioma==0){
              rep.setConsepto(c.getDescripcion());
           }
           if(idioma==1){
               rep.setConsepto(c.getDescripcion_idioma_1());
           }
           if(idioma==2){
               rep.setConsepto(c.getDescripcion_idioma_2());
           }
           if(idioma==3){
               rep.setConsepto(c.getDescripcion_idioma_3());
           }           
           Columna col=this.dao.ObtenerColumnas(i.getId()).get(2);
           if(col!=null){
              rep.setColumna_3(col.getDescripcion());
              double val=this.ObtenerValor(c, i, fecha_inicial, fecha_corte,col.isLocal());
              if(val!=0){
                 rep.setValor_3(nf.format(val));              
              }else{
                 int n=this.dao.ObtenerCuentas(c.getId()).size();
                 if(n>0){
                    rep.setValor_3(nf.format(0)); 
                 }else{
                    rep.setValor_3("");
                 }
              }
           }           
           lista.add(rep);
       }
       return lista; 
    }    
    public double ObtenerValor(Consepto c,Informe i,Date fecha_i,Date fecha_f,boolean norma_local){
        ContabilidadService cs=new ContabilidadService();
        Iterator<ConseptoEnCuenta> it=this.dao.ObtenerCuentas(c.getId()).iterator();
        double s=0;
        while(it.hasNext()){
            ConseptoEnCuenta cc=it.next();
            if(cc.getTipo().equals("Saldo")){
                if(cc.isSuma()){
                   s=s+cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_f,norma_local,null);
                }else{
                   s=s-cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_f,norma_local,null);
                }
            }
            if(cc.getTipo().equals("Suma Debitos")){
                if(cc.isSuma()){
                   s=s+cs.getDao().ObtenerDebito(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }else{
                   s=s-cs.getDao().ObtenerDebito(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }
            }
            if(cc.getTipo().equals("Suma Creditos")){
                if(cc.isSuma()){
                   s=s+cs.getDao().ObtenerCredito(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }else{
                   s=s-cs.getDao().ObtenerCredito(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }
            }
            if(cc.getTipo().equals("Saldo Anterior")){
                long dia=60*60*24*1000;
                Date f=new Date(fecha_i.getTime()-dia);
                f.setHours(23);
                if(cc.isSuma()){
                   s=s+cs.getDao().ObtenerSaldo(cc.getId_cuenta(),f,norma_local,null);
                }else{
                   s=s-cs.getDao().ObtenerSaldo(cc.getId_cuenta(),f,norma_local,null);
                }
            }
            if(cc.getTipo().equals("Saldo Entre Fechas")){
                if(cc.isSuma()){
                   s=s+cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }else{
                   s=s-cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_i,fecha_f,norma_local,null);
                }
            }
            if(cc.getTipo().equals("Variacion Entre Fechas")){                 
                long dia=60*60*24*1000;
                Date f=new Date(fecha_i.getTime()-dia);
                f.setHours(23);
                if(cc.isSuma()){
                   s=s+(cs.getDao().ObtenerSaldo(cc.getId_cuenta(),f,norma_local,null)-cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_f,norma_local,null));
                }else{
                   s=s-(cs.getDao().ObtenerSaldo(cc.getId_cuenta(),f,norma_local,null)-cs.getDao().ObtenerSaldo(cc.getId_cuenta(),fecha_f,norma_local,null));
                }
            }            
        }
        return s;
    }
    public sic.Dominio.InterfacesDAO.Interface_InformeDao getDao() {
        return dao;
    }
    
}
