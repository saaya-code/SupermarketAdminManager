package Client.UI.InternalFrames;
import Classes.Fournisseur;
import Client.CRUD.FournisseurDAO;

import javax.swing.*;
import java.awt.*;


import java.net.Socket;


public class AjouterFournisseur extends JInternalFrame {
    JTextField nomFourField, LocalisationFourField, EmailFourField, debitFourField, creditFourField;
    JButton ajouterButton, annulerButton;
    FournisseurDAO fournisseurDAO;

    public AjouterFournisseur(FournisseurDAO fournisseurDAO){
        this.setSize(500, 500);
        this.setTitle("Ajouter Produit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.fournisseurDAO = fournisseurDAO;
        initializeComponents();
        createLayout();
        addEventListeners();
    }


    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        nomFourField = new JTextField(15);
        LocalisationFourField = new JTextField(20);
        EmailFourField = new JTextField(15);
        debitFourField = new JTextField(15);
        creditFourField = new JTextField(15);
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
        JLabel titleLabel = new JLabel("Ajouter Fournisseur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);

        addField(panel, "Nom Fournisseur:", nomFourField, c, 0);
        addField(panel, "Localisation Fournisseur:", LocalisationFourField, c, 1);
        addField(panel, "Email Fournisseur:", EmailFourField, c, 2);
        addField(panel, "debit:", debitFourField, c, 3);
        addField(panel, "credit:", creditFourField, c, 4);
        

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        panel.add(ajouterButton, c);

        c.gridx = 0;
        c.gridy = 7;
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
        ajouterButton.addActionListener(e -> {
            String nomFour = nomFourField.getText();
            String localisation = LocalisationFourField.getText();
            String email = EmailFourField.getText();
            int debit = Integer.parseInt(debitFourField.getText());
            int credit = Integer.parseInt(creditFourField.getText());
            if(fournisseurDAO.addFournisseur(new Fournisseur(0, nomFour, localisation, email, debit, credit))>0){
                JOptionPane.showMessageDialog(this, "Fournisseur ajouté avec succès");
                clearInputs();
            }
            else{
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du fournisseur");
            }
        });
        annulerButton.addActionListener(e -> dispose());
    }

    private void clearInputs() {
        nomFourField.setText("");
        LocalisationFourField.setText("");
        EmailFourField.setText("");
        debitFourField.setText("");
        
    }
}
