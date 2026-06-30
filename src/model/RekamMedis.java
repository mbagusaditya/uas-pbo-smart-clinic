package model;

public class RekamMedis {

    private int idRekam;

    private String diagnosa;
    private String resep;
    private String catatan;

    private Pasien pasien;
    private Dokter dokter;

    public RekamMedis() {
    }

    public RekamMedis(
            int idRekam,
            String diagnosa,
            String resep,
            String catatan,
            Pasien pasien,
            Dokter dokter) {

        this.idRekam = idRekam;
        this.diagnosa = diagnosa;
        this.resep = resep;
        this.catatan = catatan;
        this.pasien = pasien;
        this.dokter = dokter;
    }

    public int getIdRekam() {
        return idRekam;
    }

    public void setIdRekam(int idRekam) {
        this.idRekam = idRekam;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getResep() {
        return resep;
    }

    public void setResep(String resep) {
        this.resep = resep;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    // =========================
    // Helper untuk TableView
    // =========================

    public String getNamaPasien() {
        return pasien != null ? pasien.getNama() : "";
    }

    public String getNamaDokter() {
        return dokter != null ? dokter.getNama() : "";
    }

    @Override
    public String toString() {
        return idRekam + " - " + getNamaPasien();
    }
}