/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Presentacion.Modulos.EstructuraDeCostos;

import java.util.Vector;
import javax.swing.JOptionPane;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;
import sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos;

/**
 *
 * @author Usuario1
 */
public class BuscarCentroDeCosto extends javax.swing.JFrame implements sic.Presentacion.Suscripciones.ISuscripcionBuscarEstructuraDeCostos {
     sic.Presentacion.ModelosDeTabla.CentroDeCosto_TableModel modelo;
     sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService service;
     private sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto seleccionado;
     private sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos estructura_de_costos;
     private sic.Presentacion.Suscripciones.ISuscripcionBuscarCentroDeCosto suscripcion;
     sic.Presentacion.Modulos.EstructuraDeCostos.BuscarEstructuraDeCostos bec;
    
    private int fila;
     
    /**
     * Creates new form BuscarCentroDeCosto
     */
    public BuscarCentroDeCosto() {
        initComponents();
        service=new sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostosService();                
        modelo=new sic.Presentacion.ModelosDeTabla.CentroDeCosto_TableModel();
        modelo.setLista(new Vector<CentroDeCosto>());
        this.jTable1.setModel(modelo);
        this.jTable1.repaint();
    }
    public void BuscarEstructuraDeCostos(){
        bec=new sic.Presentacion.Modulos.EstructuraDeCostos.BuscarEstructuraDeCostos();
        bec.setSuscripcion(this);
        bec.Cargar();
        this.dispose();
        bec.show();        
    }
    public void Cargar(){
        modelo=new sic.Presentacion.ModelosDeTabla.CentroDeCosto_TableModel();
        modelo.setLista(service.getDao().ObtenerCentrosDeCostos(this.estructura_de_costos.getId(),this.jTextField1.getText()));
        this.jTable1.setModel(modelo);
        this.jTable1.repaint();
    }
    public void Seleccionar(){
        fila=this.jTable1.getSelectedRow();
        if(fila!=-1){
            this.seleccionado=this.modelo.getRow(fila);
            if(this.suscripcion!=null){
                suscripcion.EventoDeSeleccionDeCentroDeCosto();
            }
        }else{
            JOptionPane.showMessageDialog(this,"Debes seleccionar una fila");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CENTRO DE COSTOS");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jButton1.setText("SELECCIONAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Buscar Estructura De Costos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        this.Cargar();
        if(evt.getKeyCode()==evt.VK_DOWN){
            this.jTable1.requestFocus();
            this.jTable1.changeSelection(0,0,false,false);
            this.fila=0;
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.Seleccionar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if(this.suscripcion!=null){
            suscripcion.show();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==evt.VK_ENTER){
            if(this.jTable1.getSelectedRow()!=-1){
                this.fila=this.jTable1.getSelectedRow();
                this.Seleccionar();
            }else{
                JOptionPane.showMessageDialog(this,"Debe Seleccionar una Fila");
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.BuscarEstructuraDeCostos();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarCentroDeCosto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarCentroDeCosto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarCentroDeCosto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarCentroDeCosto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarCentroDeCosto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    /**
     * @param estructura_de_costos the estructura_de_costos to set
     */
    public void setEstructura_de_costos(sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos estructura_de_costos) {
        this.estructura_de_costos = estructura_de_costos;
    }

    /**
     * @param suscripcion the suscripcion to set
     */
    public void setSuscripcion(sic.Presentacion.Suscripciones.ISuscripcionBuscarCentroDeCosto suscripcion) {
        this.suscripcion = suscripcion;
    }

    /**
     * @return the seleccionado
     */
    public sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto getSeleccionado() {
        return seleccionado;
    }

    @Override
    public void EventoDeSeleccionEstructuraDeCostos() {
        this.setEstructuraDeCostos(bec.getSeleccionado());
        bec.dispose();
        this.show();
        this.Cargar();
        
    }

    @Override
    public void setEstructuraDeCostos(EstructuraDeCostos obj) {
        this.estructura_de_costos=obj;
    }
}
