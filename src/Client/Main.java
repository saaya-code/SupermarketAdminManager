package Client;

import Client.UI.LoginPage;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        // connect to socket server
        try {

            LoginPage login = new LoginPage();





        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}