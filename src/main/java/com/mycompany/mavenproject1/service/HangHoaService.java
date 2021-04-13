/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;
import com.mycompany.mavenproject1.pojo.HangHoa;
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
public class HangHoaService {
     private Connection conn;
     public HangHoaService(Connection conn) {
        this.conn = conn;
    }
    public List<HangHoa> getListHangHoa(String kw) throws SQLException{
        if (kw == null)
            throw new SQLDataException();
        String sql = "SELECT * FROM salemanager.hanghoa WHERE tenHang like concat('%', ?, '%')";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<HangHoa> products = new ArrayList<>();
        while (rs.next()) {
            HangHoa p = new HangHoa();
            p.setIdHangHoa(rs.getInt("idHangHoa"));
            p.setTenHang(rs.getString("tenHang"));
            p.setLoaiHang(rs.getInt("loaiHangId"));
            p.setXuatXu(rs.getInt("xuatXuId"));
            p.setGiaBan(rs.getDouble("giaBan"));
            p.setDonViTinh(rs.getString("donViTinh"));
            p.setSoLuong(rs.getInt("soLuong"));
            p.setNgaySX(rs.getDate("ngaySX"));
            p.setHanSD(rs.getDate("hanSD"));
            products.add(p);
        }
        return products;
    }
}
