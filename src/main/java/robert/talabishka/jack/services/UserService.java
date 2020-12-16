package robert.talabishka.jack.services;

import robert.talabishka.jack.model.User;

public interface UserService extends AbstractService<User> {
    User logIn(String login, String password);
}
