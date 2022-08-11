package com.mycompany.view.gui;


import com.mycompany.util.*;
import com.mycompany.view.gui.employee.Chef;

import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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
public class SellingRecord extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public SellingRecord() {
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
        textF1.setBackground(MainTheme.mainColor);
        textF3.setBackground(MainTheme.secondColor);
        textF3.setEditable(false);
        this.setForeground(MainTheme.secondColor);
        jPanel2.setBackground(MainTheme.secondColor);
        jPanel5.setBackground(MainTheme.secondColor);
        setDocFilters();
        setDocFilerForDate();
        loadCombos();
        loadTable();
        menuBar1.foo(this);

    }

    public SellingRecord(DealerT et, HashMap<String, String> hm) {
        this();
        this.updateId = hm.get("id");

    }

    public SellingRecord(Chef c) {
        this();

    }

    String additionalQuery = "INNER JOIN `mainmenu`\n"
            + "ON `mainmenu`.`menuItemId` = `payment_item`.`menuItemId`\n"
            + "INNER JOIN `serving_type`\n"
            + "ON `serving_type`.`serving_type_id` = `mainmenu`.`serving_type_id`\n"
            + "INNER JOIN `menu_item_category` \n"
            + "ON `menu_item_category`.`menu_item_category_id` = `mainmenu`.`menu_item_category_id`";
    String loadTableQuery;
    String[] colnames = {"payment_note_id", "payment_item_id", "menuItemName", "menu_item_category_name", "payment_note.payment_time", "payment_item.price", "payment_item.qty", "total"};
    String generatedQuery;

    private void loadQuery() {
        ArrayList<String> al = new ArrayList<String>();
        al.add("payment_item");
        al.add("payment_note,payment_item");

        SearchTable st = new SearchTable(al);
        this.loadTableQuery = st.getTableQuery();

    }

    private void loadTable() {
        loadQuery();
        String loadTableQueryAttached = loadTableQuery + " " + additionalQuery;
        System.out.println("load query is " + loadTableQuery);
        loadTableQuery += " " + additionalQuery + " ";
        String sort = "ORDER BY `payment_time` DESC";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(this.loadTableQuery).toString();
        stringquerybuild.append(sort).toString();
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable1, query, this.colnames, 5, 6);
        textF3.setText(updateTotal());
        System.out.println("HEY");
    }

    private void loadTable(String stringQuery) {

        String sort = "";

        StringBuilder stringquerybuild = new StringBuilder();
        stringquerybuild.append(stringQuery);
        stringquerybuild.append(sort);
        String query = stringquerybuild.toString();

        LoadTables lt = new LoadTables(customTable1, query, this.colnames, 5, 6);
        textF3.setText(updateTotal());
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String updateId;

    private void jframeCustmize() {
        closeLabel.setIcon(labelSetIcon("/Icons/close.png", closeLabel.getWidth() - 25, closeLabel.getHeight() - 17));
        boxLabel.setIcon(labelSetIcon("/Icons/home.png", boxLabel.getWidth() - 23, boxLabel.getHeight() - 17));
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

        LoadSubTypes.loadType(comboBox2, "menu_item_category");
    }

    private void setDocFilters() {
        String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
        FilterDocRagRegex dob = new FilterDocRagRegex(textF10, dobRegex);
        dob = new FilterDocRagRegex(textF11, dobRegex);
    }

    private String updateTotal() {
        double rowTotal = 0;
        if (customTable1.getRowCount() > 0) {
            for (int i = 0; i < customTable1.getRowCount(); i++) {
                String total = customTable1.getValueAt(i, 7).toString();
                rowTotal += Double.parseDouble(total);
            }
        } else {
            rowTotal = 0;
        }
        return Double.toString(rowTotal);
    }

    private void clearAdvancedSearchField() {
        ActionListener ac = comboBox2.getActionListeners()[0];
        comboBox2.removeActionListener(ac);
        JComponent[] jc = {textF1, comboBox2, comboBox3, jDateChooser3, jDateChooser4, textF11, textF10};
        SetEmptyItems.emptyItems(jc);
        loadCombos();
        comboBox2.addActionListener(ac);
        loadTable();
    }

    public void advancedSearch() {
        String name = textF1.getText();
        String toDate = textF11.getText();
        String fromDate = textF10.getText();
        boolean isNameInvolved = false;
        boolean isSortInvolved = false;
        String categoryType = "Select menu_item_category";
        if (comboBox2.getSelectedItem() != null) {
            categoryType = comboBox2.getSelectedItem().toString();
        }

        String sort = comboBox3.getSelectedItem().toString();

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";

        if (sort.equals("Today")) {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String startDateTime = today + " 00:00:00";
            String endDateTime = today + " 23:59:59";

            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");

            queriesInvolved = true;
        } else if (sort.equals("ThisMonth")) {
            String today = new SimpleDateFormat("yyyy-MM").format(new Date());
            String startDateTime = today + "-00 00:00:00";
            String endDateTime = today + "-28 23:59:59";

            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");

            queriesInvolved = true;
        } else if (sort.equals("LastMonth")) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            Date lastyear = calendar.getTime();

            String today = new SimpleDateFormat("yyyy-MM").format(lastyear);
            String startDateTime = today + "-00 00:00:00";
            String endDateTime = today + "-28 23:59:59";

            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");

            queriesInvolved = true;
        } else if (sort.equals("ThisYear")) {
            String today = new SimpleDateFormat("yyyy").format(new Date());
            String startDateTime = today + "-01-00 00:00:00";
            String endDateTime = today + "-12-31 23:59:59";

            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");

            queriesInvolved = true;
        } else if (sort.equals("LastYear")) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -1);
            Date lastyear = calendar.getTime();

            String today = new SimpleDateFormat("yyyy").format(lastyear);
            String startDateTime = today + "-01-00 00:00:00";
            String endDateTime = today + "-12-31 23:59:59";

            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");

            queriesInvolved = true;
        } else if (sort.equals("PriceHigh")) {
            sortQuery = "ORDER BY `payment_item`.`price` DESC";
            isSortInvolved = true;
        } else if (sort.equals("PriceLow")) {
            sortQuery = "ORDER BY `payment_item`.`price` ASC";
            isSortInvolved = true;
        }
        if (!categoryType.equals("Select menu_item_category")) {

            v.add("`menu_item_category`.`menu_item_category_name` = '" + categoryType + "' ");
            queriesInvolved = true;

        }
        if (!name.equals("")) {
            v.add("`mainmenu`.`menuItemName` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (queriesInvolved) {
            whereQueryBuilder.append("WHERE ");
        }
        for (int i = 0; i < v.size(); i++) {

            whereQueryBuilder.append("");
            whereQueryBuilder.append(v.get(i));

            if (i != v.size() - 1) {
                whereQueryBuilder.append("AND ");
            }
            if (i == v.size() - 1 && !isSortInvolved) {

                whereQueryBuilder.append("ORDER BY `payment_note`.`payment_time` DESC ");
            }
        }

        if (this.loadTableQuery != null) {
            stringquerybuild.append(this.loadTableQuery);
        } else {
            loadQuery();

            String loadTableQueryAttached = loadTableQuery + " " + additionalQuery;
            stringquerybuild.append(loadTableQueryAttached);
        }
        stringquerybuild.append(whereQueryBuilder);
        stringquerybuild.append(sortQuery);

        String query = stringquerybuild.toString();
        // loadTableQuery += " " + additionalQuery + " ";
        this.generatedQuery = query;
        LoadTables lt = new LoadTables(customTable1, query, this.colnames, 5, 6);
        textF3.setText(updateTotal());

    }

    public void advancedSearchOnlyDate() {
        String name = textF1.getText();
        String toDate = textF11.getText();
        String fromDate = textF10.getText();
        boolean isNameInvolved = false;
        boolean isSortInvolved = false;
        String categoryType = "Select menu_item_category";
        if (comboBox2.getSelectedItem() != null) {
            categoryType = comboBox2.getSelectedItem().toString();
        }

        StringBuilder stringquerybuild = new StringBuilder();
        StringBuilder whereQueryBuilder = new StringBuilder();
        Vector<String> v = new Vector<String>();
        boolean queriesInvolved = false;

        String sortQuery = "";
        String whereQuery = "";
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String startDateTime = toDate;
        String endDateTime = fromDate;

        if (!toDate.isEmpty() && !fromDate.isEmpty()) {
            v.add("  (`payment_note`.`payment_time` BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ");
            queriesInvolved = true;
        }
        if (!categoryType.equals("Select menu_item_category")) {

            v.add("`menu_item_category`.`menu_item_category_name` = '" + categoryType + "' ");
            queriesInvolved = true;

        }
        if (!name.equals("")) {
            v.add("`mainmenu`.`menuItemName` LIKE '%" + name + "%' ");
            queriesInvolved = true;
        }
        if (queriesInvolved) {
            whereQueryBuilder.append("WHERE ");
        }
        for (int i = 0; i < v.size(); i++) {

            whereQueryBuilder.append("");
            whereQueryBuilder.append(v.get(i));

            if (i != v.size() - 1) {
                whereQueryBuilder.append("AND ");
            }
            if (i == v.size() - 1 && !isSortInvolved) {

                whereQueryBuilder.append("ORDER BY `payment_note`.`payment_time` DESC ");
            }
        }

        if (this.loadTableQuery != null) {
            stringquerybuild.append(this.loadTableQuery);
        } else {
            loadQuery();
            loadTableQuery += " " + additionalQuery + " ";
            stringquerybuild.append(this.loadTableQuery);
        }
        stringquerybuild.append(whereQueryBuilder);
        stringquerybuild.append(sortQuery);
        String query = stringquerybuild.toString();

        // loadTableQuery += " " + additionalQuery + " ";
        this.generatedQuery = query;
        LoadTables lt = new LoadTables(customTable1, query, this.colnames, 5, 6);
        textF3.setText(updateTotal());
    }

    private void setDocFilerForDate() {
        AbstractDocument abTextF12 = (AbstractDocument) textF10.getDocument();
        abTextF12.setDocumentFilter(new DocumentFilter() {
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws
                    BadLocationException {

                fb.remove(offset, length);
                advancedSearchOnlyDate();
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                    AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string, attr);
                advancedSearchOnlyDate();
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                    AttributeSet attrs) throws BadLocationException {
                String jtText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = jtText.substring(0, offset) + text;
                //regex = "([1-9][0-9]+|[1-9])";
                String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
                boolean match = com.mycompany.util.BasicValidator.regexMatcher(newText, dobRegex);
                if (match || newText.equals("")) {
                    fb.replace(offset, length, text, attrs);
                    advancedSearchOnlyDate();
                } else {

                }

            }
        });

        AbstractDocument abTextF11 = (AbstractDocument) textF11.getDocument();
        abTextF11.setDocumentFilter(new DocumentFilter() {
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws
                    BadLocationException {

                fb.remove(offset, length);
                advancedSearchOnlyDate();
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                    AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string, attr);
                advancedSearchOnlyDate();
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                    AttributeSet attrs) throws BadLocationException {
                String jtText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = jtText.substring(0, offset) + text;
                //regex = "([1-9][0-9]+|[1-9])";
                String dobRegex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
                boolean match = com.mycompany.util.BasicValidator.regexMatcher(newText, dobRegex);
                if (match || newText.equals("")) {
                    fb.replace(offset, length, text, attrs);
                    advancedSearchOnlyDate();
                } else {

                }

            }
        });

        SetDocFilter sdf = new SetDocFilter(textF1) {
            @Override
            public void doThings() {
                if (jDateChooser4.getDate() != null && jDateChooser3.getDate() != null) {

                    advancedSearchOnlyDate();

                } else {
                    advancedSearch();
                }

            }
        };
        sdf.set(textF1);
    }

    private void printGRN(JTable jt) {

        try {
            TableModel tm1 = jt.getModel();
            //String jasperPath = "src//reportXML//customerOrder.jrxml";
            InputStream jasperPathStream = getClass().getResourceAsStream("/reportxml/SellingHistory.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(jasperPathStream);
            HashMap<String, Object> hm = new HashMap<String, Object>();

            hm.put("CalcTotal", updateTotal());

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
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable1 = new CustomTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        textF1 = new TextF();;
        comboBox2 = new ComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboBox3 = new ComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        textF11 = new TextF();;
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        textF10 = new TextF();;
        customButton1 = new CustomButton();
        customButton2 = new CustomButton();
        customButton5 = new CustomButton();
        customButton6 = new CustomButton();
        customButton7 = new CustomButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        textF2 = new TextF();;
        comboBox4 = new ComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        comboBox5 = new ComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        textF12 = new TextF();;
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        textF13 = new TextF();;
        customButton3 = new CustomButton();
        customButton4 = new CustomButton();
        textF3 = new TextF();;
        jLabel26 = new javax.swing.JLabel();
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

        jLabel2.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Selling Record");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ItemId", "PaymentId", "ItemName", "Category", "DateTime", "Price", "Qty", "Total"
            }
        ));
        customTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(customTable1);
        if (customTable1.getColumnModel().getColumnCount() > 0) {
            customTable1.getColumnModel().getColumn(0).setMinWidth(80);
            customTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            customTable1.getColumnModel().getColumn(0).setMaxWidth(80);
            customTable1.getColumnModel().getColumn(1).setMinWidth(100);
            customTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            customTable1.getColumnModel().getColumn(1).setMaxWidth(100);
            customTable1.getColumnModel().getColumn(5).setMinWidth(60);
            customTable1.getColumnModel().getColumn(5).setPreferredWidth(60);
            customTable1.getColumnModel().getColumn(5).setMaxWidth(60);
            customTable1.getColumnModel().getColumn(6).setMinWidth(50);
            customTable1.getColumnModel().getColumn(6).setPreferredWidth(50);
            customTable1.getColumnModel().getColumn(6).setMaxWidth(50);
            customTable1.getColumnModel().getColumn(7).setMinWidth(100);
            customTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
            customTable1.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new java.awt.CardLayout());

        jLabel16.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Name");
        jLabel16.setToolTipText("");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        textF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF1ActionPerformed(evt);
            }
        });
        textF1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF1KeyTyped(evt);
            }
        });

        comboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Type");
        jLabel17.setToolTipText("");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel18.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Sort");
        jLabel18.setToolTipText("");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        comboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Today", "ThisMonth", "LastMonth", "ThisYear", "LastYear", "PriceHigh", "PriceLow" }));
        comboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox3ActionPerformed(evt);
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

        jDateChooser3.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser3.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser3.setIcon(null);
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
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

        customButton1.setText("Print a Report");
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        customButton2.setText("Advanced Analysis");
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        customButton5.setText("Clear");
        customButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton5ActionPerformed(evt);
            }
        });

        customButton6.setText("Select Menu");
        customButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton6ActionPerformed(evt);
            }
        });

        customButton7.setText("Total");
        customButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(customButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(customButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(customButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textF10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9))
        );

        jPanel4.add(jPanel2, "card3");

        jLabel21.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Name");
        jLabel21.setToolTipText("");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        textF2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF2KeyTyped(evt);
            }
        });

        comboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox4ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Type");
        jLabel22.setToolTipText("");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel23.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Sort");
        jLabel23.setToolTipText("");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        comboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Today", "ThisMonth", "LastMonth", "ThisYear", "LastYear", "PriceHigh", "PriceLow" }));
        comboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox5ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("ToDate");
        jLabel24.setToolTipText("");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel25.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("FromDate");
        jLabel25.setToolTipText("");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        textF12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textF12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textF12FocusLost(evt);
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

        jDateChooser6.setBackground(new java.awt.Color(0, 102, 255));
        jDateChooser6.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser6.setIcon(null);
        jDateChooser6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser6PropertyChange(evt);
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

        customButton3.setText("Print a Report");

        customButton4.setText("Basic Analysis");
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
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(textF13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textF12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, "card2");

        textF3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textF3ActionPerformed(evt);
            }
        });
        textF3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF3KeyTyped(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Total");
        jLabel26.setToolTipText("");
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(menuBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(menuBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(textF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_roundedPanel2MousePressed

    private void roundedPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundedPanel2MouseDragged

        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - x, yy - y);
    }//GEN-LAST:event_roundedPanel2MouseDragged

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked

        this.dispose();
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

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser3.getDate() != null) {
            textF10.setText(sdf.format(jDateChooser3.getDate()).toString());
            advancedSearchOnlyDate();
        }
    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void textF10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusGained

        //click the jdateChooser to pick the date
        //or show the jDateChooser to pick the date
        jDateChooser3.setVisible(true);

        //jDateChooser3.firePropertyChange(searchText, NORMAL, NORMAL);
        //jDateChooser3.propertyChange((PropertyChangeEvent) jDateChooser3.getPropertyChangeListeners()[0]);
    }//GEN-LAST:event_textF10FocusGained

    private void textF10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF10FocusLost

        jDateChooser3.setVisible(false);
    }//GEN-LAST:event_textF10FocusLost

    private void jDateChooser4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser4PropertyChange

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d = sdf.format(new Date());
        if (jDateChooser4.getDate() != null) {
            textF11.setText(sdf.format(jDateChooser4.getDate()).toString());
            advancedSearchOnlyDate();

        }
    }//GEN-LAST:event_jDateChooser4PropertyChange

    private void textF11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusGained

        jDateChooser4.setVisible(true);
    }//GEN-LAST:event_textF11FocusGained

    private void textF11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF11FocusLost

        jDateChooser4.setVisible(false);
    }//GEN-LAST:event_textF11FocusLost

    private void comboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox3ActionPerformed

        advancedSearch();
    }//GEN-LAST:event_comboBox3ActionPerformed

    private void comboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox2ActionPerformed

        if (comboBox2.getSelectedItem() != null) {
            advancedSearch();
        }
    }//GEN-LAST:event_comboBox2ActionPerformed

    private void textF1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF1KeyTyped



    }//GEN-LAST:event_textF1KeyTyped

    private void textF2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF2KeyTyped

    }//GEN-LAST:event_textF2KeyTyped

    private void comboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox4ActionPerformed

    }//GEN-LAST:event_comboBox4ActionPerformed

    private void comboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox5ActionPerformed

    }//GEN-LAST:event_comboBox5ActionPerformed

    private void textF12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF12FocusGained

    }//GEN-LAST:event_textF12FocusGained

    private void textF12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF12FocusLost

    }//GEN-LAST:event_textF12FocusLost

    private void jDateChooser5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser5PropertyChange

    }//GEN-LAST:event_jDateChooser5PropertyChange

    private void jDateChooser6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser6PropertyChange

    }//GEN-LAST:event_jDateChooser6PropertyChange

    private void textF13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF13FocusGained

    }//GEN-LAST:event_textF13FocusGained

    private void textF13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textF13FocusLost

    }//GEN-LAST:event_textF13FocusLost

    private void customButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton4ActionPerformed

        PanelRemover.removeP(jPanel4, jPanel2);
    }//GEN-LAST:event_customButton4ActionPerformed

    private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed

        CreateObject.make(new AdvancedAnalytics(this));

    }//GEN-LAST:event_customButton2ActionPerformed

    private void customButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton5ActionPerformed

        clearAdvancedSearchField();

    }//GEN-LAST:event_customButton5ActionPerformed

    private void textF11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF11KeyTyped

        System.out.println("HEOo");
        advancedSearchOnlyDate();
    }//GEN-LAST:event_textF11KeyTyped

    private void textF10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF10KeyTyped

        System.out.println("YET***");
        advancedSearchOnlyDate();
    }//GEN-LAST:event_textF10KeyTyped

    private void textF11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF11ActionPerformed


    }//GEN-LAST:event_textF11ActionPerformed

    private void textF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF1ActionPerformed


    }//GEN-LAST:event_textF1ActionPerformed

    private void customButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton6ActionPerformed

        CreateObject.make(new AddMenu(this));
    }//GEN-LAST:event_customButton6ActionPerformed

    private void customButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton7ActionPerformed

        textF3.setText(updateTotal());
    }//GEN-LAST:event_customButton7ActionPerformed

    private void textF3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textF3ActionPerformed

    }//GEN-LAST:event_textF3ActionPerformed

    private void textF3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF3KeyTyped

    }//GEN-LAST:event_textF3KeyTyped

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed

        loadTable(generatedQuery);
        printGRN(customTable1);
    }//GEN-LAST:event_customButton1ActionPerformed

    private void boxLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxLabelMouseClicked

         CreateObject.make(new Home());
         this.dispose();
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
       
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
            java.util.logging.Logger.getLogger(SellingRecord.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SellingRecord.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SellingRecord.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellingRecord.class
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

                JFrame jf = new SellingRecord();
                jf.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boxLabel;
    private javax.swing.JLabel closeLabel;
    private ComboBox<String> comboBox2;
    private ComboBox<String> comboBox3;
    private ComboBox<String> comboBox4;
    private ComboBox<String> comboBox5;
    private CustomButton customButton1;
    private CustomButton customButton2;
    private CustomButton customButton3;
    private CustomButton customButton4;
    private CustomButton customButton5;
    private CustomButton customButton6;
    private CustomButton customButton7;
    private CustomTable customTable1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private MenuBar menuBar1;
    private javax.swing.JLabel miniLabel;
    private RoundedPanel roundedPanel1;
    private RoundedPanel roundedPanel2;
    public TextF textF1;
    public TextF textF10;
    public TextF textF11;
    public TextF textF12;
    public TextF textF13;
    private TextF textF2;
    public TextF textF3;
    // End of variables declaration//GEN-END:variables
}
