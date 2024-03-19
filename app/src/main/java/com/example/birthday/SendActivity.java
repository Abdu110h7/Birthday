package com.example.birthday;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendActivity extends AppCompatActivity {

    ImageView SendMassege;
    TextView  txtMessage;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        SendMassege = findViewById(R.id.sendd);
        txtMessage = findViewById(R.id.xabar);
        message = findViewById(R.id.inputMassege);



        checkPerm();
        SendMassege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmsManager smsManager = SmsManager.getDefault();
                String message = "Salom, men kod bilan sms jo'natdim";

                smsManager.sendTextMessage("+998905374463", null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("+998975011714", null, message, null, null);
            }
        });
    }

    private void checkPerm() {
        if (ContextCompat.checkSelfPermission(SendActivity.this,
            android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SendActivity.this,
                android.Manifest.permission.SEND_SMS)) {
        } else {
            ActivityCompat.requestPermissions(SendActivity.this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    0);
        }
    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    }
