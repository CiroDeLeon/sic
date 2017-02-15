/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VerAsientosDeAuxiliar.java
 *
 * Created on 17/05/2012, 01:34:38 PM
 */
package sic.Presentacion.Modulos.Contabilidad;


import datechooser.beans.DateChooserCombo;
import java.awt.Color;
import sic.Presentacion.Modulos.PUC.BuscarEnAuxiliar;
import sic.Presentacion.Modulos.Documento.FolderDeDocumentos;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.text.NumberFormat;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Servicios.Contabilidad.AsientoContableDTO;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;
import sic.Aplicacion.Servicios.Reportes.ReporteService;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Presentacion.ModelosDeTabla.AsientoContableDTOModelCliente;
import sic.Presentacion.ModelosDeTabla.AsientoContableDTOModelIva;
import sic.Presentacion.ModelosDeTabla.AsientoContableDTOModelMercancia;
import sic.Presentacion.ModelosDeTabla.AsientoContableDTOModelNormal;
import sic.Presentacion.Modulos.PUC.BuscarEnAuxiliar;
import sic.Presentacion.Modulos.Subcontabilidad.BuscarSubcontabilidad;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnAuxiliar;

/**
 *
 * @author FANNY BURGOS
 */
public class VerAsientosDeAuxiliar extends javax.swing.JFrame implements ISuscripcionBuscarEnAuxiliar,sic.Presentacion.Suscripciones.ISuscripcion_BuscarSubcontabilidad{
    BuscarEnAuxiliar ba;
    Cta_PUC cta;
    AsientoContableDTOModelNormal mnormal;
    AsientoContableDTOModelCliente mcliente;
    AsientoContableDTOModelMercancia mmercancia;
    AsientoContableDTOModelIva miva;
    ContabilidadService service;
    Vector <AsientoContableDTO> lista;
    Subcontabilidad subcontabilidad;
    BuscarSubcontabilidad bs;
    int fila;
    
    java.util.Date fecha_inicial;
    java.util.Date fecha_final;
    /** Creates new form VerAsientosDeAuxiliar */
    public VerAsientosDeAuxiliar() {
        initComponents();
        service=new ContabilidadService();
        this.initModelo();
        this.jLabel8.setVisible(false);
        this.jTextField5.setVisible(false);
        this.jLabel11.setVisible(false);
        this.jTextField8.setVisible(false);
        this.jLabel12.setVisible(false);
        this.jTextField9.setVisible(false);
        this.BuscarEnAuxiliar();
        this.getContentPane().setBackground(Color.WHITE);
        this.jComboBox4.setSelectedIndex(23);
        this.jComboBox5.setSelectedIndex(59);
    }
    public void setFechaInicial(Calendar a){
        this.dateChooserCombo1.setSelectedDate(a);
    }
    public void setFechaFinal(Calendar a){
        this.dateChooserCombo2.setSelectedDate(a);
    }
    public void BuscarSubcontabilidad(){
        this.bs=new BuscarSubcontabilidad();
        bs.setSuscripcion(this);
        bs.show();
        this.dispose();
    }
    public void BuscarEnAuxiliar(){
        ba=new BuscarEnAuxiliar();        
        ba.setSuscripcion(this);  
        ba.setCtat(null);
        this.dispose();
    }
    public void initModelo(){
        mnormal=new AsientoContableDTOModelNormal();        
        this.jTable1.setModel(mnormal);
    }
    public void CargarNormal(){
        mnormal=new AsientoContableDTOModelNormal(); 
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        AsientoContableDTO.saldoanterior=saldo;
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
            int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getDebito();
            fila[6]=a.getCredito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[7]=saldo;
            fila[8]=a.getEntradas();
            fila[9]=a.getSalidas();
            fila[10]=existencia;
            fila[11]=a.getNoFactura();
            fila[12]=a.getNoFacturaCompra();
            fila[13]=a.getBaseiva();
            fila[14]=a.getBasertf();
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            mnormal.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jTable1.setModel(mnormal);            
        this.jTable1.repaint();
    }
    public void CargarMercancia(){
        mmercancia=new AsientoContableDTOModelMercancia();        
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.saldoanterior=saldo;
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.existenciaanterior=existencia;
        this.jLabel8.setVisible(true);
        this.jTextField5.setVisible(true);
        this.jTextField5.setText(""+NumberFormat.getInstance().format(existencia));
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
            int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getEntradas();
            fila[6]=a.getDebito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[7]=a.getSalidas();
            fila[8]=a.getCredito();
            fila[9]=existencia;
            fila[10]=saldo;           
            if(existencia!=0)
            fila[11]=saldo/existencia;
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            mmercancia.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jTextField8.setText(NumberFormat.getInstance().format(sumae));
        this.jTextField9.setText(NumberFormat.getInstance().format(sumas));
        this.jLabel11.setVisible(true);
        this.jTextField8.setVisible(true);
        this.jLabel12.setVisible(true);
        this.jTextField9.setVisible(true);
        this.jTable1.setModel(mmercancia);            
        this.jTable1.repaint();
    }
    public void CargarCliente(){
        mcliente=new AsientoContableDTOModelCliente();        
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.saldoanterior=saldo;
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
           int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getNoFactura();
            fila[6]=a.getDebito();
            fila[7]=a.getCredito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[8]=saldo;            
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            mcliente.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jTable1.setModel(mcliente);            
        this.jTable1.repaint();
    }
    public void CargarProveedor(){
        mcliente=new AsientoContableDTOModelCliente();        
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.saldoanterior=saldo;
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;        
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
            int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getNoFacturaCompra();
            fila[6]=a.getDebito();
            fila[7]=a.getCredito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[8]=saldo;            
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            mcliente.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();            
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jTable1.setModel(mcliente);            
        this.jTable1.repaint();
    }
    public void CargarIva(){
        miva=new AsientoContableDTOModelIva();        
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.saldoanterior=saldo;
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;
        double sumab=0;
        double sumar=0;
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
            int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getNoFactura()+a.getNoFacturaCompra();
            fila[6]=a.getDebito();
            fila[7]=a.getCredito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[8]=a.getBaseiva();
            fila[9]=saldo;           
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            miva.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();
            sumab+=a.getBaseiva();
            if(a.getBaseiva()!=0){
               sumar+=a.getDebito()-a.getCredito();
            }            
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jLabel11.setText("Suma Base : ");
        this.jTextField8.setText(NumberFormat.getInstance().format(sumab));
        this.jLabel12.setText("Suma Iva :");
        this.jTextField9.setText(NumberFormat.getInstance().format(Math.abs(sumar)));
        this.jLabel11.setVisible(true);
        this.jTextField8.setVisible(true);
        this.jTextField9.setVisible(true);
        this.jLabel12.setVisible(true);
        this.jTable1.setModel(miva);            
        this.jTable1.repaint();
        sic.Dominio.Servicios.Contabilidad.AsientoContableDTO.sumartf=Math.abs(sumar);
    }
    public void CargarRtf(){
        miva=new AsientoContableDTOModelIva();        
        boolean normal=false;
        if(this.jComboBox1.getSelectedIndex()==0){
            normal=true;
        }else{
            normal=false;
        }
        Iterator <AsientoContableDTO> it=service.getDao().ObtenerAsientos(cta.getId().toString(),this.fecha_inicial,this.fecha_final,normal,subcontabilidad).iterator();
        long dm=1;// dia en milisegundos
        Date fecha=this.fecha_inicial;
        double saldo=service.getDao().ObtenerSaldo(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        AsientoContableDTO.saldoanterior=saldo;
        this.jTextField4.setText(""+NumberFormat.getInstance().format(saldo));
        double existencia=service.getDao().ObtenerExistencia(cta.getId().toString(),new Date(fecha.getTime()-dm),normal,subcontabilidad);
        lista=new Vector<AsientoContableDTO>();
        double sumad=0;
        double sumac=0;
        double sumae=0;
        double sumas=0;
        double sumab=0;
        double sumar=0;
        while(it.hasNext()){
            AsientoContableDTO a=it.next();
            Object [] fila=new Object[15];
           int hora=a.getFechacontable().getHours();
            int minutos=a.getFechacontable().getMinutes();
            String h="";
            String min="";
            if(hora<=9){
                h="0"+hora;
            }else{
                h=""+hora;
            }            
            if(minutos<=9){
               min="0"+minutos; 
            }else{
               min=""+minutos; 
            }
            fila[0]=a.getFechacontable().getDate()+"/"+(a.getFechacontable().getMonth()+1)+"/"+(a.getFechacontable().getYear()+1900)+" "+h+":"+min;
            fila[1]=a.getNitusuario();
            fila[2]=a.getUsuario();
            fila[3]=a.getSoporte();
            fila[4]=a.getDetalle();
            fila[5]=a.getNoFactura()+a.getNoFacturaCompra();
            fila[6]=a.getDebito();
            fila[7]=a.getCredito();
            if(service.getDao().isDebito(cta.getId().toString())){
                saldo=saldo+a.getDebito()-a.getCredito();
                existencia=existencia+a.getEntradas()-a.getSalidas();
            }else{
                saldo=saldo+a.getCredito()-a.getDebito();
                existencia=existencia+a.getSalidas()-a.getEntradas();
            }
            fila[8]=a.getBasertf();
            fila[9]=saldo;           
            a.setSaldo(saldo);
            a.setExistencia(existencia);
            if(normal==true){
               a.setModo("NORMA LOCAL");
            }else{
               a.setModo("NORMA INTERNACIONAL"); 
            }
            lista.add(a);
            miva.addRow(fila);
            sumad+=a.getDebito();
            sumac+=a.getCredito();
            sumae+=a.getEntradas();
            sumas+=a.getSalidas();
            sumab+=a.getBasertf();
            if(a.getBasertf()!=0){
               sumar+=a.getDebito()-a.getCredito();
            }
        }
        this.jTextField6.setText(NumberFormat.getInstance().format(sumad));
        this.jTextField7.setText(NumberFormat.getInstance().format(sumac));
        this.jLabel11.setText("Suma Base : ");
        this.jTextField8.setText(NumberFormat.getInstance().format(sumab));        
        this.jLabel12.setText("Suma Rtf :");
        this.jTextField9.setText(NumberFormat.getInstance().format(Math.abs(sumar)));
        this.jLabel11.setVisible(true);
        this.jTextField8.setVisible(true);
        this.jLabel12.setVisible(true);
        this.jTextField9.setVisible(true);
        this.jTable1.setModel(miva);            
        this.jTable1.repaint();
        sic.Dominio.Servicios.Contabilidad.AsientoContableDTO.sumartf=Math.abs(sumar);        
    }
    java.util.Date getFecha(DateChooserCombo d,int hora,int minuto){
        Date f=d.getSelectedDate().getTime();
        Date fecha=new java.util.Date(f.getYear(),f.getMonth(),f.getDate());
        long hora_en_segundos=hora*60*60;
        long minutos_en_segundos=minuto*60;
        long t=(hora_en_segundos+minutos_en_segundos)*1000;
        return new java.util.Date(fecha.getTime()+t);
    }
    void Cargar(){
         fecha_inicial=this.getFecha(dateChooserCombo1,this.jComboBox2.getSelectedIndex(),this.jComboBox3.getSelectedIndex());
         fecha_final=this.getFecha(dateChooserCombo2,this.jComboBox4.getSelectedIndex(),this.jComboBox5.getSelectedIndex());
        System.out.println(this.fecha_inicial.toString());
        AsientoContableDTO.fechainicial=this.fecha_inicial;
        AsientoContableDTO.fechafinal=this.fecha_final;
        this.jLabel8.setVisible(false);
        this.jTextField5.setVisible(false);
        this.jLabel11.setVisible(false);
        this.jTextField8.setVisible(false);
        this.jLabel12.setVisible(false);
        this.jTextField9.setVisible(false);
        this.jLabel11.setText("Suma Entradas :");
        this.jLabel12.setText("Suma Salidas :");
        if(this.cta.getCategoria().equals("Normal")){
            this.CargarNormal();
        }
        if(this.cta.getCategoria().equals("Mercancia")){
            this.CargarMercancia();
        }
        if(this.cta.getCategoria().equals("Cliente")){
            this.CargarCliente();
        }
        if(this.cta.getCategoria().equals("Proveedor")){
            this.CargarProveedor();
        }
        if(this.cta.getCategoria().equals("Iva")){
            this.CargarIva();
        }
         if(this.cta.getCategoria().equals("Rtf")){
            this.CargarRtf();
        }
    }
    void VerSoporte(){        
        if(fila!=-1){
            FolderDeDocumentos fd=new FolderDeDocumentos();
            fd.CargarDocumento(this.lista.get(fila).getTipodocumento(),this.lista.get(fila).getNumeracion());
            fd.show();
        }
    }
    void VerReporte(){
        if(this.cta.getCategoria().equals("Normal")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarNormal.jasper", lista,"Cuenta T Detallada");
        }
        if(this.cta.getCategoria().equals("Cliente")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarCliente.jasper", lista,"Cuenta T Detallada");
        }
        if(this.cta.getCategoria().equals("Proveedor")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarCliente.jasper", lista,"Cuenta T Detallada");
        }
        if(this.cta.getCategoria().equals("Rtf")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarRtf.jasper", lista,"Cuenta T Detallada");
        }
        if(this.cta.getCategoria().equals("Iva")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarIva.jasper", lista,"Cuenta T Detallada");
        }
        if(this.cta.getCategoria().equals("Mercancia")){
            ReporteService.VerReporte("VerAsientosDeAuxiliarMercancia.jasper", lista,"Cuenta T Detallada(Kardex Contable)");
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel6 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/Presentacion/Modulos/Contabilidad/image (22).png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configuracion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Denominacion :");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Aux :");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Titulo Cuenta T :");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha Inicial :");

        dateChooserCombo1.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        dateChooserCombo1.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Fecha Final :");

        dateChooserCombo2.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        dateChooserCombo2.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("CARGAR");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("VER REPORTE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("MODO :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMA LOCAL", "NORMA INTERNACIONAL" }));

        jLabel14.setText("SUBCONTABILIDAD :");

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(255, 255, 255));
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jButton4.setText("BUSCAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel15.setText("HORA :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jLabel16.setText("MIN :");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        jLabel17.setText("HORA :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jLabel18.setText("MIN :");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel14)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(dateChooserCombo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Saldo Anterior :");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Existencia Anterior :");

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Suma Debito  :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Suma Credito :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Suma Entradas :");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Suma Salidas :");

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField6.setEnabled(false);

        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField7.setEnabled(false);

        jTextField8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField8.setEnabled(false);

        jTextField9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setEnabled(false);

        jMenu1.setText("Opciones");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem1.setText("Ver Reporte");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem2.setText("Ver Soporte");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(27, 27, 27)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(27, 27, 27)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(30, 30, 30))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(19, 19, 19)))
                            .addComponent(jLabel12))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField9)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.BuscarEnAuxiliar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.Cargar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:        
        fila=this.jTable1.getSelectedRow();        
        this.VerSoporte();        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.VerReporte();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.VerReporte();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.BuscarSubcontabilidad();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.setSubcontabilidad(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VerAsientosDeAuxiliar().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void EventoDeSeleccionAuxiliar() {
        ba.dispose();
        this.initModelo();        
        this.show();
        this.setCtaAuxiliar(ba.ObtenerAuxiliarSeleccionado());
       
    }

    @Override
    public void setCtaAuxiliar(Cta_PUC cta) {
        this.cta=cta;
        String aux=cta.getId().toString().substring(0,8);
        this.jTextField2.setText(aux);
        this.jTextField3.setText(cta.getDenominacion());
        PucService s=new PucService();
        this.jTextField1.setText(s.ObtenerCtaPuc(aux).getDenominacion());  
        this.jButton2.setEnabled(true);
    }

    @Override
    public void EventoDeSeleccionDeSubcontabilidad() {
        this.show();
        this.bs.dispose();
        this.setSubcontabilidad(bs.getSeleccionado());
    }

    @Override
    public void setSubcontabilidad(Subcontabilidad subcontabilidad) {
        this.subcontabilidad=subcontabilidad;
        if(this.subcontabilidad!=null){
            this.jTextField10.setText(this.subcontabilidad.getId()+"-"+this.subcontabilidad.getDescripcion());
        }else{
            this.jTextField10.setText("Ninguna");
        }
    }
}
