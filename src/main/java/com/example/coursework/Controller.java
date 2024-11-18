package com.example.coursework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        authSignInButton.setOnAction(event -> {
            String login = login_field.getText();
            String password = password_field.getText();

            if (login.isEmpty() || password.isEmpty()) {
                showAlert("Ошибка", "Все поля должны быть заполнены");
            } else {
                String profession = String.valueOf(databaseHandler.checkLogin(login, password));
                if (profession != null) {
                    try {
                        String fxmlFile;
                        switch (profession) {
                            case "Администратор":
                                fxmlFile = "admin-window.fxml";
                                break;
                            case "Инженер":
                                fxmlFile = "engineer-window.fxml";
                                break;
                            case "Рабочий":
                                fxmlFile = "worker-sborka.fxml";
                                break;
                            default:
                                showAlert("Ошибка", "Неверный логин или пароль");
                                return;
                        }

                        // Загружаем новую сцену
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                        Parent root = loader.load();

                        // Получаем текущее окно и устанавливаем новую сцену
                        Stage currentStage = (Stage) authSignInButton.getScene().getWindow();
                        currentStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Обработка ошибки загрузки FXML-файла
                    }
                } else {
                    showAlert("Ошибка", "Неверный логин или пароль");
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
