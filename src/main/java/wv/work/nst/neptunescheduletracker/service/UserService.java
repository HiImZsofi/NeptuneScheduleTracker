package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUserWithCredidentials(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public boolean matchPassword(User user, String password) {
        return user.getPassword().equals(passwordEncoder.encode(password));
    }

    public User getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

}
