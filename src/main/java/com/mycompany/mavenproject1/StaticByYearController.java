/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.StaticService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class StaticByYearController implements Initializable {

    @FXML
    private PieChart chartSoLuongHangBanDuoc;
    @FXML
    private PieChart ChartDoanhThu;
    @FXML
    private Text txtTongDoanhThu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int year = java.time.LocalDate.now().getYear();
        chartSoLuongHangBanDuoc.setTitle("Thống Kê Số Lượng Sản Phẩm Bán Ra Năm " + year);
        ObservableList<PieChart.Data> listSL = FXCollections.observableArrayList(getDataSoLuong(year));
        chartSoLuongHangBanDuoc.setData(listSL);

        for (PieChart.Data data : chartSoLuongHangBanDuoc.getData()) {
            data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue());
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
                }
            });
        }
        ChartDoanhThu.setTitle("Thống Kê Doanh Thu Năm " + year);
         ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListDataDoanhThuSanPham(year));
            ChartDoanhThu.setData(ol);
            for (PieChart.Data data : ChartDoanhThu.getData()) {
                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue() + "VNĐ");
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
                    }
                });
            }
        try {
            TongDoanhThu(year);
        } catch (NullPointerException e) {
            txtTongDoanhThu.setText("0 VNĐ");
        }

    }

    public List<PieChart.Data> getListDataDoanhThuSanPham(int year) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = jdbcUtil.getConn();
            StaticService ss = new StaticService(conn);
            HangHoaService hhs = new HangHoaService(conn);
            for (int id : ss.getDistinctIDHangHoaByYear(year)) {
                list.add(new PieChart.Data(hhs.getHangHoaById(id).getTenHang(), Double.valueOf(ss.staticDoanhThuSanPhamByYear(year, id).toString())));
            }

        } catch (SQLException ex) {
            Logger.getLogger(StaticByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    ;
    public List<PieChart.Data> getDataSoLuong(int year) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = jdbcUtil.getConn();
            StaticService ss = new StaticService(conn);
            HangHoaService hhs = new HangHoaService(conn);
            for (int id : ss.getDistinctIDHangHoaByYear(year)) {
                list.add(new PieChart.Data(hhs.getHangHoaById(id).getTenHang(), ss.staticByYear(year, id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(StaticByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void TongDoanhThu(int year) {
        try {
            Connection conn = jdbcUtil.getConn();
            StaticService ss = new StaticService(conn);
            txtTongDoanhThu.setText(ss.staticTongDoanhThuByYear(year).toString() + " VNĐ");

        } catch (SQLException ex) {
            Logger.getLogger(StaticByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
;
}
