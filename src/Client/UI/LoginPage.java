package Client.UI;

import Classes.User;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginPage extends JFrame {
    JLabel title;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton loginButton;
    JButton registerButton;
    JPanel pane;
    JPanel buttonsPane;
    Socket socket;
    public LoginPage() {
        this.setTitle("Login page");
        this.setSize(400, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setIconImage(new ImageIcon("C:\\Users\\boual\\IdeaProjects\\untitled\\src\\assets\\login.png").getImage());
        this.setVisible(true);
        initilizeComponents();
        createLayout();
        addEventListeners();


    }
    private void initilizeComponents() {
        title = new JLabel("Login/Register Page");
        usernameLabel = new JLabel("Username");
        usernameField = new JTextField(5);
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(5);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        pane = new JPanel();



    }

    private void createLayout() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Add labels and corresponding components
        c.fill = GridBagConstraints.HORIZONTAL;
        addComponent(panel, usernameLabel, c, 0, 0, 1, 1);
        addComponent(panel, usernameField, c, 1, 0, 2, 1);
        addComponent(panel, passwordLabel, c, 0, 1, 1, 1);
        addComponent(panel, passwordField, c, 1, 1, 2, 1);


        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        c.fill = GridBagConstraints.NONE;
        c.gridy = 5;
        c.gridwidth = 3;
        addComponent(panel, buttonPanel, c, 0, 5, 3, 1);

        getContentPane().add(panel);
        setSize(500,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        panel.add(component, c);
    }
    protected void finilize() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   private void addEventListeners() {
        loginButton.addActionListener(e -> {
            try {
                Socket socket = new Socket("127.0.0.1", 9000);
                System.out.println("Je suis Client, je suis connecté...");
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    PrintWriter pr = new PrintWriter(socket.getOutputStream());
                    pr.println("LOGIN");
                    pr.println(username);
                    pr.println(password);
                    System.out.println("MESSAGE SENT : " + username + " " + password);
                    pr.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String result = br.readLine();
                    System.out.println("MESSAGE RECEIVED : " + result);
                    //remove 4 letters from result the first ones

                    if(result.contains("Login successful")) {
                        this.dispose();
                        User user = new User(username, socket);
                        new Application(user);
                    } else {
                        JOptionPane.showMessageDialog(this, "Login failed");
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Server error");
                throw new RuntimeException(ex);
            }

        });

    }





}
