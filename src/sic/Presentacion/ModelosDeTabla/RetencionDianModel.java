/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Servicios.Dian.RetencionDian;

/**
 *
 * @author FANNY BURGOS
 */
public class RetencionDianModel extends AbstractTableModel{
    private Vector <RetencionDian> lista;
    String columns[]={"Descripcion","Aux Retencion","Aux Anticipo Retencion","Aux Autoretencion","Aux Anticipo Autoretencion","Detalle Aux","Base","% RTF"};
    public RetencionDianModel() {
        lista=new Vector <RetencionDian>();
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
        RetencionDian rd=getLista().get(rowIndex);
        switch(columnIndex){
            case 0 : return rd.getDescripcion();
            case 1 : return rd.getAuxiliar_retencion();
            case 2 : return rd.getAuxiliar_anticipo_retencion();    
            case 3 : return rd.getAuxiliar_autoretencion();
            case 4 : return rd.getAuxiliar_anticipo_autoretencion();        
            case 5 : return new PucService().ObtenerCtaPuc(rd.getAuxiliar_retencion()).getDenominacion();
            case 6 : return rd.getBase();
            case 7 : return rd.getPorcentage();    
            default: return "";
        }
    }
@Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){            
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
    
    public RetencionDian getRow(int rowIndex){
        return getLista().get(rowIndex);
    }
    public void setLista(Vector <RetencionDian> lista) {
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public Vector <RetencionDian> getLista() {
        return lista;
    }
}
