package com.example.coursework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AdministratorController {


    @FXML
    private Button SignOut_button;

    @FXML
    private Button createPartsLists;

    @FXML
    private Button createProductList;

    @FXML
    private Button createWorkersList;

    @FXML
    private Button newWorkerSignUp;


    @FXML
    private void initialize() {
        newWorkerSignUp.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorSignUp_Bridge.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) newWorkerSignUp.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
        SignOut_button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) SignOut_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
    }

}
