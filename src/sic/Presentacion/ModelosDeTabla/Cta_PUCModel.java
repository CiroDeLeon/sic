/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.PUC.Cta_PUC;

/**
 *
 * @author FANNY BURGOS
 */
public class Cta_PUCModel extends AbstractTableModel{
    Vector <Cta_PUC> lista;
    String columns[]={"Cod. CTA","Denominacion","Tipo","Publico"};
    public Cta_PUCModel() {
        lista=new Vector <Cta_PUC>();
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
        Cta_PUC cta=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return cta.getId();
            case 1 : return cta.getDenominacion();    
            case 2 : return cta.getTipoCta();
            case 3 : return cta.isPublico();
            default  : return "";    
        }
    }
        @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){            
            case 3 : return Boolean.class;
            default  : return String.class;    
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
    
    public Cta_PUC getRow(int rowIndex){
        if(rowIndex<lista.size()){
            return lista.get(rowIndex);
        }else{
            return null;
        }
    }
    public void setLista(Vector <Cta_PUC> lista) {
        this.lista = lista;
    }    
}
