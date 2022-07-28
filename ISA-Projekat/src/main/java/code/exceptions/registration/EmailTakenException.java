package code.exceptions.registration;

public class EmailTakenException extends Exception {
    public EmailTakenException(String message) {
        super(message);
    }
}
