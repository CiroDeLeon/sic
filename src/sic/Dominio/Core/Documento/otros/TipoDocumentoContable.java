/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Documento.otros;

/**
 *
 * @author FANNY BURGOS
 */
public class TipoDocumentoContable {
   private String descripcion;   
   private String abreviatura;
   private int ultimanumeracion=0;

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * @param abreviatura the abreviatura to set
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * @return the ultimanumeracion
     */
    public int getUltimanumeracion() {
        return ultimanumeracion;
    }

    /**
     * @param ultimanumeracion the ultimanumeracion to set
     */
    public void setUltimanumeracion(int ultimanumeracion) {
        this.ultimanumeracion = ultimanumeracion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
    
}
