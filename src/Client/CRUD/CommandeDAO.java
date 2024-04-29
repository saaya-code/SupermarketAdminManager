package Client.CRUD;

import Classes.Commande;
import Classes.Produit;
import Classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandeDAO implements Client.Interfaces.CommandeDaoCRUD{
    Connection con;
    public CommandeDAO(Connection con){
        this.con = con;
    }

    @Override
    public int ajouterCommande(Commande com) {
String query = "INSERT INTO Commande (username,idProduit,quantite,nomClient,addressClient) VALUES (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1,com.getUsername());
            ps.setInt(2,com.getIdProduit());
            ps.setInt(3,com.getQuantite());
            ps.setString(4,com.getNomClient());
            ps.setString(5,com.getAdressClient());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int supprimerCommande(int idCommande) {
        String query = "DELETE FROM Commande WHERE IdCommande = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1,idCommande);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int modifierCommande(Commande com) {
        String query = "UPDATE Commande SET username = ?, idProduit = ?, quantite = ?, nomClient = ?, addressClient = ? WHERE IdCommande = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1,com.getUsername());
            ps.setInt(2,com.getIdProduit());
            ps.setInt(3,com.getQuantite());
            ps.setString(4,com.getNomClient());
            ps.setString(5,com.getAdressClient());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Commande getCommande(int idCom) {
        String query = "SELECT * FROM Commande WHERE IdCommande = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1,idCom);
            rs = ps.executeQuery();
            if(rs.next()){
                Commande com = new Commande(rs.getInt("IdCommande"),rs.getString("username"),rs.getInt("idProduit"),rs.getInt("quantite"),rs.getString("nomClient"),rs.getString("addressClient"));
                return com;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getAllCommandes(User user) {
        String query = "SELECT * FROM Commande where username = '"+user.getUsername()+"'";
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


    public void confirmerCommande(int valueAt) {
        String query = "UPDATE Commande SET status = 1 WHERE IdCommande = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1,valueAt);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
