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
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class NhanVienService {
     private Connection conn;
     public NhanVien nhanVienLogin;
     public NhanVienService(Connection conn) {
        this.conn = conn;
    }
    public NhanVien Login(String username,String password) throws SQLException{
        if(username == null || password == null){
            throw new SQLDataException();
        }
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
            nv.setNghiepVu(rs.getInt("NghiepVuID"));
            nv.setMatKhau(rs.getString("MatKhau"));
            this.nhanVienLogin = nv;
            return nv;
        }   
    }
    public List<NhanVien> getListNhanVien(String kw) throws SQLException{
        if(kw==null)
            throw new SQLDataException();
            String sql = "SELECT * FROM salemanager.nhanvien where TenNhanVien like concat('%',?,'%');";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
             List<NhanVien> listNhanVien = new ArrayList<>();
             while(rs.next()){
                 NhanVien nhanVien = new NhanVien();
                 nhanVien.setMaNhanVien(rs.getInt("MaNhanVien"));
                 nhanVien.setTenNhanVien(rs.getString("TenNhanVien"));
                 nhanVien.setNghiepVu(rs.getInt("NghiepVuID"));
                 nhanVien.setTaiKhoan(rs.getString("TaiKhoan"));
                 nhanVien.setMatKhau(rs.getString("MatKhau"));
                 listNhanVien.add(nhanVien);
             }
         return listNhanVien;
    }
    public boolean checkNumberInString(String kw) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        boolean containsNumber = pattern.matcher(kw).matches();
        return containsNumber;
    }
    public boolean checkValidateInphut(String kw) {
        return kw.isEmpty();
    }

    ;
    public boolean checkLengthGreater6(String kw) {
        return kw.length() < 6;
    }

 

    public boolean addNhanVien(NhanVien nv) throws SQLException {
        if (checkLengthGreater6(nv.getMatKhau()) == true 
                || checkNumberInString(nv.getTenNhanVien()) == true
                || checkValidateInphut(nv.getMatKhau()) == true
                || checkValidateInphut(nv.getTenNhanVien()) == true
                || checkValidateInphut(nv.getTaiKhoan()) == true
                || nv.getNghiepVu() <= 0 == true
                ){
            throw new SQLDataException();
        };
        String sql = "INSERT INTO `salemanager`.`nhanvien` (`TenNhanVien`, `NghiepVuID`, `TaiKhoan`, `MatKhau`) VALUES (?, ?, ?, ?);";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, nv.getTenNhanVien());
        stm.setInt(2, nv.getNghiepVu());
        stm.setString(3, nv.getTaiKhoan());
        stm.setString(4, nv.getMatKhau());
        int row = stm.executeUpdate();
        return row > 0;
    }
     public boolean deleleNhanVien(int nhanVienid) throws SQLException {
        String sql = "DELETE FROM salemanager.nhanvien WHERE MaNhanVien=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, nhanVienid);
        int row = stm.executeUpdate();
        return row > 0;
    }
    public boolean updateNhanVien(NhanVien nv) throws SQLException {
        if (checkLengthGreater6(nv.getMatKhau()) == true 
                || checkNumberInString(nv.getTenNhanVien()) == true
                || checkValidateInphut(nv.getMatKhau()) == true
                || checkValidateInphut(nv.getTenNhanVien()) == true
                || checkValidateInphut(nv.getTaiKhoan()) == true
                || nv.getNghiepVu() <= 0 == true
                ){
            throw new SQLDataException();
        };
        String sql = "UPDATE `salemanager`.`nhanvien` SET `TenNhanVien` = ?, `NghiepVuID` = ?, `TaiKhoan` = ?, `MatKhau` = ? WHERE (`MaNhanVien` = ?);";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, nv.getTenNhanVien());
        stm.setInt(2, nv.getNghiepVu());
        stm.setString(3, nv.getTaiKhoan());
        stm.setString(4, nv.getMatKhau());
        stm.setInt(5,nv.getMaNhanVien());
        int row = stm.executeUpdate();
        return row > 0;
    }
    public NhanVien findAccount(String kw) throws SQLException{
        
        NhanVien nv = null;
        String sql = "SELECT * FROM salemanager.nhanvien where TaiKhoan=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        if(!rs.next()){
            return nv;
        }
        else{
            nv = new NhanVien();
            nv.setMaNhanVien(rs.getInt("MaNhanVien"));
            nv.setTaiKhoan(rs.getString("TaiKhoan"));
            nv.setTenNhanVien(rs.getString("TenNhanVien"));
            nv.setNghiepVu(rs.getInt("NghiepVuID"));
            nv.setMatKhau(rs.getString("MatKhau"));
            return nv;
        }   
        
    };
    
}
