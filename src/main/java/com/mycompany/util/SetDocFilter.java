/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author kanna
 */
public abstract class SetDocFilter {

    JComponent jc;

    public SetDocFilter(JComponent jc) {
        this.jc = jc;
    }

    public abstract void doThings();

    public void set(JTextField jt) {
        AbstractDocument absdoc = (AbstractDocument) jt.getDocument();
        absdoc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws
                    BadLocationException {

                fb.remove(offset, length);
                doThings();

            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                    AttributeSet attr) throws BadLocationException {

                fb.insertString(offset, string, attr);
                doThings();
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                    AttributeSet attrs) throws BadLocationException {
                String jtText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = jtText.substring(0, offset) + text;
                String regex = "([1-9][0-9]+|[1-9])";
                boolean match = com.mycompany.util.BasicValidator.regexMatcher(newText, regex);

                fb.replace(offset, length, text, attrs);
                doThings();
            }
        });
    }
}
