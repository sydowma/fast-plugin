package com.github.sydowma.fastplugin.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class OpenAIService(private val apiKey: String) {
    @Throws(IOException::class)
    fun callOpenAI(userMessage: String?): String {
        val client = OkHttpClient()

        val json = JsonObject().apply {
            addProperty("model", "gpt-4o")
            add("messages", JsonArray().apply {
                add(JsonObject().apply {
                    addProperty("role", "system")
                    addProperty("content", "You are a helpful assistant.")
                })
                add(JsonObject().apply {
                    addProperty("role", "user")
                    addProperty("content", userMessage)
                })
            })
        }

        val body: RequestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        val request: Request = Request.Builder()
            .url(API_URL)
            .header("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            val responseBody = response.body?.string() ?: throw IOException("Response body is null")
            val jsonResponse = JsonParser.parseString(responseBody).asJsonObject
            val messageContent = jsonResponse.getAsJsonArray("choices")
                .get(0).asJsonObject
                .getAsJsonObject("message")
                .get("content").asString
            return messageContent
        }
    }

    companion object {
        private const val API_URL = "https://api.openai.com/v1/chat/completions"
    }
}
