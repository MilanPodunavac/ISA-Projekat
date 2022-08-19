package code.exceptions.fishing_trip.reservation;

public class FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException extends Exception {
    public FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException(String message) {
        super(message);
    }
}