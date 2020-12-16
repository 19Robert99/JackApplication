package robert.talabishka.jack.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.talabishka.jack.model.User;
import robert.talabishka.jack.repositories.UserRepository;
import robert.talabishka.jack.services.UserService;

@Service
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public User logIn(String login, String password) {
        return userRepository.logIn(login, password);
    }
}
