
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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
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
     this.tbProduct.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e->{
                try {
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
                    SetDisableButtonProduct(false);
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            });
            return r;
     });
        
    }    
    
    @FXML
    private void addProduct(ActionEvent event) {
    }
    private void LoadData(String kw){
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
    private void LoadTable(){
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
        this.tbProduct.getColumns().addAll(colId, colName, colNgaySX, colHanSD,colGiaBan,colSoLuong,colDonVi);  
    }

}



