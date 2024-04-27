package Client.UI.InternalFrames;
import Classes.User;
import Client.Message;
import Client.ChatRemote;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.*;
import java.util.ArrayList;

public class Chat extends javax.swing.JInternalFrame{
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    Client.ChatRemote chat;
    String pseudo;
    User user;
    public Chat(User user) {
        setTitle("Chat App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.user = user;
        // Create components
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        pseudo = user.getUsername();
        messageField = new JTextField(30);
        sendButton = new JButton("Send");
        String url = "rmi://127.0.0.1:9001/chat";
        try {
            chat = (Client.ChatRemote) Naming.lookup(url);
            ArrayList<Message> messages = chat.getAllMessages();
            for (Message message : messages) {
                chatArea.append(message.toString());
            }
        } catch (Exception e) {
            System.out.println("Client is not connected: " + e);
        }
        new Thread(() -> {
            //invoke later
            while (true) {
                try {
                    ArrayList<Message> messages = chat.getAllMessages();
                    chatArea.setText("");
                    for (Message message : messages) {
                        chatArea.append(message.toString());
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Client is not connected: " + e);
                }
            }
        }).start();


        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String psd = pseudo;
                chat.sendMessage(new Message(psd, messageField.getText()));
            }
        });


    }
}
