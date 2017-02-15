/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.Contabilidad;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Contabilidad.AsientoPorFechaDTO;
import sic.Dominio.Servicios.Contabilidad.BalanceDePruebaDTO;

/**
 *
 * @author Usuario1
 */
public class BalanceDePrueba_TableModel extends AbstractTableModel{
     private Vector <BalanceDePruebaDTO> lista;
    String columns[]={"COD","DENOMINACION","SALDO ANT.","DEBITO","CREDITO","SALDO ACTUAL"};
    

    public BalanceDePrueba_TableModel() {
        lista=new Vector<BalanceDePruebaDTO>();
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
        BalanceDePruebaDTO m=getLista().get(rowIndex);
        switch(columnIndex){
            case 0: return m.getId_cuenta();
            case 1: return m.getDenominacion();    
            case 2: return NumberFormat.getInstance().format(m.getSaldo_anterior()).replaceAll(",","");
            case 3: return NumberFormat.getInstance().format(m.getDebito()).replaceAll(",","");
            case 4: return NumberFormat.getInstance().format(m.getCredito()).replaceAll(",","");
            case 5: return NumberFormat.getInstance().format(m.getSaldo_actual()).replaceAll(",","");
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
    
    public BalanceDePruebaDTO getRow(int rowIndex){
        return getLista().get(rowIndex);
    }
    public void setLista(Vector <BalanceDePruebaDTO> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <BalanceDePruebaDTO> getLista() {
        return lista;
    }
}
