package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Klinik;
import service.KlinikService;
import util.AlertUtil;
import util.SceneUtil;

public class KlinikController {

    @FXML
    private TextField txtCari;

    @FXML
    private TableView<Klinik> tableKlinik;

    @FXML
    private TableColumn<Klinik, Integer> colId;

    @FXML
    private TableColumn<Klinik, String> colNama;

    @FXML
    private TableColumn<Klinik, String> colAlamat;

    @FXML
    private TableColumn<Klinik, String> colTelp;

    ObservableList<Klinik> list = FXCollections.observableArrayList();

    private KlinikService klinikService = new KlinikService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idKlinik"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaKlinik"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colTelp.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));

        loadData();
        tableKlinik.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void loadData() {
        list.clear();
        list = klinikService.getAll();
        tableKlinik.setItems(list);
    }

    @FXML
    public void handleClose() {
        Stage stage = (Stage) tableKlinik.getScene().getWindow();
        stage.close();
    }
}
