/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.pojo.HangHoa;
import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class CreateBillTestTer {
    private static Connection conn;
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CreateBillTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CreateBillTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Tag("Search-product-name")
    public void testSearchWithKeyWord() {
        try {
            HangHoaService s = new HangHoaService(conn);
            
            List<HangHoa> products = s.getListHangHoaConTrongKho("Coca");
            products.forEach(p -> {
                Assertions.assertTrue(p.getTenHang().toLowerCase().contains("Coca".toLowerCase()) );
            });
        } catch (SQLException ex) {
            Logger.getLogger(CreateBillTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Tag("Search-product-name")
    public void testSearchWithUnknownKeyWord() {
        try {
            HangHoaService s = new HangHoaService(conn);
            List<HangHoa> products = s.getListHangHoaConTrongKho("agdjhsgdjhsa232432");
            
            Assertions.assertEquals(0, products.size());
        } catch (SQLException ex) {
            Logger.getLogger(CreateBillTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    @Tag("Search-product-CateId")
    public void testSearchCateId() {
        try {
            HangHoaService s = new HangHoaService(conn);
            
            List<HangHoa> products = s.SeachHangHoaConLaiByIdHangHoa(2);
            products.forEach(p -> {
                Assertions.assertTrue(p.getLoaiHang()== 2);
            });
        } catch (SQLException ex) {
            Logger.getLogger(CreateBillTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
