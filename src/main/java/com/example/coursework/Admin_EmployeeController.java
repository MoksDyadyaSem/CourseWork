package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

public class Admin_EmployeeController {
    @FXML
    private TextField NewUser_login;

    @FXML
    private TextField NewUser_name;

    @FXML
    private TextField NewUser_password;

    @FXML
    private TextField NewUser_surname;
    @FXML
    private ComboBox<String> professionComboBox;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button SignOut_button;

    @FXML
    private Button createPartsLists;

    @FXML
    private Button createProductList;

    @FXML
    private Button createWorkersList;
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> professionColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> surnameColumn;

    @FXML
    private TableColumn<Employee, String> loginColumn;

    @FXML
    private TableColumn<Employee, String> passwordColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField idToDelete;

    private void refreshTable() {
        ObservableList<Employee> data = DatabaseHandler.loadEmployeesFromDatabase();
        employeeTable.setItems(data);
    }
    public void initialize() {

        System.out.println("Controller initialized");
        if (employeeTable == null) {
            System.out.println("employeeTable is null");
        } else {
            System.out.println("employeeTable is not null");
        }


        addEmployeeButton.setOnAction(event -> {
            String name = NewUser_name.getText();
            String surname = NewUser_surname.getText();
            String login = NewUser_login.getText();
            String password = NewUser_password.getText();
            String profession = professionComboBox.getValue();

            if (name.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty() || profession == null) {
                System.out.println("Все поля должны быть заполнены");
                return;
            }

            try {
                DatabaseHandler.addEmployee(name, surname, login, password, profession);
                System.out.println("Работник добавлен в базу данных");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        refreshButton.setOnAction(event -> {
            refreshTable();
        });

        // Загружаем данные в таблицу при инициализации
        refreshTable();

        deleteButton.setOnAction(event -> {
            String idStr = idToDelete.getText();
            if (idStr.isEmpty()) {
                System.out.println("Поле ID не может быть пустым");
                return;
            }

            try {
                int id = Integer.parseInt(idStr);
                DatabaseHandler.deleteEmployeeById(id);
                System.out.println("Работник с ID " + id + " удален из базы данных");
                refreshTable(); // Обновляем таблицу после удаления работника
            } catch (NumberFormatException e) {
                System.out.println("ID должен быть числом");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        // Заполняем ComboBox вариантами
        ObservableList<String> professions = FXCollections.observableArrayList(
                "Администратор",
                "Инженер",
                "Рабочий"
        );
        professionComboBox.setItems(professions);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        professionColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<Employee> data = DatabaseHandler.loadEmployeesFromDatabase();

        if (employeeTable != null) {
            employeeTable.setItems(data);
        } else {
            System.out.println("employeeTable is still null after initialization");
        }


    }
}