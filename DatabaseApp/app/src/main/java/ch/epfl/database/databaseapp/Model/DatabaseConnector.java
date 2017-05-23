package ch.epfl.database.databaseapp.Model;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    Connection connection;

    public DatabaseConnector(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:oracle:thin:@//diassrv2.epfl.ch:1521/orcldias.epfl.ch";
        String username = "DB2017_G15";
        String password = "julyocol";
        try {
            connection = DriverManager.getConnection(url, username, password);
            Log.d("STATUS", "CONNECTION REUSSIE!");
            searchElement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchElement(){
        try{
            Statement stmt = connection.createStatement();
            String query = "Select * From Country";
            ResultSet result = stmt.executeQuery(query);

            System.out.print(result.getString(1));


        } catch(SQLException e){
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
