package ru.avid.scheduler.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avid.scheduler.business.entity.Stat;
import ru.avid.scheduler.business.service.StatService;
import ru.avid.scheduler.business.util.MyLogger;

@RestController
public class StatController {
    private final StatService statService;
    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email){
        MyLogger.debugMethodName("StatController: findByEmail(email)");
        return ResponseEntity.ok(this.statService.findStat(email));
    }

}
