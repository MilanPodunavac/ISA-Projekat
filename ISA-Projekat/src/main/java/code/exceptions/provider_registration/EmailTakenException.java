package code.exceptions.provider_registration;

public class EmailTakenException extends Exception {
    public EmailTakenException(String message) {
        super(message);
    }
}
