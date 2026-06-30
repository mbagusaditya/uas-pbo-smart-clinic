package model;

public class Klinik {

    private int idKlinik;
    private String namaKlinik;
    private String alamat;
    private String noTelepon;

    public Klinik() {}

    public int getIdKlinik() {
        return idKlinik;
    }

    public void setIdKlinik(int idKlinik) {
        this.idKlinik = idKlinik;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}
