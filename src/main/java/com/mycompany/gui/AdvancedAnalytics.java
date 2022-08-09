package com.mycompany.gui;

import com.mycompany.util.CreateObject;
import com.mycompany.util.FilterDocRagRegex;
import com.mycompany.util.LoadSubTypes;
import com.mycompany.util.PanelRemover;
import com.mycompany.util.SearchTable;
import com.mycompany.util.SetEmptyItems;
import com.mycompany.gui.employee.Chef;
import com.mycompany.frameutil.RoundedPanel;
import com.mycompany.frameutil.ImageSizer;
import com.mycompany.frameutil.MainTheme;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import com.mycompany.model.MySql;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author acer
 */
public class AdvancedAnalytics extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public AdvancedAnalytics() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 7, 7));

        jframeCustmize();
        this.setBackground(MainTheme.mainColor);
        roundedPanel1.setBackground(MainTheme.mainColor);
        roundedPanel2.setBackground(MainTheme.secondColor);
        jDateChooser3.setVisible(false);
        jDateChooser4.setVisible(false);
        jDateChooser5.setVisible(false);
        jDateChooser6.setVisible(false);
        jDateChooser7.setVisible(false);
        jDateChooser8.setVisible(false);
        jDateChooser3.setBackground(MainTheme.secondColor);
        jDateChooser4.setBackground(MainTheme.secondColor);
        jDateChooser5.setBackground(MainTheme.secondColor);
        jDateChooser6.setBackground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.secondColor);
        jPanel3.setBackground(MainTheme.secondColor);
        jPanel5.setBackground(MainTheme.secondColor);
        jPanel6.setBackground(MainTheme.secondColor);
        textF18.setEditable(false);

        textF20.setEditable(false);

        textF15.setEditable(false);
        this.setForeground(MainTheme.secondColor);

        setDocFilters();
        loadCombos();
        menuBar1.foo(this);
    }

    public AdvancedAnalytics(SellingRecord sr) {
        this();
        this.isOtherFramesInvolved = true;
        this.isSelllingRecordInvolved = true;
        this.sr = sr;
        sr.setVisible(false);
        sr.setEnabled(false);
    }

    public AdvancedAnalytics(DealerT et, HashMap<String, String> hm) {
        this();
        this.updateId = hm.get("id");

    }

    public AdvancedAnalytics(Chef c) {
        this();

    }
    boolean isOtherFramesInvolved = false;
    boolean isSelllingRecordInvolved = false;
    SellingRecord sr;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String updateId;
    String loadTableQuery;
    String additionalQuery = "INNER JOIN `mainmenu`\n"
            + "ON `mainmenu`.`menuItemId` = `payment_item`.`menuItemId`\n"
            + "INNER JOIN `serving_type`\n"
            + "ON `serving_type`.`serving_type_id` = `mainmenu`.`serving_type_id`\n"
            + "INNER JOIN `menu_item_category` \n"
            + "ON `menu_item_category`.`menu_item_category_id` = `mainmenu`.`menu_item_category_id`";

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

    public ImageIcon labelSetIcon(String src, int w, int h) {
        ImageSizer imgSizer = new ImageSizer();
        ImageIcon i = imgSizer.overaallResizer(src, w, h);
        return i;
    }

    private void loadCombos() {
        LoadSubTypes.loadType(comboBox2, "employee_type");
        LoadSubTypes.loadType(comboBox3, "menu_item_category");
    }

    private void loadEmpSalaryQuery() {

        ArrayList<String> al = new ArrayList<String>();
        al.add("employee_salary");
        al.add("employee,employee_salary");

        al.add("employee_type,employee");
        al.add("gender,employee");
        SearchTable st = new SearchTable(al);
        this.loadTableQuery = null;
        this.loadTableQuery = st.getTableQuery();
    }

    private void loadDealerQuery() {

        ArrayList<String> al = new ArrayList<String>();
        al.add("food_receive_item");
        al.add("food_storage,food_receive_item");
        al.add("food_item,food_storage");
        al.add("food_item_category,food_item");
        al.add("food_receive_note,food_receive_item");
        al.add("supplier,food_receive_note");
        al.add("dealer,supplier");

        al.add("dealer_type,dealer");
        SearchTable st = new SearchTable(al);
        this.loadTableQuery = null;
        this.loadTableQuery = st.getTableQuery();
    }

    public void employeeSalary() {

        String toDate = textF10.getText();
        String fromDate = textF11.getText();
        String empType = comboBox2.getSelectedItem().toString();
        if (toDate.isEmpty() || fromDate.isEmpty()) {
            Message m = new Message(this, "Date Fields cannot be empty ", "Warning");
        } else if (!toDate.isEmpty() && !fromDate.isEmpty()) {
            if (empType.equals("Select employee_type")) {
                loadEmpSalaryQuery();

                StringBuilder sb = new StringBuilder();
                sb.append(loadTableQuery);
                sb.append(" WHERE `employee_salary`.`paid_date` BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                ResultSet rs;

                try {
                    double total = 0;
                    rs = MySql.sq(sb.toString());
                    ResultSet empSalary = MySql.sq(sb.toString());
                    if (empSalary.next()) {
                        while (rs.next()) {
                            total += (Double.parseDouble(rs.getString("paid_amount")) + Double.parseDouble(rs.getString("bonus")));
                            textF12.setText(Double.toString(total));
                        }

                    } else {

                        textF12.setText(Double.toString(total));
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("This section");
                loadEmpSalaryQuery();

                StringBuilder sb = new StringBuilder();
                sb.append(loadTableQuery);
                sb.append(" WHERE ( `employee_salary`.`paid_date` BETWEEN '" + fromDate + "' AND '" + toDate + "') AND `employee_type`.`employee_type_name`='" + empType + "' ");
                ResultSet rs;
                System.out.println(sb);
                try {
                    double total = 0;
                    rs = MySql.sq(sb.toString());
                    ResultSet empSalary = MySql.sq(sb.toString());
                    if (empSalary.next()) {
                        while (rs.next()) {
                            total += (Double.parseDouble(rs.getString("paid_amount")) + Double.parseDouble(rs.getString("bonus")));
                            textF12.setText(Double.toString(total));
                        }

                    } else {

                        textF12.setText(Double.toString(total));
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private void loadSalesQuery() {
        this.loadTableQuery = null;
        ArrayList<String> al = new ArrayList<String>();
        al.add("payment_item");
        al.add("payment_note,payment_item");
        SearchTable st = new SearchTable(al);

        this.loadTableQuery = st.getTableQuery();
        String loadTableQueryAttached = loadTableQuery + " " + additionalQuery;
    }

    public void salesRecord() {
        String toDate = textF14.getText();
        String fromDate = textF13.getText();
        String menuItemCategory = comboBox3.getSelectedItem().toString();

        if (toDate.isEmpty() || fromDate.isEmpty()) {
            Message m = new Message(this, "Date Fields cannot be empty ", "Warning");
        } else if (!toDate.isEmpty() && !fromDate.isEmpty()) {
            if (menuItemCategory.equals("Select menu_item_category")) {
                loadSalesQuery();
                StringBuilder sb = new StringBuilder();
                sb.append(loadTableQuery);
                sb.append(" WHERE `payment_note`.`payment_time` BETWEEN '" + fromDate + "' AND '" + toDate + "'");

                System.out.println(sb);

                ResultSet rs;
                try {
                    double total = 0;
                    rs = MySql.sq(sb.toString());
                    ResultSet salesRecordResult = MySql.sq(sb.toString());
                    if (salesRecordResult.next()) {
                        while (rs.next()) {
                            total += (Double.parseDouble(rs.getString("total")));
                            textF15.setText(Double.toString(total));
                        }
                    } else {
                        textF15.setText(Double.toString(total));

                    }

                 
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                loadSalesQuery();
                StringBuilder sb = new StringBuilder();
                sb.append(loadTableQuery);
                sb.append("INNER JOIN `mainmenu` ON `mainmenu`.`menuItemId`=`payment_item`.`menuItemId` INNER JOIN `menu_item_category` ON `mainmenu`.`menu_item_category_id`=`menu_item_category`.`menu_item_category_id` WHERE (`payment_note`.`payment_time` BETWEEN '" + fromDate + "' AND '" + toDate + "') AND `menu_item_category`.`menu_item_category_name`='"+menuItemCategory+"'");
                System.out.println(sb);
                System.out.println("HEY HEY HEY");
                ResultSet rs;
                try {
                    double total = 0;
                    rs = MySql.sq(sb.toString());
                    ResultSet salesRecordResult = MySql.sq(sb.toString());
                    if (salesRecordResult.next()) {
                        while (rs.next()) {
                            total += (Double.parseDouble(rs.getString("payment_item.qty")) * Double.parseDouble(rs.getString("payment_item.price")));

                        }
                    } else {

                    }
                    textF15.setText(Double.toString(total));
                   clearSalseTotalFields();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdvancedAnalytics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void dealerRecord() {
        String fromDate = textF16.getText();
        String toDate = textF17.getText();
        String dealerId = textF18.getText();
        if (dealerId.isEmpty()) {
            Message m = new Message(this, "Please Select a Dealer", "Warning");
        } else if (fromDate.isEmpty() || toDate.isEmpty()) {
            Message m = new Message(this, "Please select valid dates", "Warning");

        } else {
            loadDealerQuery();

            StringBuilder sb = new StringBuilder(loadTableQuery);
            sb.append(" WHERE (`received_datetime` BETWEEN '" + fromDate + "' AND '" + toDate + "') AND `dealer`.`dealer_id`='" + dealerId + "' ORDER BY `received_datetime`");
            System.out.println(sb);
            CreateObject.make(new StorageRecord(this, sb.toString()));
        }
    }

    private void setDocFilters() {
        String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
        FilterDocRagRegex dob = new FilterDocRagRegex(textF10, dobRegex);
        dob = new FilterDocRagRegex(textF11, dobRegex);
        dob = new FilterDocRagRegex(textF13, dobRegex);
        dob = new FilterDocRagRegex(textF14, dobRegex);
        dob = new FilterDocRagRegex(textF16, dobRegex);
        dob = new FilterDocRagRegex(textF17, dobRegex);
    }

    private void clearEmpSalaryFields() {
        JComponent[] jc = {textF10, textF11, jDateChooser3, jDateChooser4};
        loadCombos();
        SetEmptyItems.emptyItems(jc);
    }

    private void clearSalseTotalFields() {
        JComponent[] jc = {textF13, textF14, jDateChooser5, jDateChooser6};
        loadCombos();
        SetEmptyItems.emptyItems(jc);
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
        roundedPanel1 = new RoundedPanel();
        roundedPanel2 = new RoundedPanel();
        jPanel1 = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        miniLabel = new javax.swing.JLabel();
        boxLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        textF11 = new frameutil.TextF();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        textF10 = new frameutil.TextF();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        customButton1 = new frameutil.CustomButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comboBox2 = new frameutil.ComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        textF12 = new frameutil.TextF();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        textF13 = new frameutil.TextF();
        textF14 = new frameutil.TextF();
        jLabel22 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        comboBox3 = new frameutil.ComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        customButton2 = new frameutil.CustomButton();
        textF15 = new frameutil.TextF();
        jPanel5 = new javax.swing.JPanel();
        jDateChooser7 = new com.toedter.calendar.JDateChooser();
        textF16 = new frameutil.TextF();
        textF17 = new frameutil.TextF();
        jDateChooser8 = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        customButton3 = new frameutil.CustomButton();
        textF18 = new frameutil.TextF();
        textF20 = new frameutil.TextF();
        customButton4 = new frameutil.CustomButton();
        jPanel6 = new javax.swing.JPanel();
        comboBox1 = new frameutil.ComboBox();
        menuBar1 = new frameutil.MenuBar();

        jToggleButton1.setText("jToggleButton1");

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
        boxLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boxLabelMouseClicked(evt);
            }
        });
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
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setLayout(new java.awt.CardLayout());

        textF11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF11FocusLost(evt);
            }
        });
        textF11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF11ActionPerformed(evt);
            }
        });
        textF11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF11KeyTyped(evt);
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

        textF10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF10FocusLost(evt);
            }
        });
        textF10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF10KeyTyped(evt);
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

        customButton1.setText("Total");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("FromDate");
        jLabel19.setToolTipText("");
        jLabel19.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel20.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ToDate");
        jLabel20.setToolTipText("");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        comboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Emp Type");
        jLabel17.setToolTipText("");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        textF12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF12FocusLost(evt);
            }
        });
        textF12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF12ActionPerformed(evt);
            }
        });
        textF12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF12KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2, "card3");

        jLabel21.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("FromDate");
        jLabel21.setToolTipText("");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jDateChooser5.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser5.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser5.setIcon(null);
        jDateChooser5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser5PropertyChange(evt);
            }
        });

        textF13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF13FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF13FocusLost(evt);
            }
        });
        textF13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF13ActionPerformed(evt);
            }
        });
        textF13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF13KeyTyped(evt);
            }
        });

        textF14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF14FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF14FocusLost(evt);
            }
        });
        textF14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF14KeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("ToDate");
        jLabel22.setToolTipText("");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jDateChooser6.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser6.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser6.setIcon(null);
        jDateChooser6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser6PropertyChange(evt);
            }
        });

        comboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox3ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("MenuItem Category");
        jLabel18.setToolTipText("");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        customButton2.setText("Total ");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        textF15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF15FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF15FocusLost(evt);
            }
        });
        textF15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF15ActionPerformed(evt);
            }
        });
        textF15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF15KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textF13, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textF14, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textF15, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3, "card2");

        jDateChooser7.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser7.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser7.setIcon(null);
        jDateChooser7.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser7PropertyChange(evt);
            }
        });

        textF16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF16FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF16FocusLost(evt);
            }
        });
        textF16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF16ActionPerformed(evt);
            }
        });
        textF16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF16KeyTyped(evt);
            }
        });

        textF17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF17FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF17FocusLost(evt);
            }
        });
        textF17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF17KeyTyped(evt);
            }
        });

        jDateChooser8.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser8.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser8.setIcon(null);
        jDateChooser8.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser8PropertyChange(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("ToDate");
        jLabel24.setToolTipText("");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel23.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("FromDate");
        jLabel23.setToolTipText("");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        customButton3.setText("Dealer");
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
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
        textF18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF18ActionPerformed(evt);
            }
        });
        textF18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF18KeyTyped(evt);
            }
        });

        textF20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF20FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF20FocusLost(evt);
            }
        });
        textF20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF20ActionPerformed(evt);
            }
        });
        textF20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF20KeyTyped(evt);
            }
        });

        customButton4.setText("Record");
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(textF16, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(textF17, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF18, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textF20, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, "card4");

        comboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Emplyee Salary", "Sales Total", "Food Receive" }));
        comboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox1ActionPerformed(evt);
            }
        });
        comboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                comboBox1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 849, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        if (isOtherFramesInvolved && isSelllingRecordInvolved) {
            this.dispose();
            this.sr.setEnabled(true);
            this.sr.setVisible(true);
        } else if (isOtherFramesInvolved) {
            this.dispose();
        } else {
            System.exit(0);
        }

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

    private void textF11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusGained
        // TODO add your handling code here:
        jDateChooser4.setVisible(true);
    }//GEN-LAST:event_textF11FocusGained

    private void textF11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusLost
        // TODO add your handling code here:
        jDateChooser4.setVisible(false);
    }//GEN-LAST:event_textF11FocusLost

    private void textF11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF11ActionPerformed

    private void textF11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF11KeyTyped
        // TODO add your handling code here:
        System.out.println("HEOo");

    }//GEN-LAST:event_textF11KeyTyped

    private void jDateChooser4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser4PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser4.getDate() != null) {
            textF11.setText(sdf.format(jDateChooser4.getDate()).toString());

        }
    }//GEN-LAST:event_jDateChooser4PropertyChange

    private void textF10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusGained
        // TODO add your handling code here:
        //click the jdateChooser to pick the date
        //or show the jDateChooser to pick the date
        jDateChooser3.setVisible(true);

        //jDateChooser3.firePropertyChange(searchText, NORMAL, NORMAL);
        //jDateChooser3.propertyChange((PropertyChangeEvent) jDateChooser3.getPropertyChangeListeners()[0]);
    }//GEN-LAST:event_textF10FocusGained

    private void textF10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusLost
        // TODO add your handling code here:
        jDateChooser3.setVisible(false);
    }//GEN-LAST:event_textF10FocusLost

    private void textF10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF10KeyTyped
        // TODO add your handling code here:
        System.out.println("YET***");

    }//GEN-LAST:event_textF10KeyTyped

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser3.getDate() != null) {
            textF10.setText(sdf.format(jDateChooser3.getDate()).toString());

        }
    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
        // TODO add your handling code here:

        employeeSalary();
        clearEmpSalaryFields();
    }//GEN-LAST:event_customButton1ActionPerformed

    private void comboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox2ActionPerformed
        // TODO add your handling code here:
        if (comboBox2.getSelectedItem() != null) {

        }
    }//GEN-LAST:event_comboBox2ActionPerformed

    private void textF12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF12FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_textF12FocusGained

    private void textF12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textF12FocusLost

    private void textF12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF12ActionPerformed

    private void textF12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF12KeyTyped

    private void textF13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF13FocusGained
        // TODO add your handling code here:
        jDateChooser5.setVisible(true);
    }//GEN-LAST:event_textF13FocusGained

    private void textF13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF13FocusLost
        // TODO add your handling code here:
        jDateChooser5.setVisible(false);
    }//GEN-LAST:event_textF13FocusLost

    private void textF13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF13ActionPerformed

    private void textF13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF13KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF13KeyTyped

    private void jDateChooser5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser5PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser5.getDate() != null) {
            textF13.setText(sdf.format(jDateChooser5.getDate()).toString());

        }
    }//GEN-LAST:event_jDateChooser5PropertyChange

    private void textF14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF14FocusGained
        // TODO add your handling code here:
        jDateChooser6.setVisible(true);
    }//GEN-LAST:event_textF14FocusGained

    private void textF14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF14FocusLost
        // TODO add your handling code here:
        jDateChooser6.setVisible(false);
    }//GEN-LAST:event_textF14FocusLost

    private void textF14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF14KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF14KeyTyped

    private void jDateChooser6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser6PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser6.getDate() != null) {
            textF14.setText(sdf.format(jDateChooser6.getDate()).toString());

        }
    }//GEN-LAST:event_jDateChooser6PropertyChange

    private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
        // TODO add your handling code here:
        salesRecord();
    }//GEN-LAST:event_customButton2ActionPerformed

    private void comboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBox3ActionPerformed

    private void textF15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF15FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_textF15FocusGained

    private void textF15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF15FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textF15FocusLost

    private void textF15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF15ActionPerformed

    private void textF15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF15KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF15KeyTyped

    private void comboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_comboBox1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBox1PropertyChange

    private void comboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox1ActionPerformed
        // TODO add your handling code here:
        if (comboBox1.getSelectedItem() != null) {
            if (comboBox1.getSelectedItem().equals("Emplyee Salary")) {
                PanelRemover.removeP(jPanel4, jPanel2);
            } else if (comboBox1.getSelectedItem().equals("Sales Total")) {
                PanelRemover.removeP(jPanel4, jPanel3);
            } else if (comboBox1.getSelectedItem().equals("Food Receive")) {
                PanelRemover.removeP(jPanel4, jPanel5);
            }
        }
    }//GEN-LAST:event_comboBox1ActionPerformed

    private void jDateChooser7PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser7PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser7.getDate() != null) {
            textF16.setText(sdf.format(jDateChooser7.getDate()).toString());

        }
    }//GEN-LAST:event_jDateChooser7PropertyChange

    private void textF16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF16FocusGained
        // TODO add your handling code here:
        jDateChooser7.setVisible(true);
    }//GEN-LAST:event_textF16FocusGained

    private void textF16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF16FocusLost
        // TODO add your handling code here:
        jDateChooser7.setVisible(false);
    }//GEN-LAST:event_textF16FocusLost

    private void textF16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF16ActionPerformed

    private void textF16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF16KeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_textF16KeyTyped

    private void textF17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF17FocusGained
        // TODO add your handling code here:
        //click the jdateChooser to pick the date
        //or show the jDateChooser to pick the date
        jDateChooser8.setVisible(true);

        //jDateChooser3.firePropertyChange(searchText, NORMAL, NORMAL);
        //jDateChooser3.propertyChange((PropertyChangeEvent) jDateChooser3.getPropertyChangeListeners()[0]);
    }//GEN-LAST:event_textF17FocusGained

    private void textF17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF17FocusLost
        // TODO add your handling code here:
        jDateChooser8.setVisible(false);
    }//GEN-LAST:event_textF17FocusLost

    private void textF17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF17KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_textF17KeyTyped

    private void jDateChooser8PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser8PropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser8.getDate() != null) {

            textF17.setText(sdf.format(jDateChooser8.getDate()).toString());
        }
    }//GEN-LAST:event_jDateChooser8PropertyChange

    private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
        // TODO add your handling code here:
        CreateObject.make(new DealerT(this));
    }//GEN-LAST:event_customButton3ActionPerformed

    private void textF18FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF18FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_textF18FocusGained

    private void textF18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF18FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textF18FocusLost

    private void textF18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF18ActionPerformed

    private void textF18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF18KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF18KeyTyped

    private void textF20FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF20FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_textF20FocusGained

    private void textF20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF20FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textF20FocusLost

    private void textF20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textF20ActionPerformed

    private void textF20KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF20KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textF20KeyTyped

    private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
        // TODO add your handling code here:
        dealerRecord();
    }//GEN-LAST:event_customButton4ActionPerformed

    private void boxLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxLabelMouseClicked
        // TODO add your handling code here:
        CreateObject.make(new Home());
    }//GEN-LAST:event_boxLabelMouseClicked
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
            java.util.logging.Logger.getLogger(AdvancedAnalytics.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdvancedAnalytics.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdvancedAnalytics.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdvancedAnalytics.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

                JFrame jf = new AdvancedAnalytics();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private frameutil.ComboBox comboBox1;
    private frameutil.ComboBox<String> comboBox2;
    private frameutil.ComboBox<String> comboBox3;
    private frameutil.CustomButton customButton1;
    private frameutil.CustomButton customButton2;
    private frameutil.CustomButton customButton3;
    private frameutil.CustomButton customButton4;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private com.toedter.calendar.JDateChooser jDateChooser7;
    private com.toedter.calendar.JDateChooser jDateChooser8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JToggleButton jToggleButton1;
    private frameutil.MenuBar menuBar1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    public frameutil.TextF textF10;
    public frameutil.TextF textF11;
    public frameutil.TextF textF12;
    public frameutil.TextF textF13;
    public frameutil.TextF textF14;
    public frameutil.TextF textF15;
    public frameutil.TextF textF16;
    public frameutil.TextF textF17;
    public frameutil.TextF textF18;
    public frameutil.TextF textF20;
    // End of variables declaration//GEN-END:variables
}
