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
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Usuario.UsuarioService;
import sic.Dominio.Servicios.Dian.Actividad;
import sic.Dominio.Servicios.Dian.DianService;
import sic.Dominio.InterfacesDAO.IUsuarioDAO;
import sic.Infraestructura.JDBC.Pool;

/**
 *
 * @author FANNY BURGOS
 */
public class UsuarioDAO implements IUsuarioDAO{   
    @Override
    public Usuario PersistirUsuario(Usuario usuario) {
        String sql =" insert into usuario ";
               sql+="(idcta_t,idmunicipio,idTipoDocumento,nodocumento,nombre,snombre,apellido,sapellido,";
               sql+="razonsocial,sobrenombre,regimen,retenedorrenta,telefono,direccion,correo, ";
               sql+="digitodian,retenedoriva,retenedorica,idactividad,autoretenedor,retenedorcree,autoretenedor_iva,autoretenedor_ica,autoretenedor_cree,cupo_credito) ";
               sql+=" values ";
               sql+=" ("+usuario.getId()+","+usuario.getMunicipio().getId()+","+usuario.getTipodocumento().getId()+",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setLong(1,usuario.getNoDocumento());
            ps.setString(2,usuario.getNombre());
            ps.setString(3,usuario.getsNombre());
            ps.setString(4,usuario.getApellido());
            ps.setString(5,usuario.getsApellido());
            ps.setString(6,usuario.getRazonSocial());
            ps.setString(7,usuario.getSobreNombre());
            ps.setString(8,usuario.getRegimen());
            ps.setBoolean(9,usuario.isRetenedor_de_renta());
            ps.setString(10,usuario.getTelefono());
            ps.setString(11,usuario.getDireccion());
            ps.setString(12,usuario.getCorreo());            
            ps.setString(13,usuario.getDigitoDIAN());
            ps.setBoolean(14,usuario.isRetenedor_de_reteiva());
            ps.setBoolean(15,usuario.isRetenedor_de_cree());
            if(usuario.getActividad()!=null)
               ps.setInt(16,usuario.getActividad().getCodigo());
            else
               ps.setInt(16,0);    
            ps.setBoolean(17,usuario.isAutoretenedor_renta());
            ps.setBoolean(18,usuario.isRetenedor_de_cree());
            ps.setBoolean(19,usuario.isAutoretenedor_iva());
            ps.setBoolean(20,usuario.isAutoretenedor_ica());
            ps.setBoolean(21,usuario.isAutoretenedor_cree());
            ps.setDouble(22,usuario.getCupo_credito());
            ps.executeUpdate();
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
    @Override
    public Usuario ObtenerUsuario(long NoDocumento, Object idTipoDocumento) {
        Connection con=null;
        String sql ="select idcta_t,idmunicipio,idtipodocumento,nodocumento, ";
               sql+="       nombre,snombre,apellido,sapellido,razonsocial, ";
               sql+="       sobrenombre,regimen,retenedorrenta,telefono,";
               sql+="       direccion,correo,digitoDIAN,retenedoriva,retenedorica,idactividad,autoretenedor,retenedorcree,autoretenedor_iva,autoretenedor_ica,autoretenedor_cree,cupo_credito ";
               sql+="       from usuario ";
               sql+="       where( ";               
               sql+="         nodocumento="+NoDocumento+" and ";
               sql+="         idtipodocumento="+idTipoDocumento+" ";               
               sql+="       ) limit 1 ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();
            DianService dian_service=new DianService();
            if(rs.next()){
                Object idcta_t=rs.getObject(1);
                Object idmunicipio=rs.getObject(2);
                Object idtipodocumento=rs.getObject(3);
                long nodocumento=rs.getLong(4);
                String nombre=rs.getString(5);
                String snombre=rs.getString(6);
                String apellido=rs.getString(7);
                String sapellido=rs.getString(8);
                String razonsocial=rs.getString(9);
                String sobrenombre=rs.getString(10);
                String regimen=rs.getString(11);
                boolean retenedor_renta=rs.getBoolean(12);
                String telefono=rs.getString(13);
                String direccion=rs.getString(14);
                String correo=rs.getString(15);
                String digitoDIAN=rs.getString(16);
                boolean retenedor_iva=rs.getBoolean(17);
                boolean retenedor_ica=rs.getBoolean(18);                
                int idActividad=rs.getInt(19);
                boolean autoretenedor=rs.getBoolean(20);
                boolean retenedor_cree=rs.getBoolean(21);
                Municipio m=new Municipio();
                m.setId(idmunicipio);
                TipoDocumento td=new TipoDocumento();
                td.setId(idtipodocumento);
                Actividad a=dian_service.getDao().ObtenerActividad(idActividad);
                Usuario u=new Usuario(m,td, NoDocumento, nombre, snombre, apellido, sapellido, razonsocial, sobrenombre, telefono, direccion, correo, regimen, retenedor_renta,digitoDIAN,retenedor_iva,retenedor_ica,a,autoretenedor,retenedor_cree);
                u.setId(idcta_t);
                u.setAutoretenedor_iva(rs.getBoolean(22));
                u.setAutoretenedor_ica(rs.getBoolean(23));
                u.setAutoretenedor_cree(rs.getBoolean(24));
                u.setCupo_credito(rs.getDouble(25));
                return u;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    @Override
    public Vector<Usuario> ObtenerUsuarios(String busqueda) {
         Connection con=null;
        String sql ="select usuario.idcta_t,usuario.idmunicipio,usuario.idtipodocumento,usuario.nodocumento, ";               
               sql+="       usuario.nombre,usuario.snombre,usuario.apellido,usuario.sapellido,usuario.razonsocial, ";
               sql+="       usuario.sobrenombre,usuario.regimen,usuario.retenedorrenta,usuario.telefono,";
               sql+="       usuario.direccion,usuario.correo,usuario.digitoDIAN, ";
               sql+="       municipio.descripcion,municipio.descripciondpto,municipio.descripcionpais, ";
               sql+="       tipodocumento.descripcion,tipodocumento.abreviatura,usuario.retenedoriva,retenedorica, ";
               sql+="       usuario.idactividad,actividad.nombre,actividad.porcentagecree,usuario.autoretenedor,usuario.retenedorcree,usuario.autoretenedor_iva,usuario.autoretenedor_ica,usuario.autoretenedor_cree,cupo_credito ";
               sql+="       from usuario,municipio,tipodocumento,actividad ";
               sql+="       where( ";               
               sql+="           municipio.idmunicipio=usuario.idmunicipio and ";
               sql+="           usuario.idactividad=actividad.idactividad and ";
               sql+="           tipodocumento.idtipodocumento=usuario.idtipodocumento and ( ";
               sql+="           usuario.NoDocumento like ?  or ";               
               sql+="           Concat_WS(' ',usuario.nombre,usuario.apellido) like ?  or ";               
               sql+="           Concat_WS(' ',usuario.sobrenombre) like ?  or ";   
               sql+="           Concat_WS(' ',usuario.razonsocial) like ?  or ";   
               sql+="           Concat_WS(' ',usuario.nombre,usuario.snombre,usuario.apellido,usuario.sapellido) like ?  ) ";
               sql+="       ) ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ps.setString(1,"%"+busqueda+"%");            
            ps.setString(2,"%"+busqueda+"%");  
            ps.setString(3,"%"+busqueda+"%"); 
            ps.setString(4,"%"+busqueda+"%"); 
            ps.setString(5,"%"+busqueda+"%"); 
            ResultSet rs=ps.executeQuery();
            Vector <Usuario> lista=new Vector <Usuario>();
            while(rs.next()){
                Object idcta_t=rs.getObject(1);
                Object idmunicipio=rs.getObject(2);
                Object idtipodocumento=rs.getObject(3);
                long nodocumento=rs.getLong(4);
                String nombre=rs.getString(5);
                String snombre=rs.getString(6);
                String apellido=rs.getString(7);
                String sapellido=rs.getString(8);
                String razonsocial=rs.getString(9);
                String sobrenombre=rs.getString(10);
                String regimen=rs.getString(11);
                boolean retenedor_renta=rs.getBoolean(12);
                String telefono=rs.getString(13);
                String direccion=rs.getString(14);
                String correo=rs.getString(15);
                String digitoDIAN=rs.getString(16);
                Municipio m=new Municipio();
                m.setId(idmunicipio);
                String descripcion=rs.getString(17);
                m.setDescripcion(descripcion);
                String descripciondpto=rs.getString(18);
                m.setDescripciondpto(descripciondpto);
                String descripcionpais=rs.getString(19);
                m.setDescripcionpais(descripcionpais);
                TipoDocumento td=new TipoDocumento();
                td.setId(idtipodocumento);
                String descripciontd=rs.getString(20);
                td.setDescripcion(descripciontd);
                String abreviatura=rs.getString(21);
                td.setAbreviatura(abreviatura);
                boolean retenedor_iva=rs.getBoolean(22);
                boolean retenedor_ica=rs.getBoolean(23);
                int codactividad=rs.getInt(24);
                String descripcion_actividad=rs.getString(25);
                double porcentage_cree=rs.getDouble(26);        
                boolean autoretenedor=rs.getBoolean(27);
                boolean retenedor_cree=rs.getBoolean(28);
                
                Actividad a=new Actividad(codactividad,descripcion_actividad,porcentage_cree);
                Usuario u=new Usuario(m,td,nodocumento, nombre, snombre, apellido, sapellido, razonsocial, sobrenombre, telefono, direccion, correo, regimen,retenedor_renta,digitoDIAN,retenedor_iva,retenedor_ica,a,autoretenedor,retenedor_cree);
                u.setId(idcta_t);
                u.setAutoretenedor_iva(rs.getBoolean(29));
                u.setAutoretenedor_ica(rs.getBoolean(30));
                u.setAutoretenedor_cree(rs.getBoolean(31));
                u.setCupo_credito(rs.getDouble(32));
                lista.add(u);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    } 
    @Override
    public Usuario ModificarUsuario(Object id, Usuario usuario) {
        String sql =" update usuario ";
               sql+="set idcta_t="+id+",idmunicipio="+usuario.getMunicipio().getId()+",idTipoDocumento="+usuario.getTipodocumento().getId()+",nodocumento=?,nombre=?,snombre=?,apellido=?,sapellido=?,";
               sql+="razonsocial=?,sobrenombre=?,regimen=?,retenedorrenta=?,telefono=?,direccion=?,correo=?,digitodian=?,retenedoriva=?,retenedorica=?,idactividad=?,autoretenedor=?,retenedorcree=?,autoretenedor_iva=?,autoretenedor_ica=?,autoretenedor_cree=?,cupo_credito=? ";
               sql+="where(usuario.idcta_t="+id+") ";            
        Connection con=null;        
        try {                        
            con=Pool.ObtenerConexion();        
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());            
            ps.setLong(1,usuario.getNoDocumento());
            ps.setString(2,usuario.getNombre());
            ps.setString(3,usuario.getsNombre());
            ps.setString(4,usuario.getApellido());
            ps.setString(5,usuario.getsApellido());
            ps.setString(6,usuario.getRazonSocial());
            ps.setString(7,usuario.getSobreNombre());
            ps.setString(8,usuario.getRegimen());
            ps.setBoolean(9,usuario.isRetenedor_de_renta());
            ps.setString(10,usuario.getTelefono());
            ps.setString(11,usuario.getDireccion());
            ps.setString(12,usuario.getCorreo());            
            ps.setString(13,usuario.getDigitoDIAN());
            ps.setBoolean(14,usuario.isRetenedor_de_reteiva());
            ps.setBoolean(15,usuario.isRetenedor_de_ica());
            ps.setInt(16,usuario.getActividad().getCodigo());
            ps.setBoolean(17,usuario.isAutoretenedor_renta());
            ps.setBoolean(18,usuario.isRetenedor_de_cree());
            ps.setBoolean(19,usuario.isAutoretenedor_iva());
            ps.setBoolean(20,usuario.isAutoretenedor_ica());
            ps.setBoolean(21,usuario.isAutoretenedor_cree());
            ps.setDouble(22,usuario.getCupo_credito());
            ps.executeUpdate();
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
    @Override
    public Usuario ObtenerUsuario(Object idUsuario) {
         Connection con=null;
        String sql ="select idcta_t,idmunicipio,idtipodocumento,nodocumento, ";
               sql+="       nombre,snombre,apellido,sapellido,razonsocial, ";
               sql+="       sobrenombre,regimen,retenedorrenta,telefono,";
               sql+="       direccion,correo,digitoDIAN,retenedoriva,retenedorica,idactividad,autoretenedor,";
               sql+="       retenedorcree,autoretenedor_iva,autoretenedor_ica,autoretenedor_cree,cupo_credito ";
               sql+="       from usuario ";
               sql+="       where( ";               
               sql+="         idcta_t="+idUsuario+"  ";               
               sql+="       ) limit 1 ";
        try {            
            con=Pool.ObtenerConexion();
            PreparedStatement ps=con.prepareStatement(sql.toLowerCase());
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){
                Object idcta_t=rs.getObject(1);
                Object idmunicipio=rs.getObject(2);
                Object idtipodocumento=rs.getObject(3);
                long nodocumento=rs.getLong(4);
                String nombre=rs.getString(5);
                String snombre=rs.getString(6);
                String apellido=rs.getString(7);
                String sapellido=rs.getString(8);
                String razonsocial=rs.getString(9);
                String sobrenombre=rs.getString(10);
                String regimen=rs.getString(11);
                boolean retenedor_renta=rs.getBoolean(12);
                String telefono=rs.getString(13);
                String direccion=rs.getString(14);
                String correo=rs.getString(15);
                String digitoDIAN=rs.getString(16);
                boolean retenedor_iva=rs.getBoolean(17);
                boolean retenedor_ica=rs.getBoolean(18);
                int idactividad=rs.getInt(19);
                boolean autoretenedor=rs.getBoolean(20);
                boolean retenedor_cree=rs.getBoolean(21);
                
                Municipio m=new DianService().getDao().ObtenerMunicipio(idmunicipio);                
                TipoDocumento td=new DianService().getDao().ObtenerTipoDocumento(idtipodocumento);                
                Actividad a=(new DianService().getDao()).ObtenerActividad(idactividad);
                Usuario u=new Usuario(m,td,nodocumento, nombre, snombre, apellido, sapellido, razonsocial, sobrenombre, telefono, direccion, correo, regimen, retenedor_renta,digitoDIAN,retenedor_iva,retenedor_ica,a,autoretenedor,retenedor_cree);
                u.setId(idcta_t);
                u.setAutoretenedor_iva(rs.getBoolean(22));
                u.setAutoretenedor_ica(rs.getBoolean(23));
                u.setAutoretenedor_cree(rs.getBoolean(24));
                u.setCupo_credito(rs.getDouble(25));
                return u;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;        
        }finally{
            Pool.LiberarConexion(con);
        }
    }
}