package net

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HttpUtil {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    /**
     * 获取数据
     */
    suspend fun getData(): String {
        val rocket = httpClient.get("https://wanandroid.com/wenda/list/1/json").body<String>()
        return rocket
    }
}