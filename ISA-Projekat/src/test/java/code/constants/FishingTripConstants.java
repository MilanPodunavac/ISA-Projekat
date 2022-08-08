package code.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FishingTripConstants {
    public static final Integer DB_USER_ID = 1;
    public static final String NEW_NAME = "Fishing trip";
    public static final String NEW_DESCRIPTION = "Description";
    public static final String NEW_RULES = "Rules";
    public static final String NEW_EQUIPMENT = "Equipment";
    public static final Integer NEW_MAX_PEOPLE = 5;
    public static final Integer NEW_COST_PER_DAY = 100;
    public static final Integer NEW_PERCENTAGE_INSTRUCTOR_KEEPS_IF_RESERVATION_CANCELLED = 0;
    public static final String NEW_COUNTRY = "Srbija";
    public static final String NEW_ADDRESS = "Vojvode Stepe";
    public static final String NEW_CITY = "Beograd";
    public static final Set<String> NEW_FISHING_TRIP_RESERVATION_TAGS = new HashSet<>(Arrays.asList("lesson", "adventure"));
}
