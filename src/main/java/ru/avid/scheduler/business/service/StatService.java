package ru.avid.scheduler.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.business.entity.Stat;
import ru.avid.scheduler.business.repository.StatRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatService {
    private StatRepository statRepository;
    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }
    public Stat findStat(String email){
        return this.statRepository.findByUserEmail(email);
    }
}
