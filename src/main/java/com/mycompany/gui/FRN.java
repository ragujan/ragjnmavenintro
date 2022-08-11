package com.mycompany.gui;

import com.mycompany.frameutil.*;
import com.mycompany.gui.employee.Manager;
import com.mycompany.gui.employee.Supplier;
import com.mycompany.util.*;

import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.mycompany.model.MySql;
import com.mycompany.view.frameutilswingcomponents.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author acer
 */
public class FRN extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public FRN() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 7, 7));

        jframeCustmize();
        this.setBackground(MainTheme.mainColor);
        roundedPanel1.setBackground(MainTheme.mainColor);
        roundedPanel2.setBackground(MainTheme.secondColor);
        textF1.setBackground(MainTheme.fourthColor);
        textF2.setBackground(MainTheme.fourthColor);
        textF3.setBackground(MainTheme.fourthColor);
        textF1.setEditable(false);
        textF2.setEditable(false);
        textF3.setEditable(false);
        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);
        textF7.setEditable(false);
        textF2.setEditable(false);
        customButton2.setEnabled(false);
        textF1.setForeground(MainTheme.secondColor);
        textF2.setForeground(MainTheme.secondColor);
        textF3.setForeground(MainTheme.secondColor);
        jPanel4.setBackground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.secondColor);
        jPanel11.setBackground(MainTheme.secondColor);
        textF23.setEditable(false);
        textF25.setEditable(false);
        textF26.setEditable(false);
        textF23.setForeground(MainTheme.secondColor);
        textF25.setForeground(MainTheme.secondColor);
        textF24.setForeground(MainTheme.secondColor);
        customButton8.setEnabled(false);
        jPanel4.setEnabled(false);

        this.setForeground(MainTheme.secondColor);
        this.setVisible(true);
        jDateChooser3.setVisible(false);
        jDateChooser4.setVisible(false);
        setValidadions();
        updateTotal();
        loadCombos();
        ragfrn = this;
        foodMenuBar1.foo(this);
    }
    public String foodItemType;
    public String managerName;
    public int optionYesNo = 0;
    public int idExitsRow = 0;
    int tableclickCount = 0;
    int tableSelectedRow = 0;
    int row = 0;
    public String mangerId;
    FRN ragfrn;

    private void jframeCustmize() {
        closeLabel.setIcon(labelSetIcon("/Icons/close.png", closeLabel.getWidth() - 25, closeLabel.getHeight() - 17));
        boxLabel.setIcon(labelSetIcon("/Icons/square.png", boxLabel.getWidth() - 23, boxLabel.getHeight() - 17));
        miniLabel.setIcon(labelSetIcon("/Icons/minus.png", miniLabel.getWidth() - 20, miniLabel.getHeight() - 13));

        miniLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setState(JFrame.ICONIFIED);
            }
        });
    }

    private void updateTotal() {
        double rowTotal = 0;
        if (customTable1.getRowCount() > 0) {
            for (int i = 0; i < customTable1.getRowCount(); i++) {
                String total = customTable1.getValueAt(i, 7).toString();
                rowTotal += Double.parseDouble(total);
            }
        } else {
            rowTotal = 0;
        }
        textF23.setText(Double.toString(rowTotal));
    }

    private void loadCombos() {
        LoadSubTypes.loadType(comboBox1, "payment_method");

    }

    public ImageIcon labelSetIcon(String src, int w, int h) {
        ImageSizer imgSizer = new ImageSizer();
        ImageIcon i = imgSizer.overaallResizer(src, w, h);
        return i;
    }

    public void clearFields() {
        JComponent[] jc = {textF1, textF2, textF8, textF9, textF10, textF11};
        SetEmptyItems.emptyItems(jc);
    }

    private void removeRow() {
        tableclickCount++;
        int previousSelectedRow = row;
        int currentlySelectedRow = 0;
        currentlySelectedRow = customTable1.getSelectedRow();

        if (previousSelectedRow == currentlySelectedRow && tableclickCount == 2) {
            row = currentlySelectedRow;
            int modelRow = customTable1.convertRowIndexToModel(row);
            DefaultTableModel model = (DefaultTableModel) customTable1.getModel();
            model.removeRow(modelRow);
            customTable1.clearSelection();
            if (!(customTable1.getRowCount() > 1)) {
                customButton8.setEnabled(false);
            }
            tableclickCount = 0;

        } else if (previousSelectedRow == currentlySelectedRow && tableclickCount == 1) {

            row = currentlySelectedRow;
            tableclickCount = 1;

        } else if (previousSelectedRow != currentlySelectedRow && tableclickCount == 1) {
            row = currentlySelectedRow;
            tableclickCount = 1;

        } else if (tableclickCount >= 2) {

            tableclickCount = 0;
        }
    }

    public void thatBeautiful() {
        Message m = new Message(this, "hey", "HEY");
    }

    private boolean checkPaymentMethod() {
        String paymentMethod = comboBox1.getSelectedItem().toString();
        boolean state = false;
        if (paymentMethod.equals("Select payment_method")) {
            state = true;
        }
        return state;
    }

    private void printGRN(JTable jt, Vector<String> v) {

        try {
            TableModel tm1 = jt.getModel();
            // String jasperPath = "src//reportXML//Blank_A4.jrxml";
            InputStream jasperPathStream = getClass().getResourceAsStream("/reportxml/Blank_A4.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(jasperPathStream);
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("Dealer", v.get(0).toString());
            hm.put("Supplier", v.get(1).toString());

            hm.put("DnT", v.get(2).toString());
            hm.put("UniqueID", v.get(3).toString());
            hm.put("SubTotal", v.get(4).toString());
            hm.put("Paid", v.get(5).toString());
            hm.put("Balance", v.get(6).toString());
            hm.put("Manager", v.get(7).toString());
            TableModel tm = jt.getModel();
            JRTableModelDataSource jrtmds = new JRTableModelDataSource(tm);
            //   JasperPrint jp = JasperFillManager.fillReport(jr, hm,jrtmds);
            JREmptyDataSource jreds = new JREmptyDataSource();
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, jrtmds);
            //   JasperViewer.viewReport(jp, false);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(FRN.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void addtoTable() {
        JComponent[] jc = {textF1, textF2, textF8, textF9, textF10, textF11, jDateChooser3, jDateChooser4};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean isDatesChosen = false;
        boolean invalidDateChosen = false;
        //System.out.println(sdf.format(new Date()));
        if (jDateChooser3.getDate() != null && jDateChooser4.getDate() != null) {
            isDatesChosen = true;
            long time_difference = jDateChooser3.getDate().getTime() - jDateChooser4.getDate().getTime();
            if (time_difference <= 0) {
                invalidDateChosen = true;
            }
        }
        String supId = textF4.getText();
        String foodItemId = textF1.getText();
        String qty = textF8.getText();
        String price = textF9.getText();
        String foodName = textF2.getText();
        String foodType = textF3.getText();
        String supName = textF5.getText();
        String dealerName = textF6.getText();
        String mfd = textF11.getText();
        String exp = textF10.getText();

        if (supId.isEmpty()) {
            Message m = new Message(this, "supplier id is not choosen", "Warning");
        } else if (foodItemId.isEmpty()) {
            Message m = new Message(this, "food item id is not choosen", "Warning");
        } else if (!isDatesChosen) {
            Message m = new Message(this, "Please choose the dates", "Warning");
        } else if (qty.isEmpty()) {
            Message m = new Message(this, "Plesae select the qty", "Warning");
        } else if (price.isEmpty()) {
            Message m = new Message(this, "Please enter the buying price", "Warning");
        } else if (invalidDateChosen) {
            Message m = new Message(this, "Please select valid exp mfd dates", "Warning");
        } else {

            boolean isIdExits = false;

            for (int i = 0; i < customTable1.getRowCount(); i++) {
                if (foodItemId.equals(customTable1.getValueAt(i, 0).toString())) {
                    idExitsRow = i;
                    isIdExits = true;
                    break;
                }
            }
            if (isIdExits) {

                OptionMessageLegit opml = new OptionMessageLegit(ragfrn, "Do you want to update the row") {
                    @Override
                    public void actionConfirmed() {

                        DefaultTableModel dftm = (DefaultTableModel) ragfrn.customTable1.getModel();
                        String id = dftm.getValueAt(idExitsRow, 0).toString();
                        String name = dftm.getValueAt(idExitsRow, 1).toString();
                        String category = dftm.getValueAt(idExitsRow, 2).toString();
                        String qty = dftm.getValueAt(idExitsRow, 3).toString();
                        String price = dftm.getValueAt(idExitsRow, 4).toString();
                        textF1.setText(id);
                        textF2.setText(name);
                        textF3.setText(category);
                        textF8.setText(id);
                        textF9.setText(name);

                        dftm.removeRow(idExitsRow);

                    }

                    @Override
                    public void actionCancelled() {
                        textF1.setText("");
                        textF2.setText("");
                        textF3.setText("");
                        textF8.setText("");
                        textF9.setText("");
                        textF10.setText("");
                        textF11.setText("");
                    }
                };
                opml.setVisible(true);

            } else {
                Vector<String> v = new Vector<String>();
                //this is like a columns of a table
                v.add(foodItemId);
                v.add(foodName);
                v.add(foodType);
                v.add(qty);
                v.add(price);
                v.add(mfd);
                v.add(exp);
                v.add(Double.toString(Double.parseDouble(qty) * Double.parseDouble(price)));
                DefaultTableModel dftm = (DefaultTableModel) customTable1.getModel();
                // add the row to the table 

                SetEmptyItems.emptyItems(jc);
                dftm.addRow(v);
            }

        }
        updateTotal();
        if (customTable1.getRowCount() > 0) {
            customButton8.setEnabled(true);
        }
        //Date onDTDiff = sdf.parse(jDateChooser3.getDate());
        //Date offDTDiff = sdf.parse(offTime);

    }

    private void updateManager() {
        if (this.mangerId == null) {

            OptionMessageLegit opml = new OptionMessageLegit(ragfrn, "You have to Select the Manager") {
                @Override
                public void actionConfirmed() {
                    CreateObject.make(new Manager(ragfrn));
                    proceedToAddFRN = true;
                    textF26.setText(managerName);
                }

                @Override
                public void actionCancelled() {

                }
            };
            opml.setVisible(true);

        } else {
            OptionMessageLegit opml = new OptionMessageLegit(ragfrn, "Do you want to change the manager ") {
                @Override
                public void actionConfirmed() {
                    proceedToAddFRN = false;
                    CreateObject.make(new Manager(ragfrn));

                }

                @Override
                public void actionCancelled() {
                    proceedToAddFRN = true;

                }
            };
            opml.setVisible(true);
        }
    }
    boolean proceedToAddFRN = false;

    public void addFRN() {
        if (isPaidAmountEmpty()) {

            Message m = new Message(this, "Please Enter the paid Amount", "Warning");
            textF24.requestFocus();
        } else if (checkPaymentMethod()) {
            Message m = new Message(this, "Please select the payment method ", "Warning");
            comboBox1.requestFocus();
        } else {
            updateManager();

        }

        if (proceedToAddFRN && this.mangerId != null) {
            addFRNfurtherMore();
        }

    }

    private boolean isPaidAmountEmpty() {
        boolean state = false;
        String amount = textF24.getText();
        if (amount.isEmpty()) {
            state = true;

        }
        return state;

    }

    public void addFRNfurtherMore() {
        String paidAmount = textF24.getText();
        String supplier = textF4.getText();
        String supplierName = textF5.getText();
        String dealerName = textF6.getText();
        String subTotal = textF23.getText();
        String balance = textF25.getText();
        String paymentMethod = comboBox1.getSelectedItem().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = sdf.format(new Date());
        String foodReceiveNoteId = null;
        String foodStorageId = null;
        if (paidAmount.isEmpty()) {
            Message m = new Message(this, "Please enter the paid amount ", "Warning");
        } else if (checkPaymentMethod()) {
            Message m = new Message(this, "Please select the payment method ", "Warning");
        } else {

            String uniqueId = Long.toString(System.currentTimeMillis());
            ArrayList<String> info = new ArrayList<>();

            info.add(this.mangerId);
            info.add(today);
            info.add(supplier);
            info.add(uniqueId);
            InsertTable it = new InsertTable("food_receive_note", info);
            foodReceiveNoteId = GetIdSingle.getId("food_receive_note", "unique_id", uniqueId);
            for (int i = 0; i < customTable1.getRowCount(); i++) {
                System.out.println("IM working here");
                String fid = customTable1.getValueAt(i, 0).toString();
                String qty = customTable1.getValueAt(i, 3).toString();
                String mfd = customTable1.getValueAt(i, 5).toString();
                String exp = customTable1.getValueAt(i, 6).toString();
                String buyingPrice = customTable1.getValueAt(i, 4).toString();
                System.out.println("fid is " + fid);
                try {
                    ResultSet rs = MySql.sq("SELECT * FROM `food_storage` WHERE `food_item_id`='" + fid + "' AND `mfd`='" + mfd + "' AND `exp`='" + exp + "' ");
                    if (rs.next()) {
                        foodStorageId = rs.getString("food_storage_id");
                        MySql.iud("UPDATE `food_storage` SET `qty`='" + qty + "' WHERE `food_storage_id`='" + foodStorageId + "'");
                        System.out.println("updated storage");
                    } else {
                        info.clear();
                        System.out.println("Newly added storage");
                        info.add(exp);
                        info.add(fid);
                        info.add(mfd);
                        info.add(qty);
                        it = new InsertTable("food_storage", info);
                        it = new InsertTable("food_storage_manage", info);
                        rs = MySql.sq("SELECT * FROM `food_storage` WHERE `food_item_id`='" + fid + "' AND `mfd`='" + mfd + "' AND `exp`='" + exp + "' ");
                        rs.next();
                        foodStorageId = rs.getString("food_storage_id");
                    }
                    info.clear();
                    info.add(buyingPrice);
                    info.add(foodReceiveNoteId);
                    info.add(foodStorageId);
                    info.add(qty);
                    it = new InsertTable("food_receive_item", info);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FRN.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FRN.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            Vector<String> v = new Vector<String>();
            v.add(dealerName);
            v.add(supplierName);

            v.add(today);
            v.add(uniqueId);
            v.add(subTotal);
            v.add(paidAmount);
            v.add(balance);
            v.add(managerName);
            printGRN(customTable1, v);
            RemoveTRows.remove(customTable1);

            String paymentMethodId = GetIdSingle.getId("payment_method", paymentMethod);
            info.clear();
            info.add(paidAmount);
            info.add(balance);
            info.add(foodReceiveNoteId);
            info.add(paymentMethodId);
            it = new InsertTable("food_receive_payment", info);
            customButton8.setEnabled(false);

        }
    }

    public void selectManager() {
        CreateObject.make(new Manager(this));

    }

    private void setValidadions() {

        String priceRegex = "^\\d*([,]\\d*)*([.]\\d*)?";
        String qtyRegex = "([1-9][0-9]+|[1-9])";

        new FilterDocRagRegex(textF8, qtyRegex, 10);
        new FilterDocRagRegex(textF9, priceRegex, 10);
        new FilterDocRagRegex(textF24, priceRegex, 10);
        String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
        FilterDocRagRegex dob = new FilterDocRagRegex(textF10, dobRegex);
        dob = new FilterDocRagRegex(textF11, dobRegex);
        //dob = new FilterDocRagRegex(textF5, dobRegex);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        customButton4 = new CustomButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        textF12 = new TextF();;
        textF13 = new TextF();;
        textF14 = new TextF();;
        textF15 = new TextF();;
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        textF16 = new TextF();;
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        textF17 = new TextF();;
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        textF18 = new TextF();;
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        customButton5 = new CustomButton();
        jPanel8 = new javax.swing.JPanel();
        customButton6 = new CustomButton();
        textF19 = new TextF();;
        jLabel20 = new javax.swing.JLabel();
        textF20 = new TextF();;
        jLabel21 = new javax.swing.JLabel();
        textF21 = new TextF();;
        jLabel22 = new javax.swing.JLabel();
        textF22 = new TextF();;
        jLabel23 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        customTable2 = new CustomTable();
        roundedPanel1 = new RoundedPanel();
        roundedPanel2 = new RoundedPanel();
        jPanel1 = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        miniLabel = new javax.swing.JLabel();
        boxLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        textF4 = new TextF();;
        jLabel6 = new javax.swing.JLabel();
        textF5 = new TextF();;
        jLabel7 = new javax.swing.JLabel();
        textF6 = new TextF();;
        jLabel8 = new javax.swing.JLabel();
        textF7 = new TextF();;
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        customButton1 = new CustomButton();
        customButton7 = new CustomButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        customButton2 = new CustomButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textF1 = new TextF();;
        textF2 = new TextF();;
        textF3 = new TextF();;
        textF8 = new TextF();;
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textF9 = new TextF();;
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        textF10 = new TextF();;
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        textF11 = new TextF();;
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        customButton3 = new CustomButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable1 = new CustomTable();
        jPanel11 = new javax.swing.JPanel();
        customButton8 = new CustomButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        textF25 = new TextF();;
        textF23 = new TextF();;
        textF24 = new TextF();;
        jLabel27 = new javax.swing.JLabel();
        textF26 = new TextF();;
        comboBox1 = new ComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        foodMenuBar1 = new FoodMenuBar();

        jToggleButton1.setText("jToggleButton1");

        jPanel7.setBackground(new java.awt.Color(51, 255, 0));

        customButton4.setText("Food Item");
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Id");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Name");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Type");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Qty");

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Price");

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Mfd");

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Exp");

        textF17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF17FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF17FocusLost(evt);
            }
        });

        jDateChooser5.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser5.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser5.setIcon(null);
        jDateChooser5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser5PropertyChange(evt);
            }
        });

        textF18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF18FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF18FocusLost(evt);
            }
        });

        jDateChooser6.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser6.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser6.setIcon(null);
        jDateChooser6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser6PropertyChange(evt);
            }
        });

        customButton5.setText("Enter FRN");
        customButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(textF18, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textF17, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textF15, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textF13, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(textF16, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(338, 338, 338)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textF14, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15)
                    .addComponent(textF14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textF17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(0, 204, 255));

        customButton6.setText("Supplier");
        customButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton6ActionPerformed(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Name");

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Dealer");

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Type");

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Id");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(textF19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF20, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textF21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        customTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food_ID", "Name", "Category", "Qty", "Buying Price", "Total"
            }
        ));
        jScrollPane2.setViewportView(customTable2);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel1.setLayout(new java.awt.BorderLayout());

        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeLabelMouseExited(evt);
            }
        });
        jPanel1.add(closeLabel, java.awt.BorderLayout.LINE_END);

        miniLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        miniLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        miniLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                miniLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                miniLabelMouseExited(evt);
            }
        });
        jPanel1.add(miniLabel, java.awt.BorderLayout.WEST);

        boxLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boxLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        jPanel1.add(boxLabel, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("RAG");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Dealer");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Type");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Id");

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.CardLayout());

        customButton1.setText("Supplier");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });
        jPanel10.add(customButton1, "card2");

        customButton7.setText("Switch");
        customButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton7ActionPerformed(evt);
            }
        });
        jPanel10.add(customButton7, "card3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textF6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel4.setBackground(new java.awt.Color(51, 255, 0));

        customButton2.setText("Food Item");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Id");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Type");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Qty");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Price");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Mfd");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Exp");

        textF10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF10FocusLost(evt);
            }
        });

        jDateChooser3.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser3.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser3.setIcon(null);
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        textF11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF11FocusLost(evt);
            }
        });

        jDateChooser4.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser4.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser4.setIcon(null);
        jDateChooser4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser4PropertyChange(evt);
            }
        });

        customButton3.setText("Enter FRN");
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textF8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(textF9, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(338, 338, 338)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(textF3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textF9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        customTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "category", "qty", "price", "mfd", "exp", "total"
            }
        ));
        customTable1.getTableHeader().setReorderingAllowed(false);
        customTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(customTable1);

        customButton8.setText("Print");
        customButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton8ActionPerformed(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Balance");

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Paid");

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Total");

        textF24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF24KeyTyped(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Manager");

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Payment Method");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textF23, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textF24, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textF25, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 107, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF26, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(customButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textF23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textF24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addComponent(textF25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(textF26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(foodMenuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(foodMenuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
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
    int x = 0;
    int y = 0;
    private void roundedPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_roundedPanel2MousePressed

    private void roundedPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MouseDragged
        // TODO add your handling code here:
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - x, yy - y);
    }//GEN-LAST:event_roundedPanel2MouseDragged

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void closeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseEntered
        // TODO add your handling code here:
        closeLabel.setOpaque(true);
        closeLabel.setBackground(MainTheme.mainColor);
    }//GEN-LAST:event_closeLabelMouseEntered

    private void closeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseExited
        // TODO add your handling code here:
        closeLabel.setBackground(MainTheme.secondColor);
        closeLabel.setOpaque(false);

    }//GEN-LAST:event_closeLabelMouseExited

    private void miniLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniLabelMouseEntered
        // TODO add your handling code here:
        miniLabel.setOpaque(true);
        miniLabel.setBackground(MainTheme.mainColor);
    }//GEN-LAST:event_miniLabelMouseEntered

    private void miniLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniLabelMouseExited
        // TODO add your handling code here:

        miniLabel.setBackground(MainTheme.secondColor);
        miniLabel.setOpaque(false);
    }//GEN-LAST:event_miniLabelMouseExited

        private void textF10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF10ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_textF10ActionPerformed

        private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_customButton4ActionPerformed

        private void textF17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF17FocusGained
            // TODO add your handling code here:
        }//GEN-LAST:event_textF17FocusGained

        private void textF17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF17FocusLost
            // TODO add your handling code here:
        }//GEN-LAST:event_textF17FocusLost

        private void jDateChooser5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser5PropertyChange
            // TODO add your handling code here:
        }//GEN-LAST:event_jDateChooser5PropertyChange

        private void textF18FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF18FocusGained
            // TODO add your handling code here:
        }//GEN-LAST:event_textF18FocusGained

        private void textF18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF18FocusLost
            // TODO add your handling code here:
        }//GEN-LAST:event_textF18FocusLost

        private void jDateChooser6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser6PropertyChange
            // TODO add your handling code here:
        }//GEN-LAST:event_jDateChooser6PropertyChange

        private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_customButton5ActionPerformed

        private void customButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton6ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_customButton6ActionPerformed

        private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
            // TODO add your handling code here:
            addtoTable();
        }//GEN-LAST:event_customButton3ActionPerformed

        private void jDateChooser4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser4PropertyChange
            // TODO add your handling code here:
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            if (jDateChooser4.getDate() != null) {
                textF11.setText(sdf.format(jDateChooser4.getDate()).toString());
            }
        }//GEN-LAST:event_jDateChooser4PropertyChange

        private void textF11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusLost
            // TODO add your handling code here:
            jDateChooser4.setVisible(false);
        }//GEN-LAST:event_textF11FocusLost

        private void textF11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusGained
            // TODO add your handling code here:
            jDateChooser4.setVisible(true);
        }//GEN-LAST:event_textF11FocusGained

        private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
            // TODO add your handling code here:
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            if (jDateChooser3.getDate() != null) {
                textF10.setText(sdf.format(jDateChooser3.getDate()).toString());
            }
        }//GEN-LAST:event_jDateChooser3PropertyChange

        private void textF10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusLost
            // TODO add your handling code here:
            jDateChooser3.setVisible(false);
        }//GEN-LAST:event_textF10FocusLost

        private void textF10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusGained
            // TODO add your handling code here:
            //click the jdateChooser to pick the date
            //or show the jDateChooser to pick the date
            jDateChooser3.setVisible(true);

            //jDateChooser3.firePropertyChange(searchText, NORMAL, NORMAL);
            //jDateChooser3.propertyChange((PropertyChangeEvent) jDateChooser3.getPropertyChangeListeners()[0]);
        }//GEN-LAST:event_textF10FocusGained

        private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
            // TODO add your handling code here:
            CreateObject.make(new FoodItem(this, this.foodItemType));
        }//GEN-LAST:event_customButton2ActionPerformed

        private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
            // TODO add your handling code here:
            CreateObject.make(new Supplier(this));
            PanelRemover.removeP(jPanel10, customButton7);
        }//GEN-LAST:event_customButton1ActionPerformed

        private void customButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton7ActionPerformed
            // TODO add your handling code here:
            PanelRemover.removeP(jPanel10, customButton1);
        }//GEN-LAST:event_customButton7ActionPerformed

        private void customButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton8ActionPerformed
            // TODO add your handling code here:
            addFRN();
        }//GEN-LAST:event_customButton8ActionPerformed

        private void customTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customTable1MouseClicked
            // TODO add your handling code here:
            removeRow();
        }//GEN-LAST:event_customTable1MouseClicked

        private void textF24KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF24KeyTyped
            // TODO add your handling code here:

            String paidAmountString = textF24.getText() + evt.getKeyChar();
            if (!paidAmountString.isEmpty()) {
                Double paidAmount = Double.parseDouble(textF24.getText() + evt.getKeyChar());
                Double total = Double.parseDouble(textF23.getText() + evt.getKeyChar());
                textF25.setText(Double.toString(total - paidAmount));
            } else {

            }

        }//GEN-LAST:event_textF24KeyTyped
    boolean emailFieldEntred = false;

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
            java.util.logging.Logger.getLogger(FRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                JFrame jf = new FRN();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private ComboBox<String> comboBox1;
    private CustomButton customButton1;
    public CustomButton customButton2;
    private CustomButton customButton3;
    public CustomButton customButton4;
    private CustomButton customButton5;
    private CustomButton customButton6;
    private CustomButton customButton7;
    private CustomButton customButton8;
    public CustomTable customTable1;
    private CustomTable customTable2;
    private FoodMenuBar foodMenuBar1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    public TextF textF1;
    public TextF textF10;
    public TextF textF11;
    public TextF textF12;
    public TextF textF13;
    public TextF textF14;
    public TextF textF15;
    public TextF textF16;
    private TextF textF17;
    private TextF textF18;
    public TextF textF19;
    public TextF textF2;
    public TextF textF20;
    public TextF textF21;
    public TextF textF22;
    public TextF textF23;
    public TextF textF24;
    public TextF textF25;
    public TextF textF26;
    public TextF textF3;
    public TextF textF4;
    public TextF textF5;
    public TextF textF6;
    public TextF textF7;
    public TextF textF8;
    public TextF textF9;
    // End of variables declaration//GEN-END:variables
}
