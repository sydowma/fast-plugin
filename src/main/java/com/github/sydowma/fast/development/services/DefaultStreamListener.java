package com.github.sydowma.fast.development.services;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import okhttp3.sse.EventSource;

public class DefaultStreamListener extends AbstractStreamListener {
    public Function2<String, Boolean, Unit> callback;

    public void setCallback(Function2<String, Boolean, Unit> callback) {
        this.callback = callback;
    }

    @Override
    public void onMsg(String message) {
        if (callback != null) {
            callback.invoke(message, false);
        }
    }

    @Override
    public void onError(Throwable throwable, String response) {
        if (callback != null) {
            callback.invoke(throwable.getMessage() + "\n" + response, true);
        }
    }

    @Override
    public void onClosed(EventSource eventSource) {
        if (callback != null) {
            callback.invoke("", true);
        }
    }
}
