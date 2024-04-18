package wv.work.nst.neptunescheduletracker.exceptions;

public class EmailIsTakenException extends RuntimeException {
    public EmailIsTakenException(String errorMessage) {
        super(errorMessage);
    }
}
