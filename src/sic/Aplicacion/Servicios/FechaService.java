/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios;

import java.util.Date;
import sic.Infraestructura.JDBC.Impl.Mysql.FechaDAO;
import sic.Dominio.InterfacesDAO.IFechaDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class FechaService {
    private IFechaDAO dao;
    public FechaService() {
        dao=new FechaDAO();
    }
    public int RestarFechasPorDias(Date f1,Date f2){
        long tf1=f1.getTime();
        long tf2=f2.getTime();
        long r=tf2-tf1;
        long dia=60*60*24*1000;        
        return Math.round(r/dia);
    }
    public int RestarFechasPorHoras(Date f1,Date f2){
        long tf1=f1.getTime();
        long tf2=f2.getTime();
        long r=tf2-tf1;
        long hora=60*60*1000;        
        return Math.round(r/hora);
    }
    public int RestarFechasPorMinutos(Date f1,Date f2){
        long tf1=f1.getTime();
        long tf2=f2.getTime();
        long r=tf2-tf1;
        long minuto=60*1000;        
        return Math.round(r/minuto);
    }
    public int RestarFechasPorSegundos(Date f1,Date f2){
        long tf1=f1.getTime();
        long tf2=f2.getTime();
        long r=tf2-tf1;
        long segundos=1000;        
        return Math.round(r/segundos);
    }
    public String ObtenerFormatoDeRestaDeFechas(Date f1,Date f2){
        int dias=this.RestarFechasPorDias(f1, f2);
        int horas=Math.round(this.RestarFechasPorHoras(f1, f2)-24*dias);
        int minutos=this.RestarFechasPorMinutos(f1, f2)-(60*horas)-(24*dias*60);
        int segundos=this.RestarFechasPorSegundos(f1, f2)-(60*minutos)-(60*horas*60)-(24*dias*60*60);
        return ""+dias+"d ,"+horas+"h ,"+minutos+"m,"+(segundos)+"s";
    }
    
    public Date getFechaActual(){
        return dao.ObtenerFechaDelServidor();
    }
    public Date ObtenerFechaRestando(Date f,int dias){
        int dia=1000*60*60*24;
        Date salida=new Date(f.getTime()-dia);
        return salida;
    }
    /**
     * @return the dao
     */
    public IFechaDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(IFechaDAO dao) {
        this.dao = dao;
    }
    
}
