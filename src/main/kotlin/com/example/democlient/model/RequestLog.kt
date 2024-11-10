package com.example.democlient.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class RequestLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val requestData: String,
    val isSuccess: Boolean
)