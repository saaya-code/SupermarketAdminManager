package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatRemote extends Remote {
    ArrayList<Message> getAllMessages() throws RemoteException;
    void sendMessage(Message message) throws RemoteException;
}
