/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IngresarDocumento.java
 *
 * Created on 4/04/2012, 06:07:52 PM
 */
package sic.Presentacion.Modulos.Documento;

import java.awt.Color;
import java.awt.event.KeyEvent;
import sic.Presentacion.Modulos.Usuario.BuscarUsuario;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.DocumentoService;
import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Subcontabilidad.SubcontabilidadService;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;
import sic.Dominio.Servicios.Facturacion.SaldoFactura;
import sic.Dominio.Servicios.SingletonAsientoCollection.SingletonAsientoCollection;
import sic.Presentacion.Modulos.PUC.BuscarEnAuxiliar;
import sic.Presentacion.BuscarFacturaDeUsuario;
import sic.Presentacion.ComponentesGraficos.AdministradorDeRegistrosCausables;
import sic.Presentacion.ComponentesGraficos.VisorDeAsientosResumidos;
import sic.Presentacion.ModelosDeTabla.AsientoModel;
import sic.Presentacion.Modulos.Documento.herramientas.TrasladoMultiple;
import sic.Presentacion.Suscripciones.ISuscripcionAdministradorDeRegistrosCausables;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnAuxiliar;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarFacturaDeUsuario;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarUsuario;

/**
 *
 * @author FANNY BURGOS
 */ 

public class IngresarDocumento extends javax.swing.JFrame implements ISuscripcionBuscarUsuario,ISuscripcionBuscarEnAuxiliar,ISuscripcionBuscarFacturaDeUsuario,ISuscripcionAdministradorDeRegistrosCausables,sic.Presentacion.Modulos.Documento.herramientas.YoUsoTrasladoMultiple,sic.Presentacion.Suscripciones.ISuscripcion_BuscarSubcontabilidad,sic.Presentacion.Suscripciones.ISuscripcionBuscarCentroDeCosto{
    Documento documento;   
    Vector <TipoDocumentoContable> lista;
    DocumentoService service;
    Usuario usuario;
    BuscarUsuario bu;
    Cta_PUC cta;
    BuscarEnAuxiliar ba;
    Vector <Asiento> asientos;
    Asiento a;
    AsientoModel m;
    int filaSeleccionada=-1;
    int NoTipoDocumentos=0;
    String item="";
    private String actor="";
    private Vector<TipoDocumentoContable> tiposdedocumentos;  
    private boolean PuedeCrearTiposDeDocumentos=true;
    BuscarFacturaDeUsuario bf;
    SaldoFactura saldo_factura;
    AdministradorDeRegistrosCausables adm=null;
    TrasladoMultiple tm;
    private String aux_de_caja_para_balanceo_automatico;
    private boolean trabaja_balanceo_automatico_con_caja;
    sic.Presentacion.Modulos.Subcontabilidad.BuscarSubcontabilidad bs;
    sic.Dominio.Core.Subcontabilidad.Subcontabilidad subcontabilidad;
    sic.Presentacion.Modulos.EstructuraDeCostos.BuscarCentroDeCosto bcc;
    sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto centro_de_costo;
    private boolean trabajan_subcontabilidades=false;
    /** Creates new form IngresarDocumento */
    public IngresarDocumento() {
        initComponents();        
        this.jFormattedTextField13.setValue(new Long(0));
        this.jDialog1.pack();
        service=new DocumentoService();
        this.tiposdedocumentos=service.ObtenerTiposDeDocumentosContablesNormaLocal_NormaInternacional(); 
        this.CargarComboBox();
        this.InicializarModeloAsientos();
        this.LimpiarPanel_Asiento();
        this.getContentPane().setBackground(Color.WHITE);
        this.subcontabilidad=null;
        this.setSubcontabilidad(subcontabilidad);
    }
    void BuscarSubcontabilidad(){
        this.bs=new sic.Presentacion.Modulos.Subcontabilidad.BuscarSubcontabilidad();
        bs.setSuscripcion(this);
        bs.show();
        this.dispose();
    }
    void BuscarCentroDeCostos(){
        if(this.subcontabilidad!=null){
        bcc=new sic.Presentacion.Modulos.EstructuraDeCostos.BuscarCentroDeCosto();
        bcc.setEstructura_de_costos(this.subcontabilidad.getEstructura_de_costos());
        bcc.Cargar();
        bcc.setSuscripcion(this);
        bcc.show();
        }else{
            JOptionPane.showMessageDialog(this,"Debes asignar una subcontabilidad");
        }
        //this.dispose();
    }
    void BalancearConCaja(){
        Asiento a=new Asiento();
        a.setCtaPuc(new PucService().ObtenerCtaPuc(this.aux_de_caja_para_balanceo_automatico));
        String d="";
        if(this.asientos.get(0)!=null){
            d=this.asientos.get(0).getDetalle();
        }
        a.setDetalle(d);
        double suma_creditos=this.service.ObtenerSumaCreditos(asientos);
        double suma_debitos=this.service.ObtenerSumaDebitos(asientos);
        if(suma_creditos>suma_debitos){
            a.setDebito(suma_creditos-suma_debitos);
            a.setCredito(0);
            this.asientos.add(a);
            this.DibujarTabla();
            this.VerificarSumasIguales();
            this.LimpiarPanel_Asiento();
            
        }
        if(suma_creditos<suma_debitos){
            a.setCredito(suma_debitos-suma_creditos);
            a.setDebito(0);
            this.asientos.add(a);
            this.DibujarTabla();
            this.VerificarSumasIguales();
            this.LimpiarPanel_Asiento();
        }        
    }
    public void BuscarFacturaDeUsuario(){
        this.dispose();
        bf=new BuscarFacturaDeUsuario();
        bf.setSuscripcion(this);        
        bf.show();
        bf.setUsuario(usuario);        
    }
    public void initTrasladoMultiple(){
        tm=new TrasladoMultiple();
        tm.show();
        tm.setSuscripcion(this);
        this.dispose();
    }
    void InicializarAdministradorDeRegistrosCausables(){
        if(this.usuario!=null){
        adm=new AdministradorDeRegistrosCausables(usuario,this.m.ObtenerRegistrosCausables());                    
        adm.setSuscripcion(this);        
        adm.show();
        }else{
        adm=new AdministradorDeRegistrosCausables();        
        adm.setLista(this.m.ObtenerRegistrosCausables());
        adm.setSuscripcion(this);        
        }
    }
    void InicializarModeloAsientos(){
        m=new AsientoModel();
        asientos=new Vector <Asiento>();
        m.setLista(asientos);
        this.jTable1.setModel(m);  
        this.setAnchoColumnas();
        this.jTable1.repaint();
        adm=null;
    }
    private void setAnchoColumnas(){       
        JViewport scroll =  (JViewport) this.jTable1.getParent();
        int ancho = scroll.getWidth();
        int anchoColumna=0;
        TableColumnModel modeloColumna = this.jTable1.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < this.jTable1.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch(i){
                case 0: anchoColumna = (7*ancho)/100;
                        break;
                case 1: anchoColumna = (30*ancho)/100;
                        break;
                case 2: anchoColumna = (35*ancho)/100;
                        break;
                case 3: anchoColumna = (8*ancho)/100;
                        break;
                case 4: anchoColumna = (10*ancho)/100;
                        break;    
                case 5: anchoColumna = (10*ancho)/100;
                        break;        
            }                     
            columnaTabla.setPreferredWidth(anchoColumna);           
        }
    }  
    void Limpiar(){                
        this.InicializarModeloAsientos();
        this.jTextField4.setText("");
        this.usuario=null;
        this.jButton2.setEnabled(false);
        this.jTextField1.setText("");
        this.jFormattedTextField1.setText("");
        this.jFormattedTextField2.setText("");
        this.jFormattedTextField3.setText("");
        this.LimpiarPanel_Asiento();
        this.jTextField5.setText("");
        this.jTextField9.setText("0");
        this.jTextField10.setText("0");                                  
        this.jButton1.setEnabled(true);  
        item=this.jComboBox1.getSelectedItem().toString();
        this.CargarComboBox();        
        this.subcontabilidad=null;
        this.setSubcontabilidad(subcontabilidad);
    }
    void LimpiarPanel_Asiento(){
        this.jButton7.setEnabled(false);
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextField4.setText("");
        this.cta=null;
        this.jFormattedTextField4.setValue(new Double(0));
        this.jFormattedTextField5.setValue(new Double(0));
        this.jFormattedTextField6.setValue(new Double(0));
        this.jFormattedTextField7.setValue(new Double(0));
        this.jFormattedTextField8.setValue(new Double(0));
        this.jFormattedTextField9.setValue(new Double(0));
        this.jFormattedTextField10.setValue(new Double(0));
        this.jFormattedTextField11.setValue(new Integer(0));
        this.jFormattedTextField12.setValue(new Integer(0));
        this.jFormattedTextField14.setValue(new Double(0));
        this.jFormattedTextField15.setValue(new Double(0));
        this.jFormattedTextField16.setValue(new Double(0));
        this.setCentroDeCosto(null);
    }
    void GuardarTipoDocumentoContable(){
        if(this.PuedeCrearTiposDeDocumentos){                
           TipoDocumentoContable td=new TipoDocumentoContable();
           td.setDescripcion(this.jTextField6.getText());
           td.setAbreviatura(this.jTextField7.getText());
           Long u=(Long) this.jFormattedTextField13.getValue();        
           int numeracion=u.intValue();
           td.setUltimanumeracion(numeracion);        
           this.jLabel24.setText(td.getAbreviatura());        
           this.jTextField8.setText(""+(numeracion+1));
           this.jDialog1.hide();
           this.jComboBox1.addItem(td);  
           this.jComboBox1.setSelectedItem(td);
           this.BuscarUsuario();
        }else{
           JOptionPane.showMessageDialog(this.jDialog1,this.actor+" NO PUEDES CREAR TIPOS DE DOCUMENTOS");             
           this.show();
           this.jDialog1.hide();
        }
    }
    void CargarComboBox(){        
        this.jComboBox1.removeAllItems();
        this.jComboBox1.addItem(" ");
        this.jComboBox1.addItem("Nuevo");
        Vector <TipoDocumentoContable> lista=this.tiposdedocumentos;
        Iterator <TipoDocumentoContable> it=lista.iterator();
        this.NoTipoDocumentos=lista.size();
        int sw=0;
        while(it.hasNext()){
            TipoDocumentoContable td=it.next();
            this.jComboBox1.addItem(td);
            if(td.toString().equals(item)){
                this.jComboBox1.setSelectedItem(td);     
                sw=1;
            }
        }
        if(sw==0){
            if(lista.size()>0){
                this.jComboBox1.setSelectedIndex(2);
            }else{
                this.jComboBox1.setSelectedItem("");
            }
        }        
        
    }
    void LimpiarDialog(){
        this.jTextField6.setText("");
        this.jTextField7.setText("");
        this.jFormattedTextField13.setValue(new Long(0));
    }
    void BuscarUsuario(){
        bu=new BuscarUsuario();
        bu.setSuscripcion(this);
        bu.show();
        this.dispose();
    }
    public void BuscarEnAuxiliar(){
        ba=new BuscarEnAuxiliar();
        ba.setSuscripcion(this);
        ba.setCtat(usuario);
        this.dispose();        
    } 
    void GuardarDocumento(){
        Documento d=new Documento();
        TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
        d.setAbreviatura(td.getAbreviatura());
        d.setTdocumento(td.getDescripcion());
        d.setFechaContable(this.dateChooserCombo1.getSelectedDate().getTime());
        d.setCreador(""+this.actor);
        d.setResumen(this.jTextField5.getText());
        d.setUsuario(usuario);
        d.setNumeracion(Integer.parseInt(this.jTextField8.getText()));
        d.setAsientos(asientos);
        d.setNorma_internacional(true);
        d.setNorma_local(true);
        d.setSubcontabilidad(subcontabilidad);
        if(this.service.Guardar(d)){
            this.Limpiar();
            this.jComboBox1.requestFocus();
        }
        JOptionPane.showMessageDialog(this,service.ObtenerMensaje());        
    }
    void InicializarAsientoDelPanel(){
        a=new Asiento(); 
        a.setCtaPuc(cta);
        a.setDetalle(this.jTextField4.getText());
        double preciounitario=((Double)this.jFormattedTextField6.getValue()).doubleValue();
        double entradas=((Double)this.jFormattedTextField7.getValue()).doubleValue();
        double salidas=((Double)this.jFormattedTextField8.getValue()).doubleValue();
        double debito;double credito;
        if(preciounitario==0 && salidas!=0){
            this.jFormattedTextField6.setValue(this.jFormattedTextField16.getValue());               
            preciounitario=((Double)this.jFormattedTextField6.getValue()).doubleValue();
        }
        if(preciounitario!=0 || entradas!=0){
            debito=Math.round(preciounitario*entradas);
        }else{
            debito=Math.round(((Double)this.jFormattedTextField4.getValue()).doubleValue());
        }
        if(preciounitario!=0 || salidas!=0){
            credito=Math.round(preciounitario*salidas);
        }else{
            credito=Math.round(((Double)this.jFormattedTextField5.getValue()).doubleValue());
        }
        a.setDebito(debito);
        a.setCredito(credito);
        this.jFormattedTextField4.setValue(debito);
        this.jFormattedTextField5.setValue(credito);
        a.setEntradas(entradas);
        a.setSalidas(salidas);
        a.setBaseRTF(((Double)this.jFormattedTextField9.getValue()).doubleValue());
        a.setBaseIVA(((Double)this.jFormattedTextField10.getValue()).doubleValue());
        a.setNoFactura(((Integer)this.jFormattedTextField11.getValue()).intValue());
        a.setNoFacturaCompra(((Integer)this.jFormattedTextField12.getValue()).intValue());        
        a.setCentro_de_costos(centro_de_costo);
    }
    void VerificarSiEsAuxCliente(){
        if(this.cta.getId().toString().substring(0,4).equals("1305")){
            this.jButton7.setEnabled(true);
        }else{
            this.jButton7.setEnabled(false);
        }           
    }
    void DibujarAsientoEnPanel(){
        this.cta=a.getCtaPuc();        
        this.VerificarSiEsAuxCliente();
        this.jTextField2.setText(cta.getId().toString());
        this.jTextField3.setText(cta.getDenominacion());
        this.jTextField4.setText(a.getDetalle());
        this.jFormattedTextField4.setValue(new Double(a.getDebito()));
        this.jFormattedTextField5.setValue(new Double(a.getCredito()));
        if(a.getEntradas()+a.getSalidas()!=0){
            this.jFormattedTextField6.setValue(new Double((a.getCredito()+a.getDebito())/(a.getEntradas()+a.getSalidas())));
        }else{
            this.jFormattedTextField6.setValue(new Double(0));  
        }
        this.jFormattedTextField7.setValue(new Double(a.getEntradas()));
        this.jFormattedTextField8.setValue(new Double(a.getSalidas()));
        this.jFormattedTextField9.setValue(new Double(a.getBaseRTF()));
        this.jFormattedTextField10.setValue(new Double(a.getBaseIVA()));
        this.jFormattedTextField11.setValue(new Integer(a.getNoFactura()));
        this.jFormattedTextField12.setValue(new Integer(a.getNoFacturaCompra()));
           //
           //   Creo un servicio de contabilidad
           //   para obtener el saldo
           ContabilidadService cs=new ContabilidadService();
           double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),true,null);
           double existencia=cs.getDao().ObtenerExistencia(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),true,null);
           this.jFormattedTextField14.setValue(new Double(saldo));                           
           this.jFormattedTextField15.setValue(new Double(existencia));                           
           if(existencia!=0){
                 this.jFormattedTextField16.setValue(new Double(saldo/existencia));
           }else{
                 this.jFormattedTextField16.setValue(new Double(0)); 
           }           
           this.setCentroDeCosto(a.getCentro_de_costos());
    }
    
    void DibujarTabla(){
        m=new AsientoModel();
        m.setLista(asientos);
        this.jTable1.setModel(m);
        this.setAnchoColumnas();
    }
    void AgregarAsiento(){
        this.InicializarAsientoDelPanel();
        asientos.add(a);
        this.DibujarTabla();
        this.VerificarSumasIguales();
        this.LimpiarPanel_Asiento();
    }
    void ModificarAsiento(){
        this.InicializarAsientoDelPanel();
        asientos.set(this.filaSeleccionada,a);
        this.DibujarTabla();
        this.VerificarSumasIguales();
        this.LimpiarPanel_Asiento();
        this.jButton3.setEnabled(true);
        this.jButton4.setEnabled(false);
    }
    void EventoSeleccionDeFila(){
        this.a=this.asientos.get(this.filaSeleccionada);                
        this.DibujarAsientoEnPanel();
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(true);
        this.jTextField4.requestFocus();        
    }
    void EventoComboBox(){        
        if(this.jComboBox1.getSelectedItem()!=null && this.jComboBox1.getSelectedItem().toString().equals("Nuevo")){
             this.LimpiarDialog();
             this.jDialog1.show();           
             this.jTextField6.requestFocus();
        }
        if(this.jComboBox1.getSelectedItem()!=null && this.jComboBox1.getSelectedIndex()>1 && this.jComboBox1.getSelectedIndex()<=1+this.NoTipoDocumentos){
            this.jButton1.setEnabled(true);
            this.jTextField8.setText(""+(service.getDao().ObtenerNumeracion(this.jComboBox1.getSelectedItem().toString())+1));
            TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
            this.jLabel24.setText(td.getAbreviatura());
        }else{
            if(this.jComboBox1.getSelectedItem()!=null && this.jComboBox1.getSelectedIndex()>1+this.NoTipoDocumentos){
               TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
               this.jTextField8.setText(""+(td.getUltimanumeracion()+1));                 
               this.jLabel24.setText(td.getAbreviatura());
               this.jButton1.setEnabled(true); 
            }else{
               this.jButton1.setEnabled(false);
            }
        }
    }
    void VerificarSumasIguales(){
        if(service.HaySumasIguales(asientos)){            
            this.jTextField10.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaDebitos(asientos)));
            this.jTextField9.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaCreditos(asientos)));
            this.jButton5.setEnabled(true);
            this.jTextField5.requestFocus();
        }else{            
            this.jTextField10.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaDebitos(asientos)));
            this.jTextField9.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaCreditos(asientos)));
            this.jButton5.setEnabled(false);
            if(adm==null){
               this.jButton2.requestFocus();
            }
        }
    }
    public void AgregarAsientos(Vector<Asiento> _asientos){
        this.asientos=new Vector<Asiento>();            
        Iterator<Asiento> it=this.m.getLista().iterator();
        while(it.hasNext()){
            Asiento miasiento=it.next();             
            this.asientos.add(miasiento);            
        }    
        Iterator<Asiento> it2=_asientos.iterator();
        while(it2.hasNext()){
            Asiento miasiento=it2.next();             
            this.asientos.add(miasiento);            
        }    
        this.DibujarTabla();        
        this.VerificarSumasIguales();
        this.jTable1.repaint();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jLabel25 = new javax.swing.JLabel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField10 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialog1WindowClosing(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Tipo de Documento Contable");

        jLabel21.setText("Descripcion  :");

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
        });

        jLabel22.setText("Abreviatura :");

        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
        });

        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFormattedTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField13KeyPressed(evt);
            }
        });

        jLabel23.setText("Ultima Numeracion :");

        jButton6.setText("Nuevo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton6KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(157, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap(358, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(26, 26, 26))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/Presentacion/Modulos/Documento/image (27).png"))); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, 0, 345, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos Informativos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Fecha Contable :");

        dateChooserCombo1.setNothingAllowed(false);
        dateChooserCombo1.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        dateChooserCombo1.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Usuario :");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("No Documento :");

        jFormattedTextField1.setEnabled(false);
        jFormattedTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Direccion :");

        jFormattedTextField2.setEnabled(false);
        jFormattedTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Tipo Doc            :");

        jFormattedTextField3.setEnabled(false);
        jFormattedTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Subcontabilidad :");

        jTextField11.setEnabled(false);

        jTextField12.setEnabled(false);

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setText("Buscar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(jFormattedTextField3)))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                    .addComponent(jFormattedTextField2)
                    .addComponent(jTextField1))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Asiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Cod CTA :");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField2.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Denominacion :");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField3.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Buscar");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Detalle :");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Debito :");

        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.setText("0");
        jFormattedTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField4FocusLost(evt);
            }
        });
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Credito :");

        jFormattedTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField5.setText("0");
        jFormattedTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField5FocusLost(evt);
            }
        });
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Precio Unitario :");

        jFormattedTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField6.setText("0");
        jFormattedTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Entradas :");

        jFormattedTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField7.setText("0");
        jFormattedTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField7KeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Salidas :");

        jFormattedTextField8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField8.setText("0");
        jFormattedTextField8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField8FocusGained(evt);
            }
        });
        jFormattedTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField8KeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Base RTF :");

        jFormattedTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField9.setText("0");
        jFormattedTextField9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField9FocusGained(evt);
            }
        });
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Base IVA :");

        jFormattedTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField10.setText("0");
        jFormattedTextField10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyPressed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Ingresar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Modificar");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Factura :");

        jFormattedTextField11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField11.setText("0");
        jFormattedTextField11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField11KeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("FactCP :");

        jFormattedTextField12.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField12.setText("0");
        jFormattedTextField12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyPressed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("SALDO :");

        jFormattedTextField14.setEditable(false);
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField14.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Existencia :");

        jFormattedTextField15.setEditable(false);
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFormattedTextField16.setEditable(false);
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField16.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Precio Prom :");

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton7.setText("BUSCAR");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jLabel29.setText("CC :");

        jButton9.setText("buscar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(jFormattedTextField6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(22, 22, 22)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField5))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel17)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jFormattedTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7))
                                    .addComponent(jFormattedTextField8)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2)
                    .addComponent(jLabel29)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jFormattedTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jFormattedTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jFormattedTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Resumen :");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Ingresar");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cod. CTA", "Denominacion", "Detalle", "Debito", "Credito"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField10.setEditable(false);
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField10.setText(" ");

        jTextField9.setEditable(false);
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setText(" ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opciones");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem2.setText("Ingresar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem1.setText("Ver Ultimo Documento");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItem3.setText("Ver Asientos Resumidos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem4.setText("Administrador de Registros Causables");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem5.setText("Pegar Movimientos Contables");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem6.setText("Modificar Detalle a Todo el Documento");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem8.setText("Balancear con caja");
        jMenuItem8.setEnabled(false);
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Avanzadas");

        jMenuItem7.setText("Ayudante de Traslado Multiple");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        this.EventoComboBox();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.GuardarTipoDocumentoContable();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jDialog1WindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowClosing
        // TODO add your handling code here:
        this.LimpiarDialog();
        this.jComboBox1.setSelectedIndex(0);
    }//GEN-LAST:event_jDialog1WindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.BuscarUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.BuscarEnAuxiliar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.AgregarAsiento();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if(this.jTable1.getSelectedRow()!=-1){
            this.filaSeleccionada=this.jTable1.getSelectedRow();
            this.EventoSeleccionDeFila();            
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.ModificarAsiento();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
         if(this.jTable1.getSelectedRow()!=-1){
            this.filaSeleccionada=this.jTable1.getSelectedRow();
            this.EventoSeleccionDeFila();
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Guardar este Documento");
        if(op==0)
        this.GuardarDocumento();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField4.requestFocus();
        }
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jFormattedTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField5.requestFocus();
        }
        
    }//GEN-LAST:event_jFormattedTextField4KeyPressed

    private void jFormattedTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField5KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField6.requestFocus();
        }
        
    }//GEN-LAST:event_jFormattedTextField5KeyPressed

    private void jFormattedTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField6KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField7.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField6KeyPressed

    private void jFormattedTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField7KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField8.requestFocus();                        
        }
    }//GEN-LAST:event_jFormattedTextField7KeyPressed

    private void jFormattedTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField8KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField9.requestFocus();                        
        }
    }//GEN-LAST:event_jFormattedTextField8KeyPressed

    private void jFormattedTextField9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField9KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField10.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField9KeyPressed

    private void jFormattedTextField10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField10KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField12.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField10KeyPressed

    private void jFormattedTextField12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField12KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField11.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField12KeyPressed

    private void jFormattedTextField11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField11KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            if(this.jButton7.isEnabled()){
              this.jButton7.requestFocus();   
            }else{ 
            if(this.jButton3.isEnabled()) 
            this.jButton3.requestFocus();
            else
            this.jButton4.requestFocus();    
            }
        }
    }//GEN-LAST:event_jFormattedTextField11KeyPressed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.AgregarAsiento();
        }
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            this.ModificarAsiento();
        }
    }//GEN-LAST:event_jButton4KeyPressed

    private void jFormattedTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField4FocusLost
        // TODO add your handling code here:
        if(((Double)this.jFormattedTextField4.getValue()).doubleValue()!=0){
            this.jFormattedTextField5.setValue(new Double(0));
        }
    }//GEN-LAST:event_jFormattedTextField4FocusLost

    private void jFormattedTextField5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField5FocusLost
        // TODO add your handling code here:
        if(this.jFormattedTextField5.getText().equals("0")==false){
            this.jFormattedTextField4.setValue(new Double(0));
        }
    }//GEN-LAST:event_jFormattedTextField5FocusLost

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){            
            if(this.trabajan_subcontabilidades){
               this.BuscarSubcontabilidad();
            }else{
               this.BuscarUsuario();
            }
        }
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jTextField7.requestFocus();
        }
    }//GEN-LAST:event_jTextField6KeyPressed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField13.requestFocus();
        }
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jFormattedTextField13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField13KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jButton6.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField13KeyPressed

    private void jButton6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton6KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.GuardarTipoDocumentoContable();
        }
    }//GEN-LAST:event_jButton6KeyPressed

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jButton5.requestFocus();
        }
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        // TODO add your handling code here:
        if(this.jButton5.isEnabled()==true){
            if(evt.getKeyCode()==evt.VK_ENTER){
                this.GuardarDocumento();
            }
        }
    }//GEN-LAST:event_jButton5KeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:      
        if(this.jComboBox1.getSelectedIndex()>1 && this.jComboBox1.getSelectedIndex()<=1+this.NoTipoDocumentos){
           FolderDeDocumentos fd=new FolderDeDocumentos();              
           fd.CargarDocumento(this.jComboBox1.getSelectedItem().toString());        
           fd.show();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if(this.jButton5.isEnabled()){
            int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Guardar este Documento");
            if(op==0)
            this.GuardarDocumento();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jFormattedTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField8FocusGained
        // TODO add your handling code here:
        if(((Double)this.jFormattedTextField7.getValue()).doubleValue()!=0){
            this.jFormattedTextField8.setValue(new Double(0));
        }
        this.InicializarAsientoDelPanel();
    }//GEN-LAST:event_jFormattedTextField8FocusGained

    private void jFormattedTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField9FocusGained
        // TODO add your handling code here:
        if(((Double)this.jFormattedTextField8.getValue()).doubleValue()!=0){
            this.jFormattedTextField7.setValue(new Double(0));
        }
        this.InicializarAsientoDelPanel();
    }//GEN-LAST:event_jFormattedTextField9FocusGained

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        this.BuscarFacturaDeUsuario();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton7KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.BuscarFacturaDeUsuario();
        }else{
            if(evt.getKeyCode()==evt.VK_RIGHT){
            if(this.jButton3.isEnabled()) 
            this.jButton3.requestFocus();
            else
            this.jButton4.requestFocus();    
            }
        }
    }//GEN-LAST:event_jButton7KeyPressed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        VisorDeAsientosResumidos v=new VisorDeAsientosResumidos();
        v.setLista(asientos);
        v.Cargar();
        v.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:        
        this.InicializarAdministradorDeRegistrosCausables();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if(this.usuario!=null){
           this.AgregarAsientos(SingletonAsientoCollection.getInstance().getAsientos());      
        }else{
           JOptionPane.showMessageDialog(this,"Escoje Primero un Usuario");             
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        String detalle=JOptionPane.showInputDialog("Detalle : ");
        this.m.ModificarDetalle(detalle);
        this.DibujarTabla();
        this.VerificarSumasIguales();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        this.initTrasladoMultiple();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        this.BalancearConCaja();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        this.BuscarSubcontabilidad();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            this.BuscarEnAuxiliar();
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.BuscarCentroDeCostos();
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IngresarDocumento().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField11;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField14;
    private javax.swing.JFormattedTextField jFormattedTextField15;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    private javax.swing.JFormattedTextField jFormattedTextField9;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
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
    public void EventoDeSeleccionUsuario() {
        this.setUsuario(bu.ObtenerUsuarioSeleccionado());                
        this.show();
        this.bu.dispose();
        this.jButton2.setEnabled(true);        
        this.BuscarEnAuxiliar();        
    }
    @Override
    public void setUsuario(Usuario u) {
        this.usuario=u;
        if(u!=null){
           this.jFormattedTextField1.setValue(new Long(this.usuario.getNoDocumento()));
           this.jTextField1.setText(usuario.NombreCompleto());
           this.jFormattedTextField3.setText(usuario.getTipodocumento().getAbreviatura());
           this.jFormattedTextField2.setText(usuario.getDireccion()+" "+usuario.getMunicipio().toString());
        }else{
           this.jFormattedTextField1.setValue(new Long(0));
           this.jTextField1.setText("");
           this.jFormattedTextField3.setText("");
           this.jFormattedTextField2.setText(""); 
        }
    }
    @Override
    public void EventoDeSeleccionAuxiliar() {       
        if(ba.ObtenerAuxiliarSeleccionado().getId().toString().length()>8){
           PucService ps=new PucService(); 
           if(ps.ObtenerCtaPuc(ba.ObtenerAuxiliarSeleccionado().getId().toString().substring(0,4))!=null && ps.ObtenerCtaPuc(ba.ObtenerAuxiliarSeleccionado().getId().toString().substring(0,6))!=null){ 
              // si es cliente habilita busqueda de facturas 
              if(ba.ObtenerAuxiliarSeleccionado().getId().toString().substring(0,4).equals("1305")){
                  this.jButton7.setEnabled(true);
              }else{
                  this.jButton7.setEnabled(false);
              } 
              this.cta=ba.ObtenerAuxiliarSeleccionado();
              this.jTextField2.setText(cta.getId().toString());
              this.jTextField3.setText(cta.getDenominacion());
              //
              //   Creo un servicio de contabilidad
              //   para obtener el saldo
              ContabilidadService cs=new ContabilidadService();
              double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),true,null);
              double existencia=cs.getDao().ObtenerExistencia(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),true,null);
              this.jFormattedTextField14.setValue(new Double(saldo));                           
              this.jFormattedTextField15.setValue(new Double(existencia));                           
              if(existencia!=0){
                 this.jFormattedTextField16.setValue(new Double(saldo/existencia));
              }else{
                 this.jFormattedTextField16.setValue(new Double(0)); 
              }
              ba.dispose();
              this.show();
              this.jTextField4.requestFocus();
              if(service.ObtenerSumaDebitos(asientos)>service.ObtenerSumaCreditos(asientos)){
                 saldo=service.ObtenerSumaDebitos(asientos)-service.ObtenerSumaCreditos(asientos); 
                 this.jFormattedTextField5.setValue(new Double(saldo));                   
              }
              if(service.ObtenerSumaCreditos(asientos)>service.ObtenerSumaDebitos(asientos)){
                 saldo=service.ObtenerSumaCreditos(asientos)-service.ObtenerSumaDebitos(asientos); 
                 this.jFormattedTextField4.setValue(new Double(saldo));                   
              }
              boolean entra_a_centro_de_costos=cta.getId().toString().charAt(0)=='5' || cta.getId().toString().charAt(0)=='6' || cta.getId().toString().charAt(0)=='7';
              if(this.trabajan_subcontabilidades && entra_a_centro_de_costos==true ){                  
                  this.BuscarCentroDeCostos();
              }
           }else{
                JOptionPane.showMessageDialog(this.ba,"la Cta o la Subcta de este Aux."+ba.ObtenerAuxiliarSeleccionado().getId().toString().substring(0,8)+" No Existe");       
           }
        }else{
            JOptionPane.showMessageDialog(this.ba,"Solo Se Admite Auxiliar Contable");
        }
    }
    @Override
    public void setCtaAuxiliar(Cta_PUC cta) {
        this.cta=cta;
    }

    /**
     * @param actor the actor to set
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * @param PuedeCrearTiposDeDocumentos the PuedeCrearTiposDeDocumentos to set
     */
    public void setPuedeCrearTiposDeDocumentos(boolean PuedeCrearTiposDeDocumentos) {
        this.PuedeCrearTiposDeDocumentos = PuedeCrearTiposDeDocumentos;
    }

    @Override
    public void EventoDeSeleccionSaldoFactura() {
        this.show();
        bf.dispose();
        this.setSaldoFactura(bf.getSeleccionado());
    }

    @Override
    public void setSaldoFactura(SaldoFactura sa) {
        if(sa!=null){           
           this.jFormattedTextField5.setValue(new Double(sa.getSaldo()));           
           this.jFormattedTextField11.setValue(new Integer(sa.getFactura()));
           this.jFormattedTextField5.requestFocus();
        }else{
           this.jFormattedTextField11.setValue(new Integer(0));
        }
    }

    @Override
    public void EventoEnvioDeRegistrosCausables() {
        Vector<Asiento> l=this.m.AsignarRegistrosCausables(adm.getLista(),adm.getUsuario().isAutoretenedor_renta());
        this.setUsuario(adm.getUsuario());
        System.out.println(l.size());
        this.asientos=l;                
        this.jButton2.setEnabled(true);
        this.jButton3.setEnabled(true);
        this.jButton4.setEnabled(false);
        this.DibujarTabla();
        this.VerificarSumasIguales();                
        this.LimpiarPanel_Asiento();        
        this.adm.dispose();         
        
    }

    @Override
    public void AsignarAsientosDeAuxiliar(Vector<Asiento> asientos) {
        this.asientos=asientos;
        this.DibujarTabla();
    }

    /**
     * @param aux_de_caja_para_balanceo_automatico the aux_de_caja_para_balanceo_automatico to set
     */
    public void setAux_de_caja_para_balanceo_automatico(String aux_de_caja_para_balanceo_automatico) {
        this.aux_de_caja_para_balanceo_automatico = aux_de_caja_para_balanceo_automatico;
        this.setTrabaja_balanceo_automatico_con_caja(true);
        this.jMenuItem8.setEnabled(true);
    }

    /**
     * @return the trabaja_balanceo_automatico_con_caja
     */
    public boolean isTrabaja_balanceo_automatico_con_caja() {
        return trabaja_balanceo_automatico_con_caja;
    }

    /**
     * @param trabaja_balanceo_automatico_con_caja the trabaja_balanceo_automatico_con_caja to set
     */
    public void setTrabaja_balanceo_automatico_con_caja(boolean trabaja_balanceo_automatico_con_caja) {
        this.trabaja_balanceo_automatico_con_caja = trabaja_balanceo_automatico_con_caja;                
    }

    @Override
    public void EventoDeSeleccionDeSubcontabilidad() {
        SubcontabilidadService ss=new SubcontabilidadService();
        if(ss.ObtenerHijos(bs.getSeleccionado().getId()).size()==0){
           this.setSubcontabilidad(bs.getSeleccionado());
           this.show();
           this.bs.dispose();
           if(this.trabajan_subcontabilidades){
              this.BuscarUsuario();
           }
        }else{
           JOptionPane.showMessageDialog(this,"Debe Ser Un Nodo Hoja");
        }
    }

    @Override
    public void setSubcontabilidad(Subcontabilidad subcontabilidad) {
        this.subcontabilidad=subcontabilidad;
        if(this.subcontabilidad!=null){
           this.jTextField11.setText(""+this.subcontabilidad.getId());
           this.jTextField12.setText(""+this.subcontabilidad.getDescripcion());
        }else{
           this.jTextField11.setText("0");
           this.jTextField12.setText("Ninguna"); 
        }
    }

    @Override
    public void EventoDeSeleccionDeCentroDeCosto() {
        this.setCentroDeCosto(bcc.getSeleccionado());
        this.show();
        bcc.dispose();
    }

    @Override
    public void setCentroDeCosto(CentroDeCosto obj) {
        this.centro_de_costo=obj;
        if(obj!=null){
            this.jTextField13.setText(""+obj.getDescripcion());
            this.jTextField13.setToolTipText(""+obj.getId());
        }else{
            this.jTextField13.setText("Ninguno");
            this.jTextField13.setToolTipText("");
        }
    }

    /**
     * @param trabajan_subcontabilidades the trabajan_subcontabilidades to set
     */
    public void setTrabajan_subcontabilidades(boolean trabajan_subcontabilidades) {
        this.trabajan_subcontabilidades = trabajan_subcontabilidades;
    }
}
