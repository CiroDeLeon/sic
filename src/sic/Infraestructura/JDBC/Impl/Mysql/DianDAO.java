/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC.Impl.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Dominio.Servicios.Dian.Actividad;
import sic.Dominio.Servicios.Dian.RetencionDian;
import sic.Infraestructura.JDBC.Pool;
import sic.Dominio.InterfacesDAO.IDianDAO;
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Servicios.Dian.exogena.Formato1001Abreviado;
import sic.Dominio.Servicios.Dian.exogena.helper.Formato1001Helper;

/**
 *
 * @author FANNY BURGOS
 */
public class DianDAO implements IDianDAO{
    @Override
    public Vector<RetencionDian> ObtenerTablaDeRetenciones(int año) {
        Connection con=null;
        String sql =" select descripcion,base,porcentage,idauxiliar,idretenciondian,idauxiliaranticipo,idaux_autoretenedor,idaux_anticipoautoretenedor ";
               sql+=" from retenciondian where(ano="+año+")  ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            Vector <RetencionDian> lista=new Vector <RetencionDian>();
            while(rs.next()){                
                RetencionDian rd=new RetencionDian();
                rd.setDescripcion(rs.getString(1));
                rd.setBase(rs.getDouble(2));
                rd.setPorcentage(rs.getDouble(3));
                rd.setAuxiliar_retencion(rs.getString(4));
                rd.setAño(año);
                rd.setId(rs.getString(5));                
                rd.setAuxiliar_anticipo_retencion(rs.getString(6));
                rd.setAuxiliar_autoretencion(rs.getString(7));
                rd.setAuxiliar_anticipo_autoretencion(rs.getString(8));
                lista.add(rd);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public RetencionDian AgregarRegistroEnTablaDeRetenciones(RetencionDian rd) {
          String sql =" insert into retenciondian ";
               sql+=" (ano,descripcion,base,porcentage,idauxiliar,idauxiliaranticipo,idaux_autoretenedor,idaux_anticipoautoretenedor) values ";
               sql+=" (?,?,?,?,'"+rd.getAuxiliar_retencion()+"','"+rd.getAuxiliar_anticipo_retencion()+"','"+rd.getAuxiliar_autoretencion()+"','"+rd.getAuxiliar_anticipo_autoretencion()+"')";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setInt(1,rd.getAño());
            ps.setString(2,rd.getDescripcion());
            ps.setDouble(3,rd.getBase());
            ps.setDouble(4,rd.getPorcentage());                        
            ps.executeUpdate();
            return rd;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public RetencionDian ModificarRegistroEnTablaDeRetenciones(Object id, RetencionDian rd) {
        String sql =" update retenciondian set ";
               sql+=" ano=?,descripcion=?,base=?,porcentage=?,idauxiliar='"+rd.getAuxiliar_retencion()+"',idauxiliaranticipo='"+rd.getAuxiliar_anticipo_retencion()+"',idaux_autoretenedor='"+rd.getAuxiliar_autoretencion()+"',idaux_anticipoautoretenedor='"+rd.getAuxiliar_anticipo_autoretencion()+"' ";
               sql+=" where(idretenciondian="+id+") ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setInt(1,rd.getAño());
            ps.setString(2,rd.getDescripcion());
            ps.setDouble(3,rd.getBase());
            ps.setDouble(4,rd.getPorcentage());            
            ps.executeUpdate();
            return rd;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public RetencionDian ObtenerRetencionDian(Object idAuxiliar) {
        Connection con=null;
        String sql =" select descripcion,base,porcentage,idauxiliar,idretenciondian,ano,idauxiliaranticipo,idaux_autoretenedor,idaux_anticipoautoretenedor ";
               sql+=" from retenciondian where(idauxiliar='"+idAuxiliar+"')order by ano Desc  ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                RetencionDian rd=new RetencionDian();
                rd.setDescripcion(rs.getString(1));
                rd.setBase(rs.getDouble(2));
                rd.setPorcentage(rs.getDouble(3));
                rd.setAuxiliar_retencion(rs.getString(4));                
                rd.setId(rs.getString(5));
                rd.setAño(rs.getInt(6));
                rd.setAuxiliar_anticipo_retencion(rs.getString(7));
                rd.setAuxiliar_autoretencion(rs.getString(8));
                rd.setAuxiliar_anticipo_autoretencion(rs.getString(9));
                return rd;   
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Actividad PersistirActividad(Actividad actividad) {
           String sql =" insert into actividad ";
               sql+=" (idactividad,nombre,PorcentageCree) values ";
               sql+=" (?,?,?) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                        
            ps.setInt(1,actividad.getCodigo());
            ps.setString(2,actividad.getDescripcion());
            ps.setDouble(3,actividad.getPorcentage());            
            ps.executeUpdate();
            return actividad;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Actividad> ObtenerActividades(String b) {
        Connection con=null;
        String sql =" select idactividad,nombre,porcentagecree ";
               sql+=" from actividad where(idactividad like '"+b+"%' or nombre like '%"+b+"%')  ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            Vector<Actividad> lista=new Vector<Actividad>();
            while(rs.next()){                
                Actividad a=new Actividad();
                a.setCodigo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setPorcentage(rs.getDouble(3));
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Actividad>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Actividad ObtenerActividad(int idactividad) {
        Connection con=null;
        String sql =" select idactividad,nombre,porcentagecree ";
               sql+=" from actividad where(idactividad="+idactividad+")  ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                Actividad a=new Actividad();
                a.setCodigo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setPorcentage(rs.getDouble(3));
                return a;   
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Municipio PersistirMunicipio(Municipio municipio) {
        String sql =" insert into municipio ";
               sql+=" (idmunicipio,descripcion,iddpto,descripciondpto,idpais,descripcionpais)";
               sql+="values ("+municipio.getId()+",?,"+municipio.getIddpto()+",?,"+municipio.getIdpais()+",?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setString(1,municipio.getDescripcion());
            ps.setString(2,municipio.getDescripciondpto());
            ps.setString(3,municipio.getDescripcionpais());
            ps.executeUpdate();
            return municipio;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Municipio ObtenerMunicipio(Object idMunicipio) {
        Connection con=null;
        String sql ="select idmunicipio,descripcion,iddpto,descripciondpto,idpais,descripcionpais ";
               sql+="from municipio where(idmunicipio="+idMunicipio+") limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                int iddpto=rs.getInt(3);
                String descripciondpto=rs.getString(4);
                int idpais=rs.getInt(5);
                String descripcionpais=rs.getString(6);
                Municipio m=new Municipio();
                m.setId(id);
                m.setDescripcion(descripcion);
                m.setIddpto(iddpto);
                m.setDescripciondpto(descripciondpto);
                m.setIdpais(idpais);
                m.setDescripcionpais(descripcionpais);
                return m;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Municipio> ObtenerMunicipios(String busqueda) {
        Connection con=null;
        String sql =" select idmunicipio,descripcion,iddpto,descripciondpto,idpais,descripcionpais ";
               sql+=" from municipio where(descripcion like ? or descripciondpto like ? or idmunicipio like ?)  ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,"%"+busqueda+"%");            
            ps.setString(2,"%"+busqueda+"%");  
            ps.setString(3,""+busqueda+""); 
            ResultSet rs=ps.executeQuery();
            Vector <Municipio> lista=new Vector <Municipio>();
            while(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                int iddpto=rs.getInt(3);
                String descripciondpto=rs.getString(4);
                int idpais=rs.getInt(5);
                String descripcionpais=rs.getString(6);
                Municipio m=new Municipio();
                m.setId(id);
                m.setDescripcion(descripcion);
                m.setIddpto(iddpto);
                m.setDescripciondpto(descripciondpto);
                m.setIdpais(idpais);
                m.setDescripcionpais(descripcionpais);
                lista.add(m);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public TipoDocumento PersistirTipoDocumento(TipoDocumento tipodocumento) {
        String sql =" insert into tipodocumento ";
               sql+=" (idtipodocumento,descripcion,abreviatura) values ("+tipodocumento.getId()+",?,?)";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setString(1,tipodocumento.getDescripcion());
            ps.setString(2,tipodocumento.getAbreviatura());
            ps.executeUpdate();
            return tipodocumento;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public TipoDocumento ObtenerTipoDocumento(Object idTipoDocumento) {
        Connection con=null;
        String sql="select idtipodocumento,descripcion,abreviatura from tipodocumento where(idtipodocumento="+idTipoDocumento+") limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                String abreviatura=rs.getString(3);
                TipoDocumento td=new TipoDocumento();
                td.setId(id);
                td.setAbreviatura(abreviatura);
                td.setDescripcion(descripcion);
                return td;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<TipoDocumento> ObtenerTipoDocumentos(String busqueda) {
        Connection con=null;
        String sql =" select idtipodocumento,descripcion,abreviatura from tipodocumento ";
               sql+=" where(abreviatura like '"+busqueda+"%' or descripcion like '%"+busqueda+"%')";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <TipoDocumento> lista=new Vector <TipoDocumento>();
            while(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                String abreviaturaa=rs.getString(3);
                TipoDocumento td=new TipoDocumento();
                td.setId(id);
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                lista.add(td);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public TipoDocumento ObtenerTipoDocumento(String abreviatura) {
        Connection con=null;
        String sql="select idtipodocumento,descripcion,abreviatura from tipodocumento where(abreviatura='"+abreviatura+"') limit 1";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                String abreviaturaa=rs.getString(3);
                TipoDocumento td=new TipoDocumento();
                td.setId(id);
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                return td;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Formato1001Abreviado> ObtenerFormato(Date fecha_inicial, Date fecha_final) {
        Connection con=null;
        String sql ="  ";
               sql+=" select distinct ";
               sql+=" puc.idCta_T, ";
               sql+=" puc.Denominacion, ";
               sql+=" puc.idCtaPUC, ";
               sql+=" SUM(asiento.Debito) ";
               sql+=" from  ";
               sql+=" documento,asiento,puc ";
               sql+=" where( ";
               sql+=" documento.idDocumento=asiento.idDocumento and ";
               sql+=" documento.Activo=true and ";
               sql+=" asiento.idCtaPUC=puc.idCtaPUC and ";
               sql+=" documento.FechaContable>=? and ";
               sql+=" documento.FechaContable<=? and ";
               sql+=" asiento.credito=0 and ";
               //sql+=" asiento.debito>=500000 and ";
               sql+=" (documento.Abreviatura='CE' or ";
               sql+="  documento.Abreviatura='EG' or ";
               sql+="  documento.Abreviatura='EQ'  ) ";
               sql+=" )group by puc.idCtaPUC  order by puc.idCta_T ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setDate(1,new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(2,new java.sql.Date(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();
            Vector <Formato1001Abreviado> lista=new Vector();
            while(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);
                String cta=rs.getString(3);
                double valor=rs.getDouble(4);
                Formato1001Abreviado f=new Formato1001Abreviado();
                f.setId_cuenta_t(id);
                f.setDescripcion(descripcion);
                f.setId_cuenta(cta);
                f.setPagado(valor);
                lista.add(f);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Formato1001Helper> ObtenerFormato1001Helper(Date fecha_inicial, Date fecha_final) {
         Connection con=null;
        String sql ="  ";
               sql+=" select distinct ";
               sql+=" puc.idCta_T, ";
               sql+=" puc.Denominacion, ";               
               sql+=" SUM(asiento.Debito) ";
               sql+=" from  ";
               sql+=" documento,asiento,puc ";
               sql+=" where( ";
               sql+=" documento.idDocumento=asiento.idDocumento and ";
               sql+=" documento.Activo=true and ";
               sql+=" asiento.idCtaPUC=puc.idCtaPUC and ";
               sql+=" documento.FechaContable>=? and ";
               sql+=" documento.FechaContable<=? and ";
               sql+=" asiento.credito=0 and ";
              // sql+=" asiento.debito>=500000 and ";
               sql+=" (documento.Abreviatura='CE' or ";
               sql+="  documento.Abreviatura='EG' or ";
               sql+="  documento.Abreviatura='EQ'  ) ";
               sql+=" )group by puc.idCta_T order by puc.idCta_T ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setDate(1,new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(2,new java.sql.Date(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();
            Vector <Formato1001Helper> lista=new Vector();
            while(rs.next()){
                Object id=rs.getObject(1);
                String descripcion=rs.getString(2);                
                double valor=rs.getDouble(3);
                Formato1001Helper f=new Formato1001Helper();
                f.setId_cuenta_t(id);
                f.setDescripcion(descripcion);
                f.setValor(valor);
                lista.add(f);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public RetencionDian ObtenerRetencionDianPorId(Object id) {
        Connection con=null;
        String sql =" select descripcion,base,porcentage,idauxiliar,idretenciondian,ano,idauxiliaranticipo,idaux_autoretenedor,idaux_anticipoautoretenedor ";
               sql+=" from retenciondian where(idretenciondian="+id.toString()+") ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                RetencionDian rd=new RetencionDian();                
                rd.setDescripcion(rs.getString(1));
                rd.setBase(rs.getDouble(2));
                rd.setPorcentage(rs.getDouble(3));
                rd.setAuxiliar_retencion(rs.getString(4));                
                rd.setId(rs.getString(5));
                rd.setAño(rs.getInt(6));
                rd.setAuxiliar_anticipo_retencion(rs.getString(7));
                rd.setAuxiliar_autoretencion(rs.getString(8));
                rd.setAuxiliar_anticipo_autoretencion(rs.getString(9));
                return rd;   
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DianDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
}
