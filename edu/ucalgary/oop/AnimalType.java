/**
 * @author Mobha Khan, Faria Mobeen, Fabiha Tuheen, and Macayla Konig
 * href="mailto:mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca"><mobha.khan@ucalgary.ca, faria.mobeen@ucalgary.ca, 
 * fabiha.tuheen@ucalgary.ca, macayla.konig@ucalgary.ca
 * @version 1.78
 * @since 1.0
 */
package edu.ucalgary.oop;

public enum AnimalType {
    // The enum values and their parameters 
    COYOTE(10, 5, 5, 19, 22),
    FOX(5, 5, 5, 0, 3),
    PORCUPINE(0, 5, 10, 19, 22),
    RACCOON(0, 5, 5, 0, 3),
    BEAVER(0, 5, 5, 8, 11);

    // The final defined parameters for the enum values 
    private final int FEEDINGPREPTIME;
    private final int FEEDINGTIME;
    private final int CLEANINGTIME;
    private final int FEEDINGSTARTHOUR;
    private final int FEEDINGENDHOUR ;

    // The enum constructor intizalizing instance variables with the associated parameters
    AnimalType(int feedingPrepTime, int feedingTime, int cleaningTime, int feedingStartHour, int feedingEndHour) {
        this.FEEDINGPREPTIME = feedingPrepTime;
        this.FEEDINGTIME = feedingTime;
        this.CLEANINGTIME = cleaningTime;
        this.FEEDINGSTARTHOUR = feedingStartHour;
        this.FEEDINGENDHOUR  = feedingEndHour;
    }

    // Getters for each parameter
    public int getFeedingPrepTime() {
        return FEEDINGPREPTIME;
    }
    
    public int getFeedingTime() {
        return FEEDINGTIME;
    }
    
    public int getCleaningTime() {
        return CLEANINGTIME;
    }
    
    public int getFeedingStartHour() {
        return FEEDINGSTARTHOUR;
    }
    
    public int getFeedingEndHour() {
        return FEEDINGENDHOUR;
    }
}
