/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Contabilidad.ResumenAuxDTO;


/**
 *
 * @author FANNY BURGOS
 */
public class ResumenAuxDTOModel extends AbstractTableModel{
    private Vector <ResumenAuxDTO> lista;
    String columns[]={"Cod. T","Titulo","Saldo","Existencia","Base RTF","RTF","Base IVA","IVA"};

    public ResumenAuxDTOModel() {
        lista=new Vector<ResumenAuxDTO>();
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
        ResumenAuxDTO r=getLista().get(rowIndex);
        switch(columnIndex){
            case 0 : return r.getCtat();
            case 1 : return r.getDenominacion();
            case 2 : return r.getSaldo();
            case 3 : return r.getExistencia();
            case 4 : return r.getBasertf();
            case 5 : return r.getRtf();     
            case 6 : return r.getBaseiva();
            case 7 : return r.getIva();     
            default : return "";    
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;            
            default: return Double.class;    
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
    
    public ResumenAuxDTO getRow(int rowIndex){
        if(rowIndex<getLista().size()){
        return getLista().get(rowIndex);
        }else{
        return null;    
        }
    }
    public void setLista(Vector <ResumenAuxDTO> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <ResumenAuxDTO> getLista() {
        return lista;
    }
}
