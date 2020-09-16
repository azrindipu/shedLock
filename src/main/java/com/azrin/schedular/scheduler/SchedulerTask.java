package com.azrin.schedular.scheduler;


import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerTask {

    @Scheduled(cron = "${cron.expression.for.schedule.task}")
    @SchedulerLock(name = "shedLockTestApplication")
    public void task(){
        System.out.println("Scheduler is running");
    }
}
