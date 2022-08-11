/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.components;

import com.mycompany.view.frameutil.MainTheme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Acer
 */
public class TextF extends JTextField {

	public TextF() {
		setBorder(new EmptyBorder(5, 8, 3, 1));
		setBackground(MainTheme.mainColor);
		setForeground(Color.WHITE);
		setSize(100, 30);
	}

	@Override
	public void paint(Graphics grphcs) {
		super.paint(grphcs);
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		int width = getWidth();
		int height = getHeight();
		g2.setColor(MainTheme.fourthColor);
		g2.fillRect(2, height - 1, width - 4, 1);

		g2.dispose();
	}
}
