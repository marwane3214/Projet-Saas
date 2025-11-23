package com.example.billing.infrastructure.scheduler;

import com.example.billing.service.RenewalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RenewalBatchScheduler {
    
    private final RenewalService renewalService;
    
    /**
     * Runs every day at 00:00 to process due renewals
     * Cron format: second minute hour day month weekday
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void processDueRenewals() {
        log.info("Starting scheduled renewal batch processing");
        try {
            renewalService.processDueRenewals();
            log.info("Completed scheduled renewal batch processing");
        } catch (Exception e) {
            log.error("Error in scheduled renewal batch processing", e);
        }
    }
    
    /**
     * Runs every hour to retry failed renewals
     */
    @Scheduled(cron = "0 0 * * * *")
    public void retryFailedRenewals() {
        log.info("Starting retry of failed renewals");
        try {
            renewalService.retryFailedRenewals();
            log.info("Completed retry of failed renewals");
        } catch (Exception e) {
            log.error("Error retrying failed renewals", e);
        }
    }
}

