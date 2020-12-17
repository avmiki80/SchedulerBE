package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.service.CategoryService;

import java.util.List;

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
    public List<Category> findAll(@RequestBody String email){
        return this.categoryService.findAll(email);
    }

}
