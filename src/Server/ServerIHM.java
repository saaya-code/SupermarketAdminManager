package Server;

import Classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON3){
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Deconncter");
                    item.addActionListener((event)->{
                        int row = jt.getSelectedRow();
                        User user = users.get(row);
                        users.remove(user);
                        Socket s = user.getUserSocket();
                        try {
                            PrintWriter pr = new PrintWriter(s.getOutputStream());
                            pr.println("disconnect");
                            pr.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        user.disconnect();
                        model.fireTableDataChanged();
                    });
                    popupMenu.add(item);
                    popupMenu.show(jt, e.getX(), e.getY());
                }
            }
        });

    }

}
