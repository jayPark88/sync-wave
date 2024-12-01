package com.parker.batch.schdules.scheduler;

import com.parker.batch.schdules.service.SchedulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulesSchduler {
    private final SchedulesService schedulesService;

    @Scheduled(cron = "0 0 * * * *")
    void alertUsersAboutScheduleInOneHourTask() {
        schedulesService.alertUsersAboutScheduleInOneHourTask();
    }

    @Scheduled(cron = "0 0 9,18 * * *")
    void alertUsersAboutTodosReminderTask(){}
}
