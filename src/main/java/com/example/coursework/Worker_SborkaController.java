package com.example.coursework;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Worker_SborkaController {

    @FXML
    private Button EndCreateButton;

    @FXML
    private Button IzyatButton;

    @FXML
    private Button SborkaButton;

    @FXML
    private Button SignOut_button;

    @FXML
    private Button VstavitButton;

    @FXML
    private TableColumn<?, ?> any_DefectsColumn;

    @FXML
    private TextField detail_idField;

    @FXML
    private TableColumn<?, ?> product_idColumn;

    @FXML
    private ComboBox<Integer> productComboBox;

    @FXML
    private TableColumn<ProductDetail, Integer> productDetailDetailIdColumn;

    @FXML
    private TableColumn<ProductDetail, Integer> productDetailProductIdColumn;

    @FXML
    private TableColumn<ProductDetail, Integer> productDetailQuantityColumn;

    @FXML
    private TableColumn<?, ?> productNumber_Column;

    @FXML
    private TableView<ProductDetail> product_detailsTable;

    @FXML
    private TableView<Map<String, Object>> temporaryWorkerTable;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempProductIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempDetailIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> tempQuantityColumn;

    @FXML
    private TableView<ReadyProduct> readyProductTable;

    @FXML
    private Text text1;

    @FXML
    private Text text11;

    private ObservableList<Product> products;
    private ObservableList<Detail> details;

    @FXML
    private void refreshReadyProductTable() {
        ObservableList<ReadyProduct> readyProducts = DatabaseHandler.loadReadyProducts();
        readyProductTable.setItems(readyProducts);
    }


    @FXML
    private void initialize() {
        EndCreateButton.setOnAction(event -> handleEndCreateButtonAction());
        IzyatButton.setOnAction(event -> handleRemoveDetailButtonAction());
        VstavitButton.setOnAction(event -> handleInsertDetailButtonAction());

        // Инициализация столбцов таблицы readyProductTable
        productNumber_Column.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        product_idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        any_DefectsColumn.setCellValueFactory(new PropertyValueFactory<>("anyDefects"));

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
        refreshReadyProductTable();
        loadProductIdsIntoComboBox();

        productDetailProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productDetailDetailIdColumn.setCellValueFactory(new PropertyValueFactory<>("detailId"));
        productDetailQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        refreshProductDetailTable();

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

    @FXML
    private void refreshProductDetailTable() {
        ObservableList<ProductDetail> productDetails = DatabaseHandler.loadProductDetailsFromDatabase();
        product_detailsTable.setItems(productDetails);
    }
    @FXML
    private void loadProductIdsIntoComboBox() {
        ObservableList<Integer> productIds = DatabaseHandler.loadProductIdsFromDatabase();
        productComboBox.setItems(productIds);
    }
    @FXML
    private void refreshTemporaryWorkerTable() {
        ObservableList<Map<String, Object>> tempWorkers = DatabaseHandler.loadTemporaryWorkerTable();
        temporaryWorkerTable.setItems(tempWorkers);
    }

    @FXML
    private void handleEndCreateButtonAction() {
        // Получение данных из таблицы temporary_worker_table
        ObservableList<Map<String, Object>> tempWorkers = DatabaseHandler.loadTemporaryWorkerTable();

        // Используем Set для хранения уникальных product_id
        Set<Integer> uniqueProductIds = new HashSet<>();

        for (Map<String, Object> row : tempWorkers) {
            int productId = (int) row.get("product_id");
            uniqueProductIds.add(productId);
        }

        // Вставка данных в таблицу ready_product для каждого уникального product_id
        for (int productId : uniqueProductIds) {
            DatabaseHandler.insertIntoReadyProductTable(productId);
        }

        // Очистка таблицы temporary_worker_table
        DatabaseHandler.clearTemporaryWorkerTable();

        // Обновление интерфейса
        refreshTemporaryWorkerTable();
        refreshReadyProductTable();
    }
    @FXML
    private void handleInsertDetailButtonAction() {
        // Получение данных из ComboBox и TextField
        Integer selectedProductId = productComboBox.getValue();
        String detailIdText = detail_idField.getText();

        if (selectedProductId == null || detailIdText.isEmpty()) {
            System.out.println("Заполните все поля!");
            return;
        }

        try {
            int detailId = Integer.parseInt(detailIdText);

            // Поиск количества для вставки
            int quantity = DatabaseHandler.getQuantityForDetail(selectedProductId, detailId);
            if (quantity == -1) {
                System.out.println("Не найдено соответствующих записей!");
                return;
            }

            // Вставка данных в таблицу
            DatabaseHandler.insertIntoTemporaryWorkerTable(selectedProductId, detailId, quantity);

            // Обновление интерфейса
            refreshTemporaryWorkerTable();

        } catch (NumberFormatException e) {
            System.out.println("ID детали должен быть числом!");
        }
    }
    @FXML
    private void handleRemoveDetailButtonAction() {
        // Получение данных из ComboBox и TextField
        Integer selectedProductId = productComboBox.getValue();
        String detailIdText = detail_idField.getText();

        if (selectedProductId == null || detailIdText.isEmpty()) {
            System.out.println("Заполните все поля!");
            return;
        }

        try {
            int detailId = Integer.parseInt(detailIdText);

            // Удаление данных из таблицы
            DatabaseHandler.removeFromTemporaryWorkerTable(selectedProductId, detailId);

            // Обновление интерфейса
            refreshTemporaryWorkerTable();

        } catch (NumberFormatException e) {
            System.out.println("ID детали должен быть числом!");
        }
    }
}
