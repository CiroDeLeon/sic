/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Cta_T.Cta_T;

/**
 *
 * @author FANNY BURGOS
 */
public class CtaTModel extends AbstractTableModel{
    Vector <Cta_T> lista;
    String columns[]={"Cod. CTA T","Descripcion"};
    public CtaTModel() {
        lista=new Vector <Cta_T>();
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
        Cta_T cta=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return cta.getId();
            case 1 : return cta.getDescripcion();
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
    
    public Cta_T getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Cta_T> lista) {
        this.lista = lista;
    }

    
}
