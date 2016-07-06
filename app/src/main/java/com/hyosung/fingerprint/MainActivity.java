package com.hyosung.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements FingerprintCallback{
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verify();
    }

    @Override
    public void onAuthenticated() {
        Toast.makeText(this, "onAuthenticated()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int msgId) {
        if(msgId == FingerprintManager.FINGERPRINT_ERROR_CANCELED) {

        } else if(msgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {

        } else {

        }
    }

    private void verify() {
        if(mFragment.initCipher()) {
            mFragment.setCryptoObject();
            mFragment.setCancelable(false);
            mFragment.setOnFingerprintListener(this);
            mFragment.setTitle("Sign in");
            mFragment.setContent(getString(R.string.fingerprint_description));
            mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            Toast.makeText(this, "Settings -> Secure", Toast.LENGTH_SHORT).show();
        }
    }
}
