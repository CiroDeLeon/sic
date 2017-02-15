/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Aplicacion.Servicios.Reportes;



/**
 *
 * @author FANNY BURGOS
 */

// Esta Clase Registra los Datos Principales de la
// Empresa Para Mostrar en los Reportes
// Es Una Clase Singleton
public class DatosGeneralesDeUnReporte {
    private String nit="Nit : 812008260-0 Regimen Comun";
    private String razonsocial="Comercializadora de Granos - Corgranos S.A";        
    private String direccion="Direccion : Km 1 via Monteria , Cerete - Cordoba";
    private String telefono="Telefono : 3107277379 - ";
    private String observacionsuperior="";
    private String observacioninferior="";
    private String publicidad="TF Microsystems - Sistemas a tu Medida Inf. 3145814781";
    private static DatosGeneralesDeUnReporte instancia=null;
    private static DatosDeReporte dr=null;
    public DatosGeneralesDeUnReporte(){
        
    }
    public static DatosGeneralesDeUnReporte ObtenerInstancia(){
        instancia=new DatosGeneralesDeUnReporte();  
        ReporteService rs=new ReporteService();
        if(DatosGeneralesDeUnReporte.dr==null){
           DatosGeneralesDeUnReporte.dr=rs.getDao().ObtenerDatosDeReporte();
        }
        if(DatosGeneralesDeUnReporte.dr!=null){
           instancia.setDireccion("Direccion : "+DatosGeneralesDeUnReporte.dr.getDireccion());
           instancia.setTelefono("Telefonos : "+DatosGeneralesDeUnReporte.dr.getTelefono());
           instancia.setRazonsocial(DatosGeneralesDeUnReporte.dr.getRazonsocial());
           instancia.setNit("Nit/Cedula : "+DatosGeneralesDeUnReporte.dr.getNit()+" Regimen "+DatosGeneralesDeUnReporte.dr.getRegimen().toUpperCase());
        }
        return instancia;
    }
    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the razonsocial
     */
    public String getRazonsocial() {
        return razonsocial;
    }

    /**
     * @param razonsocial the razonsocial to set
     */
    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the observacionsuperior
     */
    public String getObservacionsuperior() {
        return observacionsuperior;
    }

    /**
     * @param observacionsuperior the observacionsuperior to set
     */
    public void setObservacionsuperior(String observacionsuperior) {
        this.observacionsuperior = observacionsuperior;
    }

    /**
     * @return the observacioninferior
     */
    public String getObservacioninferior() {
        return observacioninferior;
    }

    /**
     * @param observacioninferior the observacioninferior to set
     */
    public void setObservacioninferior(String observacioninferior) {
        this.observacioninferior = observacioninferior;
    }

    /**
     * @return the publicidad
     */
    public String getPublicidad() {
        return publicidad;
    }

    /**
     * @param publicidad the publicidad to set
     */
    public void setPublicidad(String publicidad) {
        this.publicidad = publicidad;
    }
}
