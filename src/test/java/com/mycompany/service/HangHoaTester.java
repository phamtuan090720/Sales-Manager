/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import com.mycompany.mavenproject1.pojo.HangHoa;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLDataException;
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
public class HangHoaTester {
     private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    @Test
    @DisplayName("Test Tìm Kiếm Hàng Hóa Theo Tên Hàng Hóa")
    @Tag("Search-product-name")
    public void testSreachKeyWord(){
         try {
             HangHoaService  s = new HangHoaService(conn);
             List<HangHoa> listHangHoa = s.getListHangHoa("redbull");
             listHangHoa.forEach(p -> {
                Assertions.assertTrue(p.getTenHang().toLowerCase().contains("redbull"));
            });
         } catch (SQLException ex) {
             Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    @Test
    @DisplayName("Test Tìm Kiếm Hàng Hóa Theo Tên Hàng Hóa Không có trong Database")
    @Tag("Search-product-name")
    public void testWithUnknownKeyWord() {
        try {
            HangHoaService s = new HangHoaService(conn);
            List<HangHoa> products = s.getListHangHoa("hahaHiHiDoNgoc");
            Assertions.assertEquals(0, products.size());
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    @DisplayName("Test Tìm Kiếm Hàng Hóa Theo Tên Hàng Hóa Có Số Lượng Lớn Hơn Không")
    @Tag("Search-product-name")
    public void testSreachKeyWordCountBiggerZero(){
         try {
             HangHoaService  s = new HangHoaService(conn);
             List<HangHoa> listHangHoa = s.getListHangHoaConTrongKho("redbull");
             listHangHoa.forEach(p -> {
                Assertions.assertTrue(p.getTenHang().toLowerCase().contains("redbull") && p.getSoLuong() > 0);
            });
         } catch (SQLException ex) {
             Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    @Test
    @DisplayName("Test Tìm Kiếm Hàng Hóa Theo ID")
    public void getHangHoaByID(){
         try {
             //ID hàng hóa là 2 có trong CSDL
             // ID hàng hóa là 1 không có trong CSDL
             HangHoaService  s = new HangHoaService(conn);
             HangHoa hangHoa2 = s.getHangHoaById(2);
             HangHoa hangHoa1 = s.getHangHoaById(1);
             Assertions.assertTrue(hangHoa2 != null);
             Assertions.assertTrue(hangHoa1 == null);
         } catch (SQLException ex) {
             Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    @Test
    @DisplayName("Add sản phẩn với name = null ")
    @Tag("add-product")
    public void tesAddProductWithNameNull() {
        HangHoa p = new HangHoa();
        p.setTenHang(null);
        p.setGiaBan(new BigDecimal(15000));
        p.setGiaMua(new BigDecimal(10000));
        p.setXuatXu(1);
        p.setSoLuong(18);
        p.setDonViTinh("chai");
        p.setLoaiHang(3);
        HangHoaService s = new HangHoaService(conn);
        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            s.addHangHoa(p);
        });
    }
    @Test
    @DisplayName("Add sản phẩn với xuất xứ không đúng")
    @Tag("add-product")
    public void tesAddProductWithInvalidOrigin() {
             HangHoa p = new HangHoa();
             p.setTenHang("Bia Tiger");
             p.setGiaBan(new BigDecimal(15000));
             p.setGiaMua(new BigDecimal(10000));
             p.setXuatXu(99);
             p.setSoLuong(18);
             p.setDonViTinh("chai");
             p.setLoaiHang(3);
             HangHoaService s = new HangHoaService(conn);
             Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
                 s.addHangHoa(p);
             });
    }
    @Test
    @DisplayName("Thêm hàng hóa với loại hàng hóa không có trong DataBase")
    @Tag("add-product")
    public void tesAddProductWithInvalidCate() {
            HangHoa p = new HangHoa();
            p.setTenHang("Bia Tiger");
            p.setGiaBan(new BigDecimal(15000));
            p.setGiaMua(new BigDecimal(10000));
            p.setXuatXu(1);
            p.setSoLuong(18);
            p.setDonViTinh("chai");
            p.setLoaiHang(77);
            
            HangHoaService s = new HangHoaService(conn);
            Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
                 s.addHangHoa(p);
             });
    }
    
    @Test
    @DisplayName("Thêm hàng hóa với các trường thuộc tính đứng")
    @Tag("add-product")
    public void tesAddProduct() {
        try {
            HangHoa p = new HangHoa();
            p.setTenHang("Bia Tiger");
            p.setGiaBan(new BigDecimal(15000));
            p.setGiaMua(new BigDecimal(10000));
            p.setXuatXu(1);
            p.setSoLuong(18);
            p.setDonViTinh("chai");
            p.setLoaiHang(3);
            
            HangHoaService s = new HangHoaService(conn);
            Assertions.assertTrue(s.addHangHoa(p));
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @ParameterizedTest
    @CsvSource({"Bia Heniken, 10000, 15000, 100, 2, 2, 50, true",
        "Bia Heniken, 10000, 15000, 100, 99, 2, 50, false",
        "Bia Heniken, 10000, 15000, 100, 99, 88, 50, false",
        ", 10000, 15000,100, 2, 2, 50, false"})
    public void tesAddProductParams(String name, BigDecimal importPrice,
            BigDecimal price, int count,  int cateId, int originId, String unit,boolean expected) throws SQLException {
        try {
            HangHoa p = new HangHoa();
            p.setTenHang(name);
            p.setGiaMua(importPrice);
            p.setGiaBan(price);
            p.setDonViTinh(unit);
            p.setSoLuong(count);
            p.setLoaiHang(cateId);
            p.setXuatXu(originId);
            HangHoaService s = new HangHoaService(conn);
            Assertions.assertEquals(s.addHangHoa(p),expected);
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        };
    }
    @ParameterizedTest
    @CsvSource({", 10000, 15000, 100, 2, 2, chai", 
         "Bia Tiger,10000, 15000, 100, 2 , 2,"})
    @DisplayName("Thêm Khách Hàng Với Name Hoặc Unit empty")
    @Tag("critical")
    public void  testAddProductNullNameUnit(String name, BigDecimal importPrice,
            BigDecimal price, int count,  int cateId, int originId, String unit) { 
        
        try {
            HangHoaService s = new HangHoaService(conn);
            
            HangHoa p = new HangHoa();
            p.setTenHang(name);
            p.setGiaMua(importPrice);
            p.setGiaBan(price);
            p.setDonViTinh(unit);
            p.setSoLuong(count);
            p.setLoaiHang(cateId);
            p.setXuatXu(originId);
            
            Assertions.assertFalse(s.addHangHoa(p));
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    @ParameterizedTest
    @DisplayName("update-product-null-Name-or-unit")
    @CsvSource({"37,, 10000, 13000, 100, 2, 2,chai ",
                "37,Bia SaiGon,10000 ,13000 ,100 , 2, 2, "})
    public void testUpdateProductFailed(int Id, String name, BigDecimal importPrice,
            BigDecimal price, int count,  int cateId, int originId, String unit) {  
          
        try {
            HangHoaService s = new HangHoaService(conn);
            HangHoa p = new HangHoa();
            p.setIdHangHoa(Id);
            p.setTenHang(name);
            p.setGiaMua(importPrice);
            p.setGiaBan(price);
            p.setDonViTinh(unit);
            p.setSoLuong(count);
            p.setLoaiHang(cateId);
            p.setXuatXu(originId);
            
            Assertions.assertFalse(s.updateHangHoa(p));
            
        } catch (SQLException ex) {
            Logger.getLogger(HangHoaTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }       
    
        
}
