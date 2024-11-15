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

import java.io.IOException;

public class Admin_DetailsController {

    @FXML
    private TableView<Detail> DetailsTable;

    @FXML
    private Button SignOut_button;

    @FXML
    private Button addDetailButton;

    @FXML
    private Button createPartsLists;

    @FXML
    private Button createProductList;

    @FXML
    private Button createWorkersList;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Detail, String> dimensionColumn;

    @FXML
    private TextField dimensionField;

    @FXML
    private TableColumn<Detail, Integer> idColumn;

    @FXML
    private TextField idToDelete;

    @FXML
    private TableColumn<Detail, String> materialColumn;

    @FXML
    private TextField materialField;

    @FXML
    private TableColumn<Detail, String> nameColumn;

    @FXML
    private TextField nameField;


    @FXML
    private TableColumn<Detail, Double> priceColumn;

    @FXML
    private TextField priceField;

    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<Detail, Double> weightColumn;

    @FXML
    private TextField weightField;



    private ObservableList<String> selectedDetails = FXCollections.observableArrayList();

    private void refreshTable() {
        ObservableList<Detail> data = DatabaseHandler.loadDetailsFromDatabase();
        DetailsTable.setItems(data);
    }

    public void initialize(){
        // Загружаем данные в таблицу при инициализации
        refreshTable();

        createProductList.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-products.fxml"));
                System.out.println("Loading FXML file: " + loader.getLocation());
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) createProductList.getScene().getWindow();
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
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));

        refreshButton.setOnAction(event -> {
            refreshTable();
        });




        addDetailButton.setOnAction(event -> {
            String name = nameField.getText();
            String price = priceField.getText();
            String weight = weightField.getText();
            String dimension = dimensionField.getText();
            String material = materialField.getText();

            if (name.isEmpty() || price.isEmpty() || weight.isEmpty() || dimension.isEmpty() || material.isEmpty()) {
                System.out.println("Все поля должны быть заполнены");
                return;
            }

            try {
                double priceDouble = Double.parseDouble(price);
                double weightDouble = Double.parseDouble(weight);
                DatabaseHandler.addDetail(name, String.valueOf(priceDouble), String.valueOf(weightDouble), dimension, material);
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
                DatabaseHandler.deleteDetailById(id);
                System.out.println("Деталь с ID " + id + " удалена из базы данных");
                refreshTable(); // Обновляем таблицу после удаления работника
            } catch (NumberFormatException e) {
                System.out.println("ID должен быть числом");
            } catch (Exception e) {
                e.printStackTrace();
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
