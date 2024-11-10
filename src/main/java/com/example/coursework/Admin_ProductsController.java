package com.example.coursework;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin_ProductsController {

    @FXML
    private TableView<Product> DetailsTable;

    @FXML
    private Button SignOut_button;

    @FXML
    private Button addProductButton;

    @FXML
    private Button createPartsLists;

    @FXML
    private Button createProductList;

    @FXML
    private Button createWorkersList;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Product, String> descriptionColumn; // Исправлено на <Product, String>

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<Product, String> dimensionColumn; // Исправлено на <Product, String>

    @FXML
    private TextField dimensionField;

    @FXML
    private TableColumn<Product, Integer> idColumn; // Исправлено на <Product, Integer>

    @FXML
    private TextField idToDelete;

    @FXML
    private TableColumn<Product, String> materialColumn; // Исправлено на <Product, String>

    @FXML
    private TextField materialField;

    @FXML
    private TableColumn<Product, String> nameColumn; // Исправлено на <Product, String>

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<Product, Double> weightColumn; // Исправлено на <Product, Double>

    @FXML
    private TextField weightField;

    private void refreshTable() {
        ObservableList<Product> data = DatabaseHandler.loadProductFromDatabase();
        DetailsTable.setItems(data);
    }

    public void initialize() {
        refreshTable();
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

        createWorkersList.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSignUp_new.fxml"));
                System.out.println("Loading FXML file: " + loader.getLocation());
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) SignOut_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });

        createPartsLists.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-detali.fxml"));
                System.out.println("Loading FXML file: " + loader.getLocation());
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) createPartsLists.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });

        System.out.println("Controller initialized");
        if (DetailsTable == null) {
            System.out.println("detailsTable is null");
        } else {
            System.out.println("detailsTable is not null");
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));

        refreshButton.setOnAction(event -> {
            refreshTable();
        });

        addProductButton.setOnAction(event -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String weight = weightField.getText();
            String dimension = dimensionField.getText();
            String material = materialField.getText();

            if (name.isEmpty() || description.isEmpty() || weight.isEmpty() || dimension.isEmpty() || material.isEmpty()) {
                System.out.println("Все поля должны быть заполнены");
                return;
            }

            try {
                double weightDouble = Double.parseDouble(weight);
                DatabaseHandler.addProduct(name, description, String.valueOf(weightDouble), dimension, material);
                System.out.println("Деталь добавлена в базу данных");
                refreshTable(); // Обновляем таблицу после добавления детали
            } catch (NumberFormatException e) {
                System.out.println("Цена и вес должны быть числами");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            String idStr = idToDelete.getText();
            if (idStr.isEmpty()) {
                System.out.println("Поле ID не может быть пустым");
                return;
            }

            try {
                int id = Integer.parseInt(idStr);
                DatabaseHandler.deleteProductById(id);
                System.out.println("Изделие с ID " + id + " удалена из базы данных");
                refreshTable(); // Обновляем таблицу после удаления работника
            } catch (NumberFormatException e) {
                System.out.println("ID должен быть числом");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}