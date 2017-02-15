/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC.Impl.Mysql;

import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.InterfacesDAO.IPucDAO;
import sic.Infraestructura.JDBC.Pool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.util.Iterator;


/**
 *
 * @author FANNY BURGOS
 */
public class PucDAO implements IPucDAO{
    @Override
    public Cta_PUC PersistirCtaPuc(Cta_PUC cta) {
        Cta_T ctat=cta.getCtat();
        String sql =" insert into puc ";
        if(ctat!=null){        
               sql+=" (idCtaPuc,idCta_T,denominacion,tipocta,requierenit,publico,categoria)";
               sql+="values ("+cta.getId()+","+ctat.getId()+",?,?,?,?,?)";
        }else{
               sql+=" (idCtaPuc,idCta_T,denominacion,tipocta,requierenit,publico,categoria)";
               sql+="values ("+cta.getId()+",null,?,?,?,?,?)";
        }         
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setString(1,cta.getDenominacion());
            ps.setString(2,cta.getTipoCta());
            ps.setBoolean(3,cta.isRequiereNit());
            ps.setBoolean(4,cta.isPublico());
            ps.setString(5,cta.getCategoria());
            ps.executeUpdate();
            return cta;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Cta_PUC ObtenerCtaPuc(Object id) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria ";               
               sql+="from   puc ";
               sql+="where( ";
               sql+="       puc.idctapuc="+id+" ";
               sql+=" )limit 1 ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);                                
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);
                   cta.setCtat(t);
                }
                return cta;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Cta_PUC> ObtenerPUC(String busqueda) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria ";               
               sql+="from   puc ";
               sql+="where( ";
               sql+="       puc.idctapuc<99999999 and ( ";
               sql+="       puc.idctapuc like ? or ";
               sql+="       puc.denominacion like ? ) ";               
               sql+=" ) order by idctapuc ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();
            Vector <Cta_PUC> lista=new Vector <Cta_PUC>();
            while(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);               
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);                
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);   
                   cta.setCtat(t);
                }
                lista.add(cta);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Cta_PUC> InspeccionarAuxiliar(String busqueda, Object idAuxiliar) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria ";               
               sql+="from   puc ";
               sql+="where( ";
               sql+="       puc.idctapuc like '"+idAuxiliar+"%' and ";
               sql+="       length(puc.idctapuc)>8 and ( ";
               sql+="       puc.idctapuc like ? or ";
               sql+="       puc.denominacion like ? ) ";               
               sql+=" ) order by idctapuc";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();
            Vector <Cta_PUC> lista=new Vector <Cta_PUC>();
            while(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);                
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);                
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);   
                   cta.setCtat(t);
                }
                lista.add(cta);
            }
            System.out.println(lista.size());
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Cta_PUC> ObtenerPUC(int clase, int digito) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria ";               
               sql+="from   puc ";
               sql+="where( ";
               sql+="       puc.idctapuc<99999999 and  ";
               sql+="       puc.idctapuc like ?  and";               
               sql+="       length(puc.idctapuc)=?  ";               
               sql+=" ) order by idctapuc ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,clase+"%");
            ps.setInt(2,digito);
            ResultSet rs=ps.executeQuery();
            Vector <Cta_PUC> lista=new Vector <Cta_PUC>();
            while(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);               
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);                
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);   
                   cta.setCtat(t);
                }
                lista.add(cta);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Cta_PUC> ObtenerPUC(Object idCtaT) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria ";               
               sql+="from   puc ";
               sql+="where( ";             
               sql+="       puc.idcta_t=?  ";               
               sql+=" ) order by idctapuc ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setObject(1,idCtaT);            
            ResultSet rs=ps.executeQuery();
            Vector <Cta_PUC> lista=new Vector <Cta_PUC>();
            while(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);               
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);                
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);   
                   cta.setCtat(t);
                }
                lista.add(cta);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public boolean ModificarEnPuc(Object idCtaT, Cta_T t) {
        String sql =" update puc ";
               sql+=" set denominacion=? where(idcta_t="+idCtaT+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,t.getDescripcion());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Cta_PUC ModificarCtaPuc(Object oldid, Cta_PUC cta) {
        String sql =" update puc ";
               sql+=" set denominacion=?,";
               if(cta.getCtat()!=null){
                   sql+=" idCta_T="+cta.getCtat().getId()+",";
               }else{
                   sql+=" idCta_T=null,";
               }
               sql+=" tipocta=?,requierenit=?,publico=?,categoria=? where(idctapuc="+oldid+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setString(1,cta.getDenominacion());
            ps.setString(2,cta.getTipoCta());
            ps.setBoolean(3,cta.isRequiereNit());
            ps.setBoolean(4,cta.isPublico());
            ps.setString(5,cta.getCategoria());
            ps.executeUpdate();
            return cta;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Cta_PUC> ObtenerCuentas(Object idctai, Object idctaf) {
        Connection con=null;
        String sql ="select puc.idctapuc,puc.denominacion,puc.tipocta,puc.requierenit,puc.idcta_t,puc.publico,puc.categoria,CONCAT_WS('',puc.idctapuc) as ord ";               
               sql+="from   puc ";
               sql+="where( ";             
               sql+="  puc.idctapuc>="+idctai+"  and ";               
               sql+="  puc.idctapuc<="+idctaf+"   ";                              
               sql+=" ) order by ord ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector <Cta_PUC> lista=new Vector <Cta_PUC>();
            while(rs.next()){
                Object id_=rs.getObject(1);
                String denominacion=rs.getString(2);
                String tipo=rs.getString(3);
                boolean requierenit=rs.getBoolean(4);               
                Object idt=rs.getObject(5);
                boolean publico=rs.getBoolean(6);                
                String categoria=rs.getString(7);
                Cta_PUC cta=new Cta_PUC();
                cta.setId(id_);
                cta.setDenominacion(denominacion);
                cta.setTipoCta(tipo);
                cta.setRequiereNit(requierenit);
                cta.setPublico(publico);
                cta.setCategoria(categoria);
                if(idt!=null){
                   Cta_T t=new Cta_T();
                   t.setDescripcion(denominacion);
                   t.setId(idt);   
                   cta.setCtat(t);
                }
                lista.add(cta);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PucDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Cta_PUC> ObtenerCompletoPUC(int digito) {
        if(digito==4){
           return this.ObtenerCuentas("1","9999");
        }
        if(digito==6){
           return this.ObtenerCuentas("1","999999");
        }
        if(digito==8){
           return this.ObtenerCuentas("1","99999999");
        }
        return this.ObtenerCuentas("1","99999999999999999999");
        
    }

    @Override
    public Vector<Cta_PUC> ObtenerCuentasDeBalance(int digito) {
        Vector<Cta_PUC> lista=new Vector<Cta_PUC>();
        if(digito==4){
           lista=this.ObtenerCuentas("1","3999");
        }
        if(digito==6){
           lista=this.ObtenerCuentas("1","399999");
        }
        if(digito==8){
           lista=this.ObtenerCuentas("1","39999999");
        }
        Iterator<Cta_PUC> it=lista.iterator();
        Vector<Cta_PUC> resultado=new Vector<Cta_PUC>();
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            if(cta.getId().toString().charAt(0)=='1' || cta.getId().toString().charAt(0)=='2' || cta.getId().toString().charAt(0)=='3'){
                resultado.add(cta);
            }
        }
        return resultado;
    }

    @Override
    public Vector<Cta_PUC> ObtenerCuentasDeResultado(int digito) {
        Vector<Cta_PUC> lista=new Vector<Cta_PUC>();
        if(digito==4){
           lista=this.ObtenerCuentas("4","7999");
        }
        if(digito==6){
           lista=this.ObtenerCuentas("4","799999");
        }
        if(digito==8){
           lista=this.ObtenerCuentas("4","79999999");
        }
        Iterator<Cta_PUC> it=lista.iterator();
        Vector<Cta_PUC> resultado=new Vector<Cta_PUC>();
        while(it.hasNext()){
            Cta_PUC cta=it.next();
            if(cta.getId().toString().charAt(0)=='4' || cta.getId().toString().charAt(0)=='5' || cta.getId().toString().charAt(0)=='6' || cta.getId().toString().charAt(0)=='7'){
                resultado.add(cta);
            }
        }
        return resultado;
        
    }
}