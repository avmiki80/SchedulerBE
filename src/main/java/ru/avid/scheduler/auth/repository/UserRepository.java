package ru.avid.scheduler.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avid.scheduler.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //case начало блока условия
    //when count(u)>0 само условия
    //then true что делать если уловие выполняется
    //else false что делать если условие не выполняется
    //end конец блока условия
    @Query("select case when count(u)>0 then true  else false end from User u where lower(u.email) = lower(:email)")
    boolean existsByEmail(@Param("email") String email);
    @Query("select case when count(u)>0 then true  else false end from User u where lower(u.username) = lower(:username)")
    boolean existsByUserName(@Param("username") String username);

}
