/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author frhna
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/artifydb";
    private static final String USER = "root";
    private static final String PASSWORD = "_Fanan02";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
