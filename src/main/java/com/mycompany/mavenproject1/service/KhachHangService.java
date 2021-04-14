/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;

import com.mycompany.mavenproject1.pojo.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangService {

    private Connection conn;

    public KhachHangService(Connection conn) {
        this.conn = conn;
    }

    public List<KhachHang> getListKhachHang(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException();
        }
        String sql = "SELECT * FROM salemanager.khachhangthanthiet WHERE TenKhachHang like concat('%', ?, '%')";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<KhachHang> listkhachhang = new ArrayList<>();
        while (rs.next()) {
            KhachHang c = new KhachHang();
            c.setIdKhachHangThanThiet(rs.getInt("idKhachHangThanThiet"));
            c.setTenKhachHang(rs.getString("TenKhachHang"));
            c.setSDT(rs.getString("SDT"));
            c.setDiaChi(rs.getString("DiaChi"));
            c.setDiem(rs.getInt("Diem"));
            c.setCMND(rs.getString("CMND"));
            listkhachhang.add(c);
        }
        return listkhachhang;
    }

    public boolean addKhachHang(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO `salemanager`.`khachhangthanthiet` "
                + "(`TenKhachHang`, `SDT`, `DiaChi`, `Diem`, `CMND`)"
                + " VALUES (?, ?, ?, ?, ?);";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kh.getTenKhachHang());
        stm.setString(2, kh.getSDT());
        stm.setString(3, kh.getDiaChi());
        stm.setInt(4, kh.getDiem());
        stm.setString(5, kh.getCMND());
        int row = stm.executeUpdate();
        return row > 0;
    }
}
