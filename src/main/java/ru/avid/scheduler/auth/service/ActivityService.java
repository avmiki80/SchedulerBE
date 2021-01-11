package ru.avid.scheduler.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avid.scheduler.auth.repository.ActivityRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ActivityService {
    private ActivityRepository activityRepository;
    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
