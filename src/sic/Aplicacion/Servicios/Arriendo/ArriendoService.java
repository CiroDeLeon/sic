/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Arriendo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.Aplicacion.Servicios.FechaService;
import sic.Aplicacion.Servicios.Renovacion.Renovacion;
import sic.Aplicacion.Servicios.Renovacion.RenovacionService;

/**
 *
 * @author FANNY BURGOS
 */
public class ArriendoService {
   public void CortarServicio(){
       FileWriter fw = null;
       String ruta=System.getProperty("user.dir")+File.separator+"DatosDeEmpresa.txt";
       System.out.println(ruta);
       File f=new File(ruta);
       //f.mkdirs();
       try{
            fw = new FileWriter(ruta);  
            PrintWriter out = new PrintWriter(fw); 
            out.println(false);
        } catch (IOException ex) {
            Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
   }   
   public boolean PasaVerificacionDeServicioDiaria(){
       RenovacionService rs=new RenovacionService();
       FechaService fs=new FechaService();
       Date actual=fs.getFechaActual();
       Date actual_cliente=new Date();
       Renovacion renovacion_servidor=rs.getDao().ObtenerRenovacion(actual.getYear()+1900,actual.getMonth()+1);
       Renovacion renovacion_cliente=rs.getDao().ObtenerRenovacion(actual_cliente.getYear()+1900,actual_cliente.getMonth()+1);
       if(renovacion_servidor!=null && renovacion_cliente!=null){
          if(rs.isValida(renovacion_servidor) && rs.isValida(renovacion_cliente)){
             return true;
          } 
          this.CortarServicio();
          return false;
       }else{
          if(actual.getDate()>=5 || actual_cliente.getDate()>=5){
              this.CortarServicio();
              return false;
          } 
          return true;
       }
   }
   public void ReconexionDelServicio(){
       FileWriter fw = null;
       String ruta=System.getProperty("user.dir")+File.separator+"DatosDeEmpresa.txt";
       File f=new File(ruta);
       System.out.println(ruta);
      // f.mkdirs();
       try{
            fw = new FileWriter(ruta);  
            PrintWriter out = new PrintWriter(fw); 
            out.println(true);
        } catch (IOException ex) {
            Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
   }
   public boolean isActivoServicio(){
       FileReader fw = null;
       String ruta=System.getProperty("user.dir")+File.separator+"DatosDeEmpresa.txt";
       //File f=new File(ruta);
       //f.mkdirs();
       try{
            fw = new FileReader(ruta);  
            BufferedReader in = new BufferedReader(fw); 
            String servicio=in.readLine();
            if(servicio.equals("true")){
                return true;
            }else{
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ArriendoService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
   }
}
