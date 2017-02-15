/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Reportes;

/**
 *
 * @author FANNY BURGOS
 */
public class ReporteContable {
   
    DatosGeneralesDeUnReporte dg;
    public ReporteContable() {
        dg=DatosGeneralesDeUnReporte.ObtenerInstancia();
    }

    public String getNitempresa() {
        return dg.getNit();
    }
    /**
     * @return the razonsocial
     */
    public String getRazonsocialempresa() {
        return dg.getRazonsocial();
    }
    /**
     * @return the direccion
     */
    public String getDireccionempresa() {
        return dg.getDireccion();
    }
    /**
     * @return the telefono
     */
    public String getTelefonoempresa() {
        return dg.getTelefono();
    }
    /**
     * @return the observacionsuperior
     */
    public String getObservacionsuperior() {
        return dg.getObservacionsuperior();
    }
    /**
     * @return the observacioninferior
     */
    public String getObservacioninferior() {
        return dg.getObservacioninferior();
    }
     /**
     * @return the publicidad
     */
    public String getPublicidad() {
        return dg.getPublicidad();
    }
}
