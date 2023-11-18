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

public class Beaver extends Animal{
    public Beaver(){};

    /*This method calculates the feeding time for the beavers in the database and it throws IllegalArgumentException if the 
    input is invalid and an InsufficientFeedingTimeException if scheduling feeding for beavers within the hours in impossible*/
    public static void feedingTimeBeaver() throws IllegalArgumentException, InsufficientFeedingTimeException {
        // Getting the map of minutes left for each hour of the day
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        // Getting the approperiate values for the method either from the inherited Animal class or AnimalType enum
        int numBeavers = getNumBeavers();
        List<String> beaverNicknames = getBeaverNicknames();
        int feedingPrepTime = AnimalType.BEAVER.getFeedingPrepTime();
        int feedingTimePerBeaver = AnimalType.BEAVER.getFeedingTime();
    
        // Getting minutes left for each of the start hours 
        // If the key is not present in the map it return zero or else it return the values associated with the key
        int minutesLeft8 = minutesLeftmap.getOrDefault(8, 0);
        int minutesLeft9 = minutesLeftmap.getOrDefault(9, 0);
        int minutesLeft10 = minutesLeftmap.getOrDefault(10, 0);
    
        // Calculating the number of beavers that can be fed in each hour without exceeding 60 mins
        int numBeaversInHour8 = Math.min(numBeavers, (minutesLeft8 - feedingPrepTime) / feedingTimePerBeaver);
        numBeavers -= numBeaversInHour8;
        int numBeaversInHour9 = Math.min(numBeavers, (minutesLeft9 - feedingPrepTime) / feedingTimePerBeaver);
        numBeavers -= numBeaversInHour9;
        int numBeaversInHour10 = Math.min(numBeavers, (minutesLeft10 - feedingPrepTime) / feedingTimePerBeaver);
        
        // If the beavers can be fed in those hours add them to the schedule using the method insertFeedingBeaversToHashmap also add the nicknames of the beavers being fed in each hour
        if (numBeaversInHour8 > 0) {
            int totalFeedingTimeInHour8 = numBeaversInHour8 * feedingTimePerBeaver + feedingPrepTime;
            insertFeedingBeaversToHashmap(8, totalFeedingTimeInHour8, numBeaversInHour8, beaverNicknames.subList(0, numBeaversInHour8));
        }
        if (numBeaversInHour9 > 0) {
            int totalFeedingTimeInHour9 = numBeaversInHour9 * feedingTimePerBeaver + feedingPrepTime;
            insertFeedingBeaversToHashmap(9, totalFeedingTimeInHour9, numBeaversInHour9, beaverNicknames.subList(numBeaversInHour8, numBeaversInHour8 + numBeaversInHour9));
        }
        if (numBeaversInHour10 > 0) {
            int totalFeedingTimeInHour10 = numBeaversInHour10 * feedingTimePerBeaver + feedingPrepTime;
            insertFeedingBeaversToHashmap(10, totalFeedingTimeInHour10, numBeaversInHour10, beaverNicknames.subList(numBeaversInHour8 + numBeaversInHour9, numBeaversInHour8 + numBeaversInHour9 + numBeaversInHour10));
        }
        // if the beavers can't be fed in the designated three hour window gap throw the InsufficientFeedingTimeException prompting the user with a meaningful message
        if (numBeavers > 0 && minutesLeft8 < feedingPrepTime + numBeavers * feedingTimePerBeaver && minutesLeft9 < feedingPrepTime + numBeavers * feedingTimePerBeaver && minutesLeft10 < feedingPrepTime + numBeavers * feedingTimePerBeaver) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the beavers in the designated times.");
        }
    }

    /*This method is inserting the feeding task the beavers into the main hashmap with tasks and start hours. It throws IllegalArgumentException accordingly */ 
    public static void insertFeedingBeaversToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> beaverNicknames) throws IllegalArgumentException {
        if(startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23 inclusive.");
        }
        if(numAnimal < 1) {
            throw new IllegalArgumentException("Number of animals must be greater than 0.");
        }
        if(beaverNicknames == null || beaverNicknames.isEmpty()) {
            throw new NullPointerException("Beaver nicknames cannot be null or empty.");
        }
        // Formatting the statement for feeding beavers that would show on the GUI
        String beaverNames = String.join(", ", beaverNicknames);
        String description = "Feeding - Beaver (" + numAnimal + ": " + beaverNames + ")";
        
        // Creating a new task map for the feeding task we are inserting
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Geting the task list for the specific start hour from the main hashmap and adding the new feeding task to the task list 
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        taskList.add(task);
        
        // Lastly updating the main hashmap with the updated task list
        Schedule.mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    /* This method adds cleaning times for a beaver to the main hashmap and throws a NullPointerException if the animal info is null.*/
    public static void addCleaningBeaverTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
        // Geting the the minutes left map for each start hour
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        
        // Hashmap to help keep track of the animals already inserted
        Set<AnimalType> insertedAnimals = new HashSet<>();
        List<String> BeaverNicknames = Animal.getBeaverNicknames();

        // Looping through each beaver's nickname to add the scheduling
        for (String nickname : BeaverNicknames) {
            // start adding from the first hour
            int startHour = 0;
            // Trying to insert the cleaning time for the current beaver at each hour of the day
            while (startHour < 24) {
                // return the start hour or or the defualt 60 mins
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                // getting the appropriate values for this method from the AnimalType
                int cleaningTimeBeaver = AnimalType.BEAVER.getCleaningTime();
                AnimalType animalTypeBeaver = AnimalType.BEAVER;
                
                // If there is enough time in the current hour then insert the cleaning task and update the minutes left map
                if (minutesLeft >= cleaningTimeBeaver) {
                    Schedule.insertCleaningToHashMap(startHour, cleaningTimeBeaver, animalTypeBeaver, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeBeaver);
                    insertedAnimals.add(animalTypeBeaver);
                    break;
                // If there's not enough space check the next hour
                } else {
                    startHour++;
                }
            }
            // If all the beaverNicknames are inserted break out of the loop
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
    }
}
