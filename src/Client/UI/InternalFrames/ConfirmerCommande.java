package Client.UI.InternalFrames;

import Classes.User;
import Client.CRUD.CommandeDAO;
import Client.CRUD.ProduitDAO;
import Client.Helpers.CommandeTableModel;
import Client.Helpers.ProduitTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConfirmerCommande extends JInternalFrame {
    private JScrollPane scrollPane;
    private JTable table;
    private CommandeTableModel model;
    private CommandeDAO produitDao;
    private User user;

    public ConfirmerCommande(CommandeDAO produitDAO, User user) {
        super("Afficher Produits", true, true, true, true);
        this.setSize(800, 600);
        this.produitDao = produitDAO;
        this.user = user;
        initializeComponents();
        createLayout();
        //colorRow();
        setVisible(true);
        addEventListeners();
    }

    private void initializeComponents() {
        table = new JTable();
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);
        model = new CommandeTableModel(produitDao.getAllCommandes(user), produitDao);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        centerAlignTableData();

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(0, 153, 204));



    }

    private void createLayout() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        this.add(panel);
        //refreshDataOnSocketEvent();
    }

    private void centerAlignTableData() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private void addEventListeners(){
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem menuItem = new JMenuItem("Confirmer");
                    JMenuItem menuItem2 = new JMenuItem("Annuler");
                    menuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            //confirmer la commande
                            produitDao.confirmerCommande((int)table.getValueAt(row, 0));

                            model.updateTableWithNewResultSet(produitDao.getAllCommandes(user));
                            table.setModel(model);
                            JOptionPane.showMessageDialog(null, "Commande Confirmer");
                            centerAlignTableData();
                        }
                    });
                    menuItem2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            //confirmer la commande
                            produitDao.supprimerCommande((int)table.getValueAt(row, 0));
                            model.updateTableWithNewResultSet(produitDao.getAllCommandes(user));
                            table.setModel(model);
                            JOptionPane.showMessageDialog(null, "Commande Annuler");
                            centerAlignTableData();
                        }
                    });
                    popup.add(menuItem);
                    popup.add(menuItem2);
                    popup.show(table, evt.getX(), evt.getY());
                }
            }
        });
    }

}
