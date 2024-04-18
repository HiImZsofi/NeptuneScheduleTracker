package wv.work.nst.neptunescheduletracker.register;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;
import wv.work.nst.neptunescheduletracker.security.validate.InfoValidator;
import wv.work.nst.neptunescheduletracker.security.validate.Validateable;

/*
 Data class for information required for registration
*/
@Data
public class RegistrationInfo{

    @NotBlank(message = "A vezetéknév nem lehet üres!")
    private String firstName;
    @NotBlank(message = "A keresztnév nem lehet üres!")
    private String lastName;
    @Email
    @NotBlank(message = "Az email nem lehet üres!")
    private String email;
    @Size(min = 6, max = 30, message = "A jelszónak legalább 6 karakter hosszúnak kell lennie!")
    private String password;
}
