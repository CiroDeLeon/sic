/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC.Impl.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Infraestructura.JDBC.Pool;
import sic.Dominio.InterfacesDAO.IFechaDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class FechaDAO implements IFechaDAO{

    @Override
    public Date ObtenerMenorFecha() {
        Connection con=null;
        String sql ="select MIN(fechacontable) from documento \n";               
               
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);            
            ResultSet rs=ps.executeQuery();                        
            if(rs.next()){
               java.sql.Date d=rs.getDate(1);   
               if(d!=null){
                  return new java.util.Date(d.getTime());
               }else{
                  Calendar cal=Calendar.getInstance();
                  return cal.getTime();
               }
            }            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ContabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);             
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Date ObtenerFechaDelServidor() {
        Connection con=null;
        String sql ="select Now() \n";               
               
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql);            
            ResultSet rs=ps.executeQuery();                        
            if(rs.next()){               
               return new java.util.Date(rs.getTimestamp(1).getTime());
            }            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ContabilidadDAO.class.getName()).log(Level.SEVERE, null, ex);             
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
