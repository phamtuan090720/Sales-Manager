/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.ChiTietHoaDon;
import com.mycompany.mavenproject1.pojo.HangHoa;
import com.mycompany.mavenproject1.pojo.KhachHang;
import com.mycompany.mavenproject1.pojo.LoaiHang;
import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.KhachHangService;
import com.mycompany.mavenproject1.service.LoaiHangService;
import com.mycompany.mavenproject1.service.XuatXuService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
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
public class SellController implements Initializable {

    @FXML
    private Button btnAddBill;
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
    private ComboBox<KhachHang> cbKhachHang;
    @FXML
    private TableView<ChiTietHoaDon> tbBill;
    @FXML
    private Button btnEditBill;
    @FXML
    private Button btnPrintBill;
    @FXML
    private Button btnDeleteBill;
    @FXML
    private TableView<HangHoa> tbListProduct;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private ComboBox<LoaiHang> cbLoaiHang;
    @FXML
    private Text txtTongTien;
    @FXML
    private TextField kwSearch;
    @FXML
    private Button btnResetLoaiHang;
    @FXML
    private TextField txtDiemTichLuy;
    @FXML
    private Text txtNotiSearchKH;
    private List<ChiTietHoaDon> hoaDon;
    @FXML
    private Button btnReset;

    public List<ChiTietHoaDon> getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(List<ChiTietHoaDon> hoaDon) {
        this.hoaDon = hoaDon;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.hoaDon = new ArrayList<>();
        try {
            Connection conn = jdbcUtil.getConn();
            XuatXuService xxs = new XuatXuService(conn);
            LoaiHangService lhs = new LoaiHangService(conn);
            this.cbLoaiHang.setItems(FXCollections.observableList(lhs.getListLoaiHang()));

        } catch (SQLException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // các input ở đây chỉ được nhập số
        txtCMNDKhachHang.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtCMNDKhachHang.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });

        txtSDTKhachHang.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtSDTKhachHang.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        txtIDHangHoa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtIDHangHoa.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        txtSoLuongHangHoa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtSoLuongHangHoa.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        txtDonGiaHangHoa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtDonGiaHangHoa.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {

                }
            }
        });
        LoadTableListProduct();
        LoadDataHangHoa("");
        LoadTableBill();
        txtTongTien.setText("");
        this.kwSearch.textProperty().addListener((obj) -> {
            LoadDataHangHoa(this.kwSearch.getText());
        });
        this.txtSDTKhachHang.textProperty().addListener((obj) -> {
            SearchKhachHang(txtSDTKhachHang.getText(), txtCMNDKhachHang.getText());
        });
        this.txtCMNDKhachHang.textProperty().addListener((obj) -> {
            SearchKhachHang(txtSDTKhachHang.getText(), txtCMNDKhachHang.getText());
        });
        btnResetLoaiHang.setOnMouseClicked(e -> {
            cbLoaiHang.setPromptText("Loại Hàng");
            cbLoaiHang.setValue(null);
            LoadDataHangHoa("");
        });
        btnReset.setOnMouseClicked(e -> {
            txtSoLuongHangHoa.setText("");
            txtIDHangHoa.setText("");
            txtDonGiaHangHoa.setText("");
            txtIDHangHoa.setDisable(false);
        });
        this.tbListProduct.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                HangHoa hh = this.tbListProduct.getSelectionModel().getSelectedItem();
                txtIDHangHoa.setText(Integer.toString(hh.getIdHangHoa()));
                txtDonGiaHangHoa.setText(hh.getGiaBan().toString());
                txtSoLuongHangHoa.setText(Integer.toString(1));
            });
            return r;
        });
        disableButtonBill(false);
        this.tbBill.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                disableButtonBill(true);
                ChiTietHoaDon ct = this.tbBill.getSelectionModel().getSelectedItem();
                txtIDHangHoa.setText(Integer.toString(ct.getIdHangHoa()));
                txtIDHangHoa.setDisable(true);
                txtDonGiaHangHoa.setText(ct.getDonGia().toString());
                txtSoLuongHangHoa.setText(Integer.toString(ct.getSoLuong()));
                btnDeleteBill.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                                .showAndWait().ifPresent(bt -> {
                                    if (bt == ButtonType.OK) {
                                        hoaDon.remove(ct);
                                        LoadDataBill();
                                    }
                                });
                    }
                });
                btnEditBill.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (checkIsEmpty()) {
                            Utils.getBox("Vui nhập đầy đủ thông tin", Alert.AlertType.ERROR).show();
                        } else {
                            if (timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText())) != null) {
                                ChiTietHoaDon ct = tbBill.getSelectionModel().getSelectedItem();
                                ct.setSoLuong(Integer.parseInt(txtSoLuongHangHoa.getText()));
                                hoaDon.set(hoaDon.indexOf(ct), ct);
                            }

                            LoadDataBill();
                            String Tong = TinhTongTien().toString() + " VNĐ";
                            txtTongTien.setText(Tong);
                        }
                    }
                });

            });
            return r;
        });
        this.btnAddBill.setOnMouseClicked(e -> {
            if (checkIsEmpty()) {
                Utils.getBox("Vui nhập đầy đủ thông tin", Alert.AlertType.ERROR).show();
            } else {
                if (this.hoaDon.toArray().length > 0) {
                    if (timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText())) != null) {
                        ChiTietHoaDon ct = timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText()));
                        ct.setSoLuong(ct.getSoLuong() + Integer.parseInt(txtSoLuongHangHoa.getText()));
                        this.hoaDon.set(this.hoaDon.indexOf(ct), ct);
                    } else {
                        ChiTietHoaDon ct = new ChiTietHoaDon();
                        ct.setDonGia(new BigDecimal(txtDonGiaHangHoa.getText()));
                        ct.setSoLuong(Integer.parseInt(txtSoLuongHangHoa.getText()));
                        ct.setIdHangHoa(Integer.parseInt(txtIDHangHoa.getText()));
                        this.hoaDon.add(ct);
                    }

                } else {
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setDonGia(new BigDecimal(txtDonGiaHangHoa.getText()));
                    ct.setSoLuong(Integer.parseInt(txtSoLuongHangHoa.getText()));
                    ct.setIdHangHoa(Integer.parseInt(txtIDHangHoa.getText()));
                    this.hoaDon.add(ct);

                }
                LoadDataBill();
                BigDecimal tong = TinhTongTien();
                String Tong = TinhTongTien().toString() + " VNĐ";
                txtTongTien.setText(Tong);
            }

        });

    }
    

    public BigDecimal TinhTongTien() {
        BigDecimal tong = new BigDecimal(0);

        for (ChiTietHoaDon ct : this.hoaDon) {
            BigDecimal item = ct.getDonGia().multiply(new BigDecimal(ct.getSoLuong()));
            tong = tong.add(item);
        }
        return tong;
    }
    public ChiTietHoaDon timKiemChiTietHoaDon(int x) {
        for (ChiTietHoaDon h : this.hoaDon) {
            if (h.getIdHangHoa() == x) {
                return h;
            }
        }
        return null;
    }

    public void disableButtonBill(boolean action) {
        if (action == false) {
            btnEditBill.setDisable(true);
            btnDeleteBill.setDisable(true);
        } else {
            btnEditBill.setDisable(false);
            btnDeleteBill.setDisable(false);
        }
    }

    private boolean checkIsEmpty() {
        return this.txtIDHangHoa.getText().isEmpty() || this.txtSoLuongHangHoa.getText().isEmpty() || this.txtDonGiaHangHoa.getText().isEmpty();
    }

    private void LoadTableBill() {
        TableColumn colIdHH = new TableColumn("Mã Hàng Hóa");
        colIdHH.setCellValueFactory(new PropertyValueFactory("idHangHoa"));
        TableColumn colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuong"));
        TableColumn colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("donGia"));
        this.tbBill.getColumns().addAll(colIdHH, colSoLuong, colDonGia);
    }

    private void LoadDataBill() {
        this.tbBill.refresh();
        this.tbBill.setItems(FXCollections.observableArrayList(this.hoaDon));
    }

    private void LoadDataHangHoa(String kw) {
        try {
            this.tbListProduct.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            HangHoaService s = new HangHoaService(conn);
            this.tbListProduct.setItems(FXCollections.observableList(s.getListHangHoaConTrongKho(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LoadTableListProduct() {
        TableColumn colId = new TableColumn("Mã Hàng Hóa");
        colId.setCellValueFactory(new PropertyValueFactory("idHangHoa"));
        TableColumn colName = new TableColumn("Tên Hàng Hóa");
        colName.setCellValueFactory(new PropertyValueFactory("tenHang"));
        TableColumn colNgaySX = new TableColumn("Ngày Sản Xuất");
        colNgaySX.setCellValueFactory(new PropertyValueFactory("ngaySX"));
        TableColumn colHanSD = new TableColumn("Hạn Sử Dụng");
        colHanSD.setCellValueFactory(new PropertyValueFactory("hanSD"));
        TableColumn colGiaBan = new TableColumn("Giá Bán");
        colGiaBan.setCellValueFactory(new PropertyValueFactory("giaBan"));
        TableColumn colSoLuong = new TableColumn("Số Lượng Còn");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuong"));
        TableColumn colDonVi = new TableColumn("Đơn Vị Tính");
        colDonVi.setCellValueFactory(new PropertyValueFactory("donViTinh"));
        this.tbListProduct.getColumns().addAll(colId, colName, colNgaySX, colHanSD, colGiaBan, colSoLuong, colDonVi);
    }

    public void SearchKhachHang(String sdt, String cmnd) {
        try {
            Connection conn = jdbcUtil.getConn();
            KhachHangService s = new KhachHangService(conn);
            if (cmnd.isEmpty() == true && sdt.isEmpty() == false) {
                if (s.findKhachHangBySDT(sdt) != null) {
                    this.cbKhachHang.setValue(s.findKhachHangBySDT(sdt));
                    this.txtDiemTichLuy.setText(Integer.toString(s.findKhachHangBySDT(sdt).getDiem()));
                    txtNotiSearchKH.setText("");
                } else {
                    this.cbKhachHang.setValue(null);
                    txtDiemTichLuy.setText("");
                    txtNotiSearchKH.setText("Không Tìm Thấy Khách Hàng");
                }
            } else if (cmnd.isEmpty() == false && sdt.isEmpty() == true) {
                if (s.findKhachHangByCMND(cmnd) != null) {
                    this.cbKhachHang.setValue(s.findKhachHangByCMND(cmnd));
                    this.txtDiemTichLuy.setText(Integer.toString(s.findKhachHangByCMND(cmnd).getDiem()));
                    txtNotiSearchKH.setText("");
                } else {
                    this.cbKhachHang.setValue(null);
                    txtDiemTichLuy.setText("");
                    txtNotiSearchKH.setText("Không Tìm Thấy Khách Hàng");
                }
                this.cbKhachHang.setValue(s.findKhachHangByCMND(cmnd));
            } else {
                if (s.findKhachHangBySDTAndCMND(sdt, cmnd) != null) {
                    this.cbKhachHang.setValue(s.findKhachHangBySDTAndCMND(sdt, cmnd));
                    this.txtDiemTichLuy.setText(Integer.toString(s.findKhachHangBySDTAndCMND(sdt, cmnd).getDiem()));
                    txtNotiSearchKH.setText("");
                } else {
                    this.cbKhachHang.setValue(null);
                    txtDiemTichLuy.setText("");
                    txtNotiSearchKH.setText("Không Tìm Thấy Khách Hàng");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void SearchHangHoaByLoaiHang(ActionEvent event) {
        Connection conn;
        try {
            conn = jdbcUtil.getConn();
            HangHoaService s = new HangHoaService(conn);
            if (this.cbLoaiHang.getSelectionModel().getSelectedItem() != null) {
                int id = this.cbLoaiHang.getSelectionModel().getSelectedItem().getIdloaiHang();
                this.tbListProduct.setItems(FXCollections.observableList(s.SeachHangHoaConLaiByIdHangHoa(id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
