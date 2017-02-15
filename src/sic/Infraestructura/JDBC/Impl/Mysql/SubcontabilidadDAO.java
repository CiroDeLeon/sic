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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos;
import sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author Usuario1
 */
public class SubcontabilidadDAO implements sic.Dominio.InterfacesDAO.ISubcontabilidadDAO{
    @Override
    public void Persistir(Subcontabilidad sub) {
       String sql =" insert into subcontabilidad ";
               sql+=" (descripcion,idPadre,idestructuradecosto) values (?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,sub.getDescripcion());            
            if(sub.getPadre()!=null){
               ps.setLong(2,sub.getPadre().getId());
            }else{
               ps.setLong(2,0); 
            }
            ps.setLong(3,0);
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(SubcontabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void Modificar(Subcontabilidad sub) {
        String sql =" update subcontabilidad set ";
               sql+=" descripcion=?,idPadre=?,idestructuradecosto=? where(idsubcontabilidad="+sub.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,sub.getDescripcion());            
            if(sub.getPadre()!=null){
               ps.setLong(2,sub.getPadre().getId());
            }else{
               ps.setLong(2,0); 
            }
            if(sub.getEstructura_de_costos()!=null){
               ps.setLong(3,sub.getEstructura_de_costos().getId()); 
            }else{
               ps.setLong(3,0);
            }
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(SubcontabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Subcontabilidad> Buscar(String busqueda) {
        Connection con=null;
        String sql="select idSubcontabilidad,Descripcion,idPadre,idestructuradecosto from subcontabilidad where(idsubcontabilidad like '%"+busqueda+"%' or descripcion like '"+busqueda+"%') ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());      
            ResultSet rs=ps.executeQuery();
            Vector<Subcontabilidad> lista=new Vector<Subcontabilidad>();
            EstructuraDeCostosService service=new EstructuraDeCostosService();
            while(rs.next()){
                Subcontabilidad c=new Subcontabilidad();
                c.setId(rs.getLong(1));                
                c.setDescripcion(rs.getString(2));              
                Subcontabilidad padre=this.Obtener(rs.getLong(3));
                c.setPadre(padre);
                long id=rs.getLong(4);
                if(id!=0){
                   EstructuraDeCostos edc=service.getDao().ObtenerEstructuraDeCostos(id);//(rs.getLong(4));
                   c.setEstructura_de_costos(edc);
                }else{
                   c.setEstructura_de_costos(null);
                }
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(SubcontabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Subcontabilidad>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Subcontabilidad Obtener(long id) {
        Connection con=null;
        String sql="select idSubcontabilidad,Descripcion,idPadre,idestructuradecosto from subcontabilidad where(idsubcontabilidad="+id+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());      
            ResultSet rs=ps.executeQuery();
            Vector<Subcontabilidad> lista=new Vector<Subcontabilidad>();      
            EstructuraDeCostosService service=new EstructuraDeCostosService();
            if(rs.next()){
                Subcontabilidad c=new Subcontabilidad();
                c.setId(rs.getLong(1));                
                c.setDescripcion(rs.getString(2));              
                Subcontabilidad padre=new Subcontabilidad();
                if(rs.getLong(3)==0){
                   padre.setId(rs.getLong(3));                              
                   c.setPadre(padre);
                }else{
                   c.setPadre(null);
                }
                long ide=rs.getLong(4);
                if(ide!=0){
                   EstructuraDeCostos edc=service.getDao().ObtenerEstructuraDeCostos(ide);//(rs.getLong(4));
                   c.setEstructura_de_costos(edc);
                }else{
                   c.setEstructura_de_costos(null);
                }
                return c;
                
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(SubcontabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Subcontabilidad> ObtenerHijos(long id) {
        Connection con=null;
        String sql="select idSubcontabilidad,Descripcion,idPadre,idestructuradecosto from subcontabilidad where(idpadre="+id+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());      
            ResultSet rs=ps.executeQuery();
            Vector<Subcontabilidad> lista=new Vector<Subcontabilidad>();
            EstructuraDeCostosService service=new EstructuraDeCostosService();
            while(rs.next()){
                Subcontabilidad c=new Subcontabilidad();
                c.setId(rs.getLong(1));                
                c.setDescripcion(rs.getString(2));              
                Subcontabilidad padre=this.Obtener(rs.getLong(3));
                c.setPadre(padre);
                long ide=rs.getLong(4);
                if(ide!=0){
                   EstructuraDeCostos edc=service.getDao().ObtenerEstructuraDeCostos(ide);//(rs.getLong(4));
                   c.setEstructura_de_costos(edc);
                }else{
                   c.setEstructura_de_costos(null);
                }
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(SubcontabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Subcontabilidad>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
}
