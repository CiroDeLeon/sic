/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Documento.Reportes;

import sic.Aplicacion.Servicios.Reportes.ReporteContable;




/**
 *
 * @author FANNY BURGOS
 */
public class DocumentoRep extends ReporteContable{
  private String Nodocumento;
  private String usuario;
  private String Ubicacion;
  private String Direccion;
  private Object idDocumento;
  private String fechacontable="";
  private String Fechacreacion="";
  private String Fechaanulacion="";
  private String Resumen="";
  private String Razonanulacion="";
  private String Creador;
  private String Anulador;
  private String modificador;
  private int Numeracion;
  private String TDocumento;
  private String Abreviatura;
  private boolean activo;
  private Object idAsiento;
  private Object idctapuc;
  private String Detalle;
  private double Debito;
  private double Credito;
  private double Entradas;
  private double Salidas;
  private int NoFactura;
  private int NoFacturaCompra;
  private double BaseIVA;
  private double BaseRTF;
  private String denominacion;
  private static double sumadebito;
  private static double sumacredito;
  private String subcontabilidad;
  private String pie;
  /**
     * @return the FechaContable
     */
    public String getFechacontable() {
        return fechacontable;
    }

    /**
     * @param FechaContable the FechaContable to set
     */
    public void setFechacontable(String FechaContable) {
        this.fechacontable = FechaContable;
    }

  
    /**
     * @return the NoDocumento
     */
    public String getNodocumento() {
        return Nodocumento;
    }

    /**
     * @param NoDocumento the NoDocumento to set
     */
    public void setNodocumento(String NoDocumento) {
        this.Nodocumento = NoDocumento;
    }

    /**
     * @return the Usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param Usuario the Usuario to set
     */
    public void setUsuario(String Usuario) {
        this.usuario = Usuario;
    }

    /**
     * @return the Ubicacion
     */
    public String getUbicacion() {
        return Ubicacion;
    }

    /**
     * @param Ubicacion the Ubicacion to set
     */
    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    /**
     * @return the Direccion
     */
    public String getDireccion() {
        return Direccion;
    }

    /**
     * @param Direccion the Direccion to set
     */
    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    /**
     * @return the idDocumento
     */
    public Object getIdDocumento() {
        return idDocumento;
    }

    /**
     * @param idDocumento the idDocumento to set
     */
    public void setIdDocumento(Object idDocumento) {
        this.idDocumento = idDocumento;
    }

    
    /**
     * @return the FechaCreacion
     */
    public String getFechacreacion() {
        return Fechacreacion;
    }

    /**
     * @param FechaCreacion the FechaCreacion to set
     */
    public void setFechacreacion(String FechaCreacion) {
        this.Fechacreacion = FechaCreacion;
    }

    /**
     * @return the FechaAnulacion
     */
    public String getFechaanulacion() {
        return Fechaanulacion;
    }

    /**
     * @param FechaAnulacion the FechaAnulacion to set
     */
    public void setFechaanulacion(String FechaAnulacion) {
        this.Fechaanulacion = FechaAnulacion;
    }

    /**
     * @return the Resumen
     */
    public String getResumen() {
        return Resumen;
    }

    /**
     * @param Resumen the Resumen to set
     */
    public void setResumen(String Resumen) {
        this.Resumen = Resumen;
    }

    /**
     * @return the RazonAnulacion
     */
    public String getRazonanulacion() {
        return Razonanulacion;
    }

    /**
     * @param RazonAnulacion the RazonAnulacion to set
     */
    public void setRazonanulacion(String RazonAnulacion) {
        this.Razonanulacion = RazonAnulacion;
    }

    /**
     * @return the Creador
     */
    public String getCreador() {
        return Creador;
    }

    /**
     * @param Creador the Creador to set
     */
    public void setCreador(String Creador) {
        this.Creador = Creador;
    }

    /**
     * @return the Anulador
     */
    public String getAnulador() {
        return Anulador;
    }

    /**
     * @param Anulador the Anulador to set
     */
    public void setAnulador(String Anulador) {
        this.Anulador = Anulador;
    }

    /**
     * @return the Numeracion
     */
    public int getNumeracion() {
        return Numeracion;
    }

    /**
     * @param Numeracion the Numeracion to set
     */
    public void setNumeracion(int Numeracion) {
        this.Numeracion = Numeracion;
    }

    /**
     * @return the TDocumento
     */
    public String getTDocumento() {
        return TDocumento;
    }

    /**
     * @param TDocumento the TDocumento to set
     */
    public void setTDocumento(String TDocumento) {
        this.TDocumento = TDocumento;
    }

    /**
     * @return the Abreviatura
     */
    public String getAbreviatura() {
        return Abreviatura;
    }

    /**
     * @param Abreviatura the Abreviatura to set
     */
    public void setAbreviatura(String Abreviatura) {
        this.Abreviatura = Abreviatura;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the idAsiento
     */
    public Object getIdAsiento() {
        return idAsiento;
    }

    /**
     * @param idAsiento the idAsiento to set
     */
    public void setIdAsiento(Object idAsiento) {
        this.idAsiento = idAsiento;
    }

    /**
     * @return the idCtaPuc
     */
    public Object getIdctapuc() {
        return idctapuc;
    }

    /**
     * @param idCtaPuc the idCtaPuc to set
     */
    public void setIdctapuc(Object idCtaPuc) {
        this.idctapuc = idCtaPuc;
    }

    /**
     * @return the Detalle
     */
    public String getDetalle() {
        return Detalle;
    }

    /**
     * @param Detalle the Detalle to set
     */
    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }

    /**
     * @return the Debito
     */
    public double getDebito() {
        return Debito;
    }

    /**
     * @param Debito the Debito to set
     */
    public void setDebito(double Debito) {
        this.Debito = Debito;
    }

    /**
     * @return the Credito
     */
    public double getCredito() {
        return Credito;
    }

    /**
     * @param Credito the Credito to set
     */
    public void setCredito(double Credito) {
        this.Credito = Credito;
    }

    /**
     * @return the Entradas
     */
    public double getEntradas() {
        return Entradas;
    }

    /**
     * @param Entradas the Entradas to set
     */
    public void setEntradas(double Entradas) {
        this.Entradas = Entradas;
    }

    /**
     * @return the Salidas
     */
    public double getSalidas() {
        return Salidas;
    }

    /**
     * @param Salidas the Salidas to set
     */
    public void setSalidas(double Salidas) {
        this.Salidas = Salidas;
    }

    /**
     * @return the NoFactura
     */
    public int getNoFactura() {
        return NoFactura;
    }

    /**
     * @param NoFactura the NoFactura to set
     */
    public void setNoFactura(int NoFactura) {
        this.NoFactura = NoFactura;
    }

    /**
     * @return the NoFacturaCompra
     */
    public int getNoFacturaCompra() {
        return NoFacturaCompra;
    }

    /**
     * @param NoFacturaCompra the NoFacturaCompra to set
     */
    public void setNoFacturaCompra(int NoFacturaCompra) {
        this.NoFacturaCompra = NoFacturaCompra;
    }

    /**
     * @return the BaseIVA
     */
    public double getBaseIVA() {
        return BaseIVA;
    }

    /**
     * @param BaseIVA the BaseIVA to set
     */
    public void setBaseIVA(double BaseIVA) {
        this.BaseIVA = BaseIVA;
    }

    /**
     * @return the BaseRTF
     */
    public double getBaseRTF() {
        return BaseRTF;
    }

    /**
     * @param BaseRTF the BaseRTF to set
     */
    public void setBaseRTF(double BaseRTF) {
        this.BaseRTF = BaseRTF;
    }

    /**
     * @return the modificador
     */
    public String getModificador() {
        return modificador;
    }

    /**
     * @param modificador the modificador to set
     */
    public void setModificador(String modificador) {
        this.modificador = modificador;
    }
    
    public String getAnulado(){
        if(this.isActivo()==false){
            return "Documento Anulado";
        }
        return "";
    }

    /**
     * @return the denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * @param denominacion the denominacion to set
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * @return the sumadebito
     */
    public double getSumadebito() {
        return DocumentoRep.sumadebito;
    }

    /**
     * @param sumadebito the sumadebito to set
     */
    public void setSumadebito(double sumadebito) {
        DocumentoRep.sumadebito = sumadebito;
    }

    /**
     * @return the sumacredito
     */
    public double getSumacredito() {
        return DocumentoRep.sumacredito;
    }

    /**
     * @param sumacredito the sumacredito to set
     */
    public void setSumacredito(double sumacredito) {
        DocumentoRep.sumacredito = sumacredito;
    }
    public String getNumeroletra(){
        if(this.getSumadebito()==this.getSumacredito()){
            return sic.Aplicacion.Servicios.Util.ConvertirNumeroEnLetras.getNumero_en_letras(this.getSumadebito()).toUpperCase()+" (Pesos)";
        }else{
            return "";
        }
    }
    public String getResponsable(){
        if(this.isActivo()){
            if(this.modificador!=null){
               return this.modificador;
            }else{
               return this.Creador; 
            }
        }else{
            return this.Anulador;
        }
    }

    /**
     * @return the subcontabilidad
     */
    public String getSubcontabilidad() {
        return subcontabilidad;
    }

    /**
     * @param subcontabilidad the subcontabilidad to set
     */
    public void setSubcontabilidad(String subcontabilidad) {
        this.subcontabilidad = subcontabilidad;
    }

    /**
     * @return the pie
     */
    public String getPie() {
        return pie;
    }

    /**
     * @param pie the pie to set
     */
    public void setPie(String pie) {
        this.pie = pie;
    }
}
