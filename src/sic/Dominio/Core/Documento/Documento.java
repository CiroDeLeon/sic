/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Documento;

import java.util.Date;
import java.util.Vector;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.DomainObject;

/**
 *
 * @author FANNY BURGOS
 */
public class Documento extends DomainObject implements Comparable{
    
    // Documento
    protected Usuario  usuario;
    private Date FechaCreacion;
    private Date FechaContable;
    private Date FechaAnulacion;
    private String Resumen;
    private String RazonAnulacion;
    private String Creador;
    private String Modificador;
    private String Anulador;
    private int Numeracion;
    private String Tdocumento;
    private String Abreviatura;
    private Vector <Asiento> asientos;    
    private boolean Activo;
    private int resolucion;    
    private boolean norma_local=true;
    private boolean norma_internacional=true; 
    private Subcontabilidad subcontabilidad=null;

    public Documento(Usuario usuario, Date FechaCreacion, Date FechaContable, Date FechaAnulacion, String Resumen, String RazonAnulacion, String Creador, String Modificador, String Anulador, int Numeracion, String Tdocumento, String Abreviatura, Vector<Asiento> asientos, boolean Activo,Subcontabilidad subcontabilidad) {
        this.usuario = usuario;
        this.FechaCreacion = FechaCreacion;
        this.FechaContable = FechaContable;
        this.FechaAnulacion = FechaAnulacion;
        this.Resumen = Resumen;
        this.RazonAnulacion = RazonAnulacion;
        this.Creador = Creador;
        this.Modificador = Modificador;
        this.Anulador = Anulador;
        this.Numeracion = Numeracion;
        this.Tdocumento = Tdocumento;
        this.Abreviatura = Abreviatura;
        this.asientos = asientos;
        this.Activo = Activo;
        this.resolucion=0;     
        this.subcontabilidad=subcontabilidad;
    }
    

    public Documento() {
        this.resolucion=0;
    }

    

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the FechaCreacion
     */
    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    /**
     * @param FechaCreacion the FechaCreacion to set
     */
    public void setFechaCreacion(Date FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    /**
     * @return the FechaContable
     */
    public Date getFechaContable() {
        return FechaContable;
    }
    
    /**
     * @param FechaContable the FechaContable to set
     */
    public void setFechaContable(Date FechaContable) {
        this.FechaContable = FechaContable;
    }

    /**
     * @return the FechaAnulacion
     */
    public Date getFechaAnulacion() {
        return FechaAnulacion;
    }

    /**
     * @param FechaAnulacion the FechaAnulacion to set
     */
    public void setFechaAnulacion(Date FechaAnulacion) {
        this.FechaAnulacion = FechaAnulacion;
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
    public String getRazonAnulacion() {
        return RazonAnulacion;
    }

    /**
     * @param RazonAnulacion the RazonAnulacion to set
     */
    public void setRazonAnulacion(String RazonAnulacion) {
        this.RazonAnulacion = RazonAnulacion;
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
     * @return the Tdocumento
     */
    public String getTdocumento() {
        return Tdocumento;
    }

    /**
     * @param Tdocumento the Tdocumento to set
     */
    public void setTdocumento(String Tdocumento) {
        
        this.Tdocumento = Tdocumento;
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
     * @return the Activo
     */
    public boolean isActivo() {
        return Activo;
    }

    /**
     * @param Activo the Activo to set
     */
    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

    /**
     * @return the Modificador
     */
    public String getModificador() {
        return Modificador;
    }

    /**
     * @param Modificador the Modificador to set
     */
    public void setModificador(String Modificador) {
        this.Modificador = Modificador;
    }

    /**
     * @return the asientos
     */
    public Vector <Asiento> getAsientos() {
        return asientos;
    }

    /**
     * @param asientos the asientos to set
     */
    public void setAsientos(Vector <Asiento> asientos) {
        this.asientos=asientos;
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
     * @return the resolucion
     */
    public int getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion the resolucion to set
     */
    public void setResolucion(int resolucion) {
        this.resolucion = resolucion;
    }

    @Override
    public int compareTo(Object o) {
        long fecha=this.getFechaContable().getTime();
        long fechao=((Documento)o).getFechaContable().getTime();
        if(fecha<fechao){
            return -1;
        }else{
            if(fecha>fechao){
                return 1;
            }
            return 0;            
        }
    }

    /**
     * @return the norma_local
     */
    public boolean isNorma_local() {
        return norma_local;
    }

    /**
     * @param norma_local the norma_local to set
     */
    public void setNorma_local(boolean norma_local) {
        this.norma_local = norma_local;
    }

    /**
     * @return the norma_internacional
     */
    public boolean isNorma_internacional() {
        return norma_internacional;
    }

    /**
     * @param norma_internacional the norma_internacional to set
     */
    public void setNorma_internacional(boolean norma_internacional) {
        this.norma_internacional = norma_internacional;
    }

    /**
     * @return the subcontabilidad
     */
    public Subcontabilidad getSubcontabilidad() {
        return subcontabilidad;
    }

    /**
     * @param subcontabilidad the subcontabilidad to set
     */
    public void setSubcontabilidad(Subcontabilidad subcontabilidad) {
        this.subcontabilidad = subcontabilidad;
    }

   
}
