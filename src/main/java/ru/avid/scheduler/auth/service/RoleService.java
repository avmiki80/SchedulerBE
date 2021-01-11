package ru.avid.scheduler.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.auth.repository.RoleRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    private RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
