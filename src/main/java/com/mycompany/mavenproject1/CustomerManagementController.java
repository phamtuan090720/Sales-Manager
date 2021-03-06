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
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoadTables();
        LoadData("");
        // ch??? cho ph??p nh???p s???
        txtDiemCustomer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtDiemCustomer.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        txtCMNDCustomer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtCMNDCustomer.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        txtSDTCustomer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtSDTCustomer.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }

            }
        });
        this.tbCustomer.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                SetDisableButtonCustomer(false);
                KhachHang c = this.tbCustomer.getSelectionModel().getSelectedItem();
                try {
                    txtNameCustomer.setText(c.getTenKhachHang());
                    txtSDTCustomer.setText(c.getSDT());
                    txtDiaChiCustomer.setText(c.getDiaChi());
                    txtDiemCustomer.setText(Integer.toString(c.getDiem()));
                    txtCMNDCustomer.setText(c.getCMND());

                } catch (NullPointerException event) {
                    Utils.getBox("Vui L??ng Nh???p ?????y ????? Th??ng Tin", Alert.AlertType.INFORMATION).show();
                }

                btnDeleteCustomer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                                .showAndWait().ifPresent(bt -> {
                                    if (bt == ButtonType.OK) {
                                        try {
                                            Connection conn = jdbcUtil.getConn();
                                            KhachHangService s = new KhachHangService(conn);

                                            if (s.deleleKhachHang(c.getIdKhachHangThanThiet())) {
                                                Utils.getBox("Th??nh C??ng", Alert.AlertType.INFORMATION).show();
                                                SetDisableButtonCustomer(true);
                                                ResetInput();
                                                LoadData("");
                                            } else {
                                                Utils.getBox("Th???t B???i", Alert.AlertType.ERROR).show();
                                            }

                                            conn.close();
                                        } catch (SQLException ex) {

                                            ex.printStackTrace();
                                            Logger.getLogger(CustomerManagementController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                    }
                });
                btnUpdateCustomer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            Connection conn = jdbcUtil.getConn();
                            KhachHangService s = new KhachHangService(conn);
                            KhachHang khUpdate = new KhachHang();
                            khUpdate.setIdKhachHangThanThiet(c.getIdKhachHangThanThiet());

                            try {
                                if (checkValidateInphut() == true) {
                                    Utils.getBox("Vui L??ng Nh???p ?????y ????? Th??ng Tin", Alert.AlertType.INFORMATION).show();
                                } else if (checkNumberInString() == true) {
                                    Utils.getBox("Trong T??n Kh??ng ???????c Ch???a S???", Alert.AlertType.ERROR).show();
                                } else if (CheckLengthCMND() == true) {
                                    Utils.getBox("????? d??i c???a CMND kh??ng h???p l???", Alert.AlertType.ERROR).show();
                                } else if (CheckLengthSDT() == true) {
                                    Utils.getBox("????? d??i c???a SDT kh??ng h???p l???", Alert.AlertType.ERROR).show();
                                } else {

                                    khUpdate.setTenKhachHang(txtNameCustomer.getText());
                                    khUpdate.setSDT(txtSDTCustomer.getText());
                                    khUpdate.setDiaChi(txtDiaChiCustomer.getText());
                                    khUpdate.setCMND(txtCMNDCustomer.getText());
                                    if (txtDiemCustomer.getText().isEmpty() != true) {
                                        try {
                                            khUpdate.setDiem(Integer.parseInt(txtDiemCustomer.getText()));
                                        } catch (NullPointerException event) {

                                        }
                                    }
                                    if (s.updateKhachHang(khUpdate) == true) {
                                        Utils.getBox("Th??nh C??ng", Alert.AlertType.INFORMATION).show();
                                        conn.close();
                                        ResetInput();
                                        LoadData("");
                                        SetDisableButtonCustomer(true);

                                    } else {
                                        Utils.getBox("Th???t B???i", Alert.AlertType.INFORMATION).show();
                                        conn.close();
                                    }
                                }

                            } catch (NullPointerException event) {
                                Utils.getBox("Vui L??ng Nh???p ?????y ????? Th??ng Tin", Alert.AlertType.INFORMATION).show();
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(CustomerManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            return r;
        });
        this.kwCustomer.textProperty().addListener((obj) -> {
            LoadData(this.kwCustomer.getText());
        });
        btnRsInputCustomer.setOnMouseClicked(e -> {
            ResetInput();
            btnDeleteCustomer.setDisable(true);
            btnUpdateCustomer.setDisable(true);
            btnAddCustomer.setDisable(false);
        });
    }

    public boolean CheckLengthCMND() {
        return txtCMNDCustomer.getText().length() < 9 || txtCMNDCustomer.getText().length() > 12;
    }

    public boolean CheckLengthSDT() {
        return txtSDTCustomer.getText().length() < 10 || txtSDTCustomer.getText().length() > 12;
    }

    public boolean checkNumberInString() {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        boolean containsNumber = pattern.matcher(txtNameCustomer.getText()).matches();
        return containsNumber;
    }

    ;
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

    public void CheckValidationInputNumber() {

    }

    public void ResetInput() {
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
            if (txtDiemCustomer.getText().isEmpty() != true) {
                try {
                    kh.setDiem(Integer.parseInt(txtDiemCustomer.getText()));
                } catch (NullPointerException e) {

                }

            }
            try {
                if (checkValidateInphut() == true) {
                    Utils.getBox("Vui L??ng Nh???p ?????y ????? Th??ng Tin", Alert.AlertType.ERROR).show();
                } else if (checkNumberInString() == true) {
                    Utils.getBox("Trong T??n Kh??ng ???????c Ch???a S???", Alert.AlertType.ERROR).show();
                } else if (CheckLengthCMND() == true) {
                    Utils.getBox("????? d??i c???a CMND kh??ng h???p l???", Alert.AlertType.ERROR).show();
                } else if (CheckLengthSDT() == true) {
                    Utils.getBox("????? d??i c???a SDT kh??ng h???p l???", Alert.AlertType.ERROR).show();
                } else {
                    if (s.findKhachHangByCMND(txtCMNDCustomer.getText()) != null || s.findKhachHangBySDT(txtSDTCustomer.getText()) != null) {

                        if (s.findKhachHangByCMND(txtCMNDCustomer.getText()) != null && s.findKhachHangBySDT(txtSDTCustomer.getText()) == null) {
                            Utils.getBox("CMND ???? t???n t???i!", Alert.AlertType.INFORMATION).show();
                        } else if (s.findKhachHangBySDT(txtSDTCustomer.getText()) != null && s.findKhachHangByCMND(txtCMNDCustomer.getText()) == null) {
                            Utils.getBox("SDT ???? t???n t???i!", Alert.AlertType.INFORMATION).show();
                        } else {
                            Utils.getBox("CMND v?? SDT ???? t???n t???i!", Alert.AlertType.INFORMATION).show();
                        }
                    } else {
                        kh.setTenKhachHang(txtNameCustomer.getText());
                        kh.setSDT(txtSDTCustomer.getText());
                        kh.setDiaChi(txtDiaChiCustomer.getText());
                        kh.setCMND(txtCMNDCustomer.getText());
                        if (s.addKhachHang(kh) == true) {
                            Utils.getBox("Th??nh C??ng", Alert.AlertType.INFORMATION).show();
                            this.LoadData("");
                            SetDisableButtonCustomer(true);
                            ResetInput();
                            conn.close();
                        } else {
                            Utils.getBox("Th???t B???i", Alert.AlertType.INFORMATION).show();
                            conn.close();
                        }

                    }

                }

            } catch (NullPointerException e) {
                Utils.getBox("Vui L??ng Nh???p ?????y ????? Th??ng Tin", Alert.AlertType.INFORMATION).show();
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

    public void LoadTables() {
        TableColumn colId = new TableColumn("M?? Kh??ch H??ng");
        colId.setCellValueFactory(new PropertyValueFactory("idKhachHangThanThiet"));
        TableColumn colName = new TableColumn("T??n Kh??ch H??ng");
        colName.setCellValueFactory(new PropertyValueFactory("TenKhachHang"));
        TableColumn colSDT = new TableColumn("S??? ??i???n Tho???i");
        colSDT.setCellValueFactory(new PropertyValueFactory("SDT"));
        TableColumn colAddress = new TableColumn("?????a Ch???");
        colAddress.setCellValueFactory(new PropertyValueFactory("DiaChi"));
        TableColumn colScore = new TableColumn("Diem");
        colScore.setCellValueFactory(new PropertyValueFactory("Diem"));
        TableColumn colCMND = new TableColumn("CMND");
        colCMND.setCellValueFactory(new PropertyValueFactory("CMND"));
        this.tbCustomer.getColumns().addAll(colId, colName, colSDT, colAddress, colScore, colCMND);
    }
}
