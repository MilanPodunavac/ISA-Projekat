package code.exceptions.fishing_trip.quick_reservation;

public class QuickReservationStartDateInPastException extends Exception {
    public QuickReservationStartDateInPastException(String message) {
        super(message);
    }
}