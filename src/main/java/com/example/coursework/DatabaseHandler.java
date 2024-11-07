package com.example.coursework;

import java.sql.*;

public class DatabaseHandler {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Успешное подключение к базе данных");
            } else {
                System.out.println("Не удалось подключиться к базе данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public String checkLogin(String login, String password) {
        if (connection == null) {
            System.out.println("Соединение с базой данных не установлено");
            return null;
        }

        String query = "SELECT profession FROM employees WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Используем номер столбца для извлечения значения
                    return resultSet.getString(1); // Предполагаем, что profession - первый столбец в результате
                } else {
                    System.out.println("Логин или пароль неверны");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при проверке логина и пароля: " + e.getMessage());
            return null;
        }
    }


    public boolean addEmployee(String login, String name, String surname, String password, String profession) {
        String query = "INSERT INTO employees (login, name, surname, password, profession) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, password);
            statement.setString(5, profession);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}