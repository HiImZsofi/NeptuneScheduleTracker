package wv.work.nst.neptunescheduletracker.data;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/*
 Data class for information required for registration
*/
@Data
public class RegistrationInfo {

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
