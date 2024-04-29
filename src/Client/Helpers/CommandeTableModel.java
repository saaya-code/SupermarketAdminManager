package Client.Helpers;

import Client.CRUD.CommandeDAO;
import Client.CRUD.FournisseurDAO;
import Client.CRUD.ProduitDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class CommandeTableModel extends AbstractTableModel {
    ArrayList<Object[]> data;
    ResultSetMetaData rsmd;
    CommandeDAO commandeDAO;

    public CommandeTableModel(ResultSet rs, CommandeDAO commandeDAO) {
        data = new ArrayList<Object[]>();
        this.commandeDAO = commandeDAO;
        try {
            rsmd = rs.getMetaData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {

            while (rs.next()) {
                Object[] ligne = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    //check if the colmun is status then map the value false -> non confirmer, true -> confirmer
                    if(rsmd.getColumnName(i+1).equals("status")) {
                        if (rs.getInt(i + 1) == 0) {
                            ligne[i] = "non confirmer";
                        } else {
                            ligne[i] = "confirmer";
                        }
                    }else
                    ligne[i] = rs.getObject(i + 1);

                    //System.out.println(ligne[i]);
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
                    //if the colmunname is Status map it to values: 0 -> En attente, 1 -> En cours
                    if(rsmd.getColumnName(i+1).equals("status")) {
                        if (rs.getInt(i + 1) == 0) {
                            ligne[i] = "non";
                        } else {
                            ligne[i] = "oui";
                        }
                    }else
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
    public Class<?> getColumnClass(int columnIndex) {
        try {
            return Class.forName(rsmd.getColumnClassName(columnIndex + 1));
        } catch (Exception e) {
            return Object.class;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data.get(rowIndex)[columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
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