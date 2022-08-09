package com.mycompany.gui;

import com.mycompany.util.IdCheck;
import com.mycompany.util.InsertTable;
import com.mycompany.util.LoadSubTypes;
import com.mycompany.util.LoadTables;
import com.mycompany.util.SearchTable;
import com.mycompany.util.SetEmptyItems;
import com.mycompany.util.TypeIds;
import com.mycompany.frameutil.RoundedPanel;
import com.mycompany.frameutil.ImageSizer;
import com.mycompany.frameutil.MainTheme;
import com.mycompany.util.PanelRemover;

import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mycompany.model.MySql;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author acer
 */
public class FoodItem extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public FoodItem() {
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
        textF1.setForeground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.secondColor);
        jPanel8.setBackground(MainTheme.secondColor);
        this.setForeground(MainTheme.secondColor);
        this.setVisible(true);
        loadCombos();
        loadTable();
        tableListernRag();
        foodMenuBar1.foo(this);
    }

    public FoodItem(FRN frn, String foodItemType) {
        this();
        this.frn = frn;
        this.fi = this;
        comboBox3.setSelectedItem(foodItemType);
        isFRNInvolved = true;
        otherFramesInvolved = true;
    }
    FRN frn;
    FoodItem fi;
    boolean isFRNInvolved = false;
    boolean otherFramesInvolved = false;
    boolean updateMode = false;
    String itemId = "";

    String loadTableQuery;
    String[] colnames = {"food_item_id", "food_item_name", "food_item_category_name", "contain_method_name"};

    private void loadQuery() {
        ArrayList<String> al = new ArrayList<String>();
        al.add("food_item");
        al.add("food_item_category,food_item");
        al.add("contain_method,food_item");

        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();
        //System.out.println(this.loadTableQuery);

    }

    private void loadTable() {
        loadQuery();
        String sort = "ORDER BY `food_item_name` ASC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable1, query, this.colnames);
    }

    private void loadCombos() {
        LoadSubTypes.loadType(comboBox1, "food_item_category");
        LoadSubTypes.loadType(comboBox2, "contain_method");
        LoadSubTypes.loadType(comboBox3, "food_item_category");
        LoadSubTypes.loadType(comboBox4, "contain_method");
    }

    private void clearFields() {
        JComponent[] jp = {textF1, comboBox1, comboBox2, textF2, comboBox3, comboBox4};
        SetEmptyItems.emptyItems(jp);
    }

    private void add() {
        String name = textF1.getText();
        String containMethod = comboBox2.getSelectedItem().toString();
        String type = comboBox1.getSelectedItem().toString();

        if (name.isEmpty()) {
            Message m = new Message(this, "Please enter a food item name", "Warning");
        } else if (containMethod.equals("Select contain_method")) {
            Message m = new Message(this, "Please select a valid containing method", "Warning");
        } else if (type.equals("Select food_item_category")) {
            Message m = new Message(this, "Please select a valid category name", "Warning");
        } else {
            String typeid = TypeIds.getId("food_item_category", type);
            String containMethodId = TypeIds.getId("contain_method", containMethod);
            if (!IdCheck.isExits("food_item", "food_item_name", name)) {
                ArrayList<String> info = new ArrayList<>();
                info.add(containMethodId);
                info.add(typeid);
                info.add(name);
                InsertTable it = new InsertTable("food_item", info);
                loadTable();
                clearFields();
            } else {
                Message m = new Message(this, "This food name already exists", "Warning");
            }
        }
    }

    private void update() {
        String name = textF1.getText();
        String containMethod = comboBox2.getSelectedItem().toString();
        String type = comboBox1.getSelectedItem().toString();

        if (name.isEmpty()) {
            Message m = new Message(this, "Please enter a food item name", "Warning");
        } else if (containMethod.equals("Select contain_method")) {
            Message m = new Message(this, "Please select a valid containing method", "Warning");
        } else if (type.equals("Select food_item_category")) {
            Message m = new Message(this, "Please select a valid category name", "Warning");
        } else {
            String typeid = TypeIds.getId("food_item_category", type);
            String containMethodId = TypeIds.getId("contain_method", containMethod);
            if (!IdCheck.isExits("food_item", "food_item_name", name)) {
                MySql.iud("UPDATE `food_item` SET `food_item_name`='" + name + "' WHERE `food_item_id`='" + itemId + "'");
                loadTable();
                clearFields();
            } else {
                Message m = new Message(this, "This food name already exists", "Warning");
            }
        }
    }

    public void tableListernRag() {
        customTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = customTable1.getSelectedRow();
                if (row != -1 && updateMode) {
                    String id = customTable1.getValueAt(row, 0).toString();
                    String name = customTable1.getValueAt(row, 1).toString();
                    String category = customTable1.getValueAt(row, 2).toString();
                    String containing = customTable1.getValueAt(row, 3).toString();
                    textF1.setText(name);
                    comboBox1.setSelectedItem(category);
                    comboBox2.setSelectedItem(containing);
                    itemId = id;

                }
                if (row != -1 && isFRNInvolved) {
                    String id = customTable1.getValueAt(row, 0).toString();
                    String name = customTable1.getValueAt(row, 1).toString();
                    String category = customTable1.getValueAt(row, 2).toString();
                    String containing = customTable1.getValueAt(row, 3).toString();
                    textF1.setText(name);
                    comboBox1.setSelectedItem(category);
                    comboBox2.setSelectedItem(containing);
                    itemId = id;
                    if (isFRNInvolved) {
                        frn.textF1.setText(id);
                        frn.textF2.setText(name);
                        frn.textF3.setText(category);
                        fi.dispose();
                    }

                }
            }

        });

    }

    private void advancedSearch() {

        String sort = comboBox5.getSelectedItem().toString();
        boolean isSelected = false;
        String containMethod = "Select contain_method";
        String type = "Select food_item_category";
        String name = textF2.getText();
        if (comboBox4.getSelectedItem() != null && comboBox3.getSelectedItem() != null) {
            isSelected = true;

            containMethod = comboBox4.getSelectedItem().toString();
            type = comboBox3.getSelectedItem().toString();
        }

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        if (sort.equals("NAME A-Z")) {
            sortQuery = "ORDER BY `food_item`.`food_item_name` ASC";
        } else if (sort.equals("NAME Z-A")) {
            sortQuery = "ORDER BY `food_item`.`food_item_name` DESC";
        } else if (sort.equals("CAT A-Z")) {
            sortQuery = "ORDER BY `food_item`.`food_item_name` DESC";
        } else if (sort.equals("CAT Z-A")) {
            sortQuery = "ORDER BY `food_item`.`food_item_name` DESC";
        }
        if (!name.isEmpty()) {
            v.add("`food_item_name` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (!type.equals("Select food_item_category") && isSelected) {
            v.add("`food_item_category_name` LIKE '%" + type + "%' ");
            queriesInvolved = true;
        }
        if (!containMethod.equals("Select contain_method") && isSelected) {
            v.add("`contain_method_name` LIKE '%" + containMethod + "%' ");
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
        if (queriesInvolved) {
            stringquerybuild.append(this.loadTableQuery);
            stringquerybuild.append(whereQueryBuilder);
            stringquerybuild.append(sortQuery);
            String query = stringquerybuild.toString();
            //System.out.println("where query is " + whereQueryBuilder);
            LoadTables lt = new LoadTables(customTable1, query, this.colnames);
        }
        //System.out.println("where query is " + whereQueryBuilder);

    }

    private JComponent[] getComponentsRag() {
        JComponent[] abc = {textF1, comboBox1, comboBox2, textF2, comboBox3, comboBox4};
        return abc;

    }

    private void removeValues() {
        SetEmptyItems.emptyItems(getComponentsRag());
    }

    private void updateModeActive() {
        updateMode = true;
        jPanel7.removeAll();
        jPanel7.add(customButton4);
        jPanel7.repaint();
        jPanel7.revalidate();
        jPanel6.removeAll();
        jPanel6.add(customButton2);
        jPanel6.repaint();
        jPanel6.revalidate();
        removeValues();
    }

    private void updateModeDeactivate() {
        updateMode = false;
        jPanel7.removeAll();
        jPanel7.add(customButton3);
        jPanel7.repaint();
        jPanel7.revalidate();
        jPanel6.removeAll();
        jPanel6.add(customButton1);
        jPanel6.repaint();
        jPanel6.revalidate();
        removeValues();
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
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        textF2 = new frameutil.TextF();
        jLabel5 = new javax.swing.JLabel();
        comboBox3 = new frameutil.ComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        comboBox4 = new frameutil.ComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        customButton5 = new frameutil.CustomButton();
        comboBox5 = new frameutil.ComboBox<>();
        customButton7 = new frameutil.CustomButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textF1 = new frameutil.TextF();
        comboBox1 = new frameutil.ComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        comboBox2 = new frameutil.ComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        customButton1 = new frameutil.CustomButton();
        customButton2 = new frameutil.CustomButton();
        jPanel7 = new javax.swing.JPanel();
        customButton3 = new frameutil.CustomButton();
        customButton4 = new frameutil.CustomButton();
        customButton6 = new frameutil.CustomButton();
        customButton8 = new frameutil.CustomButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable1 = new frameutil.CustomTable();
        foodMenuBar1 = new frameutil.FoodMenuBar();

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

        jPanel4.setLayout(new java.awt.CardLayout());

        textF2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF2ActionPerformed(evt);
            }
        });
        textF2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF2KeyTyped(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Name");

        comboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox3ItemStateChanged(evt);
            }
        });
        comboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox3ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Category");

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
        comboBox4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                comboBox4PropertyChange(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Containing Method");

        customButton5.setText("Insert Mode");
        customButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton5ActionPerformed(evt);
            }
        });

        comboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAME A-Z", "NAME Z-A", "CAT A-Z", "CAT Z-A" }));
        comboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox5ActionPerformed(evt);
            }
        });

        customButton7.setText("Clear");
        customButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textF2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8, "card3");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Name");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Category");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Containing Method");

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.CardLayout());

        customButton1.setText("Enter");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(customButton1, "card8");

        customButton2.setText("Update");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(customButton2, "card3");

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.CardLayout());

        customButton3.setText("Update Mode");
        customButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton3ActionPerformed(evt);
            }
        });
        jPanel7.add(customButton3, "card2");

        customButton4.setText("Insert Mode");
        customButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton4ActionPerformed(evt);
            }
        });
        jPanel7.add(customButton4, "card3");

        customButton6.setText("Search Mode");
        customButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton6ActionPerformed(evt);
            }
        });

        customButton8.setText("Add a Category");
        customButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(customButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(comboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(customButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(92, 92, 92)))
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textF1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2, "card2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        customTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Category", "Containing"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(customTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(foodMenuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(foodMenuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            fi.dispose();
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

        private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
            // TODO add your handling code here:
            add();
            clearFields();
            loadCombos();
            loadTable();
        }//GEN-LAST:event_customButton1ActionPerformed

        private void customButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton3ActionPerformed
            // TODO add your handling code here:
            updateModeActive();
            clearFields();
            loadCombos();
            loadTable();
        }//GEN-LAST:event_customButton3ActionPerformed

        private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed
            // TODO add your handling code here:
            updateModeDeactivate();
            clearFields();
            loadCombos();
            loadTable();
        }//GEN-LAST:event_customButton4ActionPerformed

        private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
            // TODO add your handling code here:
            update();
            clearFields();
            loadCombos();
            loadTable();
        }//GEN-LAST:event_customButton2ActionPerformed

        private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed
            // TODO add your handling code here:
//		jPanel4.removeAll();
//		jPanel4.add(jPanel2);
//		jPanel4.repaint();
//		jPanel4.revalidate();
            PanelRemover.removeP(jPanel4, jPanel2);
        }//GEN-LAST:event_customButton5ActionPerformed

        private void customButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton6ActionPerformed
            // TODO add your handling code here:
            PanelRemover.removeP(jPanel4, jPanel8);
        }//GEN-LAST:event_customButton6ActionPerformed

        private void textF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF2ActionPerformed
            // TODO add your handling code here:

        }//GEN-LAST:event_textF2ActionPerformed

        private void textF2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF2KeyTyped
            // TODO add your handling code here:
            advancedSearch();
        }//GEN-LAST:event_textF2KeyTyped

        private void comboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox4ItemStateChanged
            // TODO add your handling code here:
            //advancedSearch();
        }//GEN-LAST:event_comboBox4ItemStateChanged

        private void comboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox3ItemStateChanged
            // TODO add your handling code here:
            //System.out.println(comboBox3.getSelectedItem().toString());
            //System.out.println("HEY ITEM CHANGED");
        }//GEN-LAST:event_comboBox3ItemStateChanged

        private void comboBox4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_comboBox4PropertyChange
            // TODO add your handling code here:
            //System.out.println(comboBox4.getSelectedItem().toString());

        }//GEN-LAST:event_comboBox4PropertyChange

        private void comboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox4ActionPerformed
            // TODO add your handling code here:

            advancedSearch();

        }//GEN-LAST:event_comboBox4ActionPerformed

        private void comboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox3ActionPerformed
            // TODO add your handling code here:
            advancedSearch();
        }//GEN-LAST:event_comboBox3ActionPerformed

        private void customButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton7ActionPerformed
            // TODO add your handling code here:
            clearFields();
            loadCombos();
            loadTable();
        }//GEN-LAST:event_customButton7ActionPerformed

        private void comboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox5ActionPerformed
            // TODO add your handling code here:
            advancedSearch();
        }//GEN-LAST:event_comboBox5ActionPerformed

    private void customButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton8ActionPerformed
        // TODO add your handling code here:
        FoodItemCategory fic = new FoodItemCategory(this);
        fic.setVisible(true);
    }//GEN-LAST:event_customButton8ActionPerformed
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
            java.util.logging.Logger.getLogger(FoodItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoodItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoodItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoodItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

                JFrame jf = new FoodItem();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    public frameutil.ComboBox<String> comboBox1;
    private frameutil.ComboBox<String> comboBox2;
    private frameutil.ComboBox<String> comboBox3;
    private frameutil.ComboBox<String> comboBox4;
    private frameutil.ComboBox<String> comboBox5;
    private frameutil.CustomButton customButton1;
    private frameutil.CustomButton customButton2;
    private frameutil.CustomButton customButton3;
    private frameutil.CustomButton customButton4;
    private frameutil.CustomButton customButton5;
    private frameutil.CustomButton customButton6;
    private frameutil.CustomButton customButton7;
    private frameutil.CustomButton customButton8;
    private frameutil.CustomTable customTable1;
    private frameutil.FoodMenuBar foodMenuBar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    private frameutil.TextF textF1;
    private frameutil.TextF textF2;
    // End of variables declaration//GEN-END:variables
}
