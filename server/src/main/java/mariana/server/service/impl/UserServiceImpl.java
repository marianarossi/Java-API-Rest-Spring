package mariana.server.service.impl;

import mariana.server.model.User;
import mariana.server.repository.UserRepository;
import mariana.server.service.IUserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User entity) {
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return this.userRepository;
    }
}
