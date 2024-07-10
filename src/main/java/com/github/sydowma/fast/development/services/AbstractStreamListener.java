package com.github.sydowma.fast.development.services;

import com.google.gson.Gson;
import okhttp3.Response;


import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractStreamListener extends EventSourceListener {

    private static final Logger log = LoggerFactory.getLogger(AbstractStreamListener.class);

    private static final Gson gson = new Gson();

    protected String lastMessage = "";


    /**
     * Called when all new message are received.
     *
     * @param message the new message
     */
    protected Consumer<String> onComplete = s -> {

    };

    /**
     * Called when a new message is received.
     * 收到消息 单个字
     *
     * @param message the new message
     */
    public abstract void onMsg(String message);

    /**
     * Called when an error occurs.
     * 出错时调用
     *
     * @param throwable the throwable that caused the error
     * @param response  the response associated with the error, if any
     */
    public abstract void onError(Throwable throwable, String response);

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        // do nothing
    }

    @Override
    public void onClosed(EventSource eventSource) {
        // do nothing
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        if (data.equals("[DONE]")) {
            onComplete.accept(lastMessage);
            return;
        }



        ChatCompletionResponse response = gson.fromJson(data, ChatCompletionResponse.class);
        List<ChatChoice> choices = response.getChoices();
        if (choices == null || choices.isEmpty()) {
            return;
        }
        Message delta = choices.get(0).getDelta();
        String text = delta.getContent();

        if (text != null) {
            lastMessage += text;
            onMsg(text);

        }

    }


    @Override
    public void onFailure(EventSource eventSource, Throwable throwable, Response response) {

        try {
            log.error("Stream connection error: {}", throwable);

            String responseText = "";

            if (Objects.nonNull(response)) {
                responseText = response.body().string();
            }

            log.error("response：{}", responseText);

            this.onError(throwable, responseText);

        } catch (Exception e) {
            log.warn("onFailure error:{}", e);
            // do nothing

        } finally {
            eventSource.cancel();
        }
    }
}