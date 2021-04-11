/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.NghiepVu;
import com.mycompany.mavenproject1.pojo.NhanVien;
import com.mycompany.mavenproject1.service.NghiepVuService;
import com.mycompany.mavenproject1.service.NhanVienService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private AnchorPane PaneEmployee;
    @FXML
    private AnchorPane PaneDasboard;
    @FXML
    private TextField kwEmployee;
    @FXML
    private Button btnSearchEMployee;
    @FXML
    private TableView<NhanVien> tbEmployee;
    @FXML
    private TextField txtPasswordEmployee;
    @FXML
    private TextField txtAccountEmployee;
    @FXML
    private TextField txtNameEmployee;
    @FXML
    private Button btnUpdateEmpyee;
    @FXML
    private Button btnDeleteEmployee;
    @FXML
    private ComboBox<NghiepVu> cbNghiepVu;
    @FXML
    private Button btnAddEmployee;
    @FXML
    private Button btnRsInputEmployee;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = jdbcUtil.getConn();
            NghiepVuService s = new NghiepVuService(conn);
            this.cbNghiepVu.setItems(FXCollections.observableList(s.getNghiepVu()));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnLogout.setOnMouseClicked(e -> {
            try {
                LoadScene("Login.fxml", e);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.kwEmployee.textProperty().addListener((obj) -> {
            LoadDataTableEmployee(this.kwEmployee.getText());
        });
        this.PaneDasboard.setVisible(true);
        loadTables();
        LoadDataTableEmployee("");
        this.btnEmployee.setOnMouseClicked(e -> {
            PaneDasboard.setVisible(false);
            PaneEmployee.setVisible(true);
        });
        this.btnDasboard.setOnMouseClicked(e -> {
            PaneDasboard.setVisible(true);
            PaneEmployee.setVisible(false);
        });
        this.btnRsInputEmployee.setOnMouseClicked(e -> {
            ResetInputEmployee();
        });
        this.tbEmployee.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {

                try {
                    Connection conn = jdbcUtil.getConn();
                    NhanVien nv = this.tbEmployee.getSelectionModel().getSelectedItem();
                    SetDisableButtonEmployee(false);
                    NghiepVuService s = new NghiepVuService(conn);
                    txtNameEmployee.setText(nv.getTenNhanVien());
                    txtAccountEmployee.setText(nv.getTaiKhoan());
                    txtPasswordEmployee.setText(nv.getMatKhau());
                    cbNghiepVu.getSelectionModel().select(s.getNghepVuById(nv.getNghiepVu()));
                    conn.close();
                    btnDeleteEmployee.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                                    .showAndWait().ifPresent(bt -> {
                                        if (bt == ButtonType.OK) {
                                            try {
                                                Connection conn = jdbcUtil.getConn();
                                                NhanVienService s = new NhanVienService(conn);

                                                if (s.deleleNhanVien(nv.getMaNhanVien())) {
                                                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                                    SetDisableButtonEmployee(true);
                                                    ResetInputEmployee();
                                                    LoadDataTableEmployee("");
                                                } else {
                                                    Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                                }

                                                conn.close();
                                            } catch (SQLException ex) {

                                                ex.printStackTrace();
                                                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    });
                        }
                    });
                    btnUpdateEmpyee.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            try {
                                NhanVien nvUpdate = new NhanVien();
                                nvUpdate.setMaNhanVien(nv.getMaNhanVien());
                                nvUpdate.setTenNhanVien(txtNameEmployee.getText());
                                nvUpdate.setNghiepVu(cbNghiepVu.getSelectionModel().getSelectedItem().getIdNghiepVu());
                                nvUpdate.setTaiKhoan(txtAccountEmployee.getText());
                                nvUpdate.setMatKhau(txtPasswordEmployee.getText());
                                Connection conn = jdbcUtil.getConn();
                                NhanVienService s = new NhanVienService(conn);
                                if (s.updateNhanVien(nvUpdate)==true) {
                                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                    SetDisableButtonEmployee(true);
                                    ResetInputEmployee();
                                    LoadDataTableEmployee("");
                                } else {
                                    Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                }
                                conn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return r;
        });

    }

    public void ResetInputEmployee() {
        this.txtAccountEmployee.setText("");
        this.txtNameEmployee.setText("");
        this.txtPasswordEmployee.setText("");
        this.btnAddEmployee.setDisable(false);
    }

    public void SetDisableButtonEmployee(boolean isAction) {
        if (isAction == true) {
            btnDeleteEmployee.setDisable(true);
            btnUpdateEmpyee.setDisable(true);
            btnAddEmployee.setDisable(false);
        } else {
            btnDeleteEmployee.setDisable(false);
            btnUpdateEmpyee.setDisable(false);
            btnAddEmployee.setDisable(true);
        }

    }

    public void LoadDataTableEmployee(String kw) {
        try {
            this.tbEmployee.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            NhanVienService s = new NhanVienService(conn);
            this.tbEmployee.setItems(FXCollections.observableList(s.getListNhanVien(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadScene(String fxml, MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    private void loadTables() {
        TableColumn colId = new TableColumn("Mã Nhân Viên");
        colId.setCellValueFactory(new PropertyValueFactory("MaNhanVien"));
        TableColumn colName = new TableColumn("Tên Nhân Viên");
        colName.setCellValueFactory(new PropertyValueFactory("TenNhanVien"));
        TableColumn colAccount = new TableColumn("Tài Khoản");
        colAccount.setCellValueFactory(new PropertyValueFactory("TaiKhoan"));
        TableColumn colPassword = new TableColumn("Mật Khẩu");
        colPassword.setCellValueFactory(new PropertyValueFactory("MatKhau"));
        this.tbEmployee.getColumns().addAll(colId, colName, colAccount, colPassword);
    }

    @FXML
    private void addNhanVien(ActionEvent event) {
        try {
            Connection conn = jdbcUtil.getConn();
            NhanVienService s = new NhanVienService(conn);
            NhanVien nv = new NhanVien();
            nv.setTenNhanVien(txtNameEmployee.getText());
            nv.setNghiepVu(this.cbNghiepVu.getSelectionModel().getSelectedItem().getIdNghiepVu());
            nv.setTaiKhoan(txtAccountEmployee.getText());
            nv.setMatKhau(txtPasswordEmployee.getText());
            if (s.addNhanVien(nv) == true) {
                Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                this.LoadDataTableEmployee("");
                SetDisableButtonEmployee(true);
                ResetInputEmployee();
            } else {
                Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
            }

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
