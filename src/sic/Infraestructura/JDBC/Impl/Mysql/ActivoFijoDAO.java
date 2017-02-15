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
import sic.Dominio.Core.Activo_Fijo.ActivoFijo;
import sic.Dominio.Core.Cta_T.CtaT_Service;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author Usuario1
 */
public class ActivoFijoDAO implements sic.Dominio.InterfacesDAO.IActivoFijoDAO{

    @Override
    public void Persistir(ActivoFijo a) {
        String sql =" insert into activo_fijo ";        
               sql+=" (";
               sql+=" idCta_T,Descripcion,Vida_Util_Dias,Aux_Activo_Fijo,Aux_Activo_Depreciacion, ";
               sql+=" Aux_Gasto_Depreciacion,Valor_Local,Valor_Niif,Tipo,Utilidad_Esperada_1, ";
               sql+=" Utilidad_Esperada_2,Utilidad_Esperada_3,Utilidad_Esperada_4,FechaDeAdquisicion, ";
               sql+=" Depreciacion_local,Depreciacion_Niif,FechaUltimaDepreciacion,idactivo_fijo ";
               sql+=" )";
               sql+=" values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,? ,?,?,?,? )";
        
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setObject(1,a.getCta_t().getId());
            ps.setString(2,a.getDescripcion());
            ps.setInt(3,a.getVida_util_en_dias());
            ps.setString(4,a.getAux_activo_fijo());
            ps.setString(5,a.getAux_activo_depreciacion());
            ps.setString(6,a.getAux_gasto_depreciacion());
            ps.setDouble(7,a.getValor_local());
            ps.setDouble(8,a.getValor_niif());
            ps.setString(9,a.getTipo());
            ps.setDouble(10,a.getUtilidad_esperada_1());
            ps.setDouble(11,a.getUtilidad_esperada_2());
            ps.setDouble(12,a.getUtilidad_esperada_3());
            ps.setDouble(13,a.getUtilidad_esperada_4());
            ps.setDate(14,new java.sql.Date(a.getFecha_adquisicion().getTime()));
            ps.setBoolean(15,a.isDepreciacion_local_activada());
            ps.setBoolean(16,a.isDepreciacion_niif_activada());
            ps.setDate(17,new java.sql.Date(a.getFecha_ultima_depreciacion().getTime()));
            ps.setLong(18,a.getId());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public void Modificar(long id, ActivoFijo a) {
        String sql =" update activo_fijo ";        
               sql+=" set ";
               sql+=" idCta_T=?,Descripcion=?,Vida_Util_Dias=?,Aux_Activo_Fijo=?,Aux_Activo_Depreciacion=?, ";
               sql+=" Aux_Gasto_Depreciacion=?,Valor_Local=?,Valor_Niif=?,Tipo=?,Utilidad_Esperada_1=?, ";
               sql+=" Utilidad_Esperada_2=?,Utilidad_Esperada_3=?,Utilidad_Esperada_4=?,FechaDeAdquisicion=?, ";
               sql+=" Depreciacion_local=?,Depreciacion_Niif=?,FechaUltimaDepreciacion=? ";
               sql+=" ";
               sql+=" where(idActivo_Fijo="+id+")";
        
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setObject(1,a.getCta_t().getId());
            ps.setString(2,a.getDescripcion());
            ps.setInt(3,a.getVida_util_en_dias());
            ps.setString(4,a.getAux_activo_fijo());
            ps.setString(5,a.getAux_activo_depreciacion());
            ps.setString(6,a.getAux_gasto_depreciacion());
            ps.setDouble(7,a.getValor_local());
            ps.setDouble(8,a.getValor_niif());
            ps.setString(9,a.getTipo());
            ps.setDouble(10,a.getUtilidad_esperada_1());
            ps.setDouble(11,a.getUtilidad_esperada_2());
            ps.setDouble(12,a.getUtilidad_esperada_3());
            ps.setDouble(13,a.getUtilidad_esperada_4());
            ps.setDate(14,new java.sql.Date(a.getFecha_adquisicion().getTime()));
            ps.setBoolean(15,a.isDepreciacion_local_activada());
            ps.setBoolean(16,a.isDepreciacion_niif_activada());
            ps.setDate(17,new java.sql.Date(a.getFecha_ultima_depreciacion().getTime()));
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<ActivoFijo> Listar(String busqueda) {
        Connection con=null;
        String sql ="select  ";               
               sql+=" idCta_T,Descripcion,Vida_Util_Dias,Aux_Activo_Fijo,Aux_Activo_Depreciacion, ";
               sql+=" Aux_Gasto_Depreciacion,Valor_Local,Valor_Niif,Tipo,Utilidad_Esperada_1, ";
               sql+=" Utilidad_Esperada_2,Utilidad_Esperada_3,Utilidad_Esperada_4,FechaDeAdquisicion, ";
               sql+=" Depreciacion_local,Depreciacion_Niif,FechaUltimaDepreciacion,idActivo_Fijo ";               
               sql+=" from activo_fijo where(descripcion like '"+busqueda+"%') ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector<ActivoFijo> lista=new Vector<ActivoFijo>();
            CtaT_Service cs=new  CtaT_Service();
            while(rs.next()){
                ActivoFijo a=new ActivoFijo();                
                Cta_T cuenta_t=cs.getDaot().ObtenerCtaT(rs.getObject(1));
                a.setCta_t(cuenta_t);
                a.setDescripcion(rs.getString(2));
                a.setVida_util_en_dias(rs.getInt(3));
                a.setAux_activo_fijo(rs.getString(4));
                a.setAux_activo_depreciacion(rs.getString(5));
                a.setAux_gasto_depreciacion(rs.getString(6));
                a.setValor_local(rs.getDouble(7));
                a.setValor_niif(rs.getDouble(8));
                a.setTipo(rs.getString(9));
                a.setUtilidad_esperada_1(rs.getDouble(10));
                a.setUtilidad_esperada_2(rs.getDouble(11));
                a.setUtilidad_esperada_3(rs.getDouble(12));
                a.setUtilidad_esperada_4(rs.getDouble(13));
                a.setFecha_adquisicion(new java.util.Date(rs.getDate(14).getTime()));
                a.setDepreciacion_local_activada(rs.getBoolean(15));
                a.setDepreciacion_niif_activada(rs.getBoolean(16));
                a.setFecha_ultima_depreciacion(new java.util.Date(rs.getDate(17).getTime()));
                a.setId(rs.getLong(18));
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<ActivoFijo>();        
        }finally{
            Pool.LiberarConexion(con);
        }    
    }

    @Override
    public ActivoFijo ObtenerUltimo() {
        Connection con=null;
        String sql ="select  ";               
               sql+=" idCta_T,Descripcion,Vida_Util_Dias,Aux_Activo_Fijo,Aux_Activo_Depreciacion, ";
               sql+=" Aux_Gasto_Depreciacion,Valor_Local,Valor_Niif,Tipo,Utilidad_Esperada_1, ";
               sql+=" Utilidad_Esperada_2,Utilidad_Esperada_3,Utilidad_Esperada_4,FechaDeAdquisicion, ";
               sql+=" Depreciacion_local,Depreciacion_Niif,FechaUltimaDepreciacion,idActivo_Fijo ";               
               sql+=" from activo_fijo order by idActivo_fijo desc limit 1 ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector<ActivoFijo> lista=new Vector<ActivoFijo>();
            CtaT_Service cs=new  CtaT_Service();
            if(rs.next()){
                ActivoFijo a=new ActivoFijo();                
                Cta_T cuenta_t=cs.getDaot().ObtenerCtaT(rs.getObject(1));
                a.setCta_t(cuenta_t);
                a.setDescripcion(rs.getString(2));
                a.setVida_util_en_dias(rs.getInt(3));
                a.setAux_activo_fijo(rs.getString(4));
                a.setAux_activo_depreciacion(rs.getString(5));
                a.setAux_gasto_depreciacion(rs.getString(6));
                a.setValor_local(rs.getDouble(7));
                a.setValor_niif(rs.getDouble(8));
                a.setTipo(rs.getString(9));
                a.setUtilidad_esperada_1(rs.getDouble(10));
                a.setUtilidad_esperada_2(rs.getDouble(11));
                a.setUtilidad_esperada_3(rs.getDouble(12));
                a.setUtilidad_esperada_4(rs.getDouble(13));
                a.setFecha_adquisicion(new java.util.Date(rs.getDate(14).getTime()));
                a.setDepreciacion_local_activada(rs.getBoolean(15));
                a.setDepreciacion_niif_activada(rs.getBoolean(16));
                a.setFecha_ultima_depreciacion(new java.util.Date(rs.getDate(17).getTime()));
                a.setId(rs.getLong(18));              
                return a;
            }
            ActivoFijo a=new ActivoFijo();
            a.setId(0);
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ActivoFijo a=new ActivoFijo();
            a.setId(0);
            return a;        
        }finally{
            Pool.LiberarConexion(con);
        }    
    }

    @Override
    public ActivoFijo Obtener(long id) {
            Connection con=null;
        String sql ="select  ";               
               sql+=" idCta_T,Descripcion,Vida_Util_Dias,Aux_Activo_Fijo,Aux_Activo_Depreciacion, ";
               sql+=" Aux_Gasto_Depreciacion,Valor_Local,Valor_Niif,Tipo,Utilidad_Esperada_1, ";
               sql+=" Utilidad_Esperada_2,Utilidad_Esperada_3,Utilidad_Esperada_4,FechaDeAdquisicion, ";
               sql+=" Depreciacion_local,Depreciacion_Niif,FechaUltimaDepreciacion,idActivo_Fijo ";               
               sql+=" from activo_fijo where(idactivo_fijo="+id+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector<ActivoFijo> lista=new Vector<ActivoFijo>();
            CtaT_Service cs=new  CtaT_Service();
            if(rs.next()){
                ActivoFijo a=new ActivoFijo();                
                Cta_T cuenta_t=cs.getDaot().ObtenerCtaT(rs.getObject(1));
                a.setCta_t(cuenta_t);
                a.setDescripcion(rs.getString(2));
                a.setVida_util_en_dias(rs.getInt(3));
                a.setAux_activo_fijo(rs.getString(4));
                a.setAux_activo_depreciacion(rs.getString(5));
                a.setAux_gasto_depreciacion(rs.getString(6));
                a.setValor_local(rs.getDouble(7));
                a.setValor_niif(rs.getDouble(8));
                a.setTipo(rs.getString(9));
                a.setUtilidad_esperada_1(rs.getDouble(10));
                a.setUtilidad_esperada_2(rs.getDouble(11));
                a.setUtilidad_esperada_3(rs.getDouble(12));
                a.setUtilidad_esperada_4(rs.getDouble(13));
                a.setFecha_adquisicion(new java.util.Date(rs.getDate(14).getTime()));
                a.setDepreciacion_local_activada(rs.getBoolean(15));
                a.setDepreciacion_niif_activada(rs.getBoolean(16));
                a.setFecha_ultima_depreciacion(new java.util.Date(rs.getDate(17).getTime()));
                a.setId(rs.getLong(18));              
                return a;
            }            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);            
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }  
    }

    @Override
    public ActivoFijo Obtener(Object id_cuenta_t) {
           Connection con=null;
        String sql ="select  ";               
               sql+=" idCta_T,Descripcion,Vida_Util_Dias,Aux_Activo_Fijo,Aux_Activo_Depreciacion, ";
               sql+=" Aux_Gasto_Depreciacion,Valor_Local,Valor_Niif,Tipo,Utilidad_Esperada_1, ";
               sql+=" Utilidad_Esperada_2,Utilidad_Esperada_3,Utilidad_Esperada_4,FechaDeAdquisicion, ";
               sql+=" Depreciacion_local,Depreciacion_Niif,FechaUltimaDepreciacion,idActivo_Fijo ";               
               sql+=" from activo_fijo where(idCta_T="+id_cuenta_t+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector<ActivoFijo> lista=new Vector<ActivoFijo>();
            CtaT_Service cs=new  CtaT_Service();
            if(rs.next()){
                ActivoFijo a=new ActivoFijo();                
                Cta_T cuenta_t=cs.getDaot().ObtenerCtaT(rs.getObject(1));
                a.setCta_t(cuenta_t);
                a.setDescripcion(rs.getString(2));
                a.setVida_util_en_dias(rs.getInt(3));
                a.setAux_activo_fijo(rs.getString(4));
                a.setAux_activo_depreciacion(rs.getString(5));
                a.setAux_gasto_depreciacion(rs.getString(6));
                a.setValor_local(rs.getDouble(7));
                a.setValor_niif(rs.getDouble(8));
                a.setTipo(rs.getString(9));
                a.setUtilidad_esperada_1(rs.getDouble(10));
                a.setUtilidad_esperada_2(rs.getDouble(11));
                a.setUtilidad_esperada_3(rs.getDouble(12));
                a.setUtilidad_esperada_4(rs.getDouble(13));
                a.setFecha_adquisicion(new java.util.Date(rs.getDate(14).getTime()));
                a.setDepreciacion_local_activada(rs.getBoolean(15));
                a.setDepreciacion_niif_activada(rs.getBoolean(16));
                a.setFecha_ultima_depreciacion(new java.util.Date(rs.getDate(17).getTime()));
                a.setId(rs.getLong(18));              
                return a;
            }            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);            
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }  
    }
    
}
