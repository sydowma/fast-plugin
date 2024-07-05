package com.github.sydowma.fastplugin.services;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class DefaultStreamListener extends AbstractStreamListener {
    public Function1<String, Unit> callback;

    @Override
    public void onMsg(String message) {
        callback.invoke(message);
    }

    @Override
    public void onError(Throwable throwable, String response) {

        callback.invoke(throwable.getMessage() + "\n" + response);
    }
}
