package Classes;

public class Produit {
    int IdProduit;
    String NomProduit;
    String Description;
    int Prix;
    int Quantite;
    String mesurementUnit;
    String Categorie;
    int idFour;
    public Produit(int IdProduit, String NomProduit, String Description, int Prix, int Quantite, String mesurementUnit, String Categorie, int idFour) {
        this.IdProduit = IdProduit;
        this.NomProduit = NomProduit;
        this.Description = Description;
        this.Prix = Prix;
        this.Quantite = Quantite;
        this.mesurementUnit = mesurementUnit;
        this.Categorie = Categorie;
        this.idFour = idFour;
    }

    public int getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(int idProduit) {
        IdProduit = idProduit;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String nomProduit) {
        NomProduit = nomProduit;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrix() {
        return Prix;
    }

    public void setPrix(int prix) {
        Prix = prix;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public String getMesurementUnit() {
        return mesurementUnit;
    }

    public void setMesurementUnit(String mesurementUnit) {
        this.mesurementUnit = mesurementUnit;
    }

    public String getCategorie() {
        return Categorie;
    }

    public int getIdFour() {
        return idFour;
    }

    public void setIdFour(int idFour) {
        this.idFour = idFour;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }
}
