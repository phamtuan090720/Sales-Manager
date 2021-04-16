/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.ChiTietHoaDon;
import com.mycompany.mavenproject1.pojo.HoaDon;
import com.mycompany.mavenproject1.pojo.KhachHang;
import com.mycompany.mavenproject1.service.HoaDonService;
import com.mycompany.mavenproject1.service.KhachHangService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BillManagementController implements Initializable {

    @FXML
    private TextField txtIDBill;
    @FXML
    private TextField txtIDNhanVIen;
    @FXML
    private TextField txtDonGiaHangHoa;
    @FXML
    private DatePicker pcNgayLap;
    @FXML
    private TextField txtVAT;
    @FXML
    private TextField txtThanhTien;
    @FXML
    private TextField txtIDKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtDiaChiKhachHang;
    @FXML
    private TextField txtCMNDKhachHang;
    @FXML
    private TextField txtDiemTichLuy;
    @FXML
    private Button btnEditBill;
    @FXML
    private Button btnDeleteBill;
    @FXML
    private Text txtTongTien;
    @FXML
    private Button btnPrintBill;
    @FXML
    private DatePicker pcSearch;
    @FXML
    private TableView<ChiTietHoaDon> tbChiTietBill;
    @FXML
    private TableView<HoaDon> tbListBill;
    @FXML
    private Button btnSuaListHoaDon;
    @FXML
    private Button btnXoaListHoaDon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadTables();
        LoadBill();
        LoadDataBill();
        this.txtIDBill.setDisable(true);
        this.txtIDKhachHang.setDisable(true);
        this.tbListBill.setRowFactory(obj -> {
            TableRow r = new TableRow();
            SetDisableButtonListBill(false);
            r.setOnMouseClicked(e -> {
                SetDisableButtonListBill(true);
                CheckIsEmpty();
                HoaDon hd = this.tbListBill.getSelectionModel().getSelectedItem();
                try {
                    Connection conn = jdbcUtil.getConn();
                    KhachHangService khs = new KhachHangService(conn);
                    KhachHang kh = khs.findKhachHangByID(hd.getIDKhachHangThanThiet());
                    if (kh != null) {
                        txtIDKhachHang.setText(Integer.toString(kh.getIdKhachHangThanThiet()));
                        txtTenKhachHang.setText(kh.getTenKhachHang());
                        txtSDTKhachHang.setText(kh.getSDT());
                        txtDiaChiKhachHang.setText(kh.getDiaChi());
                        txtCMNDKhachHang.setText(kh.getCMND());
                        txtDiemTichLuy.setText(Integer.toString(kh.getDiem()));

                    }

                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtIDBill.setText(Integer.toString(hd.getIdHoaDon()));
                txtIDNhanVIen.setText(Integer.toString(hd.getIDNhanVienBanHang()));
                pcNgayLap.setValue(convertToLocalDateViaSqlDate(hd.getNgayLap()));
                txtThanhTien.setText(hd.getThanhTien().toString());
                NumberFormat format = NumberFormat.getPercentInstance(Locale.US);
                String percentage = format.format(hd.getVAT());
                txtVAT.setText(percentage);
                btnSuaListHoaDon.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
//                        try {
//
//                            if (CheckIsEmpty() == true) {
//                                Utils.getBox("Vui Long Nhap Thong Tin Day Du", Alert.AlertType.ERROR).show();
//                            } else {
//                                Connection conn = jdbcUtil.getConn();
//                                HoaDon hd = new HoaDon();
//                                HoaDonService hds = new HoaDonService(conn);
//                                hd.setIdHoaDon(hd.getIdHoaDon());
//                                hd.setIDKhachHangThanThiet(hd.getIDKhachHangThanThiet());
//                                hd.setNgayLap(Date.valueOf(pcNgayLap.getValue()));
//                                hd.setThanhTien(hd.getThanhTien());
//                                hd.setIDNhanVienBanHang(hd.getIDNhanVienBanHang());
////                                hd.setVAT(Double.parseDouble(txtVAT.getText()));
//                                if (hds.UpdateListHoaDon(hd) == true) {
//                                    Utils.getBox("Succesfull", Alert.AlertType.ERROR).show();
//                                    conn.close();
//                                    LoadDataBill();
//                                    
//                                } else {
//                                    Utils.getBox("Fail", Alert.AlertType.ERROR).show();
//                                }
//                            }
//                        } catch (SQLException ex) {
//                            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                });
            });
            return r;
        });
        this.pcSearch.getEditor().textProperty().addListener((obj) -> {
            try {
                Connection conn = jdbcUtil.getConn();
                HoaDonService s = new HoaDonService(conn);
                if (this.pcSearch.getEditor().getText().isEmpty() != true) {
                    this.tbListBill.setItems(FXCollections.observableList(s.getListHoaDonByDate(Date.valueOf(this.pcSearch.getValue()))));
                }

            } catch (SQLException ex) {
                Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        pcNgayLap.getEditor().setDisable(true);
    }
    public void ResetInput(){
        txtIDBill.setText("");
        txtIDNhanVIen.setText("");
        txtThanhTien.setText("");
        txtVAT.setText("");
        pcNgayLap.setValue(null);
    }
    public boolean CheckIsEmpty() {
        return txtIDNhanVIen.getText().isEmpty()
                || txtThanhTien.getText().isEmpty()
                || txtVAT.getText().isEmpty()
                || txtSDTKhachHang.getText().isEmpty()
                || txtTenKhachHang.getText().isEmpty()
                || txtDiaChiKhachHang.getText().isEmpty()
                || txtCMNDKhachHang.getText().isEmpty();
    }

    public void SetDisableButtonListBill(boolean isAction) {
        if (isAction == true) {
            btnSuaListHoaDon.setDisable(false);
            btnXoaListHoaDon.setDisable(false);
        } else {
            btnSuaListHoaDon.setDisable(true);
            btnXoaListHoaDon.setDisable(true);
        }
    }

    public void SetDisableButtonBill(boolean isAction) {
        if (isAction == true) {
            btnDeleteBill.setDisable(false);
            btnEditBill.setDisable(false);
            btnPrintBill.setDisable(false);
        } else {
            btnDeleteBill.setDisable(true);
            btnEditBill.setDisable(true);
            btnPrintBill.setDisable(true);

        }
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void LoadTables() {
        TableColumn colIdBill = new TableColumn("ID Hóa Đơn");
        colIdBill.setCellValueFactory(new PropertyValueFactory("idHoaDon"));
        TableColumn colIdNhanVien = new TableColumn("ID Nhân Viên");
        colIdNhanVien.setCellValueFactory(new PropertyValueFactory("IDNhanVienBanHang"));
        TableColumn colNgayLap = new TableColumn("Ngày Lập");
        colNgayLap.setCellValueFactory(new PropertyValueFactory("NgayLap"));
        TableColumn colThanhTIen = new TableColumn("Thành Tiền");
        colThanhTIen.setCellValueFactory(new PropertyValueFactory("ThanhTien"));
        TableColumn colVAT = new TableColumn("VAT");
        colVAT.setCellValueFactory(new PropertyValueFactory("VAT"));
        this.tbListBill.getColumns().addAll(colIdBill, colIdNhanVien, colNgayLap, colThanhTIen, colVAT);
    }

    public void LoadBill() {
        TableColumn colIdHH = new TableColumn("Mã Hàng Hóa");
        colIdHH.setCellValueFactory(new PropertyValueFactory("idHangHoa"));
        TableColumn colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuong"));
        TableColumn colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("donGia"));
        this.tbChiTietBill.getColumns().addAll(colIdHH, colSoLuong, colDonGia);
    }

    public void LoadDataBill() {
        try {
            this.tbListBill.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            HoaDonService s = new HoaDonService(conn);
            this.tbListBill.setItems(FXCollections.observableList(s.getListHoaDon()));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
