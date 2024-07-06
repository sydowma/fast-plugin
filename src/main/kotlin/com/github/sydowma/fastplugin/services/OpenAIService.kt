package com.github.sydowma.fastplugin.services

import com.github.sydowma.fastplugin.settings.SettingsState
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ibm.icu.util.TimeUnit
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSources.createFactory
import java.net.Proxy
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

class OpenAIService(private val settingsState: SettingsState) {
    fun callOpenAI(userMessage: String?, callback: (String) -> Unit) {
        var proxy = Proxy.NO_PROXY
        if (settingsState.proxyAddress?.isNotEmpty() == true ) {
            val proxyType: Proxy.Type = Proxy.Type.valueOf(settingsState.proxyProtocol?.toUpperCase() ?: proxy.toString())
            proxy = Proxy(proxyType, java.net.InetSocketAddress(settingsState.proxyAddress, settingsState.proxyPort))
        }
        val client = OkHttpClient().newBuilder().proxy(proxy).readTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .callTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .connectTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .build()

        try {

            val messagesArray = JsonArray().apply {
                add(JsonObject().apply {
                    addProperty("role", "system")
                    addProperty("content", "You are a helpful assistant.")
                })
                add(JsonObject().apply {
                    addProperty("role", "user")
                    addProperty("content", userMessage)
                })
            }

            val json = JsonObject().apply {
                addProperty("model", "gpt-4o")
                addProperty("stream", true)
                add("messages", messagesArray)
            }

            val body: RequestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

            val request: Request = Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer ${settingsState.apiKey}")
                .post(body)
                .build()

            val factory: EventSource.Factory = createFactory(client)
            val streamListener: DefaultStreamListener = DefaultStreamListener()
            factory.newEventSource(request, streamListener)

            callback.apply { streamListener.callback = this }
        } catch (e: Exception) {
            e.message?.let { callback(it) }
        }


    }

    companion object {
        private const val API_URL = "https://api.openai.com/v1/chat/completions"
    }
}
