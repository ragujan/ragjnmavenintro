package com.mycompany.view.gui.employee;


import com.mycompany.view.gui.EmployeeNavi;
import com.mycompany.util.*;
import com.mycompany.view.gui.FRN;
import com.mycompany.view.gui.Message;

import java.awt.event.ItemListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import com.mycompany.model.MySql;
import com.mycompany.view.components.*;
import com.mycompany.view.frameutil.MainTheme;
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
public class EmployeeSalary extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public EmployeeSalary() {
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
      
        textF4.setBackground(MainTheme.secondColor);
        textF5.setBackground(MainTheme.secondColor);
        textF4.setForeground(MainTheme.fourthColor);
        textF5.setForeground(MainTheme.fourthColor);
        jPanel7.setBackground(MainTheme.secondColor);
        jDateChooser1.setVisible(false);
        textF12.setEditable(false);
        textF12.setBackground(MainTheme.secondColor);
        jDateChooser2.setVisible(false);
        jDateChooser1.setBackground(MainTheme.secondColor);
        jDateChooser2.setBackground(MainTheme.secondColor);
                employeeMenuBar1.foo(this);

    }

    public EmployeeSalary(Chef c) {
        this();
        this.chef = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Chef");
        comboBox1.setEnabled(false);
        isChefInvolved = true;
    }

    public EmployeeSalary(Manager c) {
        this();
        this.manager = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Manager");
        comboBox1.setEnabled(false);
        isMangerInvolved = true;
    }

    public EmployeeSalary(Server c) {
        this();
        this.server = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Server");
        comboBox1.setEnabled(false);
        isServerInvolved = true;
    }

    public EmployeeSalary(Cleaner c) {
        this();
        this.cleaner = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Cleaner");
        comboBox1.setEnabled(false);
        isCleanerInvolved = true;
    }

    public EmployeeSalary(Bartender c) {
        this();
        this.bartender = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Bartender");
        comboBox1.setEnabled(false);
        isBartenderInvolved = true;
    }

    public EmployeeSalary(Cashier c) {
        this();
        this.cashier = c;
        this.isUpdateStatus = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.setSelectedItem("Cashier");
        comboBox1.setEnabled(false);
        isCashierInvolved = true;
    }

    public EmployeeSalary(EmployeeNavi c) {
        this();

    }
    String loadTableQuery;
    String[] colnames = {"employee_id", "employee_name", "employee_type_name", "employee_email", "paid_amount", "bonus", "paid_date"};
    boolean isUpdateStatus = false;
    boolean isChefInvolved = false;
    boolean isMangerInvolved = false;
    boolean isServerInvolved = false;
    boolean isCleanerInvolved = false;
    boolean isBartenderInvolved = false;
    boolean isCashierInvolved = false;
    EmployeeSalary thiset;
    Chef chef;
    Manager manager;
    Server server;
    Cleaner cleaner;
    Bartender bartender;
    Cashier cashier;
    public String managerType;
    public String empName;
    public String empType;
    public String empSubType;

    String searchText;
    String seachcontact;
    String searchemail;

    private void loadQuery() {
        ArrayList<String> al = new ArrayList<String>();
        al.add("employee_salary");
        al.add("employee,employee_salary");

        al.add("employee_type,employee");
        al.add("gender,employee");
        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();
        //System.out.println(this.loadTableQuery);

    }

    private void loadCombos() {

        LoadSubTypes.loadType(comboBox1, "employee_type");
    }

    private void loadTable() {
        loadQuery();
        String sort = "ORDER BY `employee_name` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable2, query, this.colnames);
    }

    private void clearFields() {
        JComponent[] jp = {textF1, textF2, textF3, textF10, textF11, textF4, textF5, textF6, textF7, textF8, textF9, comboBox1};
        SetEmptyItems.emptyItems(jp);
    }

    public void advancedSearch() {
        String name = textF1.getText();
        boolean isNameInvolved = false;
        String contact = textF2.getText();
        boolean isContactIvolved = false;
        String email = textF3.getText();
        boolean isEmailInvolved = false;
        String sort = comboBox2.getSelectedItem().toString();
        String emptype = comboBox1.getSelectedItem().toString();
        String fromDate = textF10.getText();
        String toDate = textF11.getText();
        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        if (sort.equals("NAME A-Z")) {
            sortQuery = "ORDER BY `employee`.`employee_name` ASC";
        } else if (sort.equals("NAME Z-A")) {
            sortQuery = "ORDER BY `employee`.`employee_name` DESC";
        } else if (sort.equals("EMAIL A-Z")) {
            sortQuery = "ORDER BY `employee`.`employee_email` ASC";
        } else if (sort.equals("EMAIL Z-A")) {
            sortQuery = "ORDER BY `employee`.`employee_email` DESC";
        } else if (sort.equals("GENDER M")) {
            sortQuery = "AND `gender_name`='Male'";
        } else if (sort.equals("GENDER F")) {
            sortQuery = "AND `gender_name`='Female'";
        }
        if (!name.isEmpty()) {
            v.add("`employee_name` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (!contact.isEmpty()) {
            v.add("`employee_contact` LIKE '%" + contact + "%' ");
            queriesInvolved = true;
        }

        if (!email.isEmpty()) {
            v.add("`employee_email` LIKE '%" + email + "%' ");
            queriesInvolved = true;
        }
        if (!emptype.equals("Select employee_type")) {
            v.add("`employee_type_name` = '" + emptype + "' ");
            queriesInvolved = true;
        }
        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            String fromTime = fromDate + " 00:00:00";
            String toTime = toDate + " 00:00:00";
            v.add("`employee_salary`.`paid_date` BETWEEN '" + fromTime + "' AND '" + toTime + "' ");
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

    public void advancedSearch(String name, String contact, String email) {

        boolean isNameInvolved = false;

        boolean isContactIvolved = false;

        boolean isEmailInvolved = false;
        String sort = comboBox2.getSelectedItem().toString();
        String emptype = comboBox1.getSelectedItem().toString();

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        if (sort.equals("NAME A-Z")) {
            sortQuery = "ORDER BY `employee`.`employee_name` ASC";
        } else if (sort.equals("NAME Z-A")) {
            sortQuery = "ORDER BY `employee`.`employee_name` DESC";
        } else if (sort.equals("EMAIL A-Z")) {
            sortQuery = "ORDER BY `employee`.`employee_email` ASC";
        } else if (sort.equals("EMAIL Z-A")) {
            sortQuery = "ORDER BY `employee`.`employee_email` DESC";
        } else if (sort.equals("GENDER M")) {
            sortQuery = "AND `gender_name`='Male'";
        } else if (sort.equals("GENDER F")) {
            sortQuery = "AND `gender_name`='Female'";
        }
        if (!name.isEmpty()) {
            v.add("`employee_name` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (!contact.isEmpty()) {
            v.add("`employee_contact` LIKE '%" + contact + "%' ");
            queriesInvolved = true;
        }

        if (!email.isEmpty()) {
            v.add("`employee_email` LIKE '%" + email + "%' ");
            queriesInvolved = true;
        }
        if (!emptype.equals("Select employee_type")) {
            v.add("`employee_type_name` = '" + emptype + "' ");
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
        String priceregex = "^\\d*([,]\\d*)*([.]\\d*)?";
        FilterDocRagRegex bonus = new FilterDocRagRegex(textF5, priceregex);

        FilterDocRagRegex amount = new FilterDocRagRegex(textF4, priceregex);
        String contactregex = "((([0][7][24-8][0-9]{7})|([0][7][24-8][0-9]*))|([0][7][24-8])|[0][7]|[0])";
        FilterDocRagRegex contact = new FilterDocRagRegex(textF2, contactregex, 10);
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

    private void enterSalary() {
        String name = textF5.getText();
        String amount = textF4.getText();
        String bonus = textF5.getText();
        String empId = textF8.getText();
        String uniqueId = Long.toString(System.currentTimeMillis());
        if (name.equals("none")) {
            Message m = new Message(this, "Please a Select an Employee", "Warning");
        } else if (amount.isEmpty()) {
            Message m = new Message(this, "Please a enter amount", "Warning");
        } else if (bonus.isEmpty()) {
            Message m = new Message(this, "Please a enter bonus", "Warning");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            ArrayList<String> info = new ArrayList<>();
            info.add(bonus);
            info.add(empId);
            info.add(amount);
            info.add(date);
            info.add(uniqueId);
            InsertTable it = new InsertTable("employee_salary", info);

            Vector<String> v = new Vector<String>();
            v.add(uniqueId);
            v.add(date);
            v.add(empId);
            v.add(empName);
            v.add(empType);

            v.add(amount);
            v.add(bonus);
            printGRN(customTable2, v);
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
                    String contact = customTable2.getValueAt(row, 3).toString();
                    String email = customTable2.getValueAt(row, 4).toString();
                    String gender = customTable2.getValueAt(row, 7).toString();
                    String dob = customTable2.getValueAt(row, 9).toString();
                    String city = customTable2.getValueAt(row, 6).toString();
                    ArrayList<String> al = new ArrayList<String>();
                    al.add("employee");
                    al.add("address,employee");
                    al.add("city,address");

                    SearchTable st = new SearchTable(al);

                    String whereQuery = st.getTableQuery() + " WHERE `employee_id`='" + id + "'";
                    try {
                        System.out.println(whereQuery);
                        ResultSet rs = MySql.sq(whereQuery);
                        rs.next();
                        String add1 = rs.getString("street_1");
                        String add2 = rs.getString("street_2");
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("id", id);
                        hm.put("name", name);
                        hm.put("type", emptype);
                        hm.put("contact", contact);
                        hm.put("email", email);
                        hm.put("add1", add1);
                        hm.put("add2", add2);
                        hm.put("city", city);
                        hm.put("gender", gender);
                        hm.put("dob", dob);
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
                        } else {

                            isUpdateStatus = false;
                            thiset.dispose();
                        }

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(EmployeeSalary.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeSalary.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

    }

    private void printGRN(JTable jt, Vector<String> v) {

        try {
            TableModel tm1 = jt.getModel();
            //String jasperPath = "src//reportXML//customerOrder.jrxml";
            InputStream jasperPathStream = getClass().getResourceAsStream("/reportxml/employeeSalary.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(jasperPathStream);
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("UniqueID", v.get(0).toString());
            hm.put("Time", v.get(1).toString());
            hm.put("EmployeeID", v.get(2).toString());
            hm.put("Employee", v.get(3).toString());
            hm.put("EmpType", v.get(4).toString());
            hm.put("PaidAmount", v.get(5).toString());
            hm.put("Bonus", v.get(6).toString());
            TableModel tm = jt.getModel();
            JRTableModelDataSource jrtmds = new JRTableModelDataSource(tm);
            //   JasperPrint jp = JasperFillManager.fillReport(jr, hm,jrtmds);
            JREmptyDataSource jreds = new JREmptyDataSource();
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, jreds);
            //     JasperViewer.viewReport(jp, false);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(FRN.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textF9 = new TextF();;
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
        textF12 = new TextF();;
        jLabel17 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        textF1 = new TextF();;
        textF2 = new TextF();;
        textF3 = new TextF();;
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        customButton4 = new CustomButton();
        comboBox2 = new ComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        textF10 = new TextF();;
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textF11 = new TextF();;
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        comboBox1 = new ComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        customButton3 = new CustomButton();
        customButton5 = new CustomButton();
        employeeMenuBar1 = new EmployeeMenuBar();

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
                "E_Id", "Name", "E_Type", "Email", "Amount", "Bonus", "Date"
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.CardLayout());

        customButton1.setText("Select Employee");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(customButton1, "card2");

        jPanel7.setLayout(new java.awt.CardLayout());

        jPanel8.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Type");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ID");

        textF8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Email");

        customButton2.setText("Enter Salary");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Amount");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Bonus");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Name");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Working Hours");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(textF6, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(textF8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(textF7, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(textF9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textF5, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jLabel15)
                    .addComponent(textF4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textF8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textF7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        textF3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF3KeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email");

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

        comboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAME A-Z", "NAME Z-A", "EMAIL A-Z", "EMAIL Z-A", "GENDER M", "GENDER F" }));
        comboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox2ItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Employee");

        textF10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF10FocusLost(evt);
            }
        });
        textF10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textF10MouseClicked(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setIcon(null);
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("From");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("To");

        textF11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF11FocusLost(evt);
            }
        });
        textF11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textF11MouseClicked(evt);
            }
        });

        jDateChooser2.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser2.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser2.setIcon(null);
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

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
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(112, 112, 112)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(185, 185, 185))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeMenuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(employeeMenuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_roundedPanel2MousePressed

    private void roundedPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MouseDragged

        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - x, yy - y);
    }//GEN-LAST:event_roundedPanel2MouseDragged

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked

        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void closeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseEntered

        closeLabel.setOpaque(true);
        closeLabel.setBackground(MainTheme.mainColor);
    }//GEN-LAST:event_closeLabelMouseEntered

    private void closeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseExited

        closeLabel.setBackground(MainTheme.secondColor);
        closeLabel.setOpaque(false);

    }//GEN-LAST:event_closeLabelMouseExited

    private void miniLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniLabelMouseEntered

        miniLabel.setOpaque(true);
        miniLabel.setBackground(MainTheme.mainColor);
    }//GEN-LAST:event_miniLabelMouseEntered

    private void miniLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniLabelMouseExited


        miniLabel.setBackground(MainTheme.secondColor);
        miniLabel.setOpaque(false);
    }//GEN-LAST:event_miniLabelMouseExited
    ComboBox<String> cb;
        private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
    
            //loadCombos();
            clearSearch();

            //EmployeeSalary et = new EmployeeSalary();
            //.setVisible(true);
            //this.dispose();

        }//GEN-LAST:event_customButton4ActionPerformed

        private void textF1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF1KeyTyped
    
            String name = textF1.getText() + evt.getKeyChar();
            String contact = textF2.getText();
            String email = textF3.getText();
            advancedSearch(name, contact, email);
        }//GEN-LAST:event_textF1KeyTyped

        private void textF2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF2KeyTyped
    
            String name = textF1.getText();
            String contact = textF2.getText() + evt.getKeyChar();
            String email = textF3.getText();
            advancedSearch(name, contact, email);
        }//GEN-LAST:event_textF2KeyTyped

        private void textF3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF3KeyTyped
    
            String name = textF1.getText();
            String contact = textF2.getText();
            String email = textF3.getText() + evt.getKeyChar();
            advancedSearch(name, contact, email);
        }//GEN-LAST:event_textF3KeyTyped

        private void comboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox2ItemStateChanged
    
            advancedSearch();

        }//GEN-LAST:event_comboBox2ItemStateChanged

        private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
    
            CreateObject.make(new EmployeeT(this));
            clearSearch();
            enterMode();
        }//GEN-LAST:event_customButton1ActionPerformed

        private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
    
            enterSalary();

        }//GEN-LAST:event_customButton2ActionPerformed

        private void textF8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF8ActionPerformed
    
        }//GEN-LAST:event_textF8ActionPerformed

        private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
    
            editMode();
        }//GEN-LAST:event_customButton3ActionPerformed

        private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed
    
            enterMode();

        }//GEN-LAST:event_customButton5ActionPerformed

        private void textF10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusGained
    
            jDateChooser1.setVisible(true);
        }//GEN-LAST:event_textF10FocusGained

        private void textF10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusLost
    
            jDateChooser1.setVisible(false);
        }//GEN-LAST:event_textF10FocusLost

        private void textF10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textF10MouseClicked
    
        }//GEN-LAST:event_textF10MouseClicked

        private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            if (jDateChooser1.getDate() != null) {

                textF10.setText(sdf.format(jDateChooser1.getDate()).toString());
                advancedSearch();

            }
        }//GEN-LAST:event_jDateChooser1PropertyChange

        private void textF11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusGained
    
            jDateChooser2.setVisible(true);
        }//GEN-LAST:event_textF11FocusGained

        private void textF11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusLost
    
            jDateChooser2.setVisible(false);
        }//GEN-LAST:event_textF11FocusLost

        private void textF11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textF11MouseClicked
    
        }//GEN-LAST:event_textF11MouseClicked

        private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            if (jDateChooser2.getDate() != null) {

                textF11.setText(sdf.format(jDateChooser2.getDate()).toString());
                advancedSearch();

            }
        }//GEN-LAST:event_jDateChooser2PropertyChange

        private void comboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox1ItemStateChanged
    
            advancedSearch();
        }//GEN-LAST:event_comboBox1ItemStateChanged
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
            java.util.logging.Logger.getLogger(EmployeeSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

                JFrame jf = new EmployeeSalary();
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
    private CustomTable customTable2;
    private EmployeeMenuBar employeeMenuBar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private TextF textF1;
    private TextF textF10;
    private TextF textF11;
    public TextF textF12;
    private TextF textF2;
    private TextF textF3;
    private TextF textF4;
    private TextF textF5;
    public TextF textF6;
    public TextF textF7;
    public TextF textF8;
    public TextF textF9;
    // End of variables declaration//GEN-END:variables
}
