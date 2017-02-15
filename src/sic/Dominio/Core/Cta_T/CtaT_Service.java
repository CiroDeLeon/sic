/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Cta_T;

import java.util.Vector;
import sic.Dominio.Core.PUC.PucService;
import sic.Infraestructura.JDBC.Impl.Mysql.CtaTDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class CtaT_Service {
    private sic.Dominio.InterfacesDAO.ICtatDAO dao;
    protected String mensaje="";
    public CtaT_Service() {
        dao=new CtaTDAO();
    }
    public boolean InsertarCtaT(String descripcion){
       Cta_T t=new Cta_T();
       t.setDescripcion(descripcion);
       if(getDaot().ObtenerCtaT(descripcion)==null){
          long id=this.getDaot().ObtenerIdUltimaCtaT();
          if(id!=-1){
             id++; 
             t.setId(""+id);               
             if(getDaot().PersistirCtaT(t)!=null){
                mensaje="Cta T Ingresada con EXITO";
                return true;
             }else{
                mensaje=" Hubo Un Error";
                return false;
             }          
          }else{
              mensaje=" Hubo Un Error";
              return false;
          }
       }else{
           mensaje="Ya Existe esa Cta T";
           return false;
       }
   }
    public Cta_T ObtenerCtaT(String descripcion) {
       return getDaot().ObtenerCtaT(descripcion);
   }    
    public String ObtenerMensaje() {
        return mensaje;
    }
    public Vector<Cta_T> ObtenerCtasT(String busqueda){
        return getDaot().ObtenerCtasT(busqueda);
    }

    /**
     * @return the dao
     */
    public sic.Dominio.InterfacesDAO.ICtatDAO getDaot() {
        return dao;
    }
    public boolean ModificarCtaT(Object id, Cta_T t){
        this.mensaje=""; 
        if(this.dao.ModificarCtaT(id, t)!=null){
            PucService ps=new PucService();
            if(ps.getDao().ModificarEnPuc(id,t)){
               this.mensaje="Modificado Con Exito"; 
               return true;
            }
            this.mensaje="Modificado Con Exito , pero no se Modifico en Libros Auxiliares "; 
            return false;
        }
        this.mensaje="No Se Pudo Modificar"; 
        return false;
    }
}
