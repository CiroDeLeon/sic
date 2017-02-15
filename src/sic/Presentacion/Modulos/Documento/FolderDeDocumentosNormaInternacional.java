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
import sic.Presentacion.Modulos.Usuario.BuscarUsuario;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.Documento;
import sic.Dominio.Core.Documento.DocumentoService;
import sic.Dominio.Core.Documento.otros.TipoDocumentoContable;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Servicios.Contabilidad.ContabilidadService;
import sic.Dominio.Servicios.Facturacion.SaldoFactura;
import sic.Dominio.Servicios.SingletonAsientoCollection.SingletonAsientoCollection;
import sic.Presentacion.Modulos.PUC.BuscarEnAuxiliar;
import sic.Presentacion.BuscarFacturaDeUsuario;
import sic.Presentacion.ComponentesGraficos.AdministradorDeRegistrosCausables;
import sic.Aplicacion.Servicios.Reportes.ReporteService;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;
import sic.Dominio.Core.Subcontabilidad.Subcontabilidad;
import sic.Dominio.Core.Subcontabilidad.SubcontabilidadService;
import sic.Presentacion.ComponentesGraficos.VisorDeAsientosResumidos;
import sic.Presentacion.ModelosDeTabla.AsientoModel;
import sic.Presentacion.Suscripciones.ISuscripcionAdministradorDeRegistrosCausables;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnAuxiliar;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarFacturaDeUsuario;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarUsuario;

/**
 *
 * @author FANNY BURGOS
 */
public class FolderDeDocumentosNormaInternacional extends javax.swing.JFrame implements ISuscripcionBuscarUsuario,ISuscripcionBuscarEnAuxiliar,ISuscripcionBuscarFacturaDeUsuario,KeyListener,ISuscripcionAdministradorDeRegistrosCausables,sic.Presentacion.Suscripciones.ISuscripcion_BuscarSubcontabilidad,sic.Presentacion.Suscripciones.ISuscripcionBuscarCentroDeCosto{
    DocumentoService service;
    AsientoModel m;
    Vector <Asiento> asientos;
    int filaSeleccionada=-1;
    Asiento a;
    Cta_PUC cta;
    Usuario usuario;
    BuscarUsuario bu;
    BuscarEnAuxiliar ba;
    Documento d;
    Object idAsiento;    
    private String actor;
    private boolean ValidoModificar=true;
    private Vector<TipoDocumentoContable> tiposdedocumentos;
    BuscarFacturaDeUsuario bf;
    SaldoFactura saldo_factura;
    AdministradorDeRegistrosCausables adm=null;
    sic.Presentacion.Modulos.Subcontabilidad.BuscarSubcontabilidad bs;
    sic.Dominio.Core.Subcontabilidad.Subcontabilidad subcontabilidad;
    
    sic.Presentacion.Modulos.EstructuraDeCostos.BuscarCentroDeCosto bcc;
    sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto centro_de_costo;
    /** Creates new form IngresarDocumento */
    public FolderDeDocumentosNormaInternacional() {
        initComponents();
        this.jButton2.addKeyListener(this);
        this.jButton3.addKeyListener(this);
        service=new DocumentoService();
        asientos=new Vector <Asiento>();
        this.tiposdedocumentos=service.ObtenerTiposDeDocumentosContablesNormaInternacional(); 
        this.CargarComboBox();
        this.InicializarModeloAsientos();      
        this.getContentPane().setBackground(Color.WHITE);
    }
    void BuscarSubcontabilidad(){
        this.bs=new sic.Presentacion.Modulos.Subcontabilidad.BuscarSubcontabilidad();
        bs.setSuscripcion(this);
        bs.show();
        this.dispose();
    }
    public void Buscar(){
        String num=JOptionPane.showInputDialog("Digite el numero del "+this.jComboBox1.getSelectedItem()+" que desea ver");
        try{
           int n=Integer.parseInt(num);
           this.Cargar(this.jComboBox1.getSelectedItem().toString(),n);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Debes Digitar Un Numero");
            this.Buscar();
        }
    }
    public void BuscarFacturaDeUsuario(){
        this.dispose();
        bf=new BuscarFacturaDeUsuario();
        bf.setSuscripcion(this);        
        bf.show();
        bf.setUsuario(usuario);        
    }
    private int ObtenerPosicionEnCombo(String descripcion){
        for(int i=0;i<this.jComboBox1.getItemCount();i++){
            if(this.jComboBox1.getItemAt(i).toString().equals(descripcion)==true){
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }
    private void BuscarEnAuxiliar(){
        ba=new BuscarEnAuxiliar();
        ba.setSuscripcion(this);
        ba.setCtat(usuario);
        this.dispose();        
    } 
    private void CargarComboBox(){
        this.jComboBox1.removeAllItems();
        Iterator <TipoDocumentoContable> it=this.tiposdedocumentos.iterator();
        while(it.hasNext()){
            TipoDocumentoContable td=it.next();
            this.jComboBox1.addItem(td);
        }
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
                case 0: anchoColumna = (8*ancho)/100;
                        break;
                case 1: anchoColumna = (30*ancho)/100;
                        break;
                case 2: anchoColumna = (40*ancho)/100;
                        break;
                case 3: anchoColumna = (11*ancho)/100;
                        break;
                case 4: anchoColumna = (11*ancho)/100;
                        break;    
            }                     
            columnaTabla.setPreferredWidth(anchoColumna);           
        }
    }  
    private void BuscarUsuario(){
        bu=new BuscarUsuario();
        bu.setSuscripcion(this);
        bu.show();
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
    private void InicializarModeloAsientos(){
        m=new AsientoModel();        
        m.setLista(asientos);
        this.jTable1.setModel(m);  
        this.setAnchoColumnas();
        this.jTable1.repaint();
    }
    private void EventoComboBox(){        
        TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
        if(td!=null){        
           this.jTextField6.setText(""+service.getDao().ObtenerNumeracion(td.getDescripcion()));        
           this.Cargar(td.getDescripcion(),service.getDao().ObtenerNumeracion(td.getDescripcion()));
        }
    }
    private void Cargar(String tipodocumento,int numeracion){
        Documento doc=service.ObtenerDocumento(tipodocumento, numeracion);
        if(doc!=null){ 
           d=doc; 
           this.setSubcontabilidad(d.getSubcontabilidad());
           this.jTextField6.setText(""+numeracion);
           Calendar cal=Calendar.getInstance();
           cal.setTime(d.getFechaContable());
           this.dateChooserCombo1.setSelectedDate(cal);           
           this.jFormattedTextField1.setText(""+d.getUsuario().getNoDocumentoCompleto());
           this.jTextField5.setText(""+d.getResumen());
           this.jTextField1.setText(d.getUsuario().NombreCompleto());
           this.jFormattedTextField3.setText(d.getUsuario().getTipodocumento().getDescripcion());
           this.jFormattedTextField2.setText(d.getUsuario().getDireccion()+" "+d.getUsuario().getMunicipio().toString());
           asientos=d.getAsientos();
           this.InicializarModeloAsientos();
           this.VerificarSumasIguales();
           this.jButton4.setEnabled(false);
           this.jButton6.setEnabled(true);
           this.usuario=d.getUsuario();
           this.LimpiarPanel_Asiento();
           if(d.isActivo()==false){
               this.jButton8.setEnabled(false);
               this.jLabel20.setText("ANULADO");
               this.jLabel20.setToolTipText(d.getAnulador()+" "+d.getRazonAnulacion()+" "+d.getFechaAnulacion().toLocaleString());
               //this.jButton5.setEnabled(false);
               //this.jButton7.setEnabled(false);               
           }else{
               this.jButton8.setEnabled(true);
               this.jLabel20.setText("");
               this.jLabel20.setToolTipText("");
               this.jButton5.setEnabled(true);
               this.jButton7.setEnabled(true);               
           }           
           this.jButton2.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this," El "+tipodocumento+" "+numeracion+" No Existe");
        }        
    }
    private void EventoSeleccionDeFila(){
       // if(d.isActivo()==true){
           System.out.println("Evento Seleccion de Fila : "+this.filaSeleccionada);             
           this.a=this.asientos.get(this.filaSeleccionada);           
           this.DibujarAsientoEnPanel();        
           this.jButton4.setEnabled(true);
           this.jButton6.setEnabled(true);
           this.jButton9.setEnabled(false);
           this.jTextField4.requestFocus();
        //}
    }
    
    private void VerificarSumasIguales(){
        if(service.HaySumasIguales(asientos)){            
            this.jTextField10.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaDebitos(asientos)));
            this.jTextField9.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaCreditos(asientos)));
            this.jButton5.setEnabled(true);
            this.jButton8.setEnabled(true);
            this.jButton1.setEnabled(true);
            this.jButton6.setEnabled(false);
            //this.jTextField5.requestFocus();
        }else{            
            this.jTextField10.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaDebitos(asientos)));
            this.jTextField9.setText(""+NumberFormat.getInstance().format(service.ObtenerSumaCreditos(asientos)));
            this.jButton5.setEnabled(false);            
            this.jButton8.setEnabled(false);
            this.jButton1.setEnabled(true);
            this.jButton6.setEnabled(true);
        }
    }
    private void ModificarAsiento(){
        this.InicializarAsientoDelPanel();
        System.out.println(a.getId());
        a.setDocumento(d);
        asientos.set(this.filaSeleccionada,a);
        this.DibujarTabla();
        this.VerificarSumasIguales();
        this.LimpiarPanel_Asiento();
        this.jButton3.setEnabled(true);
        this.jButton4.setEnabled(false);
        this.jButton9.setEnabled(true);
    }
       private void AgregarAsiento(){
        a=new Asiento();    
        this.InicializarAsientoDelPanel();
        a.setId("0");
        a.setDocumento(d);
        asientos.add(a);
        this.DibujarTabla();
        this.VerificarSumasIguales();
        this.LimpiarPanel_Asiento();
        this.jButton3.setEnabled(true);
        this.jButton4.setEnabled(false);        
    }
    void InicializarAsientoDelPanel(){        
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
            this.jButton10.setEnabled(true);
        }else{
            this.jButton10.setEnabled(false);
        }           
    }
    void DibujarAsientoEnPanel(){
        this.cta=a.getCtaPuc();
        this.VerificarSiEsAuxCliente();
        this.jTextField2.setText(cta.getId().toString());
        PucService ps=new PucService();
        this.jTextField3.setText(ps.ObtenerCtaPuc(cta.getId()).getDenominacion());
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
           double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),false,null);
           double existencia=cs.getDao().ObtenerExistencia(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),false,null);
           this.jFormattedTextField14.setValue(new Double(saldo));                           
           this.jFormattedTextField15.setValue(new Double(existencia));                           
           if(existencia!=0){
                 this.jFormattedTextField16.setValue(new Double(saldo/existencia));
           }else{
                 this.jFormattedTextField16.setValue(new Double(0)); 
           }
          this.setCentroDeCosto(a.getCentro_de_costos());
    }
    private void DibujarTabla(){
        m=new AsientoModel();
        m.setLista(asientos);
        System.out.println("Dibujando Tabla > "+asientos.size());
        this.jTable1.setModel(m);
        this.setAnchoColumnas();
    }
    private void LimpiarPanel_Asiento(){
        this.jButton10.setEnabled(false);
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
        this.idAsiento=null;
        this.setCentroDeCosto(null);
    }    
    private void Anular(){
        String razon=JOptionPane.showInputDialog("Razon de la Anulacion : ");
        Object id=d.getId();
        String anulador=this.actor;
        if(this.service.getDao().AnularDocumento(id, anulador, razon) && razon!=null){
             TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
             if(td!=null){
                 int n=Integer.parseInt(this.jTextField6.getText());            
                 this.Cargar(td.getDescripcion(),n);
             }
        }
    }
    private void Modificar(){
        if(this.isValidoModificar()){
           d.setAsientos(asientos);
           d.setUsuario(usuario);
           d.setFechaContable(this.dateChooserCombo1.getSelectedDate().getTime());
           d.setResumen(this.jTextField5.getText());
           d.setModificador(actor);
           d.setSubcontabilidad(subcontabilidad);
           this.service.Modificar(d);                
           JOptionPane.showMessageDialog(this,service.ObtenerMensaje());
           TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
           int numeracion=Integer.parseInt(this.jTextField6.getText());
           this.Cargar(td.getDescripcion(),numeracion);
        }else{
           JOptionPane.showMessageDialog(this,this.actor+" NO TIENES PRIVILEGIOS DE MODIFICACION"); 
        }
    }
    private void VerReporte(){
            TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
            if(td!=null){
                int n=Integer.parseInt(this.jTextField6.getText());                            
                ReporteService.VerReporte("DocumentoContable.jasper",this.service.getDao().ObtenerReporteDocumento(td.getDescripcion(),n),td.getDescripcion());               
            }
    }
    public void CargarDocumento(String documento){                                          
        if(this.ObtenerPosicionEnCombo(documento)!=-1){
           this.jComboBox1.setSelectedIndex( this.ObtenerPosicionEnCombo(documento));                         
        }
    }
    public void CargarDocumento(String documento,int numeracion){                                          
        if(this.ObtenerPosicionEnCombo(documento)!=-1){
           this.jComboBox1.setSelectedIndex( this.ObtenerPosicionEnCombo(documento));                         
           this.Cargar(documento,numeracion);
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
            miasiento.setId("0");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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
        jButton7 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
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
        jButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jButton10 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/Presentacion/Modulos/Documento/image (27).png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jComboBox1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Comprobante de Egreso", "Comprobante de Ingreso" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setText("<<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(">>");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos Informativos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Fecha Contable :");

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

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Subcontabilidad :");

        jTextField7.setEditable(false);

        jTextField8.setEditable(false);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setText("Buscar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setToolTipText("Este Boton te pone Vacia la Subcontabilidad");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addGap(34, 34, 34)
                        .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(34, 34, 34)
                        .addComponent(jFormattedTextField1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(jFormattedTextField3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField2)
                            .addComponent(jTextField1)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12)))
                .addGap(81, 81, 81))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

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

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Modificar");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setText("Nuevo");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("SALDO :");

        jFormattedTextField14.setEditable(false);
        jFormattedTextField14.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Existencia :");

        jFormattedTextField15.setEditable(false);
        jFormattedTextField15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Precio Prom.");

        jFormattedTextField16.setEditable(false);
        jFormattedTextField16.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton10.setText("Buscar");
        jButton10.setEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jButton10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton10KeyPressed(evt);
            }
        });

        jLabel22.setText("CC:");

        jButton13.setText("BUSCAR");

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
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jFormattedTextField11)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton10))
                                    .addComponent(jFormattedTextField8)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton13)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(jButton6)
                    .addComponent(jLabel22)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13))
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
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton4)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Resumen :");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Ver Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jTextField10.setEditable(false);
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField10.setText(" ");
        jPanel6.add(jTextField10);

        jTextField9.setEditable(false);
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setText(" ");
        jPanel6.add(jTextField9);

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setText("Anular");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(46, 46, 46)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(278, 278, 278))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("Opciones");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem3.setText("Modificar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem1.setText("Ver Reporte");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem2.setText("Anular");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem4.setText("Buscar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItem5.setText("Ver Asientos Resumidos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem6.setText("Administrador De Registros Causables");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem7.setText("Copiar Movimientos Contables");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem8.setText("Pegar Movimientos Contables");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem9.setText("Modificar Detalle a todo el Documento");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        this.EventoComboBox();                 
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
         if(this.jTable1.getSelectedRow()!=-1){
            this.filaSeleccionada=this.jTable1.getSelectedRow();
            this.EventoSeleccionDeFila();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
        if(td!=null){
           int n=Integer.parseInt(this.jTextField6.getText());            
           this.Cargar(td.getDescripcion(),n-1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
        if(td!=null){
           int n=Integer.parseInt(this.jTextField6.getText());            
           this.Cargar(td.getDescripcion(),n+1);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        this.BuscarUsuario();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.ModificarAsiento();
        this.jButton6.setEnabled(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.BuscarEnAuxiliar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Anular este Documento");
        if(op==0)
        this.Anular();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Modificar este Documento");
        if(op==0)
        this.Modificar();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.VerReporte();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if(this.jButton5.isEnabled()){
           int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Modificar este Documento");
           if(op==0) 
           this.Modificar();
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.VerReporte();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
       if(this.jButton8.isEnabled()) {
        int op=JOptionPane.showConfirmDialog(this,"Esta Seguro de Anular este Documento");
        if(op==0)   
        this.Anular();
       }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.AgregarAsiento();
        this.jButton6.setEnabled(true);
    }//GEN-LAST:event_jButton9ActionPerformed

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
            if(this.jButton10.isEnabled()){
              this.jButton10.requestFocus();   
            }else{ 
            if(this.jButton3.isEnabled()) 
            this.jButton3.requestFocus();
            else
            this.jButton4.requestFocus();    
            }
        }
    }//GEN-LAST:event_jFormattedTextField11KeyPressed

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

    private void jButton10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton10KeyPressed
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
    }//GEN-LAST:event_jButton10KeyPressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        this.BuscarFacturaDeUsuario();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        this.Buscar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        VisorDeAsientosResumidos v=new VisorDeAsientosResumidos();
        v.setLista(asientos);
        v.Cargar();
        v.show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        this.InicializarAdministradorDeRegistrosCausables();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:        
        SingletonAsientoCollection.getInstance().setAsientos(this.asientos);         
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        this.AgregarAsientos(SingletonAsientoCollection.getInstance().getAsientos());      
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        String detalle=JOptionPane.showInputDialog("Detalle : ");
        this.m.ModificarDetalle(detalle);
        this.DibujarTabla();
        this.VerificarSumasIguales();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        this.BuscarSubcontabilidad();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        this.setSubcontabilidad(null);
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FolderDeDocumentosNormaInternacional().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField11;
    private javax.swing.JFormattedTextField jFormattedTextField12;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
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
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
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
        usuario=bu.ObtenerUsuarioSeleccionado();
        this.jFormattedTextField1.setValue(new Long(this.usuario.getNoDocumento()));
        this.jTextField1.setText(usuario.NombreCompleto());
        this.jFormattedTextField3.setText(usuario.getTipodocumento().getAbreviatura());
        this.jFormattedTextField2.setText(usuario.getDireccion()+" "+usuario.getMunicipio().toString());
        this.bu.dispose();
        this.show();
        this.jButton2.setEnabled(true);        
    }

    @Override
    public void setUsuario(Usuario u) {
        this.usuario=u;
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
                 // this.jButton7.setEnabled(false);
              } 
               this.cta=ba.ObtenerAuxiliarSeleccionado();
               this.jTextField2.setText(cta.getId().toString());
               this.jTextField3.setText(cta.getDenominacion());           
               //
               //   Creo un servicio de contabilidad
               //   para obtener el saldo
               ContabilidadService cs=new ContabilidadService();
              double saldo=cs.getDao().ObtenerSaldo(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),false,null);
              double existencia=cs.getDao().ObtenerExistencia(cta.getId().toString(),this.dateChooserCombo1.getSelectedDate().getTime(),false,null);
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

    @Override
    public void keyTyped(KeyEvent evt) {
       // throw new UnsupportedOperationException("Not supported yet.");
        
    }

    @Override
    public void keyPressed(KeyEvent evt) {        
        if(evt.getKeyCode()==evt.VK_RIGHT){
            TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
            if(td!=null){
                int n=Integer.parseInt(this.jTextField6.getText());            
                this.Cargar(td.getDescripcion(),n+1);
                this.jButton3.requestFocus();
            }     
        }
        if(evt.getKeyCode()==evt.VK_LEFT){
            TipoDocumentoContable td=(TipoDocumentoContable) this.jComboBox1.getSelectedItem();
            if(td!=null){
               int n=Integer.parseInt(this.jTextField6.getText());            
               this.Cargar(td.getDescripcion(),n-1);
               this.jButton2.requestFocus();
            }   
        }        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @param actor the actor to set
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * @return the ValidoModificar
     */
    public boolean isValidoModificar() {
        return ValidoModificar;
    }

    /**
     * @param ValidoModificar the ValidoModificar to set
     */
    public void setValidoModificar(boolean ValidoModificar) {
        this.ValidoModificar = ValidoModificar;
    }

    /**
     * @param tiposdedocumentos the tiposdedocumentos to set
     */
    public void setTiposdedocumentos(Vector<TipoDocumentoContable> tiposdedocumentos) {
        this.tiposdedocumentos = tiposdedocumentos;
        this.CargarComboBox();
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
        this.DibujarTabla();
        this.VerificarSumasIguales();                
        this.LimpiarPanel_Asiento();        
        this.adm.dispose();         
    }

    @Override
    public void EventoDeSeleccionDeSubcontabilidad() {
        SubcontabilidadService ss=new SubcontabilidadService();
        if(ss.ObtenerHijos(bs.getSeleccionado().getId()).size()==0){
           this.setSubcontabilidad(bs.getSeleccionado());
           this.show();
           bs.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"Debe Ser Un Nodo Hoja");
        }
    }

    @Override
    public void setSubcontabilidad(Subcontabilidad subcontabilidad) {
        this.subcontabilidad=subcontabilidad;
        if(this.subcontabilidad!=null){
            this.jTextField7.setText(""+subcontabilidad.getId());
            this.jTextField8.setText(""+subcontabilidad.getDescripcion());
        }else{
            this.jTextField7.setText("0");
            this.jTextField8.setText("Ninguna");
        }
    }

    @Override
    public void EventoDeSeleccionDeCentroDeCosto() {
        this.setCentroDeCosto(bcc.getSeleccionado());        
        bcc.dispose();
        this.show();
    }

    @Override
    public void setCentroDeCosto(CentroDeCosto obj) {
        this.centro_de_costo=obj;
        if(obj!=null){
            this.jTextField11.setText(""+obj.getDescripcion());
            this.jTextField11.setToolTipText(""+obj.getId());
        }else{
            this.jTextField11.setText("");
            this.jTextField11.setToolTipText("");
        }
    }
}
