package Client.UI.InternalFrames;

import Client.CRUD.ProduitDAO;
import Client.Helpers.ProduitTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AfficherProduits extends JInternalFrame {
    JScrollPane jsp;
    JTable table;
    ProduitTableModel model;
    ProduitDAO produitDao;
    Socket socket;
    BufferedReader br;

    public AfficherProduits(ProduitDAO Pr, Socket s){
        this.setSize(500, 500);
        this.setTitle("Afficher Produits");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.produitDao = Pr;
        this.socket = s;

        initializeComponents();
        createLayout();
        //addEventListeners();

    }
    private void initializeComponents(){
        table = new JTable();
        jsp = new JScrollPane(table);
        model = new ProduitTableModel(this.produitDao.getAllProduits(), this.produitDao);
        table.setModel(model);

    }
    public void createLayout(){
        this.add(jsp, BorderLayout.CENTER);
        new Thread(()->{
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String line = br.readLine();
                    System.out.println("awaiting : "+line);
                    if(line.contains("refresh")){
                        model.updateTableWithNewResultSet(produitDao.getAllProduits());
                        model.fireTableDataChanged();
                        JOptionPane.showMessageDialog(this, "Refresh avec success");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

}
