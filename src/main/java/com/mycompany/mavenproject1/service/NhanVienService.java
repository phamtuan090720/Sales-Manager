/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;

import com.mycompany.mavenproject1.pojo.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Admin
 */
public class NhanVienService {
     private Connection conn; 
     public NhanVienService(Connection conn) {
        this.conn = conn;
    }
    public NhanVien Login(String username,String password) throws SQLException{
        NhanVien nv = null;
        String sql = "SELECT * FROM salemanager.nhanvien where TaiKhoan=? and MatKhau=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, username);
        stm.setString(2, password);
        ResultSet rs = stm.executeQuery();
        if(!rs.next()){
            return nv;
        }
        else{
            nv = new NhanVien();
            nv.setMaNhanVien(rs.getInt("MaNhanVien"));
            nv.setTaiKhoan(rs.getString("TaiKhoan"));
            nv.setTenNhanVien(rs.getString("TenNhanVien"));
            nv.setNghiepVu(rs.getString("NghiepVu"));
            return nv;
        }   
    }
    
}
