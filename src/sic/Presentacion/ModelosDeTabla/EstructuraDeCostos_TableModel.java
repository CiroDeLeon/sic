/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuario1
 */
public class EstructuraDeCostos_TableModel extends AbstractTableModel{
     Vector <sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos> lista;
    String columns[]={"Cod","Descripcion"};
    public EstructuraDeCostos_TableModel() {
        lista=new Vector <sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos>();
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
        sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos obj=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return obj.getId();
            case 1 : return obj.getDescripcion();
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;    
    }    
        
    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos> lista) {
        this.lista = lista;
    }

   
}
