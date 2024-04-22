package Server;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lecture extends Thread {
    Socket s;
    Connection con;
    ArrayList<Socket> sockets;
    BufferedReader br;
    OutputStream oos;

    public Lecture(BufferedReader br, OutputStream oos, Connection con, ArrayList<Socket> sockets) {
        //this.s = s;
        this.con = con;
        this.sockets = sockets;
        this.br = br;
        this.oos = oos;

    }

    public void run() {
        try {
            while (true) {
                //BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                //check if it's an SQL query
                String query = br.readLine();
                if (query.startsWith("SELECT") || query.startsWith("INSERT") || query.startsWith("UPDATE") || query.startsWith("DELETE")) {
                    Statement st = con.createStatement();
                    System.out.println("Query: " + query);
                    if (query.startsWith("SELECT")) {
                        ResultSet rs = st.executeQuery(query);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(oos);
                        objectOutputStream.writeObject(rs);
                    } else {
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                    }

                } else {
                    for (Socket s : sockets) {
                        PrintWriter pw = new PrintWriter(s.getOutputStream());
                        pw.println(query);
                        pw.flush();
                    }

                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
