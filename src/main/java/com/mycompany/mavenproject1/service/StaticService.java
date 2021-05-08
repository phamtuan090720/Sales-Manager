/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.service;

import com.mycompany.mavenproject1.pojo.Quy;
import java.math.BigDecimal;
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

    public int staticByMonth(int month, int idHH) throws SQLException {
        if (month <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(salemanager.chitiethoadon.SoLuong)  AS tong"
                + " FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=? and IDHangHoa=? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, idHH);
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }

    public BigDecimal staticDoanhThuSanPhamByMonth(int month, int idHH) throws SQLException {
        if (month <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=? and IDHangHoa=? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, idHH);
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }

    public BigDecimal staticTongDoanhThuByMonth(int month) throws SQLException {
        if (month <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=? and year(salemanager.hoadon.NgayLap)=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }

    public List<Integer> getDistinctIDHangHoaByMonth(int month) throws SQLException {
        if (month <= 0) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT salemanager.chitiethoadon.IDHangHoa"
                + " AS IDHangHoa"
                + " FROM salemanager.chitiethoadon,salemanager.hoadon "
                + "Where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap)=? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("IDHangHoa"));
        }
        return listIDHangHoa;

    }

    public int staticByYear(int year, int idHH) throws SQLException {
        if (year <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(salemanager.chitiethoadon.SoLuong) AS tong "
                + "from salemanager.chitiethoadon,salemanager.hoadon "
                + "where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon "
                + "and  year(salemanager.hoadon.NgayLap)=? "
                + "and IDHangHoa=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        stm.setInt(2, idHH);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }

    public BigDecimal staticDoanhThuSanPhamByYear(int year, int idHH) throws SQLException {
        if (year <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and year(salemanager.hoadon.NgayLap)=? and IDHangHoa=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        stm.setInt(2, idHH);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }

    public BigDecimal staticTongDoanhThuByYear(int year) throws SQLException {
        if (year <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and year(salemanager.hoadon.NgayLap)=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }

    public List<Integer> getDistinctIDHangHoaByYear(int year) throws SQLException {
        if (year <= 0) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT salemanager.chitiethoadon.IDHangHoa"
                + " AS IDHangHoa"
                + " FROM salemanager.chitiethoadon,salemanager.hoadon "
                + "Where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("IDHangHoa"));
        }
        return listIDHangHoa;

    }

    public int staticByQuy(Quy quy, int idHH) throws SQLException {
        if (quy == null && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(salemanager.chitiethoadon.SoLuong)"
                + "  AS tong FROM salemanager.chitiethoadon,salemanager.hoadon "
                + "where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon "
                + "and month(salemanager.hoadon.NgayLap) between ? and"
                + " ? and IDHangHoa=? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, idHH);
        stm.setInt(4, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }

    public List<Integer> getDistinctIDHangHoaByQuy(Quy quy) throws SQLException {
        if (quy == null) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT salemanager.chitiethoadon.IDHangHoa"
                + "  AS IDHangHoa FROM salemanager.chitiethoadon,salemanager.hoadon "
                + "where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon "
                + "and month(salemanager.hoadon.NgayLap) between ? and"
                + " ? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("IDHangHoa"));
        }
        return listIDHangHoa;

    }

    public BigDecimal staticDoanhThuSanPhamByQuy(Quy quy, int idHH) throws SQLException {
        if (quy == null && idHH <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap) between ? and ? and IDHangHoa=? and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, idHH);
        stm.setInt(4, year);

        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }

    public BigDecimal staticTongDoanhThuByQuy(Quy quy) throws SQLException {
        if (quy == null) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(salemanager.hoadon.ThanhTien)"
                + "  AS TongDoanThu FROM salemanager.chitiethoadon,salemanager.hoadon"
                + " where salemanager.hoadon.idHoaDon = salemanager.chitiethoadon.IDHoaDon"
                + " and month(salemanager.hoadon.NgayLap) between ? and ? "
                + "and year(salemanager.hoadon.NgayLap)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }
}
