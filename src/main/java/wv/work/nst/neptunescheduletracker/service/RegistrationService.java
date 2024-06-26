package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.data.RegistrationInfo;

@Service
public class RegistrationService {


    private final UserService userService;

    @Autowired
    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public User register(RegistrationInfo registrationInfo){
        User user = new User();
        user.setFirstName(registrationInfo.getFirstName());
        user.setLastName(registrationInfo.getLastName());
        user.setEmail(registrationInfo.getEmail()); //TODO neptune kod meg a tobbi

        return userService.createUserWithCredidentials(user, registrationInfo.getPassword());
    }
}
