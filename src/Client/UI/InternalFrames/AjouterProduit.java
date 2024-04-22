package Client.UI.InternalFrames;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Client.Helpers.Serilizer.getObjectFromServer;

public class AjouterProduit extends JInternalFrame {
    Socket socket;
    JLabel nomProduitLabel;
    JTextField nomProduitField;
    JLabel descriptionProduitLabel;
    JTextField descriptionProduitField;

    JLabel prixProduitLabel;
    JTextField prixProduitField;
    JLabel quantiteProduitLabel;
    JTextField quantiteProduitField;
    JLabel fournisserLabel;
    JComboBox<String> fournisserField;
    JLabel categorieProduitLabel;
    JComboBox<String> categorieProduitField;
    JLabel mesurementLabel;
    JComboBox<String> mesurementField;

    JButton ajouterButton;
    JButton annulerButton;


    public AjouterProduit(Socket s) throws SQLException, IOException, ClassNotFoundException {
        this.setSize(500, 500);
        this.setTitle("Ajouter Produit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.socket = s;
        initializeComponents();
        createLayout();
        addEventListeners();

    }

    private void initializeComponents() throws IOException, ClassNotFoundException, SQLException {
        nomProduitLabel = new JLabel("Nom Produit :");
        nomProduitField = new JTextField(15);
        descriptionProduitLabel = new JLabel("Description Produit :");
        descriptionProduitField = new JTextField(20);
        prixProduitLabel = new JLabel("Prix Produit :");
        prixProduitField = new JTextField(15);
        quantiteProduitLabel = new JLabel("Quantite Produit :");
        quantiteProduitField = new JTextField(15);
        fournisserLabel = new JLabel("Fournisseur :");
        fournisserField = new JComboBox<>();
        categorieProduitLabel = new JLabel("Categorie Produit :");
        categorieProduitField = new JComboBox<>(new String[]{"Categorie 1", "Categorie 2", "Categorie 3"});
        mesurementLabel = new JLabel("Mesurement :");
        mesurementField = new JComboBox<>(new String[]{"Kg", "Litre", "Piece"});
        ajouterButton = new JButton("Ajouter");
        annulerButton = new JButton("Annuler");


        // Print received ArrayList
        System.out.println("Received ArrayList:");
        ArrayList<Integer> receivedList = (ArrayList<Integer>) getObjectFromServer(socket, "SELECT idFour FROM FOURNISSEUR");
        for (Integer element : receivedList) {
            fournisserField.addItem(String.valueOf(element));
        }

    }
    private void createLayout(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Add labels and corresponding components
        c.fill = GridBagConstraints.HORIZONTAL;
        addComponent(panel, nomProduitLabel, c, 0, 0, 1, 1);
        addComponent(panel, nomProduitField, c, 1, 0, 2, 1);
        addComponent(panel, descriptionProduitLabel, c, 0, 1, 1, 1);
        addComponent(panel, descriptionProduitField, c, 1, 1, 2, 1);
        addComponent(panel, prixProduitLabel, c, 0, 2, 1, 1);
        addComponent(panel, prixProduitField, c, 1, 2, 2, 1);
        addComponent(panel, quantiteProduitLabel, c, 0, 3, 1, 1);
        addComponent(panel, quantiteProduitField, c, 1, 3, 2, 1);
        addComponent(panel, fournisserLabel, c, 0, 4, 1, 1);
        addComponent(panel, fournisserField, c, 1, 4, 2, 1);
        addComponent(panel, categorieProduitLabel, c, 0, 5, 1, 1);
        addComponent(panel, categorieProduitField, c, 1, 5, 2, 1);
        addComponent(panel, mesurementLabel, c, 0, 6, 1, 1);
        addComponent(panel, mesurementField, c, 1, 6, 2, 1);
        addComponent(panel, ajouterButton, c, 0, 7, 1, 1);
        addComponent(panel, annulerButton, c, 1, 7, 1, 1);
        add(panel);

    }
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        panel.add(component, c);
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
    private void addEventListeners() {
        ajouterButton.addActionListener((e) -> {
            //change reference to int
            String nomProduit = nomProduitField.getText();
            String descriptionProduit = descriptionProduitField.getText();
            String prixProduit = prixProduitField.getText();
            String quantiteProduit = quantiteProduitField.getText();
            String fournisser = (String) fournisserField.getSelectedItem();
            String categorieProduit = (String) categorieProduitField.getSelectedItem();
            String mesurement = (String) mesurementField.getSelectedItem();

            //send data to server
            try {
                PrintWriter pr = new PrintWriter(socket.getOutputStream());
                pr.println("UPDATING");
                pr.println("INSERT INTO PRODUIT (nomProduit, description, prix, quantite, idFour, categorie, mesurementunit) VALUES ('"+nomProduit+"', '"+descriptionProduit+"', '"+prixProduit+"', '"+quantiteProduit+"', '"+fournisser+"', '"+categorieProduit+"', '"+mesurement+"');");
                pr.flush();
                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès");
                clearInputs();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        annulerButton.addActionListener(e -> dispose());
    }
}
