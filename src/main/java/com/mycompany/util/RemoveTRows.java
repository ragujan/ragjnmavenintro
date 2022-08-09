/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class RemoveTRows {
	public static void remove(JTable jt){
		DefaultTableModel dftm = (DefaultTableModel)jt.getModel();
		dftm.setRowCount(0);
	}
}
