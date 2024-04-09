package wv.work.nst.neptunescheduletracker.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.service.UserService;

@Service
public class RegistrationService {

    @Autowired
    UserService userService;

    public User register(RegistrationInfo registrationInfo){
        User user = new User();
        user.setFirstName(registrationInfo.getFirstName());
        user.setLastName(registrationInfo.getLastName());
        user.setEmail(registrationInfo.getEmail()); //TODO neptune kod meg a tobbi

        return userService.createUser(user, registrationInfo.getPassword());
    }
}
