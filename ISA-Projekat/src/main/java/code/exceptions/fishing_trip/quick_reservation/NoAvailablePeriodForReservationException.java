package code.exceptions.fishing_trip.quick_reservation;

public class NoAvailablePeriodForReservationException extends Exception {
    public NoAvailablePeriodForReservationException(String message) {
        super(message);
    }
}