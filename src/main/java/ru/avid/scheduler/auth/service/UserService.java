package ru.avid.scheduler.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.auth.entity.User;
import ru.avid.scheduler.auth.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User save(User user){
        return this.userRepository.save(user);
    }
}
