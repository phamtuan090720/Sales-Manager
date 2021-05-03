/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.ChiTietHoaDon;
import com.mycompany.mavenproject1.pojo.HoaDon;
import com.mycompany.mavenproject1.pojo.Thang;
import com.mycompany.mavenproject1.service.ChiTietHoaDonService;
import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.HoaDonService;
import com.mycompany.mavenproject1.service.StaticService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class StaticByMonthController implements Initializable {

    @FXML
    private PieChart chartSoLuongHangBanDuoc;
    @FXML
    private PieChart ChartDoanhThu;
    @FXML
    private ComboBox<Thang> cbThang;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbThang.setItems(FXCollections.observableList(thang()));

//        getDataSoLuong(5);
//        ChartDoanhThu.setTitle("CoronaVirus Statistics COVID-19");
//        ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListData());
//        ChartDoanhThu.setData(ol);
//        for (PieChart.Data data : ChartDoanhThu.getData()) {
//            data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue());
//            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
//                }
//            });
//        }
    }

    ;
    public List<PieChart.Data> getListData() {
        List<PieChart.Data> list = new ArrayList<>();
        list.add(new PieChart.Data("haha", 10));
        return list;
    }

    ;
     public List<PieChart.Data> getListData2() {
        List<PieChart.Data> list = new ArrayList<>();
        list.add(new PieChart.Data("hihi", 20));
        list.add(new PieChart.Data("hoho", 10));
        return list;
    }

    ;
    public List<PieChart.Data> getDataSoLuong(int month) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = jdbcUtil.getConn();
            StaticService ss = new StaticService(conn);
            HangHoaService hhs = new HangHoaService(conn);
            for (int id : ss.getDistinctIDHangHoaByMonth(month)) {
                list.add(new PieChart.Data(hhs.getHangHoaById(id).getTenHang(), ss.staticByMonth(month, id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(StaticByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    ;
    public List<Thang> thang() {
        List<Thang> listM = new ArrayList<>();
        Thang m1 = new Thang();
        m1.setName("Thang 1");
        m1.setValue(1);
        Thang m2 = new Thang();
        m2.setName("Thang 2");
        m2.setValue(2);
        Thang m3 = new Thang();
        m3.setName("Thang 3");
        m3.setValue(3);
        Thang m4 = new Thang();
        m4.setName("Thang 4");
        m4.setValue(4);
        Thang m5 = new Thang();
        m5.setName("Thang 5");
        m5.setValue(5);
        Thang m6 = new Thang();
        m6.setName("Thang 6");
        m6.setValue(6);
        Thang m7 = new Thang();
        m7.setName("Thang 7");
        m7.setValue(7);
        Thang m8 = new Thang();
        m8.setName("Thang 8");
        m8.setValue(8);
        Thang m9 = new Thang();
        m9.setName("Thang 9");
        m9.setValue(9);
        Thang m10 = new Thang();
        m10.setName("Thang 10");
        m10.setValue(10);
        Thang m11 = new Thang();
        m11.setName("Thang 11");
        m11.setValue(11);
        Thang m12 = new Thang();
        m12.setName("Thang 12");
        m12.setValue(12);
        listM.add(m1);
        listM.add(m2);
        listM.add(m3);
        listM.add(m4);
        listM.add(m5);
        listM.add(m6);
        listM.add(m7);
        listM.add(m8);
        listM.add(m9);
        listM.add(m10);
        listM.add(m11);
        listM.add(m12);
        return listM;
    }

    @FXML
    private void changeThang(ActionEvent event) {
        System.out.println(getDataSoLuong(this.cbThang.getSelectionModel().getSelectedItem().getValue()));
        int month = this.cbThang.getSelectionModel().getSelectedItem().getValue();

        if (getDataSoLuong(month).size() <= 0) {
            chartSoLuongHangBanDuoc.setTitle("Không có dữ liệu bán hàng trong tháng "+month);
            ObservableList<PieChart.Data> ol2 = FXCollections.observableArrayList(getDataSoLuong(month));
            chartSoLuongHangBanDuoc.setData(ol2);
        } else {
            chartSoLuongHangBanDuoc.setTitle("Thống Kê Tháng " + month);
            ObservableList<PieChart.Data> ol2 = FXCollections.observableArrayList(getDataSoLuong(month));
            chartSoLuongHangBanDuoc.setData(ol2);
            for (PieChart.Data data : chartSoLuongHangBanDuoc.getData()) {
                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue());
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
                    }
                });
            }
        }
    }

}
