package com.example.coursework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdministratorSignUp_br {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddNewWorker_Button;

    @FXML
    private Button DeleteWorker_button;

    @FXML
    private Button return_button;

    @FXML
    void initialize() {
        DeleteWorker_button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorDeleteWorker.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) return_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
        return_button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("administrator-window.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) return_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
        AddNewWorker_Button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorSignUp.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) AddNewWorker_Button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
    }

}
