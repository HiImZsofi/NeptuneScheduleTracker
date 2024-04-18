package wv.work.nst.neptunescheduletracker.security.validate;

import org.springframework.validation.Errors;
import wv.work.nst.neptunescheduletracker.exceptions.EmailIsTakenException;

/*
 Inheritable interface for every validator class
 */
public interface InfoValidator<T> {
    public void validate(T target, Errors errors) throws EmailIsTakenException;
}
