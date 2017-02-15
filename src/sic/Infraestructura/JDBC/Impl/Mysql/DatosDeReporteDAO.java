/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC.Impl.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Infraestructura.JDBC.Pool;
import sic.Dominio.InterfacesDAO.IDatosDeReporteDAO;
import sic.Aplicacion.Servicios.Reportes.DatosDeReporte;

/**
 *
 * @author FANNY BURGOS
 */
public class DatosDeReporteDAO implements IDatosDeReporteDAO{

    @Override
    public DatosDeReporte PersistirDatosDeReporte(DatosDeReporte rd) {
        String sql =" insert into datosdereporte ";
               sql+=" (idDatosDeReporte,Nit,Razonsocial,Direccion,Telefonos,Publicidad,Regimen) values ";
               sql+=" ("+rd.getId()+",?,?,?,?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            //ps.setObject(1,rd.getId());
            ps.setLong(1,rd.getNit());
            ps.setString(2,rd.getRazonsocial());
            ps.setString(3,rd.getDireccion());            
            ps.setString(4,rd.getTelefono());            
            ps.setString(5,rd.getPublicidad());            
            ps.setString(6,rd.getRegimen());            
            ps.executeUpdate();
            return rd;
        } catch (SQLException ex) {
            Logger.getLogger(DatosDeReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public DatosDeReporte ModificarDatosDeReporte(DatosDeReporte rd) {
        String sql =" update datosdereporte ";
               sql+=" set idDatosDeReporte=?,Nit=?,Razonsocial=?,Direccion=?,Telefonos=?,Publicidad=?,Regimen=? where(iddatosdereporte="+rd.getId()+") ";
              
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setObject(1,rd.getId());
            ps.setLong(2,rd.getNit());
            ps.setString(3,rd.getRazonsocial());
            ps.setString(4,rd.getDireccion());            
            ps.setString(5,rd.getTelefono());            
            ps.setString(6,rd.getPublicidad());            
            ps.setString(7,rd.getRegimen());            
            ps.executeUpdate();
            return rd;
        } catch (SQLException ex) {
            Logger.getLogger(DatosDeReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public DatosDeReporte ObtenerDatosDeReporte() {
         String sql =" select  ";
               sql+=" idDatosDeReporte,Nit,Razonsocial,Direccion,Telefonos,Publicidad,Regimen from datosdereporte ";
               sql+=" where(iddatosdereporte=1) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            DatosDeReporte rd=new DatosDeReporte();
            rd.setId(1);
            rd.setNit(rs.getLong(2));
            rd.setRazonsocial(rs.getString(3));
            rd.setDireccion(rs.getString(4));
            rd.setTelefono(rs.getString(5));
            rd.setPublicidad(rs.getString(6));
            rd.setRegimen(rs.getString(7));
            return rd;
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
