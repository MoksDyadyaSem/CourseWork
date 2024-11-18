package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
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
                    return resultSet.getString("profession");
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

    public static void addProduct(String name, String description, String weight, String dimension, String material) throws Exception {
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM product")) {

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

    public static ObservableList<ProductDetail> loadProductDetailsFromDatabase() {
        ObservableList<ProductDetail> productDetails =FXCollections.observableArrayList();
        String sql = "SELECT * FROM product_details";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(rs.getInt("product_id"));
                Detail detail = new Detail(rs.getInt("detail_id"));
                int quantity = rs.getInt("quantity");
                ProductDetail productDetail = new ProductDetail(product, detail, quantity);
                productDetails.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetails;
    }
    public static ObservableList<Integer> loadProductIdsFromDatabase() {
        ObservableList<Integer> productIds = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT product_id FROM product_details";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                productIds.add(rs.getInt("product_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIds;
    }
    public static int getQuantityForDetail(int productId, int detailId) {
        String sql = "SELECT quantity FROM product_details WHERE product_id = ? AND detail_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, detailId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если запись не найдена
    }
    public static void insertIntoTemporaryWorkerTable(int productId, int detailId, int quantity) {
        String sql = "INSERT INTO temporary_worker_table (product_id, details_id_in, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, detailId);
            pstmt.setInt(3, quantity);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Запись успешно добавлена в temporary_worker_table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении записи в temporary_worker_table");
        }
    }
    public static void removeFromTemporaryWorkerTable(int productId, int detailId) {
        String sql = "DELETE FROM temporary_worker_table WHERE product_id = ? AND details_id_in = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, detailId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Запись успешно удалена из temporary_worker_table");
            } else {
                System.out.println("Запись не найдена в temporary_worker_table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении записи из temporary_worker_table");
        }
    }
    public static void insertIntoReadyProductTable(int productId) {
        String sql = "INSERT INTO ready_product (product_id, any_defects) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setString(2, "Не проверено");

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Запись успешно добавлена в ready_product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении записи в ready_product");
        }
    }
    public static ObservableList<Integer> loadProductNumbersFromReadyProductTable() {
        ObservableList<Integer> productNumbers = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT product_number FROM ready_product";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                productNumbers.add(rs.getInt("product_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productNumbers;
    }
    public static void updateReadyProductStatus(int productNumber, String status) {
        String sql = "UPDATE ready_product SET any_defects = ? WHERE product_number = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, productNumber);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Статус успешно обновлен в ready_product");
            } else {
                System.out.println("Запись с указанным product_number не найдена в ready_product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при обновлении статуса в ready_product");
        }
    }
    public static void clearTemporaryWorkerTable() {
        String sql = "DELETE FROM temporary_worker_table";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Таблица temporary_worker_table успешно очищена");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при очистке таблицы temporary_worker_table");
        }
    }
    public static ObservableList<Map<String, Object>> loadTemporaryWorkerTable() {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM temporary_worker_table";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("product_id", rs.getInt("product_id"));
                row.put("details_id_in", rs.getInt("details_id_in")); // Исправлено на правильное имя столбца
                row.put("quantity", rs.getInt("quantity"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"));
                // Заполните другие поля по необходимости
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Detail> loadDetails() {
        List<Detail> details = new ArrayList<>();
        String sql = "SELECT * FROM details";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Detail detail = new Detail(rs.getInt("id"), rs.getString("name"));
                // Заполните другие поля по необходимости
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    public static List<ProductDetail> loadProductDetails() {
        List<ProductDetail> productDetails = new ArrayList<>();
        String sql = "SELECT * FROM product_details";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(rs.getInt("product_id"));
                Detail detail = new Detail(rs.getInt("detail_id"));
                int quantity = rs.getInt("quantity");
                ProductDetail productDetail = new ProductDetail(product, detail, quantity);
                productDetails.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetails;
    }

    public static void saveProductDetail(ProductDetail productDetail) {
        String sql = "INSERT INTO product_details (product_id, detail_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productDetail.getProduct().getId());
            pstmt.setInt(2, productDetail.getDetail().getId());
            pstmt.setInt(3, productDetail.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductDetail(ProductDetail productDetail) {
        String sql = "DELETE FROM product_details WHERE product_id = ? AND detail_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productDetail.getProduct().getId());
            pstmt.setInt(2, productDetail.getDetail().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProductDetail(ProductDetail productDetail) {
        String sql = "UPDATE product_details SET quantity = ? WHERE product_id = ? AND detail_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productDetail.getQuantity());
            pstmt.setInt(2, productDetail.getProduct().getId());
            pstmt.setInt(3, productDetail.getDetail().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ObservableList<ProductDetail> loadProductDetailsForProduct(int productId) {
        ObservableList<ProductDetail> productDetails = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product_details WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("product_id"));
                Detail detail = new Detail(rs.getInt("detail_id"));
                int quantity = rs.getInt("quantity");
                ProductDetail productDetail = new ProductDetail(product, detail, quantity);
                productDetails.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetails;
    }
    public static ObservableList<ProductDetail> loadProductDetails1() {
        ObservableList<ProductDetail> productDetails = FXCollections.observableArrayList();
        String sql = "SELECT product_id, detail_id, quantity FROM product_details";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int detailId = rs.getInt("detail_id");
                int quantity = rs.getInt("quantity");

                // Создание объекта ProductDetail
                Product product = new Product(productId); // Минимальный объект Product
                Detail detail = new Detail(detailId);     // Минимальный объект Detail
                ProductDetail productDetail = new ProductDetail(product, detail, quantity);

                productDetails.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productDetails;
    }

    public static ObservableList<ReadyProduct> loadReadyProducts() {
        ObservableList<ReadyProduct> readyProducts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ready_product";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int productNumber = rs.getInt("product_number");
                int productId = rs.getInt("product_id");
                String anyDefects = rs.getString("any_defects");
                ReadyProduct readyProduct = new ReadyProduct(productNumber, productId, anyDefects);
                readyProducts.add(readyProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readyProducts;
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