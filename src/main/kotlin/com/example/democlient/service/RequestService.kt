package com.example.democlient.service

import com.example.democlient.model.RequestLog
import com.example.democlient.repository.RequestLogRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.*
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class RequestService(private val repository: RequestLogRepository) {

    private val restTemplate = RestTemplate()

    @Value("\${server.url:http://localhost:8080/data}")
    private lateinit var serverUrl: String

    fun sendRandomRequest() {
        val isPost = Random.nextBoolean()
        val url = "$serverUrl/${getRandomStatusCode()}"
        val params = generateRandomParams()

        val response: ResponseEntity<String>
        try {
            response = if (isPost) {
                val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
                val request = HttpEntity(params, headers)
                restTemplate.postForEntity(url, request, String::class.java)
            } else {
                val uri = buildUriWithParams(url, params)
                restTemplate.getForEntity(uri, String::class.java)
            }

            saveLog(params.toString(), response.statusCode.is2xxSuccessful)
        } catch (ex: Exception) {
            saveLog(params.toString(), false)
        }
    }

    private fun getRandomStatusCode(): Int {
        val statusCodes = listOf(100, 101, 102, 103, 200, 201, 202, 203, 204, 205, 206, 207, 208, 226)
        return statusCodes.random()
    }

    private fun generateRandomParams(): Map<String, Any> {
        return (1..Random.nextInt(1, 11)).associate {
            generateRandomString(5, 10) to generateRandomValue()
        }
    }

    private fun generateRandomString(minLen: Int, maxLen: Int): String {
        val len = Random.nextInt(minLen, maxLen + 1)
        return (1..len).map { ('a'..'z').random() }.joinToString("")
    }

    private fun generateRandomValue(): Any {
        return when (Random.nextInt(4)) {
            0 -> Random.nextInt(100)
            1 -> Random.nextDouble(100.0)
            2 -> Random.nextBoolean()
            else -> generateRandomString(5, 10)
        }
    }

    private fun buildUriWithParams(url: String, params: Map<String, Any>): String {
        return url + "?" + params.entries.joinToString("&") { "${it.key}=${it.value}" }
    }

    private fun saveLog(requestData: String, isSuccess: Boolean) {
        val log = RequestLog(
            timestamp = LocalDateTime.now(),
            requestData = requestData,
            isSuccess = isSuccess
        )
        repository.save(log)
    }
}