


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedList;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;
import javax.swing.table.DefaultTableCellRenderer;
public class Reservaciones extends javax.swing.JFrame {

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
    
    
      //Llamado del metodo para listar
      public ArrayList<Reservacion> ListaReservaciones(){
       ArrayList<Reservacion> ArrayReservacion =new ArrayList();
        try{
       Conectar();
       stm= con.prepareCall("{call PKG_RESERVACIONES.SP_LISTARESERVACIONES(?)}");
       stm.registerOutParameter(1, OracleTypes.CURSOR);
       stm.execute();
       
       rs=(ResultSet) stm.getObject(1);
       
       while(rs.next()){
           Reservacion reservacion =new Reservacion();
           reservacion.setIdReservacion(rs.getInt(1));
           reservacion.setNombreCliente(rs.getString(2));
           reservacion.setSucursal(rs.getString(3));
           reservacion.setDescripcion(rs.getString(4));
           reservacion.setMaquina(rs.getString(5));
           reservacion.setCoach(rs.getString(6));
           reservacion.setPuesto(rs.getString(7));
           reservacion.setFecha(rs.getString(8));
                   
           ArrayReservacion.add(reservacion);
           
       }
        rs.close();
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        }
       return ArrayReservacion;
   }
      
      
      //Llenado de la tabla con los datos
      
      public void llenarTabla(){
       ArrayList<Reservacion> LisRes =ListaReservaciones();
       DefaultTableModel tb= (DefaultTableModel)jTable1.getModel();
       jTable1.setDefaultRenderer(Object.class, new render() {});
       
       for(Reservacion c1: LisRes){
           tb.addRow(new Object[]{c1.getIdReservacion(),c1.getNombreCliente(),
           c1.getSucursal(),c1.getDescripcion(),c1.getMaquina(),c1.getCoach(),
           c1.getPuesto(),c1.getFecha()});
       }  
   }
      
      public void consultarMontoFecha(){
        try{ 
            Conectar();
            String fecha= fechatx.getText().toString();
       stm= con.prepareCall("{? = call PKG_RESERVACIONES.FN_TOTALMONTO(?)}");
       stm.registerOutParameter(1, OracleTypes.VARCHAR);
       stm.setString(2,fecha);
       stm.execute();
       
       double monto=stm.getDouble(1);
       
       if(monto!=0){
       JOptionPane.showMessageDialog(null,"El total facturado el día"+ fechatx.getText().toString()+" es de ₡"+monto);
       }else{
           JOptionPane.showMessageDialog(null,"No se encontraron registros para esta fecha","Error",JOptionPane.ERROR_MESSAGE);
           
       }
        rs.close();
        con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
        
      }
      }
      
      
      
      public void consultarPorCedula(){
           ArrayList<Reservacion> ArrayReservacion =new ArrayList();
           String pcedula= cedulaCtx.getText();
        try{
       Conectar();
       stm= con.prepareCall("{call PKG_RESERVACIONES.SP_RESERVACIONES_CEDULA(?,?)}");
       stm.registerOutParameter(1, OracleTypes.CURSOR);
       stm.setString(2, pcedula);
       stm.execute();
       
       rs=(ResultSet) stm.getObject(1);
       
       while(rs.next()){
           Reservacion reservacion =new Reservacion();
           
           reservacion.setNombreCliente(rs.getString(1));
           reservacion.setSucursal(rs.getString(2));
           reservacion.setCoach(rs.getString(3));
           reservacion.setFecha(rs.getString(4));
           reservacion.setMonto(rs.getInt(5));
                   
           ArrayReservacion.add(reservacion);
           
       }
            rs.close();
            con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos!" + ex.getMessage());
            }
          
        String s="";
        for(int i=0;i< ArrayReservacion.size();i++){
            s+="Nombre:"+ArrayReservacion.get(i).getNombreCliente()+" "+ArrayReservacion.get(i).getSucursal()
                    +" "+ArrayReservacion.get(i).getCoach()+ " "+ArrayReservacion.get(i).getFecha()+" "
                    +ArrayReservacion.get(i).getMonto()+"\n";
        }
        JOptionPane.showMessageDialog(null,"Reservaciones\n"+s ," ",JOptionPane.INFORMATION_MESSAGE);
          
      }
      
      
      public void eliminarReservacion(){
          int fila=jTable1.getSelectedRow();
          int valorId= Integer.parseInt(jTable1.getValueAt(fila,0).toString());
         
          
         if( JOptionPane.showConfirmDialog(null, "Desea eliminar la reservacion #"+valorId, "Eliminar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
              try{ 
                Conectar();
                    stm= con.prepareCall("{call PKG_RESERVACIONES.SP_ELIMINARRESERVACION(?)}");
                    stm.setInt(1,valorId);
                    stm.execute();
                    JOptionPane.showMessageDialog(null,"Reservación eliminada con Exito","Eliminado",JOptionPane.INFORMATION_MESSAGE);


                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

                }
         }   
      }
      
      
      public void cargarComboNombre(){
          try{
              Conectar();
          String ced= comboCedula.getSelectedItem().toString();
                  stm=con.prepareCall("{call PKG_RESERVACIONES.SP_CLIENTE_NOMBRE_COMBO(?,?)}");
                  stm.registerOutParameter(1, OracleTypes.CURSOR);
                  stm.setString(2, ced);
                  stm.execute();
                  
                  rs=(ResultSet) stm.getObject(1);
                  while(rs.next()){
                      
                  jTextField3.setText(rs.getString(1));
                  }
                  rs.close();
                  con.close();
          }catch(SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

                }
              
          
          
      }
      
      public void rellenarCombos(){
           try{ 
                Conectar();
                   Statement st;
                   //Llena las cedulas
                   stm= con.prepareCall("{call PKG_RESERVACIONES.SP_CEDULAS_COMBO(?)}");
                   stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.execute();
       
                    rs=(ResultSet) stm.getObject(1);
                   
                   while(rs.next()){
                     comboCedula.addItem(rs.getString(1));
                       
                   }
                   //Llena los nombres
                  
                  
                  //Llena las sucursales
                  stm=con.prepareCall("{call PKG_RESERVACIONES.SP_SUCURSAL_COMBO(?)}");
                  stm.registerOutParameter(1, OracleTypes.CURSOR);
                  
                  stm.execute();
                  
                  rs=(ResultSet) stm.getObject(1);
                  while(rs.next()){
                      
                  combosucursal.addItem(rs.getString(1));
                  }
                  
                  //llena las rutinas
                  stm=con.prepareCall("{call PKG_RESERVACIONES.SP_LISTARUTINASCOMBO(?)}");
                  stm.registerOutParameter(1, OracleTypes.CURSOR);
                  
                  stm.execute();
                  
                  rs=(ResultSet) stm.getObject(1);
                  while(rs.next()){
                      
                  comboRutina.addItem(rs.getString(1).trim());
                  }
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

                }
          
          
      }
      
      public void registrarReservacion(){
           try{ 
                Conectar();
                    stm= con.prepareCall("{call PKG_RESERVACIONES.SP_REGISTRORESERVACION(?,?,?,?,?)}");
                    String ced= comboCedula.getSelectedItem().toString();
                    String suscu=combosucursal.getSelectedItem().toString();
                    String ruti= comboRutina.getSelectedItem().toString().trim();
                    String fecha=fechatx1.getText();
                    int mont=Integer.parseInt(jTextField4.getText());
                    
                    stm.setString(1,ced);
                    stm.setString(2, suscu);
                    stm.setString(3,ruti);
                    stm.setString(4, fecha);
                    stm.setInt(5,mont);
                    
                    stm.execute();
                    JOptionPane.showMessageDialog(null,"Reservación Registrada con Exito","Registrado",JOptionPane.INFORMATION_MESSAGE);


                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la ejecución!" + ex.getMessage());

                }
          
          
      }
      
      
      
      
      
      
      
      
      
      public void limpiar(){
          fechatx.setValue(null);
          DefaultTableModel tb= (DefaultTableModel)jTable1.getModel();
       for(int i=tb.getRowCount()-1;i>=0;i--){
           tb.removeRow(i);
       }
      }
      
      
      
      
    public Reservaciones() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Ventana de reservaciones");
        llenarTabla();
        rellenarCombos();
        
    }
    // Metodo para agregar distintas maquinas a la reservación y verifica que las maquinas esten disponibles
   
      
    public void limpiarControles() {
        
        jTextField2.setText("");
        cedulaCtx.setText("");
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        cedulaCtx = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fechatx = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        eliminarbtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        combosucursal = new javax.swing.JComboBox<>();
        comboRutina = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        comboCedula = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        eliminarbtn1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        fechatx1 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setRollover(true);

        jButton5.setText("Inicio");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton1.setText("Registro de Usuarios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Catalogos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setText("Reservaciones");
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

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 629, 25));

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jButton12.setBackground(new java.awt.Color(153, 153, 255));
        jButton12.setText("Consultar todas las reservaciones");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton12MouseExited(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reservacion-en-linea.png"))); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setPreferredSize(new java.awt.Dimension(64, 64));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 153, 255));
        jButton7.setText("Agregar");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton7MouseExited(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(153, 153, 255));
        jButton10.setText("Modificar");
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jButton11.setBackground(new java.awt.Color(153, 153, 255));
        jButton11.setText("Cancelar");
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jButton9.setBackground(new java.awt.Color(153, 153, 255));
        jButton9.setText("Consultar");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        cedulaCtx.setText("Cedula");
        cedulaCtx.setToolTipText("");
        cedulaCtx.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cedulaCtx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cedulaCtxActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fecha");

        jButton6.setText("Consultar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Maquinaria a utilizar");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Hora");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Lista De Reservaciones");

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reservación", "Cliente", "Sucursal", "Rutina", "Maquina", "Entrenador", "Puesto", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Opciones");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Monto Facturado por fecha");

        try {
            fechatx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        fechatx.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fechatx.setText(" DD/0MM /  YY");
        fechatx.setToolTipText("DD/MM/YY");
        fechatx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fechatxMouseClicked(evt);
            }
        });
        fechatx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechatxActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Consultar Reservación");

        jButton13.setText("Consultar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        eliminarbtn.setText("Eliminar");
        eliminarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarbtnActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Agregar");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cliente");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sucursal");

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Monto");

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Rutina");

        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Cedula");

        comboCedula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboCedulaMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboCedulaMouseReleased(evt);
            }
        });
        comboCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCedulaActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setText(" ");
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextField3MouseEntered(evt);
            }
        });

        jTextField4.setText("Monto");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        eliminarbtn1.setText("Registrar ");
        eliminarbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarbtn1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fecha");

        try {
            fechatx1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        fechatx1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fechatx1.setText(" DD/0MM /  YY");
        fechatx1.setToolTipText("DD/MM/YY");
        fechatx1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fechatx1MouseClicked(evt);
            }
        });
        fechatx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechatx1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fechatx)
                            .addComponent(cedulaCtx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addComponent(jButton13)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel13)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel10)
                        .addGap(159, 159, 159)
                        .addComponent(jLabel12)
                        .addGap(189, 189, 189)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eliminarbtn))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton12)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addGap(597, 597, 597)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11)))
                        .addGap(12, 12, 12))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel6))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboRutina, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(fechatx1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1287, 1287, 1287))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(eliminarbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(eliminarbtn)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jButton12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboRutina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechatx1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eliminarbtn1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9)
                                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel5)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(fechatx, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cedulaCtx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton13))))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                    .addGap(9, 9, 9))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton10))
                                    .addGap(36, 36, 36))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap()))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106))))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 860, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        new Registro_clientes().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Reservaciones r = new Reservaciones();
        r.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    this.dispose();
    new Menu().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        consultarMontoFecha();
        limpiar();
        llenarTabla();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseExited
        // TODO add your handling code here:
        jButton9.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton9MouseExited

    private void jButton9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseEntered
        // TODO add your handling code here:
        jButton9.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton9MouseEntered

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseExited
        // TODO add your handling code here:
        jButton11.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton11MouseExited

    private void jButton11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseEntered
        // TODO add your handling code here:
        jButton11.setBackground(Color.LIGHT_GRAY);

    }//GEN-LAST:event_jButton11MouseEntered

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseExited
        // TODO add your handling code here:
        jButton10.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton10MouseExited

    private void jButton10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseEntered
        // TODO add your handling code here:
        jButton10.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton10MouseEntered

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseExited
        // TODO add your handling code here:
        
        jButton7.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton7MouseExited

    private void jButton7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseEntered
        // TODO add your handling code here:
        
        jButton7.setBackground(Color.LIGHT_GRAY);
        //jButton6.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton7MouseEntered

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void fechatxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechatxActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_fechatxActionPerformed

    private void fechatxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechatxMouseClicked
        // TODO add your handling code here:
        fechatx.setText("");
    }//GEN-LAST:event_fechatxMouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        consultarPorCedula();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void cedulaCtxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulaCtxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulaCtxActionPerformed

    private void eliminarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarbtnActionPerformed
        // TODO add your handling code here:
        eliminarReservacion();
        limpiar();
        llenarTabla();
    }//GEN-LAST:event_eliminarbtnActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void eliminarbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarbtn1ActionPerformed
        // TODO add your handling code here:
        registrarReservacion();
        limpiar();
        ListaReservaciones();
        llenarTabla();
        
        
        
        
        
    }//GEN-LAST:event_eliminarbtn1ActionPerformed

    private void fechatx1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechatx1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fechatx1MouseClicked

    private void fechatx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechatx1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechatx1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseExited
        // TODO add your handling code here:
        jButton12.setBackground(new java.awt.Color(153, 153, 255));
    }//GEN-LAST:event_jButton12MouseExited

    private void jButton12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseEntered
        // TODO add your handling code here
        jButton12.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton12MouseEntered

    private void comboCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCedulaActionPerformed

    private void comboCedulaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCedulaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCedulaMouseReleased

    private void comboCedulaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCedulaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_comboCedulaMouseClicked

    private void jTextField3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseEntered
        // TODO add your handling code here:
        cargarComboNombre();
    }//GEN-LAST:event_jTextField3MouseEntered

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

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
            java.util.logging.Logger.getLogger(Reservaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cedulaCtx;
    private javax.swing.JComboBox<String> comboCedula;
    private javax.swing.JComboBox<String> comboRutina;
    private javax.swing.JComboBox<String> combosucursal;
    private javax.swing.JButton eliminarbtn;
    private javax.swing.JButton eliminarbtn1;
    private javax.swing.JFormattedTextField fechatx;
    private javax.swing.JFormattedTextField fechatx1;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
