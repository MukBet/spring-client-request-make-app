package com.example.democlient.repository

import com.example.democlient.model.RequestLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestLogRepository : JpaRepository<RequestLog, Long>