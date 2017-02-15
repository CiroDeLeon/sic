/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Documento.otros.RegistroDeCausacion;
import sic.Dominio.Core.PUC.PucService;

/**
 *
 * @author FANNY BURGOS
 */
public class RegistroDeCausacionModel extends AbstractTableModel{
    Vector <RegistroDeCausacion> lista;
    String columns[]={"Aux","Denominacion","Precio Unitario","Cantidad","Subtotal","IVA","RTF","Total"};
    boolean isAutoretenedor; 
    public RegistroDeCausacionModel(Vector<RegistroDeCausacion> lista,boolean isAutoretenedor) {
        this.lista = lista;
        this.isAutoretenedor=isAutoretenedor;
    }
    public RegistroDeCausacionModel(boolean isAutoretenedor) {
        this.lista=new Vector <RegistroDeCausacion>();
        this.isAutoretenedor=isAutoretenedor;
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
        RegistroDeCausacion rd=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return rd.getIdcuenta();
            case 1 : return (new PucService().ObtenerCtaPuc(rd.getIdcuenta())).getDenominacion();    
            case 2 : return rd.getDebito()/rd.getCantidad();
            case 3 : return rd.getCantidad();
            case 4 : return rd.getDebito();    
            case 5 : return rd.ObtenerValorIva();
            case 6 : return rd.ObtenerValorRTF(rd.getDebito(),isAutoretenedor);    
            case 7 : return rd.ObtenerTotal(isAutoretenedor);    
            default: return "";
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return Double.class;
            case 3 : return Double.class;
            case 4 : return Double.class;
            case 5 : return Double.class;
            case 6 : return Double.class;    
            case 7 : return Double.class;        
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
    public void setLista(Vector <RegistroDeCausacion> lista) {
        this.lista = lista;
    }
    public RegistroDeCausacion getRow(int rowIndex){
        return lista.get(rowIndex);
    }

}
