package wv.work.nst.neptunescheduletracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wv.work.nst.neptunescheduletracker.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
