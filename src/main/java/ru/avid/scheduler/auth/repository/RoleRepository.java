package ru.avid.scheduler.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avid.scheduler.auth.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
