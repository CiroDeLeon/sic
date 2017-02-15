/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.Core.Activo_Fijo.dto;

import java.util.Date;

/**
 *
 * @author Usuario1
 */
public class Registro_De_Deterioro {
    
    private long id;
    private String descripcion;
    private double valor_local;
    private double valor_niif;
    private double valor_deterioro_local;    
    private double valor_de_uso;
    private double saldo_en_libro;
    private double valor_deterioro_niif;
    private int periodo;
    private Date fecha;


    @Override
    public String toString() {
        return id+"/"+descripcion+"/"+valor_local+"/"+valor_niif+"/"+valor_deterioro_local+"/"+valor_deterioro_niif+"/"+periodo; //To change body of generated methods, choose Tools | Templates.
    }
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
     * @return the valor_deterioro_local
     */
    public double getValor_deterioro_local() {
        return valor_deterioro_local;
    }

    /**
     * @param valor_deterioro_local the valor_deterioro_local to set
     */
    public void setValor_deterioro_local(double valor_deterioro_local) {
        this.valor_deterioro_local = valor_deterioro_local;
    }

    /**
     * @return the valor_deterioro_niif
     */
    public double getValor_deterioro_niif() {
        return valor_deterioro_niif;
    }

    /**
     * @param valor_deterioro_niif the valor_deterioro_niif to set
     */
    public void setValor_deterioro_niif(double valor_deterioro_niif) {
        this.valor_deterioro_niif = valor_deterioro_niif;
    }

    /**
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the valor_de_uso
     */
    public double getValor_de_uso() {
        return valor_de_uso;
    }

    /**
     * @param valor_de_uso the valor_de_uso to set
     */
    public void setValor_de_uso(double valor_de_uso) {
        this.valor_de_uso = valor_de_uso;
    }

    /**
     * @return the saldo_en_libro
     */
    public double getSaldo_en_libro() {
        return saldo_en_libro;
    }

    /**
     * @param saldo_en_libro the saldo_en_libro to set
     */
    public void setSaldo_en_libro(double saldo_en_libro) {
        this.saldo_en_libro = saldo_en_libro;
    }
}
