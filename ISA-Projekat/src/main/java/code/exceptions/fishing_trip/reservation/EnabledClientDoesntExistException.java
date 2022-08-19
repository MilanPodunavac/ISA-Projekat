package code.exceptions.fishing_trip.reservation;

public class EnabledClientDoesntExistException extends Exception {
    public EnabledClientDoesntExistException(String message) {
        super(message);
    }
}