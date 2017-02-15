/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.EstructuraDeCostos;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import sic.Aplicacion.Servicios.Reportes.ReporteService;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.Documento.DocumentoService;
import sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO;
import sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;



/**
 *
 * @author Usuario1
 */
public class EstructuraDeCostosService {
    private sic.Dominio.InterfacesDAO.IEstructuraDeCostosDAO dao;    
    public EstructuraDeCostosService() {
        dao=new sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO();
    }
    public void Ingresar(EstructuraDeCostos obj){
        getDao().Persistir(obj);       
    }
    public void IngresarCentroDeCosto(EstructuraDeCostos obj,CentroDeCosto obj2){
        obj2.setId(dao.UltimoCentroDeCostos()+1);
        obj2.setEstructura(obj);        
        getDao().Persistir(obj2);
    }
    public void ModificarCentroDeCosto(CentroDeCosto obj){
        getDao().Modificar(obj.getId(),obj);
    }
    public boolean isEsteril(CentroDeCosto obj){
        DocumentoService ds=new DocumentoService();
        return ds.getDao().ExisteAsientoConCentroDeCosto(obj.getId());
    }
    public void VerReporte(String nodo_hijo,String nodo_padre,long idSubcontabilidad,Date fecha_inicial,Date fecha_final){
       if(nodo_padre!=null){ 
       String [] hijo=nodo_hijo.split("<>");
       String [] padre=nodo_padre.split("<>");    
       Object id_cuenta_t=hijo[1];
       Cta_T t=new sic.Dominio.Core.Cta_T.CtaT_Service().getDaot().ObtenerCtaT(id_cuenta_t);
       long idt=Long.parseLong(""+id_cuenta_t.toString());
       if(t!=null && t.getDescripcion().contains(hijo[2])){
          long idCentroDeCosto=Long.parseLong(padre[1]);
          Vector <sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto> lista=dao.ObtenerReporte(idCentroDeCosto,idSubcontabilidad,idt, fecha_inicial, fecha_final);
          ReporteService.VerReporte("AsientoDeCentroDeCosto.jasper",lista,"");
       }       
       }
    }
    public void ObtenerNodosHojaHijos(CentroDeCosto c,Vector<CentroDeCosto> lista){
        Iterator<CentroDeCosto> it=this.getDao().ObtenerHijos(c.getId()).iterator();
        if(it.hasNext()==false){
            lista.add(c);
        }else{
            while(it.hasNext()){
               CentroDeCosto cc=it.next();
               this.ObtenerNodosHojaHijos(cc,lista);    
            }
            
        }
    }
    public DefaultMutableTreeNode ObtenerHijos(Subcontabilidad sub,DefaultMutableTreeNode root,Vector<CentroDeCosto> hijos,Date fecha_inicial,Date fecha_final){
        Iterator<CentroDeCosto> it=hijos.iterator();        
        while(it.hasNext()){
            CentroDeCosto obj=it.next();  
            double saldo=dao.ObtenerSaldo(obj.getId(),sub.getId(), fecha_inicial, fecha_final);
            DefaultMutableTreeNode child=new DefaultMutableTreeNode("<>"+obj.getId()+"<>"+obj.getDescripcion()+"<>"+NumberFormat.getInstance().format(saldo)+"<>");            
            if(dao.ObtenerHijos(obj.getId()).isEmpty()){// Si es nodo hoja
               Iterator<sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo> ita=dao.ObtenerSaldosDeCentroDeCostoPorCuentaT(obj.getId(),sub.getId(), fecha_inicial, fecha_final).iterator();
               while(ita.hasNext()){
                   CentroDeCostoDTO_Saldo dto=ita.next();
                   DefaultMutableTreeNode child_=new DefaultMutableTreeNode(dto.toString());        
                   child.add(child_);
               }
            }
            root.add(this.ObtenerHijos(sub,child,dao.ObtenerHijos(obj.getId()), fecha_inicial, fecha_final));                                                   
        }
        return root;
    }    
    public sic.Dominio.InterfacesDAO.IEstructuraDeCostosDAO getDao() {
        return dao;
    }
    
}
