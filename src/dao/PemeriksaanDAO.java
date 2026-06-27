package dao;

import database.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dokter;
import model.Pasien;
import model.Pemeriksaan;
import model.Pendaftaran;

public class PemeriksaanDAO {

    Connection conn = DBConnection.connect();

    // LOAD DATA
    public ObservableList<Pemeriksaan> getAllPemeriksaan() {
        ObservableList<Pemeriksaan> list = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT
                        pm.*,
                        pd.keluhan,
                        ps.id_pasien,
                        ps.nama AS nama_pasien,
                        d.id_dokter,
                        d.nama AS nama_dokter
                    FROM pemeriksaan pm
                    JOIN pendaftaran pd
                        ON pm.id_daftar = pd.id_daftar
                    JOIN pasien ps
                        ON pd.id_pasien = ps.id_pasien
                    JOIN dokter d
                        ON pd.id_dokter = d.id_dokter
                    """;

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pemeriksaan p = new Pemeriksaan();
                p.setIdPeriksa(rs.getInt("id_periksa"));

                // Menginisialisasi objek Pendaftaran untuk Foreign Key
                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("id_pasien"));
                pasien.setNama(rs.getString("nama_pasien"));

                Dokter dokter = new Dokter();
                dokter.setIdDokter(rs.getInt("id_dokter"));
                dokter.setNama(rs.getString("nama_dokter"));

                Pendaftaran pendaftaran = new Pendaftaran();
                pendaftaran.setIdDaftar(rs.getInt("id_daftar"));
                pendaftaran.setKeluhan(rs.getString("keluhan"));
                pendaftaran.setPasien(pasien);
                pendaftaran.setDokter(dokter);

                p.setPendaftaran(pendaftaran);

                p.setTanggalPeriksa(rs.getDate("tanggal_periksa"));
                p.setDiagnosa(rs.getString("diagnosa"));
                p.setTekananDarah(rs.getDouble("tekanan_darah"));
                p.setGulaDarah(rs.getDouble("gula_darah"));
                p.setSuhu(rs.getDouble("suhu"));
                p.setBeratBadan(rs.getDouble("berat_badan"));
                p.setCatatan(rs.getString("catatan"));
                p.setHasilPrediksi(rs.getString("hasil_prediksi"));
                p.setTingkatResiko(rs.getString("tingkat_resiko"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // INSERT
    public void insertPemeriksaan(Pemeriksaan p) {
        try {
            String sql = "INSERT INTO pemeriksaan (id_daftar, tanggal_periksa, diagnosa, tekanan_darah, gula_darah, suhu, berat_badan, catatan, hasil_prediksi, tingkat_resiko) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, p.getPendaftaran().getIdDaftar());
            // Konversi java.util.Date ke java.sql.Date
            ps.setDate(2, new java.sql.Date(p.getTanggalPeriksa().getTime()));
            ps.setString(3, p.getDiagnosa());
            ps.setDouble(4, p.getTekananDarah());
            ps.setDouble(5, p.getGulaDarah());
            ps.setDouble(6, p.getSuhu());
            ps.setDouble(7, p.getBeratBadan());
            ps.setString(8, p.getCatatan());
            ps.setString(9, p.getHasilPrediksi());
            ps.setString(10, p.getTingkatResiko());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updatePemeriksaan(Pemeriksaan p) {
        try {
            String sql = "UPDATE pemeriksaan SET id_daftar=?, tanggal_periksa=?, diagnosa=?, tekanan_darah=?, gula_darah=?, suhu=?, berat_badan=?, catatan=?, hasil_prediksi=?, tingkat_resiko=? WHERE id_periksa=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, p.getPendaftaran().getIdDaftar());
            ps.setDate(2, new java.sql.Date(p.getTanggalPeriksa().getTime()));
            ps.setString(3, p.getDiagnosa());
            ps.setDouble(4, p.getTekananDarah());
            ps.setDouble(5, p.getGulaDarah());
            ps.setDouble(6, p.getSuhu());
            ps.setDouble(7, p.getBeratBadan());
            ps.setString(8, p.getCatatan());
            ps.setString(9, p.getHasilPrediksi());
            ps.setString(10, p.getTingkatResiko());

            ps.setInt(11, p.getIdPeriksa());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletePemeriksaan(int id) {
        try {
            String sql = "DELETE FROM pemeriksaan WHERE id_periksa=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SEARCH
    public ObservableList<Pemeriksaan> searchPemeriksaan(String keyword) {
        ObservableList<Pemeriksaan> list = FXCollections.observableArrayList();

        try {
            // Pencarian dikonfigurasi berdasarkan diagnosa atau hasil prediksi
            String sql = "SELECT * FROM pemeriksaan WHERE diagnosa LIKE ? OR hasil_prediksi LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pemeriksaan p = new Pemeriksaan();
                p.setIdPeriksa(rs.getInt("id_periksa"));

                Pendaftaran pendaftaran = new Pendaftaran();
                pendaftaran.setIdDaftar(rs.getInt("id_daftar"));
                p.setPendaftaran(pendaftaran);

                p.setTanggalPeriksa(rs.getDate("tanggal_periksa"));
                p.setDiagnosa(rs.getString("diagnosa"));
                p.setTekananDarah(rs.getDouble("tekanan_darah"));
                p.setGulaDarah(rs.getDouble("gula_darah"));
                p.setSuhu(rs.getDouble("suhu"));
                p.setBeratBadan(rs.getDouble("berat_badan"));
                p.setCatatan(rs.getString("catatan"));
                p.setHasilPrediksi(rs.getString("hasil_prediksi"));
                p.setTingkatResiko(rs.getString("tingkat_resiko"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
