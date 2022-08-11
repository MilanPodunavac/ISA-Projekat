package code.exceptions.fishing_trip.quick_reservation;

public class ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException extends Exception {
    public ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException(String message) {
        super(message);
    }
}