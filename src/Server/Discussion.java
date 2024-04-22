package Server;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Discussion extends Thread {
    Socket s;
    ArrayList<Socket> sockets;
    Connection con;
    public Discussion(Socket s, ArrayList<Socket> sockets, Connection con) {
        this.s = s;
        this.sockets = sockets;
        this.con = con;
    }

    public void run() {
        try {
            // listen to user requests
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            OutputStream oos = s.getOutputStream();
            String query = br.readLine();
            while (true) {

                //check if it's an SQL query
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
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    pw.println("Query executed");
                    pw.flush();
                } else {
                    for (Socket s : sockets) {
                        PrintWriter pw = new PrintWriter(s.getOutputStream());
                        pw.println(query);
                        pw.flush();
                    }

                }
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
