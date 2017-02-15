/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.GestionDeInformes;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.GeneradorDeInformes.Consepto;
import sic.Dominio.Core.GeneradorDeInformes.InformeService;

/**
 *
 * @author Usuario1
 */
public class ConseptoTableModel extends AbstractTableModel{
Vector <Consepto> lista;
    String columns[]={"Descripcion","Cuentas"};
    public ConseptoTableModel() {
        lista=new Vector <Consepto>();
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
        Consepto c=lista.get(rowIndex);
        switch(columnIndex){            
            case 0 : return c.getDescripcion();
            case 1 : return new InformeService().ObtenerCuentas(c.getId());
            
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {        
        switch(columnIndex){            
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
    
    public Consepto getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <Consepto> lista) {
        this.lista = lista;
    }    
}
