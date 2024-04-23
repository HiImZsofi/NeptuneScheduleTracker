package wv.work.nst.neptunescheduletracker.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginInfo {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
