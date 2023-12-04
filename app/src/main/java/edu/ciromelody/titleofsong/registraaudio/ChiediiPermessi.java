package edu.ciromelody.titleofsong.registraaudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import edu.ciromelody.titleofsong.R;

public class ChiediiPermessi extends AppCompatActivity {

    private static final String TAG = "CHIEDIPERMESSI";
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final int REQUEST_CODE_IMAGE = 100;

    public  final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public  final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;
    public final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 3;
    public final int MY_PERMISSIONS_REQUEST_WRITE_CALL_LOG = 4;
    public final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 5;
    public final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 6;

    final private int MY_REQUEST_READ_EXTERNAL_STORAGE = 123;
    final private int MY_REQUEST_WRITE_EXTERNAL_STORAGE = 124;

    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 4;

    private static final List<String> sPermissions = Arrays.asList(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,

            Manifest.permission.RECORD_AUDIO
    );

    private int mPermissionRequestCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chiedii_permessi);
        if (savedInstanceState != null) {
            mPermissionRequestCount =
                    savedInstanceState.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0);
        }
        // Make sure the app has correct permissions to run
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissionsIfNecessary();}
    }
    private void requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionRequestCount += 1;
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toArray(new String[0]),
                        REQUEST_CODE_PERMISSIONS);
            } else {
              /*  Toast.makeText(this, R.string.set_permissions_in_settings,
                        Toast.LENGTH_LONG).show();
                findViewById(R.id.selectImage).setEnabled(false);*/
                Toast.makeText(this,"permessi concessi",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this, edu.ciromelody.titleofsong.MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {Toast.makeText(this,"permessi concessi",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(this, edu.ciromelody.titleofsong.MainActivity.class);
            startActivity(intent);
            finish();
           }
    }

    private boolean checkAllPermissions() {
        boolean hasPermissions = true;
        for (String permission : sPermissions) {
            hasPermissions &=
                    ContextCompat.checkSelfPermission(
                            this, permission) == PackageManager.PERMISSION_GRANTED;
        }

        return hasPermissions;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary(); // no-op if permissions are granted already.
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PERMISSIONS_REQUEST_COUNT, mPermissionRequestCount);
    }
}