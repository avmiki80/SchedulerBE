package ru.avid.scheduler.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.auth.entity.Activity;
import ru.avid.scheduler.auth.entity.Role;
import ru.avid.scheduler.auth.entity.User;
import ru.avid.scheduler.auth.repository.ActivityRepository;
import ru.avid.scheduler.auth.repository.RoleRepository;
import ru.avid.scheduler.auth.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    public static final String DEFAULT_ROLE = "USER";
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }
    public void register(User user, Activity activity){
        this.userRepository.save(user);
        this.activityRepository.save(activity);
    }
    public void save(User user){
        this.userRepository.save(user);
    }

    public boolean userExists(String username, String email){
        if (this.userRepository.existsByEmail(email)){
            return true;
        }
        if (this.userRepository.existsByUserName(username)){
            return true;
        }
        return false;
    }
    public Optional<Role> findByName(String role){
        return this.roleRepository.findByName(role);
    }
}
