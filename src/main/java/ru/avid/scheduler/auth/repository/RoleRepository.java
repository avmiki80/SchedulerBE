package ru.avid.scheduler.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.avid.scheduler.auth.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // возвращает контейнер Optional, в котором может быть объект или null
    Optional<Role> findByName(String name); // поиск роли по названию
}
