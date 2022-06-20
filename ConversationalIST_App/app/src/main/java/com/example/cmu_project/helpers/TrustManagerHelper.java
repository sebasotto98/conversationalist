package com.example.cmu_project.helpers;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustManagerHelper implements X509TrustManager {

    private static final Logger logger = Logger.getLogger(TrustManagerHelper.class.getName());

    static class LocalStoreX509TrustManager implements X509TrustManager {

        private final X509TrustManager trustManager;

        LocalStoreX509TrustManager(KeyStore localTrustStore) {
            try {
                TrustManagerFactory tmf = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(localTrustStore);

                trustManager = findX509TrustManager(tmf);
                if (trustManager == null) {
                    throw new IllegalStateException(
                            "Couldn't find X509TrustManager");
                }
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            trustManager.checkClientTrusted(chain, authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return trustManager.getAcceptedIssuers();
        }
    }

    static X509TrustManager findX509TrustManager(TrustManagerFactory tmf) {
        TrustManager[] tms = tmf.getTrustManagers();
        for (TrustManager tm : tms) {
            if (tm instanceof X509TrustManager) {
                return (X509TrustManager) tm;
            }
        }

        return null;
    }

    private final X509TrustManager defaultTrustManager;
    private final X509TrustManager localTrustManager;

    private final X509Certificate[] acceptedIssuers;

    public TrustManagerHelper(KeyStore localKeyStore) {
        try {
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);

            defaultTrustManager = findX509TrustManager(tmf);
            if (defaultTrustManager == null) {
                throw new IllegalStateException(
                        "Couldn't find X509TrustManager");
            }

            localTrustManager = new LocalStoreX509TrustManager(localKeyStore);

            List<X509Certificate> allIssuers = new ArrayList<X509Certificate>();
            allIssuers.addAll(Arrays.asList(defaultTrustManager
                    .getAcceptedIssuers()));
            allIssuers.addAll(Arrays.asList(localTrustManager.getAcceptedIssuers()));
            acceptedIssuers = allIssuers.toArray(new X509Certificate[0]);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        try {
            logger.info("Checking client trust with default trust manager...");
            defaultTrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException ce) {
            logger.info("Checking client trust with local trust manager...");
            localTrustManager.checkClientTrusted(chain, authType);
        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        try {
            logger.info("Checking server trust with default trust manager...");
            defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException ce) {
            logger.info("Checking server trust with local trust manager...");
            localTrustManager.checkServerTrusted(chain, authType);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return acceptedIssuers;
    }

}