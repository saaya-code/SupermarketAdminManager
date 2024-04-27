package Server;

import Classes.User;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class ClientHandler extends Thread {
    private Socket socket;
    private ArrayList<User> users;
    private Connection con;
    ObjectOutputStream oos;
    UsersTableModel model;
    public ClientHandler(Socket socket, ArrayList<User> users, Connection con, UsersTableModel model) throws IOException {
        this.socket = socket;
        this.users = users;
        this.con = con;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.model = model;
    }
    private void HandleLogin(BufferedReader br, PrintWriter pr) throws IOException{

        String username = br.readLine();
        String password = br.readLine();
        if (MyConnection.checkLoginCredentials(con, username, password)) {
            System.out.println("Login successful");
            pr.flush();
            pr.println("Login successful");
            pr.flush();
            users.add(new User(username, socket));
            model.fireTableDataChanged();
            for (User user : users) {
                System.out.println(user.getUsername());
            }
            //new Discussion(socket, sockets, con).start();
            //destroy this instance of clientHandler
        } else {
            System.out.println("Login failed");
            pr.println("Login failed");
            pr.flush();
        }
    }

    private void UpdateRequest(BufferedReader br, PrintWriter pr) throws IOException, SQLException {
        System.out.println("Updating request");
        String query = br.readLine();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        //PrintWriter out = new PrintWriter(socket.getOutputStream());
        for(User u: users){
            PrintWriter p = new PrintWriter(u.getUserSocket().getOutputStream());
            System.out.println(u.getUsername());
            p.println("refresh");
            p.flush();
        }
    }
    private void SelectionRequest(BufferedReader br, PrintWriter pr) throws IOException, SQLException {
        String query = br.readLine();
        Statement st = con.createStatement();
        //System.out.println("Query: " + query);
        if (query.startsWith("SELECT")) {
            ResultSet rs = st.executeQuery(query);
            //create a list of objects to store
            ArrayList<Object> objects = new ArrayList<Object>();
            while(rs.next()){
                //add the object to the list
                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                    objects.add(rs.getObject(i));
                }
            }
            //send the list of objects to the client
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(objects);
            objectStream.flush();

            // Get the byte array and send its length first
            byte[] byteArray = byteStream.toByteArray();
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(byteArray.length);
            outputStream.write(byteArray);
            outputStream.flush();

            // Close streams and socket
            objectStream.close();
            byteStream.close();
            //dataOutputStream.close();
            //outputStream.close();
        } else {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
    }
    private void handleQuery(String header, BufferedReader br, PrintWriter pr) throws IOException{
        try{
            System.out.println("Header: " + header);
            switch(header){
                case "LOGIN":
                    HandleLogin(br, pr);
                    break;
                case "SELECTION":
                    SelectionRequest(br, pr);
                    break;
                case "UPDATING":
                    UpdateRequest(br, pr);
                    for (User user : users) {
                        PrintWriter p = new PrintWriter(user.getUserSocket().getOutputStream());
                        p.println("refresh");
                        p.flush();
                    }
                    break;
                default:
                    System.out.println("Unknown request");
            }
        }catch (IOException | SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void run() {
        try {
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pr = new PrintWriter(socket.getOutputStream());
                //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                String query = br.readLine();
                System.out.println("GOT NEW Query: " + query);
                handleQuery(query, br, pr);

            }
        } catch (IOException e) {

            System.out.println("Error handling client: " + e.getMessage());
            //throw  new RuntimeException(e); comment this to stop server from crashing when a user is not gracefully disconnected
        } finally {
            // Remove the socket from the list when the client disconnects
            for(User user: users){
                if(user.getUserSocket() == socket){
                    user.disconnect();
                    users.remove(user);
                    System.out.println("Client disconnected");
                    model.fireTableDataChanged();
                    break;
                }
            }

        }
    }
}
