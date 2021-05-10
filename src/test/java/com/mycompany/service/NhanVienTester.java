/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.pojo.NhanVien;
import com.mycompany.mavenproject1.service.NhanVienService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
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
public class NhanVienTester {

    private static Connection CONN;

    @BeforeAll
    public static void setUpClass() {
        try {
            CONN = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (CONN != null)
            try {
            CONN.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Tìm thấy List Nhân Viên theo kw")
    public void testWithKeyWord() {
        try {
            NhanVienService s = new NhanVienService(CONN);
            List<NhanVien> employees = s.getListNhanVien("tuan");

            employees.forEach(p -> {
                Assertions.assertTrue(p.getTaiKhoan().toLowerCase().contains("tuan"));
            });
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Tìm Nhân Viên theo kw không thấy trong Database")
    public void testUnknownWithKeyWord() {
        try {
            NhanVienService s = new NhanVienService(CONN);
            List<NhanVien> employees = s.getListNhanVien("43*&^&^GYGFUY");

            Assertions.assertEquals(0, employees.size());
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @ParameterizedTest
    @DisplayName("Thêm Nhân Viên Đúng")
    @CsvSource({"Uyeen,uyen12334,123456,1"})
    public void testAddSuccessNhanVien(String name,String username, String pass,
                int majorId) throws SQLException {
        
        NhanVienService s = new NhanVienService(CONN);
            
        NhanVien e = new NhanVien(); 
        
        e.setTenNhanVien(name);
        e.setTaiKhoan(username);
        e.setMatKhau(pass);
        e.setNghiepVu(majorId);
        System.out.println(e);
        Assertions.assertTrue(s.addNhanVien(e));
    }
    @ParameterizedTest
    @DisplayName("Thêm Nhân Viên vơi pass dưới 6 ký tự")
    @CsvSource({"Uyeen,uyen12334,12345,1"})
    public void testAddNhanVienPasswordUnder6(String name,String username, String pass,
                int majorId) {
        
        try {
            NhanVienService s = new NhanVienService(CONN);
            
            NhanVien e = new NhanVien();
            
            e.setTenNhanVien(name);
            e.setTaiKhoan(username);
            e.setMatKhau(pass);
            e.setNghiepVu(majorId);
            System.out.println(e);
            Assertions.assertFalse(s.addNhanVien(e));
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @ParameterizedTest
    @DisplayName("Thêm Nhân Viên Trùng Username")
    @CsvSource({"Uyeen,tuan123,123456,1"})
    public void testAddDuplicateUsernameNhanVien(String name,String username, String pass,
                int majorId) throws SQLException {
        
        NhanVienService s = new NhanVienService(CONN);
            
        NhanVien e = new NhanVien(); 
        
        e.setTenNhanVien(name);
        e.setTaiKhoan(username);
        e.setMatKhau(pass);
        e.setNghiepVu(majorId);
        System.out.println(e);
        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            s.addNhanVien(e);
        });
        
    }
    @ParameterizedTest
    @DisplayName("update-employee")
    @CsvSource({"12,Uyên,uyen26,123456,1"})
    public void testUpdateEmployee(int Id, String name,String username, String pass,
                int majorId) throws SQLException {
        
        NhanVienService s = new NhanVienService(CONN);
            
        NhanVien e = new NhanVien(); 
        
        e.setMaNhanVien(Id);
        e.setTenNhanVien(name);
        e.setTaiKhoan(username);
        e.setMatKhau(pass);
        e.setNghiepVu(majorId); 
        
        Assertions.assertTrue(s.updateNhanVien(e));
    }
    @ParameterizedTest
    @DisplayName("update-employee-password dưới 6 ký tự")
    @CsvSource({"12,Uyên,uyen26,12345,1"})
    public void testUpdateEmployeeWithPassUnder6(int Id, String name,String username, String pass,
                int majorId) {
        
        try {
            NhanVienService s = new NhanVienService(CONN);
            
            NhanVien e = new NhanVien();
            
            e.setMaNhanVien(Id);
            e.setTenNhanVien(name);
            e.setTaiKhoan(username);
            e.setMatKhau(pass);
            e.setNghiepVu(majorId);
            
            Assertions.assertFalse(s.updateNhanVien(e));
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @ParameterizedTest
    @DisplayName("update-employee-null-Name-or-username-or-pass")
    @CsvSource({"12,Uyên,,123456,1","12,Uyên,uyen27,,1","12,,uyen28,123456,1"})
    public void testUpdateEmployeeNullNameUsernamePass(int Id, String name, String username, 
            String pass, int majorId) {  
          
        
            NhanVienService s = new NhanVienService(CONN);
            NhanVien e = new NhanVien();
            e.setMaNhanVien(Id);
            e.setTenNhanVien(name);
            e.setTaiKhoan(username);
            e.setMatKhau(pass);
            e.setNghiepVu(majorId);
             Assertions.assertThrows(NullPointerException.class, () -> {
                s.updateNhanVien(e);
            });
        
        
    }        
}
