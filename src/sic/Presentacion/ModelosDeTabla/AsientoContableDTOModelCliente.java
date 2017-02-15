/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;




import java.util.Date;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author FANNY BURGOS
 */
public class AsientoContableDTOModelCliente extends DefaultTableModel{

    
    String columns[]={"Fecha","No Documento","Usuario","Soporte","Detalle","Factura","Debito","Credito","Saldo"};
    

    public AsientoContableDTOModelCliente() {
    }

    

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    
       @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return Integer.class;                             
            case 6 : return Double.class;
            case 7 : return Double.class;
            case 8 : return Double.class;            
            default : return Object.class;    
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
}
