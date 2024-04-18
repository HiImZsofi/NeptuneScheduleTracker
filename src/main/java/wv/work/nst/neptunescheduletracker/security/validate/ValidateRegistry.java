package wv.work.nst.neptunescheduletracker.security.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import wv.work.nst.neptunescheduletracker.register.RegistrationInfo;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;

@Component
public class ValidateRegistry implements Validateable<RegistrationInfo>{

    private final UserRepository userRepository;

    @Autowired
    public ValidateRegistry(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public InfoValidator<RegistrationInfo> validator() {
        return new InfoValidator<RegistrationInfo>() {

            @Override
            public boolean validate(RegistrationInfo target, Errors errors) {

                if (userRepository.findOneByEmail(target.getEmail()) != null) {
                    errors.rejectValue("email", "emailIsTaken", "Ez az email cím már foglalt!");
                    return false;
                }
                return true;
            }
        };
    }
}
