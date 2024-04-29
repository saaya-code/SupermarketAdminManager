package Classes;

public class Commande {
    int idCommande;
    String username;
    int idProduit;
    int quantite;
    String nomClient;
    String adressClient;

    public Commande(int idCommande, String username, int idProduit, int quantite, String nomClient, String adressClient) {
        this.idCommande = idCommande;
        this.username = username;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.nomClient = nomClient;
        this.adressClient = adressClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getAdressClient() {
        return adressClient;
    }

    public void setAdressClient(String adressClient) {
        this.adressClient = adressClient;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
