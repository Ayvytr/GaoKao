package com.ayvytr.commonlibrary.util;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import ru.noties.markwon.AbstractMarkwonPlugin;
import ru.noties.markwon.image.AsyncDrawableLoader;
import ru.noties.markwon.image.ImagesPlugin;
import ru.noties.markwon.image.network.NetworkSchemeHandler;
import ru.noties.markwon.priority.Priority;

import java.util.Arrays;

/**
 * Plugin to use OkHttpClient to obtain images from network (http and https schemes)
 *
 * @see #create(OkHttpClient)
 * @since 3.0.0
 */
@SuppressWarnings("WeakerAccess")
public class OkHttpImagesPlugin extends AbstractMarkwonPlugin {

    private static String baseUrl;

    @NonNull
    public static OkHttpImagesPlugin create(@NonNull OkHttpClient okHttpClient, String baseUrl) {
        OkHttpImagesPlugin.baseUrl = baseUrl;
        return new OkHttpImagesPlugin(okHttpClient);
    }

    private final OkHttpClient client;

    OkHttpImagesPlugin(@NonNull OkHttpClient client) {
        this.client = client;
    }

    @Override
    public void configureImages(@NonNull AsyncDrawableLoader.Builder builder) {
        builder.addSchemeHandler(
                Arrays.asList(NetworkSchemeHandler.SCHEME_HTTP, NetworkSchemeHandler.SCHEME_HTTPS),
                new OkHttpSchemeHandler(client, baseUrl)
        );
    }

    @NonNull
    @Override
    public Priority priority() {
        return Priority.after(ImagesPlugin.class);
    }
}
