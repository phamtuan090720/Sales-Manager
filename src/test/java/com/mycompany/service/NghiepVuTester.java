/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.mavenproject1.pojo.NghiepVu;
import com.mycompany.mavenproject1.service.NghiepVuService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class NghiepVuTester {
     private static Connection CONN;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            CONN = jdbcUtil.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(NghiepVuTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (CONN != null)
            try {
                CONN.close();
            } catch (SQLException ex) {
                Logger.getLogger(NghiepVuTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     @Test
    public void testQuantity() {
        try {
            int expected = 2;
            NghiepVuService s = new NghiepVuService(CONN);
            List<NghiepVu> nv = s.getNghiepVu();
            Assertions.assertTrue(nv.size() >= expected);
        } catch (SQLException ex) {
            Logger.getLogger(NghiepVuTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
     public void testUniqueName() {
         try {
           NghiepVuService s = new NghiepVuService(CONN);
            List<NghiepVu> nv = s.getNghiepVu();
            List<String> names = new ArrayList<>();
            nv.forEach(c -> {
                names.add(c.getTenNghiepVu());
            });
            Set<String> names2 = new HashSet<>(names);
    
            Assertions.assertTrue(names.size() == names2.size());
         } catch (SQLException ex) {
            Logger.getLogger(NghiepVuTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
