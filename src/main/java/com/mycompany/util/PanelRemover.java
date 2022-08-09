/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Acer
 */
public class PanelRemover {
	public static void removeP(JPanel jp,JComponent jc){
		jp.removeAll();
		jp.add(jc);
		jp.repaint();
		jp.revalidate();
	}
}
