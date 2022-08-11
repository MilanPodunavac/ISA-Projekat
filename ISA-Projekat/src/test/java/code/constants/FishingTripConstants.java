package code.constants;

import java.time.LocalDate;
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
    public static final Integer DB_FISHING_TRIP_ID = 1;
    public static final LocalDate NEW_START_DATE = LocalDate.of(2023, 9, 10);
    public static final Integer NEW_DURATION_IN_DAYS = 5;
    public static final Integer NEW_PRICE = 200;
    public static final LocalDate NEW_VALID_UNTIL_AND_INCLUDING_DATE = LocalDate.of(2023, 9, 8);
}
