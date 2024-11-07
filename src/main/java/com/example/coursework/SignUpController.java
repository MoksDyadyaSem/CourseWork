package com.example.coursework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField SignUp_login;

    @FXML
    private TextField SignUp_name;

    @FXML
    private PasswordField SignUp_password;

    @FXML
    private TextField SignUp_surname;

    @FXML
    private MenuButton SignUp_workerSpeciality;

    @FXML
    private Button return_button;

    @FXML
    private ComboBox<String> professionChoice;

    private DatabaseHandler databaseHandler;

    @FXML
    void choice_administrator(ActionEvent event) {
        // Обработка выбора "Администратор"
    }

    @FXML
    void choice_engineer(ActionEvent event) {
        // Обработка выбора "Инженер"
    }

    @FXML
    void choice_worker(ActionEvent event) {
        // Обработка выбора "Рабочий"
    }

    @FXML
    private void initialize() {
        return_button.setOnAction(actionEvent -> {
            try {
                // Загружаем новую сцену
                FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorSignUp_bridge.fxml"));
                Parent root = loader.load();

                // Получаем текущее окно и устанавливаем новую сцену
                Stage currentStage = (Stage) return_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки загрузки FXML-файла
            }
        });

        ObservableList<String> items = FXCollections.observableArrayList(
                "Администратор",
                "Инженер",
                "Рабочий"
        );
        professionChoice.setItems(items);

        // Устанавливаем текст-подсказку
        professionChoice.setPromptText("Выберите профессию");

        // Инициализируем DatabaseHandler
        databaseHandler = new DatabaseHandler();

        // Обработка нажатия кнопки "зарегистрировать работника"
        SignUpButton.setOnAction(event -> {
            String login = SignUp_login.getText();
            String name = SignUp_name.getText();
            String surname = SignUp_surname.getText();
            String password = SignUp_password.getText();
            String profession = professionChoice.getValue();

            if (login.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty() || profession == null) {
                showAlert("Ошибка", "Все поля должны быть заполнены");
            } else {
                boolean success = databaseHandler.addEmployee(login, name, surname, password, profession);
                if (success) {
                    showAlert("Успех", "Работник успешно зарегистрирован");
                } else {
                    showAlert("Ошибка", "Не удалось зарегистрировать работника");
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}