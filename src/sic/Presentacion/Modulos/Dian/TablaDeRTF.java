/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TablaDeRTF.java
 *
 * Created on 5/02/2013, 10:06:24 PM
 */
package sic.Presentacion.Modulos.Dian;

import sic.Presentacion.Modulos.PUC.BuscarEnPUC;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Servicios.Dian.DianService;
import sic.Dominio.Servicios.Dian.RetencionDian;
import sic.Presentacion.ModelosDeTabla.RetencionDianModel;
import sic.Presentacion.Suscripciones.ISuscripcionBuscarEnPUC;

/**
 *
 * @author FANNY BURGOS
 */
public class TablaDeRTF extends javax.swing.JFrame implements ISuscripcionBuscarEnPUC{
    Cta_PUC cta;
    BuscarEnPUC bp;
    DianService service;
    RetencionDianModel modelo;
    int fila=-1;    
    int seleccionado_retencion=1;
    final int retencion=1;
    final int anticipo_retencion=2;
    final int autoretencion=3;
    final int anticipo_autoretencion=4;
    /** Creates new form TablaDeRTF */
    public TablaDeRTF() {
        initComponents();
        Date d=Calendar.getInstance().getTime();
        this.jFormattedTextField1.setValue(new Integer(d.getYear()+1900));
        this.jFormattedTextField2.setValue(new Double(0));
        this.jFormattedTextField3.setValue(new Double(0));
        this.jTextField1.requestFocus();
        service=new DianService();
        this.Cargar();
    }
    void Limpiar(){
        this.jFormattedTextField2.setValue(new Double(0));
        this.jFormattedTextField3.setValue(new Double(0));
        this.jTextField1.setText("");
        this.seleccionado_retencion=1;
        this.setCta_PUC(null);
        this.seleccionado_retencion=2;
        this.setCta_PUC(null);
        this.seleccionado_retencion=3;
        this.setCta_PUC(null);
        this.seleccionado_retencion=4;
        this.setCta_PUC(null);
    }
    void BuscarEnPUC(){
        bp=new BuscarEnPUC();
        bp.show();
        bp.setSuscripcion(this);
        this.dispose();
    }
    void Cargar(){
        modelo=new RetencionDianModel();
        int año=((Integer)this.jFormattedTextField1.getValue()).intValue();
        modelo.setLista(service.getDao().ObtenerTablaDeRetenciones(año));
        this.jTable1.setModel(modelo);
        this.jTable1.repaint();
    }
    void Ingresar(){
        int año=((Integer)this.jFormattedTextField1.getValue()).intValue();
        String descripcion=this.jTextField1.getText();
        double base=((Double)this.jFormattedTextField2.getValue()).doubleValue();
        double rtf=((Double)this.jFormattedTextField3.getValue()).doubleValue();
        String idaux=this.jTextField2.getText();
        RetencionDian rd=new RetencionDian();
        rd.setAño(año);
        rd.setDescripcion(descripcion);
        rd.setBase(base);
        rd.setAuxiliar_retencion(idaux);
        rd.setAuxiliar_anticipo_retencion(this.jTextField3.getText());
        rd.setPorcentage(rtf);
        rd.setAuxiliar_autoretencion(this.jTextField4.getText());
        rd.setAuxiliar_anticipo_autoretencion(this.jTextField5.getText());
        service.getDao().AgregarRegistroEnTablaDeRetenciones(rd);        
        this.Limpiar();
        this.Cargar();
    }
    void Modificar(){
        int año=((Integer)this.jFormattedTextField1.getValue()).intValue();
        String descripcion=this.jTextField1.getText();
        double base=((Double)this.jFormattedTextField2.getValue()).doubleValue();
        double rtf=((Double)this.jFormattedTextField3.getValue()).doubleValue();
        String idaux=this.jTextField2.getText();
        RetencionDian rd=new RetencionDian();
        rd.setAño(año);
        rd.setDescripcion(descripcion);
        rd.setBase(base);
        rd.setAuxiliar_retencion(idaux);
        rd.setPorcentage(rtf);
        rd.setAuxiliar_anticipo_retencion(this.jTextField3.getText());
        rd.setAuxiliar_autoretencion(this.jTextField4.getText());
        rd.setAuxiliar_anticipo_autoretencion(this.jTextField5.getText());
        rd.setId(modelo.getLista().get(this.fila).getId());
        service.getDao().ModificarRegistroEnTablaDeRetenciones(rd.getId(),rd);
        this.jButton2.setEnabled(true);
        this.jButton3.setEnabled(false);
        this.Limpiar();
        this.Cargar();
        
    }
    void EventoSeleccionDeFila(){
        this.jButton2.setEnabled(false);
        this.jButton3.setEnabled(true);
        RetencionDian rd=this.modelo.getRow(fila);
        this.jTextField1.setText(rd.getDescripcion());
        this.jFormattedTextField2.setValue(new Double(rd.getBase()));
        this.jFormattedTextField3.setValue(new Double(rd.getPorcentage()));
        this.jTextField2.setText(""+rd.getAuxiliar_retencion());
        this.jTextField3.setText(""+rd.getAuxiliar_anticipo_retencion());
        this.jTextField4.setText(""+rd.getAuxiliar_autoretencion());
        this.jTextField5.setText(""+rd.getAuxiliar_anticipo_autoretencion());
        PucService ps=new PucService();
        this.jTextField2.setToolTipText(ps.ObtenerCtaPuc(rd.getAuxiliar_retencion()).getDenominacion());        
        this.jTextField3.setToolTipText(ps.ObtenerCtaPuc(rd.getAuxiliar_anticipo_retencion()).getDenominacion());                
        this.jTextField4.setToolTipText(ps.ObtenerCtaPuc(rd.getAuxiliar_autoretencion()).getDenominacion());        
        this.jTextField5.setToolTipText(ps.ObtenerCtaPuc(rd.getAuxiliar_anticipo_autoretencion()).getDenominacion());                
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
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tabla de Retenciones");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Año :");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Retencion"));

        jLabel3.setText("Descripcion :");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel4.setText("Base :");

        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });

        jLabel5.setText("% RTF :");

        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyPressed(evt);
            }
        });

        jLabel6.setText("Aux Retencion  :");

        jTextField2.setEditable(false);

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ingresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Aux Anticipo  Retencion  :");

        jTextField3.setEditable(false);

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Aux. Autoretencion  :");

        jTextField4.setEditable(false);

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel9.setText("Aux Anticipo Autoretencion  :");

        jTextField5.setEditable(false);

        jButton6.setText("...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5))))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addComponent(jButton4)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.seleccionado_retencion=this.retencion;
        this.BuscarEnPUC();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.Ingresar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        this.fila=this.jTable1.getSelectedRow();
        if(fila!=-1){
            this.EventoSeleccionDeFila();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.Modificar();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField2.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.jFormattedTextField3.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField2KeyPressed

    private void jFormattedTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            this.BuscarEnPUC();
        }
    }//GEN-LAST:event_jFormattedTextField3KeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            if(this.jTable1.getSelectedRow()!=-1){
                this.fila=this.jTable1.getSelectedRow();
                this.EventoSeleccionDeFila();
            }else{
                JOptionPane.showMessageDialog(this,"Debe Seleccionar una Fila");
            }
        }
        
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.seleccionado_retencion=this.anticipo_retencion;
        this.BuscarEnPUC();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.seleccionado_retencion=this.autoretencion;
        this.BuscarEnPUC();        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.seleccionado_retencion=this.anticipo_autoretencion;
        this.BuscarEnPUC();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TablaDeRTF().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    @Override
    public void EventoDeSeleccionCtaEnPUC() {
        if(bp.ObtenerCtaSeleccionada().getId().toString().length()==8){
            this.setCta_PUC(bp.ObtenerCtaSeleccionada());
            bp.dispose();
            this.show();
        }else{
           JOptionPane.showMessageDialog(this.bp,"Debe Ser Un Auxiliar (8 Digitos)");             
        }
    }

    @Override
    public void setCta_PUC(Cta_PUC cta) {
        this.cta=cta;
        if(cta!=null){
            PucService ps=new PucService();
            if(this.seleccionado_retencion==this.retencion){
               this.jTextField2.setText(""+cta.getId());            
               this.jTextField2.setToolTipText(ps.ObtenerCtaPuc(cta.getId()).getDenominacion());
            }
            if(this.seleccionado_retencion==this.anticipo_retencion){
               this.jTextField3.setText(""+cta.getId());            
               this.jTextField3.setToolTipText(ps.ObtenerCtaPuc(cta.getId()).getDenominacion()); 
            }
            if(this.seleccionado_retencion==this.autoretencion){
               this.jTextField4.setText(""+cta.getId());            
               this.jTextField4.setToolTipText(ps.ObtenerCtaPuc(cta.getId()).getDenominacion()); 
            }
            if(this.seleccionado_retencion==this.anticipo_autoretencion){
               this.jTextField5.setText(""+cta.getId());            
               this.jTextField5.setToolTipText(ps.ObtenerCtaPuc(cta.getId()).getDenominacion()); 
            }
        }else{
            if(this.seleccionado_retencion==this.retencion){
               this.jTextField2.setText("");
               this.jTextField2.setToolTipText("");
            }
            if(this.seleccionado_retencion==this.anticipo_retencion){
               this.jTextField3.setText("");
               this.jTextField3.setToolTipText(""); 
            }
            if(this.seleccionado_retencion==this.autoretencion){
               this.jTextField4.setText("");            
               this.jTextField4.setToolTipText(""); 
            }
            if(this.seleccionado_retencion==this.anticipo_autoretencion){
               this.jTextField5.setText("");            
               this.jTextField5.setToolTipText(""); 
            }
        }
    }
}
