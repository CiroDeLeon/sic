/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.PUC;

import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.DomainObject;

/**
 *
 * @author FANNY BURGOS
 */
public class Cta_PUC extends DomainObject{
    private Cta_T Ctat;
    private String Denominacion;
    private String TipoCta;
    private boolean RequiereNit;
    private boolean Publico;
    private String Categoria;
    /**
     * @return the Ctat
     */
    public Cta_T getCtat() {
        return Ctat;
    }

    /**
     * @param Ctat the Ctat to set
     */
    public void setCtat(Cta_T Ctat) {
        this.Ctat = Ctat;
    }

    /**
     * @return the Denominacion
     */
    public String getDenominacion() {
        return Denominacion;
    }

    /**
     * @param Denominacion the Denominacion to set
     */
    public void setDenominacion(String Denominacion) {
        this.Denominacion = Denominacion;
    }

    /**
     * @return the TipoCta
     */
    public String getTipoCta() {
        return TipoCta;
    }

    /**
     * @param TipoCta the TipoCta to set
     */
    public void setTipoCta(String TipoCta) {
        this.TipoCta = TipoCta;
    }

    /**
     * @return the RequiereNit
     */
    public boolean isRequiereNit() {
        return RequiereNit;
    }

    /**
     * @param RequiereNit the RequiereNit to set
     */
    public void setRequiereNit(boolean RequiereNit) {
        this.RequiereNit = RequiereNit;
    }

    /**
     * @return the Publico
     */
    public boolean isPublico() {
        return Publico;
    }

    /**
     * @param Publico the Publico to set
     */
    public void setPublico(boolean Publico) {
        this.Publico = Publico;
    }

    /**
     * @return the Categoria
     */
    public String getCategoria() {
        return Categoria;
    }

    /**
     * @param Categoria the Categoria to set
     */
    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }
    public boolean isDebito(){
        if(this.getTipoCta().toLowerCase().equals("debito"))
            return true;
        return false;
    }
    public boolean isCredito(){
        if(this.getTipoCta().toLowerCase().equals("credito"))
            return true;
        return false;
    }
}
