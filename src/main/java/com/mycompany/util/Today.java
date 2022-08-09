/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author kanna
 */
public class Today {

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }
}
