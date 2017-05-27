package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
        String password = "julyocol";
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("STATUS-CONNECTION REUSSIE!");
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
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = result.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

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
            stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query){
        //TODO : Check validity of query

        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e){
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
