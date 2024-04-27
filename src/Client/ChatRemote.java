package Client;

import java.util.ArrayList;

public interface ChatRemote {
    ArrayList<Message> getAllMessages();
    void sendMessage(Message message);
}
