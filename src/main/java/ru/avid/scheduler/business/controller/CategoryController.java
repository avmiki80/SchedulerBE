package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.search.CategorySearchValues;
import ru.avid.scheduler.business.service.CategoryService;
import ru.avid.scheduler.business.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //    @GetMapping("/all")
//    public List<Category> findAll(){
//        return this.categoryService.findAll("avmiki80@mail.ru");
//    }
    @PostMapping("/all")
    public ResponseEntity<List<Category>> findAll(@RequestBody String email) {
        MyLogger.debugMethodName("CategoryController: findAll(email)");
        return ResponseEntity.ok(this.categoryService.findAll(email));
    }

    @PutMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        MyLogger.debugMethodName("CategoryController: add(category)");
        if (category == null) {
            return new ResponseEntity("category is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (category.getId() != 0) {
                return new ResponseEntity("reduntal param: id Must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((category.getTitle() == null) || (category.getTitle().trim().length() == 0)) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
//            две строки ниже идентичны
//            return new ResponseEntity(this.categoryService.add(category), HttpStatus.OK);
            return ResponseEntity.ok(this.categoryService.addOrUpdate(category));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        MyLogger.debugMethodName("CategoryController: update(category)");
        if (category == null) {
            return new ResponseEntity("category is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (category.getId() == 0) {
                return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
            }
            if ((category.getTitle() == null) || category.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            this.categoryService.addOrUpdate(category);
            //объект не возвращаем так как он уже создан и только обновляется
            // и нет смысла его обраьно передавать
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id) {
        MyLogger.debugMethodName("CategoryController: delete(id)");
        if(id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.categoryService.delete(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id =" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){
        MyLogger.debugMethodName("CategoryController: search(categorySearchValues)");
        return ResponseEntity.ok(this.categoryService.search(categorySearchValues.getTitle(), categorySearchValues.getEmail()));
    }
    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id){
        MyLogger.debugMethodName("CategoryController: finfById(id)");
        Category category = null;
        try {
            category = this.categoryService.findById(id);
        } catch (NoSuchElementException ex){
            ex.printStackTrace();;
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }
}
