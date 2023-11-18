/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Tuheen, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.Tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.Tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */
package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;
//comments
public class Animal {
    private static Connection dbConnect;
    private static ResultSet resultsAnimals;
    private static int numCoyotes;
    private static int numRaccoons;
    private static int numBeavers;
    private static int numFoxes;
    private static int numPorcupines;
    private static List<String> coyoteNicknames = new ArrayList<String>();
    private static List<String> raccoonNicknames = new ArrayList<String>();
    private static List<String> beaverNicknames = new ArrayList<String>();
    private static List<String> foxNicknames = new ArrayList<String>();
    private static List<String> porcupineNicknames = new ArrayList<String>();


//constructor
public Animal() {
    // Call createConnection method to establish connection with database
    createConnection();
    // Call extractAnimalTable to get data from the animal table 
    extractAnimalTable();
}

/**
 * This method createConnection() creates a connection 
 * to a MySQL database with the specified URL, username and password. It 
 * uses the JDBC driver DriverManager to create the connection. If an exception 
 * occurs while creating the connection, a stack trace is printed to the console using 
 * the printStackTrace() method of the SQLException object.
 */
public void createConnection() {
    try {
        //create a connection to the database
        dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "oop", "password");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * This method extracts data from the "animals" table of a database 
 * and updates lists of animal nicknames and counts for each species of animal. 
 * It first creates a statement object to execute a query to get all rows from the 
 * "animals" table. It then loops through each row of the result set and retrieves the species
 *  and nickname of the current animal. Based on the species, it updates the appropriate list 
 * of nicknames and increments the count of that species. If the species is unknown,
 *  it does nothing. If there is an SQLException, it prints the stack trace.
 */
public void extractAnimalTable() {
    try {
        Statement myStmt = dbConnect.createStatement();
        // query to get all rows from animals table
        resultsAnimals = myStmt.executeQuery("SELECT * FROM animals");

        while (resultsAnimals.next()) {
            // get species and nickname of current animal
            String species = resultsAnimals.getString("AnimalSpecies");
            String nickname = resultsAnimals.getString("AnimalNickname");

            // Update number and nickname lists for each species of animal
            switch (species) {
                case "coyote":
                    coyoteNicknames.add(nickname);
                    numCoyotes++;
                    break;
                case "raccoon":
                    raccoonNicknames.add(nickname);
                    numRaccoons++;
                    break;
                case "beaver":
                    beaverNicknames.add(nickname);
                    numBeavers++;
                    break;
                case "fox":
                    foxNicknames.add(nickname);
                    numFoxes++;
                    break;
                case "porcupine":
                    porcupineNicknames.add(nickname);
                    numPorcupines++;
                    break;
                default:
                    // Do nothing if the species is unknown
                    break;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

//getters
    public static int getNumCoyotes() { return numCoyotes;}
    public static int getNumBeavers() { return numBeavers;}
    public static int getNumFoxes() { return numFoxes;}
    public static int getNumRaccoons() { return numRaccoons; }
    public static int getNumPorcupines() { return numPorcupines;}

    public static List<String> getCoyoteNicknames() { return coyoteNicknames;}   
    public  static List<String> getFoxNicknames() { return foxNicknames; }   
    public static List<String> getPorcupineNicknames() { return porcupineNicknames; }  
    public static List<String> getRaccoonNicknames() { return raccoonNicknames; }      
    public static List<String> getBeaverNicknames() { return beaverNicknames; }

//Closes the ResultSet and database connection.

public static void close() {
    try {
        resultsAnimals.close();
        dbConnect.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public static  void setNumCoyotes(int i) {
    Animal.numCoyotes = i;
}


public List<String> getAnimalList() {
    return null;
}

public void resetAnimalNumbers() {
    numBeavers = 0;
    numCoyotes = 0;
    numFoxes = 0;
    numPorcupines = 0;
    numRaccoons = 0;
    beaverNicknames.clear();
    coyoteNicknames.clear();
    foxNicknames.clear();
    porcupineNicknames.clear();
    raccoonNicknames.clear();
}
   
}

