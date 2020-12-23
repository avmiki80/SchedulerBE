package ru.avid.scheduler.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.business.entity.Priority;
import ru.avid.scheduler.business.repository.PriorityRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PriorityService{
    private PriorityRepository priorityRepository;
    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }
    public List<Priority> findAll(String email){
        return this.priorityRepository.findByUserEmailOrderByIdAsc(email);
    }

    public Priority addOrUpdate(Priority priority){
        return this.priorityRepository.save(priority);
    }

    public void delete(Long id){
        this.priorityRepository.deleteById(id);
    }
    public List<Priority> search(String title, String email){
        return this.priorityRepository.find(title, email);
    }
    public Priority findById(Long id){
        return this.priorityRepository.findById(id).get();
    }
}
