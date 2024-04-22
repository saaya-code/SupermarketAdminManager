package Client.Helpers;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Serilizer {
    public static Object getObjectFromServer(Socket socket, String  query) throws IOException, ClassNotFoundException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println("SELECTION");
        pr.println("SELECT idFour FROM FOURNISSEUR");
        pr.flush();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int length = dataInputStream.readInt();

        // Receive the byte array
        byte[] byteArray = new byte[length];
        inputStream.read(byteArray);

        // Deserialize the byte array into an ArrayList
        ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArray);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        ArrayList<Integer> receivedList = (ArrayList<Integer>) objectStream.readObject();
        return receivedList;
    }
}
