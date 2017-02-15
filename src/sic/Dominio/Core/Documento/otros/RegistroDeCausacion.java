/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Core.Documento.otros;

import java.util.Vector;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Servicios.Dian.RetencionDian;

/**
 *
 * @author FANNY BURGOS
 */
public class RegistroDeCausacion {
    private Object idcuenta;
    private int nofactura;
    private double debito;    
    private double cantidad;
    private RetencionDian  itemtablartf;
    private String aux_rtf;
    private double p_iva;
    private String aux_iva;
    private int tipo;
    private Usuario usuario;
    private double p_rtf;
   

    public RegistroDeCausacion() {
        this.itemtablartf=new RetencionDian();
    }

    /**
     * @return the idcuenta
     */
    public Object getIdcuenta() {
        return idcuenta;
    }

    /**
     * @param idcuenta the idcuenta to set
     */
    public void setIdcuenta(Object idcuenta) {
        this.idcuenta = idcuenta;
    }

    /**
     * @return the nofactura
     */
    public int getNofactura() {
        return nofactura;
    }

    /**
     * @param nofactura the nofactura to set
     */
    public void setNofactura(int nofactura) {
        this.nofactura = nofactura;
    }

    /**
     * @return the debito
     */
    public double getDebito() {
        return debito;
    }

    /**
     * @param debito the debito to set
     */
    public void setDebito(double debito) {
        this.debito = debito;
    }


    

    /**
     * @return the aux_rtf
     */
    public String getAux_rtf() {
        return aux_rtf;
    }

    /**
     * @param aux_rtf the aux_rtf to set
     */
    public void setAux_rtf(String aux_rtf) {
        this.aux_rtf = aux_rtf;
    }

    /**
     * @return the p_iva
     */
    public double getP_iva() {
        return p_iva;
    }

    /**
     * @param p_iva the p_iva to set
     */
    public void setP_iva(double p_iva) {
        this.p_iva = p_iva;
    }

    /**
     * @return the aux_iva
     */
    public String getAux_iva() {
        return aux_iva;
    }

    /**
     * @param aux_iva the aux_iva to set
     */
    public void setAux_iva(String aux_iva) {
        this.aux_iva = aux_iva;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the itemtablartf
     */
    public RetencionDian getItemtablartf() {
        return itemtablartf;
    }

    /**
     * @param itemtablartf the itemtablartf to set
     */
    public void setItemtablartf(RetencionDian itemtablartf) {        
        this.itemtablartf = itemtablartf;
    }
    public double ObtenerValorRTF(double base,boolean isAutoretenedor){
        return itemtablartf.obtenerValorRetencion(base,this.p_rtf,isAutoretenedor);
    }
    public double ObtenerValorIva(){
        return Math.round(this.debito*(this.p_iva/100));
    }
    public double ObtenerTotal(boolean isAutoretenedor){
        return this.debito+this.ObtenerValorIva()-this.ObtenerValorRTF(debito,isAutoretenedor);
    }
    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public Vector<Asiento> ObtenerAsientos(){
        Vector<Asiento> lista=new Vector<Asiento>();
        PucService ps=new PucService();
        Asiento a;
        a=new Asiento();
        a.setId("0");
        a.setCtaPuc(ps.ObtenerCtaPuc(idcuenta));
        a.setDetalle("");
        a.setDebito(debito);
        a.setCredito(0);
        a.setEntradas(cantidad);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        a.setBaseIVA(0);
        a.setBaseRTF(0);
        a.setTiporegistro(tipo);        
        lista.add(a);
        System.out.println(this.aux_iva);
        a=new Asiento();
        a.setId("0");
        a.setCtaPuc(ps.ObtenerCtaPuc(this.aux_iva.toString()));
        a.setDetalle("");
        a.setDebito(this.ObtenerValorIva());
        a.setCredito(0);
        a.setEntradas(0);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        if(this.ObtenerValorIva()==0){
           a.setBaseIVA(0);    
        }else{
           a.setBaseIVA(debito);
        }
        a.setBaseRTF(0);
        a.setTiporegistro(tipo);
        lista.add(a);
        
        a=new Asiento();
        a.setId("0");
        a.setCtaPuc(ps.ObtenerCtaPuc(this.aux_rtf));
        a.setDetalle("");
        a.setDebito(0);
        a.setCredito(this.ObtenerValorRTF(debito,this.usuario.isAutoretenedor_renta()));
        a.setEntradas(0);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        a.setBaseIVA(0);
        if(this.ObtenerValorRTF(debito,usuario.isAutoretenedor_renta())==0){
        a.setBaseRTF(0);
        }else{
        a.setBaseRTF(debito);    
        }
        a.setTiporegistro(tipo);
        lista.add(a);
        
        return lista;
    }
    public Vector<Asiento> ObtenerAsientos(Object idAsientoDebito,Object idAsientoIva,Object idAsientoRTF){
        Vector<Asiento> lista=new Vector<Asiento>();
        PucService ps=new PucService();
        Asiento a;
        a=new Asiento();
        a.setId(idAsientoDebito);
        a.setCtaPuc(ps.ObtenerCtaPuc(idcuenta));
        a.setDetalle("");
        a.setDebito(debito);
        a.setCredito(0);
        a.setEntradas(cantidad);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        a.setBaseIVA(0);
        a.setBaseRTF(0);
        a.setTiporegistro(tipo);        
        lista.add(a);
        
        a=new Asiento();
        a.setId(idAsientoIva);
        a.setCtaPuc(ps.ObtenerCtaPuc(this.aux_iva));
        a.setDetalle("");
        a.setDebito(this.ObtenerValorIva());
        a.setCredito(0);
        a.setEntradas(0);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        if(this.ObtenerValorIva()==0){
           a.setBaseIVA(0);    
        }else{
           a.setBaseIVA(debito);
        }
        a.setBaseRTF(0);
        a.setTiporegistro(tipo);
        lista.add(a);
        
        a=new Asiento();
        a.setId(idAsientoRTF);
        a.setCtaPuc(ps.ObtenerCtaPuc(this.aux_rtf));
        a.setDetalle("");
        a.setDebito(0);
        a.setCredito(this.ObtenerValorRTF(debito,this.usuario.isAutoretenedor_renta()));
        a.setEntradas(0);
        a.setSalidas(0);
        a.setNoFactura(0);
        a.setNoFacturaCompra(nofactura);       
        a.setBaseIVA(0);
        if(this.ObtenerValorRTF(debito,this.usuario.isAutoretenedor_renta())==0){
        a.setBaseRTF(0);
        }else{
        a.setBaseRTF(debito);    
        }
        a.setTiporegistro(tipo);
        lista.add(a);
        
        return lista;
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
     * @return the p_rtf
     */
    public double getP_rtf() {
        return p_rtf;
    }

    /**
     * @param p_rtf the p_rtf to set
     */
    public void setP_rtf(double p_rtf) {
        this.p_rtf = p_rtf;
    }
}
