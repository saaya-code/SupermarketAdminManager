package Client.UI;
import Classes.User;
import Client.CRUD.ProduitDAO;
import Client.UI.InternalFrames.AjouterProduit;
import Client.UI.InternalFrames.RechercherProduit;
import Server.Config;
import Server.MyConnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application extends JFrame{
    public JLabel title;
    JMenuBar menuBar;
    JMenu menuProduit;
    JMenu menuClient;
    JMenu menuCommande;
    JMenuItem menuItemAjouterProduit;
    JMenuItem menuItemRechercherProduit;
    JMenuItem menuItemAjouterClient;
    JMenuItem menuItemRechercherClient;
    JMenuItem menuItemAjouterCommande;
    JMenuItem menuItemRechercherCommande;
    JButton btn;
    JDesktopPane desktop;

    private Socket socket;
    Connection con;
    ProduitDAO produitDAO;
    public Application(User user){
        this.setSize(700, 700);
        this.setTitle("Supermarket Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop = new JDesktopPane();
        this.add(desktop);
        this.setVisible(true);
        this.socket = user.getUserSocket();
        initliazeComponents();
        createLayout();
        addEventListeners();
        this.con = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
        produitDAO = new ProduitDAO(con);

    }
    private void initliazeComponents(){
        title = new JLabel("Welcome to supermarket manager");
        menuBar = new JMenuBar();
        menuProduit = new JMenu("Produit");
        menuClient = new JMenu("Client");
        menuCommande = new JMenu("Commande");
        menuItemAjouterProduit = new JMenuItem("Ajouter");
        menuItemRechercherProduit = new JMenuItem("Rechercher");
        menuItemAjouterClient = new JMenuItem("Ajouter");
        menuItemRechercherClient = new JMenuItem("Rechercher");
        menuItemAjouterCommande = new JMenuItem("Ajouter");
        menuItemRechercherCommande = new JMenuItem("Rechercher");
    }
    private void createLayout(){
        this.setJMenuBar(menuBar);
        menuBar.add(menuProduit);
        menuBar.add(menuClient);
        menuBar.add(menuCommande);
        menuProduit.add(menuItemAjouterProduit);
        menuProduit.add(menuItemRechercherProduit);
        menuClient.add(menuItemAjouterClient);
        menuClient.add(menuItemRechercherClient);
        menuCommande.add(menuItemAjouterCommande);
        menuCommande.add(menuItemRechercherCommande);
        //center the title

    }
    private void addEventListeners(){
        menuItemAjouterProduit.addActionListener(e -> {
            AjouterProduit ajouterProduit = null;
            RechercherProduit rechercherProduit = null;
            try {
                ajouterProduit = new AjouterProduit(socket, produitDAO);

            } catch (SQLException | ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
            desktop.add(ajouterProduit);
        });
        menuItemRechercherProduit.addActionListener(e -> {
            RechercherProduit rechercherProduit = new RechercherProduit(produitDAO);
            desktop.add(rechercherProduit);
        });
    }


}
