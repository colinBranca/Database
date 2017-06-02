package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    private Connection connection;

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
            System.out.println("CONNECTION ESTABLISHED!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String searchElement(String table, String elem){
    	String query = "";
    	int elemAsNumber = 0;
    	if(isInteger(elem)){
    		elemAsNumber = Integer.parseInt(elem);
    	}
    	table = table.toUpperCase();
    	switch (table) {
    	case "ARTISTS" :
    		query = "Select * From ARTISTS Where ARTISTS.name LIKE " + "'%"+elem+"%'";
    		break;
    		
    	case "BRANDGROUP" :
    		query = "Select * From BRANDGROUP Where BRANDGROUP.name LIKE " + "'%"+elem+"%'" +
    				" OR BRANDGROUP.url = " + "'%"+elem+"%'";
    		break;
    		
    	case "CHARACTERS" :
    		query = "Select * From CHARACTERS Where CHARACTERS.name LIKE " + "'%"+elem+"%'";
    		break;
    		
    	case "COUNTRY":
    			query = "Select * From COUNTRY Where COUNTRY.name LIKE " + "'%"+elem+"%'" + 
    					" OR COUNTRY.code = " + "'%"+elem+"%'";
    		break; 
    		
    	case "GENRE":
    		query = "Select * From GENRE Where GENRE.name LIKE " + "'%"+elem+"%'";
    		break;
    	
    	case "INDICIAPUBLISHER":
    		query = "Select * From INDICIAPUBLISHER Where INDICIAPUBLISHER.name LIKE " + "'%"+elem+"%'"+ 
    				" OR INDICIAPUBLISHER.url LIKE " + "'%"+elem+"%'";
    		break;
    	
    	case "ISSUE":
    		if(isInteger(elem)){
    			query = "Select * From ISSUE Where ISSUE.ISSUE_NUMBER LIKE " + "%"+elemAsNumber+"%" + 
    					" OR ISSUE.PUBLICATION_DATE LIKE " + "%"+elemAsNumber+"%" + 
    					" OR ISSUE.PRICE LIKE " + "%"+elemAsNumber+"%" + 
    					" OR ISSUE.PAGE_COUNT LIKE " + "%"+elemAsNumber+"%" + 
    					" OR ISSUE.ISBN LIKE " + "%"+elemAsNumber+"%" +
    					" OR ISSUE.BARCODE LIKE " + "%"+elemAsNumber+"%";
    		}
    		query = "Select * From ISSUE Where ISSUE.EDITING LIKE " + "'%"+elem+"%'" +
    				" OR ISSUE.TITLE LIKE " + "'%"+elem+"%'" +
    				" OR ISSUE.ON_SALE_DATE LIKE " + "'%"+elem+"%'" + 
    				" OR ISSUE.RATING LIKE " + "'%"+elem+"%'";
    		break;
    	
    	case "LANGUAGE":
   			query = "Select * From LANGUAGE Where LANGUAGE.name LIKE " + "'%"+elem+"%'" + 
   					"OR LANGUAGE.CODE LIKE "+ "'%"+elem+"%'";
    		
    		break;
    	
    	case "PERSONS":
    		query = "Select * From PERSONS Where PERSONS.name LIKE " + "'%"+elem+"%'";
    		break;
    		
    	case "PUBLISHER":
    		if(isInteger(elem)){
    			query = "Select * From PUBLISHER Where PUBLISHER.YEAR_BEGAN LIKE " + "%"+elemAsNumber+"%" +
    					" OR PUBLISHER.YEAR_ENDED LIKE " + "%"+elemAsNumber+"%";
    		} else {
    			query = "Select * From PUBLISHER Where PUBLISHER.name LIKE " + "'%"+elem+"%'" + 
    					" OR PUBLISHER.URL LIKE " + "'%"+elem+"%'";
    		}
    		break;
    		
    	case "SERIES":
    		if(isInteger(elem)){
    			query = "Select * From SERIES Where SERIES.YEAR_BEGAN = " + "%"+elemAsNumber+"%" + 
        				" OR SERIES.YEAR_ENDED LIKE " + "%"+elemAsNumber+"%";
    		} else {
    			query = "Select * From SERIES Where SERIES.name LIKE " + "'%"+elem+"%'" + 
    					" OR SERIES.format LIKE " + "'%"+elem+"%'" + 
    					" OR SERIES.PUBLICATION_DATES LIKE " + "'%"+elem+"%'" + 
    					" OR SERIES.DIMENSIONS LIKE " + "'%"+elem+"%'";
    		}
    		break;
    	
    	case "STORY":
    		query = "Select * From STORY Where STORY.TITLE LIKE " + "'%"+elem+"%'";
    		break;
    		
    	case "STORY_TYPE":
    		query = "Select * From STORY_TYPE Where STORY_TYPE.NAME LIKE " + "'%"+elem+"%'";
    		break;
    	
    	default : System.out.println("BAD QUERY ERROR");
    		break;
    	}

        return executeQuery(query);
    }

    public String insertElement(String table, String...values){ 
    	checkTableInsertDelete(table);
    	table = table.toUpperCase();
    	
    	String query = "Insert into " + table + " Values (";
    	
    	switch(table){
		case "ARTISTS" :
			query = query + values[0] + ", " + "'"+values[1]+"'";
			break;
			
		case "AUTHORS" :
			query = query + values[0] + ", " + values[1];
			break;
			
		case "BRANDGROUP" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + values[2] +
					", " + values[3] + ", " + values[4] + ", " + "'"+values[5]+"'" + 
					", " + "'"+values[6]+"'";
			break;
			
		case "CHARACTERS" : 
			query = query + values[0] + ", " + "'"+values[1]+"'";
			break;
			
		case "COLORS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "COUNTRY" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + "'"+values[2]+"'";
			break;
			
		case "EDITORS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "FEATURE" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "GENRE" :
			query = query + values[0] + ", " + "'"+values[1]+"'";
			break;
			
		case "HAS_CHARACTERS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "HAS_GENRE" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "HAS_STORY_TYPE" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "HAS_TYPE" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "INDICIAPUBLISHER" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + values[2] + 
					", " + values[3] + ", " + "'"+values[4]+"'" + ", " +"'"+values[5]+"'" +
					", " + values[6] + ", " + values[7] + ", " + values[8];
			break;
			
		case "INKS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "ISSUE" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + values[2] +
					", " + values[3] + ", " + "'"+values[4]+"'" + ", " + "'"+values[5]+"'" + 
					", " + "'"+values[6]+"'" + ", " + "'"+values[7]+"'" + ", " + "'"+values[8]+"'" + ", "
					+ "'"+values[9]+"'" + ", " + "'"+values[10]+"'" + ", " + "'"+values[11]+"'" + ", " 
					+ "'"+values[12]+"'" + ", " + "'"+values[13]+"'" + ", " + "'"+values[14]+"'" + ", " + values[15];
			break;
			
		case "ISSUEREPRINT" :
			query = query + values[0] + ", "+ values[1] + ", " + values[2];
			break;
			
		case "LANGUAGE" :
			query = query + values[0] + ", "+ "'"+values[1]+"'" + ", " + "'"+values[2]+"'";
			break;
			
		case "LETTERS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "MAIN" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "PENCILS" :
			query = query + values[0] + ", "+ values[1];
			break;
			
		case "PERSONS" :
			query = query + values[0] + ", "+ "'"+values[1]+"'";
			break;
			
		case "PUBLISHER" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + values[2] +
					", " + values[3] + ", " + "'"+values[4]+"'" + ", " + "'"+values[5]+"'" + 
					", " + values[6];
			break;
			
		case "SERIES" :
			query = query + values[0] + ", " + 
					"'"+values[1]+"'" + ", " +"'"+values[2]+"'" + ", " +"'"+values[3]+"'" + ", " +
					values[4] + ", "+values[5] + ", "+values[6] + ", "+values[7] + ", "+values[8] + ", "+values[9] + ", "+values[10] + ", "+
					"'"+values[11]+"'" + ", " +"'"+values[12]+"'" + ", " +"'"+values[13]+"'" + ", " +"'"+values[14]+"'" + ", " +"'"+values[15]+"'" + ", " +"'"+values[16]+"'" + ", " +"'"+values[17]+"'";
			break;
			
		case "STORY" :
			query = query + values[0] + ", " + "'"+values[1]+"'" + ", " + values[2] + ", " + 
					"'"+values[3]+"'" + ", " +"'"+values[4]+"'" + ", " +"'"+values[5]+"'" + ", "
					+ values[6];
			break;
			
		case "STORY_TYPE" :
			query = query + values[0] + ", "+ "'"+values[1]+"'";
			break;
			
		case "STORYREPRINT" :
			query = query + values[0] + ", "+ values[1] + ", " + values[2];
			break;
			
		default : 
			System.out.println("WRONG TABLE INPUT");
    	}
    
        query = query.concat(")");
        return executeQuery(query);
    }

    public String deleteElement(String table, String... values){
    	checkTableInsertDelete(table);
    	table = table.toUpperCase();
    	String elem = values[0];
    	String elem1 = "";
    	
    	String query = "";
    	switch(table){
    	case "ARTISTS":
            query = "Delete From ARTISTS Where ARTISTS.ID = "+elem;
    		break;
    		
    	case "AUTHORS":
    		elem1 = values[1];
            query = "Delete From AUTHORS Where AUTHORS.STORY_ID = "+elem+" AND AUTHORS.PERSONS_ID = "+elem1;
    		break;

    	case "BRANDGROUP":
            query = "Delete From BRANDGROUP Where BRANDGROUP.ID = "+elem;
    		break;
    		
    	case "CHARACTERS":
            query = "Delete From CHARACTERS Where CHARACTERS.ID = "+elem;
    		break;
    		
    	case "COLORS":
    		elem1 = values[1];
            query = "Delete From COLORS Where COLORS.STORY_ID = "+elem+" AND COLORS.PERSON_ID = "+elem1;
    		break;
    		
    	case "COUNTRY":
            query = "Delete From COUNTRY Where COUNTRY.ID = "+elem;
    		break;
    		
    	case "EDITORS":
    		elem1 = values[1];
            query = "Delete From EDITORS Where EDITORS.STORY_ID = "+elem+" AND EDITORS.PERSON_ID = "+elem1;
    		break;
    		
    	case "FEATURE":
    		elem1 = values[1];
            query = "Delete From FEATURE Where FEATURE.STORY_ID = "+elem+" AND FEATURES.CHARACTER_ID = "+elem1;
    		break;
    		
    	case "GENRE":
            query = "Delete From GENRE Where GENRE.ID = "+elem;
    		break;
    		
    	case "HAS_CHARACTERS":
    		elem1 = values[1];
            query = "Delete From HAS_CHARACTERS Where HAS_CHARACTERS.STORY_ID = "+elem+" AND HAS_CHARACTERS.CHARACTER_ID = "+elem1;
    		break;
    		
    	case "HAS_GENRE":
    		elem1 = values[1];
            query = "Delete From HAS_GENRE Where HAS_GENRE.STORY_ID = "+elem+" AND HAS_GENRE.GENRE_ID = "+elem1;
    		break;
    		
    	case "HAS_STORY_TYPE":
    		elem1 = values[1];
            query = "Delete From HAS_STORY_TYPE Where HAS_STORY_TYPE.STORY_ID = "+elem+" AND HAS_STORY_TYPE.TYPE_ID = "+elem1;
    		break;
    		
    	case "HAS_TYPE":
    		elem1 = values[1];
            query = "Delete From HAS_TYPE Where HAS_TYPE.STORY_ID = "+elem+" AND HAS_TYPE.TYPE_ID = "+elem1;
    		break;
    		
    	case "INDICIAPUBLISHER":
            query = "Delete From INDICIAPUBLISHER Where INDICIAPUBLISHER.ID = "+elem;
    		break;
    		
    	case "INKS":
    		elem1 = values[1];
            query = "Delete From INKS Where INKS.STORY_ID = "+elem+" AND INKS.PERSON_ID = "+elem1;
    		break;
    		
    	case "ISSUE":
            query = "Delete From ISSUE Where ISSUE.ID = "+elem;
    		break;
    		
    	case "ISSUEREPRINT":
            query = "Delete From ISSUEREPRINT Where ISSUEREPRINT.ID = "+elem;
    		break;
    		
    	case "LANGUAGE":
            query = "Delete From LANGUAGE Where LANGUAGE.ID = "+elem;
    		break;
    		
    	case "LETTERS":
    		elem1 = values[1];
            query = "Delete From LETTERS Where LETTERS.STORY_ID = "+elem+" AND LETTERS.PERSON_ID = "+elem1;
    		break;
    		
    	case "MAIN":
    		elem1 = values[1];
            query = "Delete From MAIN Where MAIN.STORY_ID = "+elem+" AND MAIN.CHARACTER_ID = "+elem1;
    		break;
    		
    	case "PENCILS":
    		elem1 = values[1];
            query = "Delete From PENCILS Where PENCILS.STORY_ID = "+elem+" AND PENCILS.ARTIST_ID = "+elem1;
    		break;
    		
    	case "PERSONS":
            query = "Delete From PERSONS Where PERSONS.ID = "+elem;
    		break;
    		
    	case "PUBLISHER":
            query = "Delete From PUBLISHER Where PUBLISHER.ID = "+elem;
    		break;
    		
    	case "SERIES":
            query = "Delete From SERIES Where SERIES.ID = "+elem;
    		break;
    		
    	case "STORY":
            query = "Delete From STORY Where STORY.ID = "+elem;
    		break;
    		
    	case "STORY_TYPE":
            query = "Delete From STORY_TYPE Where STORY_TYPE.ID = "+elem;
    		break;
    		
    	case "STORYREPRINT":
            query = "Delete From STORYREPRINT Where STORYREPRINT.ID = "+elem;
    		break;
    		
    	default : 
    		System.out.println("BAD QUERY TABLE");
    	}
    	       
        return executeQuery(query);
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String executeQuery(String query){
    	String resultText = ""; 
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
             
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                    	resultText = resultText.concat(", ");
                    	}
                    String columnValue = result.getString(i);
                    resultText = resultText + " " + rsmd.getColumnName(i) + ": " + columnValue;
                }
                resultText = resultText + System.lineSeparator();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultText;
    }
    
    private void checkTableInsertDelete(String table){
    	table = table.toUpperCase();
    	if(!(table.equals("ARTISTS") || table.equals("AUTHORS") || table.equals("BRANDGROUP")
    			|| table.equals("CHARACTERS") || table.equals("COLORS") || table.equals("COUNTRY")
    			|| table.equals("EDITORS") || table.equals("EDITORS") || table.equals("GENRE")
    			|| table.equals("HAS_CHARACTERS") || table.equals("HAS_GENRE")
    			|| table.equals("HAS_STORY_TYPE") || table.equals("HAS_TYPE")
    			|| table.equals("INDICIAPUBLISHER") || table.equals("INKS") || table.equals("ISSUE")
    			|| table.equals("ISSUEREPRINT") || table.equals("LANGUAGE") || table.equals("LETTERS")
    			|| table.equals("MAIN") || table.equals("PENCILS") || table.equals("PERSONS")
    			|| table.equals("PUBLISHER") || table.equals("SERIES") || table.equals("STORY")
    			|| table.equals("STORY_TYPE") || table.equals("STORYREPRINT"))){
    		System.out.print("WRONG TABLE INPUT");
    	}
    }
    
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try{
           Integer.parseInt(s);
           // s is a valid integer
           isValidInteger = true;
        }
        catch (NumberFormatException ex){
           // s is not an integer
        }
        return isValidInteger;
     }
}
