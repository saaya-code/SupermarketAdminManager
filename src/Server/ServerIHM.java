package Server;

import Classes.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerIHM extends Thread {
    ArrayList<User> users;
    JScrollPane jsp;
    JTable jt;
    UsersTableModel model;
    public ServerIHM(ArrayList<User> users, UsersTableModel model) {
        start();
        this.model = model;
        this.users = users;
    }
    public void run() {
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        ImageIcon icon = new ImageIcon("C:\\Users\\boual\\IdeaProjects\\untitled\\src\\assets\\server.png");

        // Set the icon image for the JFrame
        frame.setIconImage(icon.getImage());
        jt = new JTable();
        jt.setModel(model);
        jsp = new JScrollPane(jt);
        frame.add(jsp, BorderLayout.CENTER);
        frame.setVisible(true);
        //createLayout(frame);
        //addEventListeners(frame);

    }
    private void initializeComponents(JFrame frame) {




    }
}
