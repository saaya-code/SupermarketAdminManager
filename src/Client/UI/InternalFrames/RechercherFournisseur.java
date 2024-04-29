package Client.UI.InternalFrames;

import Classes.Fournisseur;
import Client.CRUD.FournisseurDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechercherFournisseur extends JInternalFrame {
    FournisseurDAO fournisseurDAO;
    JTextField idFournisseurField, nomFournisseurField, localisationFournisseurField, emailFournisseurField;
    JTextField creditFournisseurField, debitFournisseurField;
    JButton rechercherButton, annulerButton, modifierButton;

    public RechercherFournisseur(FournisseurDAO fournisseurDAO) {
        this.setSize(500, 500);
        this.setTitle("Rechercher Fournisseur");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.fournisseurDAO = fournisseurDAO;
        initializeComponents();
        createLayout();
        addEventListeners();
    }

    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        idFournisseurField = new JTextField(15);
        nomFournisseurField = new JTextField(15);
        localisationFournisseurField = new JTextField(20);
        emailFournisseurField = new JTextField(15);
        creditFournisseurField = new JTextField(15);
        debitFournisseurField = new JTextField(15);

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
        JLabel titleLabel = new JLabel("Rechercher Fournisseur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);
        addField(panel, "ID Fournisseur:", idFournisseurField, c, 0);
        addField(panel, "Nom Fournisseur:", nomFournisseurField, c, 1);
        addField(panel, "Localisation Fournisseur:", localisationFournisseurField, c, 2);
        addField(panel, "Email Fournisseur:", emailFournisseurField, c, 3);
        addField(panel, "Debit:", debitFournisseurField, c, 4);
        addField(panel, "Credit:", creditFournisseurField, c, 5);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        panel.add(rechercherButton, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        panel.add(modifierButton, c);

        c.gridx = 0;
        c.gridy = 8;
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
                Fournisseur fournisseur = fournisseurDAO.getFournisseur(Integer.parseInt(idFournisseurField.getText()));
                if (fournisseur != null) {
                    nomFournisseurField.setText(fournisseur.getNomFour());
                    localisationFournisseurField.setText(fournisseur.getLocalisation());
                    emailFournisseurField.setText(fournisseur.getEmail());
                    debitFournisseurField.setText(String.valueOf(fournisseur.getDebit()));
                    creditFournisseurField.setText(String.valueOf(fournisseur.getCredit()));
                }
            }
        });
        modifierButton.addActionListener(e -> {
            String nomFournisseur = nomFournisseurField.getText();
            String localisation = localisationFournisseurField.getText();
            String email = emailFournisseurField.getText();
            int idFournisseur = Integer.parseInt(idFournisseurField.getText());
            Fournisseur fournisseur = new Fournisseur(idFournisseur, nomFournisseur, localisation, email, 0, 0); // Assuming debit and credit are not editable here
            fournisseurDAO.updateFournisseur(fournisseur);
            JOptionPane.showMessageDialog(null, "Fournisseur modifié avec succès!");
        });
    }
}
