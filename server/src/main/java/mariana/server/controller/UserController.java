package mariana.server.controller;

import mariana.server.model.User;
import mariana.server.service.UserService;
import mariana.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createUser(@RequestBody @Valid User user) {
        userService.save(user);
        return ResponseEntity.ok(new GenericResponse("User saved!"));
    }

}
