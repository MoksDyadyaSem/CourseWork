package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static void addEmployee(String name, String surname, String login, String password, String profession) throws Exception {
        String query = "INSERT INTO employees (name, surname, login, password, profession) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, profession);

            statement.executeUpdate();
        }
    }
    public static ResultSet executeQuery(String query) throws Exception {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeQuery(query);
        }
    }
    public static void deleteEmployeeById(int id) throws Exception {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public static ObservableList<Employee> loadEmployeesFromDatabase() {
        ObservableList<Employee> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM employees")) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("profession"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
                data.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
    public static ObservableList<Detail> loadDetailsFromDatabase() {
        ObservableList<Detail> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM details")) {

            while (resultSet.next()) {
                Detail detail = new Detail(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("weight"),
                        resultSet.getString("dimension"),
                        resultSet.getString("material")
                );
                data.add(detail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
    public static void addDetail(String name, String price, String weight, String dimension, String material) throws Exception {
        String query = "INSERT INTO details (name, price, weight, dimension, material) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setDouble(2, Double.parseDouble(price));
            statement.setDouble(3, Double.parseDouble(weight));
            statement.setString(4, dimension);
            statement.setString(5, material);

            statement.executeUpdate();
        }
    }
    public static void deleteDetailById(int id) throws Exception {
        String query = "DELETE FROM details WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public static Detail getDetailById(int id) throws Exception {
        String query = "SELECT * FROM details WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Detail(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("weight"),
                        resultSet.getString("dimension"),
                        resultSet.getString("material")
                );
            }
        }
        return null;
    }

    public static void addProduct(String name, String description, String weight, String dimension,  String material) throws Exception {
        String query = "INSERT INTO product (name, description, weight, dimension, material) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, Double.parseDouble(weight));
            statement.setString(4, dimension);
            statement.setString(5, material);

            statement.executeUpdate();
        }
    }
    public static void deleteProductById(int id) throws Exception {
        String query = "DELETE FROM product WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public static ObservableList<Product> loadProductFromDatabase() {
        ObservableList<Product> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM product")) { // Исправлено на "product"

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("weight"),
                        resultSet.getString("dimension"),
                        resultSet.getString("material")
                );
                data.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
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