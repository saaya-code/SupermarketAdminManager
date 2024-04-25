package Client.UI;
import Classes.User;
import Client.CRUD.FournisseurDAO;
import Client.CRUD.ProduitDAO;
import Client.UI.InternalFrames.*;
import Server.Config;
import Server.MyConnection;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Application extends JFrame{
    public JLabel title;
    JMenuBar menuBar;
    JMenu menuProduit;
    JMenu menuClient;
    JMenu menuCommande;
    JMenu chat;
    JMenuItem menuItemAjouterProduit;
    JMenuItem menuItemRechercherProduit;
    JMenuItem menuItemAfficherProduit;
    JMenuItem menuItemAjouterClient;
    JMenuItem menuItemRechercherClient;
    JMenuItem getMenuItemAfficherClient;
    JMenuItem menuItemAjouterCommande;
    JMenuItem menuItemRechercherCommande;
    JMenuItem menuItemAfficherCommande;
    JMenuItem joindreChat;

    JDesktopPane desktop;

    private Socket socket;
    Connection con;
    ProduitDAO produitDAO;
    FournisseurDAO fournisseurDAO;

    public Application(User user){
        this.setSize(700, 700);
        this.setTitle("Supermarket Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("C:\\Users\\boual\\IdeaProjects\\untitled\\src\\assets\\ajoutImage.png");

        // Set the icon image for the JFrame
        setIconImage(icon.getImage());
        desktop = new JDesktopPane();
        this.add(desktop);
        this.setVisible(true);
        this.socket = user.getUserSocket();
        initliazeComponents();
        createLayout();
        addEventListeners();
        this.con = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
        produitDAO = new ProduitDAO(con);
        fournisseurDAO = new FournisseurDAO(con);
        new Thread(()->{
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String message = br.readLine();
                    if(message.contains("disconnect")){
                        socket.close();
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
    private void initliazeComponents(){
        title = new JLabel("Welcome to supermarket manager");
        menuBar = new JMenuBar();
        menuProduit = new JMenu("Produit");
        menuClient = new JMenu("Client");
        menuCommande = new JMenu("Commande");
        chat = new JMenu("Chat");
        menuItemAjouterProduit = new JMenuItem("Ajouter");
        menuItemRechercherProduit = new JMenuItem("Rechercher");
        menuItemAfficherProduit = new JMenuItem("Afficher");
        menuItemAjouterClient = new JMenuItem("Ajouter");
        menuItemRechercherClient = new JMenuItem("Rechercher");
        getMenuItemAfficherClient = new JMenuItem("Afficher");
        menuItemAjouterCommande = new JMenuItem("Ajouter");
        menuItemRechercherCommande = new JMenuItem("Rechercher");
        menuItemAfficherCommande = new JMenuItem("Afficher");
        joindreChat = new JMenuItem("Joindre");

    }
    private void createLayout(){
        this.setJMenuBar(menuBar);
        menuBar.add(menuProduit);
        menuBar.add(menuClient);
        menuBar.add(menuCommande);
        menuProduit.add(menuItemAjouterProduit);
        menuProduit.add(menuItemRechercherProduit);
        menuProduit.add(menuItemAfficherProduit);
        menuClient.add(menuItemAjouterClient);
        menuClient.add(menuItemRechercherClient);
        menuClient.add(getMenuItemAfficherClient);
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
        menuItemAfficherProduit.addActionListener(e -> {
            AfficherProduits afficherProduit = new AfficherProduits(produitDAO, socket);
            desktop.add(afficherProduit);
        });
        menuItemAjouterClient.addActionListener(e->{
            AjouterFournisseur ajouterFournisseur = new AjouterFournisseur(fournisseurDAO);
            desktop.add(ajouterFournisseur);
        });
        menuItemRechercherClient.addActionListener(e->{
            RechercherFournisseur rechercherFournisseur = new RechercherFournisseur(fournisseurDAO);
            desktop.add(rechercherFournisseur);
        });

    }


}
