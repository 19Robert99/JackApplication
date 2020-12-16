package robert.talabishka.jack.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import robert.talabishka.jack.model.User;

@Repository
public interface UserRepository extends AbstractRepository<User> {
    @Query("SELECT u from User u where u.email = ?1 and u.password = ?2")
    User logIn(String login, String password);

    @Query("SELECT u from User u where u.email = ?1")
    User getUserByEmail(String email);
}
