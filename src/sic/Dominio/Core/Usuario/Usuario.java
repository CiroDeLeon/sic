/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Usuario;

import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Core.Cta_T.Cta_T;
import sic.Dominio.Servicios.Dian.Actividad;

/**
 *
 * @author FANNY BURGOS
 */
public class Usuario extends Cta_T{
   private Municipio municipio;
   private TipoDocumento tipodocumento;
   private long NoDocumento;
   private String Nombre;
   private String sNombre;
   private String Apellido;
   private String sApellido;
   private String RazonSocial;
   private String SobreNombre;
   private String telefono;
   private String direccion;
   private String correo;
   private String Regimen;
   private boolean retenedor_de_renta;
   private String digitoDIAN;
   private boolean retenedor_de_reteiva;
   private boolean retenedor_de_ica;   
   private Actividad actividad;
   private boolean autoretenedor_renta;
   private boolean retenedor_de_cree;
   private boolean autoretenedor_iva;
   private boolean autoretenedor_ica;
   private boolean autoretenedor_cree; 
   private double cupo_credito;

    public Usuario(Municipio municipio, TipoDocumento tipodocumento, long NoDocumento, String Nombre, String sNombre, String Apellido, String sApellido, String RazonSocial, String SobreNombre, String telefono, String direccion, String correo, String Regimen, boolean retenedor_de_renta, String digitoDIAN, boolean retenedor_de_reteiva, boolean retenedor_de_ica, Actividad actividad, boolean autoretenedor, boolean retenedor_de_cree) {
        this.municipio = municipio;
        this.tipodocumento = tipodocumento;
        this.NoDocumento = NoDocumento;
        this.Nombre = Nombre;
        this.sNombre = sNombre;
        this.Apellido = Apellido;
        this.sApellido = sApellido;
        this.RazonSocial = RazonSocial;
        this.SobreNombre = SobreNombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.Regimen = Regimen;
        this.retenedor_de_renta = retenedor_de_renta;
        this.digitoDIAN = digitoDIAN;
        this.retenedor_de_reteiva = retenedor_de_reteiva;
        this.retenedor_de_ica = retenedor_de_ica;
        this.actividad = actividad;
        this.autoretenedor_renta = autoretenedor;
        this.retenedor_de_cree = retenedor_de_cree;
    }


   
   

    

    public Usuario() {
        
    }

    /**
     * @return the municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the tipodocumento
     */
    public TipoDocumento getTipodocumento() {
        return tipodocumento;
    }

    /**
     * @param tipodocumento the tipodocumento to set
     */
    public void setTipodocumento(TipoDocumento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    /**
     * @return the NoDocumento
     */
    public long getNoDocumento() {
        return NoDocumento;
    }

    /**
     * @param NoDocumento the NoDocumento to set
     */
    public void setNoDocumento(long NoDocumento) {
        this.NoDocumento = NoDocumento;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the sNombre
     */
    public String getsNombre() {
        return sNombre;
    }

    /**
     * @param sNombre the sNombre to set
     */
    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    /**
     * @return the Apellido
     */
    public String getApellido() {
        return Apellido;
    }

    /**
     * @param Apellido the Apellido to set
     */
    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    /**
     * @return the sApellido
     */
    public String getsApellido() {
        return sApellido;
    }

    /**
     * @param sApellido the sApellido to set
     */
    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    /**
     * @return the RazonSocial
     */
    public String getRazonSocial() {
        return RazonSocial;
    }

    /**
     * @param RazonSocial the RazonSocial to set
     */
    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    /**
     * @return the SobreNombre
     */
    public String getSobreNombre() {
        return SobreNombre;
    }

    /**
     * @param SobreNombre the SobreNombre to set
     */
    public void setSobreNombre(String SobreNombre) {
        this.SobreNombre = SobreNombre;
    }

    /**
     * @return the Regimen
     */
    public String getRegimen() {
        return Regimen;
    }

    /**
     * @param Regimen the Regimen to set
     */
    public void setRegimen(String Regimen) {
        this.Regimen = Regimen;
    }

    /**
     * @return the AgenteRetenedor
     */
    /*
    public String getAgenteRetenedor() {
        return AgenteRetenedor;
    }

    /**
     * @param AgenteRetenedor the AgenteRetenedor to set
     */
    /*
    public void setAgenteRetenedor(String AgenteRetenedor) {
        this.AgenteRetenedor = AgenteRetenedor;
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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    @Override
    public String getDescripcion(){
        return this.RazonSocial+"".trim()+this.getNombre()+" "+this.getsNombre()+" "+this.getApellido()+" "+this.getsApellido()+" - "+this.getNoDocumento()+""+this.digitoDIAN;
    }

    /**
     * @return the digitoDIAN
     */
    public String getDigitoDIAN() {
        return digitoDIAN;
    }

    /**
     * @param digitoDIAN the digitoDIAN to set
     */
    public void setDigitoDIAN(String digitoDIAN) {
        this.digitoDIAN = digitoDIAN;
    }
    @Override
    public String toString(){
        return this.RazonSocial+""+this.getNombre()+" "+this.getsNombre()+" "+this.getApellido()+" "+this.getsApellido()+"  ("+this.getSobreNombre()+")";
    }
    public String NombreCompleto(){
        return this.RazonSocial+""+this.getNombre()+" "+this.getsNombre()+" "+this.getApellido()+" "+this.getsApellido();
    }
    public String getNoDocumentoCompleto(){
        if(digitoDIAN!=null && digitoDIAN.trim().equals("")==false ){
           return this.NoDocumento+"-"+this.digitoDIAN;
        }else{
           return ""+this.NoDocumento; 
        }
    }
    public String getUbicacionCompleta(){
        return this.direccion+" "+this.municipio.toString();
    }
    

    

    

    /**
     * @return the actividad
     */
    public Actividad getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    /**
     * @param autoretenedor the autoretenedor to set
     */
    public void setAutoretenedor_renta(boolean autoretenedor) {
        this.autoretenedor_renta = autoretenedor;
    }

    /**
     * @return the retenedor_de_renta
     */
    public boolean isRetenedor_de_renta() {
        return retenedor_de_renta;
    }

    /**
     * @param retenedor_de_renta the retenedor_de_renta to set
     */
    public void setRetenedor_de_renta(boolean retenedor_de_renta) {
        this.retenedor_de_renta = retenedor_de_renta;
    }

    /**
     * @return the retenedor_de_reteiva
     */
    public boolean isRetenedor_de_reteiva() {
        return retenedor_de_reteiva;
    }

    /**
     * @param retenedor_de_reteiva the retenedor_de_reteiva to set
     */
    public void setRetenedor_de_reteiva(boolean retenedor_de_reteiva) {
        this.retenedor_de_reteiva = retenedor_de_reteiva;
    }

    /**
     * @return the retenedor_de_ica
     */
    public boolean isRetenedor_de_ica() {
        return retenedor_de_ica;
    }

    /**
     * @param retenedor_de_ica the retenedor_de_ica to set
     */
    public void setRetenedor_de_ica(boolean retenedor_de_ica) {
        this.retenedor_de_ica = retenedor_de_ica;
    }

    /**
     * @return the autoretenedor
     */
    public boolean isAutoretenedor_renta() {
        return autoretenedor_renta;
    }

    /**
     * @return the retenedor_de_cree
     */
    public boolean isRetenedor_de_cree() {
        return retenedor_de_cree;
    }

    /**
     * @param retenedor_de_cree the retenedor_de_cree to set
     */
    public void setRetenedor_de_cree(boolean retenedor_de_cree) {
        this.retenedor_de_cree = retenedor_de_cree;
    }

    /**
     * @return the autoretenedor_iva
     */
    public boolean isAutoretenedor_iva() {
        return autoretenedor_iva;
    }

    /**
     * @param autoretenedor_iva the autoretenedor_iva to set
     */
    public void setAutoretenedor_iva(boolean autoretenedor_iva) {
        this.autoretenedor_iva = autoretenedor_iva;
    }

    /**
     * @return the autoretenedor_ica
     */
    public boolean isAutoretenedor_ica() {
        return autoretenedor_ica;
    }

    /**
     * @param autoretenedor_ica the autoretenedor_ica to set
     */
    public void setAutoretenedor_ica(boolean autoretenedor_ica) {
        this.autoretenedor_ica = autoretenedor_ica;
    }

    /**
     * @return the autoretenedor_cree
     */
    public boolean isAutoretenedor_cree() {
        return autoretenedor_cree;
    }

    /**
     * @param autoretenedor_cree the autoretenedor_cree to set
     */
    public void setAutoretenedor_cree(boolean autoretenedor_cree) {
        this.autoretenedor_cree = autoretenedor_cree;
    }
    public boolean isPersonaNatural(){
        return !(getRazonSocial().length()>0);  
    }
    public boolean isSimplificado(){
        return this.getRegimen().toLowerCase().equals("simplificado");
    }
    public boolean isComun(){
        return this.getRegimen().toLowerCase().equals("comun");
    }
    public boolean isGranContribuyente(){
        return this.getRegimen().toLowerCase().equals("gran contribuyente");
    }

    /**
     * @return the cupo_credito
     */
    public double getCupo_credito() {
        return cupo_credito;
    }

    /**
     * @param cupo_credito the cupo_credito to set
     */
    public void setCupo_credito(double cupo_credito) {
        this.cupo_credito = cupo_credito;
    }
}
