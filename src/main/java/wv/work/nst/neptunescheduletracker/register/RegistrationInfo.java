package wv.work.nst.neptunescheduletracker.data;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import wv.work.nst.neptunescheduletracker.security.validate.InfoValidator;
import wv.work.nst.neptunescheduletracker.security.validate.Validateable;

import javax.validation.constraints.Size;

@Data
public class RegistrationInfo implements Validateable<RegistrationInfo> {

    @NotBlank(message = "A vezetéknév nem lehet üres!")
    private String firstName;
    @NotBlank(message = "A keresztnév nem lehet üres!")
    private String lastName;
    @Email
    @NotBlank(message = "Az email nem lehet üres!")
    private String email;
    @Size(min = 6, max = 30, message = "A jelszónak legalább 6 karakter hosszúnak kell lennie!")
    private String password;
    private String passwordConfirm;


    @Override
    public InfoValidator<RegistrationInfo> validator() {
        return null;
    }
}
