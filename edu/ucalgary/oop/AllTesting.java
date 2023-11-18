package edu.ucalgary.oop;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

import org.junit.Test;

public class AllTesting {
    //Animal Class tests
    @Test
    public void testGetNumCoyotes() {
        Animal animal = new Animal();
        int expectedNumCoyotes = 8;
        int actualNumCoyotes = animal.getNumCoyotes();
        assertEquals(expectedNumCoyotes, actualNumCoyotes);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetNumBeavers() {
        Animal animal = new Animal();
        int expectedNumBeavers = 0;
        int actualNumBeavers = animal.getNumBeavers();
        assertEquals(expectedNumBeavers, actualNumBeavers);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetNumFoxes() {
        Animal animal = new Animal();
        int expectedNumFoxes = 2;
        int actualNumFoxes = animal.getFoxNicknames().size();
        assertEquals(expectedNumFoxes, actualNumFoxes);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetNumRaccoons() {
        Animal animal = new Animal();
        int expectedNumRaccoons = 0;
        int actualNumRaccoons = animal.getRaccoonNicknames().size();
        assertEquals(expectedNumRaccoons, actualNumRaccoons);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetNumPorcupines() {
        Animal animal = new Animal();
        int expectedNumPorcupines = 5;
        int actualNumPorcupines = animal.getPorcupineNicknames().size();
        assertEquals(expectedNumPorcupines, actualNumPorcupines);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetCoyoteNicknames() {
        Animal animal = new Animal();
        List<String> expectedCoyoteNicknames = new ArrayList<String>();
        expectedCoyoteNicknames.add("Loner");
        expectedCoyoteNicknames.add("Biter");
        expectedCoyoteNicknames.add("Bitter");
        expectedCoyoteNicknames.add("Pencil");
        expectedCoyoteNicknames.add("Eraser");
        expectedCoyoteNicknames.add("Boots");
        expectedCoyoteNicknames.add("Spin");
        expectedCoyoteNicknames.add("Spot");
        List<String> actualCoyoteNicknames = animal.getCoyoteNicknames();
        assertEquals(expectedCoyoteNicknames, actualCoyoteNicknames);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetFoxNicknames() {
        Animal animal = new Animal();
        List<String> expectedFoxNicknames = new ArrayList<String>();
        expectedFoxNicknames.add("Annie, Oliver and Mowgli");
        expectedFoxNicknames.add("Slinky");
        List<String> actualFoxNicknames = animal.getFoxNicknames();
        assertEquals(expectedFoxNicknames, actualFoxNicknames);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetPorcupineNicknames() {
        Animal animal = new Animal();
        List<String> expectedPorcupineNicknames = new ArrayList<String>();
        expectedPorcupineNicknames.add("Spike");
        expectedPorcupineNicknames.add("Javelin");
        expectedPorcupineNicknames.add("Gatekeeper");
        expectedPorcupineNicknames.add("Sunshine");
        expectedPorcupineNicknames.add("Shadow");
        List<String> actualPorcupineNicknames = animal.getPorcupineNicknames();
        assertEquals(expectedPorcupineNicknames, actualPorcupineNicknames);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetRaccoonNicknames() {
        Animal animal = new Animal();
        List<String> expectedRaccoonNicknames = new ArrayList<String>();
        List<String> actualRaccoonNicknames = animal.getRaccoonNicknames();
        assertEquals(expectedRaccoonNicknames, actualRaccoonNicknames);
        animal.resetAnimalNumbers();
        animal.close();
    }

    @Test
    public void testGetBeaverNicknames() {
        Animal animal = new Animal();
        List<String> expectedBeaverNicknames = new ArrayList<String>();
        List<String> actualBeaverNicknames = animal.getBeaverNicknames();
        assertEquals(expectedBeaverNicknames, actualBeaverNicknames);
        animal.resetAnimalNumbers();
        animal.close();
    }


    //AnimalType Class tests
    @Test
    public void testAnimalTypeConstructor() {
        AnimalType coyote = AnimalType.COYOTE;
        assertEquals(10, coyote.getFeedingPrepTime());
        assertEquals(5, coyote.getFeedingTime());
        assertEquals(5, coyote.getCleaningTime());
        assertEquals(19, coyote.getFeedingStartHour());
        assertEquals(22, coyote.getFeedingEndHour());
        
        AnimalType fox = AnimalType.FOX;
        assertEquals(5, fox.getFeedingPrepTime());
        assertEquals(5, fox.getFeedingTime());
        assertEquals(5, fox.getCleaningTime());
        assertEquals(0, fox.getFeedingStartHour());
        assertEquals(3, fox.getFeedingEndHour());
        
        AnimalType porcupine = AnimalType.PORCUPINE;
        assertEquals(0, porcupine.getFeedingPrepTime());
        assertEquals(5, porcupine.getFeedingTime());
        assertEquals(10, porcupine.getCleaningTime());
        assertEquals(19, porcupine.getFeedingStartHour());
        assertEquals(22, porcupine.getFeedingEndHour());
        
        AnimalType raccoon = AnimalType.RACCOON;
        assertEquals(0, raccoon.getFeedingPrepTime());
        assertEquals(5, raccoon.getFeedingTime());
        assertEquals(5, raccoon.getCleaningTime());
        assertEquals(0, raccoon.getFeedingStartHour());
        assertEquals(3, raccoon.getFeedingEndHour());
        
        AnimalType beaver = AnimalType.BEAVER;
        assertEquals(0, beaver.getFeedingPrepTime());
        assertEquals(5, beaver.getFeedingTime());
        assertEquals(5, beaver.getCleaningTime());
        assertEquals(8, beaver.getFeedingStartHour());
        assertEquals(11, beaver.getFeedingEndHour());
    }

    @Test
    public void testCoyoteGetters() {
        AnimalType coyote = AnimalType.COYOTE;
        assertEquals(10, coyote.getFeedingPrepTime());
        assertEquals(5, coyote.getFeedingTime());
        assertEquals(5, coyote.getCleaningTime());
        assertEquals(19, coyote.getFeedingStartHour());
        assertEquals(22, coyote.getFeedingEndHour());
    }
    
    @Test
    public void testFoxGetters() {
        AnimalType fox = AnimalType.FOX;
        assertEquals(5, fox.getFeedingPrepTime());
        assertEquals(5, fox.getFeedingTime());
        assertEquals(5, fox.getCleaningTime());
        assertEquals(0, fox.getFeedingStartHour());
        assertEquals(3, fox.getFeedingEndHour());
    }
    
    @Test
    public void testPorcupineGetters() {
        AnimalType porcupine = AnimalType.PORCUPINE;
        assertEquals(0, porcupine.getFeedingPrepTime());
        assertEquals(5, porcupine.getFeedingTime());
        assertEquals(10, porcupine.getCleaningTime());
        assertEquals(19, porcupine.getFeedingStartHour());
        assertEquals(22, porcupine.getFeedingEndHour());
    }
    
    @Test
    public void testRaccoonGetters() {
        AnimalType raccoon = AnimalType.RACCOON;
        assertEquals(0, raccoon.getFeedingPrepTime());
        assertEquals(5, raccoon.getFeedingTime());
        assertEquals(5, raccoon.getCleaningTime());
        assertEquals(0, raccoon.getFeedingStartHour());
        assertEquals(3, raccoon.getFeedingEndHour());
    }
    
    @Test
    public void testBeaverGetters() {
        AnimalType beaver = AnimalType.BEAVER;
        assertEquals(0, beaver.getFeedingPrepTime());
        assertEquals(5, beaver.getFeedingTime());
        assertEquals(5, beaver.getCleaningTime());
        assertEquals(8, beaver.getFeedingStartHour());
        assertEquals(11, beaver.getFeedingEndHour());
    }


    //Schedule Class tests
    @Test
    public void testCreateTaskMap() {
    Schedule schedule = new Schedule();
    // Create a new task
    int startHour = 22;
    String description = "Eyedrops";
    int duration = 25;
    int maxWindow = 1;
    String animalNickname = "Loner";
    // Add the new task to the database
    // ...

    // Call the createTaskMap() method
    schedule.createTaskMap();

    // Get the actual task map
    HashMap<Integer, List<Map<String, String>>> actualTaskMap = Schedule.mainHashmapWithTasksAndStartHours;

    // Check if the actual task map contains the new task
    Map<String, String> expectedTask = new HashMap<>();
    expectedTask.put("Description", description);
    expectedTask.put("AnimalNickname", animalNickname);
    expectedTask.put("Duration", Integer.toString(duration));
    expectedTask.put("MaxWindow", Integer.toString(maxWindow));

    boolean foundExpectedTask = false;

    for (Map.Entry<Integer, List<Map<String, String>>> entry : actualTaskMap.entrySet()) {
        List<Map<String, String>> actualTaskList = entry.getValue();

        for (Map<String, String> actualTask : actualTaskList) {
            boolean isExpectedTask = true;
            for (Map.Entry<String, String> expectedEntry : expectedTask.entrySet()) {
                String key = expectedEntry.getKey();
                String expectedValue = expectedEntry.getValue();
                String actualValue = actualTask.get(key);

                if (!expectedValue.equals(actualValue)) {
                    isExpectedTask = false;
                    break;
                }
            }

            if (isExpectedTask) {
                foundExpectedTask = true;
                break;
            }
        }

        if (foundExpectedTask) {
            break;
        }
    }

    assertTrue(foundExpectedTask);
    }

    @Test
    public void testGetMinutesLeftMap() {
        // Create a sample task map with tasks that take 10, 20, and 30 minutes
        Map<String, String> task1 = new HashMap<>();
        task1.put("Name", "Task 1");
        task1.put("Duration", "10");
        Map<String, String> task2 = new HashMap<>();
        task2.put("Name", "Task 2");
        task2.put("Duration", "20");
        Map<String, String> task3 = new HashMap<>();
        task3.put("Name", "Task 3");
        task3.put("Duration", "30");
        List<Map<String, String>> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        HashMap<Integer, List<Map<String, String>>> taskMap = new HashMap<>();
        taskMap.put(8, taskList);

        // Call the method and check the result
        HashMap<Integer, Integer> result = Schedule.getMinutesLeftMap(taskMap);
        assertEquals(0, (int) result.get(8)); // 60 - 10 - 20 - 30 = 0 minutes left in the hour
        assertEquals(60, (int) result.get(9)); // No tasks in the hour, 60 minutes left
        assertEquals(60, (int) result.get(10)); // No tasks in the hour, 60 minutes left
        assertEquals(60, (int) result.get(11)); // No tasks in the hour, 60 minutes left
    }

    @Test
    public void testCheckDuration() {
        HashMap<Integer, List<Map<String, String>>> taskMap = new HashMap<>();

        // add tasks to the taskMap
        List<Map<String, String>> tasks = new ArrayList<>();
        Map<String, String> task1 = new HashMap<>();
        task1.put("Description", "Any Task 1");
        task1.put("Duration", "30");
        tasks.add(task1);
        Map<String, String> task2 = new HashMap<>();
        task2.put("Description", "Any Task 2");
        task2.put("Duration", "40");
        taskMap.put(9, tasks);

        // test for a start hour with total duration less than 60
        assertFalse(Schedule.checkDuration(taskMap, 9));

        // test for a start hour with total duration greater than 60
        tasks.add(task2);
        assertTrue(Schedule.checkDuration(taskMap, 9));
    }

    //5 Animals Classes tests
    //Coyote
    @Test
    public void testInsertFeedingCoyotesToHashmap() {
        Schedule db = new Schedule();
        db.createConnection();
        int startHour = 8;
        int feedingDuration = 60;
        int numAnimal = 3;
        List<String> coyoteNicknames = Arrays.asList("Alpha", "Beta", "Gamma");

        // Call the method to insert the feeding task into the hashmap
        Coyote.insertFeedingCoyotesToHashmap(startHour, feedingDuration, numAnimal, coyoteNicknames);

        // Retrieve the task list for the start hour from the hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.get(startHour);

        // Check that the task list is not null and has only one task
        assert taskList != null && taskList.size() == 1;

        // Retrieve the task map from the task list
        Map<String, String> task = taskList.get(0);

        // Check that the task map is not null and contains the correct values
        assert task != null;
        assert task.get("Description").equals("Feeding - Coyote (3: Alpha, Beta, Gamma)");
        assert task.get("Duration").equals("60");
    }

    //Beaver
    @Test
    public void testInsertFeedingBeaverToHashmap() {
        Schedule db = new Schedule();
        db.createConnection();
        int startHour = 8;
        int feedingDuration = 60;
        int numAnimal = 3;
        List<String> BeaverNicknames = Arrays.asList("Alpha", "Beta", "Gamma");

        // Call the method to insert the feeding task into the hashmap
        Beaver.insertFeedingBeaversToHashmap(startHour, feedingDuration, numAnimal, BeaverNicknames);

        // Retrieve the task list for the start hour from the hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.get(startHour);

        // Check that the task list is not null and has only one task
        assert taskList != null && taskList.size() == 1;

        // Retrieve the task map from the task list
        Map<String, String> task = taskList.get(0);

        // Check that the task map is not null and contains the correct values
        assert task != null;
        assert task.get("Description").equals("Feeding - Beaver (3: Alpha, Beta, Gamma)");
        assert task.get("Duration").equals("60");
    }

    //Foxes
    @Test
    public void testInsertFeedingFoxesToHashmap() {
        Schedule db = new Schedule();
        db.createConnection();
        int startHour = 8;
        int feedingDuration = 60;
        int numAnimal = 3;
        List<String> foxNicknames = Arrays.asList("fox1", "fox2", "fox3");

        // Call the method to insert the feeding task into the hashmap
        Fox.insertFeedingFoxToHashmap(startHour, feedingDuration, numAnimal, foxNicknames);
        // Retrieve the task list for the start hour from the hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.get(startHour);

        // Check that the task list is not null and has only one task
        assert taskList != null && taskList.size() == 1;

        // Retrieve the task map from the task list
        Map<String, String> task = taskList.get(0);

        // Check that the task map is not null and contains the correct values
        assert task != null;
        assert task.get("Description").equals("Feeding - Fox (3: fox1, fox2, fox3)");
        assert task.get("Duration").equals("60");
    }


    //Raccoons
    @Test
    public void testInsertFeedingRaccoonsToHashmap() {
        Schedule db = new Schedule();
        db.createConnection();
        int startHour = 8;
        int feedingDuration = 60;
        int numAnimal = 3;
        List<String> raccoonNicknames = Arrays.asList("raccoon1", "raccoon2", "raccoon3");

        // Call the method to insert the feeding task into the hashmap
        Raccoon.insertFeedingraccoonsToHashmap(startHour, feedingDuration, numAnimal, raccoonNicknames);
        // Retrieve the task list for the start hour from the hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.get(startHour);

        // Check that the task list is not null and has only one task
        assert taskList != null && taskList.size() == 1;

        // Retrieve the task map from the task list
        Map<String, String> task = taskList.get(0);

        // Check that the task map is not null and contains the correct values
        assert task != null;
        assert task.get("Description").equals("Feeding - Raccoon (3: raccoon1, raccoon2, raccoon3)");
        assert task.get("Duration").equals("30");
    }

    //Porcupines
    @Test
    public void testInsertFeedingPorcupinesToHashmap() {
        Schedule db = new Schedule();
        db.createConnection();
        int startHour = 8;
        int feedingDuration = 60;
        int numAnimal = 3;
        List<String> porcupineNicknames = Arrays.asList("Alpha", "Beta", "Gamma");

        // Call the method to insert the feeding task into the hashmap
        Porcupine.insertFeedingPorcupinesToHashmap(startHour, feedingDuration, numAnimal, porcupineNicknames);

        // Retrieve the task list for the start hour from the hashmap
        List<Map<String, String>> taskList = Schedule.mainHashmapWithTasksAndStartHours.get(startHour);

        // Check that the task list is not null and has only one task
        assert taskList != null && taskList.size() == 1;

        // Retrieve the task map from the task list
        Map<String, String> task = taskList.get(0);

        // Check that the task map is not null and contains the correct values
        assert task != null;
        assert task.get("Description").equals("Feeding - Porcupine (3: Alpha, Beta, Gamma)");
        assert task.get("Duration").equals("60");
    }
}
