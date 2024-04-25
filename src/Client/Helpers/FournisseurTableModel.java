package Client.Helpers;

import Client.CRUD.FournisseurDAO;
import Client.CRUD.ProduitDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class FournisseurTableModel extends AbstractTableModel {
    ArrayList<Object[]> data;
    ResultSetMetaData rsmd;
    FournisseurDAO fournisseurDAO;

    public FournisseurTableModel(ResultSet rs, FournisseurDAO fournisseurDAO) {
        data = new ArrayList<Object[]>();
        this.fournisseurDAO = fournisseurDAO;
        try {
            rsmd = rs.getMetaData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {

            while (rs.next()) {
                Object[] ligne = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    ligne[i] = rs.getObject(i + 1);
                }
                data.add(ligne);
            }
        } catch (SQLException SQLErr) {
            System.out.println("Error " + SQLErr);
        }


    }


    public void updateTableWithNewResultSet(ResultSet rs) {
        data = new ArrayList<Object[]>();
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            while (rs.next()) {
                Object[] ligne = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    ligne[i] = rs.getObject(i + 1);
                }
                data.add(ligne);
            }
        } catch (SQLException SQLErr) {
            System.out.println("Error " + SQLErr);
        }
        fireTableDataChanged();
    }


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        try {
            return rsmd.getColumnName(column + 1);
        } catch (SQLException e) {
            return null;
        }
    }
}