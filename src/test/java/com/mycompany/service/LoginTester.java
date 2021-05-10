/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.service.NhanVienService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Admin
 */
public class LoginTester {

    private static Connection CONN;

    @BeforeAll
    public static void setUpClass() {
        try {
            CONN = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(LoginTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (CONN != null)
            try {
            CONN.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Đăng Nhập Với Username password empty")
    @Tag("critical")
    public void testLoginUsernamePasswordEmpty() {
        try {
            NhanVienService s = new NhanVienService(CONN);
            String username = "";
            String password = "";
            System.out.println(username + password);
            boolean isHaveNhanVien = false;
            if (s.Login(username, password) != null) {
                isHaveNhanVien = true;
            }
            Assertions.assertFalse(isHaveNhanVien);
        } catch (SQLException ex) {
            Logger.getLogger(LoginTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @ParameterizedTest
    @CsvSource({"tuan123,''", "'',123"})
    @DisplayName("Đăng Nhập Với Username Hoặc password empty")
    @Tag("critical")
    public void  testLoginUsernamePasswordEmpty(String username, String password) throws SQLException {
        NhanVienService s = new NhanVienService(CONN);
        boolean isHaveNhanVien = false;
        if (s.Login(username, password) != null) {
            isHaveNhanVien = true;
        }
        Assertions.assertFalse(isHaveNhanVien);
    }
    
    @ParameterizedTest
    @CsvSource({"tuan123,'123'"})
    @DisplayName("Đăng Nhập sai username password")
    public void  testLoginFailed(String username, String password) throws SQLException {
        NhanVienService s = new NhanVienService(CONN);
        boolean isHaveNhanVien = false;
        if (s.Login(username, password) != null) {
            isHaveNhanVien = true;
        }
        Assertions.assertFalse(isHaveNhanVien);
    }
    @ParameterizedTest
    @CsvSource({"tuan123,'1'"})
    @DisplayName("Đăng Nhập thành công username password")
    public void  testLoginSuccess(String username, String password) throws SQLException {
        NhanVienService s = new NhanVienService(CONN);
        boolean isHaveNhanVien = false;
        if (s.Login(username, password) != null) {
            isHaveNhanVien = true;
        }
        Assertions.assertTrue(isHaveNhanVien);
    }
}
