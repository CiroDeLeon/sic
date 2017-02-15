/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Contabilidad.EstadoFinancieroDTO;

/**
 *
 * @author FANNY BURGOS
 */
public class EstadoFinancieroDTOModel extends AbstractTableModel{
    private Vector <EstadoFinancieroDTO> lista;
    String columns[]={"Cod. Cta","Denominacion","Saldo"};

    public EstadoFinancieroDTOModel() {
        lista=new Vector <EstadoFinancieroDTO>();
    }

    @Override
    public int getRowCount() {
        return this.getLista().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EstadoFinancieroDTO ef=getLista().get(rowIndex);
        switch(columnIndex){
            case 0  : return ef.getCodigocta();
            case 1  : return ef.getDenominacion();
            case 2  : return ef.getSaldo();
            default : return "";    
        }
    }
            @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return Double.class;         
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
    
    public EstadoFinancieroDTO getRow(int rowIndex){
        if(rowIndex<getLista().size()){
            return getLista().get(rowIndex);
        }else{
            return null;
        }
    }
    public void setLista(Vector <EstadoFinancieroDTO> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <EstadoFinancieroDTO> getLista() {
        return lista;
    }
}
