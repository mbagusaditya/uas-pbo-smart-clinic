package service;

import dao.PemeriksaanDAO;
import javafx.collections.ObservableList;
import model.Pemeriksaan;

public class PemeriksaanService {

    private PemeriksaanDAO dao = new PemeriksaanDAO();

    // =========================
    // GET ALL
    // =========================
    public ObservableList<Pemeriksaan> getAll() {
        return dao.getAllPemeriksaan();
    }

    // =========================
    // SEARCH
    // =========================
    public ObservableList<Pemeriksaan> search(String keyword) {
        return dao.searchPemeriksaan(keyword);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID pemeriksaan tidak valid");
        }

        dao.deletePemeriksaan(id);
    }

    // =========================
    // SIMPAN
    // =========================
    public void simpan(Pemeriksaan pemeriksaan, boolean modeEdit)
        throws Exception {
        // VALIDASI

        if (
            pemeriksaan.getNamaPasien() == null ||
            pemeriksaan.getNamaPasien().trim().isEmpty()
        ) {
            throw new Exception("Pasien harus dipilih");
        }

        if (
            pemeriksaan.getNamaDokter() == null ||
            pemeriksaan.getNamaDokter().trim().isEmpty()
        ) {
            throw new Exception("Dokter harus dipilih");
        }

        // INSERT / UPDATE

        if (modeEdit) {
            dao.updatePemeriksaan(pemeriksaan);
        } else {
            dao.insertPemeriksaan(pemeriksaan);
        }
    }
}
