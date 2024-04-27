package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatRemoteImplementation  extends UnicastRemoteObject implements ChatRemote{
    ArrayList<Message> messages;

    public ChatRemoteImplementation() throws RemoteException {
        messages = new ArrayList<Message>();
    }
    @Override
    public ArrayList<Message> getAllMessages() throws RemoteException {
        return messages;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        messages.add(message);
    }
}
