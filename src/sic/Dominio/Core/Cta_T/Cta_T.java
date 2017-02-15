/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Cta_T;

import sic.Dominio.DomainObject;

/**
 *
 * @author FANNY BURGOS
 */
public class Cta_T extends DomainObject{
    private String descripcion;
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
