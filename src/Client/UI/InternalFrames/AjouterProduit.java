package Client.UI.InternalFrames;
import Client.CRUD.ProduitDAO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static Client.Helpers.Serializer.getObjectFromServer;

public class AjouterProduit extends JInternalFrame {
    Socket socket;
    JTextField nomProduitField, descriptionProduitField, prixProduitField, quantiteProduitField;
    JComboBox<String> fournisserField, categorieProduitField, mesurementField;
    JButton ajouterButton, annulerButton;
    ProduitDAO produitDAO;

    public AjouterProduit(Socket s, ProduitDAO produitDAO) throws SQLException, IOException, ClassNotFoundException {
        this.setSize(500, 500);
        this.setTitle("Ajouter Produit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.socket = s;
        this.produitDAO = produitDAO;

        initializeComponents();
        createLayout();
        addEventListeners();
    }


    private void initializeComponents() throws IOException, ClassNotFoundException {
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        nomProduitField = new JTextField(15);
        descriptionProduitField = new JTextField(20);
        prixProduitField = new JTextField(15);
        quantiteProduitField = new JTextField(15);
        fournisserField = new JComboBox<>();
        categorieProduitField = new JComboBox<>(new String[]{"Categorie 1", "Categorie 2", "Categorie 3"});
        mesurementField = new JComboBox<>(new String[]{"Kg", "Litre", "Piece"});
        ajouterButton = new JButton("Ajouter");

        ajouterButton.setFont(labelFont);
        ajouterButton.setBackground(new Color(0, 153, 204));
        ajouterButton.setForeground(Color.WHITE);

        annulerButton = new JButton("Annuler");
        annulerButton.setFont(labelFont);
        annulerButton.setBackground(new Color(204, 0, 0));
        annulerButton.setForeground(Color.WHITE);

        ArrayList<Object> receivedList = (ArrayList<Object>) getObjectFromServer(socket, "SELECT idFour FROM FOURNISSEUR");
        for (Object element : receivedList) {
            fournisserField.addItem(produitDAO.getNomFournisseurById(Integer.parseInt(String.valueOf(element))));
        }
    }

    private void createLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);
        JLabel titleLabel = new JLabel("Ajouter Produit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);

        addField(panel, "Nom Produit:", nomProduitField, c, 0);
        addField(panel, "Description Produit:", descriptionProduitField, c, 1);
        addField(panel, "Prix Produit:", prixProduitField, c, 2);
        addField(panel, "Quantite Produit:", quantiteProduitField, c, 3);
        addField(panel, "Fournisseur:", fournisserField, c, 4);
        addField(panel, "Categorie Produit:", categorieProduitField, c, 5);
        addField(panel, "Mesurement:", mesurementField, c, 6);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        panel.add(ajouterButton, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 3;
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
        c.gridwidth = 2;
        panel.add(component, c);
    }

    private void addEventListeners() {
        ajouterButton.addActionListener((e) -> {
            String nomProduit = nomProduitField.getText();
            String descriptionProduit = descriptionProduitField.getText();
            String prixProduit = prixProduitField.getText();
            String quantiteProduit = quantiteProduitField.getText();
            String fournisseur = (String) fournisserField.getSelectedItem();
            String categorieProduit = (String) categorieProduitField.getSelectedItem();
            String mesurement = (String) mesurementField.getSelectedItem();

            try {
                PrintWriter pr = new PrintWriter(socket.getOutputStream());
                pr.println("UPDATING");
                pr.println("INSERT INTO PRODUIT (nomProduit, description, prix, quantite, idFour, categorie, mesurementunit) " +
                        "VALUES ('" + nomProduit + "', '" + descriptionProduit + "', '" + prixProduit + "', '" + quantiteProduit + "', '" + produitDAO.getIdFournisseurByNom(fournisseur) + "', '" + categorieProduit + "', '" + mesurement + "');");
                pr.flush();
                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès");
                clearInputs();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        annulerButton.addActionListener(e -> dispose());
    }

    private void clearInputs() {
        nomProduitField.setText("");
        descriptionProduitField.setText("");
        prixProduitField.setText("");
        quantiteProduitField.setText("");
        fournisserField.setSelectedIndex(0);
        categorieProduitField.setSelectedIndex(0);
        mesurementField.setSelectedIndex(0);
    }
}
