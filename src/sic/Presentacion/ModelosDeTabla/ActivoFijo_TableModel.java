/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Activo_Fijo.ActivoFijo;


/**
 *
 * @author Usuario1
 */
public class ActivoFijo_TableModel extends AbstractTableModel{
 Vector <ActivoFijo> lista;
    String columns[]={"Cod.","Descripcion","Vida Util","Valor Local","Valor Niif","Aux Activo","Aux Depreciacion","Aux Gasto Depreciacion "};
    public ActivoFijo_TableModel() {
        lista=new Vector <ActivoFijo>();
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
        ActivoFijo a=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return ""+a.getId();
            case 1 : return a.getDescripcion();
            case 2 : return a.getVida_util_en_dias();
            case 3 : return a.getValor_local();
            case 4 : return a.getValor_niif();    
            case 5 : return a.getAux_activo_fijo();
            case 6 : return a.getAux_activo_depreciacion();
            case 7 : return a.getAux_gasto_depreciacion();
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 2 : return Integer.class;
            case 3 : return Double.class;
            case 4 : return Double.class;    
                
            default :    return String.class;
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
    
    public ActivoFijo getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <ActivoFijo> lista) {
        this.lista = lista;
    }    
}
