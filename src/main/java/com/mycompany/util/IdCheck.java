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
public class IdCheck {

	public static boolean isExits(String tname, String tid, String value) {
		boolean state = false;

		ResultSet rs;
		try {
			rs = MySql.sq("SELECT * FROM `" + tname + "` WHERE `" + tid + "`='" + value + "'");
			if (rs.next()) {
				state = true;
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		}

		return state;
	}

	public static boolean isExits(String tname, String value) {
		boolean state = false;
		String tid = tname + "_id";
		ResultSet rs;
		try {
			rs = MySql.sq("SELECT * FROM `" + tname + "` WHERE `" + tid + "`='" + value + "'");
			if (rs.next()) {
				state = true;
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		}

		return state;
	}
        public static int rowCount = 0;
	public static boolean isLikeExits(String tname, String colname, String value) {
		boolean state = false;
		String tid = tname + "_id";
		ResultSet rs;
		ResultSet countrs;
		rowCount = 0;
		try {
			rs = MySql.sq("SELECT * FROM `" + tname + "` WHERE `" + colname + "` LIKE '%" + value + "%'");
			countrs = MySql.sq("SELECT * FROM `" + tname + "` WHERE `" + colname + "` LIKE '%" + value + "%'");

			if (rs.next()) {
				while (countrs.next()) {
					rowCount++;
					
				}
				state = true;
			}
			System.out.println(rowCount);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(IdCheck.class.getName()).log(Level.SEVERE, null, ex);
		}

		return state;
	}
}
