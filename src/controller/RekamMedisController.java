package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import model.Dokter;
import model.Pasien;
import model.RekamMedis;

import service.DokterService;
import service.PasienService;
import service.RekamMedisService;

public class RekamMedisController implements Initializable {

    // ==========================
    // FORM
    // ==========================

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Pasien> cbPasien;

    @FXML
    private ComboBox<Dokter> cbDokter;

    @FXML
    private TextField txtDiagnosa;

    @FXML
    private TextField txtResep;

    @FXML
    private TextArea txtCatatan;

    @FXML
    private TextField txtCari;

    @FXML
    private Button btnSimpan;

    // ==========================
    // TABLE
    // ==========================

    @FXML
    private TableView<RekamMedis> tableRekam;

    @FXML
    private TableColumn<RekamMedis, Integer> colId;

    @FXML
    private TableColumn<RekamMedis, String> colPasien;

    @FXML
    private TableColumn<RekamMedis, String> colDokter;

    @FXML
    private TableColumn<RekamMedis, String> colDiagnosa;

    @FXML
    private TableColumn<RekamMedis, String> colResep;

    @FXML
    private TableColumn<RekamMedis, String> colCatatan;

    // ==========================
    // SERVICE
    // ==========================

    private RekamMedisService rekamService = new RekamMedisService();

    private PasienService pasienService = new PasienService();

    private DokterService dokterService = new DokterService();

    private boolean editMode = false;

    // ==========================
    // INITIALIZE
    // ==========================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadComboPasien();

        loadComboDokter();

        loadData();

        initializeTableEvent();

        colId.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.08));

        colPasien.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.20));

        colDokter.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.20));

        colDiagnosa.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.20));

        colResep.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.16));

        colCatatan.prefWidthProperty().bind(
                tableRekam.widthProperty().multiply(0.16));

    }

    // ==========================
    // LOAD TABLE
    // ==========================

    private void loadData() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idRekam"));

        colPasien.setCellValueFactory(
                new PropertyValueFactory<>("namaPasien"));

        colDokter.setCellValueFactory(
                new PropertyValueFactory<>("namaDokter"));

        colDiagnosa.setCellValueFactory(
                new PropertyValueFactory<>("diagnosa"));

        colResep.setCellValueFactory(
                new PropertyValueFactory<>("resep"));

        colCatatan.setCellValueFactory(
                new PropertyValueFactory<>("catatan"));

        tableRekam.setItems(
                rekamService.getAll());

    }

    // ==========================
    // LOAD PASIEN
    // ==========================

    private void loadComboPasien() {

        ObservableList<Pasien> list = pasienService.getAll();

        cbPasien.setItems(list);

    }

    // ==========================
    // LOAD DOKTER
    // ==========================

    private void loadComboDokter() {

        ObservableList<Dokter> list = dokterService.getAll();

        cbDokter.setItems(list);

    }

    // ==========================
    // SIMPAN
    // ==========================

    @FXML
    private void handleSimpan() {

        try {

            RekamMedis rekam = new RekamMedis();

            if (editMode) {
                rekam.setIdRekam(
                        Integer.parseInt(txtId.getText()));
            }

            rekam.setPasien(
                    cbPasien.getValue());

            rekam.setDokter(
                    cbDokter.getValue());

            rekam.setDiagnosa(
                    txtDiagnosa.getText());

            rekam.setResep(
                    txtResep.getText());

            rekam.setCatatan(
                    txtCatatan.getText());

            rekamService.simpan(
                    rekam,
                    editMode);

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

        RekamMedis rekam = tableRekam.getSelectionModel().getSelectedItem();

        if (rekam == null) {

            showAlert(
                    "Peringatan",
                    "Pilih data terlebih dahulu.");

            return;
        }

        editMode = true;

        selectedData(rekam);

    }

    // ==========================
    // HAPUS
    // ==========================

    @FXML
    private void handleHapus() {

        RekamMedis rekam = tableRekam.getSelectionModel().getSelectedItem();

        if (rekam == null) {

            showAlert(
                    "Peringatan",
                    "Pilih data yang akan dihapus.");

            return;
        }

        try {

            rekamService.delete(
                    rekam.getIdRekam());

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

        tableRekam.setItems(

                rekamService.search(keyword)

        );

    }

    // ==========================
    // BATAL
    // ==========================

    @FXML
    private void handleBatal() {

        clearForm();

    }

    // ==========================
    // TAMPILKAN DATA KE FORM
    // ==========================

    private void selectedData(RekamMedis rekam) {

        txtId.setText(
                String.valueOf(rekam.getIdRekam()));

        cbPasien.setValue(
                rekam.getPasien());

        cbDokter.setValue(
                rekam.getDokter());

        txtDiagnosa.setText(
                rekam.getDiagnosa());

        txtResep.setText(
                rekam.getResep());

        txtCatatan.setText(
                rekam.getCatatan());

    }

    // ==========================
    // CLEAR FORM
    // ==========================

    private void clearForm() {

        txtId.clear();

        cbPasien.getSelectionModel().clearSelection();

        cbDokter.getSelectionModel().clearSelection();

        txtDiagnosa.clear();

        txtResep.clear();

        txtCatatan.clear();

        txtCari.clear();

        tableRekam.getSelectionModel().clearSelection();

        editMode = false;

    }

    // ==========================
    // EVENT TABLE
    // ==========================

    private void initializeTableEvent() {

        tableRekam.setOnMouseClicked(event -> {

            RekamMedis rekam = tableRekam.getSelectionModel().getSelectedItem();

            if (rekam != null) {

                selectedData(rekam);

            }

        });

    }

    // ==========================
    // ALERT
    // ==========================

    private void showAlert(String title, String message) {

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

}