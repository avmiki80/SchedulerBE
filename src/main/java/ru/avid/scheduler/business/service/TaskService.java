package ru.avid.scheduler.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.business.entity.Task;
import ru.avid.scheduler.business.repository.TaskRepository;

import javax.transaction.Transactional;
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
}
