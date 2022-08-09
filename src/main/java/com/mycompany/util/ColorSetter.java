/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.awt.Color;
import javax.swing.JComponent;

/**
 *
 * @author Acer
 */
public class ColorSetter {

	public static void setC(JComponent[] jc, Color c, int item) {
		if (item == 1) {
			for (int i = 0; i < jc.length; i++) {
				jc[i].setBackground(c);
			}
		}
		if (item == 2) {
			for (int i = 0; i < jc.length; i++) {
				jc[i].setForeground(c);
			}
		}

	}

}
