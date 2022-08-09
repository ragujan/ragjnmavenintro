/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import com.mycompany.gui.FoodNavi;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author acer
 */
public class CreateObject {

    public static void make(JFrame jf) {
        jf.setVisible(true);
    }

    public static void make(JFrame jf, JFrame thisjf) {
        jf.setVisible(true);
        thisjf.dispose();
    }

    public static void make(JFrame jf, JDialog thisjf) {
        

        thisjf.dispose();
        jf.setVisible(true);

    }

    public static void make(JDialog jd) {
        jd.setVisible(true);
    }

    public static void make(JDialog jd, JFrame thisjf) {
        jd.setVisible(true);
        thisjf.dispose();
    }

    public static void make(JFrame closing, JFrame jf, boolean isDispose) {
        make(jf);

        if (isDispose) {
            closing.dispose();
        }

    }

    public static void make(FoodNavi foodNavi, JDialog thisJF) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
