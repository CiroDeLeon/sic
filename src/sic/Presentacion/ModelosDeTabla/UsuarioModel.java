/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Usuario.Usuario;

/**
 *
 * @author FANNY BURGOS
 */
public class UsuarioModel extends AbstractTableModel{
    Vector <Usuario> lista;
    String columns[]={"Td","NoDocumento","Usuario","Ubicacion","Direccion","Telefono","Correo","Regimen","Autoretenedor"};
    public UsuarioModel() {
        lista=new Vector<Usuario>();
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
        Usuario u=lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return u.getTipodocumento().getAbreviatura();
            case 1 : return u.getNoDocumentoCompleto();
            case 2 : return u.NombreCompleto();
            case 3 : return u.getMunicipio().toString();
            case 4 : return u.getDireccion();
            case 5 : return u.getTelefono();
            case 6 : return u.getCorreo();
            case 7 : return u.getRegimen();    
            case 8 : return u.isAutoretenedor_renta();    
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
            case 6 : return String.class;    
            case 7 : return String.class;    
            case 8 : return Boolean.class;    
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
    
    public Usuario getRow(int rowIndex){
        if(rowIndex<lista.size()){
        return lista.get(rowIndex);
        }else{
        return null;    
        }
    }
    public void setLista(Vector <Usuario> lista) {
        this.lista = lista;
    }
}
