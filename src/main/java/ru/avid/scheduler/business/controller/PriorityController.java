package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.entity.Priority;
import ru.avid.scheduler.business.search.CategorySearchValues;
import ru.avid.scheduler.business.search.PrioritySearchValues;
import ru.avid.scheduler.business.service.PriorityService;
import ru.avid.scheduler.business.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/priority")
public class PriorityController {
    private PriorityService priorityService;
    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }
    @PostMapping("/all")
    public ResponseEntity<List<Priority>> findAll(@RequestBody String email) {
        MyLogger.debugMethodName("PriorityController: findAll(email)");
        return ResponseEntity.ok(this.priorityService.findAll(email));
    }

    @PutMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        MyLogger.debugMethodName("PriorityController: add(priority)");
        if (priority == null) {
            return new ResponseEntity("priority is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (priority.getId() != 0) {
                return new ResponseEntity("reduntal param: id Must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((priority.getTitle() == null) || (priority.getTitle().trim().length() == 0)) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((priority.getColor() == null) || (priority.getColor().trim().length() == 0)) {
                return new ResponseEntity("missing param: color", HttpStatus.NOT_ACCEPTABLE);
            }
//            две строки ниже идентичны
//            return new ResponseEntity(this.categoryService.add(category), HttpStatus.OK);
            return ResponseEntity.ok(this.priorityService.addOrUpdate(priority));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority) {
        MyLogger.debugMethodName("PriorityController: update(priority)");
        if (priority == null) {
            return new ResponseEntity("priority is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (priority.getId() == 0) {
                return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((priority.getTitle() == null) || priority.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((priority.getColor() == null) || priority.getColor().trim().length() == 0) {
                return new ResponseEntity("missing param: color", HttpStatus.NOT_ACCEPTABLE);
            }

            this.priorityService.addOrUpdate(priority);
            //объект не возвращаем так как он уже создан и только обновляется
            // и нет смысла его обраьно передавать
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id) {
        MyLogger.debugMethodName("PriorityController: delete(id)");
        if(id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.priorityService.delete(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id =" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues){
        MyLogger.debugMethodName("PriorityController: search(prioritySearchValues)");
        return ResponseEntity.ok(this.priorityService.search(prioritySearchValues.getTitle(), prioritySearchValues.getEmail()));
    }
    @PostMapping("/id")
    public ResponseEntity<Priority> finfById(@RequestBody Long id){
        MyLogger.debugMethodName("PriorityController: finfById(id)");
        Priority priority = null;
        try {
            priority = this.priorityService.findById(id);
        } catch (NoSuchElementException ex){
            ex.printStackTrace();;
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }
}
