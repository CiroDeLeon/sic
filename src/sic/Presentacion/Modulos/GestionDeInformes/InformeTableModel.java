/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.GestionDeInformes;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.GeneradorDeInformes.Informe;

/**
 *
 * @author Usuario1
 */
public class InformeTableModel extends AbstractTableModel{
    Vector <Informe> lista;
    String columns[]={"id","Descripcion","fechas","horizontal","vertical"};
    public InformeTableModel() {
        lista=new Vector <Informe>();
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
        Informe cta=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return cta.getId();
            case 1 : return cta.getDescripcion();
            case 2 : return cta.getNumero_de_fechas();
            case 3 : return cta.isHorizontal();
            case 4 : return !cta.isHorizontal();
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {        
        switch(columnIndex){
            case 3: return Boolean.class;
            case 4: return Boolean.class;    
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
    
    public Informe getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Informe> lista) {
        this.lista = lista;
    }
 
}
