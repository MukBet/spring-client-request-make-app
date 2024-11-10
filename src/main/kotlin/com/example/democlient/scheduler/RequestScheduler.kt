package com.example.democlient.scheduler

import com.example.democlient.service.RequestService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RequestScheduler(private val requestService: RequestService) {

    @Scheduled(fixedRateString = "\${request.interval:3000}")
    fun scheduleRequests() {
        requestService.sendRandomRequest()
    }
}