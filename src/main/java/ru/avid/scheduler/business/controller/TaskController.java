package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.business.entity.Task;
import ru.avid.scheduler.business.service.TaskService;
import ru.avid.scheduler.business.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody String email){
        MyLogger.debugMethodName("TaskController: findAll(email)");
        return ResponseEntity.ok(this.taskService.findAll(email));
    }
    @PutMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task){
        MyLogger.debugMethodName("TaskController: add(task)");
        if (task == null){
            return new ResponseEntity("task is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (task.getId() != 0){
                return new ResponseEntity("reduntal param: id must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (task.getTitle() == null || task.getTitle().trim().length() == 0){
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return ResponseEntity.ok(this.taskService.addOrUpdate(task));
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Task task){
        MyLogger.debugMethodName("TaskController: update(task)");
        if (task == null){
            return new ResponseEntity("task is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (task.getId() == 0){
                return new ResponseEntity("reduntal param: id must be not null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (task.getTitle() == null || task.getTitle().trim().length() == 0){
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            this.taskService.addOrUpdate(task);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id){
        MyLogger.debugMethodName("TaskController: delete(id)");
        if (id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.taskService.delete(id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id){
        MyLogger.debugMethodName("TaskController: findById(id)");
        Task task = null;
        try {
            task  = this.taskService.findById(id);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }
}
