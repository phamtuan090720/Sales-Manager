package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.HangHoa;
import com.mycompany.mavenproject1.pojo.LoaiHang;
import com.mycompany.mavenproject1.pojo.XuatXu;
import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.LoaiHangService;
import com.mycompany.mavenproject1.service.NhanVienService;
import com.mycompany.mavenproject1.service.XuatXuService;
import com.mycompany.mavenproject1.service.jdbcUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ProductManagementController implements Initializable {

    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnUpdateProduct;
    @FXML
    private Button btnDeleteProduct;
    @FXML
    private Button btnRsInputProduct;
    @FXML
    private TableView<HangHoa> tbProduct;
    @FXML
    private ComboBox<LoaiHang> cbLoaiHang;
    @FXML
    private ComboBox<XuatXu> cbXuatXu;
    @FXML
    private TextField txtHangHoa;
    @FXML
    private TextField txtGiaBan;
    @FXML
    private TextField txtSoLuong;
    @FXML
    private DatePicker txtNgaySanXuat;
    @FXML
    private DatePicker txtHanSuDung;
    @FXML
    private TextField txtDonVi;
    @FXML
    private TextField kwSeachh;
    @FXML
    private Button rsDateSX;
    @FXML
    private Button rsDateHSD;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = jdbcUtil.getConn();
            LoaiHangService lhs = new LoaiHangService(conn);
            XuatXuService xxs = new XuatXuService(conn);
            this.cbLoaiHang.setItems(FXCollections.observableList(lhs.getListLoaiHang()));
            this.cbXuatXu.setItems(FXCollections.observableList(xxs.getListXuatXu()));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        LoadTable();
        LoadData("");
        txtNgaySanXuat.getEditor().setDisable(true);
        txtHanSuDung.getEditor().setDisable(true);
        btnRsInputProduct.setOnMouseClicked(e -> {
            ResetInput();
        });
        this.kwSeachh.textProperty().addListener((obj) -> {
            LoadData(this.kwSeachh.getText());
        });
        this.tbProduct.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                try {
                    SetDisableButtonProduct(false);
                    Connection conn = jdbcUtil.getConn();
                    HangHoa hh = this.tbProduct.getSelectionModel().getSelectedItem();
                    XuatXuService xxs = new XuatXuService(conn);
                    LoaiHangService lhs = new LoaiHangService(conn);
                    txtHangHoa.setText(hh.getTenHang());
                    txtDonVi.setText(hh.getDonViTinh());
                    txtGiaBan.setText(hh.getGiaBan().toString());
                    txtSoLuong.setText(Integer.toString(hh.getSoLuong()));
                    cbLoaiHang.getSelectionModel().select(lhs.getLoaiHangById(hh.getLoaiHang()));
                    cbXuatXu.getSelectionModel().select(xxs.getXuatXuById(hh.getXuatXu()));
                    if (hh.getNgaySX() != null) {
                        txtNgaySanXuat.setValue(convertToLocalDateViaSqlDate(hh.getNgaySX()));
                    } else {
                        txtNgaySanXuat.setValue(null);
                    }
                    if (hh.getHanSD() != null) {
                        txtHanSuDung.setValue(convertToLocalDateViaSqlDate(hh.getHanSD()));
                    } else {
                        txtHanSuDung.setValue(null);
                    }
                    btnDeleteProduct.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                                    .showAndWait().ifPresent(bt -> {
                                        if (bt == ButtonType.OK) {
                                            try {
                                                Connection conn = jdbcUtil.getConn();
                                                HangHoaService s = new HangHoaService(conn);

                                                if (s.deleleHangHoa(hh.getIdHangHoa())) {
                                                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                                    LoadData("");
                                                    SetDisableButtonProduct(true);
                                                    ResetInput();
                                                } else {
                                                    Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                                }

                                                conn.close();
                                            } catch (SQLException ex) {

                                                ex.printStackTrace();
                                                Logger.getLogger(EmployeeManagementController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    });
                        }
                    });
                    btnUpdateProduct.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Connection conn;
                            try {
                                conn = jdbcUtil.getConn();
                                HangHoaService s = new HangHoaService(conn);
                                HangHoa hhUpdate = new HangHoa();
                                try {
                                    hhUpdate.setIdHangHoa(hh.getIdHangHoa());
                                    hhUpdate.setTenHang(txtHangHoa.getText());
                                    hhUpdate.setLoaiHang(cbLoaiHang.getSelectionModel().getSelectedItem().getIdloaiHang());
                                    hhUpdate.setXuatXu(cbXuatXu.getSelectionModel().getSelectedItem().getIdXuatXu());
                                    hhUpdate.setDonViTinh(txtDonVi.getText());
                                    hhUpdate.setGiaBan(new BigDecimal(txtGiaBan.getText()));
                                    hhUpdate.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                                    try {
                                        hhUpdate.setNgaySX(Date.valueOf(txtNgaySanXuat.getValue()));
                                        hhUpdate.setHanSD(Date.valueOf(txtHanSuDung.getValue()));
                                    } catch (NullPointerException evnt) {
                                        System.out.println("NullPointerException thrown!");
                                    }
                                    if (s.updateHangHoa(hhUpdate) == true) {
                                        Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                        LoadData("");
                                    } else {
                                        Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
                                    }
                                } catch (NullPointerException evnt) {
                                    Utils.getBox("Vui Lòng Nhập Đầy Đủ Thông Tin", Alert.AlertType.INFORMATION).show();
                                }
                                conn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(EmployeeManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            return r;
        });
        rsDateHSD.setOnMouseClicked(e->{
             txtHanSuDung.setValue(null);
        });
        rsDateSX.setOnMouseClicked(e->{
             txtNgaySanXuat.setValue(null);
        });
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    private void addProduct(ActionEvent event) {
        Connection conn;
        try {
            conn = jdbcUtil.getConn();
            HangHoaService s = new HangHoaService(conn);
            HangHoa hh = new HangHoa();
            try {
                hh.setTenHang(txtHangHoa.getText());
                hh.setLoaiHang(this.cbLoaiHang.getSelectionModel().getSelectedItem().getIdloaiHang());
                hh.setXuatXu(this.cbXuatXu.getSelectionModel().getSelectedItem().getIdXuatXu());
                hh.setDonViTinh(txtDonVi.getText());
                hh.setGiaBan(new BigDecimal(txtGiaBan.getText()));
                hh.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                try {
                    hh.setNgaySX(Date.valueOf(txtNgaySanXuat.getValue()));
                    hh.setHanSD(Date.valueOf(txtHanSuDung.getValue()));
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                if (s.addHangHoa(hh) == true) {
                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                    this.LoadData("");
                } else {
                    Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
                }
            } catch (NullPointerException e) {
                Utils.getBox("Vui Lòng Nhập Đầy Đủ Thông Tin", Alert.AlertType.INFORMATION).show();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void LoadData(String kw) {
        try {
            this.tbProduct.getItems().clear();
            Connection conn = jdbcUtil.getConn();
            HangHoaService s = new HangHoaService(conn);
            this.tbProduct.setItems(FXCollections.observableList(s.getListHangHoa(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SetDisableButtonProduct(boolean isAction) {
        if (isAction == true) {
            btnDeleteProduct.setDisable(true);
            btnUpdateProduct.setDisable(true);
            btnAddProduct.setDisable(false);
        } else {
            btnDeleteProduct.setDisable(false);
            btnUpdateProduct.setDisable(false);
            btnAddProduct.setDisable(true);
        }

    }

    public void ResetInput() {
        txtDonVi.setText(null);
        txtGiaBan.setText(null);
        txtHanSuDung.setValue(null);
        txtNgaySanXuat.setValue(null);
        txtHangHoa.setText(null);
        txtSoLuong.setText(null);
        cbLoaiHang.getSelectionModel().select(null);
        cbXuatXu.getSelectionModel().select(null);
    }

    private void LoadTable() {
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
        this.tbProduct.getColumns().addAll(colId, colName, colNgaySX, colHanSD, colGiaBan, colSoLuong, colDonVi);
    }

}
