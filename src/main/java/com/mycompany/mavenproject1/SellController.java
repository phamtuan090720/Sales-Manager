/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SellController implements Initializable {


    @FXML
    private Button btnAddBill;
    @FXML
    private Button btnResetBill;
    @FXML
    private TextField txtIDHangHoa;
    @FXML
    private TextField txtSoLuongHangHoa;
    @FXML
    private TextField txtDonGiaHangHoa;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtCMNDKhachHang;
    @FXML
    private ComboBox<?> cbKhachHang;
    @FXML
    private TableView<?> tbBill;
    @FXML
    private Button btnEditBill;
    @FXML
    private Button btnPrintBill;
    @FXML
    private Button btnDeleteBill;
    @FXML
    private TableView<?> tbListProduct;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<?> cbXuatXu;
    @FXML
    private ComboBox<?> cbLoaiHang;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
