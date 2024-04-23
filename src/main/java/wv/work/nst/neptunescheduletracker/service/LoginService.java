package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.data.LoginInfo;
import wv.work.nst.neptunescheduletracker.entity.User;

import java.util.HashMap;

@Service
public class LoginService {
    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public int checkCredentials(LoginInfo loginInfo) {
        HashMap<Integer, String> response = new HashMap<>();
        if (userService.getUserByEmail(loginInfo.getEmail()) == null) {
            return 404; //user not found
        }
        User foundUser = userService.getUserByEmail(loginInfo.getEmail());

        if (!userService.matchPassword(foundUser, loginInfo.getPassword())) {
            return 401; //password not matching
        }

        return 200;
    }


}
