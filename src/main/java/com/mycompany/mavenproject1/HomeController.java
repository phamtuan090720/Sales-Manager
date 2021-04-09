/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.NhanVien;
import com.mycompany.mavenproject1.service.NhanVienService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {


    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnStatistic;
    @FXML
    private Button btnBill;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnDasboard;
    @FXML
    private AnchorPane PaneEmployee;
    @FXML
    private AnchorPane PaneDasboard;
    @FXML
    private TextField kwEmployee;
    @FXML
    private Button btnSearchEMployee;
    @FXML
    private TableView<NhanVien> tbEmployee;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.btnLogout.setOnMouseClicked(e->{
            try {
                LoadScene("Login.fxml", e);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        loadTables();
        LoadDataTableEmployee("");
        this.kwEmployee.textProperty().addListener((obj) -> {
            LoadDataTableEmployee(this.kwEmployee.getText());
        });
        this.PaneDasboard.setVisible(true);
        
        this.btnEmployee.setOnMouseClicked(e->{
            PaneDasboard.setVisible(false);
            PaneEmployee.setVisible(true);
        });
        this.btnDasboard.setOnMouseClicked(e->{
            PaneDasboard.setVisible(true);
            PaneEmployee.setVisible(false);
        });
    }
    public void LoadDataTableEmployee(String kw){
         try {
            this.tbEmployee.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            NhanVienService s = new NhanVienService(conn);
            System.out.println(s.getListNhanVien(kw));
            this.tbEmployee.setItems(FXCollections.observableList(s.getListNhanVien(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void LoadScene (String fxml, MouseEvent event) throws IOException{
        Parent root =  FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }
    private void loadTables(){
        TableColumn colId = new TableColumn("Mã Nhân Viên");
        colId.setCellValueFactory(new PropertyValueFactory("MaNhanVien"));
        TableColumn colName = new TableColumn("Tên Nhân Viên");
        colName.setCellValueFactory(new PropertyValueFactory("TenNhanVien"));
        TableColumn colAccount = new TableColumn("Tài Khoản");
        colAccount.setCellValueFactory(new PropertyValueFactory("TaiKhoan"));
         TableColumn colPassword = new TableColumn("Mật Khẩu");
        colPassword.setCellValueFactory(new PropertyValueFactory("MatKhau"));
        this.tbEmployee.getColumns().addAll(colId,colName,colAccount,colPassword);
    }
    
}
