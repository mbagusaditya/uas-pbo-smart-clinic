package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Obat;
import model.Pemeriksaan;
import model.ResepObat;
import service.ObatService;
import service.ResepObatService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormResepController {

    @FXML
    private TextField txtId, txtIdPemeriksaan, txtJumlah, txtDosis, txtKeterangan;

    @FXML
    private ComboBox<Obat> cbObat;

    private ResepObatService resepService = new ResepObatService();
    private ObatService obatService = new ObatService();
    private boolean modeEdit = false;

    @FXML
    public void initialize() {
        ObservableList<Obat> listObat = obatService.getAll();
        cbObat.setItems(listObat);
    }

    public void setModeTambah() {
        modeEdit = false;
    }

    public void setModeEdit(ResepObat r) {
        modeEdit = true;
        txtId.setText(String.valueOf(r.getIdResep()));
        // Asumsi nek pemeriksaan diset ke textfield
        // txtIdPemeriksaan.setText(String.valueOf(r.getPemeriksaan().getIdPemeriksaan()));
        cbObat.setValue(r.getObat());
        txtJumlah.setText(String.valueOf(r.getJumlah()));
        txtDosis.setText(r.getDosis());
        txtKeterangan.setText(r.getKeterangan());
    }

    @FXML
    public void handleSimpan() {
        try {
            if (
                ValidationUtil.isEmpty(
                    txtIdPemeriksaan,
                    "ID Pemeriksaan wajib diisi"
                )
            ) return;
            if (
                ValidationUtil.isEmpty(cbObat, "Pilih obat terlebih dahulu")
            ) return;
            if (ValidationUtil.isEmpty(txtJumlah, "Jumlah wajib diisi")) return;
            if (ValidationUtil.isEmpty(txtDosis, "Dosis wajib diisi")) return;

            ResepObat r = new ResepObat();
            if (modeEdit) r.setIdResep(Integer.parseInt(txtId.getText()));

            Pemeriksaan pem = new Pemeriksaan();
            pem.setIdPemeriksaan(Integer.parseInt(txtIdPemeriksaan.getText()));
            r.setPemeriksaan(pem);

            r.setObat(cbObat.getValue());
            r.setJumlah(Integer.parseInt(txtJumlah.getText()));
            r.setDosis(txtDosis.getText());
            r.setKeterangan(txtKeterangan.getText());

            resepService.simpan(r, modeEdit);
            AlertUtil.success(
                modeEdit ? "Data berhasil diupdate" : "Data berhasil disimpan"
            );
            closeWindow();
        } catch (NumberFormatException e) {
            AlertUtil.error("ID Pemeriksaan dan Jumlah harus berupa angka!");
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBatal() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtJumlah.getScene().getWindow();
        stage.close();
    }
}
