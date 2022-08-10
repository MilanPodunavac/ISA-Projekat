package code.exceptions.admin;

public class PasswordSameException extends Exception {
    public PasswordSameException(String message) {
        super(message);
    }
}
