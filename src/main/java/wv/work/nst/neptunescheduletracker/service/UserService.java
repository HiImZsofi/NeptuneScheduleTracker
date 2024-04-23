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

    public boolean matchPassword(User user, String password) {
        return user.getPassword().equals(passwordEncoder.encoder().encode(password));
    }

    public User getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

}
