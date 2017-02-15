/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.PUC;

import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Cta_T.CtaT_Service;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.InterfacesDAO.IPucDAO;
import sic.Infraestructura.JDBC.Impl.Mysql.PucDAO;

/**
 *
 * @author FANNY BURGOS
 */
public class PucService {
    private IPucDAO dao;
    String mensaje="";
    public PucService() {
        dao=new PucDAO();
    }
    public Cta_PUC IngresarDentroDeUnAuxiliar(Object idAuxiliar,Object idCtaT){
       CtaT_Service service=new CtaT_Service();
       Cta_T t=service.getDaot().ObtenerCtaT(idCtaT);
       Cta_PUC cta=dao.ObtenerCtaPuc(idAuxiliar);
       if(this.IngresarEnElPUC(idAuxiliar+""+idCtaT,t.getDescripcion(),cta.getTipoCta(),cta.isRequiereNit(),t,cta.isPublico(),cta.getCategoria())){
           return dao.ObtenerCtaPuc(idAuxiliar+""+idCtaT);
       }
       return null;
    }
    public boolean IngresarEnElPUC(Object codigo,String denominacion,String tipo,boolean requierenit,Cta_T t,boolean publico,String categoria){
        int nivel=codigo.toString().length();
        boolean ExisteCta=true;
        boolean ExisteSubCta=true;        
        if(nivel>=6){
            ExisteCta=this.ObtenerCtaPuc(codigo.toString().substring(0,4))!=null;
            if(ExisteCta==false){
                this.mensaje="La Cuenta No Existe, primero debes crear la cuenta";
                return true;
            }
        }
        if(nivel>6 && nivel<=8){
            ExisteSubCta=this.ObtenerCtaPuc(codigo.toString().substring(0,6))!=null;
            if(ExisteSubCta==false){
                this.mensaje="La Subcuenta No Existe , primero debes crear la subcuenta";
                return false;
            }
        }
        if(getDao().ObtenerCtaPuc(codigo)==null){
            Cta_PUC cta=new Cta_PUC();
            cta.setId(codigo);
            cta.setDenominacion(denominacion);
            cta.setRequiereNit(requierenit);
            cta.setTipoCta(tipo);
            cta.setCtat(t);
            cta.setPublico(publico);
            cta.setCategoria(categoria);
            Cta_PUC c=getDao().PersistirCtaPuc(cta);
            mensaje="Ingresado Con Exito"; 
            if(c==null){
                mensaje="Hubo Un Error";
                return false;
            }
            return true;
        }else{
            mensaje="La CTA Ya Existe"; 
            return false;
        }
    }    
    public Vector <Cta_PUC>ObtenerPUC(String busqueda){
        return getDao().ObtenerPUC(busqueda);
    }
    public Cta_PUC ObtenerCtaPuc(Object id) {
        Cta_PUC cta=getDao().ObtenerCtaPuc(id);        
        return cta;
    }
    public String ObtenerMensaje(){
        return this.mensaje;
    }
    public Vector <Cta_PUC> InspeccionarAuxiliar(String busqueda,Object idAuxiliar){
        return getDao().InspeccionarAuxiliar(busqueda, idAuxiliar);
    }
    public boolean ModificarCTA(Object codigo,String denominacion,String tipo,boolean requierenit,Cta_T t,boolean publico,String categoria){
            Cta_PUC cta=new Cta_PUC();
            cta.setId(codigo);
            cta.setDenominacion(denominacion);
            cta.setRequiereNit(requierenit);
            cta.setTipoCta(tipo);
            cta.setCtat(t);
            cta.setPublico(publico);
            cta.setCategoria(categoria);
            if(this.dao.ModificarCtaPuc(codigo,cta)!=null){
                if(codigo.toString().length()==8){
                    Iterator<Cta_PUC> it=this.InspeccionarAuxiliar("",codigo).iterator();
                    while(it.hasNext()){
                        Cta_PUC cta_=it.next();
                        if(cta_.getId().toString().trim().equals(codigo.toString().trim())==false){
                        cta_.setTipoCta(tipo);
                        cta_.setPublico(publico);
                        cta_.setCategoria(categoria);
                        System.out.println("MODIFICAMOS "+cta_.getDenominacion());
                        dao.ModificarCtaPuc(cta_.getId(),cta_);
                        }
                    }
                }
                this.mensaje="Cta Modificada Con Exito";
                return true;       
            }
            this.mensaje="Error , No Se pudo Modificar";
            return false; 
    }
    public IPucDAO getDao() {
        return dao;
    }
    public Vector<Cta_PUC> ObtenerAuxiliaresT(Object idctai,Object idctaf){
        Vector <Cta_PUC> salida=new Vector <Cta_PUC>();
        Iterator <Cta_PUC> it =dao.ObtenerCuentas(idctai, idctaf).iterator();
        while(it.hasNext()){
            Iterator <Cta_PUC> itaux=dao.InspeccionarAuxiliar("",it.next().getId()).iterator();
            while(itaux.hasNext()){
                salida.add(itaux.next());
            }
        }
        return salida;
    }
}
