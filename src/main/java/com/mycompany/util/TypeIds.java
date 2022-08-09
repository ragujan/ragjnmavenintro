/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.model.MySql;

/**
 *
 * @author Acer
 */
public class TypeIds {

	String tbname;
	String colname;
	String value;

	public TypeIds(String tbname, String colname, String value) {
		this();
		this.tbname = tbname;
		this.colname = colname;
		this.value = value;
	}

	public TypeIds() {

	}

	public static String getId(String tbname, String colname, String value) {

		String id = null;
		try {
			ResultSet rs = MySql.sq("SELECT * FROM `" + tbname + "` WHERE `" + colname + "`='" + value + "'");
			rs.next();
			id = rs.getString(tbname + "_" + "id");

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(TypeIds.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(TypeIds.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}

	public static String getId(String tbname, String value) {

		String id = null;
		String colname = tbname+"_name";
		try {
			ResultSet rs = MySql.sq("SELECT * FROM `" + tbname + "` WHERE `" + colname+ "`='" + value + "'");
			rs.next();
			id = rs.getString(tbname + "_" + "id");

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(TypeIds.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(TypeIds.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}
}

