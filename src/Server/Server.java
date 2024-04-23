package Server;
import Classes.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        System.out.println("Lancement Server...");

        Connection con = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
        //System.out.println(MyConnection.checkLoginCredentials(con,"aa","aa"));
        ArrayList<User> Users = new ArrayList<User>();
        try {
            ServerSocket server = new ServerSocket(9000);
            System.out.println("Server en écoute sur PORT 9000...");
            UsersTableModel model = new UsersTableModel(Users);
            new ServerIHM(Users, model);

            while(true) {
                Socket s = server.accept();
                System.out.println("Client connecté : " + s.getInetAddress());
                // Start a new thread to handle the client
                new ClientHandler(s, Users, con, model).start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

