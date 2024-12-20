package com.example.coursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EngineerController {

    @FXML
    private Button SignOut_button;

    @FXML
    private Button createPartsListsButton;

    @FXML
    private Button createSequencyButton;

    @FXML
    private Button goodOrTrashButton;

    @FXML
    private Button trackingAssemblyButton;

    public void initialize(){
        createPartsListsButton.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("engineer-createPartsLists.fxml"));
                System.out.println("Loading FXML file: " + loader.getLocation());
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) createPartsListsButton.getScene().getWindow();
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
        goodOrTrashButton.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("engineer-readyProducts.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) goodOrTrashButton.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
        trackingAssemblyButton.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("engineer-trackWorker.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) trackingAssemblyButton.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });
    }
}
