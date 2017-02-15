/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Subcontabilidad;

import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Documento.DocumentoService;

/**
 *
 * @author Usuario1
 */
public class SubcontabilidadService {
    private sic.Dominio.InterfacesDAO.ISubcontabilidadDAO dao;    
    public SubcontabilidadService() {
        dao=new sic.Infraestructura.JDBC.Impl.Mysql.SubcontabilidadDAO();
    }
    public Vector<Subcontabilidad> ObtenerHijos(long idSubcontabilidad){
        Iterator<Subcontabilidad> it=dao.ObtenerHijos(idSubcontabilidad).iterator();
        Vector<Subcontabilidad> lista=new Vector();
        while(it.hasNext()){
            Subcontabilidad sub=it.next();
            lista.add(sub);
            Vector<Subcontabilidad> lista_sub=this.ObtenerHijos(sub.getId());
            if(lista_sub.size()>0){
               lista.addAll(lista_sub);
            }
        }
        return lista;
    }
    public boolean isEsteril(long idSubcontabilidad){
        return new DocumentoService().getDao().ExisteDocumentoConSubcontabilidad(idSubcontabilidad);
    }
    public sic.Dominio.InterfacesDAO.ISubcontabilidadDAO getDao() {
        return dao;
    }
}
