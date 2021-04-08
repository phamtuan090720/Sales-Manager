/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.pojo;

/**
 *
 * @author Admin
 */
public class NhanVien {
    private int MaNhanVien;
    private String TenNhanVien;
    private String NghiepVu;
    private String TaiKhoan;    
    private String MatKhau;
    @Override
    public String toString() {
        return "NhanVien{" + "MaNhanVien=" + MaNhanVien + ", TenNhanVien=" + TenNhanVien + ", NghiepVu=" + NghiepVu + ", TaiKhoan=" + TaiKhoan + ", MatKhau=" + MatKhau + '}';
    }

    public int getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(int MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public String getNghiepVu() {
        return NghiepVu;
    }

    public void setNghiepVu(String NghiepVu) {
        this.NghiepVu = NghiepVu;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }
}
