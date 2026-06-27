package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Pemeriksaan;
import model.Pendaftaran;

import service.PemeriksaanService;
import service.PendaftaranService;

public class PemeriksaanController implements Initializable {

        // ==========================
        // FORM
        // ==========================

        @FXML
        private TextField txtId;

        @FXML
        private DatePicker dpTanggal;

        @FXML
        private ComboBox<Pendaftaran> cbPendaftaran;

        @FXML
        private TextField txtDiagnosa;

        @FXML
        private TextField txtTekanan;

        @FXML
        private TextField txtGula;

        @FXML
        private TextField txtSuhu;

        @FXML
        private TextField txtBerat;

        @FXML
        private TextArea txtCatatan;

        @FXML
        private TextField txtPrediksi;

        @FXML
        private ComboBox<String> cbResiko;

        @FXML
        private TextField txtCari;

        @FXML
        private Button btnSimpan;

        // ==========================
        // TABLE
        // ==========================

        @FXML
        private TableView<Pemeriksaan> tablePemeriksaan;

        @FXML
        private TableColumn<Pemeriksaan, Integer> colId;

        @FXML
        private TableColumn<Pemeriksaan, String> colPasien;

        @FXML
        private TableColumn<Pemeriksaan, String> colDokter;

        @FXML
        private TableColumn<Pemeriksaan, java.util.Date> colTanggal;

        @FXML
        private TableColumn<Pemeriksaan, String> colDiagnosa;

        @FXML
        private TableColumn<Pemeriksaan, String> colPrediksi;

        @FXML
        private TableColumn<Pemeriksaan, String> colResiko;

        // ==========================
        // SERVICE
        // ==========================

        private PemeriksaanService pemeriksaanService = new PemeriksaanService();
        private PendaftaranService pendaftaranService = new PendaftaranService();

        private boolean editMode = false;

        // ==========================
        // INITIALIZE
        // ==========================

        @Override
        public void initialize(URL url, ResourceBundle rb) {

                loadComboPendaftaran();

                loadComboResiko();

                loadData();

                initializeTableEvent();

                btnHapus.setDisable(true);

                colId.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.06));

                colPasien.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.18));

                colDokter.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.18));

                colTanggal.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.12));

                colDiagnosa.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.20));

                colPrediksi.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.14));

                colResiko.prefWidthProperty().bind(
                                tablePemeriksaan.widthProperty().multiply(0.12));

        }

        // ==========================
        // LOAD TABLE
        // ==========================

        private void loadData() {

                colId.setCellValueFactory(
                                new PropertyValueFactory<>("idPeriksa"));

                colPasien.setCellValueFactory(
                                new PropertyValueFactory<>("namaPasien"));

                colDokter.setCellValueFactory(
                                new PropertyValueFactory<>("namaDokter"));

                colTanggal.setCellValueFactory(
                                new PropertyValueFactory<>("tanggalPeriksa"));

                colDiagnosa.setCellValueFactory(
                                new PropertyValueFactory<>("diagnosa"));

                colPrediksi.setCellValueFactory(
                                new PropertyValueFactory<>("hasilPrediksi"));

                colResiko.setCellValueFactory(
                                new PropertyValueFactory<>("tingkatResiko"));

                tablePemeriksaan.setItems(
                                pemeriksaanService.getAll());

        }

        // ==========================
        // LOAD PENDAFTARAN
        // ==========================

        private void loadComboPendaftaran() {

                ObservableList<Pendaftaran> list = pendaftaranService.getAll();

                cbPendaftaran.setItems(list);

        }

        // ==========================
        // LOAD RESIKO
        // ==========================

        private void loadComboResiko() {

                cbResiko.getItems().clear();

                cbResiko.getItems().addAll(
                                "Rendah",
                                "Sedang",
                                "Tinggi");

        }

        // ==========================
        // SIMPAN
        // ==========================

        @FXML
        private void handleSimpan() {

                try {

                        Pemeriksaan p = new Pemeriksaan();

                        if (editMode) {
                                p.setIdPeriksa(Integer.parseInt(txtId.getText()));
                        }

                        p.setPendaftaran(cbPendaftaran.getValue());

                        p.setTanggalPeriksa(
                                        java.sql.Date.valueOf(dpTanggal.getValue()));

                        p.setDiagnosa(txtDiagnosa.getText());

                        p.setTekananDarah(
                                        Double.parseDouble(txtTekanan.getText()));

                        p.setGulaDarah(
                                        Double.parseDouble(txtGula.getText()));

                        p.setSuhu(
                                        Double.parseDouble(txtSuhu.getText()));

                        p.setBeratBadan(
                                        Double.parseDouble(txtBerat.getText()));

                        p.setCatatan(txtCatatan.getText());

                        p.setHasilPrediksi(txtPrediksi.getText());

                        p.setTingkatResiko(cbResiko.getValue());

                        pemeriksaanService.simpan(p, editMode);

                        showAlert("Informasi",
                                        "Data berhasil disimpan.");

                        clearForm();

                        loadData();

                } catch (Exception e) {

                        showAlert("Error",
                                        e.getMessage());

                }

        }

        // ==========================
        // EDIT
        // ==========================

        @FXML
        private void handleEdit() {

                Pemeriksaan p = tablePemeriksaan.getSelectionModel().getSelectedItem();

                if (p == null) {
                        showAlert("Peringatan", "Pilih data yang akan diedit.");
                        return;
                }

                editMode = true;

                selectedData(p);

                btnSimpan.setText("💾 Update");

                txtId.setEditable(false);

                showAlert("Informasi",
                                "Silakan ubah data lalu klik tombol Update.");
        }

        // ==========================
        // HAPUS
        // ==========================

        @FXML
        private Button btnHapus;

        @FXML
        private void handleHapus() {

                Pemeriksaan p = tablePemeriksaan.getSelectionModel().getSelectedItem();

                if (p == null) {

                        showAlert("Peringatan",
                                        "Pilih data yang akan dihapus.");

                        return;

                }

                try {

                        pemeriksaanService.delete(p.getIdPeriksa());

                        loadData();

                        clearForm();

                        btnHapus.setDisable(true);

                        showAlert("Informasi",
                                        "Data berhasil dihapus.");

                } catch (Exception e) {

                        showAlert("Error",
                                        e.getMessage());

                }

        }

        // ==========================
        // CARI
        // ==========================

        @FXML
        private void handleCari() {

                String keyword = txtCari.getText();

                tablePemeriksaan.setItems(

                                pemeriksaanService.search(keyword)

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

        private void selectedData(Pemeriksaan p) {

                txtId.setText(String.valueOf(p.getIdPeriksa()));

                if (p.getTanggalPeriksa() != null) {
                        dpTanggal.setValue(
                                        new java.sql.Date(
                                                        p.getTanggalPeriksa().getTime())
                                                        .toLocalDate());
                }

                cbPendaftaran.setValue(p.getPendaftaran());

                txtDiagnosa.setText(p.getDiagnosa());

                txtTekanan.setText(
                                String.valueOf(p.getTekananDarah()));

                txtGula.setText(
                                String.valueOf(p.getGulaDarah()));

                txtSuhu.setText(
                                String.valueOf(p.getSuhu()));

                txtBerat.setText(
                                String.valueOf(p.getBeratBadan()));

                txtCatatan.setText(p.getCatatan());

                txtPrediksi.setText(p.getHasilPrediksi());

                cbResiko.setValue(p.getTingkatResiko());

                btnHapus.setDisable(false);

        }

        // ==========================
        // CLEAR FORM
        // ==========================

        private void clearForm() {

                btnHapus.setDisable(true);

                txtId.clear();

                dpTanggal.setValue(null);

                cbPendaftaran.getSelectionModel().clearSelection();

                txtDiagnosa.clear();

                txtTekanan.clear();

                txtGula.clear();

                txtSuhu.clear();

                txtBerat.clear();

                txtCatatan.clear();

                txtPrediksi.clear();

                cbResiko.getSelectionModel().clearSelection();

                txtCari.clear();

                tablePemeriksaan.getSelectionModel().clearSelection();

                btnSimpan.setText("💾 Simpan");

                editMode = false;

        }

        // ==========================
        // PILIH DATA TABLE
        // ==========================

        @FXML
        private void initializeTableEvent() {

                tablePemeriksaan.setOnMouseClicked(event -> {

                        Pemeriksaan p = tablePemeriksaan.getSelectionModel().getSelectedItem();

                        if (p != null) {

                                selectedData(p);

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