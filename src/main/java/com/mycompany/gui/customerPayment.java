/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.gui;

import com.mycompany.util.GetIdSingle;
import com.mycompany.util.InsertTable;
import com.mycompany.util.LoadSubTypes;
import com.mycompany.util.LoadTables;
import com.mycompany.util.SearchTable;
import com.mycompany.frameutil.ImageSizer;
import com.mycompany.frameutil.MainTheme;
import com.mycompany.frameutil.OptionMessageLegit;
import com.mycompany.frameutil.RoundedPanel;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.mycompany.model.MySql;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Acer
 */
 abstract class CustomerPayment extends javax.swing.JDialog {

    /**
     * Creates new form NewJDialog
     */
    public CustomerPayment(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        jframeCustmize();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 7, 7));

        //	jLabel2.setText(message);
        //jLabel2.setForeground(Color.WHITE);
        //jLabel1.setText(title);
        roundedPanel1.setBackground(MainTheme.thirdColor);
        roundedPanel2.setBackground(MainTheme.secondColor);
        jLabel1.setForeground(Color.WHITE);
        jPanel2.setBackground(MainTheme.secondColor);
      
        textF1.setEditable(false);
        loadCombos();
        this.loadQuery();
        loadTableQuery += " INNER JOIN `mainmenu` ON `mainmenu`.`menuItemId`=`customer_ordered_item`.`menuItemId` ";
        System.out.println(loadTableQuery);
        loadTable();
        textF1.setText(getSubTotal());
                menuBar1.foo(this);


    }

    public CustomerPayment(java.awt.Frame parent, String orderId) {
        this(parent, true);
        if (parent instanceof OnGoingOrder) {
            this.ogorder = (OnGoingOrder) parent;
        }
        loadTableQuery += " WHERE `customer_order`.`customer_order_id`='" + orderId + "' ";
        System.out.println(loadTableQuery);
        loadTable();
        textF1.setText(getSubTotal());
        this.orderId = orderId;
        this.isOtherFramesInvolved = true;

    }

    public CustomerPayment(java.awt.Frame parent, boolean b, String status) {
        this(parent, true);

    }

    public CustomerPayment(java.awt.Frame parent, DefaultTableModel dftm) {
        this(parent, true);
        this.customTable1.setModel(dftm);

    }
    OnGoingOrder ogorder;
    boolean isOtherFramesInvolved;
    String orderId;
    String loadTableQuery;
    String[] colnames = {"menuItemId", "menuItemName", "qty", "menuItemPrice", "total"};

    private void loadQuery() {
        ArrayList<String> al = new ArrayList<String>();

        al.add("customer_ordered_item");
        al.add("customer_order,customer_ordered_item");
        al.add("customer_table,customer_order");
        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();

    }

    private void loadTable() {

        String sort = "ORDER BY `menuItemName` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable1, query, this.colnames, "blah");
    }

    private void loadTable(String loadTableQuery) {
        loadQuery();
        String sort = "ORDER BY `menuItemName` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable1, query, this.colnames, "blah");
        System.out.println(loadTableQuery);
    }

    private String getSubTotal() {
        double total = 0;
        if (customTable1.getRowCount() != 0) {
            for (int i = 0; i < customTable1.getRowCount(); i++) {
                total += Double.parseDouble(customTable1.getValueAt(i, 4).toString());
            }
        }
        return Double.toString(total);
    }

    private void showOrderedItems(String orderId) {

    }

    private void loadCombos() {
        LoadSubTypes.loadType(comboBox1, "payment_method");
    }

    private void printGRN(JTable jt, Vector<String> v) {

        try {
            TableModel tm1 = jt.getModel();
            //String jasperPath = "src//reportXML//customerOrder.jrxml";
            InputStream jasperPathStream = getClass().getResourceAsStream("/reportxml/orderpayment.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(jasperPathStream);
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("UniqueId", v.get(0).toString());
            hm.put("Table", v.get(1).toString());

            hm.put("PaymentId", v.get(2).toString());
            hm.put("Time", v.get(3).toString());
            hm.put("PaymentVia", v.get(4).toString());
            hm.put("Total", v.get(5).toString());
            TableModel tm = jt.getModel();
            JRTableModelDataSource jrtmds = new JRTableModelDataSource(tm);
            //   JasperPrint jp = JasperFillManager.fillReport(jr, hm,jrtmds);
            JREmptyDataSource jreds = new JREmptyDataSource();
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, jrtmds);
            //     JasperViewer.viewReport(jp, false);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(FRN.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void makePayment() {
        if (comboBox1.getSelectedItem().toString().equals("Select payment_method")) {
//            Message m = new Message(new JFrame(), "Please select a valid payment method ", "Warning");
//            m.setVisible(true);
            OptionMessageLegit m = new OptionMessageLegit(new JFrame(), "Please select a valid payment method") {
                @Override
                public void actionConfirmed() {

                }

                @Override
                public void actionCancelled() {
                }
            };
            m.setVisible(true);
        } else if (orderId != null) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                StringBuilder uid = new StringBuilder(UUID.randomUUID().toString() + String.valueOf(System.currentTimeMillis()));
                String subTotal = textF1.getText();
                String today = sdf.format(new Date());
                ArrayList<String> info = new ArrayList<>();
                info.add(orderId);
                info.add(today);
                info.add(subTotal);
                info.add(uid.toString());
                InsertTable it = new InsertTable("payment_note", info);
                info.clear();

                String tableName = null;
                String fullTotal = null;
                StringBuilder paymentUid = null;
                ResultSet rs = null;

                String paymentNoteId = GetIdSingle.getId("payment_note", "unique_id", uid.toString());
                for (int i = 0; i < customTable1.getRowCount(); i++) {

                    String menuItemID = (String) customTable1.getValueAt(i, 0);
                    String price = (String) customTable1.getValueAt(i, 3);
                    String qty = (String) customTable1.getValueAt(i, 2);
                    info.add(menuItemID);
                    info.add(paymentNoteId);
                    info.add(price);
                    info.add(qty);
                    it = new InsertTable("payment_item", info);
                    info.clear();

                    //print grn process
                }
                MySql.iud("UPDATE `customer_order` SET `order_status_id`='3' WHERE `customer_order_id`='" + orderId + "'");
                rs = MySql.sq(loadTableQuery);
                rs.next();
                tableName = rs.getString("customer_table.customer_table_name");
                fullTotal = rs.getString("customer_order.total");

                paymentUid = new StringBuilder(UUID.randomUUID().toString() + String.valueOf(System.currentTimeMillis()));
                String paymentMethodId = GetIdSingle.getId("payment_method", comboBox1.getSelectedItem().toString());
                info.add(today);
                info.add(paymentMethodId);
                info.add(paymentNoteId);
                info.add(paymentUid.toString());

                it = new InsertTable("customer_payment", info);
                info.clear();

                Vector<String> v = new Vector<String>();
                v.add(uid.toString());

                v.add(tableName);
                v.add(paymentUid.toString());
                v.add(today);
                v.add(comboBox1.getSelectedItem().toString());
                v.add(fullTotal);
                printGRN(customTable1, v);

                this.dispose();
                ogorder.loadTable();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CustomerPayment.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void closeFunctionalities() {

        if (isOtherFramesInvolved) {
            this.dispose();

        } else {
            System.exit(0);
        }
    }

    private void jframeCustmize() {
        closeLabel.setIcon(labelSetIcon("/Icons/close.png", closeLabel.getWidth() - 25, closeLabel.getHeight() - 17));
        miniLabel.setIcon(labelSetIcon("/Icons/minus.png", miniLabel.getWidth() - 20, miniLabel.getHeight() - 13));

        miniLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });
    }

    public ImageIcon labelSetIcon(String src, int w, int h) {
        ImageSizer imgSizer = new ImageSizer();
        ImageIcon i = imgSizer.overaallResizer(src, w, h);
        return i;
    }

    public abstract void actionConfirmed();

    public abstract void actionCancelled();
    int x = 0;
    int y = 0;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new RoundedPanel();
        roundedPanel2 = new RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        miniLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        textF1 = new frameutil.TextF();
        jLabel5 = new javax.swing.JLabel();
        comboBox1 = new frameutil.ComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        customButton1 = new frameutil.CustomButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable1 = new frameutil.CustomTable();
        menuBar1 = new frameutil.MenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        roundedPanel1.setBackground(new java.awt.Color(153, 153, 153));
        roundedPanel1.setRoundBottomLeft(7);
        roundedPanel1.setRoundBottomRight(7);
        roundedPanel1.setRoundTopLeft(7);
        roundedPanel1.setRoundTopRight(7);

        roundedPanel2.setBackground(new java.awt.Color(51, 51, 51));
        roundedPanel2.setRoundTopLeft(7);
        roundedPanel2.setRoundTopRight(7);
        roundedPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                roundedPanel2MouseDragged(evt);
            }
        });
        roundedPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundedPanel2MousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("RAG");
        jLabel1.setToolTipText("");

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        jPanel1.add(closeLabel, java.awt.BorderLayout.LINE_END);

        miniLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        miniLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        jPanel1.add(miniLabel, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total");

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Payment Method");

        customButton1.setText("Make Payment");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(comboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(318, 318, 318))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        customTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "menuId", "menuItem", "qty", "price", "total"
            }
        ));
        jScrollPane1.setViewportView(customTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void roundedPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MouseDragged
            // TODO add your handling code here:
            int xx = evt.getXOnScreen();
            int yy = evt.getYOnScreen();
            this.setLocation(xx - x, yy - y);
        }//GEN-LAST:event_roundedPanel2MouseDragged

        private void roundedPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MousePressed
            // TODO add your handling code here:
            x = evt.getX();
            y = evt.getY();
        }//GEN-LAST:event_roundedPanel2MousePressed

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
        // TODO add your handling code here:
        makePayment();
    }//GEN-LAST:event_customButton1ActionPerformed

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        // TODO add your handling code here:
       closeFunctionalities();
    }//GEN-LAST:event_closeLabelMouseClicked

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
            java.util.logging.Logger.getLogger(CustomerPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CustomerPayment dialog = new CustomerPayment(new javax.swing.JFrame(), true) {
                    @Override
                    public void actionConfirmed() {
                    }

                    @Override
                    public void actionCancelled() {
                    }
                };
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel closeLabel;
    private frameutil.ComboBox<String> comboBox1;
    private frameutil.CustomButton customButton1;
    private frameutil.CustomTable customTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private frameutil.MenuBar menuBar1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private frameutil.TextF textF1;
    // End of variables declaration//GEN-END:variables
}
