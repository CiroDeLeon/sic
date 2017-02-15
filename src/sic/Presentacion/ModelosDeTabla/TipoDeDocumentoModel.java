/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Dian.TipoDocumento;

/**
 *
 * @author FANNY BURGOS
 */
public class TipoDeDocumentoModel extends AbstractTableModel{
    private Vector <TipoDocumento> lista;
    String columns[]={"id","descripcion","abreviatura"};
    public TipoDeDocumentoModel() {
        lista=new Vector <TipoDocumento>();
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
        TipoDocumento td=this.lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return td.getId();
            case 1 : return td.getDescripcion();
            case 2 : return td.getAbreviatura();
            default: return "";    
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
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
    
    public TipoDocumento getRow(int rowIndex){
        return lista.get(rowIndex);
    }
    public void setLista(Vector <TipoDocumento> lista) {
        this.lista = lista;
    }

    
}
