package Client.Interfaces;

import Classes.Produit;

import java.sql.ResultSet;

public interface ProduitDaoCRUD {
    public int addProduit(Produit prod);
    public int deleteProduit(int IdProd);
    public int updateProduit(Produit prod);
    public Produit getProduit(int IdProd);
    public ResultSet getAllProduits();
}
