/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.GestionDeInformes;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.GeneradorDeInformes.Columna;

/**
 *
 * @author Usuario1
 */
public class ColumnaTableModel extends AbstractTableModel{
    Vector <Columna> lista;
    String columns[]={"posicion","Descripcion","Tipo","Totalizar","Local","Niif"};
    public ColumnaTableModel() {
        lista=new Vector <Columna>();
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
        Columna cta=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return cta.getPosicion();
            case 1 : return cta.getDescripcion();
            case 2 : return cta.getTipo();
            case 3 : return cta.isTotalizado();            
            case 4: return cta.isLocal();
            case 5: return !cta.isLocal();    
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {        
        switch(columnIndex){
            case 3: return Boolean.class;            
            case 4: return Boolean.class;            
            case 5: return Boolean.class;                
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
    
    public Columna getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Columna> lista) {
        this.lista = lista;
    }
}
