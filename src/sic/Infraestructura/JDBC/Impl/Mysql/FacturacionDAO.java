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
import sic.Dominio.Servicios.Facturacion.SaldoFactura;
import sic.Infraestructura.JDBC.Pool;
import sic.Dominio.InterfacesDAO.IFacturacionDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class FacturacionDAO implements IFacturacionDAO{

    @Override
    public Vector<SaldoFactura> ObtenerSaldos(Object idUsuario,String busqueda) {
        String sql ="";
               sql+="select \n";
               sql+="   asiento.NoFactura, \n";
               sql+="   SUM(asiento.Debito)-SUM(asiento.Credito) \n";
               sql+="from  \n";
               sql+="   documento,asiento,puc \n";
               sql+="where( \n";
               sql+="   documento.idDocumento=asiento.idDocumento and \n";
               sql+="   puc.idCtaPUC=asiento.idCtaPUC and  \n";
               sql+="   puc.idCta_T="+idUsuario+" and \n";
               sql+="   asiento.NoFactura like ? and \n";
               sql+="   asiento.idCtaPUC like '1305%' and \n";
               sql+="   documento.Activo=true \n";
               sql+=")group by nofactura \n";         
               Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,busqueda+"%");
            ResultSet rs=ps.executeQuery();
            Vector <SaldoFactura> lista=new Vector <SaldoFactura>();
            while(rs.next()){                                
                SaldoFactura sa=new SaldoFactura();
                sa.setFactura(rs.getInt(1));
                sa.setSaldo(rs.getDouble(2));
                lista.add(sa);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(FacturacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public double ObtenerSaldoFactura(int numeracionfactura) {
        String sql ="";
               sql+="select \n";               
               sql+="   SUM(asiento.Debito)-SUM(asiento.Credito) \n";
               sql+="from  \n";
               sql+="   documento,asiento,puc \n";
               sql+="where( \n";
               sql+="   documento.idDocumento=asiento.idDocumento and \n";
               sql+="   puc.idCtaPUC=asiento.idCtaPUC and  \n";               
               sql+="   asiento.NoFactura=? and \n";
               sql+="   asiento.idCtaPUC like '1305%' and \n";               
               sql+="   documento.Activo=true \n";
               sql+=") \n";         
               Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setInt(1,numeracionfactura);            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                                                
                return rs.getDouble(1);                
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(FacturacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
}
