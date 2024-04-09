package wv.work.nst.neptunescheduletracker.security.validate;

/*
  Inheritable interface for classes that require info validation
 */
public interface Validateable<This> {
    public InfoValidator<This> validator();
}
