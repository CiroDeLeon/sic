/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Renovacion;

import secure_math.SecureMath;
import sic.Infraestructura.JDBC.Impl.Mysql.RenovacionDAO;
import sic.Dominio.InterfacesDAO.IRenovacionDAO;
import sic.Aplicacion.Servicios.Reportes.DatosDeReporte;
import sic.Aplicacion.Servicios.Reportes.ReporteService;

/**
 *
 * @author FANNY BURGOS
 */
public class RenovacionService {
   private IRenovacionDAO dao;
   private String mensaje="";    
   public RenovacionService() {
        dao=new RenovacionDAO();
    }
   public boolean ValidarRenovacion(Renovacion r){
       Renovacion res=getDao().ObtenerRenovacion(r.getAño(),r.getMes());
       if(res!=null){
           res.setClave(r.getClave());
           if(this.isValida(res)){
                getDao().ModificarRenovacion(res);
               this.mensaje="VALIDADO CORRECTAMENTE";
               return true;
           }else{
               this.mensaje="Clave Incorrecta";
               return false;
           }
       }else{
           if(this.isValida(r)){
                getDao().PersistirRenovacion(r);
               this.mensaje="VALIDADO CORRECTAMENTE";
               return true;
           }else{
               this.mensaje="Clave Incorrecta";
               return false;
           }
       }
   } 
   public static String ObtenerClave(String nit, int año, int mes) {
        double a=Double.parseDouble(nit);
        int ultimo=Integer.parseInt(""+nit.charAt(nit.length()-1));
        int betha=SecureMath.ObtenerPrimo((int) (Math.sqrt(a)/10));
        int alpha=SecureMath.ObtenerPrimo(año+SecureMath.ObtenerNumeroDeParticiones(13-mes));
        long clave=betha*alpha+SecureMath.ObtenerNumeroDeParticiones(ultimo);                
        return (clave+"").replace('0','c').replace('1','i').replace('2','r').replace('3','o')
                .replace('4','m').replace('5','a').replace('6','n').replace('7','u').replace('8','e').replace('9','l').toUpperCase();
       
        
    }    
   public boolean isValida(Renovacion r){
       DatosDeReporte dr=new ReporteService().getDao().ObtenerDatosDeReporte();
       return this.isValidaClave(""+dr.getNit(),r.getAño(),r.getMes(),r.getClave());
   }
   private boolean isValidaClave(String nit, int año, int mes,String clave){
       String key=clave.replace("-","").replace("_","").replace('0','c').replace('1','i').replace('2','r').replace('3','o')
                .replace('4','m').replace('5','a').replace('6','n').replace('7','u').replace('8','e').replace('9','l').toUpperCase();;       
       //System.out.println("key="+key);
       //                                        key 
       
       String clave_=RenovacionService.ObtenerClave(nit, año, mes).replace('0','c').replace('1','i').replace('2','r').replace('3','o')
                .replace('4','m').replace('5','a').replace('6','n').replace('7','u').replace('8','e').replace('9','l').toUpperCase();
      if(clave_.equals(key))       {
          return true;
      }else{
          return false;
      }
   }
   public String getMensaje() {
        return mensaje;
    }
   
    /**
     * @return the dao
     */
    public IRenovacionDAO getDao() {
        return dao;
    }
}
