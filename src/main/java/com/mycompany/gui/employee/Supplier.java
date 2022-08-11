package com.mycompany.gui.employee;


import com.mycompany.frameutil.*;
import com.mycompany.gui.FRNView;
import com.mycompany.util.*;
import com.mycompany.gui.DealerT;
import com.mycompany.gui.FRN;
import com.mycompany.gui.Message;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.mycompany.model.MySql;
import com.mycompany.view.frameutilswingcomponents.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author acer
 */
public class Supplier extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public Supplier() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 7, 7));

        jframeCustmize();
        this.setBackground(MainTheme.mainColor);
        roundedPanel1.setBackground(MainTheme.mainColor);
        roundedPanel2.setBackground(MainTheme.secondColor);

        this.setForeground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.fourthColor);
        loadTable();
        loadCombos();
        setValidadions();
        this.tableListernRag();
        this.thiset = this;
        jPanel5.setBackground(MainTheme.thirdColor);
        textF4.setBackground(MainTheme.secondColor);
        textF5.setBackground(MainTheme.secondColor);
        textF4.setForeground(MainTheme.fourthColor);
        textF5.setForeground(MainTheme.fourthColor);
        jPanel7.setBackground(MainTheme.secondColor);
        textF8.setEditable(false);
        textF7.setEditable(false);
        textF6.setEditable(false);
        menuBar1.foo(this);

    }

    public Supplier(Chef c) {
        this();
        this.chef = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Chef");
        comboBox1.setEnabled(false);
        isChefInvolved = true;
    }

    public Supplier(Manager c) {
        this();
        this.manager = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Manager");
        comboBox1.setEnabled(false);
        isMangerInvolved = true;
    }

    public Supplier(Server c) {
        this();
        this.server = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Server");
        comboBox1.setEnabled(false);
        isServerInvolved = true;
    }

    public Supplier(Cleaner c) {
        this();
        this.cleaner = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Cleaner");
        comboBox1.setEnabled(false);
        isCleanerInvolved = true;
    }

    public Supplier(Bartender c) {
        this();
        this.bartender = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Bartender");
        comboBox1.setEnabled(false);
        isBartenderInvolved = true;
    }

    public Supplier(Cashier c) {
        this();
        this.cashier = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Cashier");
        comboBox1.setEnabled(false);
        isCashierInvolved = true;
    }

    public Supplier(FRN frn) {
        this();
        this.frn = frn;
        this.isUpdateStatus = true;
        
        ArrayList<String> al = new ArrayList<String>();
        al.add("supplier");
        al.add("dealer,supplier");
        al.add("dealer_type,dealer");

        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();

        String sort = "ORDER BY `supplier_name` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(" INNER JOIN `food_item_category`\n"
                + "ON  `food_item_category`.`dealer_type_id` =`dealer_type`.`dealer_type_id` ").toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();
        System.out.println("the query is ");
        System.out.println(query);
        LoadTables lt = new LoadTables(customTable2, query, this.colnames);
        
        //comboBox1.setEnabled(false);
        isFRNInvolved = true;
        otherFramesInvolved = true;
        otherFrame = this;
    }

    public Supplier(FRNView frnView) {
        this();
        frnView.setEnabled(false);
        this.frnView = frnView;
        this.isUpdateStatus = true;

        isFRNViewInvolved = true;
        otherFramesInvolved = true;
        otherFrame = frnView;
    }
    boolean isUpdateStatus = false;
    boolean isChefInvolved = false;
    boolean isMangerInvolved = false;
    boolean isServerInvolved = false;
    boolean isCleanerInvolved = false;
    boolean isBartenderInvolved = false;
    boolean isCashierInvolved = false;
    boolean isFRNInvolved = false;
    boolean isFRNViewInvolved = false;
    boolean otherFramesInvolved = false;
    JFrame otherFrame;
    Supplier thiset;
    Chef chef;
    Manager manager;
    Server server;
    Cleaner cleaner;
    Bartender bartender;
    Cashier cashier;
    FRN frn;
    FRNView frnView;
    String loadTableQuery;
    String[] colnames = {"supplier_id", "supplier_name", "supplier_contact", "dealer_email", "dealer_name", "dealer_contact", "dealer_type_name"};

    private void loadQuery() {
        ArrayList<String> al = new ArrayList<String>();
        al.add("supplier");
        al.add("dealer,supplier");
        al.add("dealer_type,dealer");

        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();
        //System.out.println(this.loadTableQuery);

    }

    private void loadTable() {
        loadQuery();
        String sort = "ORDER BY `supplier_name` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable2, query, this.colnames);
    }

    private void loadCombos() {

        LoadSubTypes.loadType(comboBox1, "dealer_type");
    }
    String searchText;
    String seachcontact;
    String searchemail;

    private void clearFields() {
        JComponent[] jp = {textF1, textF2, textF4, textF5, textF6, textF7, textF8, comboBox1};
        SetEmptyItems.emptyItems(jp);
    }

    public void advancedSearch() {
        String name = textF1.getText();
        boolean isNameInvolved = false;
        String contact = textF2.getText();
        boolean isContactIvolved = false;
        String dealerType = comboBox1.getSelectedItem().toString();
        String sort = comboBox2.getSelectedItem().toString();

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        if (sort.equals("NAME A-Z")) {
            sortQuery = "ORDER BY `supplier`.`supplier_name` ASC";
        } else if (sort.equals("NAME Z-A")) {
            sortQuery = "ORDER BY `supplier`.`supplier_name` DESC";
        }
        if (!name.isEmpty()) {
            v.add("`supplier_name` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (!contact.isEmpty()) {
            v.add("`employee_contact` LIKE '%" + contact + "%' ");
            queriesInvolved = true;
        }
        if (!dealerType.equals("Select dealer_type")) {
            v.add("`dealer_type_name` LIKE '%" + dealerType + "%' ");
            queriesInvolved = true;
        }
        if (queriesInvolved) {
            whereQueryBuilder.append("where ");
        }
        for (int i = 0; i < v.size(); i++) {
            //System.out.println("vectors are " + v.get(i));

            whereQueryBuilder.append("");
            whereQueryBuilder.append(v.get(i));

            if (i != v.size() - 1) {
                whereQueryBuilder.append("AND ");
            }
        }
        //System.out.println("where query is " + whereQueryBuilder);
        stringquerybuild.append(this.loadTableQuery);
        stringquerybuild.append(whereQueryBuilder);
        stringquerybuild.append(sortQuery);
        String query = stringquerybuild.toString();
        //System.out.println("where query is " + whereQueryBuilder);
        LoadTables lt = new LoadTables(customTable2, query, this.colnames);
    }

    public void advancedSearch(String name, String contact) {

        boolean isNameInvolved = false;

        boolean isContactIvolved = false;

        boolean isEmailInvolved = false;
        String sort = comboBox2.getSelectedItem().toString();
        String dealerType = comboBox1.getSelectedItem().toString();

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        if (sort.equals("NAME A-Z")) {
            sortQuery = "ORDER BY `supplier`.`supplier_name` ASC";
        } else if (sort.equals("NAME Z-A")) {
            sortQuery = "ORDER BY `supplier`.`supplier_name` DESC";
        }
        if (!name.isEmpty()) {
            v.add("`supplier_name` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (!contact.isEmpty()) {
            v.add("`supplier_contact` LIKE '%" + contact + "%' ");
            queriesInvolved = true;
        }
        if (!dealerType.equals("Select dealer_type")) {
            v.add("`dealer_type_name` LIKE '%" + dealerType + "%' ");
            queriesInvolved = true;
        }
        if (queriesInvolved) {
            whereQueryBuilder.append("where ");
        }
        for (int i = 0; i < v.size(); i++) {
            //System.out.println("vectors are " + v.get(i));

            whereQueryBuilder.append("");
            whereQueryBuilder.append(v.get(i));

            if (i != v.size() - 1) {
                whereQueryBuilder.append("AND ");
            }
        }
        //System.out.println("where query is " + whereQueryBuilder);
        stringquerybuild.append(this.loadTableQuery);
        stringquerybuild.append(whereQueryBuilder);
        stringquerybuild.append(sortQuery);
        String query = stringquerybuild.toString();
        //System.out.println("where query is " + whereQueryBuilder);
        LoadTables lt = new LoadTables(customTable2, query, this.colnames);

    }

    private void setValidadions() {

        String contactregex = "((([0][7][24-8][0-9]{7})|([0][7][24-8][0-9]*))|([0][7][24-8])|[0][7]|[0])";
        new FilterDocRagRegex(textF5, contactregex, 10);
        new FilterDocRagRegex(textF2, contactregex, 10);
    }

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

    private void clearSearch() {
        ItemListener it = comboBox1.getItemListeners()[0];
        comboBox1.removeItemListener(it);
        loadCombos();
        clearFields();
        loadTable();
        comboBox1.addItemListener(it);
    }

    private void enterDealer() {
        String name = textF4.getText();
        String contact = textF5.getText();
        String dealerId = textF8.getText();
        if (dealerId.isEmpty()) {
            Message m = new Message(this, "Please a enter a supplier name", "Warning");
        } else if (name.equals("")) {
            Message m = new Message(this, "Please a enter a supplier name", "Warning");
        } else if (contact.isEmpty()) {
            Message m = new Message(this, "Please a enter contact number ", "Warning");
        } else {

            ArrayList<String> info = new ArrayList<>();
            info.add(dealerId);
            info.add(contact);
            info.add(name);
            InsertTable it = new InsertTable("supplier", info);
            clearFields();
            loadTable();
        }
    }

    private void editMode() {
        jPanel7.removeAll();
        jPanel7.add(jPanel9);
        jPanel7.repaint();
        jPanel7.revalidate();
        jPanel11.removeAll();
        jPanel11.add(customButton5);
        jPanel11.repaint();
        jPanel11.revalidate();
    }

    private void enterMode() {
        jPanel7.removeAll();
        jPanel7.add(jPanel8);
        jPanel7.repaint();
        jPanel7.revalidate();
        jPanel11.removeAll();
        jPanel11.add(customButton3);
        jPanel11.repaint();
        jPanel11.revalidate();
    }

    public void tableListernRag() {
        customTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = customTable2.getSelectedRow();
                if (row != -1 && isUpdateStatus) {
                    String id = customTable2.getValueAt(row, 0).toString();
                    String name = customTable2.getValueAt(row, 1).toString();
                    String emptype = customTable2.getValueAt(row, 2).toString();
                    String contact = customTable2.getValueAt(row, 2).toString();
                    String email = customTable2.getValueAt(row, 4).toString();
                    String gender = customTable2.getValueAt(row, 5).toString();
                    String dealertype = customTable2.getValueAt(row, 6).toString();
                    String dealerName = customTable2.getValueAt(row, 4).toString();
                    //	String dob = customTable2.getValueAt(row, 9).toString();
                    //	String city = customTable2.getValueAt(row, 6).toString();

                    if (isChefInvolved) {
                        chef.textF1.setText(name);

                        chef.textF3.setText(email);
                        chef.setEnabled(true);
                        chef.empId = id;
                        thiset.dispose();
                    } else if (isMangerInvolved) {
                        manager.textF1.setText(name);

                        manager.textF3.setText(email);
                        manager.setEnabled(true);
                        manager.empId = id;
                        thiset.dispose();
                    } else if (isServerInvolved) {
                        server.textF1.setText(name);

                        server.textF3.setText(email);
                        server.setEnabled(true);
                        server.empId = id;
                        thiset.dispose();
                    } else if (isCleanerInvolved) {
                        cleaner.textF1.setText(name);

                        cleaner.textF3.setText(email);
                        cleaner.setEnabled(true);
                        cleaner.empId = id;
                        thiset.dispose();
                    } else if (isBartenderInvolved) {
                        bartender.textF1.setText(name);

                        bartender.textF3.setText(email);
                        bartender.setEnabled(true);
                        bartender.empId = id;
                        thiset.dispose();
                    } else if (isCashierInvolved) {
                        cashier.textF1.setText(name);

                        cashier.textF3.setText(email);
                        cashier.setEnabled(true);
                        cashier.empId = id;
                        thiset.dispose();
                    } else if (isFRNViewInvolved) {
                        frnView.textF1.setText(name);
                        frnView.textF2.setText(dealerName);
                        frnView.supplierID = id;
                        frnView.setSupplierId(id);
                        frnView.loadBySupplier();
                        frnView.setEnabled(true);

                        thiset.dispose();
                    } else if (isFRNInvolved) {
                        try {
                            //	frn.textF4.setText(id);
                            frn.textF4.setText(id);
                            frn.textF5.setText(name);
                            frn.textF6.setText(dealerName);
                            frn.textF7.setText(dealertype);
                            frn.textF1.setText("");
                            frn.textF2.setText("");

                            frn.customButton2.setEnabled(true);
                            frn.jPanel4.setEnabled(true);
                            String dId = TypeIds.getId("dealer_type", dealertype);
                            String fId = TypeIds.getId("food_item_category", "dealer_type_id", dId);
                            ResultSet rs;
                            try {
                                rs = MySql.sq("SELECT * FROM `food_item_category` WHERE `food_item_category_id`='" + fId + "'");
                                rs.next();
                                String fooItemTypeName = rs.getString("food_item_category_name");
                                frn.textF3.setText(fooItemTypeName);
                                frn.foodItemType = fooItemTypeName;
                                DefaultTableModel dftm = (DefaultTableModel) frn.customTable1.getModel();
                                System.out.println(frn.customTable1.getRowCount());
                                if (frn.customTable1.getRowCount() > 0) {
                                    frn.textF4.setText(id);
                                    frn.textF5.setText(name);
                                    frn.textF6.setText(dealerName);
                                    frn.textF7.setText(dealertype);
                                    frn.textF3.setText(fooItemTypeName);
                                    frn.foodItemType = fooItemTypeName;
                                    frn.textF1.setText("");
                                    frn.textF2.setText("");

                                    frn.textF8.setText("");
                                    frn.textF9.setText("");
                                    frn.textF10.setText("");
                                    frn.textF11.setText("");
                                    dftm.setRowCount(0);
                                }

                                thiset.dispose();

                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {

                        isUpdateStatus = false;
                        thiset.dispose();
                    }

                }
            }

        });

    }

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
        jPanel1 = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        miniLabel = new javax.swing.JLabel();
        boxLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        customTable2 = new CustomTable();
        jPanel3 = new javax.swing.JPanel();
        customButton1 = new CustomButton();
        customButton6 = new CustomButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        textF8 = new TextF();;
        textF7 = new TextF();;
        textF6 = new TextF();;
        jLabel14 = new javax.swing.JLabel();
        customButton2 = new CustomButton();
        jLabel16 = new javax.swing.JLabel();
        textF4 = new TextF();;
        jLabel15 = new javax.swing.JLabel();
        textF5 = new TextF();;
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        textF1 = new TextF();;
        textF2 = new TextF();;
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        customButton4 = new CustomButton();
        comboBox2 = new ComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboBox1 = new ComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        customButton3 = new CustomButton();
        customButton5 = new CustomButton();
        menuBar1 = new MenuBar();

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

        customTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sup_ID", "Sup_Name", "Sup_Contact", "Dea_Email", "Dea_Name", "Dea_Contact", "Dea_Type"
            }
        ));
        customTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(customTable2);
        if (customTable2.getColumnModel().getColumnCount() > 0) {
            customTable2.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.CardLayout());

        customButton1.setText("Select Delaer");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(customButton1, "card2");

        customButton6.setText("Change");
        customButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(customButton6, "card3");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        jPanel7.setLayout(new java.awt.CardLayout());

        jPanel8.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Dealer ID");

        textF8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Email");

        customButton2.setText("Enter Supplier");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Supplier Name");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Suppleir Contact");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Dealer Name");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF5, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textF8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textF6, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(416, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textF8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, "card2");

        jPanel9.setOpaque(false);

        textF1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF1KeyTyped(evt);
            }
        });

        textF2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF2KeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contact");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.CardLayout());

        customButton4.setText("Clear");
        customButton4.setStyle(CustomButton.ButtonStyle.SECONDARY);
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(customButton4, "card2");

        comboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAME A-Z", "NAME Z-A" }));
        comboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox2ItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Employee");

        comboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(486, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel9, "card3");

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.CardLayout());

        customButton3.setText("Search Mode");
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
            }
        });
        jPanel11.add(customButton3, "card2");

        customButton5.setText("Enter Mode");
        customButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton5ActionPerformed(evt);
            }
        });
        jPanel11.add(customButton5, "card3");

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(menuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        if (otherFramesInvolved) {
            System.out.println("Gonna close");
          //  this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            otherFrame.setEnabled(true);
            thiset.dispose();
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
    ComboBox<String> cb;
        private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
            // TODO add your handling code here:
            //loadCombos();
            clearSearch();

            //EmployeeSalary et = new EmployeeSalary();
            //.setVisible(true);
            //this.dispose();

        }//GEN-LAST:event_customButton4ActionPerformed

        private void textF1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF1KeyTyped
            // TODO add your handling code here:
            String name = textF1.getText() + evt.getKeyChar();
            String contact = textF2.getText();

            advancedSearch(name, contact);
        }//GEN-LAST:event_textF1KeyTyped

        private void textF2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF2KeyTyped
            // TODO add your handling code here:
            String name = textF1.getText();
            String contact = textF2.getText() + evt.getKeyChar();

            advancedSearch(name, contact);
        }//GEN-LAST:event_textF2KeyTyped

        private void comboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox2ItemStateChanged
            // TODO add your handling code here:
            advancedSearch();

        }//GEN-LAST:event_comboBox2ItemStateChanged

        private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
            // TODO add your handling code here:
            CreateObject.make(new DealerT(this));
            clearSearch();
            enterMode();
        }//GEN-LAST:event_customButton1ActionPerformed

        private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
            // TODO add your handling code here:
            enterDealer();

        }//GEN-LAST:event_customButton2ActionPerformed

        private void textF8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF8ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_textF8ActionPerformed

        private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
            // TODO add your handling code here:
            editMode();
        }//GEN-LAST:event_customButton3ActionPerformed

        private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed
            // TODO add your handling code here:
            enterMode();

        }//GEN-LAST:event_customButton5ActionPerformed

        private void comboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox1ItemStateChanged
            // TODO add your handling code here:
            advancedSearch();
        }//GEN-LAST:event_comboBox1ItemStateChanged

    private void customButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customButton6ActionPerformed
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
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

                JFrame jf = new Supplier();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;
    private CustomButton customButton1;
    private CustomButton customButton2;
    private CustomButton customButton3;
    private CustomButton customButton4;
    private CustomButton customButton5;
    private CustomButton customButton6;
    private CustomTable customTable2;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private MenuBar menuBar1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private TextF textF1;
    private TextF textF2;
    private TextF textF4;
    private TextF textF5;
    public TextF textF6;
    public TextF textF7;
    public TextF textF8;
    // End of variables declaration//GEN-END:variables
}
