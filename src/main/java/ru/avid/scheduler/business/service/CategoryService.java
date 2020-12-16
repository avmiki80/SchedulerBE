package ru.avid.scheduler.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryRepository categoryRepository;

    public List<Category> findAll(String email){
        return this.categoryRepository.findAll();
    }
}
