package Client.UI.InternalFrames;

import Client.CRUD.ProduitDAO;
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

public class AfficherProduits extends JInternalFrame {
    private JScrollPane scrollPane;
    private JTable table;
    private ProduitTableModel model;
    private ProduitDAO produitDao;
    private Socket socket;

    public AfficherProduits(ProduitDAO produitDAO, Socket socket) {
        super("Afficher Produits", true, true, true, true);
        this.setSize(800, 600);
        this.produitDao = produitDAO;
        this.socket = socket;
        initializeComponents();
        createLayout();
        setVisible(true);
    }

    private void initializeComponents() {
        table = new JTable();
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);
        model = new ProduitTableModel(produitDao.getAllProduits(), produitDao);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        centerAlignTableData();

        // Customize table header
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
        refreshDataOnSocketEvent();
    }

    private void centerAlignTableData() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void refreshDataOnSocketEvent() {
        new Thread(() -> {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String line = br.readLine();
                    if (line != null && line.contains("refresh")) {
                        SwingUtilities.invokeLater(() -> {
                            model.updateTableWithNewResultSet(produitDao.getAllProduits());
                            model.fireTableDataChanged();
                            JOptionPane.showMessageDialog(this, "Données actualisées avec succès");
                        });
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
