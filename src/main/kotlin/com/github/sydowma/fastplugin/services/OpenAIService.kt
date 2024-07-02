package com.github.sydowma.fastplugin.services

import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class OpenAIService(private val apiKey: String) {
    @Throws(IOException::class)
    fun callOpenAI(prompt: String?): String {
        val client = OkHttpClient()

        val json = JsonObject().apply {
            addProperty("prompt", prompt)
            addProperty("max_tokens", 10000)
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
            return response.body?.string() ?: throw IOException("Response body is null")
        }
    }

    companion object {
        private const val API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions"
    }
}
