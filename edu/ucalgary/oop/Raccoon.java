/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Tuheen, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */

package edu.ucalgary.oop;

import java.util.*;

 
public class Raccoon extends Animal{
    public Raccoon(){};
    // This method inserts the feeding task for raccoons into the main hashmap
    public static void insertFeedingraccoonsToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> raccoonNicknames) {
        // Check if the input is valid
        if (raccoonNicknames == null) {
            throw new NullPointerException("raccoonNicknames cannot be null");
        }
        // Check if the input is valid
        if (startHour < 0) {
            throw new IllegalArgumentException("startHour cannot be negative");
        }
        // Check if the input is valid
        if (feedingDuration <= 0) {
            throw new IllegalArgumentException("feedingDuration must be greater than zero");
        }
        try {
            // Create a string of all the raccoon nicknames
            String raccoonNames = String.join(", ", raccoonNicknames);
            String description = "Feeding - Raccoon (" + numAnimal + ": " + raccoonNames + ")";
        
            // Create a new task map for the feeding task
            Map<String, String> task = new HashMap<>();
            task.put("Description", description);
            task.put("Duration", Integer.toString(feedingDuration));
        
            // Get the task list for the specified start hour from the main hashmap
            List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
            // Add the new feeding task to the task list
            taskList.add(task);
        
            // Update the main hashmap with the modified task list
            Schedule.mainHashmapWithTasksAndStartHours.put(startHour, taskList);
        } catch (Exception e) {
            throw new RuntimeException("Error while inserting feeding raccoons task into hashmap", e);
        }
    }
    
     /*This method calculates the feeding time for the raccoons in the database and it throws IllegalArgumentException if the 
    input is invalid and an InsufficientFeedingTimeException if scheduling feeding for raccoons within the hours in impossible*/
    public static void feedingTimeRaccoon() throws IllegalArgumentException, InsufficientFeedingTimeException {
        // Getting the map of minutes left for each hour of the day
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        // Getting the approperiate values for the method either from the inherited Animal class or AnimalType enum
        int numraccoons = getNumRaccoons();
        List<String> raccoonNicknames = getRaccoonNicknames();
        int feedingPrepTime = AnimalType.RACCOON.getFeedingPrepTime();
        int feedingTimePerraccoon = AnimalType.RACCOON.getFeedingTime();
        // Getting minutes left for each of the start hours 
        // If the key is not present in the map it return zero or else it return the values associated with the key
        int minutesLeft19 = minutesLeftmap.getOrDefault(19, 0);
        int minutesLeft20 = minutesLeftmap.getOrDefault(20, 0);
        int minutesLeft21 = minutesLeftmap.getOrDefault(21, 0);
         // Calculating the number of raccoons that can be fed in each hour without exceeding 60 mins
        int numraccoonsInHour19 = Math.min(numraccoons, (minutesLeft19 - feedingPrepTime) / feedingTimePerraccoon);
        numraccoons -= numraccoonsInHour19;
        // If the raccoons can be fed in those hours add them to the schedule using the method insertFeedingRaccoonsToHashmap also add the nicknames of the raccoons being fed in each hour
        int numraccoonsInHour20 = Math.min(numraccoons, (minutesLeft20 - feedingPrepTime) / feedingTimePerraccoon);
        numraccoons -= numraccoonsInHour20;
        // If the raccoons can be fed in those hours add them to the schedule using the method insertFeedingRaccoonsToHashmap also add the nicknames of the raccoons being fed in each hour
        int numraccoonsInHour21 = Math.min(numraccoons, (minutesLeft21 - feedingPrepTime) / feedingTimePerraccoon);
        numraccoons -= numraccoonsInHour21;
        // If the raccoons cannot be fed in those hours throw an InsufficientFeedingTimeException
        if (numraccoonsInHour19 > 0) {
            int totalFeedingTimeInHour19 = numraccoonsInHour19 * feedingTimePerraccoon + feedingPrepTime;
            insertFeedingraccoonsToHashmap(19, totalFeedingTimeInHour19, numraccoonsInHour19, raccoonNicknames.subList(0, numraccoonsInHour19));
        }
        // If the raccoons cannot be fed in those hours throw an InsufficientFeedingTimeException
        if (numraccoonsInHour20 > 0) {
            int totalFeedingTimeInHour20 = numraccoonsInHour20 * feedingTimePerraccoon + feedingPrepTime;
            insertFeedingraccoonsToHashmap(20, totalFeedingTimeInHour20, numraccoonsInHour20, raccoonNicknames.subList(numraccoonsInHour19, numraccoonsInHour19 + numraccoonsInHour20));
        }
        if (numraccoonsInHour21 > 0) {
            int totalFeedingTimeInHour21 = numraccoonsInHour21 * feedingTimePerraccoon + feedingPrepTime;
            insertFeedingraccoonsToHashmap(21, totalFeedingTimeInHour21, numraccoonsInHour21, raccoonNicknames.subList(numraccoonsInHour19 + numraccoonsInHour20, numraccoonsInHour19 + numraccoonsInHour20 + numraccoonsInHour21));
        }
        if (numraccoons > 0 && minutesLeft19 < feedingPrepTime + numraccoons * feedingTimePerraccoon && minutesLeft20 < feedingPrepTime + numraccoons * feedingTimePerraccoon && minutesLeft21 < feedingPrepTime + numraccoons * feedingTimePerraccoon) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the raccoons in the designated times.");
        }      
    }
    //* This method inserts the cleaning tasks for the raccoons in the database*/
    public static void addCleaningRaccoonTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        Set<AnimalType> insertedAnimals = new HashSet<>();
        List<String> coyoteNicknames = Animal.getRaccoonNicknames();
        // Loop through the raccoon nicknames and add the cleaning tasks to the hashmap
        for (String nickname : coyoteNicknames) {
            int startHour = 0;
            while (startHour < 24) {
                // Get the minutes left for the current hour
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                int cleaningTimeRaccoon = AnimalType.RACCOON.getCleaningTime();
                AnimalType animalTypeRaccoon = AnimalType.RACCOON;
                if (minutesLeft >= cleaningTimeRaccoon) {
                    // If the minutes left is greater than the cleaning time for the raccoon add the cleaning task to the hashmap
                    Schedule.insertCleaningToHashMap(startHour, cleaningTimeRaccoon, animalTypeRaccoon, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeRaccoon);
                    insertedAnimals.add(animalTypeRaccoon);
                    break;
                } else {
                    startHour++;
                }
            }
            // If all the raccoons have been inserted into the hashmap break out of the loop
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
    }
}

