/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Dian;

/**
 *
 * @author FANNY BURGOS
 */
public class NomenclaturaDeDireccion {
    private String nomenclatura;
    private String abreviatura;

    public NomenclaturaDeDireccion(String abreviatura, String nomenclatura) {
        this.nomenclatura = nomenclatura;
        this.abreviatura = abreviatura;
    }


    /**
     * @return the nomenclatura
     */
    public String getNomenclatura() {
        return nomenclatura;
    }

    /**
     * @param nomenclatura the nomenclatura to set
     */
    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
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

    @Override
    public String toString() {
        return this.getNomenclatura()+" ("+this.getAbreviatura()+")";
    }
    
}
