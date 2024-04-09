package wv.work.nst.neptunescheduletracker.security.validate;

import org.springframework.validation.Errors;

/*
 Inheritable interface for every validator class
 */
public interface InfoValidator<T> {
    public void validate(T target, Errors errors);
}
