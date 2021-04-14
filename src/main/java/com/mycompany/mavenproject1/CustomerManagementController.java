/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.KhachHang;
import com.mycompany.mavenproject1.service.KhachHangService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author Admin
 */
public class CustomerManagementController implements Initializable {


    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnUpdateCustomer;
    @FXML
    private Button btnDeleteCustomer;
    @FXML
    private Button btnRsInputCustomer;
    @FXML
    private TableView<KhachHang> tbCustomer;
    @FXML
    private TextField kwCustomer;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private TextField txtNameCustomer;
    @FXML
    private TextField txtSDTCustomer;
    @FXML
    private TextField txtDiaChiCustomer;
    @FXML
    private TextField txtCMNDCustomer;
    @FXML
    private TextField txtDiemCustomer;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LoadTables();
        LoadData("");
        this.tbCustomer.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e->{
                SetDisableButtonCustomer(false);
                KhachHang c = this.tbCustomer.getSelectionModel().getSelectedItem();
                txtNameCustomer.setText(c.getTenKhachHang());
                txtSDTCustomer.setText(c.getSDT());
                txtDiaChiCustomer.setText(c.getDiaChi());
                txtDiemCustomer.setText(Integer.toString(c.getDiem()));
                txtCMNDCustomer.setText(c.getCMND());
           });
            return r;
        });
        this.kwCustomer.textProperty().addListener((obj) -> {
            LoadData(this.kwCustomer.getText());
        });
        btnRsInputCustomer.setOnMouseClicked(e->{
            ResetInput();
        });
    }    
   
     private void LoadData(String kw) {
        try {
            this.tbCustomer.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            KhachHangService s = new KhachHangService(conn);
            this.tbCustomer.setItems(FXCollections.observableList(s.getListKhachHang(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ResetInput(){
        txtNameCustomer.setText(null);
        txtDiaChiCustomer.setText(null);
        txtSDTCustomer.setText(null);
        txtDiemCustomer.setText(null);
        txtCMNDCustomer.setText(null);
        btnAddCustomer.setDisable(false);
    }
     public void SetDisableButtonCustomer(boolean isAction) {
        if (isAction == true) {
            btnDeleteCustomer.setDisable(true);
            btnUpdateCustomer.setDisable(true);
            btnAddCustomer.setDisable(false);
        } else {
            btnDeleteCustomer.setDisable(false);
            btnUpdateCustomer.setDisable(false);
            btnAddCustomer.setDisable(true);
        }

    }
    @FXML
    private void addKhachHang(ActionEvent event) {
        Connection conn;
        try {
            conn = jdbcUtil.getConn();
            KhachHangService s = new KhachHangService(conn);
            KhachHang kh = new KhachHang();
            if(txtDiemCustomer.getText().isEmpty()!=true)
            {
                 kh.setDiem(Integer.parseInt(txtDiemCustomer.getText())); 
            }
            if (checkValidateInphut()==true)
                Utils.getBox("Vui Lòng Nhập Đầy Đủ Thông Tin", Alert.AlertType.INFORMATION).show();
            else{
                kh.setTenKhachHang(txtNameCustomer.getText());
                kh.setSDT(txtSDTCustomer.getText());
                kh.setDiaChi(txtDiaChiCustomer.getText());
                kh.setCMND(txtCMNDCustomer.getText());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public boolean checkValidateInphut() {
        return txtNameCustomer.getText().isEmpty()
                || txtDiaChiCustomer.getText().isEmpty()
                || txtSDTCustomer.getText().isEmpty()
                || txtCMNDCustomer.getText().isEmpty();
    }
    public void LoadTables(){
        TableColumn colId = new TableColumn("Mã Khách Hàng");
        colId.setCellValueFactory(new PropertyValueFactory("idKhachHangThanThiet"));
        TableColumn colName = new TableColumn("Tên Khách Hàng");
        colName.setCellValueFactory(new PropertyValueFactory("TenKhachHang"));
        TableColumn colSDT = new TableColumn("Số Điện Thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory("SDT"));
        TableColumn colAddress = new TableColumn("Địa Chỉ");
        colAddress.setCellValueFactory(new PropertyValueFactory("DiaChi"));
        TableColumn colScore = new TableColumn("Diem");
        colScore.setCellValueFactory(new PropertyValueFactory("Diem"));
        TableColumn colCMND = new TableColumn("CMND");
        colCMND.setCellValueFactory(new PropertyValueFactory("CMND"));
        this.tbCustomer.getColumns().addAll(colId,colName,colSDT,colAddress,colScore,colCMND);
    }
}
