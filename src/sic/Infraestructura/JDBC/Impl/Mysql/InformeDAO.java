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
import sic.Dominio.Core.GeneradorDeInformes.Columna;
import sic.Dominio.Core.GeneradorDeInformes.Consepto;
import sic.Dominio.Core.GeneradorDeInformes.ConseptoEnCuenta;
import sic.Dominio.Core.GeneradorDeInformes.Informe;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author Usuario1
 */
public class InformeDAO implements sic.Dominio.InterfacesDAO.Interface_InformeDao {

    @Override
    public void PersistirInforme(Informe i) {
        String sql =" insert into informe ";
               sql+=" (descripcion,no_de_fechas,horizontal) values (?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql);                        
            ps.setString(1,i.getDescripcion());
            ps.setInt(2,i.getNumero_de_fechas());
            ps.setBoolean(3,i.isHorizontal());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void PersistirColumna(Columna col) {
        String sql =" insert into columna ";
               sql+=" (idInforme,Descripcion,Tipo,totalizada,posicion,local) values (?,?,?,?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setLong(1,col.getInforme().getId());
            ps.setString(2,col.getDescripcion());
            ps.setString(3,col.getTipo());
            ps.setBoolean(4,col.isTotalizado());
            ps.setInt(5,col.getPosicion());
            ps.setBoolean(6,col.isLocal());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void PersistirConsepto(Consepto consepto) {
         String sql =" insert into consepto ";
               sql+=" (idInforme,Descripcion,Descripcion_idioma_1,Descripcion_idioma_2,Descripcion_idioma_3,posicion) values (?,?,?,?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setLong(1,consepto.getInforme().getId());
            ps.setString(2,consepto.getDescripcion());
            ps.setString(3,consepto.getDescripcion_idioma_1());
            ps.setString(4,consepto.getDescripcion_idioma_2());
            ps.setString(5,consepto.getDescripcion_idioma_3());
            ps.setInt(6,consepto.getPosicion());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void PersistirConseptoEnCuenta(ConseptoEnCuenta cc) {
        String sql =" insert into cuentaenconsepto ";
               sql+=" (idCtaPUC,idConsepto,suma,tipo) values (?,?,?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,cc.getId_cuenta());
            ps.setLong(2,cc.getConsepto().getId());
            ps.setBoolean(3,cc.isSuma());            
            ps.setString(4,cc.getTipo());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void ModificarInforme(Informe i) {
        String sql =" update informe set ";
               sql+=" descripcion=?,no_de_fechas=?,horizontal=? where(idinforme="+i.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                                    
            ps.setString(1,i.getDescripcion());
            ps.setInt(2,i.getNumero_de_fechas());
            ps.setBoolean(3,i.isHorizontal());                
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void ModificarColumna(Columna col) {
         String sql =" update columna set ";
               sql+=" idInforme=?,Descripcion=?,Tipo=?,totalizada=?,posicion=?,local=? where(idcolumna="+col.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setLong(1,col.getInforme().getId());
            ps.setString(2,col.getDescripcion());
            ps.setString(3,col.getTipo());
            ps.setBoolean(4,col.isTotalizado());
            ps.setInt(5,col.getPosicion());
            ps.setBoolean(6,col.isLocal());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void ModificarConsepto(Consepto consepto) {
             String sql =" update consepto set ";
               sql+=" idInforme=?,Descripcion=?,Descripcion_idioma_1=?,Descripcion_idioma_2=?,Descripcion_idioma_3=?,posicion=? where(idconsepto="+consepto.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setLong(1,consepto.getInforme().getId());
            ps.setString(2,consepto.getDescripcion());
            ps.setString(3,consepto.getDescripcion_idioma_1());
            ps.setString(4,consepto.getDescripcion_idioma_2());
            ps.setString(5,consepto.getDescripcion_idioma_3());
            ps.setInt(6,consepto.getPosicion());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void ModificarConseptoEnCuenta(ConseptoEnCuenta cc) {
        String sql =" update cuentaenconsepto set ";
               sql+=" idCtaPUC=?,idConsepto=?,suma=?,tipo=? where(cuentaenconsepto.idcuentaenconsepto="+cc.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,cc.getId_cuenta());
            ps.setLong(2,cc.getConsepto().getId());
            ps.setBoolean(3,cc.isSuma());            
            ps.setString(4,cc.getTipo());
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void EliminarInforme(Informe i) {
         String sql =" delete from informe  ";
               sql+=" where(idinforme="+i.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                                                
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void EliminarColumna(Columna col) {
              String sql =" delete from columna ";
               sql+="  where(idcolumna="+col.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                                 
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void EliminarConsepto(Consepto consepto) {
                String sql =" delete from consepto ";
               sql+="  where(idconsepto="+consepto.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                                 
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public void EliminarConseptoEnCuenta(ConseptoEnCuenta cc) {
        String sql =" delete from cuentaenconsepto ";
               sql+=" where(cuentaenconsepto.idcuentaenconsepto="+cc.getId()+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql);                                   
            ps.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Consepto> ObtenerConseptos(long id_informe) {
        Connection con=null;
        String sql="select idConsepto,idInforme,Descripcion,Descripcion_idioma_1,Descripcion_idioma_2,Descripcion_idioma_3,posicion from consepto where(idinforme="+id_informe+")order by posicion ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());      
            ResultSet rs=ps.executeQuery();
            Vector<Consepto> lista=new Vector<Consepto>();
            while(rs.next()){
                Consepto c=new Consepto();
                c.setId(rs.getLong(1));
                Informe i=new Informe();
                i.setId(rs.getLong(2));
                c.setInforme(i);
                c.setDescripcion(rs.getString(3));
                c.setDescripcion_idioma_1(rs.getString(4));
                c.setDescripcion_idioma_2(rs.getString(5));
                c.setDescripcion_idioma_3(rs.getString(6));
                c.setPosicion(rs.getInt(7));
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Consepto>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<ConseptoEnCuenta> ObtenerCuentas(long id_consepto) {
        Connection con=null;
        String sql="select idCuentaEnConsepto,idCtaPUC,idConsepto,suma,tipo from cuentaenconsepto where(idconsepto="+id_consepto+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector<ConseptoEnCuenta> lista=new Vector<ConseptoEnCuenta>();
            while(rs.next()){
                ConseptoEnCuenta c=new ConseptoEnCuenta();
                c.setId(rs.getLong(1));
                c.setId_cuenta(rs.getString(2));
                Consepto consepto=new Consepto();
                consepto.setId(id_consepto);
                c.setConsepto(consepto);
                c.setSuma(rs.getBoolean(4));
                c.setTipo(rs.getString(5));
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<ConseptoEnCuenta>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Columna> ObtenerColumnas(long id_informe) {
         Connection con=null;
        String sql="select idcolumna,idinforme,descripcion,tipo,totalizada,posicion,local from columna where(idinforme="+id_informe+") order by posicion";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());      
            ResultSet rs=ps.executeQuery();
            Vector<Columna> lista=new Vector<Columna>();
            while(rs.next()){
                Columna columna=new Columna();
                columna.setId(rs.getLong(1));
                Informe in=new Informe();
                in.setId(rs.getLong(2));
                columna.setInforme(in);                
                columna.setDescripcion(rs.getString(3));
                columna.setTipo(rs.getString(4));
                columna.setTotalizado(rs.getBoolean(5));
                columna.setPosicion(rs.getInt(6));
                columna.setLocal(rs.getBoolean(7));
                lista.add(columna);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Columna>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Informe> ObtenerInformes(String busqueda) {
        Connection con=null;
        String sql="select idinforme,descripcion,no_de_fechas,horizontal from informe where(descripcion like ? or idinforme like ?) ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,"%"+busqueda+"%");
            ps.setString(2,busqueda+"%");
            ResultSet rs=ps.executeQuery();
            Vector<Informe> lista=new Vector<Informe>();
            while(rs.next()){
                Informe i=new Informe();
                i.setId(rs.getLong(1));
                i.setDescripcion(rs.getString(2));
                i.setNumero_de_fechas(rs.getInt(3));
                i.setHorizontal(rs.getBoolean(4));
                lista.add(i);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Informe>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Informe ObtenerUltimoInforme() {
        Connection con=null;
        String sql="select idinforme,descripcion,no_de_fechas,horizontal from informe order by idinforme desc limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Informe i=new Informe();
                long id=rs.getLong(1);                
                i.setId(id);
                i.setDescripcion(rs.getString(2));
                i.setNumero_de_fechas(rs.getInt(3));
                i.setHorizontal(rs.getBoolean(4));
                return i;
            }
            return new Informe();
        } catch (SQLException ex) {
            Logger.getLogger(InformeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
