/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC.Impl.Mysql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.Reportes.DocumentoRep;
import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.InterfacesDAO.IDocumentoDAO;
import sic.Infraestructura.JDBC.Pool;
import java.sql.Connection;
import java.util.Date;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Usuario.UsuarioService;

/**
 *
 * @author FANNY BURGOS
 */
public class DocumentoDAO implements IDocumentoDAO{
    @Override
    public Documento PersistirDocumento(Documento d) {
        String sql="";
        sql+=" insert into documento ";
        sql+="(iddocumento,idCta_T_Usuario,fechacreacion,fechacontable,resumen,creador,numeracion,tdocumento,abreviatura,activo,resolucion,norma_local,norma_internacional,idsubcontabilidad) ";
        sql+=" values ";
        sql+=" ("+d.getId()+","+d.getUsuario().getId()+",now(),?,?,?,?,?,?,true,?,?,?,?) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());                                 
            ps.setTimestamp(1,new java.sql.Timestamp(d.getFechaContable().getTime()));
            ps.setString(2,d.getResumen());
            ps.setString(3,d.getCreador());
            ps.setInt(4,d.getNumeracion());
            ps.setString(5,d.getTdocumento());
            ps.setString(6,d.getAbreviatura());
            ps.setInt(7,d.getResolucion());
            ps.setBoolean(8,d.isNorma_local());
            ps.setBoolean(9,d.isNorma_internacional());
            if(d.getSubcontabilidad()==null){
                ps.setLong(10,0);
            }else{
                ps.setLong(10,d.getSubcontabilidad().getId());
            }
            ps.executeUpdate();
            return d;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public long ObtenerIdUltimoDocumento() {
        Connection con=null;
        String sql="select Max(iddocumento) from documento ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Asiento PersistirAsiento(Asiento a) {
     String sql =" insert into asiento ";
     sql+="(idCtaPUC,idDocumento,detalle,debito,credito,entradas,salidas,nofactura,nofacturacompra,baseiva,basertf,tiporegistro,idCentroDeCosto) ";
     sql+=" values ";
     sql+=" ("+a.getCtaPuc().getId()+","+a.getDocumento().getId()+",?,?,?,?,?,?,?,?,?,?,?) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setString(1,a.getDetalle());                
            ps.setDouble(2,a.getDebito());
            ps.setDouble(3,a.getCredito());
            ps.setDouble(4,a.getEntradas());
            ps.setDouble(5,a.getSalidas());
            ps.setInt(6,a.getNoFactura());
            ps.setInt(7,a.getNoFacturaCompra());
            ps.setDouble(8,a.getBaseIVA());
            ps.setDouble(9,a.getBaseRTF());
            ps.setInt(10,a.getTiporegistro());
            if(a.getCentro_de_costos()!=null){
                ps.setLong(11,a.getCentro_de_costos().getId());
            }else{
                ps.setLong(11,0);
            }
            ps.executeUpdate();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public int ObtenerNumeracion(String tipodocumento,int resolucion) {
        Connection con=null;
        String sql="select Max(documento.numeracion) from documento,asiento where(documento.iddocumento=asiento.iddocumento and tdocumento=? and resolucion=?)";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,tipodocumento);
            ps.setInt(2,resolucion);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public boolean AnularDocumento(Object idDocumento, String anulador, String razon) {
        String sql =" update documento set ";
               sql+=" anulador=?,razonanulacion=?,fechaanulacion=curdate(),activo=false,modificador=? where(iddocumento="+idDocumento+")";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setString(1,anulador);
            ps.setString(2,razon);
            ps.setString(3,anulador);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }   
    @Override
    public boolean ModificarDocumento(Object idDocumento, Documento documento) {
        String sql =" update documento set ";
               sql+=" idCta_T_Usuario=?,fechacontable=?,resumen=?,Modificador=?,fechaanulacion=curdate(),numeracion=?,norma_local=?,norma_internacional=?,idsubcontabilidad=? ";
               sql+=" where(iddocumento="+idDocumento+") ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setObject(1,documento.getUsuario().getId());
            ps.setTimestamp(2,new java.sql.Timestamp(documento.getFechaContable().getTime()));
            ps.setString(3,documento.getResumen());
            ps.setString(4,documento.getModificador());
            ps.setInt(5,documento.getNumeracion());
            ps.setBoolean(6,documento.isNorma_local());
            ps.setBoolean(7,documento.isNorma_internacional());
            if(documento.getSubcontabilidad()!=null){
               ps.setLong(8,documento.getSubcontabilidad().getId());
            }else{
               ps.setLong(8,0); 
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }   
    @Override
     public int ObtenerNumeracion(String tipodocumento) {
        Connection con=null;
        String sql="select Max(documento.numeracion) from documento where(tdocumento=?)";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,tipodocumento);            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){                
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<DocumentoRep> ObtenerReporteDocumento(String tipodocumento, int numeracion) {
        String sql ="";
               sql+=" select \n";
               sql+=" usuario.NoDocumento as NoDocumento  , \n";
               sql+=" concat_ws(' ',usuario.RazonSocial,usuario.Nombre,usuario.Apellido,usuario.Sapellido) as Usuario ,\n";
               sql+=" CONCAT(municipio.Descripcion,',',municipio.DescripcionDPTO,'-',municipio.DescripcionPAIS)as Ubicacion,\n";
               sql+=" usuario.Direccion,\n";
               sql+=" documento.idDocumento,\n";
               sql+=" documento.FechaContable,\n";
               sql+=" documento.FechaCreacion,   \n";
               sql+=" documento.FechaAnulacion,\n";
               sql+=" documento.Resumen,\n";
               sql+=" documento.RazonAnulacion,\n";
               sql+=" documento.Creador,\n";
               sql+=" documento.Anulador,\n";
               sql+=" documento.Modificador,\n";
               sql+=" documento.Numeracion,\n";
               sql+=" documento.TDocumento,\n";
               sql+=" documento.Abreviatura,\n";
               sql+=" documento.Activo ,\n";
               sql+=" asiento.idAsiento,\n";
               sql+=" asiento.idCtaPUC as idc,\n";
               sql+=" asiento.Detalle,\n";
               sql+=" asiento.Debito,\n";
               sql+=" asiento.Credito,\n";
               sql+=" asiento.Entradas,\n";
               sql+=" asiento.Salidas,\n";
               sql+=" asiento.NoFactura,\n";
               sql+=" asiento.NoFacturaCompra,\n";
               sql+=" asiento.BaseIVA,\n";
               sql+=" asiento.BaseRTF, \n";
               sql+=" (select denominacion from puc where(puc.idctapuc=substr(CONCAT(idc,''),1,8))) as denominacion, \n";               
               sql+=" documento.idsubcontabilidad ,\n";
               sql+=" asiento.idCentroDeCosto  ";
               sql+=" from\n";
               sql+=" documento,municipio,asiento,usuario\n";
               sql+=" where( \n";
               sql+="         documento.tdocumento='"+tipodocumento+"'  and     \n";
               sql+="         documento.Numeracion="+numeracion+"    and\n";
               sql+="         documento.idDocumento=asiento.idDocumento  and\n";
               sql+="         documento.IdCta_T_Usuario=usuario.idCta_T and\n";
               sql+="         usuario.idMunicipio=municipio.idMunicipio \n";
               sql+="       )        \n";
         
               
               Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <DocumentoRep> lista=new Vector <DocumentoRep>();
            double sumadebito=0;
            double sumacredito=0;
            while(rs.next()){                
               DocumentoRep d=new DocumentoRep();
               d.setNodocumento(rs.getString(1));
               d.setUsuario(rs.getString(2));
               d.setUbicacion(rs.getString(3));
               d.setDireccion(rs.getString(4));
               d.setIdDocumento(rs.getObject(5));
               d.setFechacontable(rs.getString(6));
               d.setFechacreacion(rs.getString(7).replace("00:00:00.0",""));
               if(rs.getString(8)!=null){
                  d.setFechaanulacion(rs.getString(8).replace("00:00:00.0",""));
               }
               d.setResumen(rs.getString(9));
               if(rs.getString(10)!=null){
                  d.setRazonanulacion(rs.getString(10));
               }
               d.setCreador(rs.getString(11));
               d.setAnulador(rs.getString(12));
               d.setModificador(rs.getString(13));
               d.setNumeracion(rs.getInt(14));
               d.setTDocumento(rs.getString(15));
               d.setAbreviatura(rs.getString(16));
               d.setActivo(rs.getBoolean(17));
               d.setIdAsiento(rs.getObject(18));
               d.setIdctapuc(rs.getString(19));
               d.setDetalle(rs.getString(20));
               d.setDebito(rs.getDouble(21));
               d.setCredito(rs.getDouble(22));
               d.setEntradas(rs.getDouble(23));
               d.setSalidas(rs.getDouble(24));
               d.setNoFactura(rs.getInt(25));
               d.setNoFacturaCompra(rs.getInt(26));
               d.setBaseIVA(rs.getDouble(27));
               d.setBaseRTF(rs.getDouble(28));
               d.setDenominacion(rs.getString(29));
               sumadebito+=d.getDebito();
               sumacredito+=d.getCredito();
               if(rs.isLast()){
                   d.setSumadebito(sumadebito);
                   d.setSumacredito(sumacredito);
               }
               Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(rs.getLong(30));
               if(sub!=null){
                  d.setSubcontabilidad(sub.toString());
               }else{
                  d.setSubcontabilidad(0+"-Ninguna");
               }
               lista.add(d);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
        
    }
    @Override
    public Documento ObtenerDocumento(String tipodocumento, int numeracion) {
        String sql =" ";
               sql+=" select \n";                        
               sql+=" documento.idDocumento,\n";
               sql+=" documento.FechaContable,\n";
               sql+=" documento.FechaCreacion,   \n";
               sql+=" documento.FechaAnulacion,\n";
               sql+=" documento.Resumen,\n";
               sql+=" documento.RazonAnulacion,\n";
               sql+=" documento.Creador,\n";
               sql+=" documento.Anulador,\n";               
               sql+=" documento.Numeracion,\n";
               sql+=" documento.TDocumento,\n";
               sql+=" documento.Abreviatura,\n";
               sql+=" documento.Activo ,\n";               
               sql+=" documento.idcta_t_usuario, \n";                              
               sql+=" documento.modificador, \n";                              
               sql+=" documento.norma_local,";
               sql+=" documento.norma_internacional, ";
               sql+=" documento.idsubcontabilidad";
               sql+=" from\n";
               sql+=" documento \n";
               sql+=" where( \n";               
               sql+="         documento.tdocumento='"+tipodocumento+"'  and     \n";
               sql+="         documento.Numeracion="+numeracion+"    \n";                              
               sql+="       )limit 1        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                Documento d=new Documento();
                d.setId(rs.getObject(1));
                d.setFechaContable(new java.util.Date(rs.getDate(2).getTime()));
                d.setFechaCreacion(new java.util.Date(rs.getDate(3).getTime()));
                d.setActivo(rs.getBoolean(12));
                if(d.isActivo()==false){
                   d.setRazonAnulacion(rs.getString(6));
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));
                   d.setAnulador(rs.getString(8));
                }else{
                   if(rs.getDate(4)!=null)
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));                    
                }
                d.setResumen(rs.getString(5));                
                d.setCreador(rs.getString(7));                
                d.setNumeracion(rs.getInt(9));
                d.setTdocumento(rs.getString(10));
                d.setAbreviatura(rs.getString(11));
                
                UsuarioService usuario_service=new UsuarioService();                
                Usuario u=usuario_service.getDao().ObtenerUsuario(rs.getObject(13));
                d.setUsuario(u);
                d.setModificador(rs.getString(14));
                d.setNorma_local(rs.getBoolean(15));
                d.setNorma_internacional(rs.getBoolean(16));
                Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(rs.getLong(17));                
                d.setSubcontabilidad(sub);
                return d;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Asiento> ObtenerAsientos(Object idDocumento) {
         String sql ="";
               sql+=" select \n";         
               sql+=" asiento.idAsiento,\n";
               sql+=" asiento.idCtaPUC,\n";
               sql+=" asiento.Detalle,\n";
               sql+=" asiento.Debito,\n";
               sql+=" asiento.Credito,\n";
               sql+=" asiento.Entradas,\n";
               sql+=" asiento.Salidas,\n";
               sql+=" asiento.NoFactura,\n";
               sql+=" asiento.NoFacturaCompra,\n";
               sql+=" asiento.BaseIVA,\n";
               sql+=" asiento.BaseRTF, \n";               
               sql+=" asiento.tiporegistro, \n";               
               sql+=" asiento.idCentroDeCosto \n";               
               sql+=" from\n";
               sql+=" asiento \n";
               sql+=" where( \n";               
               sql+="         asiento.idDocumento="+idDocumento+"   \n";                              
               sql+="       )        \n";
         
               
               Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <Asiento> lista=new Vector <Asiento>();
            sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService service=new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService();
            while(rs.next()){                                                                
                Asiento a=new Asiento();
                a.setId(rs.getObject(1));
                   Cta_PUC cta=new Cta_PUC();
                   cta.setId(rs.getObject(2));
                a.setCtaPuc(cta);                   
                a.setDetalle(rs.getString(3));
                a.setDebito(rs.getDouble(4));
                a.setCredito(rs.getDouble(5));
                a.setEntradas(rs.getDouble(6));
                a.setSalidas(rs.getDouble(7));
                a.setNoFactura(rs.getInt(8));
                a.setNoFacturaCompra(rs.getInt(9));
                a.setBaseIVA(rs.getDouble(10));
                a.setBaseRTF(rs.getDouble(11));
                a.setTiporegistro(rs.getInt(12));
                long idc=rs.getLong(13);
                if(idc!=0){
                   a.setCentro_de_costos(service.getDao().ObtenerCentroDeCosto(idc));
                }else{
                   a.setCentro_de_costos(null);
                }
                Documento d=new Documento();
                d.setId(idDocumento);
                a.setDocumento(d);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector <Asiento>();       // si hay error return null 
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public boolean ModificarAsiento(Object idAsiento, Asiento a) {
        String sql =" update asiento set ";
               sql+=" idctapuc=?,iddocumento=?, ";
               sql+=" detalle=?,debito=?,credito=?,entradas=?, ";
               sql+=" salidas=?,nofactura=?,nofacturacompra=?,baseiva=?,basertf=?,tiporegistro=?,idCentroDeCosto=? where(idasiento="+idAsiento+") ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setObject(1,a.getCtaPuc().getId());
            ps.setObject(2,a.getDocumento().getId());
            ps.setString(3,a.getDetalle());
            ps.setDouble(4,a.getDebito());
            ps.setDouble(5,a.getCredito());
            ps.setDouble(6,a.getEntradas());
            ps.setDouble(7,a.getSalidas());
            ps.setInt(8,a.getNoFactura());
            ps.setInt(9,a.getNoFacturaCompra());
            ps.setDouble(10,a.getBaseIVA());
            ps.setDouble(11,a.getBaseRTF());
            ps.setInt(12,a.getTiporegistro());
            if(a.getCentro_de_costos()!=null){
               ps.setLong(13,a.getCentro_de_costos().getId());
            }else{
               ps.setLong(13,0); 
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Documento ObtenerDocumentoDescuadrado() {
        Connection con=null;
      String sql ="   ";
             sql+="select \n";
             sql+="asiento.idDocumento , \n";
             sql+="documento.TDocumento, \n";
             sql+="documento.Numeracion, \n";
             sql+="sum(asiento.Debito) as debito, \n";
             sql+="sum(asiento.Credito) as credito, \n";
             sql+="abs(sum(asiento.Debito)-sum(asiento.Credito)) as diferencia \n";
             sql+="from  \n";
             sql+=" documento,asiento \n";
             sql+=" where( \n";
             sql+=" documento.idDocumento=asiento.idDocumento and \n";
             sql+=" documento.Activo=true  \n";
             sql+=" )group by idDocumento order by diferencia DESC limit 1 \n";
             
        try {
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                double diferencia=rs.getDouble(6);
                if(diferencia>0){
                   Documento d=this.ObtenerDocumento(rs.getString(2),rs.getInt(3));
                   return d;
                }                
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Documento ObtenerDocumento(Object idDocumento) {
        String sql =" ";
               sql+=" select \n";                        
               sql+=" documento.idDocumento,\n";
               sql+=" documento.FechaContable,\n";
               sql+=" documento.FechaCreacion,   \n";
               sql+=" documento.FechaAnulacion,\n";
               sql+=" documento.Resumen,\n";
               sql+=" documento.RazonAnulacion,\n";
               sql+=" documento.Creador,\n";
               sql+=" documento.Anulador,\n";               
               sql+=" documento.Numeracion,\n";
               sql+=" documento.TDocumento,\n";
               sql+=" documento.Abreviatura,\n";
               sql+=" documento.Activo ,\n";               
               sql+=" documento.idcta_t_usuario,\n";                              
               sql+=" documento.modificador, \n";      
               sql+=" documento.norma_local,";
               sql+=" documento.norma_internacional, ";
               sql+=" documento.idsubcontabilidad ";
               sql+=" from\n";
               sql+=" documento\n";
               sql+=" where( \n";               
               sql+="         documento.iddocumento="+idDocumento+"       \n";                              
               sql+="       )        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                Documento d=new Documento();
                d.setId(rs.getObject(1));
                d.setFechaContable(new java.util.Date(rs.getDate(2).getTime()));
                d.setFechaCreacion(new java.util.Date(rs.getDate(3).getTime()));
                d.setActivo(rs.getBoolean(12));
                if(d.isActivo()==false){
                   d.setRazonAnulacion(rs.getString(6));
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));
                   d.setAnulador(rs.getString(8));
                }else{
                   if(rs.getDate(4)!=null)
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));                    
                }
                d.setResumen(rs.getString(5));                
                d.setCreador(rs.getString(7));                
                d.setNumeracion(rs.getInt(9));
                d.setTdocumento(rs.getString(10));
                d.setAbreviatura(rs.getString(11));                
                Usuario usuario=(new UsuarioService().getDao()).ObtenerUsuario(rs.getObject(13));
                d.setModificador(rs.getString(14));
                d.setNorma_local(rs.getBoolean(15));
                d.setNorma_internacional(rs.getBoolean(16));
                Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(rs.getLong(17));                
                d.setSubcontabilidad(sub);
                d.setUsuario(usuario);
                return d;
            }
            System.out.println("documento no encontrado");
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Documento> ObtenerDocumentos(String tipodocumento) {
        String sql =" ";
               sql+=" select \n";                        
               sql+=" documento.idDocumento,\n";
               sql+=" documento.FechaContable,\n";
               sql+=" documento.FechaCreacion,   \n";
               sql+=" documento.FechaAnulacion,\n";
               sql+=" documento.Resumen,\n";
               sql+=" documento.RazonAnulacion,\n";
               sql+=" documento.Creador,\n";
               sql+=" documento.Anulador,\n";               
               sql+=" documento.Numeracion,\n";
               sql+=" documento.TDocumento,\n";
               sql+=" documento.Abreviatura,\n";
               sql+=" documento.Activo ,\n";               
               sql+=" usuario.idcta_t ,\n";               
               sql+=" usuario.nodocumento ,\n";  
               sql+=" usuario.nombre ,\n";               
               sql+=" usuario.snombre ,\n";               
               sql+=" usuario.apellido ,\n";               
               sql+=" usuario.sapellido ,\n";               
               sql+=" usuario.razonsocial ,\n";               
               sql+=" usuario.sobrenombre ,\n";    
               sql+=" usuario.regimen ,\n";               
               sql+=" usuario.retenedorrenta ,\n";               
               sql+=" usuario.telefono ,\n";               
               sql+=" usuario.direccion ,\n";               
               sql+=" usuario.correo ,\n";                                         
               sql+=" usuario.digitodian , \n"; 
               sql+=" municipio.idmunicipio , \n";                                           
               sql+=" municipio.descripcion , \n";                                           
               sql+=" municipio.iddpto , \n";                                           
               sql+=" municipio.descripciondpto , \n";                                           
               sql+=" municipio.idpais , \n";                                           
               sql+=" municipio.descripcionpais , \n";                                           
               sql+=" documento.Modificador,\n";
               sql+=" tipodocumento.idtipodocumento ,\n";
               sql+=" tipodocumento.descripcion ,\n";
               sql+=" tipodocumento.abreviatura, \n";
               sql+=" usuario.retenedoriva , \n"; 
               sql+=" usuario.retenedorica , \n"; 
               sql+=" usuario.autoretenedor , \n"; 
               sql+=" usuario.retenedorcree, \n"; 
               sql+=" documento.norma_local ,\n";               
               sql+=" documento.norma_internacional, \n";               
               sql+=" documento.idsubcontabilidad ";
               sql+=" from\n";
               sql+=" documento,municipio,usuario,tipodocumento\n";
               sql+=" where( \n";               
               sql+="         documento.tdocumento='"+tipodocumento+"'  and     \n";
               //sql+="         documento.Numeracion="+numeracion+"    and\n";               
               sql+="         usuario.idtipodocumento=tipodocumento.idtipodocumento  and     \n";
               sql+="         documento.IdCta_T_Usuario=usuario.idCta_T and\n";
               sql+="         usuario.idMunicipio=municipio.idMunicipio \n";
               sql+="       )        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();            
            Vector <Documento> lista=new Vector <Documento>();
            while(rs.next()){                
                Documento d=new Documento();
                d.setId(rs.getObject(1));
                d.setFechaContable(new java.util.Date(rs.getDate(2).getTime()));
                d.setFechaCreacion(new java.util.Date(rs.getDate(3).getTime()));
                d.setActivo(rs.getBoolean(12));
                if(d.isActivo()==false){
                   d.setRazonAnulacion(rs.getString(6));
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));
                   d.setAnulador(rs.getString(8));
                }else{
                   if(rs.getDate(4)!=null)
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));                    
                }
                d.setResumen(rs.getString(5));                
                d.setCreador(rs.getString(7));                
                d.setNumeracion(rs.getInt(9));
                d.setTdocumento(rs.getString(10));
                d.setAbreviatura(rs.getString(11));
                d.setModificador(rs.getString(33));
                d.setNorma_local(rs.getBoolean(41));
                d.setNorma_internacional(rs.getBoolean(42));
                Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(rs.getLong(43));                
                d.setSubcontabilidad(sub);
                
                Usuario u=new Usuario();
                u.setId(rs.getObject(13));                                
                u.setNoDocumento(rs.getLong(14));
                u.setNombre(rs.getString(15));
                u.setsNombre(rs.getString(16));
                u.setApellido(rs.getString(17));
                u.setsApellido(rs.getString(18));
                u.setRazonSocial(rs.getString(19));
                u.setSobreNombre(rs.getString(20));
                u.setRegimen(rs.getString(21));
                u.setRetenedor_de_renta(rs.getBoolean(22));
                u.setTelefono(rs.getString(23));
                u.setDireccion(rs.getString(24));
                u.setCorreo(rs.getString(25));
                u.setDigitoDIAN(rs.getString(26));
                
                
                Municipio m=new Municipio();
                m.setId(rs.getObject(27));
                m.setDescripcion(rs.getString(28));
                m.setIddpto(rs.getInt(29));
                m.setDescripciondpto(rs.getString(30));
                m.setIdpais(rs.getInt(31));
                m.setDescripcionpais(rs.getString(32));
                
                TipoDocumento td=new TipoDocumento();
                td.setId(rs.getObject(34));
                td.setDescripcion(rs.getString(35));
                td.setAbreviatura(rs.getString(36));
                u.setRetenedor_de_reteiva(rs.getBoolean(37));
                u.setRetenedor_de_ica(rs.getBoolean(38));
                u.setAutoretenedor_renta(rs.getBoolean(39));
                u.setRetenedor_de_cree(rs.getBoolean(40));
                u.setTipodocumento(td);
                u.setMunicipio(m);
                d.setUsuario(u);
                lista.add(d);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public boolean EliminarAsientos(Object idDocumento) {
        String sql ="  delete  from asiento  ";               
               sql+="  where(asiento.iddocumento="+idDocumento+") ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());           
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<Integer> ObtenerColeccionNumeracion(String tipodocumento, Date fecha_inicial, Date fecha_final) {
        String sql =" ";
               sql+=" select \n";                        
               sql+=" documento.numeracion\n";               
               sql+=" from\n";
               sql+=" documento\n";
               sql+=" where( \n";               
               sql+="        documento.tdocumento='"+tipodocumento+"'  and     \n";
               sql+="        documento.FechaContable>=? and \n";
               sql+="        documento.FechaContable<=?  \n";
               sql+="       )order by fechacontable,numeracion        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setDate(1,new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(2,new java.sql.Date(fecha_final.getTime()));
            ResultSet rs=ps.executeQuery();            
            Vector<Integer> v=new Vector<Integer>();
            while(rs.next()){                
                v.add(new Integer(rs.getInt(1)));                
            }
            return v;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Integer>();        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    // muestra los td que tiene norma_local true y norma_internacional true
    @Override
    public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal_NormaInternacional() {
        String sql =" select tdocumento,abreviatura from documento where(documento.Norma_Local=true and documento.Norma_Internacional=true)group by tdocumento order by iddocumento";               
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <TipoDocumentoContable> lista=new Vector <TipoDocumentoContable>();
            while(rs.next()){                
                String descripcion=rs.getString(1);
                String abreviaturaa=rs.getString(2);                
                TipoDocumentoContable td=new TipoDocumentoContable();                
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                td.setUltimanumeracion(this.ObtenerNumeracion(descripcion));
                lista.add(td);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    // muestra los td que tienen norma_internacional true y norma local false
    @Override
    public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaInternacional() {
        String sql =" select tdocumento,abreviatura from documento where(documento.Norma_Local=false and documento.Norma_Internacional=true)group by tdocumento order by iddocumento";               
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <TipoDocumentoContable> lista=new Vector <TipoDocumentoContable>();
            while(rs.next()){                
                String descripcion=rs.getString(1);
                String abreviaturaa=rs.getString(2);                
                TipoDocumentoContable td=new TipoDocumentoContable();                
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                td.setUltimanumeracion(this.ObtenerNumeracion(descripcion));
                lista.add(td);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
   // muestra los td que tiene norma_local true y norma_internacional false
    @Override
    public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentosContablesNormaLocal() {
        String sql =" select tdocumento,abreviatura from documento where(documento.Norma_Local=true and documento.Norma_Internacional=false)group by tdocumento order by iddocumento";               
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <TipoDocumentoContable> lista=new Vector <TipoDocumentoContable>();
            while(rs.next()){                
                String descripcion=rs.getString(1);
                String abreviaturaa=rs.getString(2);                
                TipoDocumentoContable td=new TipoDocumentoContable();                
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                td.setUltimanumeracion(this.ObtenerNumeracion(descripcion));
                lista.add(td);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Documento ObtenerUltimoDocumentoActivo(String tipo_documento){
        String sql =" ";
               sql+=" select \n";                        
               sql+=" documento.idDocumento,\n";
               sql+=" documento.FechaContable,\n";
               sql+=" documento.FechaCreacion,   \n";
               sql+=" documento.FechaAnulacion,\n";
               sql+=" documento.Resumen,\n";
               sql+=" documento.RazonAnulacion,\n";
               sql+=" documento.Creador,\n";
               sql+=" documento.Anulador,\n";               
               sql+=" documento.Numeracion,\n";
               sql+=" documento.TDocumento,\n";
               sql+=" documento.Abreviatura,\n";
               sql+=" documento.Activo ,\n";               
               sql+=" documento.idcta_t_usuario,\n";                              
               sql+=" documento.modificador, \n";      
               sql+=" documento.norma_local,";
               sql+=" documento.norma_internacional, ";
               sql+=" documento.idsubcontabilidad ";
               sql+=" from\n";
               sql+=" documento\n";
               sql+=" where( \n";               
               sql+="         documento.activo=true  and documento.TDocumento=?    \n";                              
               sql+="       )order by idDocumento desc limit 1        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,tipo_documento);
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                Documento d=new Documento();
                d.setId(rs.getObject(1));
                d.setFechaContable(new java.util.Date(rs.getDate(2).getTime()));
                d.setFechaCreacion(new java.util.Date(rs.getDate(3).getTime()));
                d.setActivo(rs.getBoolean(12));
                if(d.isActivo()==false){
                   d.setRazonAnulacion(rs.getString(6));
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));
                   d.setAnulador(rs.getString(8));
                }else{
                   if(rs.getDate(4)!=null)
                   d.setFechaAnulacion(new java.util.Date(rs.getDate(4).getTime()));                    
                }
                d.setResumen(rs.getString(5));                
                d.setCreador(rs.getString(7));                
                d.setNumeracion(rs.getInt(9));
                d.setTdocumento(rs.getString(10));
                d.setAbreviatura(rs.getString(11));                
                Usuario usuario=(new UsuarioService().getDao()).ObtenerUsuario(rs.getObject(13));
                d.setModificador(rs.getString(14));
                d.setNorma_local(rs.getBoolean(15));
                d.setNorma_internacional(rs.getBoolean(16));
                Subcontabilidad sub=new sic.Dominio.Core.Subcontabilidad.SubcontabilidadService().getDao().Obtener(rs.getLong(17));                
                d.setSubcontabilidad(sub);
                d.setUsuario(usuario);
                return d;
            }
            System.out.println("documento no encontrado");
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public boolean ExisteDocumentoConSubcontabilidad(long idSubcontabilidad) {
        String sql =" ";
               sql+=" select \n";                        
               sql+="  * \n";               
               sql+=" from\n";
               sql+=" documento\n";
               sql+=" where( \n";               
               sql+="        documento.idsubcontabilidad="+idSubcontabilidad+"  and     \n";
               sql+="        documento.activo=true  \n";               
               sql+="       )        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());           
            ResultSet rs=ps.executeQuery();                        
            if(rs.next()){                
                return true;          
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public boolean ExisteAsientoConCentroDeCosto(long idCentroDeCosto) {
          String sql =" ";
               sql+=" select distinct \n";                        
               sql+=" asiento.idasiento \n";               
               sql+=" from\n";
               sql+=" documento,asiento \n";
               sql+=" where( \n";               
               sql+="        asiento.idCentroDeCosto is not null  and     \n";
               sql+="        asiento.idCentroDeCosto="+idCentroDeCosto+"  and     \n";
               sql+="        documento.idDocumento=asiento.iddocumento  and     \n";
               sql+="        documento.activo=true  \n";               
               sql+="       )        \n";        
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());           
            ResultSet rs=ps.executeQuery();                        
            if(rs.next()){                
                return true;          
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Vector<TipoDocumentoContable> ObtenerTiposDeDocumentos(String modo) {
        String sql="";
        if(modo.toLowerCase().equals("local")){
           sql =" select tdocumento,abreviatura from documento where(documento.Norma_Local=true)group by tdocumento order by iddocumento";               
        }else{
           sql =" select tdocumento,abreviatura from documento where(documento.Norma_Internacional=true)group by tdocumento order by iddocumento";                   
        }
        Connection con=null;
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            Vector <TipoDocumentoContable> lista=new Vector <TipoDocumentoContable>();
            while(rs.next()){                
                String descripcion=rs.getString(1);
                String abreviaturaa=rs.getString(2); 
                //System.out.println(descripcion);
                TipoDocumentoContable td=new TipoDocumentoContable();                
                td.setAbreviatura(abreviaturaa);
                td.setDescripcion(descripcion);
                td.setUltimanumeracion(this.ObtenerNumeracion(descripcion));
                lista.add(td);                
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
