/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.Dian.exogena;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Dian.exogena.Formato1001Abreviado;

/**
 *
 * @author Usuario1
 */
public class Formato1001AbreviadoTableModel extends AbstractTableModel{
    private Vector <Formato1001Abreviado> lista;
    String columns[]={"id","descripcion","Cta PUC","salida"};
    public Formato1001AbreviadoTableModel() {
        lista=new Vector <Formato1001Abreviado>();
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
        Formato1001Abreviado td=this.lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return td.getId_cuenta_t();
            case 1 : return td.getDescripcion();
            case 2 : return td.getId_cuenta();
            case 3 : return td.getPagado();    
            default: return "";    
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return Object.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return Double.class;    
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
    
    public Formato1001Abreviado getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Formato1001Abreviado> lista) {
        this.lista = lista;
    }
}
