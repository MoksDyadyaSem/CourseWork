package com.example.coursework;

import java.io.IOException;
import java.net.*;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminDeleteWorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea InfoText;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane delete_Button;

    @FXML
    private Button return_button;

    @FXML
    private ComboBox<String> workers_ComboBox;

    @FXML
    public void initialize() {
        return_button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorSignUp_bridge.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) return_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });

    }

    private void loadEmployee() {
        ObservableList<String> employees = FXCollections.observableArrayList();
        String query = "SELECT name FROM employees";
        try (PreparedStatement statement = DatabaseHandler.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            InfoText.setText("Ошибка при загрузке данных: " + e.getMessage());
        }
        workers_ComboBox.setItems(employees);
    }
}


