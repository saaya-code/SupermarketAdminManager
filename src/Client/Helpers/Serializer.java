package Client.Helpers;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Serializer {
    public static Object getObjectFromServer(Socket socket, String  query) throws IOException, ClassNotFoundException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println("SELECTION");
        pr.println("SELECT idFour FROM FOURNISSEUR");
        pr.flush();
        InputStream inputStream = socket.getInputStream();
        // byte array serillized
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int length = dataInputStream.readInt();
        byte[] byteArray = new byte[length];
        // read byte array
        inputStream.read(byteArray);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArray);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        ArrayList<Object> receivedList = (ArrayList<Object>) objectStream.readObject();
        return receivedList;
    }
}
