
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.pojo.HangHoa;
import com.mycompany.mavenproject1.service.HangHoaService;
import com.mycompany.mavenproject1.service.NhanVienService;
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

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTable();
        LoadData("");
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



