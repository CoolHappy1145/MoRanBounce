package net.ccbluex.liquidbounce.utils.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0007J\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004H\u0002J\"\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004H\u0002J \u0010\u0013\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004J \u0010\u0014\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004J\"\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004J\"\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "DEFAULT_AGENT_CHINA", "download", "", "url", "file", "Ljava/io/File;", "downloadChina", PropertyDescriptor.GET, "getChina", "make", "Ljava/net/HttpURLConnection;", "method", "agent", "makeChina", "request", "requestChina", "requestStream", "Ljava/io/InputStream;", "requestStreamChina", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/HttpUtils.class */
public final class HttpUtils {
    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String DEFAULT_AGENT_CHINA = "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.0; MyIE2; .NET CLR 1.1.4322)";
    public static final HttpUtils INSTANCE = new HttpUtils();

    private HttpUtils() {
    }

    static {
        HttpURLConnection.setFollowRedirects(true);
    }

    static HttpURLConnection make$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.make(str, str2, str3);
    }

    private final HttpURLConnection make(String str, String str2, String str3) throws IOException {
        URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
        if (uRLConnectionOpenConnection == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setConnectTimeout(2000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("User-Agent", str3);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    static HttpURLConnection makeChina$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT_CHINA;
        }
        return httpUtils.makeChina(str, str2, str3);
    }

    private final HttpURLConnection makeChina(String str, String str2, String str3) throws IOException {
        URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
        if (uRLConnectionOpenConnection == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setConnectTimeout(2000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("accept", "*/*");
        httpURLConnection.setRequestProperty("connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("user-agent", str3);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    public static String request$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.request(str, str2, str3);
    }

    @NotNull
    public final String request(@NotNull String url, @NotNull String method, @NotNull String agent) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        InputStream inputStream = make(url, method, agent).getInputStream();
        Intrinsics.checkExpressionValueIsNotNull(inputStream, "connection.inputStream");
        return TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8));
    }

    public static String requestChina$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT_CHINA;
        }
        return httpUtils.requestChina(str, str2, str3);
    }

    @NotNull
    public final String requestChina(@NotNull String url, @NotNull String method, @NotNull String agent) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        InputStream inputStream = makeChina(url, method, agent).getInputStream();
        Intrinsics.checkExpressionValueIsNotNull(inputStream, "connection.inputStream");
        return TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8));
    }

    public static InputStream requestStream$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.requestStream(str, str2, str3);
    }

    @Nullable
    public final InputStream requestStream(@NotNull String url, @NotNull String method, @NotNull String agent) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        return make(url, method, agent).getInputStream();
    }

    public static InputStream requestStreamChina$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT_CHINA;
        }
        return httpUtils.requestStreamChina(str, str2, str3);
    }

    @Nullable
    public final InputStream requestStreamChina(@NotNull String url, @NotNull String method, @NotNull String agent) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        return makeChina(url, method, agent).getInputStream();
    }

    @JvmStatic
    @NotNull
    public static final String get(@NotNull String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        return request$default(INSTANCE, url, "GET", null, 4, null);
    }

    @JvmStatic
    @NotNull
    public static final String getChina(@NotNull String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        return requestChina$default(INSTANCE, url, "GET", null, 4, null);
    }

    @JvmStatic
    public static final void download(@NotNull String url, @NotNull File file) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(file, "file");
        FileUtils.copyInputStreamToFile(make$default(INSTANCE, url, "GET", null, 4, null).getInputStream(), file);
    }

    @JvmStatic
    public static final void downloadChina(@NotNull String url, @NotNull File file) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(file, "file");
        FileUtils.copyInputStreamToFile(makeChina$default(INSTANCE, url, "GET", null, 4, null).getInputStream(), file);
    }
}
