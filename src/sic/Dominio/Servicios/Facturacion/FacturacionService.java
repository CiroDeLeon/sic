/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Facturacion;

import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Infraestructura.JDBC.Impl.Mysql.FacturacionDAO;
import sic.Dominio.InterfacesDAO.IFacturacionDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class FacturacionService {
   private IFacturacionDAO dao;
   public  FacturacionService() {
        dao=new FacturacionDAO();
    }
   public  Vector<SaldoFactura> ObtenerFacturasPorCobrar(Usuario u,String busqueda){              
       Iterator <SaldoFactura> it=getDao().ObtenerSaldos(u.getId(),busqueda).iterator();
       Vector <SaldoFactura> lista=new Vector <SaldoFactura>();       
       while(it.hasNext()){
           SaldoFactura sa=it.next();
           sa.setUsuario(u.NombreCompleto());
           sa.setNit(u.getNoDocumentoCompleto());
           if(sa.getFactura()!=0){
              if(sa.getSaldo()!=0 ){
                 lista.add(sa);
              }
           }
       }       
       return lista;
   }
   public IFacturacionDAO getDao() {
        return dao;
   }
}
