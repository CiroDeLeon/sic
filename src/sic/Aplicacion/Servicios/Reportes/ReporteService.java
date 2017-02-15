/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Reportes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import sic.Infraestructura.JDBC.Impl.Mysql.DatosDeReporteDAO;
import sic.Dominio.InterfacesDAO.IDatosDeReporteDAO;


/**
 *
 * @author FANNY BURGOS
 */
public class ReporteService {
    private IDatosDeReporteDAO dao;
    public ReporteService() {
        dao=new DatosDeReporteDAO();
    }
    public static void VerReporte(String ArchivoJasper,Vector vr,String titulo){
        try {
            // TODO add your handling code here:
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(vr);
            JasperPrint jp = JasperFillManager.fillReport(ArchivoJasper, null, dataSource);            
            String archivo="Reporte"+ArchivoJasper.replace(".jasper","").replace(".JASPER","");
            String curDir = System.getProperty("user.dir")+"\\Reportes";            
            File directorio = new File(curDir);
            directorio.mkdirs();
            String ruta=curDir+"\\"+archivo;
            System.out.println(ruta);
            JasperExportManager.exportReportToPdfFile(jp,ruta+".pdf");                        
            java.io.File a=new File(ruta+".pdf");
            java.awt.Desktop.getDesktop().open(a);
        } catch (IOException ex) {
            Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
                try {
                   JRBeanCollectionDataSource dataSource;
                   dataSource = new JRBeanCollectionDataSource(vr);
                   JasperPrint jp;
                   jp = JasperFillManager.fillReport(ArchivoJasper, null, dataSource);
                   JasperViewer jv = new JasperViewer(jp, false);
                   jv.setTitle(""+titulo);
                   jv.setVisible(true);
                } catch (JRException ex1) {
                   Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, null, ex1);
                }
        }        
    }       
    
    public static void Imprimir(String ArchivoJasper,Vector vr){        
        try{
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(vr);
            JasperPrint jp = JasperFillManager.fillReport(ArchivoJasper, null, dataSource);            
            JasperPrintManager.printReport(jp,false); 
        } catch (JRException ex1) {
              Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, null, ex1);
        }        
    }
    public void AsignarDatosDeReporte(long nit, String razonsocial, String direccion, String telefono, String publicidad, String regimen){
       DatosDeReporte  dr=new DatosDeReporte(nit,razonsocial,direccion,telefono,publicidad,regimen);
       dr.setId("1");
       if(getDao().PersistirDatosDeReporte(dr)==null){
            getDao().ModificarDatosDeReporte(dr);
       }
    }    
    public IDatosDeReporteDAO getDao() {
        return dao;
    }
}
