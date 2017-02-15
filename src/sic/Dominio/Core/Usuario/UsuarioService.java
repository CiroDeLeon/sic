/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Usuario;



import java.util.Vector;
import sic.Dominio.Core.Cta_T.CtaT_Service;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Core.Usuario.cupo_credito.CupoCreditoPorDefecto;
import sic.Dominio.InterfacesDAO.IUsuarioDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class UsuarioService extends CtaT_Service{
    private IUsuarioDAO dao;    
    
    public  UsuarioService(){
        dao=new sic.Infraestructura.JDBC.Impl.Mysql.UsuarioDAO();
        
    }    
   public Vector<Usuario> ObtenerUsuarios(String busqueda){        
        return getDao().ObtenerUsuarios(busqueda);
    }
   public double getCupoCreditoPorDefecto(){
      return CupoCreditoPorDefecto.getInstance().getCupoDeCreditoPorDefecto();
   }
    public boolean InsertarUsuario(Usuario u){            
        Usuario usuario=getDao().ObtenerUsuario(u.getNoDocumento(),u.getTipodocumento().getId());            
        if(usuario==null){                      
            if(this.InsertarCtaT(u.getDescripcion())==true){
               Cta_T t=this.ObtenerCtaT(u.getDescripcion());
               u.setId(t.getId());
               u.setCupo_credito(sic.Dominio.Core.Usuario.cupo_credito.CupoCreditoPorDefecto.getInstance().getCupoDeCreditoPorDefecto());
               getDao().PersistirUsuario(u);
               mensaje="Ingresado Usuario Con Exito";
               return true;
            }
            mensaje="Hubo Un Error";
            return false;
        }else{
            mensaje="Ese No Documento ya Existe";
            return false;
        }        
    }

    /**
     * @return the dao
     */
    public IUsuarioDAO getDao() {
        return dao;
    }
public boolean ModificarUsuario(Usuario u){                   
           if(this.getDao().ModificarUsuario(u.getId(),u)!=null){
               this.ModificarCtaT(u.getId(),u);
               this.mensaje="Modificado con Exito este Usuario";
               return true;
           }else{
               this.mensaje="No Se Pudo Modificar";
               return false;
           }
    }    

  

    
}
