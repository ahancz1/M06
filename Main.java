package database;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    // user input TextFields
    private TextField tfId = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMi = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfPhone = new TextField();
    private TextField tfEmail = new TextField();

    // Database connection settings
    private final String DB_URL = "jdbc:mysql://localhost/StaffDB"; // Database URL
    private final String DB_USER = "root";                         // MySQL username
    private final String DB_PASSWORD = "";                         // MySQL password (empty for XAMPP)

    @Override
    public void start(Stage primaryStage) {
        // GridPane layout for UI components
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); 

        // grid labels and input fields 
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(tfId, 1, 0);
        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(tfLastName, 1, 1);
        gridPane.add(new Label("First Name:"), 0, 2);
        gridPane.add(tfFirstName, 1, 2);
        gridPane.add(new Label("MI:"), 0, 3);
        gridPane.add(tfMi, 1, 3);
        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(tfAddress, 1, 4);
        gridPane.add(new Label("City:"), 0, 5);
        gridPane.add(tfCity, 1, 5);
        gridPane.add(new Label("State:"), 0, 6);
        gridPane.add(tfState, 1, 6);
        gridPane.add(new Label("Phone:"), 0, 7);
        gridPane.add(tfPhone, 1, 7);
        gridPane.add(new Label("Email:"), 0, 8);
        gridPane.add(tfEmail, 1, 8);

        // user action buttons
        Button btnView = new Button("View");
        Button btnInsert = new Button("Insert");
        Button btnUpdate = new Button("Update");
        Button btnClear = new Button("Clear");

        // add buttons to the grid
        gridPane.add(btnView, 0, 9);
        gridPane.add(btnInsert, 1, 9);
        gridPane.add(btnUpdate, 2, 9);
        gridPane.add(btnClear, 3, 9);

        // wrap GridPane in VBox for centering
        VBox vbox = new VBox(10); // spacing between elements
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(gridPane);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setTitle("M06 Staff Table");
        primaryStage.setScene(scene);
        primaryStage.show();

        // actions for buttons
        btnView.setOnAction(e -> viewRecord());
        btnInsert.setOnAction(e -> insertRecord());
        btnUpdate.setOnAction(e -> updateRecord());
        btnClear.setOnAction(e -> clearFields());
    }

    private void viewRecord() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM Staff WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tfId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String result = "Record Found:\n\n" +
                        "ID: " + resultSet.getString("id") + "\n" +
                        "Last Name: " + resultSet.getString("lastName") + "\n" +
                        "First Name: " + resultSet.getString("firstName") + "\n" +
                        "MI: " + resultSet.getString("mi") + "\n" +
                        "Address: " + resultSet.getString("address") + "\n" +
                        "City: " + resultSet.getString("city") + "\n" +
                        "State: " + resultSet.getString("state") + "\n" +
                        "Phone: " + resultSet.getString("phone") + "\n" +
                        "Email: " + resultSet.getString("email");

                showResultWindow("View Record", result);
            } else {
                showResultWindow("View Record", "No record found for ID: " + tfId.getText());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // insert new record
    private void insertRecord() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, phone, email) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tfId.getText());
            preparedStatement.setString(2, tfLastName.getText());
            preparedStatement.setString(3, tfFirstName.getText());
            preparedStatement.setString(4, tfMi.getText());
            preparedStatement.setString(5, tfAddress.getText());
            preparedStatement.setString(6, tfCity.getText());
            preparedStatement.setString(7, tfState.getText());
            preparedStatement.setString(8, tfPhone.getText());
            preparedStatement.setString(9, tfEmail.getText());
            preparedStatement.executeUpdate();

            showResultWindow("Insert Record", "Successfully added new record:\n\n" + "ID: " + tfId.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // update existing record based on the provided ID
    private void updateRecord() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, " +
                           "phone = ?, email = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tfLastName.getText());
            preparedStatement.setString(2, tfFirstName.getText());
            preparedStatement.setString(3, tfMi.getText());
            preparedStatement.setString(4, tfAddress.getText());
            preparedStatement.setString(5, tfCity.getText());
            preparedStatement.setString(6, tfState.getText());
            preparedStatement.setString(7, tfPhone.getText());
            preparedStatement.setString(8, tfEmail.getText());
            preparedStatement.setString(9, tfId.getText());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                showResultWindow("Update Record", "Record updated successfully for ID: " + tfId.getText());
            } else {
                showResultWindow("Update Record", "No record found for ID: " + tfId.getText());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // clear all text fields
    private void clearFields() {
        tfId.clear();
        tfLastName.clear();
        tfFirstName.clear();
        tfMi.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfPhone.clear();
        tfEmail.clear();
    }

    // show results in a separate window
    private void showResultWindow(String title, String message) {
        Stage resultStage = new Stage();
        resultStage.setTitle(title);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(new Label(message));


        Scene scene = new Scene(layout, 400, 400);
        resultStage.setScene(scene);
        resultStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
