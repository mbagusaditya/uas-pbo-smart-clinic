package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ResepObat;
import service.ResepObatService;

public class ResepObatController {

    @FXML
    private TextField txtCari;

    @FXML
    private TableView<ResepObat> tableResep;

    @FXML
    private TableColumn<ResepObat, Integer> colIdResep;

    @FXML
    private TableColumn<ResepObat, Integer> colPemeriksaan;

    @FXML
    private TableColumn<ResepObat, String> colObat;

    @FXML
    private TableColumn<ResepObat, Integer> colJumlah;

    @FXML
    private TableColumn<ResepObat, String> colDosis;

    @FXML
    private TableColumn<ResepObat, String> colKeterangan;

    ObservableList<ResepObat> list = FXCollections.observableArrayList();

    private ResepObatService resepService = new ResepObatService();

    @FXML
    public void initialize() {
        colIdResep.setCellValueFactory(new PropertyValueFactory<>("idResep"));
        colPemeriksaan.setCellValueFactory(
            new PropertyValueFactory<>("pemeriksaan")
        );
        colObat.setCellValueFactory(new PropertyValueFactory<>("obat"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colDosis.setCellValueFactory(new PropertyValueFactory<>("dosis"));
        colKeterangan.setCellValueFactory(
            new PropertyValueFactory<>("keterangan")
        );

        loadData();
        tableResep.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void loadData() {
        list.clear();
        list = resepService.getAll();
        tableResep.setItems(list);
    }

    @FXML
    public void handleClose() {
        Stage stage = (Stage) tableResep.getScene().getWindow();
        stage.close();
    }
}
