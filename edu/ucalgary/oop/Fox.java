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

public class Fox extends Animal{

    public Fox(){};

    /*This method is inserting the feeding task the fox into the main hashmap with tasks and start hours. It throws IllegalArgumentException accordingly */ 
    public static void insertFeedingFoxToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> foxNicknames) throws IllegalArgumentException {
        if(startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Invalid start hour: " + startHour);
        }
        if(feedingDuration <= 0) {
            throw new IllegalArgumentException("Feeding duration must be positive: " + feedingDuration);
        }
        if(numAnimal <= 0) {
            throw new IllegalArgumentException("Number of animals must be positive: " + numAnimal);
        }
        if(foxNicknames == null || foxNicknames.isEmpty()) {
            throw new NullPointerException("List of fox nicknames cannot be null or empty");
        }

        // Formatting the statement for feeding foxes that would show on the GUI
        String foxNames = String.join(", ", foxNicknames);
        String description = "Feeding - Fox (" + numAnimal + ": " + foxNames + ")";
        
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

    /*This method calculates the feeding time for the foxes in the database and it throws IllegalArgumentException if the 
    input is invalid and an InsufficientFeedingTimeException if scheduling feeding for foxes within the hours in impossible*/
    public static void feedingTimeFox() throws IllegalArgumentException, InsufficientFeedingTimeException {

        // Getting the map of minutes left for each hour of the day
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        // Getting the approperiate values for the method either from the inherited Animal class or AnimalType enum
        int numFoxes = getNumFoxes();
        List<String> foxNicknames = getFoxNicknames();
        int feedingPrepTime = AnimalType.FOX.getFeedingPrepTime();
        int feedingTimePerFox = AnimalType.FOX.getFeedingTime();    
        // Getting minutes left for each of the start hours 
        // If the key is not present in the map it return zero or else it return the values associated with the key
        int minutesLeft0 = minutesLeftmap.getOrDefault(0, 0);
        int minutesLeft1 = minutesLeftmap.getOrDefault(1, 0);
        int minutesLeft2 = minutesLeftmap.getOrDefault(2, 0);

        // Calculating the number of foxes that can be fed in each hour without exceeding 60 mins
        int numFoxesInHour0 = Math.min(numFoxes, (minutesLeft0 - feedingPrepTime) / feedingTimePerFox);
        numFoxes -= numFoxesInHour0;
    
        int numFoxesInHour1 = Math.min(numFoxes, (minutesLeft1 - feedingPrepTime) / feedingTimePerFox);
        numFoxes -= numFoxesInHour1;
    
        int numFoxesInHour2 = Math.min(numFoxes, (minutesLeft2 - feedingPrepTime) / feedingTimePerFox);
    
        // If the foxes can be fed in those hours add them to the schedule using the method insertFeedingBeaversToHashmap also add the nicknames of the foxes being fed in each hour
        if (numFoxesInHour0 > 0) {
            int totalFeedingTimeInHour0 = numFoxesInHour0 * feedingTimePerFox + feedingPrepTime;
            insertFeedingFoxToHashmap(0, totalFeedingTimeInHour0, numFoxesInHour0, foxNicknames.subList(0, numFoxesInHour0));
        }
        if (numFoxesInHour1 > 0) {
            int totalFeedingTimeInHour1 = numFoxesInHour1 * feedingTimePerFox + feedingPrepTime;
            insertFeedingFoxToHashmap(1, totalFeedingTimeInHour1, numFoxesInHour1, foxNicknames.subList(numFoxesInHour0, numFoxesInHour0 + numFoxesInHour1));
        }
        if (numFoxesInHour2 > 0) {
            int totalFeedingTimeInHour2 = numFoxesInHour2 * feedingTimePerFox + feedingPrepTime;
            insertFeedingFoxToHashmap(2, totalFeedingTimeInHour2, numFoxesInHour2, foxNicknames.subList(numFoxesInHour0 + numFoxesInHour1, numFoxesInHour0 + numFoxesInHour1 + numFoxesInHour2));
        }
        // if the foxes can't be fed in the designated three hour window gap throw the InsufficientFeedingTimeException prompting the user with a meaningful message
        if (numFoxes > 0 && minutesLeft0 < feedingPrepTime + numFoxes * feedingTimePerFox && minutesLeft1 < feedingPrepTime + numFoxes * feedingTimePerFox && minutesLeft2 < feedingPrepTime + numFoxes * feedingTimePerFox) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the foxes in the designated times.");
        }
    }

    /* This method adds cleaning times for a fox to the main hashmap and throws a NullPointerException if the animal info is null.*/
    public static void addCleaningFoxTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
        // Geting the the minutes left map for each start hour
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());

        // Hashmap to help keep track of the animals already inserted
        Set<AnimalType> insertedAnimals = new HashSet<>();
        List<String> foxNicknames = Animal.getFoxNicknames();
    
        // Looping through each fox's nickname to add the scheduling
        for (String nickname : foxNicknames) {
            // start adding from the first hour
            int startHour = 0;
            // Trying to insert the cleaning time for the current fox at each hour of the day
            while (startHour < 24) {
                // return the start hour or or the defualt 60 mins
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                // getting the appropriate values for this method from the AnimalType
                int cleaningTimeFox = AnimalType.FOX.getCleaningTime();
                AnimalType animalTypeFox = AnimalType.FOX;
                // If there is enough time in the current hour then insert the cleaning task and update the minutes left map
                if (minutesLeft >= cleaningTimeFox) {
                    Schedule.insertCleaningToHashMap(startHour, cleaningTimeFox, animalTypeFox, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeFox);
                    insertedAnimals.add(animalTypeFox);
                    break;
                } else {
                    startHour++;
                }
            }
                // If there's not enough space check the next hour
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
    }


}