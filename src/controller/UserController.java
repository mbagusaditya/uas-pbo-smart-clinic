package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import model.User;
import model.Role;

import service.UserService;

public class UserController implements Initializable {

    // ==========================
    // FORM
    // ==========================

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<Role> cbRole;

    @FXML
    private TextField txtCari;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnHapus;

    // ==========================
    // TABLE
    // ==========================

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colRole;

    // ==========================
    // SERVICE
    // ==========================

    private UserService userService = new UserService();

    private boolean editMode = false;

    // ==========================
    // INITIALIZE
    // ==========================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadComboRole();

        loadData();

        initializeTableEvent();

        btnHapus.setDisable(true);

        colId.prefWidthProperty().bind(
                tableUser.widthProperty().multiply(0.15));

        colUsername.prefWidthProperty().bind(
                tableUser.widthProperty().multiply(0.45));

        colRole.prefWidthProperty().bind(
                tableUser.widthProperty().multiply(0.40));
    }

    // ==========================
    // LOAD TABLE
    // ==========================

    private void loadData() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idUser"));

        colUsername.setCellValueFactory(
                new PropertyValueFactory<>("username"));

        colRole.setCellValueFactory(
                new PropertyValueFactory<>("role"));

        tableUser.setItems(userService.getAll());
    }

    // ==========================
    // LOAD ROLE
    // ==========================

    private void loadComboRole() {

        cbRole.getItems().clear();

        cbRole.getItems().addAll(
                new Role(1, "Admin"),
                new Role(2, "Petugas"),
                new Role(3, "Dokter"));
    }

    // ==========================
    // SIMPAN
    // ==========================

    @FXML
    private void handleSimpan() {

        try {

            User user = new User();

            if (editMode) {
                user.setIdUser(Integer.parseInt(txtId.getText()));
            }

            user.setUsername(txtUsername.getText());

            user.setPassword(txtPassword.getText());

            user.setRole(cbRole.getValue());

            userService.simpan(user, editMode);

            showAlert(
                    "Informasi",
                    "Data berhasil disimpan.");

            clearForm();

            loadData();

        } catch (Exception e) {

            showAlert(
                    "Error",
                    e.getMessage());

        }

    }

    // ==========================
    // EDIT
    // ==========================

    @FXML
    private void handleEdit() {

        User user = tableUser.getSelectionModel().getSelectedItem();

        if (user == null) {

            showAlert(
                    "Peringatan",
                    "Pilih data terlebih dahulu.");

            return;
        }

        editMode = true;

        selectedData(user);

        btnSimpan.setText("💾 Update");

    }

    // ==========================
    // HAPUS
    // ==========================

    @FXML
    private void handleHapus() {

        User user = tableUser.getSelectionModel().getSelectedItem();

        if (user == null) {

            showAlert(
                    "Peringatan",
                    "Pilih data yang akan dihapus.");

            return;
        }

        try {

            userService.delete(user.getIdUser());

            loadData();

            clearForm();

            showAlert(
                    "Informasi",
                    "Data berhasil dihapus.");

        } catch (Exception e) {

            showAlert(
                    "Error",
                    e.getMessage());

        }

    }

    // ==========================
    // CARI
    // ==========================

    @FXML
    private void handleCari() {

        String keyword = txtCari.getText();

        tableUser.setItems(
                userService.search(keyword));

    }

    // ==========================
    // BATAL
    // ==========================

    @FXML
    private void handleBatal() {

        clearForm();

    }

    // ==========================
    // TAMPILKAN DATA
    // ==========================

    private void selectedData(User user) {

        txtId.setText(String.valueOf(user.getIdUser()));

        txtUsername.setText(user.getUsername());

        txtPassword.setText(user.getPassword());

        cbRole.setValue(user.getRole());

        btnHapus.setDisable(false);

    }

    // ==========================
    // CLEAR FORM
    // ==========================

    private void clearForm() {

        txtId.clear();

        txtUsername.clear();

        txtPassword.clear();

        cbRole.getSelectionModel().clearSelection();

        txtCari.clear();

        tableUser.getSelectionModel().clearSelection();

        btnHapus.setDisable(true);

        btnSimpan.setText("💾 Simpan");

        editMode = false;

    }

    // ==========================
    // EVENT TABLE
    // ==========================

    private void initializeTableEvent() {

        tableUser.setOnMouseClicked(event -> {

            User user = tableUser.getSelectionModel().getSelectedItem();

            if (user != null) {

                selectedData(user);

            }

        });

    }

    // ==========================
    // ALERT
    // ==========================

    private void showAlert(
            String title,
            String message) {

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }
}