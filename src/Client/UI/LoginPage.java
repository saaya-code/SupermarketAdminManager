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
        title.setFont(new Font("Arial", Font.BOLD, 20));

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");



    }

    private void createLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 20, 0); // Add vertical spacing

        panel.add(title, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 10, 10); // Reset insets

        panel.add(usernameLabel, c);
        c.gridx = 1;
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(passwordLabel, c);
        c.gridx = 1;
        panel.add(passwordField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0); // Add more space below buttons

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Center-align buttons with spacing
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        panel.add(buttonPanel, c);

        getContentPane().add(panel);
    }
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        panel.add(component, c);
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

    protected void finilize() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
