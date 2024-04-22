package Client.CRUD;

import Classes.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProduitDAO implements Client.Interfaces.ProduitDaoCRUD{
    Connection con;
    public ProduitDAO(Connection con){
        this.con = con;
    }

    @Override
    public int addProduit(Produit prod) {
        String query = "INSERT INTO Produit (Idproduit,nomProduit,description,prix,quantite,categorie,mesurementunit,idFour) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1,prod.getIdProduit());
            ps.setString(2,prod.getNomProduit());
            ps.setString(3,prod.getDescription());
            ps.setInt(4,prod.getPrix());
            ps.setInt(5,prod.getQuantite());
            ps.setString(6,prod.getCategorie());
            ps.setString(7,prod.getMesurementUnit());
            ps.setInt(8,prod.getIdFour());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteProduit(int IdProd) {
        String query = "DELETE FROM Produit WHERE IdProduit = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1,IdProd);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateProduit(Produit prod) {
        String query = "UPDATE Produit SET nomProduit = ?, description = ?, prix = ?, quantite = ?, categorie = ?, mesurementunit = ?, idFour = ? WHERE IdProduit = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1,prod.getNomProduit());
            ps.setString(2,prod.getDescription());
            ps.setInt(3,prod.getPrix());
            ps.setInt(4,prod.getQuantite());
            ps.setString(5,prod.getCategorie());
            ps.setString(6,prod.getMesurementUnit());
            ps.setInt(7,prod.getIdFour());
            ps.setInt(8,prod.getIdProduit());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Produit getProduit(int IdProd) {
        String query = "SELECT * FROM Produit WHERE IdProduit = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, IdProd);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Produit(rs.getInt("IdProduit"), rs.getString("nomProduit"), rs.getString("description"), rs.getInt("prix"), rs.getInt("quantite"), rs.getString("mesurementunit"), rs.getString("categorie"), rs.getInt("idFour"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public ResultSet getAllProduits() {
        String query = "SELECT * FROM Produit";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNomFournisseurById(int IdFour){
        String query = "SELECT nomFour FROM Fournisseur WHERE IdFour = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, IdFour);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nomFour");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
