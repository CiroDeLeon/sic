/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Dian;

/**
 *
 * @author FANNY BURGOS
 */
public class Actividad {
   private int codigo;
   private String descripcion;
   private double porcentage;


    public Actividad() {
    }

    public Actividad(int codigo, String descripcion, double porcentage) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.porcentage = porcentage;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
     * @return the porcentage
     */
    public double getPorcentage() {
        return porcentage;
    }

    /**
     * @param porcentage the porcentage to set
     */
    public void setPorcentage(double porcentage) {
        this.porcentage = porcentage;
    }
}
