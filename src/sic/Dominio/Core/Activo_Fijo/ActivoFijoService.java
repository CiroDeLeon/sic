/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Activo_Fijo;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import sic.Aplicacion.Servicios.FechaService;
import sic.Dominio.Core.Activo_Fijo.dto.Registro_De_Deterioro;
import sic.Dominio.Core.Cta_T.CtaT_Service;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.DocumentoService;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;

/**
 *
 * @author Usuario1
 */
public class ActivoFijoService {
    public static String TipoDocumentoDepreciacionLocal="Depreciacion Local";
    public static String AbreviaturaDepreciacionLocal="DL";
    public static String TipoDocumentoDepreciacionNiif="Depreciacion Niif";
    public static String AbreviaturaDepreciacionNiif="DN";
    private sic.Dominio.InterfacesDAO.IActivoFijoDAO dao;    

    public ActivoFijoService() {
        dao=new sic.Infraestructura.JDBC.Impl.Mysql.ActivoFijoDAO();
    }
    public void Guardar(ActivoFijo a){
        long id=getDao().ObtenerUltimo().getId();
        a.setId(id+1);
        if(a.getCta_t()==null){
            CtaT_Service cs=new CtaT_Service();
            cs.InsertarCtaT(a.getDescripcion()+"-"+a.getId());
            Cta_T cta_t=cs.ObtenerCtaT(a.getDescripcion()+"-"+a.getId());
            a.setCta_t(cta_t);
        }
        getDao().Persistir(a);
        PucService ps=new PucService();        
        ps.IngresarDentroDeUnAuxiliar(a.getAux_activo_fijo(),a.getCta_t().getId());
        ps.IngresarDentroDeUnAuxiliar(a.getAux_activo_depreciacion(),a.getCta_t().getId());
        ps.IngresarDentroDeUnAuxiliar(a.getAux_gasto_depreciacion(),a.getCta_t().getId());
    }
    public void Modificar(ActivoFijo a){
        ActivoFijo ant=this.dao.Obtener(a.getId());
        if(ant.getDescripcion().equals(a.getDescripcion())==false){
            Cta_T t=a.getCta_t();
            t.setDescripcion(a.getDescripcion()+"-"+a.getId());
            a.setCta_t(t);
            new sic.Dominio.Core.Cta_T.CtaT_Service().ModificarCtaT(ant.getCta_t().getId(),a.getCta_t());                                
        }
        this.dao.Modificar(a.getId(),a);
        PucService ps=new PucService();        
        ps.IngresarDentroDeUnAuxiliar(a.getAux_activo_fijo(),a.getCta_t().getId());
        ps.IngresarDentroDeUnAuxiliar(a.getAux_activo_depreciacion(),a.getCta_t().getId());
        ps.IngresarDentroDeUnAuxiliar(a.getAux_gasto_depreciacion(),a.getCta_t().getId());
    }
    public void Anular(int n){
        PucService ps=new PucService();
        DocumentoService ds=new DocumentoService();
        Documento d=ds.getDao().ObtenerDocumento(ActivoFijoService.TipoDocumentoDepreciacionLocal,n);
        ds.getDao().AnularDocumento(d.getId(),"","");
        Documento b=ds.getDao().ObtenerDocumento(ActivoFijoService.TipoDocumentoDepreciacionNiif,n);
        ds.getDao().AnularDocumento(b.getId(),"","");
        Iterator<Asiento> it=ds.getDao().ObtenerAsientos(d.getId()).iterator();
        while(it.hasNext()){
            Asiento a=it.next();            
            String id_cuenta_t=a.getCtaPuc().getId().toString().substring(8,a.getCtaPuc().getId().toString().length());
            System.out.println(id_cuenta_t);
            ActivoFijo af=this.dao.Obtener(id_cuenta_t);
            Documento ultimo=ds.getDao().ObtenerUltimoDocumentoActivo(ActivoFijoService.TipoDocumentoDepreciacionLocal);
            if(ultimo!=null){
               af.setFecha_ultima_depreciacion(ultimo.getFechaContable());               
            }else{
               af.setFecha_ultima_depreciacion(af.getFecha_adquisicion());
            }
            this.dao.Modificar(af.getId(),af);
        }
    }
    public void Procesar(Vector<Registro_De_Deterioro> lista,Usuario usuario,Date fecha,double tasa){
       Iterator<Registro_De_Deterioro> it=this.ObtenerReporte(fecha,tasa).iterator();
       DocumentoService ds=new DocumentoService();       
       PucService ps=new PucService();
       // Para Norma Local       
       Documento dl=new Documento();
       dl.setUsuario(usuario);
       dl.setFechaContable(fecha);
       dl.setNorma_internacional(false);
       dl.setNorma_local(true);
       dl.setTdocumento(ActivoFijoService.TipoDocumentoDepreciacionLocal);
       dl.setAbreviatura(ActivoFijoService.AbreviaturaDepreciacionLocal);
       dl.setActivo(true);
       dl.setResumen("depreciacion de activo fijo");
       dl.setNumeracion(ds.getDao().ObtenerNumeracion(dl.getTdocumento())+1);
       dl.setId(""+(ds.getDao().ObtenerIdUltimoDocumento()+1));
       ds.getDao().PersistirDocumento(dl);
       // para Niif
       Documento dn=new Documento();
       dn.setUsuario(usuario);
       dn.setFechaContable(fecha);
       dn.setNorma_internacional(true);
       dn.setNorma_local(false);
       dn.setTdocumento(ActivoFijoService.TipoDocumentoDepreciacionNiif);
       dn.setAbreviatura(ActivoFijoService.AbreviaturaDepreciacionNiif);
       dn.setActivo(true);
       dn.setResumen("depreciacion de activo fijo");
       dn.setNumeracion(ds.getDao().ObtenerNumeracion(dn.getTdocumento())+1);
       dn.setId(""+(ds.getDao().ObtenerIdUltimoDocumento()+1));
       ds.getDao().PersistirDocumento(dn);       
       while(it.hasNext()){
           Registro_De_Deterioro r=it.next();
           ActivoFijo af=this.getDao().Obtener(r.getId());
           // Norma LOCAL
           if(af.isDepreciacion_local_activada() && r.getValor_deterioro_local()>0){
           Cta_PUC cta=ps.getDao().ObtenerCtaPuc(af.getAux_activo_depreciacion()+""+af.getCta_t().getId());
           if(cta==null){
               cta=ps.IngresarDentroDeUnAuxiliar(af.getAux_activo_depreciacion(),af.getCta_t().getId());
           }
           Asiento a=new Asiento();
           a.setDocumento(dl);
           a.setCtaPuc(cta);
           a.setDetalle("Asiento Depreciacion ");
           a.setCredito(Math.round(r.getValor_deterioro_local()));
           ds.getDao().PersistirAsiento(a);
           cta=ps.getDao().ObtenerCtaPuc(af.getAux_gasto_depreciacion()+""+af.getCta_t().getId());
           if(cta==null){
               cta=ps.IngresarDentroDeUnAuxiliar(af.getAux_gasto_depreciacion(),af.getCta_t().getId());
           }
           a=new Asiento();
           a.setDocumento(dl);
           a.setCtaPuc(cta);
           a.setDetalle("Asiento Gasto Depreciacion ");
           a.setDebito(Math.round(r.getValor_deterioro_local()));
           ds.getDao().PersistirAsiento(a);
           }
           // Norma NIIF
           if(af.isDepreciacion_niif_activada() && r.getValor_deterioro_niif()>0){
           Cta_PUC cta=null;    
           cta=ps.getDao().ObtenerCtaPuc(af.getAux_activo_depreciacion()+""+af.getCta_t().getId());
           if(cta==null){
               cta=ps.IngresarDentroDeUnAuxiliar(af.getAux_activo_depreciacion(),af.getCta_t().getId());
           }
           Asiento a=new Asiento();
           a.setDocumento(dn);
           a.setCtaPuc(cta);
           a.setDetalle("Asiento Depreciacion ");
           a.setCredito(Math.round(r.getValor_deterioro_niif()));
           ds.getDao().PersistirAsiento(a);
           cta=ps.getDao().ObtenerCtaPuc(af.getAux_gasto_depreciacion()+""+af.getCta_t().getId());
           if(cta==null){
               cta=ps.IngresarDentroDeUnAuxiliar(af.getAux_gasto_depreciacion(),af.getCta_t().getId());
           }
           a=new Asiento();
           a.setDocumento(dn);
           a.setCtaPuc(cta);
           a.setDetalle("Asiento Gasto Depreciacion ");
           a.setDebito(Math.round(r.getValor_deterioro_niif()));
           ds.getDao().PersistirAsiento(a);           
           }
           if(r.getValor_deterioro_local()>0){
              af.setFecha_ultima_depreciacion(fecha);
              this.Modificar(af);
           }
           
       }       
    }
    public Vector<Registro_De_Deterioro> ObtenerReporte(Date fecha,double tasa){
        System.out.println(""+fecha.toLocaleString());
        Iterator <ActivoFijo> it=dao.Listar("").iterator();
        Vector<Registro_De_Deterioro> lista=new Vector<Registro_De_Deterioro>();
        FechaService fs=new FechaService();
        ContabilidadService cs=new ContabilidadService();
        while(it.hasNext()){
            ActivoFijo a=it.next();            
            Registro_De_Deterioro rep=new Registro_De_Deterioro();
            rep.setId(a.getId());
            rep.setDescripcion(a.getDescripcion());
            rep.setFecha(fecha);
            rep.setValor_local(a.getValor_local());
            rep.setValor_niif(a.getValor_niif());
            rep.setPeriodo(fs.RestarFechasPorDias(a.getFecha_ultima_depreciacion(),fecha));
            //para local
            double saldo_local_contable=cs.getDao().ObtenerSaldo(a.getAux_activo_fijo()+""+a.getCta_t().getId() ,fecha,true,null)+cs.getDao().ObtenerSaldo(a.getAux_activo_depreciacion()+""+a.getCta_t().getId() ,fecha,true,null);
            double ajuste_local=a.getPrecioDiarioLocal()*rep.getPeriodo();
            if(a.isDepreciacion_local_activada()){
               if(saldo_local_contable>ajuste_local){
                  rep.setValor_deterioro_local(ajuste_local);
               }else{
                  if(saldo_local_contable==0){
                     rep.setValor_deterioro_local(0); 
                  }else{ 
                     rep.setValor_deterioro_local(saldo_local_contable);
                  }
               }
            }else{
               rep.setValor_deterioro_local(0); 
            }
            // para niif
            int periodo=fs.RestarFechasPorDias(a.getFecha_adquisicion(),fecha);
            double saldo_niif_contable=cs.getDao().ObtenerSaldo(a.getAux_activo_fijo()+""+a.getCta_t().getId() ,fecha,false,null)+cs.getDao().ObtenerSaldo(a.getAux_activo_depreciacion()+""+a.getCta_t().getId() ,fecha,false,null);            
            double vp1=a.getUtilidad_esperada_1()*Math.pow(1+(tasa/100),(periodo)*-1);
            double vp2=a.getUtilidad_esperada_2()*Math.pow(1+(tasa/100),(periodo)*-1);
            double vp3=a.getUtilidad_esperada_3()*Math.pow(1+(tasa/100),(periodo)*-1);
            double vp4=a.getUtilidad_esperada_4()*Math.pow(1+(tasa/100),(periodo)*-1);
            double valor_de_uso=vp1+vp2+vp3+vp4;
            rep.setValor_de_uso(valor_de_uso);
            rep.setSaldo_en_libro(saldo_niif_contable);
            if(a.isDepreciacion_niif_activada()){
               if(valor_de_uso<saldo_niif_contable){                                     
                     rep.setValor_deterioro_niif(saldo_niif_contable-valor_de_uso);                   
               }else{
                     rep.setValor_deterioro_niif(0);
               }
            }else{
                rep.setValor_deterioro_niif(0);
            }
            System.out.println(vp1+"/"+vp2+"/"+vp3+"/"+vp4+"="+NumberFormat.getInstance().format(valor_de_uso));
            System.out.println(rep.toString());
            lista.add(rep);
        }
        return lista;
    }
    /**
     * @return the dao
     */
    public sic.Dominio.InterfacesDAO.IActivoFijoDAO getDao() {
        return dao;
    }
    
}
