package Server;

import Classes.User;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class UsersTableModel extends AbstractTableModel {
    ArrayList<User> data;

    public UsersTableModel(ArrayList<User> users) {
        data = users;

    }




    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
            return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return data.get(rowIndex).getUsername();
            }
            case 1 -> {
                return data.get(rowIndex).getUserSocket().getInetAddress().getHostAddress();
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Username";
            case 1 -> "Socket";
            default -> "";
        };
    }

    int colmunNameToIndex(String colmunName){
        for (int i = 0; i < getColumnCount(); i++) {
            if(getColumnName(i).equalsIgnoreCase(colmunName)){
                return i;
            }
        }
        return -1;
    }
    Boolean stringToBool(String str){
        return str.equalsIgnoreCase("true");
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }







}
