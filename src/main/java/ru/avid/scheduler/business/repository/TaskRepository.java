package ru.avid.scheduler.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avid.scheduler.business.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
