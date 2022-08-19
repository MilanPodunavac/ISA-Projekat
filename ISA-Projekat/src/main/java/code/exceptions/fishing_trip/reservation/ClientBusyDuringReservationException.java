package code.exceptions.fishing_trip.reservation;

public class ClientBusyDuringReservationException extends Exception {
    public ClientBusyDuringReservationException(String message) {
        super(message);
    }
}