/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Contabilidad.AnexoDTO;

/**
 *
 * @author FANNY BURGOS
 */
public class AnexoDTOModel extends AbstractTableModel{
    private Vector <AnexoDTO> lista;
    String columns[]={"Cod.Cta","Denominacion","Parcial","Saldo",};

    public AnexoDTOModel() {
        lista=new Vector<AnexoDTO>();
    }

    @Override
    public int getRowCount() {
        return getLista().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnexoDTO a=getLista().get(rowIndex);
        switch(columnIndex){
            case 0: 
                    if(a.getCodigocta().length()>8){
                        return a.getCodigocta().substring(8,a.getCodigocta().length());
                    }else{
                        return a.getCodigocta();
                    }
            case 1: return a.getDenominacion();    
            case 2: return a.getParcial();
            case 3: return a.getSaldo();    
            default : return "";
        }
    }
     @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;            
            case 2 : return Double.class;
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
    
    public AnexoDTO getRow(int rowIndex){
        return getLista().get(rowIndex);
    }
    public void setLista(Vector <AnexoDTO> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <AnexoDTO> getLista() {
        return lista;
    }
}
