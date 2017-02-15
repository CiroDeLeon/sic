/*
 * To change this template, choose Tools | Templates
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
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.InterfacesDAO.ICtatDAO;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author FANNY BURGOS
 */
public class CtaTDAO implements ICtatDAO{
    @Override
    public Cta_T PersistirCtaT(Cta_T t) {
        String sql =" insert into Cta_T ";
               sql+=" (idcta_t,descripcion) values ("+t.getId()+",?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,t.getDescripcion());
            ps.executeUpdate();
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Cta_T ObtenerCtaT(String descripcion) {
        Connection con=null;
        String sql="select idcta_t,descripcion from cta_t where(descripcion='"+descripcion+"') limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id=rs.getObject(1);
                String descrip=rs.getString(2);                
                Cta_T t=new Cta_T();
                t.setId(id);
                t.setDescripcion(descripcion);
                return t;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Cta_T> ObtenerCtasT(String busqueda) {
        Connection con=null;
        String sql="select idcta_t,descripcion from cta_t where(descripcion like ? or idcta_t like ?) ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,"%"+busqueda+"%");
            ps.setString(2,busqueda+"%");
            ResultSet rs=ps.executeQuery();
            Vector<Cta_T> lista=new Vector<Cta_T>();
            while(rs.next()){
                Object id=rs.getObject(1);
                String descrip=rs.getString(2);                
                Cta_T t=new Cta_T();
                t.setId(id);
                t.setDescripcion(descrip);
                lista.add(t);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Cta_T ObtenerCtaT(Object idctat) {
        Connection con=null;
        String sql="select idcta_t,descripcion from cta_t where(idcta_t="+idctat+") limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id=rs.getObject(1);
                String descrip=rs.getString(2);                
                Cta_T t=new Cta_T();
                t.setId(id);
                t.setDescripcion(descrip);
                return t;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Cta_T ModificarCtaT(Object id, Cta_T t) {
        String sql =" update Cta_T ";
               sql+=" set descripcion=? where(idcta_t="+id+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,t.getDescripcion());
            ps.executeUpdate();
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public long ObtenerIdUltimaCtaT() {
        Connection con=null;
        String sql="select idcta_t from cta_t order by idcta_t desc limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                long id=rs.getLong(1);                
                return id;
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(CtaTDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
