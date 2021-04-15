/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SecondaryController implements Initializable {

    @FXML
    private StackPane containerPane;
    @FXML
    private Button btnSell;
    @FXML
    private Button btnRegistration;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnDasboard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LoadPane("Dashboard.fxml");

        } catch (IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnSell.setOnMouseClicked(e -> {
            try {
                LoadPane("sell.fxml");
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnDasboard.setOnMouseClicked(e -> {
            try {
                LoadPane("Dashboard.fxml");
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnLogout.setOnMouseClicked(e -> {
            try {
                LoadScene("Login.fxml", e);
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    public void LoadScene(String fxml, MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public void LoadPane(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        containerPane.getChildren().removeAll();
        containerPane.getChildren().setAll(pane);
    }

}
