package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class Actividad_TableModel extends AbstractTableModel{
   private Vector <sic.Dominio.Servicios.Dian.Actividad> lista;
   String columns[]={"Codigo","Actividad","% Cree"};
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
      sic.Dominio.Servicios.Dian.Actividad instancia=lista.get(rowIndex);
      switch(columnIndex){
         case 0 : return ""+instancia.getCodigo();
         case 1 : return instancia.getDescripcion();
         case 2 : return instancia.getPorcentage();
         default: return "";
      }
   }
   @Override
   public Class<?> getColumnClass(int columnIndex) {
      switch(columnIndex){
         case 0 : return String.class;
         case 1 : return java.lang.String.class;
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
   public void setLista(Vector <sic.Dominio.Servicios.Dian.Actividad> lista) {
      this.lista = lista;
   }
   public sic.Dominio.Servicios.Dian.Actividad getRow(int rowIndex){
      return lista.get(rowIndex);
   }
   public Vector <sic.Dominio.Servicios.Dian.Actividad> getLista(){
      return lista;
   }
}
