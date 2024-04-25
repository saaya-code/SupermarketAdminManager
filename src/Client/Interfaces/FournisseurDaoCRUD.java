package Client.Interfaces;

import Classes.Fournisseur;

import java.sql.ResultSet;

public interface FournisseurDaoCRUD {
    public int addFournisseur(Fournisseur four);
    public int deleteFournisseur(int idFour);
    public int updateFournisseur(Fournisseur four);
    public Fournisseur getFournisseur(int idFour);
    public ResultSet getAllFournisseurs();
}
