package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO password encoder
    public User createUserWithCredidentials(User user, String password) {
        return userRepository.save(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
