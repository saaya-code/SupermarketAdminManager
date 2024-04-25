package Client.UI.InternalFrames;

import Client.CRUD.FournisseurDAO;
import Client.Helpers.FournisseurTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AfficherFournisseurs extends JInternalFrame {
    private JScrollPane scrollPane;
    private JTable table;
    private FournisseurTableModel model;
    private FournisseurDAO fournisseurDao;
    private Socket socket;

    public AfficherFournisseurs(FournisseurDAO fournisseurDAO, Socket socket) {
        super("Afficher Fournisseurs", true, true, true, true);
        this.setSize(800, 600);
        this.fournisseurDao = fournisseurDAO;
        this.socket = socket;
        initializeComponents();
        createLayout();
        setVisible(true);
    }

    private void initializeComponents() {
        table = new JTable();
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);
        model = new FournisseurTableModel(fournisseurDao.getAllFournisseurs(), fournisseurDao);
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
        //refreshDataOnSocketEvent();
    }

    private void centerAlignTableData() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }


}
