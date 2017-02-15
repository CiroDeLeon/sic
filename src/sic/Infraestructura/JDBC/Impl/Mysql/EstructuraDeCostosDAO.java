/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Infraestructura.JDBC.Impl.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;
import sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos;
import sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo;
import sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author Usuario1
 */
public class EstructuraDeCostosDAO implements sic.Dominio.InterfacesDAO.IEstructuraDeCostosDAO{

    @Override
    public void Persistir(EstructuraDeCostos e) {
        String sql="insert into estructuradecosto (idestructuradecosto,descripcion) values (?,?) ";
        Connection con=null;
        try{
            con=sic.Infraestructura.JDBC.Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setLong(1,e.getId());
            ps.setString(2,e.getDescripcion());
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void Modificar(long id, EstructuraDeCostos e) {
        String sql="update estructuradecosto  set descripcion=? where(idestructuradecosto="+id+") ";
        Connection con=null;
        try{
            con=sic.Infraestructura.JDBC.Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,e.getDescripcion());
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void Persistir(CentroDeCosto obj) {
        String sql="insert into centrodecosto (descripcion,idPadre,idEstructuraDeCosto) values (?,?,?) ";
        Connection con=null;
        try{
            con=sic.Infraestructura.JDBC.Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,obj.getDescripcion());
            if(obj.getPadre()==null){
               ps.setLong(2,0);
            }else{
               ps.setLong(2,obj.getPadre().getId()); 
            }
            ps.setLong(3,obj.getEstructura().getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void Modificar(long id, CentroDeCosto obj) {
        String sql="update centrodecosto set descripcion=?,idPadre=?,idEstructuraDeCosto=? where(idcentrodecosto="+id+") ";
        Connection con=null;
        try{
            con=sic.Infraestructura.JDBC.Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,obj.getDescripcion());
            if(obj.getPadre()==null){
               ps.setLong(2,0);
            }else{
               ps.setLong(2,obj.getPadre().getId()); 
            }
            ps.setLong(3,obj.getEstructura().getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public long UltimaEstrucutraDeCostos() {
        Connection con=null;
        String sql="select idestructuradecosto from estructuradecosto order by idestructuradecosto desc limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<EstructuraDeCostos> BusquedaIncremental(String busqueda) {
        Connection con=null;
        String sql ="select idestructuradecosto,descripcion from estructuradecosto ";
               sql+=" where(descripcion like '"+busqueda+"%' or idestructuradecosto like '%"+busqueda+"%')";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos>();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos obj=new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               lista.add(obj);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<CentroDeCosto> ObtenerCentrosDeCostos(long idEstructuraDeCostos) {
        Connection con=null;
        String sql ="select idcentrodecosto,descripcion,idpadre from centrodecosto ";
               sql+=" where(idestructuradecosto="+idEstructuraDeCostos+")order by idcentrodecosto ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto obj=new sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               if(rs.getLong(3)==0){
                   obj.setPadre(null);
               }else{
                  obj.setPadre(this.ObtenerCentroDeCosto(rs.getLong(3)));
               }
               lista.add(obj);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public CentroDeCosto ObtenerCentroDeCosto(long id) {
        Connection con=null;
        String sql ="select idcentrodecosto,descripcion,idpadre from centrodecosto ";
               sql+=" where(idcentrodecosto="+id+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto obj=new sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               if(rs.getLong(3)==0){
                   obj.setPadre(null);
               }else{
                  obj.setPadre(this.ObtenerCentroDeCosto(rs.getLong(3)));
               }               
               return obj;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public long UltimoCentroDeCostos() {
        Connection con=null;
        String sql="select idcentrodecosto from centrodecosto order by idcentrodecosto desc limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<CentroDeCosto> ObtenerCentrosDeCostos(long idEstructuraDeCostos, String busqueda) {
         Connection con=null;
        String sql ="select idcentrodecosto,descripcion,idpadre from centrodecosto ";
               sql+=" where(idestructuradecosto="+idEstructuraDeCostos+" and (descripcion like '"+busqueda+"%' or idcentrodecosto like '%"+busqueda+"%')) ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto obj=new sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               if(rs.getLong(3)==0){
                   obj.setPadre(null);
               }else{
                  obj.setPadre(this.ObtenerCentroDeCosto(rs.getLong(3)));
               }
               lista.add(obj);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public EstructuraDeCostos ObtenerEstructuraDeCostos(long id) {
        Connection con=null;
        String sql ="select idestructuradecosto,descripcion from estructuradecosto ";
               sql+=" where(idestructuradecosto="+id+")";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos obj=new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               return obj;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<CentroDeCosto> ObtenerHijos(long idCentroDeCostos) {
        Connection con=null;
        String sql ="select idcentrodecosto,descripcion,idpadre from centrodecosto ";
               sql+=" where(idPadre="+idCentroDeCostos+")order by idcentrodecosto ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto obj=new sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               if(rs.getLong(3)==0){
                   obj.setPadre(null);
               }else{
                   obj.setPadre(this.ObtenerCentroDeCosto(rs.getLong(3)));
               }
               lista.add(obj);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<CentroDeCosto> ObtenerPadres(long idEstructuraDeCostos) {
        Connection con=null;
        String sql ="select idcentrodecosto,descripcion,idpadre from centrodecosto ";
               sql+=" where(idestructuradecosto="+idEstructuraDeCostos+" and idpadre=0)order by idcentrodecosto ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto obj=new sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto();
               obj.setId(rs.getLong(1));
               obj.setDescripcion(rs.getString(2));
               if(rs.getLong(3)==0){
                   obj.setPadre(null);
               }else{
                  obj.setPadre(this.ObtenerCentroDeCosto(rs.getLong(3)));
               }
               lista.add(obj);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
    @Override
    public double ObtenerSaldo(long idCentroDeCosto, long idSubcontabilidad, Date fecha_inicial, Date fecha_final) {
        Vector<CentroDeCosto> lista=new Vector<CentroDeCosto>();
        CentroDeCosto c=new CentroDeCosto();
        c.setId(idCentroDeCosto);
        new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService().ObtenerNodosHojaHijos(c,lista);
        Iterator<CentroDeCosto> it=lista.iterator();
        Connection con=null;
        System.out.println("id centro costo="+idCentroDeCosto);
        String sql =" select  \n";
               sql+=" coalesce(sum(asiento.Debito-asiento.Credito),0) as saldo \n";
               sql+=" from \n";
               sql+=" documento,asiento \n";
               sql+=" where( \n";
               sql+=" documento.idDocumento=asiento.idDocumento and  \n";
               sql+=" documento.Activo=true and \n";                                             
               if(!lista.isEmpty()){
                  sql+="( "; 
                  while(it.hasNext()){
                     CentroDeCosto cc=it.next();                     
                     sql+=" asiento.idCentroDeCosto="+cc.getId();                     
                     if(it.hasNext()){
                         sql+=" or \n";
                     }
                  }
                  sql+=")  and ";
               }else{
                  sql+=" asiento.idCentroDeCosto="+idCentroDeCosto+"  and \n";
               }
               sql+=" asiento.idCentroDeCosto!=0  and \n";
               sql+=" documento.idSubcontabilidad="+idSubcontabilidad+" and \n ";
               sql+=" documento.FechaContable>=? and \n ";
               sql+=" documento.FechaContable<=? and  \n";
               sql+=" documento.Norma_Local=true  \n ";
               sql+=") ";
               //sql+=" documento.Norma_Internacional=true  ";
               
               System.out.println(sql);
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);            
            ps.setTimestamp(1,new java.sql.Timestamp(fecha_inicial.getTime()));
            ps.setTimestamp(2,new java.sql.Timestamp(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();
            if(rs.next()){ 
                System.out.println(idCentroDeCosto+"<->"+rs.getDouble(1));
                return rs.getDouble(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<CentroDeCostoDTO_Saldo> ObtenerSaldosDeCentroDeCostoPorCuentaT(long idCentroDeCosto, long idSubcontabilidad, Date fecha_inicial, Date fecha_final) {
        Connection con=null;
        String sql =" select ";
               sql+=" substring(asiento.idCtaPUC,1,8) as auxiliar, ";
               sql+=" substring(asiento.idCtaPUC,9,length(asiento.idCtaPUC)-8) as cta_t, ";
               sql+=" coalesce(sum(asiento.Debito-asiento.Credito),0) as saldo  ";
               sql+=" from ";
               sql+=" documento,asiento ";
               sql+=" where( ";
               sql+="    documento.Activo=true  and    ";
               sql+="    asiento.idDocumento=documento.idDocumento and ";
               sql+="    documento.idSubcontabilidad="+idSubcontabilidad+" and ";
               sql+="    asiento.idCentroDeCosto="+idCentroDeCosto+" and ";
               sql+="    documento.FechaContable>=? and ";
               sql+="    documento.FechaContable<=? and ";
               sql+="    documento.Norma_Local=true  ";
               sql+=" )group by cta_t ";
               try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setTimestamp(1,new java.sql.Timestamp(fecha_inicial.getTime()));
            ps.setTimestamp(2,new java.sql.Timestamp(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();
            Vector<sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo>();
            sic.Dominio.Core.Cta_T.CtaT_Service service=new sic.Dominio.Core.Cta_T.CtaT_Service();
            while(rs.next()){                
               sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo obj=new sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo();
               obj.setId_cuenta_t(rs.getLong(2));
               System.out.println("---"+rs.getString(2));
               Object o=rs.getString(2);
               Cta_T t=service.getDaot().ObtenerCtaT(o);
               if(t!=null){
                  obj.setDescripcion(t.getDescripcion());
               }
               obj.setValue(rs.getDouble(3));               
               lista.add(obj);
            }
            System.out.println(lista.size());
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public double ObtenerSaldoEstructuraDeCosto(long idEstructuraDeCosto,long idSubcontabilidad, Date fecha_inicial, Date fecha_final) {
        Connection con=null;
        String sql =" select  ";
               sql+=" coalesce(sum(asiento.Debito-asiento.Credito),0) as saldo ";
               sql+=" from ";
               sql+=" documento,asiento,centrodecosto ";
               sql+=" where( ";
               sql+=" documento.idDocumento=asiento.idDocumento and  ";
               sql+=" documento.Activo=true and ";
               sql+=" asiento.idCentroDeCosto=centrodecosto.idCentroDeCosto and ";
               sql+=" centrodecosto.idestructuradecosto="+idEstructuraDeCosto+" and";              
               sql+=" documento.idSubcontabilidad="+idSubcontabilidad+" and ";
               sql+=" documento.FechaContable>=? and ";
               sql+=" documento.FechaContable<=? and ";
               sql+=" documento.Norma_Local=true  ";
               sql+=") ";
               //sql+=" documento.Norma_Internacional=true  ";
               
               
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setTimestamp(1,new java.sql.Timestamp(fecha_inicial.getTime()));
            ps.setTimestamp(2,new java.sql.Timestamp(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<ReporteAsientoPorCentroDeCosto> ObtenerReporte(long idCentroDeCosto, long idSubcontabilidad, long id_cuenta_t, Date fecha_inicial, Date fecha_final) {
                Connection con=null;
                String sql =" select  ";
                       sql+=" documento.FechaContable, ";
                       sql+=" CONCAT_ws('',documento.Abreviatura,documento.Numeracion) as soporte, ";
                       sql+=" asiento.Detalle, ";
                       sql+=" asiento.Debito, ";
                       sql+=" asiento.Credito, ";
                       sql+=" asiento.Entradas, ";
                       sql+=" asiento.Salidas ";
                       sql+=" from ";
                       sql+=" documento,asiento ";
                       sql+=" where(  ";
                       sql+="  documento.idDocumento=asiento.idDocumento and ";
                       sql+="  documento.Activo=true and ";
                       sql+="  documento.idSubcontabilidad="+idSubcontabilidad+" and ";
                       sql+="  asiento.idCentroDeCosto="+idCentroDeCosto+" and ";
                       sql+="  documento.Norma_Local=true and ";
                       sql+=" documento.FechaContable>=? and ";
                       sql+=" documento.FechaContable<=? and ";
                       sql+="  substring(asiento.idCtaPUC,9,Length(asiento.idCtaPUC-9))="+id_cuenta_t+" ";
                       sql+=" ) ";
                           try {            
                              con=Pool.ObtenerConexion();
                              PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
                              ps.setTimestamp(1,new java.sql.Timestamp(fecha_inicial.getTime()));
                              ps.setTimestamp(2,new java.sql.Timestamp(fecha_final.getTime()));
                              ResultSet rs=ps.executeQuery();
                              Vector<sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto> lista=new Vector<sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto>();
                              sic.Dominio.Core.Cta_T.CtaT_Service service=new sic.Dominio.Core.Cta_T.CtaT_Service();
                              Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(idSubcontabilidad);
                              Object idt=""+id_cuenta_t;
                              Cta_T t=new sic.Dominio.Core.Cta_T.CtaT_Service().getDaot().ObtenerCtaT(idt);
                              CentroDeCosto cc=this.ObtenerCentroDeCosto(idCentroDeCosto);
                              while(rs.next()){                
                                 sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto a=new sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto();
                                 a.setId_subcontabilidad(sub.getId());
                                 a.setDescripcion_subcontabilidad(sub.getDescripcion());
                                 a.setId_cuenta_t(id_cuenta_t);
                                 String[]st=t.getDescripcion().split("-"); 
                                 a.setDescripcion_cuenta_t(st[0]);
                                 a.setIdcc(idCentroDeCosto);
                                 a.setDescripcion_cc(cc.getDescripcion());
                                 a.setFecha_inicial(new java.sql.Timestamp(fecha_inicial.getTime()));
                                 a.setFecha_final(new java.sql.Timestamp(fecha_final.getTime()));                                 
                                 a.setFecha_contable(rs.getTimestamp(1));
                                 a.setSoporte(rs.getString(2));
                                 a.setDetalle(rs.getString(3));
                                 a.setDebito(rs.getDouble(4));
                                 a.setCredito(rs.getDouble(5));
                                 a.setEntradas(rs.getDouble(6));
                                 a.setSalidas(rs.getDouble(7));
                                 lista.add(a);
                              }            
                              return lista;
                           } catch (SQLException ex) {
                               Logger.getLogger(sic.Infraestructura.JDBC.Impl.Mysql.EstructuraDeCostosDAO.class.getName()).log(Level.SEVERE, null, ex);
                               return new Vector<sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto>();        
                           }finally{
                           Pool.LiberarConexion(con);
                           }
                
    }
    
}
