package code.exceptions.fishing_trip.quick_reservation;

public class NoAvailablePeriodForQuickReservationException extends Exception {
    public NoAvailablePeriodForQuickReservationException(String message) {
        super(message);
    }
}