/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Neera, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */
package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Coyote extends Animal{
    public Coyote(){};


    /**
     * The method checks for invalid input parameters and then creates a new task map for a feeding task, with a description and duration. 
     * It then retrieves the task list for the specified start hour from the main hashmap and adds the new feeding task to the task list. 
     * Finally, it updates the main hashmap with the modified task list.

There are some error handling statements in the code, and the method throws exceptions if there are any errors. The method appears to be part of a larger program related to scheduling and managing tasks.
     * @param startHour
     * @param feedingDuration
     * @param numAnimal
     * @param coyoteNicknames
     */
    public static void insertFeedingCoyotesToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> coyoteNicknames) {
        //Throws a NullPointerException if the coyoteNicknames parameter is null.
    
        if (coyoteNicknames == null) {
            throw new NullPointerException("coyoteNicknames cannot be null");
            }
           // Throws an IllegalArgumentException if the startHour parameter is negative.
            if (startHour < 0) {
            throw new IllegalArgumentException("startHour cannot be negative");
            }
            //Throws an IllegalArgumentException if the feedingDuration parameter is not greater than zero.

            if (feedingDuration <= 0) {
            throw new IllegalArgumentException("feedingDuration must be greater than zero");
            }
            
            try {
            // string of coyote nicknames
            String coyoteNames = String.join(", ", coyoteNicknames);
            // description string for the feeding task
            String description = "Feeding - Coyote (" + numAnimal + ": " + coyoteNames + ")";
            // new task map for the feeding task
            Map<String, String> task = new HashMap<>();
            task.put("Description", description);
            task.put("Duration", Integer.toString(feedingDuration));

            // Get the task list for specified start hour from the main hashmap
            List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

            // Add new feeding task to task list
            taskList.add(task);

            // Update main hashmap with the modified task list
            Schedule.mainHashmapWithTasksAndStartHours.put(startHour, taskList);
            } catch (Exception e) {
                throw new RuntimeException("Error while inserting feeding coyotes task into hashmap", e);
                }
                }

   
   
   
            /**
             * This is a method called "feedingTimeCoyote" that calculates the feeding schedule for coyotes
             *  based on the available time slots and the number of coyotes to be fed, and throws an exception if 
             * there is not enough time to feed all of the coyotes in the designated times.
             * @throws IllegalArgumentException
             * @throws InsufficientFeedingTimeException
             */
            public static void feedingTimeCoyote() throws IllegalArgumentException, InsufficientFeedingTimeException {
            
            //  hashmap that maps hour to minutes left in that hour based on  schedule
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());

        // Get  number of coyotes in  facility
        int numCoyotes = getNumCoyotes();

        // Get list of coyote nicknames in facility
        List<String> coyoteNicknames = getCoyoteNicknames();

        // Get feeding preparation time and feeding time per coyote for coyotes
        int feedingPrepTime = AnimalType.COYOTE.getFeedingPrepTime();
        int feedingTimePerCoyote = AnimalType.COYOTE.getFeedingTime();

        // Get the minutes left in hours 19, 20, and 21
        int minutesLeft19 = minutesLeftmap.getOrDefault(19, 0);
        int minutesLeft20 = minutesLeftmap.getOrDefault(20, 0);
        int minutesLeft21 = minutesLeftmap.getOrDefault(21, 0);

        // Calculate number of coyotes that can be fed in each hour
        int numCoyotesInHour19 = Math.min(numCoyotes, (minutesLeft19 - feedingPrepTime) / feedingTimePerCoyote);
        //update num coyotes
        numCoyotes -= numCoyotesInHour19;

        // Calculate  number of coyotes that can be fed in the 20th hour and subtract it from  total number of coyotes
        int numCoyotesInHour20 = Math.min(numCoyotes, (minutesLeft20 - feedingPrepTime) / feedingTimePerCoyote);
        //update num coyotes
        numCoyotes -= numCoyotesInHour20;

        // Calculate  number of coyotes that can be fed in  21st hour and subtract it from  total number of coyotes
        int numCoyotesInHour21 = Math.min(numCoyotes, (minutesLeft21 - feedingPrepTime) / feedingTimePerCoyote);
        //update num coyotes
        numCoyotes -= numCoyotesInHour21;

        // If there are coyotes that can be fed in 19th hour, add them to feeding schedule
        if (numCoyotesInHour19 > 0) {
            int totalFeedingTimeInHour19 = numCoyotesInHour19 * feedingTimePerCoyote + feedingPrepTime;
            insertFeedingCoyotesToHashmap(19, totalFeedingTimeInHour19, numCoyotesInHour19, coyoteNicknames.subList(0, numCoyotesInHour19));
        }

        // If there are coyotes that can be fed in  20th hour, add them to feeding schedule
        if (numCoyotesInHour20 > 0) {
            int totalFeedingTimeInHour20 = numCoyotesInHour20 * feedingTimePerCoyote + feedingPrepTime;
            insertFeedingCoyotesToHashmap(20, totalFeedingTimeInHour20, numCoyotesInHour20, coyoteNicknames.subList(numCoyotesInHour19, numCoyotesInHour19 + numCoyotesInHour20));
        }

        // If there are coyotes that can be fed in 21st hour, add them to feeding schedule
        if (numCoyotesInHour21 > 0) {
            int totalFeedingTimeInHour21 = numCoyotesInHour21 * feedingTimePerCoyote + feedingPrepTime;
            insertFeedingCoyotesToHashmap(21, totalFeedingTimeInHour21, numCoyotesInHour21, coyoteNicknames.subList(numCoyotesInHour19 + numCoyotesInHour20, numCoyotesInHour19 + numCoyotesInHour20 + numCoyotesInHour21));
        }

        // If there are coyotes left to feed and there is not enough time to feed them in any of th hours, throw an exception
        if (numCoyotes > 0 && minutesLeft19 < feedingPrepTime + numCoyotes * feedingTimePerCoyote && minutesLeft20 < feedingPrepTime + numCoyotes * feedingTimePerCoyote && minutesLeft21 < feedingPrepTime + numCoyotes * feedingTimePerCoyote) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the coyotes in the designated times.");
        }
            }

        /**
         * This method that adds cleaning times for coyotes to a schedule hashmap. It uses a set to keep track of inserted animals and 
         * iterates through each coyote nickname to find a suitable time slot. It then checks if there 
         * is enough time left in the current hour for the cleaning and adds it to the schedule hashmap if there is. 
         * If not, it moves to the next hour. Once all animals have been inserted, the loop exits.
         * @param db
         * @param animalInfo
         * @throws IllegalArgumentException
         * @throws NullPointerException
         */
        public static void addCleaningCoyoteTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
            //  minutes left for each hour
            HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
            // Create set to keep track of  animal types that have been inserted
            Set<AnimalType> insertedAnimals = new HashSet<>();
            // list of coyote nicknames
            List<String> coyoteNicknames = Animal.getCoyoteNicknames();
            
            // Iterate through each coyote nickname and try to find  suitable time slot
            for (String nickname : coyoteNicknames) {
            // Start at hour 0 and continue iterating until a suitable time slot is found or all hours have been checked
            int startHour = 0;
            while (startHour < 24) {
            // Get the minutes left for the current hour
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                // Get cleaning time for a coyote
                int cleaningTimeCoyote = AnimalType.COYOTE.getCleaningTime();
                // Get animal type for  coyote
                AnimalType animalTypeCoyote = AnimalType.COYOTE;
                // If theres enough time left in the hour for the cleaning, add it to  schedule hashmap
                if (minutesLeft >= cleaningTimeCoyote) {
                Schedule.insertCleaningToHashMap(startHour, cleaningTimeCoyote, animalTypeCoyote, nickname);
                // Update minutes left for current hour
                minutesLeftmap.put(startHour, minutesLeft - cleaningTimeCoyote);
                // Add  animal type to  set of inserted animals
                insertedAnimals.add(animalTypeCoyote);
                // Exit  loop b/c  suitable time slot found
                break;
            } else {
                // Move to  next hour if   not enough time left in the current hour
                startHour++;
                }
                }
            // If all animals have been inserted, exit the loop
            if (insertedAnimals.size() == AnimalType.values().length) {
            break;
            }
            }
            }
}
