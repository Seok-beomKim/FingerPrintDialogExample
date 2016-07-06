package com.hyosung.fingerprint;

/**
 * Created by SBKim on 2016-07-06.
 */
public interface FingerprintCallback {
    void onAuthenticated();
    void onError(int msgId);
}
