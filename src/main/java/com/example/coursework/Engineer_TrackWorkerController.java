package com.example.coursework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;

public class Engineer_TrackWorkerController {

    @FXML
    private Button SignOut_button;

    @FXML
    private Button createPartsListsButton;

    @FXML
    private Button goodOrTrashButton;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempProductIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempDetailIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempQuantityColumn;

    @FXML
    private TableView<Map<String, Object>> temporaryWorkerTable;

    @FXML
    private Button trackingAssemblyButton;
    private Timeline timeline;

    public void initialize(){
        tempProductIdColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>((Integer) cellData.getValue().get("product_id"))
        );

        tempDetailIdColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>((Integer) cellData.getValue().get("details_id_in"))
        );

        tempQuantityColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>((Integer) cellData.getValue().get("quantity"))
        );

        refreshTemporaryWorkerTable();
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> refreshTemporaryWorkerTable()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();




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
        createPartsListsButton.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("engineer-createPartsLists.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) createPartsListsButton.getScene().getWindow();
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
    }
    @FXML
    private void refreshTemporaryWorkerTable() {
        ObservableList<Map<String, Object>> tempWorkers = DatabaseHandler.loadTemporaryWorkerTable();
        temporaryWorkerTable.setItems(tempWorkers);
    }

}
