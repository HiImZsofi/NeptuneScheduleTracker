package wv.work.nst.neptunescheduletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.repository.UserRepository;
import wv.work.nst.neptunescheduletracker.security.password.PasswordHash;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHash passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordHash passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUserWithCredidentials(User user, String password) {
        String passwordSalt = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encoder().encode(password + passwordSalt));
        return userRepository.save(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
