package Client.UI.InternalFrames;

import Classes.Commande;
import Classes.User;
import Client.CRUD.CommandeDAO;
import Client.CRUD.ProduitDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AjouterCommande extends JInternalFrame {
    JTextField  usernameField, addressClient, quantiteField;
    JComboBox<String> nomProduit;
    User user;
    JButton ajouterButton, annulerButton;
    CommandeDAO commandeDAO;
    ProduitDAO produitDAO;

    public AjouterCommande(CommandeDAO commandeDAO, User user, ProduitDAO produitDAO) {
        this.setSize(500, 300);
        this.setTitle("Ajouter Commande");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.commandeDAO = commandeDAO;
        this.user = user;
        this.produitDAO = produitDAO;
        initializeComponents();
        createLayout();
        addEventListeners();
    }

    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        usernameField = new JTextField(15);
        addressClient = new JTextField(15);
        quantiteField = new JTextField(15);
        nomProduit = new JComboBox<String>();
        ResultSet rs = produitDAO.getAllProduits();
        try {
            while (rs.next()) {
                String nom = rs.getString("nomproduit");
                nomProduit.addItem(nom);
                //System.out.println(nom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ajouterButton = new JButton("Ajouter");
        ajouterButton.setFont(labelFont);
        ajouterButton.setBackground(new Color(0, 153, 204));
        ajouterButton.setForeground(Color.WHITE);

        annulerButton = new JButton("Annuler");
        annulerButton.setFont(labelFont);
        annulerButton.setBackground(new Color(204, 0, 0));
        annulerButton.setForeground(Color.WHITE);
    }

    private void createLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);
        JLabel titleLabel = new JLabel("Ajouter Commande");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);

        addField(panel, "Nom Client :", usernameField, c, 0);
        addField(panel, "Produit:", nomProduit, c, 1);
        addField(panel, "Quantité:", quantiteField, c, 2);
        addField(panel, "Address Client:", addressClient, c, 3);


        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        panel.add(ajouterButton, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        panel.add(annulerButton, c);

        this.add(panel);
    }

    private void addField(JPanel panel, String labelText, JComponent component, GridBagConstraints c, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = yPos;
        c.gridwidth = 1;
        panel.add(label, c);

        c.gridx = 1;
        c.gridy = yPos;
        c.gridwidth = 1;
        panel.add(component, c);
    }

    private void addEventListeners() {
        ajouterButton.addActionListener(e -> {
            String nom = (String) nomProduit.getSelectedItem();
            String username = user.getUsername();
            int idProduit = produitDAO.getProduitIdByNom(nom);
            int quantite = Integer.parseInt(quantiteField.getText());
            String address = addressClient.getText();
            String client = usernameField.getText();

            int res = commandeDAO.ajouterCommande(new Commande(0, username, idProduit, quantite, client, address));
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Commande ajoutée avec succès");
                clearInputs();
            }else if(res == -1){
                JOptionPane.showMessageDialog(this, "Quantité insuffisante");
            }
            else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la commande");
            }
        });
        annulerButton.addActionListener(e -> dispose());
    }

    private void clearInputs() {
        nomProduit.setSelectedIndex(0);
        usernameField.setText("");
        addressClient.setText("");
        quantiteField.setText("");
    }
}
