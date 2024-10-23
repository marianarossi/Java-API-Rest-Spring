package mariana.server.controller;

import mariana.server.dto.UserDTO;
import mariana.server.model.User;
import mariana.server.service.ICrudService;
import mariana.server.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController extends CrudController<User, UserDTO, Long> {

    private final IUserService userService;
    private final ModelMapper modelMapper;

    public UserController(IUserService userService, ModelMapper modelMapper) {
        super(User.class, UserDTO.class);
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }

    @Override
    protected ICrudService<User, Long> getService() {
        return this.userService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
