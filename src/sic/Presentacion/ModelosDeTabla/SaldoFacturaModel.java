/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Servicios.Facturacion.SaldoFactura;

/**
 *
 * @author FANNY BURGOS
 */
public class SaldoFacturaModel extends AbstractTableModel {

    private Vector <SaldoFactura> lista;
    String columns[]={"Factura","Saldo"};

   

    public SaldoFacturaModel() {
        lista=new Vector <SaldoFactura>();
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
        SaldoFactura sa=getLista().get(rowIndex);
        switch(columnIndex){
            case 0: return sa.getFactura();
            case 1: return sa.getSaldo();                
            default : return "";
        }
    }
     @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return Integer.class;
            case 1 : return Double.class;                        
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
    
    public SaldoFactura getRow(int rowIndex){
        return getLista().get(rowIndex);
    }
    public void setLista(Vector <SaldoFactura> lista) {
        this.lista = lista;
    }
    public double ObtenerSumatoria(){
        double suma=0;
        Iterator <SaldoFactura> it=this.getLista().iterator();
        while(it.hasNext()){
           SaldoFactura sa=it.next(); 
           suma+=sa.getSaldo(); 
        }
        return suma;
    }

    /**
     * @return the lista
     */
    public Vector <SaldoFactura> getLista() {
        return lista;
    }
    
}
