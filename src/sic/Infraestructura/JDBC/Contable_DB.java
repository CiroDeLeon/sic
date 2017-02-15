/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Infraestructura.JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import sic.Infraestructura.JDBC.DataBaseConfiguration;

/**
 *
 * @author FANNY BURGOS
 */
public class Contable_DB {
public Properties getProperties() {
        try
        {
            //se crea una instancia a la clase Properties
            Properties propiedades = new Properties();
            //se leen el archivo .properties
            String curDir =System.getProperty("user.dir");
            FileInputStream fis=new FileInputStream(curDir+"\\Contable_DB.properties");
            propiedades.load(fis);            
            //si el archivo de propiedades NO esta vacio retornan las propiedes leidas
            if (!propiedades.isEmpty()) 
            {                
                return propiedades;
            } else {//sino  retornara NULL
                return null;
            }
        } catch (IOException ex) {
            return null;
        }
   }
  public void InicializarPropiedades(){
      Properties p=this.getProperties();
      String dc=p.getProperty("driverclass");
      String ps=p.getProperty("password");
      String ur=p.getProperty("url");
      String u=p.getProperty("user");
      DataBaseConfiguration.ObtenerInstancia().setDriverclass(dc);
      DataBaseConfiguration.ObtenerInstancia().setPassword(ps);
      DataBaseConfiguration.ObtenerInstancia().setUrl(ur);
      DataBaseConfiguration.ObtenerInstancia().setUser(u);
  }    
}
