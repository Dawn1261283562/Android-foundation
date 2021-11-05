package com.example.demo1.schedule;

import com.example.demo1.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    private StockDao stockDao;
    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?") //测试，每5秒出发任务
    @Scheduled(cron = "0 0 0 * * ?") // 每天0点触发任务
    private void configureTasks() {
        stockDao.ReduceHit();
    }
}
