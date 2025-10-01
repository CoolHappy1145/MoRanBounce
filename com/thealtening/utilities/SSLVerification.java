package com.thealtening.utilities;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: L-out.jar:com/thealtening/utilities/SSLVerification.class */
public class SSLVerification {
    private boolean verified = false;

    public void verify() throws NoSuchAlgorithmException, KeyManagementException {
        if (!this.verified) {
            bypassSSL();
            whitelistTheAltening();
            this.verified = true;
        }
    }

    private void bypassSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustManagerArr = {new X509TrustManager(this) { // from class: com.thealtening.utilities.SSLVerification.1
            final SSLVerification this$0;

            {
                this.this$0 = this;
            }
        }};
        try {
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        } catch (Exception unused) {
        }
    }

    private void whitelistTheAltening() {
        HttpsURLConnection.setDefaultHostnameVerifier(SSLVerification::lambda$whitelistTheAltening$0);
    }

    private static boolean lambda$whitelistTheAltening$0(String str, SSLSession sSLSession) {
        return str.equals("authserver.thealtening.com") || str.equals("sessionserver.thealtening.com");
    }
}
