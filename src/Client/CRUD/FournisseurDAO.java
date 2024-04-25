package Client.CRUD;

import Classes.Fournisseur;
import Client.Interfaces.FournisseurDaoCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FournisseurDAO implements FournisseurDaoCRUD {
    Connection con;

    public FournisseurDAO(Connection con) {
        this.con = con;
    }

    @Override
    public int addFournisseur(Fournisseur four) {
        String query = "INSERT INTO Fournisseur (nomFour,localisation,email,debit,credit) VALUES (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, four.getNomFour());
            ps.setString(2, four.getLocalisation());
            ps.setString(3, four.getEmail());
            ps.setInt(4, four.getDebit());
            ps.setInt(5, four.getCredit());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteFournisseur(int idFour) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Fournisseur WHERE idFour = ?");
            ps.setInt(1, idFour);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateFournisseur(Fournisseur four) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Fournisseur SET nomFour = ?, localisation = ?, email = ?, debit = ?, credit = ? WHERE idFour = ?");
            ps.setString(1, four.getNomFour());
            ps.setString(2, four.getLocalisation());
            ps.setString(3, four.getEmail());
            ps.setInt(4, four.getDebit());
            ps.setInt(5, four.getCredit());
            ps.setInt(6, four.getIdFour());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Fournisseur getFournisseur(int idFour) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Fournisseur WHERE idFour = ?");
            ps.setInt(1, idFour);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Fournisseur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet getAllFournisseurs() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Fournisseur");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}