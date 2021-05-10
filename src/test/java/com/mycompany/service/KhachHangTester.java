/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.pojo.KhachHang;
import com.mycompany.mavenproject1.service.KhachHangService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
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
public class KhachHangTester {

    private static Connection CONN;

    @BeforeAll
    public static void setUpClass() {
        try {
            CONN = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (CONN != null)
            try {
            CONN.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Kiem thu chuc nang tìm khách hàng theo tên")
    public void testWithKeyWord() {
        try {
            KhachHangService s = new KhachHangService(CONN);
            List<KhachHang> customers = s.getListKhachHang("tuan");

            customers.forEach(c -> {
                Assertions.assertTrue(c.getTenKhachHang().toLowerCase().contains("tuan"));
            });
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Kiem thu chuc nang tìm danh sách khách hàng theo tên")
    public void testUnknownWithKeyWord() {
        try {
            KhachHangService s = new KhachHangService(CONN);
            List<KhachHang> customers = s.getListKhachHang("43*&^&^GYGFUYGFUYGFHGD%$");

            Assertions.assertEquals(0, customers.size());
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testException() {
        Assertions.assertThrows(SQLDataException.class, () -> {
            new KhachHangService(CONN).getListKhachHang(null);
        });
    }

    @Test
    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            new KhachHangService(CONN).getListKhachHang("");
        });
    }

    @ParameterizedTest
    @CsvSource({",090,997,aaa,0", "uyen,,989,aaa,0", "uyen,0970,,aaa,0",
        "uyen,0906,99,,0"})
    @DisplayName("Thêm Khách Hàng Với SDT Hoặc Name Hoặc CMND Hoặc DiaChi null")
    @Tag("critical")
    public void testAddCustomerNullNameUserNamePassMajor(String name, String sdt, String cmnd,
            String diachi, int diem) {

        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang c = new KhachHang();
            c.setTenKhachHang(name);
            c.setSDT(sdt);
            c.setCMND(cmnd);
            c.setDiaChi(diachi);
            c.setDiem(diem);

            Assertions.assertFalse(s.addKhachHang(c));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @ParameterizedTest
    @DisplayName("update-customer-null-Name-or-sdt-or-cmnd-or-diachi")
    @CsvSource({"1,,090,998,aaa,0", "1,uyen,,998,aaa,0", "1,uyen,090,,aaa,0",
        "1,uyen,090,99,,0"})
    public void testUpdateCustomerFailed(int Id, String name, String sdt, String cmnd,
            String diachi, int diem) {

        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang c = new KhachHang();
            c.setIdKhachHangThanThiet(Id);
            c.setTenKhachHang(name);
            c.setSDT(sdt);
            c.setCMND(cmnd);
            c.setDiaChi(diachi);
            c.setDiem(diem);
            Assertions.assertFalse(s.updateKhachHang(c));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @DisplayName("Kiem thu chuc nang tìm khách hàng theo cmnd hoac sdt")
    public void testFindKhacHangByCMNDOrSDT() {
        String SDT = "1223456789";
        String cmnd = "123456789";
        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang khByCMND = s.findKhachHangByCMND(cmnd);
            KhachHang khBySDT = s.findKhachHangBySDT(SDT);
            KhachHang khByCMNDAndSDT = s.findKhachHangBySDTAndCMND(SDT, cmnd);
            Assertions.assertTrue(khByCMND != null && khByCMND.getCMND().equals(cmnd));
            Assertions.assertTrue(khBySDT != null && khByCMND.getSDT().equals(SDT));
            Assertions.assertTrue(khByCMNDAndSDT != null && khByCMNDAndSDT.getCMND().equals(cmnd) && khByCMNDAndSDT.getSDT().equals(SDT));
        } catch (SQLException ex2) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex2);
        }

    }

    @Test
    @DisplayName("Kiem thu chuc nang Add Khach Hang")
    public void testAddKhachHangSuccess() throws SQLException {

        KhachHangService s = new KhachHangService(CONN);
        KhachHang c = new KhachHang();
        c.setTenKhachHang("tran");
        c.setSDT("035123456");
        c.setCMND("0789456123");
        c.setDiaChi("Chuồng Heo");
        c.setDiem(0);
        Assertions.assertTrue(s.addKhachHang(c));

    }

    @Test
    @DisplayName("Kiem thu chuc nang Add Khach Hang Trùng CMND hoặc SDT")
    public void testAddKhachHangDuplicateCMND()  {

        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang c = new KhachHang();
            c.setTenKhachHang("ngan");
            c.setSDT("0363902002");
            c.setCMND("066200000083");
            c.setDiaChi("Chuồng Heo");
            c.setDiem(0);
            Assertions.assertFalse(s.addKhachHang(c));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//
    @Test
    @DisplayName("Kiem thu chuc nang Add Khach Hang Trùng CMND hoặc SDT")
    public void testAddKhachHangDuplicateSDT()  {

        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang kh = new KhachHang();
            kh.setTenKhachHang("uyen");
            kh.setSDT("0358833453");
            kh.setCMND("066200000080");
            kh.setDiaChi("Chuồng Heo");
            kh.setDiem(0);
            Assertions.assertFalse(s.addKhachHang(kh));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @DisplayName("Kiem thu chuc nang Update Khach Hang thành công")
    public void testUpdateKhachHangSuccess() throws SQLException {

        KhachHangService s = new KhachHangService(CONN);
        KhachHang c = new KhachHang();
        c.setIdKhachHangThanThiet(6);
        c.setTenKhachHang("Tuan Pham");
        c.setSDT("0368921284");
        c.setCMND("0966123456");
        c.setDiaChi("Chuồng Lợn");
        c.setDiem(0);
        Assertions.assertTrue(s.updateKhachHang(c));

    }

    @Test
    @DisplayName("Kiem thu chuc nang Update Khach Hang Trùng CMND ")
    public void testUpdateKhachHangDuplicateCMND()  {
        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang c = new KhachHang();
            c.setIdKhachHangThanThiet(14);
            c.setTenKhachHang("Nghia");
            c.setSDT("0345987987");
            c.setCMND("066200000082");
            c.setDiaChi("Chuồng Heo");
            c.setDiem(0);
            Assertions.assertFalse(s.updateKhachHang(c));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("Kiem thu chuc nang Update Khach Hang Trùng SDT ")
    public void testUpdateKhachHangDuplicateSDT()  {
        try {
            KhachHangService s = new KhachHangService(CONN);
            KhachHang c2 = new KhachHang();
            c2.setIdKhachHangThanThiet(57);
            c2.setTenKhachHang("Nghia");
            c2.setSDT("0345987987");
            c2.setCMND("0123987654");
            c2.setDiaChi("Chuồng Heo");
            c2.setDiem(0);
            Assertions.assertFalse(s.updateKhachHang(c2));
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
