package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
    public static Connection getConnection(String url, String username, String password){
        String nomDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(nomDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur connection : "+ e.getMessage());
            return null;
        }
        System.out.println("Driver Chargé");
        // connection a la base de donnée

        Connection con = null;
        Statement st = null;
        try{
            con = DriverManager.getConnection(url,username,password);
            System.out.println("Conncted to db..");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+ e.getMessage());
            return null;
        }
        return con;
    }

    public static boolean checkLoginCredentials(Connection con, String username, String password) {
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            return st.executeQuery(query).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
