/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Tuheen, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */
package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

public class Schedule {

    static Connection dbConnect;
    
    static HashMap<Integer, List<Map<String, String>>> mainHashmapWithTasksAndStartHours;

    /**
     * This constructor initializes a connection and creates 
     * a task map, which is the main hashmap.
     */
    public Schedule() {

        createConnection();
        createTaskMap();//have mainhashmap
    }


    /*
     * This method closes the database connection. 
     * It calls the close() close the connection. 
     */
    public void close() {
        try {
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    * This method creates connection to sql database. It catches any 
    * SQL Exception that may occur and prints the stack trace if it does.
    */
    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "oop", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   
   /**
    * The createTaskMap() method queries the database and creates a hashmap of the tasks with the start hour as the key. It then adds the hashmap 
    to a list of tasks for the current start hour and adds the list to the main hashmap, with the start hour as the key. Finally, 
    it sets the class variable mainHashmapWithTasksAndStartHours to the new hashmap.
    */
    public void createTaskMap() {
        // Create new hashmap to hold all  tasks, with  start hour as  key
        HashMap<Integer, List<Map<String, String>>> taskMap = new HashMap<>();
    
        try {
            // Create a statement to query the database
            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT t.StartHour, ts.Description, ts.Duration, ts.MaxWindow, a.AnimalNickname " +
                    "FROM treatments t " +
                    "JOIN tasks ts ON t.TaskID = ts.TaskID " +
                    "JOIN animals a ON t.AnimalID = a.AnimalID");
    
            // Iterate over the results of the query
            while (results.next()) {
                // Get the start hour, description, duration, max window, and animal nickname for each task
                int startHour = results.getInt("StartHour");
                String description = results.getString("Description");
                int duration = results.getInt("Duration");
                int maxWindow = results.getInt("MaxWindow");
                String animalNickname = results.getString("AnimalNickname");
    
                // Create  new hashmap for details of  task
                Map<String, String> task = new HashMap<>();
                task.put("Description", description);
                task.put("AnimalNickname", animalNickname);
                task.put("Duration", Integer.toString(duration));
                task.put("MaxWindow", Integer.toString(maxWindow));
    
                // Get the list of tasks for the current start hour or create new one if  doesn't exist
                List<Map<String, String>> taskList = taskMap.getOrDefault(startHour, new ArrayList<>());
                taskList.add(task);
                // Add the list of tasks to the hashmap, with the start hour as the key
                taskMap.put(startHour, taskList);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set the class variable mainHashmapWithTasksAndStartHours to the new taskMap
        Schedule.mainHashmapWithTasksAndStartHours = taskMap;
    }
    
    //getter
    public static HashMap<Integer, List<Map<String, String>>> getMainHashmapWithTasksandStartHours(){
        return mainHashmapWithTasksAndStartHours;
    }


    /**

    Inserts a new cleaning task into the mainHashmapWithTasksAndStartHours hashmap, using the arguments start hour, cleaning time, animal type, and animal nickname.
    Throws an IllegalArgumentException if any of the arguments are invalid (start hour is not between 0 and 23, cleaning time is negative, animal type is null, or nickname is null or empty).
    @param startHour the start hour for the cleaning task
    @param cleaningTime the duration of the cleaning task
    @param animalType the type of animal for which the cage is being cleaned
    @param nickname the nickname of the animal for which the cage is being cleaned
    @throws IllegalArgumentException if any of the arguments are invalid
    */
    public static void insertCleaningToHashMap(int startHour, int cleaningTime, AnimalType animalType, String nickname) throws IllegalArgumentException {
        // Check if start hour is between 0 and 23
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        // Check if cleaning time is a positive integer
        if (cleaningTime < 0) {
            throw new IllegalArgumentException("Cleaning time must be a positive integer");
        }
        // Check if animal type is not null
        if (animalType == null) {
            throw new IllegalArgumentException("Animal type cannot be null");
        }
        // Check if nickname is not null or empty
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }

    // Create  task description and add it to a Map with the duration
    String description = "Cage Cleaning for " + animalType.name().toLowerCase() + " " + nickname;
    Map<String, String> task = new HashMap<>();
    task.put("Description", description);
    task.put("Duration", Integer.toString(cleaningTime));

    // Get task list associated with the start hour, or create new one if doesn't exist yet
    List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
    taskList.add(task);

    // Update the mainHashmapWithTasksAndStartHours with new task list
    mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
/**

    This method checks total duration of all tasks in the given taskMap at the specified start hour.
    If the total duration exceeds 60 minutes, it returns true, indicating that the schedule is overbooked for that hour.
    @param taskMap The hashmap of tasks to check.
    @param startHour The start hour to check for overbooking.
    @return true if  schedule is overbooked, false otherwise.
    @throws IllegalArgumentException if  duration is missing or invalid for a task in the taskMap.
    */
    public static boolean checkDuration(HashMap<Integer, List<Map<String, String>>> taskMap, int startHour) throws IllegalArgumentException {
        int totalDuration = 0;
        
        // Get list of tasks at  specified start hour or create  empty list if none exist.
        List<Map<String, String>> taskList = taskMap.getOrDefault(startHour, new ArrayList<>());
        
        // Iterate through each task in  list and add up its duration.
        for (Map<String, String> task : taskList) {
            String durationStr = task.get("Duration");
            if (durationStr == null) {
                throw new IllegalArgumentException("Duration missing for task at start hour " + startHour);
            }
            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid duration for task at start hour " + startHour + ": " + durationStr, e);
            }
            totalDuration += duration;
        }
        
        // If total duration exceeds 60 minutes, return true indicating overbooking, otherwise return false.
        if (totalDuration > 60) {
            return true;
        } else {
            return false;
        }
    }

    /**
    Calculates remaining minutes available for cleaning tasks for each hour
    based on  argument cleaning tasks HashMap  and their durations
    @param taskMap  HashMap of cleaning tasks and their durations, with start hour as key
    @return  HashMap with  remaining minutes available for cleaning tasks for each hour of  day
    @throws NullPointerException if  input taskMap is null
    @throws NumberFormatException if duration of a cleaning task is not  valid integer
    */
    public static HashMap<Integer, Integer> getMinutesLeftMap(HashMap<Integer, List<Map<String, String>>> taskMap)
    throws NullPointerException, NumberFormatException {
    HashMap<Integer, Integer> minutesLeftMap = new HashMap<>();
    for (int i = 0; i < 24; i++) {
    if (taskMap.containsKey(i)) {
        int totalDuration = 0;
        List<Map<String, String>> taskList = taskMap.get(i);
    for (Map<String, String> task : taskList) {
        int duration = Integer.parseInt(task.get("Duration"));
        totalDuration += duration;
    }
    if (totalDuration < 60) {
        int minutesLeft = 60 - totalDuration;
        minutesLeftMap.put(i, minutesLeft);
    } else {
        minutesLeftMap.put(i, 0);
    }
    } else {
        // If no tasks are scheduled for an hour, all 60 minutes are available
        minutesLeftMap.put(i, 60);
    }
    }
        return minutesLeftMap;
    }
}