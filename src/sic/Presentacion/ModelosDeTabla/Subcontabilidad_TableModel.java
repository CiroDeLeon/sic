/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Subcontabilidad.SubcontabilidadService;

/**
 *
 * @author Usuario1
 */
public class Subcontabilidad_TableModel extends AbstractTableModel{
   private Vector <Subcontabilidad> lista;
    String columns[]={"id","descripcion","padre"};
    public Subcontabilidad_TableModel() {
        lista=new Vector <Subcontabilidad>();
    }
    @Override
    public int getRowCount() {
        return lista.size();
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Subcontabilidad td=this.lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return td.getId();
            case 1 : return td.getDescripcion();            
            case 2 : 
                     if(td.getPadre()!=null){
                         Subcontabilidad s=new SubcontabilidadService().getDao().Obtener(td.getPadre().getId());
                         return s.getDescripcion();
                     }else{
                         return "    ";
                     } 
            default: return "";    
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;            
            default: return String.class;    
        }
    }    
    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public Subcontabilidad getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Subcontabilidad> lista) {
        this.lista = lista;
    } 
}
