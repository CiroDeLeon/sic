/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VsitaDeCausacion.java
 *
 * Created on 6/02/2013, 09:12:50 AM
 */
package sic.Presentacion.ComponentesGraficos;

import java.util.Vector;
import javax.swing.JOptionPane;
import sic.Dominio.Core.Documento.otros.RegistroDeCausacion;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Servicios.Dian.RetencionDian;
import sic.Presentacion.Modulos.PUC.BuscarEnAuxiliar;
import sic.Presentacion.Modulos.Dian.BuscarEnTablaDeRTF;
import sic.Presentacion.Modulos.Usuario.BuscarUsuario;
import sic.Presentacion.ModelosDeTabla.RegistroDeCausacionModel;
import sic.Presentacion.Suscripciones.ISuscripcionAdministradorDeRegistrosCausables;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnAuxiliar;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnTablaDeRTF;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarUsuario;

/**
 *
 * @author FANNY BURGOS
 */
public class AdministradorDeRegistrosCausables extends javax.swing.JFrame implements ISuscripcionBuscarUsuario,ISuscripcionBuscarEnAuxiliar,ISuscripcionBuscarEnTablaDeRTF{
    private Usuario usuario;
    BuscarEnAuxiliar ba;
    BuscarEnTablaDeRTF bt;
    Cta_PUC aux_debito;    
    Cta_PUC aux_iva;
    RetencionDian rd;
    int sw=1;
    final int debito=1;
    final int iva=2;
    private Vector <RegistroDeCausacion> lista;
    RegistroDeCausacionModel modelo;
    RegistroDeCausacion rc;
    int fila;
    private ISuscripcionAdministradorDeRegistrosCausables suscripcion;
    BuscarUsuario bu;
    /** Creates new form VsitaDeCausacion */
    public AdministradorDeRegistrosCausables() {
        initComponents();
        this.jFormattedTextField5.setValue(new Double(0));
        this.limpiar();
        lista=new Vector <RegistroDeCausacion>();
        this.BuscarUsuario();        
    }

    public AdministradorDeRegistrosCausables(Usuario usuario,Vector <RegistroDeCausacion>lista) {
        initComponents();
        this.jFormattedTextField5.setValue(new Double(0));
        this.limpiar();
        this.lista=lista;
        this.usuario=usuario;
        this.setTitle(usuario.NombreCompleto());        
        this.ActualizarModelo();
    }
    void limpiar(){        
        this.jFormattedTextField4.setValue(new Double(0));
        this.jFormattedTextField3.setValue(new Double(0));
        this.jFormattedTextField2.setValue(new Double(0));
        this.jFormattedTextField1.setValue(new Double(0));
        sw=iva;
        this.setCtaAuxiliar(null);
        sw=debito;
        this.setCtaAuxiliar(null);
        this.setRetenciondian(null);
    }
    void BuscarUsuario(){
        bu=new BuscarUsuario();
        bu.setSuscripcion(this);
        this.dispose();
        bu.show();
    }
    public void BuscarEnAuxiliar(){
        ba=new BuscarEnAuxiliar();
        ba.setSuscripcion(this);
        ba.setCtat(getUsuario());
        this.dispose();
    }
    public void BuscarEnTableDeRetencion(){
        bt=new BuscarEnTablaDeRTF();
        bt.setSuscripcion(this);
        bt.show();
        this.dispose();
    }
    public void GenerarRegistroDeCausacion(){        
        rc.setIdcuenta(this.jTextField1.getText());
        rc.setCantidad(((Double)this.jFormattedTextField1.getValue()).doubleValue());
        rc.setDebito(Math.round(((Double)this.jFormattedTextField2.getValue()).doubleValue()*rc.getCantidad()));
        rc.setItemtablartf(rd);
        rc.setAux_rtf(rc.getItemtablartf().getAuxiliar_retencion()+""+this.usuario.getId());
        rc.setP_iva(((Double)this.jFormattedTextField4.getValue()).doubleValue());
        rc.setAux_iva(""+this.aux_iva.getId());      
        rc.setNofactura(((Double)this.jFormattedTextField5.getValue()).intValue());
        rc.setP_rtf(((Double)this.jFormattedTextField3.getValue()).doubleValue());
    }
    public void AgregarRegistro(){
        if(this.usuario!=null && this.aux_debito!=null && this.rd!=null && this.aux_iva!=null){
        rc=new  RegistroDeCausacion();
        rc.setUsuario(usuario);
        this.GenerarRegistroDeCausacion();
        rc.setTipo((getLista().size()+1)*2);                        
        this.GuardarEnPUC();
        getLista().add(rc);
        this.ActualizarModelo();
        this.limpiar();
        rc=null;
        }else{
            JOptionPane.showMessageDialog(this,"Los Campos Cod. Aux , Tabla RTF , Aux Iva son Obligatorios");
        }
    }
    void GuardarEnPUC(){
        PucService ps=new PucService();        
        System.out.println(rc.getAux_rtf());
        Cta_PUC cta=ps.ObtenerCtaPuc(rc.getAux_rtf());        
        if(cta==null){
           Cta_PUC aux=ps.ObtenerCtaPuc(rc.getAux_rtf().substring(0,8)); 
           ps.IngresarEnElPUC(this.rc.getItemtablartf().getAuxiliar_retencion()+""+this.usuario.getId(),usuario.getDescripcion(),aux.getTipoCta(),aux.isRequiereNit(),usuario,aux.isPublico(),aux.getCategoria());        
        }
    }
    public void ActualizarModelo(){      
        modelo=new RegistroDeCausacionModel(this.getUsuario().isAutoretenedor_renta());
        modelo.setLista(getLista());       
        this.jTable1.setModel(modelo);
        this.jTable1.repaint();
    }
    public void ModificarRegistro(){
        this.GenerarRegistroDeCausacion();
        rc.setUsuario(usuario);
        this.GuardarEnPUC();
        getLista().set(fila,rc);
        this.ActualizarModelo();
        this.limpiar();
        rc=null;
        this.jButton5.setEnabled(false);
        this.jButton4.setEnabled(true);
    }
    void EventoSeleccionDeFila(){
        rc=getLista().get(fila);
        this.Dibujar();
        this.jButton5.setEnabled(true);
        this.jButton4.setEnabled(false);
    }
    void Dibujar(){
        sw=debito;
        this.setCtaAuxiliar(new PucService().ObtenerCtaPuc(rc.getIdcuenta()));
        this.jFormattedTextField1.setValue(new Double(rc.getCantidad()));
        this.jFormattedTextField2.setValue(new Double(rc.getDebito()/rc.getCantidad()));
        this.setRetenciondian(rc.getItemtablartf());
        this.jFormattedTextField3.setValue(new Double(rd.getPorcentage()));
        sw=iva;
        this.setCtaAuxiliar(new PucService().ObtenerCtaPuc(rc.getAux_iva()));
        this.jFormattedTextField4.setValue(new Double(rc.getP_iva()));
        this.jFormattedTextField4.setToolTipText(this.aux_iva.getDenominacion());
    }
    void Enviar(){
        if(suscripcion!=null)
        suscripcion.EventoEnvioDeRegistrosCausables();
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
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setText(" Administrador de  Registros Causables ");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de  Causacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setText("Cod. Aux :");

        jTextField1.setEditable(false);
        jTextField1.setEnabled(false);

        jLabel3.setText("Denominacion :");

        jTextField2.setEditable(false);

        jLabel4.setText("Cantidad :");

        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyPressed(evt);
            }
        });

        jLabel5.setText("Precio Unitrario :");

        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });

        jLabel6.setText("% RTF :");

        jFormattedTextField3.setEditable(false);
        jFormattedTextField3.setText("0");

        jButton1.setText("Tabla RTF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jLabel7.setText("% IVA :");

        jFormattedTextField4.setText("0");
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });

        jButton2.setText("Aux Iva");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFormattedTextField1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel7)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Factura :");

        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Agregar");
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

        jButton5.setText("Modificar");
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

        jButton6.setText("Enviar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButton4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5)
                            .addGap(23, 23, 23)))
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        sw=debito;
        this.BuscarEnAuxiliar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        sw=iva;
        this.BuscarEnAuxiliar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jFormattedTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField2.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField1KeyPressed

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jButton1.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField2KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.BuscarEnTableDeRetencion();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField5KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            sw=debito;
            this.BuscarEnAuxiliar();
        }
    }//GEN-LAST:event_jFormattedTextField5KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.BuscarEnTableDeRetencion();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jFormattedTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.sw=iva;
            this.BuscarEnAuxiliar();
        }
    }//GEN-LAST:event_jFormattedTextField4KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.AgregarRegistro();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=this.jTable1.getSelectedRow();
        if(fila!=-1){
           this.EventoSeleccionDeFila();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.ModificarRegistro();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.Enviar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER)
        this.AgregarRegistro();
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER)
        this.ActualizarModelo();
    }//GEN-LAST:event_jButton5KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AdministradorDeRegistrosCausables().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    /**
     * @param usuario the usuario to set
     */
    @Override
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.setTitle(usuario.NombreCompleto());
    }

    @Override
    public void EventoDeSeleccionAuxiliar() {
        ba.dispose();
        this.show();
        this.setCtaAuxiliar(ba.ObtenerAuxiliarSeleccionado());        
    }

    @Override
    public void setCtaAuxiliar(Cta_PUC cta) {
        if(sw==debito){
            this.aux_debito=cta;
            if(cta!=null){
                this.jTextField1.setText(""+cta.getId());
                this.jTextField2.setText(""+cta.getDenominacion());
                this.jFormattedTextField1.requestFocus();
            }else{
                this.jTextField1.setText("");
                this.jTextField2.setText("");
            }
        }
        if(sw==iva){
            this.aux_iva=cta;
            if(cta!=null){
                this.jFormattedTextField4.setToolTipText(""+cta.getId()+">>>"+cta.getDenominacion());
                if(this.jButton4.isEnabled())
                    this.jButton4.requestFocus();
                if(this.jButton5.isEnabled())
                    this.jButton5.requestFocus();
            }else{
                this.jFormattedTextField4.setToolTipText("");
            }
        }
    }

    @Override
    public void EventoSeleccionDeTablaRTF() {
        this.setRetenciondian(bt.getSeleccionado());        
        bt.dispose();
        this.show();
        this.jFormattedTextField4.requestFocus();
    }

    @Override
    public void setRetenciondian(RetencionDian rd) {
        this.rd=rd;
        if(rd!=null){
            this.jFormattedTextField3.setValue(new Double(rd.getPorcentage()));
            this.jFormattedTextField3.setToolTipText(""+rd.toString());
        }
    }

    /**
     * @return the lista
     */
    public Vector <RegistroDeCausacion> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(Vector <RegistroDeCausacion> lista) {
        this.lista = lista;
    }

    /**
     * @param suscripcion the suscripcion to set
     */
    public void setSuscripcion(ISuscripcionAdministradorDeRegistrosCausables suscripcion) {
        this.suscripcion = suscripcion;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public void EventoDeSeleccionUsuario() {
        this.setUsuario(bu.ObtenerUsuarioSeleccionado());
        this.ActualizarModelo();
        bu.dispose();
        this.show();
        
    }
}
