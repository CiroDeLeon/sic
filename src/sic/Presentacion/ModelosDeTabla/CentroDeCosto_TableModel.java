/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;

/**
 *
 * @author Usuario1
 */
public class CentroDeCosto_TableModel extends AbstractTableModel {
    private Vector <sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista;
    String columns[]={"posicion","id","descripcion","padre"};
    public CentroDeCosto_TableModel() {
        lista=new Vector <sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto>();
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
        sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto td=this.lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return rowIndex+1;
            case 1 : return td.getId();
            case 2 : return td.getDescripcion();            
            case 3 : 
                     if(td.getPadre()!=null){
                         CentroDeCosto s=new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService().getDao().ObtenerCentroDeCosto(td.getPadre().getId());
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
    
    public sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> lista) {
        this.lista = lista;
    }    
}
