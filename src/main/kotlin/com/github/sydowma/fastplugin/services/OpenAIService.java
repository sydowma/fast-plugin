package com.github.sydowma.fastplugin.services;

import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class OpenAIService {
    private static final String API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
    private final String apiKey;

    public OpenAIService(String apiKey) {
        this.apiKey = apiKey;
    }

    public String callOpenAI(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("prompt", prompt);
        json.addProperty("max_tokens", 100);

        RequestBody body = RequestBody.create(
                json.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
