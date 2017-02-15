/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Activo_Fijo;

import java.util.Date;
import sic.Dominio.Core.Cta_T.Cta_T;

/**
 *
 * @author Usuario1
 */
public class ActivoFijo {
    private long id;
    private Cta_T cta_t;
    private String descripcion;
    private int vida_util_en_dias;
    private String aux_activo_fijo;
    private String aux_activo_depreciacion;
    private String aux_gasto_depreciacion;
    private double valor_local;
    private double valor_niif;
    private String tipo;
    private double utilidad_esperada_1;
    private double utilidad_esperada_2;
    private double utilidad_esperada_3;
    private double utilidad_esperada_4;
    private Date fecha_adquisicion;    
    private Date fecha_ultima_depreciacion;    
    private boolean depreciacion_local_activada;
    private boolean depreciacion_niif_activada;
    

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the cta_t
     */
    public Cta_T getCta_t() {
        return cta_t;
    }

    /**
     * @param cta_t the cta_t to set
     */
    public void setCta_t(Cta_T cta_t) {
        this.cta_t = cta_t;
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
     * @return the vida_util_en_dias
     */
    public int getVida_util_en_dias() {
        return vida_util_en_dias;
    }

    /**
     * @param vida_util_en_dias the vida_util_en_dias to set
     */
    public void setVida_util_en_dias(int vida_util_en_dias) {
        this.vida_util_en_dias = vida_util_en_dias;
    }

    /**
     * @return the aux_activo_fijo
     */
    public String getAux_activo_fijo() {
        return aux_activo_fijo;
    }

    /**
     * @param aux_activo_fijo the aux_activo_fijo to set
     */
    public void setAux_activo_fijo(String aux_activo_fijo) {
        this.aux_activo_fijo = aux_activo_fijo;
    }

    /**
     * @return the aux_activo_depreciacion
     */
    public String getAux_activo_depreciacion() {
        return aux_activo_depreciacion;
    }

    /**
     * @param aux_activo_depreciacion the aux_activo_depreciacion to set
     */
    public void setAux_activo_depreciacion(String aux_activo_depreciacion) {
        this.aux_activo_depreciacion = aux_activo_depreciacion;
    }

    /**
     * @return the aux_gasto_depreciacion
     */
    public String getAux_gasto_depreciacion() {
        return aux_gasto_depreciacion;
    }

    /**
     * @param aux_gasto_depreciacion the aux_gasto_depreciacion to set
     */
    public void setAux_gasto_depreciacion(String aux_gasto_depreciacion) {
        this.aux_gasto_depreciacion = aux_gasto_depreciacion;
    }

    /**
     * @return the valor_local
     */
    public double getValor_local() {
        return valor_local;
    }

    /**
     * @param valor_local the valor_local to set
     */
    public void setValor_local(double valor_local) {
        this.valor_local = valor_local;
    }

    /**
     * @return the valor_niif
     */
    public double getValor_niif() {
        return valor_niif;
    }

    /**
     * @param valor_niif the valor_niif to set
     */
    public void setValor_niif(double valor_niif) {
        this.valor_niif = valor_niif;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the utilidad_esperada_1
     */
    public double getUtilidad_esperada_1() {
        return utilidad_esperada_1;
    }

    /**
     * @param utilidad_esperada_1 the utilidad_esperada_1 to set
     */
    public void setUtilidad_esperada_1(double utilidad_esperada_1) {
        this.utilidad_esperada_1 = utilidad_esperada_1;
    }

    /**
     * @return the utilidad_esperada_2
     */
    public double getUtilidad_esperada_2() {
        return utilidad_esperada_2;
    }

    /**
     * @param utilidad_esperada_2 the utilidad_esperada_2 to set
     */
    public void setUtilidad_esperada_2(double utilidad_esperada_2) {
        this.utilidad_esperada_2 = utilidad_esperada_2;
    }

    /**
     * @return the utilidad_esperada_3
     */
    public double getUtilidad_esperada_3() {
        return utilidad_esperada_3;
    }

    /**
     * @param utilidad_esperada_3 the utilidad_esperada_3 to set
     */
    public void setUtilidad_esperada_3(double utilidad_esperada_3) {
        this.utilidad_esperada_3 = utilidad_esperada_3;
    }

    /**
     * @return the utilidad_esperada_4
     */
    public double getUtilidad_esperada_4() {
        return utilidad_esperada_4;
    }

    /**
     * @param utilidad_esperada_4 the utilidad_esperada_4 to set
     */
    public void setUtilidad_esperada_4(double utilidad_esperada_4) {
        this.utilidad_esperada_4 = utilidad_esperada_4;
    }

    /**
     * @return the fecha_adquisicion
     */
    public Date getFecha_adquisicion() {
        return fecha_adquisicion;
    }

    /**
     * @param fecha_adquisicion the fecha_adquisicion to set
     */
    public void setFecha_adquisicion(Date fecha_adquisicion) {
        this.fecha_adquisicion = fecha_adquisicion;
    }


    /**
     * @return the depreciacion_local_activada
     */
    public boolean isDepreciacion_local_activada() {
        return depreciacion_local_activada;
    }

    /**
     * @param depreciacion_local_activada the depreciacion_local_activada to set
     */
    public void setDepreciacion_local_activada(boolean depreciacion_local_activada) {
        this.depreciacion_local_activada = depreciacion_local_activada;
    }

    /**
     * @return the depreciacion_niif_activada
     */
    public boolean isDepreciacion_niif_activada() {
        return depreciacion_niif_activada;
    }

    /**
     * @param depreciacion_niif_activada the depreciacion_niif_activada to set
     */
    public void setDepreciacion_niif_activada(boolean depreciacion_niif_activada) {
        this.depreciacion_niif_activada = depreciacion_niif_activada;
    }

    /**
     * @return the fecha_ultima_depreciacion
     */
    public Date getFecha_ultima_depreciacion() {
        return fecha_ultima_depreciacion;
    }

    /**
     * @param fecha_ultima_depreciacion the fecha_ultima_depreciacion to set
     */
    public void setFecha_ultima_depreciacion(Date fecha_ultima_depreciacion) {
        this.fecha_ultima_depreciacion = fecha_ultima_depreciacion;
    }
    public double getPrecioDiarioLocal(){
       return this.valor_local/this.vida_util_en_dias; 
    }
    public double getPrecioDiarioNiif(){
       return this.valor_local/this.vida_util_en_dias; 
    }
}
