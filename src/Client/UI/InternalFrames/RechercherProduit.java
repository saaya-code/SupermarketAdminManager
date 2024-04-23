package Client.UI.InternalFrames;

import Classes.Produit;
import Client.CRUD.ProduitDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechercherProduit extends JInternalFrame {
    ProduitDAO produitDAO;
    JTextField idProduitField, nomProduitField, descriptionProduitField, prixProduitField, quantiteProduitField, fournisserField, categorieProduitField, mesurementField;
    JButton rechercherButton, annulerButton, modifierButton;

    public RechercherProduit(ProduitDAO produitDAO) {
        this.setSize(500, 500);
        this.setTitle("Rechercher Produit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.produitDAO = produitDAO;
        initializeComponents();
        createLayout();
        addEventListeners();
    }

    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        idProduitField = new JTextField(15);
        nomProduitField = new JTextField(15);
        descriptionProduitField = new JTextField(20);
        prixProduitField = new JTextField(15);
        quantiteProduitField = new JTextField(15);
        fournisserField = new JTextField(15);
        categorieProduitField = new JTextField(15);
        mesurementField = new JTextField(15);

        rechercherButton = new JButton("Rechercher");
        rechercherButton.setFont(labelFont);
        rechercherButton.setBackground(new Color(0, 153, 204));
        rechercherButton.setForeground(Color.WHITE);

        annulerButton = new JButton("Annuler");
        annulerButton.setFont(labelFont);
        annulerButton.setBackground(new Color(204, 0, 0));
        annulerButton.setForeground(Color.WHITE);

        modifierButton = new JButton("Modifier");
        modifierButton.setFont(labelFont);
        modifierButton.setBackground(new Color(0, 204, 10));
        modifierButton.setForeground(Color.WHITE);
    }

    private void createLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10); // Add some padding
        JLabel titleLabel = new JLabel("Rechercher Produit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);
        addField(panel, "ID Produit:", idProduitField, c, 0);
        addField(panel, "Nom Produit:", nomProduitField, c, 1);
        addField(panel, "Description Produit:", descriptionProduitField, c, 2);
        addField(panel, "Prix Produit:", prixProduitField, c, 3);
        addField(panel, "Quantite Produit:", quantiteProduitField, c, 4);
        addField(panel, "Fournisseur:", fournisserField, c, 5);
        addField(panel, "Categorie Produit:", categorieProduitField, c, 6);
        addField(panel, "Mesurement:", mesurementField, c, 7);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 3;
        panel.add(rechercherButton, c);

        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 3;
        panel.add(modifierButton, c);

        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 3;
        panel.add(annulerButton, c);

        this.add(panel);
    }

    private void addField(JPanel panel, String labelText, JTextField textField, GridBagConstraints c, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = yPos;
        c.gridwidth = 1;
        panel.add(label, c);

        c.gridx = 1;
        c.gridy = yPos;
        c.gridwidth = 2;
        panel.add(textField, c);
    }

    private void addEventListeners() {
        annulerButton.addActionListener(e -> this.dispose());

        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Produit prod = produitDAO.getProduit(Integer.parseInt(idProduitField.getText()));
                if (prod != null) {
                    nomProduitField.setText(prod.getNomProduit());
                    descriptionProduitField.setText(prod.getDescription());
                    prixProduitField.setText(String.valueOf(prod.getPrix()));
                    quantiteProduitField.setText(String.valueOf(prod.getQuantite()));
                    fournisserField.setText(produitDAO.getNomFournisseurById(prod.getIdFour()));
                    categorieProduitField.setText(prod.getCategorie());
                    mesurementField.setText(prod.getMesurementUnit());
                }
            }
        });
        modifierButton.addActionListener(e->{
            String nomProduit = nomProduitField.getText();
            String descriptionProduit = descriptionProduitField.getText();
            int prixProduit = Integer.parseInt(prixProduitField.getText());
            int quantiteProduit = Integer.parseInt(quantiteProduitField.getText());
            String fournisseur = fournisserField.getText();
            String categorieProduit = categorieProduitField.getText();
            String mesurement = mesurementField.getText();
            Produit prod = new Produit(Integer.parseInt(idProduitField.getText()), nomProduit, descriptionProduit, prixProduit, quantiteProduit, mesurement, categorieProduit, produitDAO.getIdFournisseurByNom(fournisseur));
            produitDAO.updateProduit(prod);
            JOptionPane.showMessageDialog(null, "Produit modifié avec succès!");

        });
    }


}
