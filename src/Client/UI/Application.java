package Client.UI;
import Classes.User;
import Client.CRUD.FournisseurDAO;
import Client.CRUD.ProduitDAO;
import Client.UI.InternalFrames.*;
import Server.Config;
import Server.MyConnection;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
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
    User user;

    public Application(User user){
        this.setSize(700, 700);
        this.setTitle("Supermarket Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon("C:\\Users\\boual\\IdeaProjects\\untitled\\src\\assets\\ajoutImage.png");
        this.user = user;
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
        displayWeather();
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
                ajouterProduit = new AjouterProduit(socket, produitDAO, fournisseurDAO);

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

    private void displayWeather() {
        JPanel weatherPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JSONObject weatherData = getWeatherData();
        String weatherDescription = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = weatherData.getJSONObject("main").getDouble("temp") - 273.15;
        String iconCode = weatherData.getJSONArray("weather").getJSONObject(0).getString("icon");
        String humidite = String.valueOf(weatherData.getJSONObject("main").getInt("humidity"));
        String pression = String.valueOf(weatherData.getJSONObject("main").getInt("pressure"));
        String vent = String.valueOf(weatherData.getJSONObject("wind").getInt("speed"));


        JLabel weatherDescriptionLabel = new JLabel("Desciption du meteo : "+weatherDescription+" at Sousse");
        JLabel temperatureLabel = new JLabel("Température : "+ String.format("%.2f", temperature)+" C");
        JLabel humiditeLabel = new JLabel("Humidité : "+humidite+" %");
        JLabel pressionLabel = new JLabel("Pression : "+pression+" hPa");
        JLabel ventLabel = new JLabel("Vitesse du vent : "+vent+" m/s");

        //JLabel iconLabel = new JLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel greetings = new JLabel("Hello, " + this.user.getUsername());
        greetings.setFont(new Font("Arial", Font.BOLD, 30)); // Increase font size
        greetings.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        weatherPanel.add(greetings, gbc);
        gbc.gridy = 1;
        weatherPanel.add(weatherDescriptionLabel, gbc);

        gbc.gridy = 2;
        weatherPanel.add(temperatureLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        weatherPanel.add(displayIcon(iconCode), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        weatherPanel.add(humiditeLabel, gbc);

        gbc.gridy = 4;
        weatherPanel.add(pressionLabel, gbc);

        gbc.gridy = 5;
        weatherPanel.add(ventLabel, gbc);
        this.add(weatherPanel, BorderLayout.CENTER);
    }

    private JSONObject getWeatherData() {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Sousse&appid=6de4eb69b72e233e36fedce9b075052b");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response.toString());
            return new JSONObject(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel displayIcon(String iconCode) {
        try {
            URL url = new URL("http://openweathermap.org/img/w/" + iconCode + ".png");
            System.out.println("Image URL: " + url);  // Print the URL
            ImageIcon icon = new ImageIcon(url);
            int status = icon.getImageLoadStatus();
            if (status == MediaTracker.ERRORED) {
                throw new IOException("Error loading image");
            } else if (status == MediaTracker.ABORTED) {
                throw new IOException("Image loading aborted");
            }
            JLabel label = new JLabel(icon);
            this.revalidate();
            this.repaint();
            return label;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
