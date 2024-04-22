package Classes;
import java.net.Socket;
public class User {
    private String username;
    private Socket userSocket;
    public User(String username, Socket userSocket) {
        this.username = username;
        this.userSocket = userSocket;
    }
    public String getUsername() {
        return username;
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public void disconnect(){
        try {
            userSocket.close();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }



}
