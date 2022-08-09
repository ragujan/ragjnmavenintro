/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Acer
 */
public abstract class TableListenerAbs {

	JTable jt;

	public TableListenerAbs(JTable jt) {
		this.jt = jt;
	}


	protected abstract void foo(ListSelectionEvent e);

	final public void tableListenerRag(JTable jt) {
		jt.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				int row = jt.getSelectedRow();
				if (row != -1) {
					foo(e);
				}

			}

		});
	}
}


