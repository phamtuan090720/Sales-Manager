/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;

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
public class StaticService {

    private Connection conn;

    public StaticService(Connection conn) {
        this.conn = conn;
    }
    public int staticByMonth(int month,int idHH) throws SQLException{
        if(month <=0 && idHH<=0)
            throw new SQLDataException();
        int count = 0;
        String sql = "SELECT SUM(salemanager.chitiethoadon.SoLuong)  AS tong"
                + " FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=? and IDHangHoa=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,month);
        stm.setInt(2, idHH);
        ResultSet rs = stm.executeQuery();
         while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }
    public List<Integer> getDistinctIDHangHoaByMonth(int month) throws SQLException{
        if(month<=0)
           throw new SQLDataException(); 
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT salemanager.chitiethoadon.IDHangHoa"
                + " AS IDHangHoa"
                + " FROM salemanager.chitiethoadon,salemanager.hoadon "
                + "Where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,month);
         ResultSet rs = stm.executeQuery();
         while (rs.next()) {
            listIDHangHoa.add(rs.getInt("IDHangHoa"));
        }
        return listIDHangHoa;
        
    }
}
