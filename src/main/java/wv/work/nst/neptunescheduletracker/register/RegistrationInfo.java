package wv.work.nst.neptunescheduletracker.register;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;
import wv.work.nst.neptunescheduletracker.security.validate.InfoValidator;
import wv.work.nst.neptunescheduletracker.security.validate.Validateable;

/*
 Data class for information required for registration
*/
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


    @Override
    public InfoValidator<RegistrationInfo> validator() {
        return new InfoValidator<RegistrationInfo>() {

            @Autowired
            UserRepository userRepository;

            @Override
            public void validate(RegistrationInfo target, Errors errors) {

                if (userRepository.findOneByEmail(target.getEmail()) != null) {
                    errors.rejectValue("email", "", "Ez az email cím már foglalt!");
                }
            }
        };
    }
}
