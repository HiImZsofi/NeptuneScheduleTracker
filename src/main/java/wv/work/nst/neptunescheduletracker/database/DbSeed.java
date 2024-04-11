package wv.work.nst.neptunescheduletracker.database;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.service.UserService;

@Component
public class DbSeed {

    private final UserService userService;

    @Autowired
    public DbSeed(UserService userService) {
        this.userService = userService;
    }

    public void seedEntity(){
        userService.createUser(createEntity());
    }

    public User createEntity(){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        return new User(firstName, lastName, email, password);
    }
}
