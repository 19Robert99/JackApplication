package robert.talabishka.jack.controllers;

import org.springframework.web.bind.annotation.*;
import robert.talabishka.jack.model.User;
import robert.talabishka.jack.services.UserService;

@RestController
@RequestMapping(value = "/userManagement/")
public class UserController extends AbstractController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        return userService.logIn(user.getEmail(), user.getPassword());
    }
}
