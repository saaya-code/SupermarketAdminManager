package Classes;

public class Fournisseur {
    int idFour;
    String nomFour, localisation, email;
    int debit,credit;

    public Fournisseur(int idFour, String nomFour, String localisation, String email, int debit, int credit) {
        this.idFour = idFour;
        this.nomFour = nomFour;
        this.localisation = localisation;
        this.email = email;
        this.debit = debit;
        this.credit = credit;
    }

    public int getIdFour() {
        return idFour;
    }

    public void setIdFour(int idFour) {
        this.idFour = idFour;
    }

    public String getNomFour() {
        return nomFour;
    }

    public void setNomFour(String nomFour) {
        this.nomFour = nomFour;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
