package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //TODO password encoder
    public User createUser(User user, String password) {
        return userRepository.save(user);
    }
}
