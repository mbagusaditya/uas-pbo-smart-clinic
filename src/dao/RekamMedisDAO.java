package dao;

import database.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dokter;
import model.Pasien;
import model.RekamMedis;

public class RekamMedisDAO {

    Connection conn = DBConnection.connect();

    // =========================
    // GET ALL
    // =========================
    public ObservableList<RekamMedis> getAllRekamMedis() {

        ObservableList<RekamMedis> list =
                FXCollections.observableArrayList();

        try {

            String sql = """
                SELECT
                    rm.*,
                    p.nama AS nama_pasien,
                    d.nama AS nama_dokter

                FROM rekam_medis rm

                JOIN pasien p
                    ON rm.id_pasien = p.id_pasien

                JOIN dokter d
                    ON rm.id_dokter = d.id_dokter
                """;

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("id_pasien"));
                pasien.setNama(rs.getString("nama_pasien"));

                Dokter dokter = new Dokter();
                dokter.setIdDokter(rs.getInt("id_dokter"));
                dokter.setNama(rs.getString("nama_dokter"));

                RekamMedis r = new RekamMedis();

                r.setIdRekam(rs.getInt("id_rekam"));
                r.setDiagnosa(rs.getString("diagnosa"));
                r.setResep(rs.getString("resep"));
                r.setCatatan(rs.getString("catatan"));

                r.setPasien(pasien);
                r.setDokter(dokter);

                list.add(r);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // INSERT
    // =========================
    public void insertRekamMedis(RekamMedis r) {

        try {

            String sql = """
                INSERT INTO rekam_medis
                (diagnosa,resep,catatan,id_pasien,id_dokter)
                VALUES(?,?,?,?,?)
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, r.getDiagnosa());
            ps.setString(2, r.getResep());
            ps.setString(3, r.getCatatan());

            ps.setInt(4,
                    r.getPasien().getIdPasien());

            ps.setInt(5,
                    r.getDokter().getIdDokter());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // =========================
    // UPDATE
    // =========================
    public void updateRekamMedis(RekamMedis r) {

        try {

            String sql = """
                UPDATE rekam_medis
                SET
                    diagnosa=?,
                    resep=?,
                    catatan=?,
                    id_pasien=?,
                    id_dokter=?
                WHERE id_rekam=?
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, r.getDiagnosa());
            ps.setString(2, r.getResep());
            ps.setString(3, r.getCatatan());

            ps.setInt(4,
                    r.getPasien().getIdPasien());

            ps.setInt(5,
                    r.getDokter().getIdDokter());

            ps.setInt(6,
                    r.getIdRekam());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // =========================
    // DELETE
    // =========================
    public void deleteRekamMedis(int id) {

        try {

            String sql =
                    "DELETE FROM rekam_medis WHERE id_rekam=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // =========================
    // SEARCH
    // =========================
    public ObservableList<RekamMedis> search(String keyword) {

        ObservableList<RekamMedis> list =
                FXCollections.observableArrayList();

        try {

            String sql = """
                SELECT
                    rm.*,
                    p.nama AS nama_pasien,
                    d.nama AS nama_dokter

                FROM rekam_medis rm

                JOIN pasien p
                    ON rm.id_pasien=p.id_pasien

                JOIN dokter d
                    ON rm.id_dokter=d.id_dokter

                WHERE
                    p.nama LIKE ?
                    OR d.nama LIKE ?
                    OR rm.diagnosa LIKE ?
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1,"%"+keyword+"%");
            ps.setString(2,"%"+keyword+"%");
            ps.setString(3,"%"+keyword+"%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("id_pasien"));
                pasien.setNama(rs.getString("nama_pasien"));

                Dokter dokter = new Dokter();
                dokter.setIdDokter(rs.getInt("id_dokter"));
                dokter.setNama(rs.getString("nama_dokter"));

                RekamMedis r = new RekamMedis();

                r.setIdRekam(rs.getInt("id_rekam"));
                r.setDiagnosa(rs.getString("diagnosa"));
                r.setResep(rs.getString("resep"));
                r.setCatatan(rs.getString("catatan"));
                r.setPasien(pasien);
                r.setDokter(dokter);

                list.add(r);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

}