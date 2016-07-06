package com.hyosung.fingerprint;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by SBKim on 2016-07-05.
 */
public class BaseActivity extends AppCompatActivity {

    FingerprintAuthenticationDialogFragment mFragment;
    FingerprintManager mFingerprintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            mFragment = new FingerprintAuthenticationDialogFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFingerprintAuthAvailable();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void isFingerprintAuthAvailable() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.USE_FINGERPRINT)) {
                Toast.makeText(this, "Need Fingerprint", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, 100);
                Toast.makeText(this, "isFingerprintAuthAvailable() requestPermissions() Success", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "isFingerprintAuthAvailable() PERMISSION_GRANTED Success", Toast.LENGTH_SHORT).show();
            mFragment.isHardwareDetected = mFingerprintManager.isHardwareDetected();
            mFragment.hasEnrolledFingerprints = mFingerprintManager.hasEnrolledFingerprints();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mFragment.isHardwareDetected = mFingerprintManager.isHardwareDetected();
                    mFragment.hasEnrolledFingerprints = mFingerprintManager.hasEnrolledFingerprints();
                    Toast.makeText(this, "onRequestPermissionsResult() Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Need Fingerprint", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
