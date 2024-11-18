package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

public class Engineer_PartsListsController {
    @FXML
    private TableView<Product> ProductList;
    @FXML
    private TableColumn<Product, Integer> idProductColumn;
    @FXML
    private TableColumn<Product, String> nameProductColumn;

    @FXML
    private TableView<Detail> DetailsList;
    @FXML
    private TableColumn<Detail, Integer> idPartColumn;
    @FXML
    private TableColumn<Detail, String> namePartColumn;

    @FXML
    private TableView<ProductDetail> productDetailTable;
    @FXML
    private TableColumn<ProductDetail, Integer> productDetailProductIdColumn;
    @FXML
    private TableColumn<ProductDetail, Integer> productDetailDetailIdColumn;
    @FXML
    private TableColumn<ProductDetail, Integer> productDetailQuantityColumn;

    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private ComboBox<Detail> detailComboBox;
    @FXML
    private TextField quantityTextField;

    @FXML
    private ComboBox<Product> updateProductComboBox;
    @FXML
    private ComboBox<Detail> updateDetailComboBox;
    @FXML
    private TextField updateQuantityTextField;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addDetailToProduct;

    @FXML
    private Button updateDetailQuantity;

    @FXML
    private Button removeDetailFromProduct;

    @FXML
    private Button SignOut_button;

    @FXML
    private Button trackingAssemblyButton;

    @FXML
    private Button goodOrTrashButton;

    private ObservableList<Product> products;
    private ObservableList<Detail> details;

    @FXML
    private void initialize() {
        // Инициализация ObservableList
        products = DatabaseHandler.loadProductFromDatabase();
        details = DatabaseHandler.loadDetailsFromDatabase();

        // Настройка колонок таблиц
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        idPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        productDetailProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productDetailDetailIdColumn.setCellValueFactory(new PropertyValueFactory<>("detailId"));
        productDetailQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Заполнение ComboBox данными из базы данных
        productComboBox.setItems(products);
        detailComboBox.setItems(details);
        updateProductComboBox.setItems(products);
        updateDetailComboBox.setItems(details);

        // Настройка отображения элементов в ComboBox
        configureComboBoxDisplay(productComboBox);
        configureComboBoxDisplay(detailComboBox);
        configureComboBoxDisplay(updateProductComboBox);
        configureComboBoxDisplay(updateDetailComboBox);

        // Настройка кнопок
        refreshButton.setOnAction(event -> refreshProductDetailTable());
        addDetailToProduct.setOnAction(event -> addDetailToProduct());
        updateDetailQuantity.setOnAction(event -> updateDetailQuantity());
        removeDetailFromProduct.setOnAction(event -> removeDetailFromProduct());

        // Изначальное обновление таблиц
        refreshProductTable();
        refreshDetailsTable();
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

    private <T> void configureComboBoxDisplay(ComboBox<T> comboBox) {
        comboBox.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item instanceof Product) {
                    Product product = (Product) item;
                    setText(empty ? "" : "ID: " + product.getId() + ", Name: " + product.getName());
                } else if (item instanceof Detail) {
                    Detail detail = (Detail) item;
                    setText(empty ? "" : "ID: " + detail.getId() + ", Name: " + detail.getName());
                } else {
                    setText(null);
                }
            }
        });

        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item instanceof Product) {
                    Product product = (Product) item;
                    setText(empty ? "" : "ID: " + product.getId() + ", Name: " + product.getName());
                } else if (item instanceof Detail) {
                    Detail detail = (Detail) item;
                    setText(empty ? "" : "ID: " + detail.getId() + ", Name: " + detail.getName());
                } else {
                    setText(null);
                }
            }
        });
    }

    @FXML
    private void refreshProductTable() {
        products = DatabaseHandler.loadProductFromDatabase();
        ProductList.setItems(products);
    }

    @FXML
    private void refreshDetailsTable() {
        details = DatabaseHandler.loadDetailsFromDatabase();
        DetailsList.setItems(details);
    }

    @FXML
    private void refreshProductDetailTable() {
        ObservableList<ProductDetail> productDetails = DatabaseHandler.loadProductDetailsFromDatabase();
        productDetailTable.setItems(productDetails);
    }

    @FXML
    private void addDetailToProduct() {
        Product selectedProduct = productComboBox.getSelectionModel().getSelectedItem();
        Detail selectedDetail = detailComboBox.getSelectionModel().getSelectedItem();
        String quantityText = quantityTextField.getText();

        if (selectedProduct != null && selectedDetail != null && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                ProductDetail newProductDetail = new ProductDetail(selectedProduct, selectedDetail, quantity);
                DatabaseHandler.saveProductDetail(newProductDetail);
                refreshProductDetailTable();
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Количество должно быть числом.");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите продукт, деталь и введите количество.");
        }
    }

    @FXML
    private void updateDetailQuantity() {
        Product selectedProduct = updateProductComboBox.getSelectionModel().getSelectedItem();
        Detail selectedDetail = updateDetailComboBox.getSelectionModel().getSelectedItem();
        String quantityText = updateQuantityTextField.getText();

        if (selectedProduct != null && selectedDetail != null && !quantityText.isEmpty()) {
            try {
                int newQuantity = Integer.parseInt(quantityText);
                ProductDetail productDetail = new ProductDetail(selectedProduct, selectedDetail, newQuantity);
                DatabaseHandler.updateProductDetail(productDetail);
                refreshProductDetailTable();
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Количество должно быть числом.");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите продукт, деталь и введите новое количество.");
        }
    }

    @FXML
    private void removeDetailFromProduct() {
        Product selectedProduct = updateProductComboBox.getSelectionModel().getSelectedItem();
        Detail selectedDetail = updateDetailComboBox.getSelectionModel().getSelectedItem();

        if (selectedProduct != null && selectedDetail != null) {
            ProductDetail productDetail = new ProductDetail(selectedProduct, selectedDetail, 0);
            DatabaseHandler.deleteProductDetail(productDetail);
            refreshProductDetailTable();
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите продукт и деталь для удаления.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
