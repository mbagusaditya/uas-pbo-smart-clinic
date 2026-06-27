package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Klinik;
import service.KlinikService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormKlinikController {

    @FXML
    private TextField txtId, txtNama, txtAlamat, txtTelp;

    KlinikService klinikService = new KlinikService();
    private boolean modeEdit = false;

    public void setModeTambah() {
        modeEdit = false;
    }

    public void setModeEdit(Klinik k) {
        modeEdit = true;
        txtId.setText(String.valueOf(k.getIdKlinik()));
        txtNama.setText(k.getNamaKlinik());
        txtAlamat.setText(k.getAlamat());
        txtTelp.setText(k.getNoTelepon());
    }

    @FXML
    public void handleSimpan() {
        try {
            if (
                ValidationUtil.isEmpty(txtNama, "Nama Klinik wajib diisi")
            ) return;
            if (ValidationUtil.isEmpty(txtAlamat, "Alamat wajib diisi")) return;
            if (
                ValidationUtil.isEmpty(txtTelp, "No Telepon wajib diisi")
            ) return;

            Klinik k = new Klinik();
            if (modeEdit) k.setIdKlinik(Integer.parseInt(txtId.getText()));
            k.setNamaKlinik(txtNama.getText());
            k.setAlamat(txtAlamat.getText());
            k.setNoTelepon(txtTelp.getText());

            klinikService.simpan(k, modeEdit);
            AlertUtil.success(
                modeEdit ? "Data berhasil diupdate" : "Data berhasil disimpan"
            );
            closeWindow();
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
        Stage stage = (Stage) txtNama.getScene().getWindow();
        stage.close();
    }
}
