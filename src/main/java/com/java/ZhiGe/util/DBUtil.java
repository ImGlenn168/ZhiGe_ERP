package com.java.ZhiGe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DBUtil {
    private static final String USER = "root";
    private static final String PWD = "123456";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ZhiGeDB?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static Statement stmt;
    private static Connection con;
    private static DBUtil utils = null;
    // 预编译语句对象
    private static PreparedStatement pstmt;

    public DBUtil() {
    }

    // 不是线程安全的 如果有并发访问实例化的时候会出现线程安全的问题，解决办法加同步锁synchronized
    public synchronized static DBUtil getDBUtil() {
        if (utils == null) {
            utils = new DBUtil();
            return utils;
        }
        return utils;
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(URL, USER, PWD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public static Statement getStatement() {
        if (stmt == null) {
            try {
                if (con == null) {
                    con = getConnection();
                }
                stmt = con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stmt;
    }

    // 预编译语句对象
    public static PreparedStatement getPstmt(String sql) {
        try {
            if (con == null) {
                con = getConnection();
            }
            pstmt = con.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pstmt;
    }

}
