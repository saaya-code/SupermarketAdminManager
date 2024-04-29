package Client.Interfaces;

import Classes.Commande;
import Classes.Produit;
import Classes.User;

import java.sql.ResultSet;

public interface CommandeDaoCRUD {
    public int ajouterCommande(Commande commande);
    public int modifierCommande(Commande commande);
    public int supprimerCommande(int idCommande);
    public Commande getCommande(int idCommande);
    public ResultSet getAllCommandes(User user);

}
