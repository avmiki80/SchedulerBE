package ru.avid.scheduler.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.business.entity.Task;
import ru.avid.scheduler.business.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> findAll(String email){
        return this.taskRepository.findByUserEmailOrderByTitleAsc(email);
    }
    public Task findById(Long id){
        return this.taskRepository.findById(id).get();
    }
    public void delete(Long id){
        this.taskRepository.deleteById(id);
    }
    public Task addOrUpdate(Task task){
        return this.taskRepository.save(task);
    }

    public Page<Task> find(
            String title,
            Integer completed,
            Long priorityId,
            Long categoryId,
            String email,
            Date dateFrom,
            Date dateTo,
            PageRequest paging){
        return this.taskRepository.find(title, completed, priorityId, categoryId, email, dateFrom, dateTo, paging);
    }
}
