package edu.ucalgary.oop;

/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Tuheen, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */

import java.util.*;

public class Porcupine extends Animal{

    public Porcupine(){};

    /*This method is inserting the feeding task the porcupines into the main hashmap with tasks and start hours. It throws IllegalArgumentException accordingly */ 
    public static void insertFeedingPorcupinesToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> porcupineNicknames) throws IllegalArgumentException {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23 (inclusive).");
        }
        if (feedingDuration <= 0) {
            throw new IllegalArgumentException("Feeding duration must be greater than 0.");
        }
        if (numAnimal <= 0) {
            throw new IllegalArgumentException("Number of animals must be greater than 0.");
        }
        if (porcupineNicknames.isEmpty()) {
            throw new NullPointerException("List of porcupine nicknames cannot be empty.");
        }
        // Formatting the statement for feeding porcupines that would show on the GUI
        String porcupineNames = String.join(", ", porcupineNicknames);
        String description = "Feeding - Porcupine (" + numAnimal + ": " + porcupineNames + ")";
        
        // Create a new task map for the feeding task being inserted
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
        // Add the new feeding task to the task list
        taskList.add(task);
        
        // Update the main hashmap with the modified task list
        Schedule.mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }

    /*This method calculates the feeding time for the porcupines in the database and it throws IllegalArgumentException if the 
    input is invalid and an InsufficientFeedingTimeException if scheduling feeding for porcupines within the hours in impossible*/
    public static void feedingTimePorcupine() throws IllegalArgumentException, InsufficientFeedingTimeException {
        // Getting the map of minutes left for each hour of the day
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        // Getting the appropriate values for the method either from the inherited Animal class or AnimalType enum
        int numPorcupines = getNumPorcupines();
        List<String> porcupineNicknames = getPorcupineNicknames();
        int feedingPrepTime = AnimalType.PORCUPINE.getFeedingPrepTime();
        int feedingTimePerPorcupine = AnimalType.PORCUPINE.getFeedingTime();
    
        // Getting minutes left for each of the start hours 
        // If the key is not present in the map it return zero or else it return the values associated with the key
        int minutesLeft19 = minutesLeftmap.getOrDefault(19, 0);
        int minutesLeft20 = minutesLeftmap.getOrDefault(20, 0);
        int minutesLeft21 = minutesLeftmap.getOrDefault(21, 0);
    
        // Calculating the number of porcupines that can be fed in each hour without exceeding 60 mins
        int numPorcupineInHour19 = Math.min(numPorcupines, (minutesLeft19 - feedingPrepTime) / feedingTimePerPorcupine);
        numPorcupines -= numPorcupineInHour19;
    
        int numPorcupineInHour20 = Math.min(numPorcupines, (minutesLeft20 - feedingPrepTime) / feedingTimePerPorcupine);
        numPorcupines -= numPorcupineInHour20;
    
        int numPorcupineInHour21 = Math.min(numPorcupines, (minutesLeft21 - feedingPrepTime) / feedingTimePerPorcupine);
    
        // If the porcupines can be fed in those hours add them to the schedule using the method insertFeedingPorcupinesToHashmap also add the nicknames of the porcupines being fed in each hour
        if (numPorcupineInHour19 > 0) {
            int totalFeedingTimeInHour19 = numPorcupineInHour19 * feedingTimePerPorcupine + feedingPrepTime;
            insertFeedingPorcupinesToHashmap(19, totalFeedingTimeInHour19, numPorcupineInHour19, porcupineNicknames.subList(0, numPorcupineInHour19));
        }
        if (numPorcupineInHour20 > 0) {
            int totalFeedingTimeInHour20 = numPorcupineInHour20 * feedingTimePerPorcupine + feedingPrepTime;
            insertFeedingPorcupinesToHashmap(20, totalFeedingTimeInHour20, numPorcupineInHour20, porcupineNicknames.subList(numPorcupineInHour19, numPorcupineInHour19 + numPorcupineInHour20));
        }
        if (numPorcupineInHour21 > 0) {
            int totalFeedingTimeInHour21 = numPorcupineInHour21 * feedingTimePerPorcupine + feedingPrepTime;
            insertFeedingPorcupinesToHashmap(21, totalFeedingTimeInHour21, numPorcupineInHour21, porcupineNicknames.subList(numPorcupineInHour19 + numPorcupineInHour20, numPorcupineInHour19 + numPorcupineInHour20 + numPorcupineInHour21));
        }

        // if the porcupines can't be fed in the designated three hour window gap throw the InsufficientFeedingTimeException prompting the user with a meaningful message
        if (numPorcupines > 0 && minutesLeft19 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine && minutesLeft20 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine && minutesLeft21 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the porcupines in the designated times.");
        }        
    }

    /* This method adds cleaning times for a porcupines to the main hashmap and throws a NullPointerException if the animal info is null.*/
    public static void addCleaningPorcupineTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
        // Geting the the minutes left map for each start hour
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        // Hashmap to help keep track of the animals already inserted
        Set<AnimalType> insertedAnimals = new HashSet<>();
        List<String> porcupineNicknames = Animal.getPorcupineNicknames();
    
        // Looping through each porcupine's nickname to add the scheduling
        for (String nickname : porcupineNicknames) {
            // start adding from the first hour
            int startHour = 0;
            // Trying to insert the cleaning time for the current porcupine at each hour of the day
            while (startHour < 24) {
                // return the start hour or or the default 60 mins
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                // getting the appropriate values for this method from the AnimalType
                int cleaningTimePorcupine = AnimalType.PORCUPINE.getCleaningTime();
                AnimalType animalTypePorcupine = AnimalType.PORCUPINE;

                // If there is enough time in the current hour then insert the cleaning task and update the minutes left map
                if (minutesLeft >= cleaningTimePorcupine) {
                    Schedule.insertCleaningToHashMap(startHour, cleaningTimePorcupine, animalTypePorcupine, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimePorcupine);
                    insertedAnimals.add(animalTypePorcupine);
                    break;
                    // If there's not enough space check the next hour
                } else {
                    startHour++;
                }
            }
            // If all the porcupineNicknames are inserted break out of the loop
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
    }
}