package com.mycompany.gui;

import com.mycompany.frameutil.*;
import com.mycompany.gui.employee.Chef;
import com.mycompany.util.*;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class AddTypes extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public AddTypes() {
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
        textF1.setBackground(MainTheme.thirdColor);
        textF2.setBackground(MainTheme.thirdColor);
        customButton1.setBackground(MainTheme.thirdColor);
        
        JComponent[] jclbl = {jLabel2, jLabel3, jLabel4};
        
        ColorSetter.setC(jclbl, Color.WHITE, 2);
        
        JComponent[] jc = {comboBox1, textF1, textF2, textF3};
        ColorSetter.setC(jc, MainTheme.thirdColor, 1);
        jPanel2.setBackground(MainTheme.secondColor);
        
        setDocFilters();
        loadCombos();
        menuBar1.foo(this);
        
    }
    
    public AddTypes(DealerT et, HashMap<String, String> hm) {
        this();
        this.updateId = hm.get("id");
        
    }

    public AddTypes(FoodItem fi) {
        this();
        fi.setEnabled(false);
        fi.setVisible(false);
    }

    public AddTypes(Chef c) {
        this();
        
    }
    
    private void clearFields() {
        JComponent[] jc = {comboBox1, textF1, textF2, textF3};
        SetEmptyItems.emptyItems(jc);
        loadCombos();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String updateId;
    
    public void addTypeName() {
        String type = null;
        String typename = textF1.getText();
        if (comboBox1.getSelectedItem() != null) {
            type = comboBox1.getSelectedItem().toString();
        }
        if (type.equals("Select Types")) {
            Message m = new Message(this, "Please choose a valid type", "Warning");
        } else if (typename.isEmpty()) {
            Message m = new Message(this, "Please enter the type name", "Warning");
        } else {
            boolean exits = IdCheck.isExits(type, type + "_name", typename);
            if (exits) {
                Message m = new Message(this, "This name already does it exits", "Warning");
            } else {
                ArrayList<String> info = new ArrayList<>();
                info.add(typename);
                
                InsertTable it = new InsertTable(type, info);
                clearFields();
            }
        }
        
    }
    
    private void addMenuItemCategory(String tableName) {
        
        String typename = textF2.getText();
        
        if (typename.isEmpty()) {
            Message m = new Message(this, "Please enter the type name", "Warning");
        } else {
            boolean exits = IdCheck.isExits(tableName, "menu_item_category_name", typename);
            if (exits) {
                Message m = new Message(this, "This name already does it exits", "Warning");
            } else {
                ArrayList<String> info = new ArrayList<>();
                info.add(typename);
                
                InsertTable it = new InsertTable(tableName, info);
                clearFields();
            }
        }
    }
    
    private void addCity(String tableName) {
        
        String typename = textF3.getText();
        
        if (typename.isEmpty()) {
            Message m = new Message(this, "Please enter the type name", "Warning");
        } else {
            boolean exits = IdCheck.isExits(tableName, "" + tableName + "_name", typename);
            if (exits) {
                Message m = new Message(this, "This name already does it exits", "Warning");
            } else {
                ArrayList<String> info = new ArrayList<>();
                info.add(typename);
                
                InsertTable it = new InsertTable(tableName, info);
                clearFields();
            }
        }
    }
    
    private void addTable(String tableName) {
        
        String typename = textF4.getText();
        
        if (typename.isEmpty()) {
            Message m = new Message(this, "Please enter the type name", "Warning");
        } else {
            boolean exits = IdCheck.isExits(tableName, "" + tableName + "_name", typename);
            if (exits) {
                Message m = new Message(this, "This name already does it exits", "Warning");
            } else {
                ArrayList<String> info = new ArrayList<>();
                info.add(typename);
                
                InsertTable it = new InsertTable(tableName, info);
                clearFields();
            }
        }
    }
    
    private void viewMenuItemCategory() {
        
        TypeList tl = new TypeList(this, "menu_item_category") {
            @Override
            public void actionConfirmed() {
                
            }
            
            @Override
            public void actionCancelled() {
                
            }
        };
        tl.setVisible(true);
        
    }
    
    private void viewCity() {
        
        TypeList tl = new TypeList(this, "city") {
            @Override
            public void actionConfirmed() {
                
            }
            
            @Override
            public void actionCancelled() {
                
            }
        };
        tl.setVisible(true);
        
    }
    
    private void viewTable() {
        
        TypeList tl = new TypeList(this, "customer_table") {
            @Override
            public void actionConfirmed() {
                
            }
            
            @Override
            public void actionCancelled() {
                
            }
        };
        tl.setVisible(true);
        
    }
    
    private void viewType() {
        if (comboBox1.getSelectedItem() != null) {
            if (!comboBox1.getSelectedItem().toString().equals("Select Types")) {
                System.out.println(comboBox1.getSelectedItem().toString());
                TypeList tl = new TypeList(this, comboBox1.getSelectedItem().toString()) {
                    @Override
                    public void actionConfirmed() {
                        
                    }
                    
                    @Override
                    public void actionCancelled() {
                        
                    }
                };
                tl.setVisible(true);
            }
            
        } else {
            
        }
    }
    
    private void jframeCustmize() {
        closeLabel.setIcon(labelSetIcon("/Icons/close.png", closeLabel.getWidth() - 25, closeLabel.getHeight() - 17));
        boxLabel.setIcon(labelSetIcon("/Icons/square.png", boxLabel.getWidth() - 20, boxLabel.getHeight() - 14));
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
        
        LoadSubTypes.loadSubTypeTables(comboBox1);
        
    }
    
    private void setDocFilters() {
        
    }
    
    private void contactCheck(String contact) {
        
        ResultSet rs;
        try {
            rs = MySql.sq("SELECT * FROM `dealer` WHERE `dealer_contact`='" + contact + "' ");
            if (rs.next()) {
                Message m = new Message(this, "this contact is already exits ", "warning");
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddTypes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void emailCheck(String email) {
        
        ResultSet rs;
        try {
            rs = MySql.sq("SELECT * FROM `dealer` WHERE `dealer_email`='" + email + "' ");
            if (rs.next()) {
                Message m = new Message(this, "this email is already exits ", "warning");
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddTypes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddTypes.class.getName()).log(Level.SEVERE, null, ex);
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
        comboBox1 = new ComboBox<>();
        textF1 = new TextF();;
        customButton1 = new CustomButton();
        customButton3 = new CustomButton();
        textF2 = new TextF();;
        jLabel3 = new javax.swing.JLabel();
        customButton2 = new CustomButton();
        customButton5 = new CustomButton();
        customButton7 = new CustomButton();
        customButton6 = new CustomButton();
        textF3 = new TextF();;
        jLabel4 = new javax.swing.JLabel();
        customButton4 = new CustomButton();
        textF4 = new TextF();;
        jLabel5 = new javax.swing.JLabel();
        customButton8 = new CustomButton();
        customButton9 = new CustomButton();
        menuBar1 = new MenuBar();

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
        jPanel1.add(miniLabel, java.awt.BorderLayout.CENTER);

        boxLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boxLabel.setPreferredSize(new java.awt.Dimension(40, 25));
        boxLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boxLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boxLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boxLabelMouseExited(evt);
            }
        });
        jPanel1.add(boxLabel, java.awt.BorderLayout.LINE_START);

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Choose Types");

        customButton1.setText("Add Type Name");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        customButton3.setText("View Type Names");
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Menu Item Category");

        customButton2.setText("Add Type Name");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        customButton5.setText("View Type Names");
        customButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton5ActionPerformed(evt);
            }
        });

        customButton7.setText("View City Names");
        customButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton7ActionPerformed(evt);
            }
        });

        customButton6.setText("Add City Name");
        customButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton6ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Add City");

        customButton4.setText("Clear");
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tables");

        customButton8.setText("Add Table");
        customButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton8ActionPerformed(evt);
            }
        });

        customButton9.setText("View Table");
        customButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(textF2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(menuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        this.dispose();
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

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
        // TODO add your handling code here:
        addTypeName();
    }//GEN-LAST:event_customButton1ActionPerformed

    private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
        viewType();
        

    }//GEN-LAST:event_customButton3ActionPerformed

    private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
        // TODO add your handling code here:
        clearFields();

    }//GEN-LAST:event_customButton4ActionPerformed

    private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
        // TODO add your handling code here:
        addMenuItemCategory("menu_item_category");
        clearFields();
    }//GEN-LAST:event_customButton2ActionPerformed

    private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed
        // TODO add your handling code here:
        viewMenuItemCategory();
    }//GEN-LAST:event_customButton5ActionPerformed

    private void customButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton6ActionPerformed
        // TODO add your handling code here:
        addCity("city");
    }//GEN-LAST:event_customButton6ActionPerformed

    private void customButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton7ActionPerformed
        // TODO add your handling code here:
        viewCity();
    }//GEN-LAST:event_customButton7ActionPerformed

    private void customButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton8ActionPerformed
        // TODO add your handling code here:
        addTable("customer_table");
    }//GEN-LAST:event_customButton8ActionPerformed

    private void customButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton9ActionPerformed
        // TODO add your handling code here:
        viewTable();
    }//GEN-LAST:event_customButton9ActionPerformed

    private void boxLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxLabelMouseExited
        // TODO add your handling code here:

        //boxLabel.setBackground(MainTheme.mainColor);
        //boxLabel.setOpaque(false);
        boxLabel.setBackground(MainTheme.secondColor);
        boxLabel.setOpaque(false);
    }//GEN-LAST:event_boxLabelMouseExited

    private void boxLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxLabelMouseEntered
        // TODO add your handling code here:
        boxLabel.setOpaque(true);
        boxLabel.setBackground(MainTheme.mainColor);
    }//GEN-LAST:event_boxLabelMouseEntered

    private void boxLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxLabelMouseClicked
        // TODO add your handling code here:
        
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
            java.util.logging.Logger.getLogger(AddTypes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTypes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTypes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTypes.class
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
                
                JFrame jf = new AddTypes();
                jf.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private ComboBox<String> comboBox1;
    private CustomButton customButton1;
    private CustomButton customButton2;
    private CustomButton customButton3;
    private CustomButton customButton4;
    private CustomButton customButton5;
    private CustomButton customButton6;
    private CustomButton customButton7;
    private CustomButton customButton8;
    private CustomButton customButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton jToggleButton1;
    private MenuBar menuBar1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private TextF textF1;
    private TextF textF2;
    private TextF textF3;
    private TextF textF4;
    // End of variables declaration//GEN-END:variables
}
