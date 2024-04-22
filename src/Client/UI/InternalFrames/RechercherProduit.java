package Client.UI.InternalFrames;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class RechercherProduit extends JInternalFrame {
    Socket socket;
    JLabel idProduitLabel;
    JTextField idProduitField;
    JLabel nomProduitLabel;
    JTextField nomProduitField;
    JLabel descriptionProduitLabel;
    JTextField descriptionProduitField;
    JLabel prixProduitLabel;
    JTextField prixProduitField;
    JLabel quantiteProduitLabel;
    JTextField quantiteProduitField;
    JLabel fournisserLabel;
    JTextField fournisserField;
    JLabel categorieProduitLabel;
    JTextField categorieProduitField;
    JLabel mesurementLabel;
    JTextField mesurementField;

    JButton rechercherButton;
    JButton annulerButton;

    public RechercherProduit(Socket s) {
        this.setSize(500, 500);
        this.setTitle("Rechercher Produit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.socket = s;
        initializeComponents();
        createLayout();
        //addEventListeners();
    }
    private void initializeComponents() {
        idProduitLabel = new JLabel("ID Produit :");
        idProduitField = new JTextField(15);
        nomProduitLabel = new JLabel("Nom Produit :");
        nomProduitField = new JTextField(15);
        descriptionProduitLabel = new JLabel("Description Produit :");
        descriptionProduitField = new JTextField(20);
        prixProduitLabel = new JLabel("Prix Produit :");
        prixProduitField = new JTextField(15);
        quantiteProduitLabel = new JLabel("Quantite Produit :");
        quantiteProduitField = new JTextField(15);
        fournisserLabel = new JLabel("Fournisseur :");
        fournisserField = new JTextField(15);
        categorieProduitLabel = new JLabel("Categorie Produit :");
        categorieProduitField = new JTextField(15);
        mesurementLabel = new JLabel("Mesurement :");
        mesurementField = new JTextField(15);
        rechercherButton = new JButton("Rechercher");
        annulerButton = new JButton("Annuler");
    }
    private void createLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        addComponent(panel, idProduitLabel, c, 0, 0, 1, 1);
        addComponent(panel, idProduitField, c, 1, 0, 2, 1);
        addComponent(panel, nomProduitLabel, c, 0, 1, 1, 1);
        addComponent(panel, nomProduitField, c, 1, 1, 2, 1);
        addComponent(panel, descriptionProduitLabel, c, 0, 2, 1, 1);
        addComponent(panel, descriptionProduitField, c, 1, 2, 2, 1);
        addComponent(panel, prixProduitLabel, c, 0, 3, 1, 1);
        addComponent(panel, prixProduitField, c, 1, 3, 2, 1);
        addComponent(panel, quantiteProduitLabel, c, 0, 4, 1, 1);
        addComponent(panel, quantiteProduitField, c, 1, 4, 2, 1);
        addComponent(panel, fournisserLabel, c, 0, 5, 1, 1);
        addComponent(panel, fournisserField, c, 1, 5, 2, 1);
        addComponent(panel, categorieProduitLabel, c, 0, 6, 1, 1);
        addComponent(panel, categorieProduitField, c, 1, 6, 2, 1);
        addComponent(panel, mesurementLabel, c, 0, 7, 1, 1);
        addComponent(panel, mesurementField, c, 1, 7, 2, 1);
        addComponent(panel, rechercherButton, c, 0, 8, 1, 1);
        addComponent(panel, annulerButton, c, 1, 8, 1, 1);
        this.add(panel);
    }
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        panel.add(component, c);
    }
}
