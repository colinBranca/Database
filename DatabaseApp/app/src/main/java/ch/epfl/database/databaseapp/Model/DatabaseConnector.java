package ch.epfl.database.databaseapp.Model;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    Connection connection;

    public DatabaseConnector(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:oracle:thin:@//diassrv2.epfl.ch:1521/orcldias.epfl.ch";
        String username = "DB2017_G15";
        String password = "julcolyo";
        try {
            connection = DriverManager.getConnection(url, username, password);
            Log.d("STATUS", "CONNECTION REUSSIE!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertElement(String query){
        //TODO : Check validity of query

        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteElement(String query){
        //TODO : Check validity of query

        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery("query");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
