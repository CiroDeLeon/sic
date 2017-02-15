/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Dian.Municipio;

/**
 *
 * @author FANNY BURGOS
 */
public class MunicipioModel extends AbstractTableModel{
    private Vector <Municipio> lista;
    String columns[]={"Cod. Municipio","Municipio","Cod. Dpto","Dpto","Cod. Pais","Pais"};
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
        Municipio m=this.lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return ""+m.getId();
            case 1 : return m.getDescripcion();
            case 2 : return ""+m.getIddpto();
            case 3 : return m.getDescripciondpto();
            case 4 : return m.getIdpais();
            case 5 : return m.getDescripcionpais();
            default: return "";    
        }
    }
     @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return String.class;
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

    /**
     * @param lista the lista to set
     */
    public void setLista(Vector <Municipio> lista) {
        this.lista = lista;
    }
    public Municipio getRow(int rowIndex){
        return lista.get(rowIndex);
    }
}
