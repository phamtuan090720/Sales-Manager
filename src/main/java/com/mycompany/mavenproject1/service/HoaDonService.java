/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;

import com.mycompany.mavenproject1.pojo.HoaDon;
import java.sql.Connection;
import java.sql.Date;
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
public class HoaDonService {

    private Connection conn;

    public HoaDonService(Connection conn) {
        this.conn = conn;
    }

    public List<HoaDon> getListHoaDon() throws SQLException {
        String sql = "SELECT * FROM salemanager.hoadon;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
       
        ResultSet rs = stm.executeQuery();
        List<HoaDon> Bills = new ArrayList<>();
        while (rs.next()) {
            HoaDon b = new HoaDon();
            b.setIdHoaDon(rs.getInt("idHoaDon"));
            b.setIDNhanVienBanHang(rs.getInt("IDNhanVienBanHang"));
            b.setNgayLap(rs.getDate("NgayLap"));
            b.setThanhTien(rs.getBigDecimal("ThanhTien"));
            b.setVAT(rs.getDouble("VAT"));
            Bills.add(b);
        }
        return Bills;
    }
    public List<HoaDon> getListHoaDonByDate(Date kw) throws SQLException {
        String sql = "SELECT * FROM salemanager.hoadon where NgayLap=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setDate(1, kw);
        ResultSet rs = stm.executeQuery();
        List<HoaDon> Bills = new ArrayList<>();
        while (rs.next()) {
            HoaDon b = new HoaDon();
            b.setIdHoaDon(rs.getInt("idHoaDon"));
            b.setIDNhanVienBanHang(rs.getInt("IDNhanVienBanHang"));
            b.setNgayLap(rs.getDate("NgayLap"));
            b.setThanhTien(rs.getBigDecimal("ThanhTien"));
            b.setVAT(rs.getDouble("VAT"));
            Bills.add(b);
        }
        return Bills;
    }
}
