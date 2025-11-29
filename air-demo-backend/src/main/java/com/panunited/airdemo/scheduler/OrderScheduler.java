package com.panunited.airdemo.scheduler;

import com.panunited.airdemo.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    @PostConstruct
    public void updateOrderOnStartUp() {
        hardCodedOrderDate ();
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void updateOrderDate() {
        hardCodedOrderDate ();
    }

    private void hardCodedOrderDate() {
        log.info("Update first 2 order date -- START");
        orderService.updateOrderDate ();
        log.info("Update first 2 order date -- END");
    }

}
