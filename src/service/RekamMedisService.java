package service;

import dao.RekamMedisDAO;
import javafx.collections.ObservableList;
import model.RekamMedis;

public class RekamMedisService {

    // =========================
    // DAO
    // =========================

    private RekamMedisDAO dao = new RekamMedisDAO();

    // =========================
    // GET ALL
    // =========================

    public ObservableList<RekamMedis> getAll() {
        return dao.getAllRekamMedis();
    }

    // =========================
    // SEARCH
    // =========================

    public ObservableList<RekamMedis> search(String keyword) {
        return dao.search(keyword);
    }

    // =========================
    // DELETE
    // =========================

    public void delete(int id) throws Exception {

        if (id <= 0) {
            throw new Exception("ID Rekam Medis tidak valid");
        }

        dao.deleteRekamMedis(id);
    }

    // =========================
    // SIMPAN
    // =========================

    public void simpan(RekamMedis rekam, boolean editMode) throws Exception {

        // =====================
        // VALIDASI
        // =====================

        if (rekam.getPasien() == null) {
            throw new Exception("Pasien wajib dipilih");
        }

        if (rekam.getDokter() == null) {
            throw new Exception("Dokter wajib dipilih");
        }

        if (rekam.getDiagnosa() == null ||
                rekam.getDiagnosa().trim().isEmpty()) {

            throw new Exception("Diagnosa wajib diisi");
        }

        if (rekam.getResep() == null ||
                rekam.getResep().trim().isEmpty()) {

            throw new Exception("Resep wajib diisi");
        }

        if (rekam.getCatatan() == null ||
                rekam.getCatatan().trim().isEmpty()) {

            throw new Exception("Catatan wajib diisi");
        }

        // =====================
        // INSERT / UPDATE
        // =====================

        if (editMode) {

            dao.updateRekamMedis(rekam);

        } else {

            dao.insertRekamMedis(rekam);

        }

    }

}