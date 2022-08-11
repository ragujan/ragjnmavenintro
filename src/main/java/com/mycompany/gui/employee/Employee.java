package com.mycompany.gui.employee;

import com.mycompany.frameutil.*;
import com.mycompany.util.*;
import com.mycompany.gui.Message;
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
import com.mycompany.view.frameutilswingcomponents.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author acer
 */
public class Employee extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public Employee() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 7, 7));

        jframeCustmize();
        this.setBackground(MainTheme.mainColor);
        roundedPanel1.setBackground(MainTheme.mainColor);
        roundedPanel2.setBackground(MainTheme.secondColor);

        comboBox1.setBackground(MainTheme.thirdColor);
        comboBox2.setBackground(MainTheme.thirdColor);

        comboBox4.setBackground(MainTheme.thirdColor);

        textF1.setBackground(MainTheme.mainColor);
        textF2.setBackground(MainTheme.mainColor);
        textF3.setBackground(MainTheme.mainColor);
        textF4.setBackground(MainTheme.mainColor);
        jDateChooser1.setBackground(MainTheme.secondColor);
        jDateChooser1.setForeground(MainTheme.secondColor);
        jDateChooser1.setVisible(false);
        this.setForeground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.secondColor);
        setDocFilters();
        loadCombos();

        employeeMenuBar1.foo(this);
    }

    public Employee(EmployeeT et, HashMap<String, String> hm) {
        this();
        isOtherFramesInvolved = true;
        this.updateId = hm.get("id");
        textF1.setText(hm.get("name"));
        textF2.setText(hm.get("contact"));
        textF3.setText(hm.get("email"));
        textF4.setText(hm.get("dob"));
        textF5.setText(hm.get("add1"));
        textF6.setText(hm.get("add2"));

        comboBox1.setSelectedItem(hm.get("gender"));
        comboBox4.setSelectedItem(hm.get("city"));
        comboBox2.setSelectedItem(hm.get("type"));
        jPanel3.removeAll();
        jPanel3.add(customButton4);
        jPanel3.repaint();
        jPanel3.revalidate();

    }

    public Employee(EmployeeT et) {
        this();
        isOtherFramesInvolved = true;
        isEmployeeTInvolved = true;
        et.setVisible(false);
        et.setEnabled(false);
        emplyeet = et;
    }

    public Employee(Chef c) {
        this();

    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String updateId;
    boolean isOtherFramesInvolved = false;
    boolean isEmployeeTInvolved = false;
    EmployeeT emplyeet;

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
        LoadSubTypes.loadType(comboBox1, "gender");
        LoadSubTypes.loadType(comboBox2, "employee_type");
        LoadSubTypes.loadType(comboBox4, "city");
    }

    private void setDocFilters() {
        String nameregex = "(([A-Z])([a-z])* ([A-Z])([a-z])*|([A-Z])([a-z])* |([A-Z])([a-z])*)";
        FilterDocRagRegex name = new FilterDocRagRegex(textF1, nameregex);

        String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
        FilterDocRagRegex dob = new FilterDocRagRegex(textF4, dobRegex);

        String contactregex = "((([0][7][24-8][0-9]{7})|([0][7][24-8][0-9]*))|([0][7][24-8])|[0][7]|[0])";
        FilterDocRagRegex contact = new FilterDocRagRegex(textF2, contactregex, 10);
    }

    private void contactCheck(String contact) {

        ResultSet rs;
        try {
            rs = MySql.sq("SELECT * FROM `employee` WHERE `employee_contact`='" + contact + "' ");
            if (rs.next()) {
                Message m = new Message(this, "this contact is already exits ", "warning");
                textF2.setText("");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void emailCheck(String email) {

        ResultSet rs;
        try {
            rs = MySql.sq("SELECT * FROM `employee` WHERE `employee_email`='" + email + "' ");
            if (rs.next()) {
                Message m = new Message(this, "this email is already exits ", "warning");
                textF3.setText("");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void insertEmployee() {
        String name = textF1.getText();
        String contact = textF2.getText();
        String email = textF3.getText();
        String add1 = textF5.getText();
        String add2 = textF6.getText();
        String dob = textF4.getText();
        String city = comboBox4.getSelectedItem().toString();
        String gender = comboBox1.getSelectedItem().toString();
        String emptype = comboBox2.getSelectedItem().toString();

        if (name.isEmpty()) {
            //JOP.setJOPMessage(this, "name field is empty", "warning", 1);
            Message m = new Message(this, "name field is empty", "warning");
        } else if (contact.isEmpty()) {

            Message m = new Message(this, "contact field is empty", "warning");
        } else if (email.isEmpty()) {

            Message m = new Message(this, "email field is empty", "warning");
        } else if (!BasicValidator.email(email)) {

            Message m = new Message(this, "email is not a valid one", "warning");
        } else if (dob.isEmpty()) {

            Message m = new Message(this, "dob field is empty", "warning");
        } else if (city.equals("Select city")) {

            Message m = new Message(this, "please select a valid city", "warning");
        } else if (gender.equals("Select gender")) {

            Message m = new Message(this, "please select a valid gender", "warning");
        } else if (add1.isEmpty()) {

            Message m = new Message(this, "address field is empty", "warning");
        } else if (emptype.equals("Select employee_type")) {

            Message m = new Message(this, "Select a valid employee type", "warning");
        } else {
            try {
                ResultSet rs = MySql.sq("SELECT * FROM `employee` WHERE `employee_contact`='" + contact + "' OR `employee_email`='" + email + "'");
                if (rs.next()) {
                    Message m = new Message(this, "address field is empty", "warning");
                } else {

                    rs = MySql.sq("SELECT * FROM `city` WHERE `city_name`='" + city + "'");
                    rs.next();
                    String cityid = rs.getString("city_id");
                    ArrayList<String> info = new ArrayList<>();
                    info.add(cityid);
                    info.add(add1);
                    info.add(add2);
                    InsertTable it = new InsertTable("address", info);
                    rs = MySql.sq("SELECT * FROM	address	 WHERE `street_1` ='" + add1 + "' AND `street_2`='" + add2 + "' AND `city_id`='" + cityid + "'");
                    rs.next();
                    String addId = rs.getString("address_id");

                    //today date
                    String today = sdf.format(new Date());
                    //emp type id
                    rs = MySql.sq("SELECT * FROM `employee_type` WHERE `employee_type_name`='" + emptype + "'");
                    rs.next();
                    String employeeTypeId = rs.getString("employee_type_id");
                    //gender id 
                    rs = MySql.sq("SELECT * FROM `gender` WHERE `gender_name`='" + gender + "'");
                    rs.next();
                    String genderId = rs.getString("gender_id");
                    info.clear();
                    info.add(addId);
                    info.add(dob);
                    info.add(contact);
                    info.add(email);
                    info.add(name);
                    info.add(employeeTypeId);
                    info.add(genderId);
                    info.add(today);
                    it = new InsertTable("employee", info);
                    JComponent[] jp = {textF1, textF2, textF3, textF4, textF5, textF6, comboBox1, comboBox2, comboBox4};
                    SetEmptyItems.emptyItems(jp);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void updateEmployee() {
        String name = textF1.getText();
        String contact = textF2.getText();
        String email = textF3.getText();
        String add1 = textF5.getText();
        String add2 = textF6.getText();
        String dob = textF4.getText();
        String city = comboBox4.getSelectedItem().toString();
        String gender = comboBox1.getSelectedItem().toString();
        String emptype = comboBox2.getSelectedItem().toString();

        if (name.isEmpty()) {
            //JOP.setJOPMessage(this, "name field is empty", "warning", 1);
            Message m = new Message(this, "name field is empty", "warning");
        } else if (contact.isEmpty()) {

            Message m = new Message(this, "contact field is empty", "warning");
        } else if (email.isEmpty()) {

            Message m = new Message(this, "email field is empty", "warning");
        } else if (!BasicValidator.email(email)) {

            Message m = new Message(this, "email is not a valid one", "warning");
        } else if (dob.isEmpty()) {

            Message m = new Message(this, "dob field is empty", "warning");
        } else if (city.equals("Select city")) {

            Message m = new Message(this, "please select a valid city", "warning");
        } else if (gender.equals("Select gender")) {

            Message m = new Message(this, "please select a valid gender", "warning");
        } else if (add1.isEmpty()) {

            Message m = new Message(this, "address field is empty", "warning");
        } else if (emptype.equals("Select employee_type")) {

            Message m = new Message(this, "Select a valid employee type", "warning");
        } else {
            ResultSet rs;
            try {
                rs = MySql.sq("SELECT * FROM `employee` WHERE `employee_id`='" + updateId + "'");
                rs.next();
                String address_id = rs.getString("address_id");
                String addressUp = "UPDATE `address` set `street_1`='" + add1 + "',`street_2`='" + add2 + "' WHERE `address_id`='" + address_id + "'";
                MySql.iud(addressUp);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            }

            String update = "UPDATE `employee` SET `DOB`='" + dob + "',`employee_name`='" + name + "' WHERE `employee_id`='" + this.updateId + "' ";
            MySql.iud(update);
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

        jToggleButton1 = new javax.swing.JToggleButton();
        roundedPanel1 = new RoundedPanel();
        roundedPanel2 = new RoundedPanel();
        jPanel1 = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        miniLabel = new javax.swing.JLabel();
        boxLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textF1 = new TextF();;
        textF2 = new TextF();;
        textF3 = new TextF();;
        textF5 = new TextF();;
        textF6 = new TextF();;
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboBox4 = new ComboBox<String>();
        jLabel10 = new javax.swing.JLabel();
        textF4 = new TextF();;
        jLabel5 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        comboBox1 = new ComboBox<String>();
        jLabel6 = new javax.swing.JLabel();
        comboBox2 = new ComboBox<String>();
        jLabel7 = new javax.swing.JLabel();
        customButton3 = new CustomButton();
        jPanel3 = new javax.swing.JPanel();
        customButton1 = new CustomButton();
        customButton4 = new CustomButton();
        employeeMenuBar1 = new EmployeeMenuBar();

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

        jPanel2.setBackground(new java.awt.Color(204, 204, 0));
        jPanel2.setForeground(new java.awt.Color(0, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contact");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email");

        textF2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF2FocusLost(evt);
            }
        });
        textF2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF2KeyTyped(evt);
            }
        });

        textF3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF3FocusLost(evt);
            }
        });
        textF3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF3KeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Add_2");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Add_1");

        comboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox4ItemStateChanged(evt);
            }
        });
        comboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("City");

        textF4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF4FocusLost(evt);
            }
        });
        textF4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textF4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DOB");

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
        jLabel6.setText("Gender");

        comboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox2ItemStateChanged(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Employee Type");

        customButton3.setText("View Table");
        customButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
            }
        });

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.CardLayout());

        customButton1.setBackground(new java.awt.Color(204, 153, 0));
        customButton1.setText("Enter ");
        customButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(customButton1, "card2");

        customButton4.setText("Update");
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(customButton4, "card3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(textF6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBox4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(textF5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textF4, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(8, 8, 8)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeeMenuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeMenuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if (isOtherFramesInvolved) {
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

        private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
            // TODO add your handling code here:
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            if (jDateChooser1.getDate() != null) {
                SimpleDateFormat years = new SimpleDateFormat("yyyyMMdd");
                int todayinInt = Integer.parseInt(years.format(new Date()));
                int bdayinInt = Integer.parseInt(years.format(jDateChooser1.getDate()));
                int dateDiff = (todayinInt - bdayinInt) / 10000;
                System.out.println("difference is " + dateDiff);
                if (!(dateDiff < 14)) {
                    String selectedDate = sdf.format(jDateChooser1.getDate());
                    textF4.setText(sdf.format(jDateChooser1.getDate()).toString());

                } else {
                    jDateChooser1.setDate(null);
                    Message m = new Message(this, "Should be atleast age 20", "Warning");
                    //JOP.setJOPMessage(this, "Should be atleast age 20", "Warning", 1);
                }

            }

        }//GEN-LAST:event_jDateChooser1PropertyChange
    boolean emailFieldEntred = false;
        private void comboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox2ItemStateChanged
            // TODO add your handling code here:


        }//GEN-LAST:event_comboBox2ItemStateChanged

        private void comboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox4ItemStateChanged
            // TODO add your handling code here:
        }//GEN-LAST:event_comboBox4ItemStateChanged

        private void comboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox4ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_comboBox4ActionPerformed

        private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
            // TODO add your handling code here:
            insertEmployee();

        }//GEN-LAST:event_customButton1ActionPerformed

        private void textF4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textF4MouseClicked
            // TODO add your handling code here:

        }//GEN-LAST:event_textF4MouseClicked

        private void textF4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF4FocusGained
            // TODO add your handling code here:
            jDateChooser1.setVisible(true);

        }//GEN-LAST:event_textF4FocusGained

        private void textF4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF4FocusLost
            // TODO add your handling code here:
            jDateChooser1.setVisible(false);
        }//GEN-LAST:event_textF4FocusLost

        private void textF2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF2FocusGained
            // TODO add your handling code here:


        }//GEN-LAST:event_textF2FocusGained

        private void textF2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF2FocusLost
            // TODO add your handling code here:
            contactCheck(textF2.getText());
        }//GEN-LAST:event_textF2FocusLost

        private void textF2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF2KeyTyped
            // TODO add your handling code here:
            String contact = textF2.getText() + evt.getKeyChar();
            contactCheck(contact);
        }//GEN-LAST:event_textF2KeyTyped

        private void textF3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF3FocusLost
            // TODO add your handling code here:
            emailCheck(textF3.getText());
        }//GEN-LAST:event_textF3FocusLost

        private void textF3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF3KeyTyped
            // TODO add your handling code here:
            String email = textF3.getText() + evt.getKeyChar();
            emailCheck(email);
        }//GEN-LAST:event_textF3KeyTyped

        private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
            // TODO add your handling code here:
            if (isEmployeeTInvolved) {
                emplyeet.setEnabled(true);
                emplyeet.setVisible(true);
                emplyeet.loadTable();
                this.dispose();
            } else {
                new EmployeeT();
                this.dispose();
            }

        }//GEN-LAST:event_customButton3ActionPerformed

        private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
            // TODO add your handling code here:
            updateEmployee();
        }//GEN-LAST:event_customButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Employee.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                JFrame jf = new Employee();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;
    private ComboBox<String> comboBox4;
    private CustomButton customButton1;
    private CustomButton customButton3;
    private CustomButton customButton4;
    private EmployeeMenuBar employeeMenuBar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private TextF textF1;
    private TextF textF2;
    private TextF textF3;
    private TextF textF4;
    private TextF textF5;
    private TextF textF6;
    // End of variables declaration//GEN-END:variables
}
