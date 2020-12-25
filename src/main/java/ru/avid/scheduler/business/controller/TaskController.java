package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.business.entity.Task;
import ru.avid.scheduler.business.search.TaskSearchValues;
import ru.avid.scheduler.business.service.TaskService;
import ru.avid.scheduler.business.util.MyLogger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static final String ID_COLUMN = "id";

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

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues){
        MyLogger.debugMethodName("TaskController: search(taskSearchValues)");

        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;
        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;
        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : 0;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : 10;
        String email = taskSearchValues.getEmail() != null ? taskSearchValues.getEmail() : null;

        Date dateFrom = null;
        Date dateTo = null;
        if (taskSearchValues.getDateFrom() != null){
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(taskSearchValues.getDateFrom());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
            calendarFrom.set(Calendar.MINUTE, 0);
            calendarFrom.set(Calendar.SECOND, 0);
            calendarFrom.set(Calendar.MILLISECOND, 0);
            dateFrom = calendarFrom.getTime();
        }
        if (taskSearchValues.getFromTo() != null){
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(taskSearchValues.getFromTo());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 23);
            calendarFrom.set(Calendar.MINUTE, 59);
            calendarFrom.set(Calendar.SECOND, 59);
            calendarFrom.set(Calendar.MILLISECOND, 999);
            dateTo = calendarFrom.getTime();
        }
        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        if (sortColumn == null){
            sortColumn = ID_COLUMN;
        }

        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Task> result = this.taskService.find(title, completed, priorityId, categoryId, email, dateFrom, dateTo, pageRequest);
        return ResponseEntity.ok(result);
    }
}
