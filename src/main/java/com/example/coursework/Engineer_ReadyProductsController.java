package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class Engineer_ReadyProductsController {

    @FXML
    private Button SignOut_button;

    @FXML
    private TableColumn<?, ?> any_DefectsColumn;

    @FXML
    private Button createPartsListsButton;

    @FXML
    private Button goodOrTrashButton;

    @FXML
    private TableColumn<?, ?> productNumber_Column;

    @FXML
    private ComboBox<Integer> productNumber_ComboBox;

    @FXML
    private ComboBox<String> productStatus_ComboBox;

    @FXML
    private TableColumn<?, ?> product_idColumn;

    @FXML
    private TableView<ReadyProduct> readyProductTable;

    @FXML
    private Button trackingAssemblyButton;

    @FXML
    private Button updateStatus_Button;

    public void initialize(){
        // Инициализация столбцов таблицы readyProductTable
        productNumber_Column.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        product_idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        any_DefectsColumn.setCellValueFactory(new PropertyValueFactory<>("anyDefects"));
        refreshReadyProductTable();
        loadProductNumbersIntoComboBox();
        populateProductStatusComboBox();

        // Связывание кнопки updateStatus_Button с методом handleUpdateStatusButtonAction
        updateStatus_Button.setOnAction(event -> handleUpdateStatusButtonAction());

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

    @FXML
    void addDetailToProduct(ActionEvent event) {

    }
    @FXML
    private void refreshReadyProductTable() {
        ObservableList<ReadyProduct> readyProducts = DatabaseHandler.loadReadyProducts();
        readyProductTable.setItems(readyProducts);
    }
    @FXML
    private void loadProductNumbersIntoComboBox() {
        ObservableList<Integer> productNumbers = DatabaseHandler.loadProductNumbersFromReadyProductTable();
        productNumber_ComboBox.setItems(productNumbers);
    }
    @FXML
    private void populateProductStatusComboBox() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Брак", "Успешная сборка");
        productStatus_ComboBox.setItems(statusOptions);
    }
    @FXML
    private void handleUpdateStatusButtonAction() {
        // Получение выбранного product_number из productNumber_ComboBox
        Integer selectedProductNumber = (Integer) productNumber_ComboBox.getValue();
        // Получение выбранного статуса из productStatus_ComboBox
        String selectedStatus = (String) productStatus_ComboBox.getValue();

        if (selectedProductNumber == null || selectedStatus == null) {
            System.out.println("Выберите номер продукта и статус!");
            return;
        }

        // Обновление статуса в таблице ready_product
        DatabaseHandler.updateReadyProductStatus(selectedProductNumber, selectedStatus);

        // Обновление интерфейса
        refreshReadyProductTable();
    }

}
