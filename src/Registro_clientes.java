

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedList;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

public class Registro_clientes extends javax.swing.JFrame {
    public static Connection con = null;
    public static ResultSet rs =null;
    public static Statement st =null;
    public static CallableStatement stm=null;
    

   public  void Conectar() {
        try{
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "System", "Hnevey70");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
    }
   
   
   //Cargar los clientes de la base de datos
   public ArrayList<Cliente> ListaCliente(){
       ArrayList<Cliente> Arraycliente =new ArrayList();
        try{
       Conectar();
       stm= con.prepareCall("{call PKG_CLIENTE.SP_LISTACLIENTES(?)}");
       stm.registerOutParameter(1, OracleTypes.CURSOR);
       stm.execute();
       
       rs=(ResultSet) stm.getObject(1);
       
       while(rs.next()){
           Cliente cliente =new Cliente();
           
           cliente.setCedula(rs.getString(1));
           cliente.setNombre(rs.getString(2));
           cliente.setApellidos(rs.getString(3));
           cliente.setCorreo(rs.getString(4));
           cliente.setTelefono(rs.getString(5));
           
           Arraycliente.add(cliente);
           
       }
        rs.close();
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
       return Arraycliente;
   }
   
   
   
   
   //Llenado de la tabla
   public void llenarTabla(){
       ArrayList<Cliente> Liscliente =ListaCliente();
       DefaultTableModel tb= (DefaultTableModel)jTable2.getModel();
       
       for(Cliente c1: Liscliente){
           tb.addRow(new Object[]{c1.getCedula(),c1.getNombre(), c1.getApellidos(),c1.getCorreo(),c1.getTelefono()});
       }  
   }
   
   
   //Registrar Nuevo cliente
   
   public void insertarCliente(Cliente cliente){
       
       try{
        Conectar();
        stm= con.prepareCall("{call PKG_CLIENTE.SP_REGISTROCLIENTE (?,?,?,?,?)}");
        
        stm.setString(1, cliente.getCedula());
        stm.setString(2, cliente.getNombre());
        stm.setString(3, cliente.getApellidos());
        stm.setString(4, cliente.getCorreo());
        stm.setString(5, cliente.getTelefono());
        
        stm.executeUpdate();
        
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
    }

   public void actualizarCliente(Cliente cliente){
       
       try{
        Conectar();
        stm= con.prepareCall("{call PKG_CLIENTE.SP_ACTUALIZARCLIENTE (?,?,?,?,?)}");
        
        stm.setString(1, cliente.getCedula());
        stm.setString(2, cliente.getNombre());
        stm.setString(3, cliente.getApellidos());
        stm.setString(4, cliente.getCorreo());
        stm.setString(5, cliente.getTelefono());
        
        stm.executeUpdate();
        
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
    }
   
   public void eliminarCliente(Cliente cliente){
       
       try{
        Conectar();
        stm= con.prepareCall("{call PKG_CLIENTE.SP_ELIMINARCLIENTE (?)}");
        
        stm.setString(1, cliente.getCedula());
        
        
        stm.executeUpdate();
        
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error con la base de datos!" + ex.getMessage());
        }
    }
   
   
   
   
   
   public void limpiar(){
       DefaultTableModel tb= (DefaultTableModel)jTable2.getModel();
       for(int i=tb.getRowCount()-1;i>=0;i--){
           tb.removeRow(i);
       }
       cedulatxt.setText("Cedula");
       nombretxt.setText("Nombre");
       apellidotxt.setText("Apellidos");
       correotxt.setText("Correo");
       telefonotxt.setText("Telefono");
       
   }

    public Registro_clientes() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Control Clientes");
        //this.jTable1.setModel(modelo);
        limpiar();
        llenarTabla();
      
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        cedulatxt = new javax.swing.JTextField();
        nombretxt = new javax.swing.JTextField();
        apellidotxt = new javax.swing.JTextField();
        telefonotxt = new javax.swing.JTextField();
        correotxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton9.setBackground(new java.awt.Color(153, 153, 255));
        jButton9.setForeground(new java.awt.Color(0, 0, 0));
        jButton9.setText("Guardar");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton9MouseExited(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 290, 110, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CEDULA", "NOMBRE", "APELLIDOS", "CORREO", "TELEFONO"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 552, 150));

        cedulatxt.setBackground(new java.awt.Color(102, 102, 102));
        cedulatxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cedulatxt.setForeground(new java.awt.Color(255, 255, 255));
        cedulatxt.setText("Cedula");
        cedulatxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cedulatxtMouseClicked(evt);
            }
        });
        jPanel1.add(cedulatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 67, -1));

        nombretxt.setBackground(new java.awt.Color(102, 102, 102));
        nombretxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nombretxt.setForeground(new java.awt.Color(255, 255, 255));
        nombretxt.setText("Nombre");
        nombretxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nombretxtMouseClicked(evt);
            }
        });
        nombretxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombretxtActionPerformed(evt);
            }
        });
        jPanel1.add(nombretxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 70, -1));

        apellidotxt.setBackground(new java.awt.Color(102, 102, 102));
        apellidotxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        apellidotxt.setForeground(new java.awt.Color(255, 255, 255));
        apellidotxt.setText("Apellido");
        apellidotxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apellidotxtMouseClicked(evt);
            }
        });
        jPanel1.add(apellidotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 103, -1));

        telefonotxt.setBackground(new java.awt.Color(102, 102, 102));
        telefonotxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        telefonotxt.setForeground(new java.awt.Color(255, 255, 255));
        telefonotxt.setText("Telefono");
        telefonotxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                telefonotxtMouseClicked(evt);
            }
        });
        telefonotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonotxtActionPerformed(evt);
            }
        });
        jPanel1.add(telefonotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 80, -1));

        correotxt.setBackground(new java.awt.Color(102, 102, 102));
        correotxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        correotxt.setForeground(new java.awt.Color(255, 255, 255));
        correotxt.setText("Correo");
        correotxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                correotxtMouseClicked(evt);
            }
        });
        jPanel1.add(correotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 129, -1));

        jSeparator1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 627, 10));

        jLabel6.setBackground(new java.awt.Color(51, 153, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Lista De Clientes");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 240, 30));

        jButton10.setBackground(new java.awt.Color(153, 153, 255));
        jButton10.setForeground(new java.awt.Color(0, 0, 0));
        jButton10.setText("Actualizar");
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton10MouseExited(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 110, -1));

        jLabel8.setBackground(new java.awt.Color(51, 153, 255));
        jLabel8.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Registrar - Eliminar - Actualizar Cliente");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 330, 30));

        jButton11.setBackground(new java.awt.Color(153, 153, 255));
        jButton11.setForeground(new java.awt.Color(0, 0, 0));
        jButton11.setText("Eliminar");
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton11MouseExited(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 610, 340));

        jToolBar1.setRollover(true);

        jButton5.setText("Inicio");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton1.setText("Clientes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Rutinas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setText("Entrenadores");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setText("Cajas");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 527, 25));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cedula");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 109, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    this.dispose();
    new Menu().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        Cliente cl =new Cliente();
        
        
        if( cedulatxt.getText().equals("") ||nombretxt.getText().equals("")||  apellidotxt.getText().equals("") ||
                correotxt.getText().equals("")||telefonotxt.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Accion Invalida, ingrese todos los datos");
        }else{
        cl.setCedula(cedulatxt.getText());
        cl.setNombre(nombretxt.getText());
        cl.setApellidos(apellidotxt.getText());
        cl.setCorreo(correotxt.getText());
        cl.setTelefono(telefonotxt.getText());
        insertarCliente(cl);
        
        }
        limpiar();
        llenarTabla();
        
        
        

        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseEntered
        // TODO add your handling code here:
        jButton9.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton9MouseEntered

    private void jButton9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseExited
        // TODO add your handling code here:
        jButton9.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton9MouseExited

    private void nombretxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombretxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombretxtActionPerformed

    private void cedulatxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cedulatxtMouseClicked
      
        cedulatxt.setText("");
// TODO add your handling code here:
    }//GEN-LAST:event_cedulatxtMouseClicked

    private void nombretxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombretxtMouseClicked
        // TODO add your handling code here:
        nombretxt.setText("");
    }//GEN-LAST:event_nombretxtMouseClicked

    private void apellidotxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_apellidotxtMouseClicked
        // TODO add your handling code here:
         apellidotxt.setText("");
    }//GEN-LAST:event_apellidotxtMouseClicked

    private void correotxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_correotxtMouseClicked
       correotxt.setText("");
       // TODO add your handling code here:
    }//GEN-LAST:event_correotxtMouseClicked

    private void telefonotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonotxtActionPerformed

    private void telefonotxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_telefonotxtMouseClicked
        // TODO add your handling code here:
        telefonotxt.setText("");
    }//GEN-LAST:event_telefonotxtMouseClicked

    private void jButton10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseEntered

    private void jButton10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseExited

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Cliente cl =new Cliente();
        
        
        if( cedulatxt.getText().equals("") ||nombretxt.getText().equals("")||  apellidotxt.getText().equals("") ||
                correotxt.getText().equals("")||telefonotxt.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Accion Invalida, ingrese todos los datos");
        }else{
        cl.setCedula(cedulatxt.getText());
        cl.setNombre(nombretxt.getText());
        cl.setApellidos(apellidotxt.getText());
        cl.setCorreo(correotxt.getText());
        cl.setTelefono(telefonotxt.getText());
        actualizarCliente(cl);
        JOptionPane.showMessageDialog(null,"Información actualizada con exito","",JOptionPane.INFORMATION_MESSAGE);
        limpiar();
        llenarTabla();
        }
        
        
        

        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseEntered

    private void jButton11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseExited

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Cliente cl =new Cliente();
        if( cedulatxt.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Accion Invalida, ingrese la cedula de un cliente");
        }else{
        cl.setCedula(cedulatxt.getText());
        cl.setNombre(nombretxt.getText());
        cl.setApellidos(apellidotxt.getText());
        cl.setCorreo(correotxt.getText());
        cl.setTelefono(telefonotxt.getText());
        eliminarCliente(cl);
        JOptionPane.showMessageDialog(null,"Registro eliminado","",JOptionPane.INFORMATION_MESSAGE);
        limpiar();
        llenarTabla();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed





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
            
        } catch (InstantiationException ex) {
            
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro_clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidotxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cedulatxt;
    private javax.swing.JTextField correotxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nombretxt;
    private javax.swing.JTextField telefonotxt;
    // End of variables declaration//GEN-END:variables
}
