
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

public class RegistroEntrenadores extends javax.swing.JFrame {

    public static Connection con = null;
    public static ResultSet rs = null;
    public static Statement st = null;
    public static CallableStatement stm = null;

    public void Conectar() {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "System", "Hnevey70");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
    }

    //Cargar los entrenadores de la base de datos
    public ArrayList<Entrenador> ListaEntrenador() {
        ArrayList<Entrenador> Arrayentrenador = new ArrayList();
        try {
            Conectar();
            stm = con.prepareCall("{call PK_GENERAL.SP_LISTAENTRENADORES(?)}");
            stm.registerOutParameter(1, OracleTypes.CURSOR);
            stm.execute();

            rs = (ResultSet) stm.getObject(1);

            while (rs.next()) {
                Entrenador entrenador = new Entrenador();

                entrenador.setIdEntrenador(rs.getInt(1));
                entrenador.setNombre(rs.getString(2));
                entrenador.setApellidos(rs.getString(3));
                entrenador.setCorreo(rs.getString(4));
                entrenador.setPuesto(rs.getString(5));

                Arrayentrenador.add(entrenador);

            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datosx!" + ex.getMessage());
        }
        return Arrayentrenador;
    }

    public void llenarCombo() {
        try {
            Conectar();
            Statement st;
            
            //Llena las cedulas
            stm = con.prepareCall("{call PK_GENERAL.SP_LISTAPUESTOSCOMBO(?)}");
            stm.registerOutParameter(1, OracleTypes.CURSOR);
            stm.execute();

            rs = (ResultSet) stm.getObject(1);

            while (rs.next()) {
                comboPuesto.addItem(rs.getString(1));

            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

        }

    }

    //Llenado de la tabla
    public void llenarTabla() {
        ArrayList<Entrenador> Lisentre = ListaEntrenador();
        DefaultTableModel tb = (DefaultTableModel) jTable2.getModel();

        for (Entrenador c1 : Lisentre) {
            tb.addRow(new Object[]{c1.getIdEntrenador(), c1.getNombre(), c1.getApellidos(), c1.getCorreo(), c1.getPuesto()});
        }
    }

    //Registrar Nuevo ENTRENADOR
    public void insertarEntrenador(){
       
       try{
        Conectar();
        stm= con.prepareCall("{call PK_GENERAL.SP_REGISTROENTRENADOR (?,?,?,?)}");
        
        stm.setString(1,nombretxt.getText());
        stm.setString(2, apellidotxt.getText());
        stm.setString(3, correotxt.getText());
        stm.setString(4, comboPuesto.getSelectedItem().toString());
        
        
        stm.executeUpdate();
        
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
    }
    
    
    
    public void actualizarEntrenador(){
        
        int fila=jTable2.getSelectedRow();
          int valorId= Integer.parseInt(jTable2.getValueAt(fila,0).toString());
         
          
         if( JOptionPane.showConfirmDialog(null, "Desea actualizar la información #"+valorId, "Actualizar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
              try{ 
                Conectar();
                    stm= con.prepareCall("{call PK_GENERAL.SP_ACTUALIZARENTRENADOR(?,?,?,?,?)}");
                    stm.setInt(1,valorId);
                    stm.setString(2,nombretxt.getText());
                    stm.setString(3, apellidotxt.getText());
                    stm.setString(4,correotxt.getText());
                    stm.setString(5, comboPuesto.getSelectedItem().toString());
                    
                    stm.execute();
                    JOptionPane.showMessageDialog(null,"ACtualización con Exito","Eliminado",JOptionPane.INFORMATION_MESSAGE);


                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

                }
         }   
    }
    
    
    

 
 public void eliminarEntrenador(){
     int fila=jTable2.getSelectedRow();
          int valorId= Integer.parseInt(jTable2.getValueAt(fila,0).toString());
          if( JOptionPane.showConfirmDialog(null, "Desea eliminar el entrenador id: "+valorId, "Eliminar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
       
       try{
        Conectar();
        stm= con.prepareCall("{call PK_GENERAL.SP_ELIMINARENTRENADOR (?)}");
        
        stm.setInt(1, valorId);
        
        
        stm.executeUpdate();
        
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error con la base de datos!" + ex.getMessage());
        }
          }
    }
 
    public void limpiar() {
        DefaultTableModel tb = (DefaultTableModel) jTable2.getModel();
        for (int i = tb.getRowCount() - 1; i >= 0; i--) {
            tb.removeRow(i);
        }
        
        nombretxt.setText("Nombre");
        apellidotxt.setText("Apellidos");
        correotxt.setText("Correo");
        comboPuesto.setSelectedIndex(0);
        

    }

    public RegistroEntrenadores() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Control Clientes");
        //this.jTable1.setModel(modelo);
        llenarCombo();
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
        nombretxt = new javax.swing.JTextField();
        apellidotxt = new javax.swing.JTextField();
        correotxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        comboPuesto = new javax.swing.JComboBox<>();
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
                "ID", "NOMBRE", "APELLIDOS", "CORREO", "PUESTO"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 552, 150));

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
        jPanel1.add(nombretxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 70, -1));

        apellidotxt.setBackground(new java.awt.Color(102, 102, 102));
        apellidotxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        apellidotxt.setForeground(new java.awt.Color(255, 255, 255));
        apellidotxt.setText("Apellido");
        apellidotxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apellidotxtMouseClicked(evt);
            }
        });
        jPanel1.add(apellidotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 103, -1));

        correotxt.setBackground(new java.awt.Color(102, 102, 102));
        correotxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        correotxt.setForeground(new java.awt.Color(255, 255, 255));
        correotxt.setText("Correo");
        correotxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                correotxtMouseClicked(evt);
            }
        });
        correotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correotxtActionPerformed(evt);
            }
        });
        jPanel1.add(correotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, 160, -1));

        jSeparator1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 627, 10));

        jLabel6.setBackground(new java.awt.Color(51, 153, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Entrenadores");
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
        jLabel8.setText("Registrar - Eliminar - Actualizar Entrenador");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 350, 30));

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

        comboPuesto.setBackground(new java.awt.Color(102, 102, 102));
        comboPuesto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboPuesto.setForeground(new java.awt.Color(255, 255, 255));
        comboPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Puesto" }));
        jPanel1.add(comboPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 130, 30));

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

     

        if ( nombretxt.getText().equals("") || apellidotxt.getText().equals("")
                || correotxt.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Accion Invalida, ingrese todos los datos");
        } else {
           insertarEntrenador();
            
            

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

    private void jButton10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseEntered

    private void jButton10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseExited

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Cliente cl = new Cliente();

        if (nombretxt.getText().equals("") || apellidotxt.getText().equals("")
                || correotxt.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Accion Invalida, ingrese todos los datos");
        } else {
            
            actualizarEntrenador();
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
       eliminarEntrenador();
            limpiar();
            llenarTabla();
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void correotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correotxtActionPerformed

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
    private javax.swing.JComboBox<String> comboPuesto;
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
    // End of variables declaration//GEN-END:variables
}
