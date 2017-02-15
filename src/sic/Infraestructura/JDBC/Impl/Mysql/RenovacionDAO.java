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
import sic.Infraestructura.JDBC.Pool;
import sic.Dominio.InterfacesDAO.IRenovacionDAO;
import sic.Aplicacion.Servicios.Renovacion.Renovacion;

/**
 *
 * @author FANNY BURGOS
 */
public class RenovacionDAO implements IRenovacionDAO{

    @Override
    public Renovacion PersistirRenovacion(Renovacion r) {
        String sql =" insert into renovacion  ";
               sql+=" (anio,mes,clave) values ";
               sql+=" (?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            //ps.setObject(1,rd.getId());
            ps.setInt(1,r.getAño());
            ps.setInt(2,r.getMes());
            ps.setString(3,r.getClave());
            ps.executeUpdate();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(RenovacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Renovacion ModificarRenovacion(Renovacion r) {
        String sql =" update renovacion ";
               sql+=" set anio=?,mes=?,clave=? where(idrenovacion="+r.getId()+") ";
               sql+=" ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            //ps.setObject(1,rd.getId());
            ps.setInt(1,r.getAño());
            ps.setInt(2,r.getMes());
            ps.setString(3,r.getClave());           
            ps.executeUpdate();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(RenovacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Renovacion> ObtenerRenovaciones() {
        String sql =" select  ";
               sql+=" idrenovacion,anio,mes,clave from renovacion ";               
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ResultSet rs=ps.executeQuery();
            Vector<Renovacion> lista=new Vector<Renovacion>();
            while(rs.next()){
               Renovacion r=new Renovacion();
               r.setId(rs.getObject(1));
               r.setAño(rs.getInt(2));                  
               r.setMes(rs.getInt(3));
               r.setClave(rs.getString(4));
               lista.add(r);
            }            
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DatosDeReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Renovacion ObtenerRenovacion(int año, int mes) {
        String sql =" select  ";
               sql+=" idrenovacion,anio,mes,clave from renovacion ";
               sql+=" where(anio=? and mes=?) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setInt(1,año);
            ps.setInt(2,mes);
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){
               Renovacion r=new Renovacion();
               r.setId(rs.getObject(1));
               r.setAño(rs.getInt(2));                  
               r.setMes(rs.getInt(3));
               r.setClave(rs.getString(4));
               return r;
            }            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DatosDeReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
