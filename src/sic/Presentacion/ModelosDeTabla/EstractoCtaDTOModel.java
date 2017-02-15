/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Contabilidad.EstractoCtaDTO;

/**
 *
 * @author FANNY BURGOS
 */
public class EstractoCtaDTOModel extends AbstractTableModel{
    Vector <EstractoCtaDTO> lista;

    public EstractoCtaDTOModel() {
        lista=new Vector <EstractoCtaDTO>();
    }

    String columns[]={"Subcta","AUX","Denominacion","Saldo Ant.","Debito","Credito","Saldo"};
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
        EstractoCtaDTO e=lista.get(rowIndex);
        switch(columnIndex){            
            case 0:return e.getCodigocta().substring(0,6);
            case 1:return e.getCodigocta().substring(6,8);
            case 2:return e.getDenominacion();    
            case 3:return e.getSaldoanterior();
            case 4:return e.getDebito();
            case 5:return e.getCredito();
            case 6:return e.getSaldo();
            default: return "";
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return Double.class;                    
            case 4 : return Double.class;
            case 5 : return Double.class;
            case 6 : return Double.class;            
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
    
    public EstractoCtaDTO getRow(int rowIndex){
        if(rowIndex<getLista().size()){
        return getLista().get(rowIndex);
        }else{
        return null;    
        }
    }
    public void setLista(Vector <EstractoCtaDTO> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <EstractoCtaDTO> getLista() {
        return lista;
    }
}
