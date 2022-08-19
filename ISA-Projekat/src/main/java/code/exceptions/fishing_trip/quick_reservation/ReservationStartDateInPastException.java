package code.exceptions.fishing_trip.quick_reservation;

public class ReservationStartDateInPastException extends Exception {
    public ReservationStartDateInPastException(String message) {
        super(message);
    }
}