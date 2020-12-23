package ru.avid.scheduler.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avid.scheduler.business.entity.Priority;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    List<Priority> findByUserEmailOrderByIdAsc(String email);
    @Query("select p from Priority p where " +
            "(:title is null or :title=''" +
            " or lower(p.title) like lower(concat('%', :title, '%'))) " +
            "and p.user.email like :email order by p.title asc")
    List<Priority> find(@Param("title") String title, @Param("email") String email);
}
