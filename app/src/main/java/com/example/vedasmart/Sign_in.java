package com.example.vedasmart;

import static android.Manifest.permission.READ_SMS;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Sign_in extends AppCompatActivity {
    //FirebaseAuth class
    private FirebaseAuth auth;
    //sms request code
    private static final int SMS_REQ_CODE = 200;
    TextInputEditText phonenumber, otp;
    TextView getcode, skip;
    Button sign_in_sign_up;
    //string for storing our verification ID
    private String verificationId;
    //
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(Sign_in.this);
        setContentView(R.layout.activity_sign_in);
        smsPermissionActions();
        auth = FirebaseAuth.getInstance();
        actions();

    }

    private void smsPermissionActions() {
        if (checkPermission()) {
        } else {
            ActivityCompat.requestPermissions(Sign_in.this, new String[]{READ_SMS}, SMS_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQ_CODE) {
            if (grantResults.length > 0) {
                int msg = grantResults[0];
                boolean checkSms = msg == PackageManager.PERMISSION_GRANTED;
                if (checkSms) {
                    Toast.makeText(this, "permission granted ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "permission Denied ", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    public boolean checkPermission() {
        int msgRequest = ActivityCompat.checkSelfPermission(this, READ_SMS);
        return msgRequest == PackageManager.PERMISSION_GRANTED;
    }

    private void actions() {
        phonenumber = findViewById(R.id.phone_number);
        otp = findViewById(R.id.verification_code);
        getcode = findViewById(R.id.verification_code_text);
        skip = findViewById(R.id.skip);
        sign_in_sign_up = findViewById(R.id.sign_in);

        //skip the login Activity and goes to dashBoard directly

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sign_in.this, DashBoard.class));
            }
        });
        //getting otp from firebase
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonenumber.getText().toString().isEmpty() || phonenumber.getText().toString().length() != 10) {
                    //when mobile number text field is empty displaying a toast message.
                    Toast.makeText(Sign_in.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.show();
                    //if the text field is not empty we are calling our send OTP method for gettig OTP from Firebase.
                    String phone = "+91" + phonenumber.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });
        sign_in_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validating if the OTP text field is empty or not.
                if (phonenumber.getText().toString().isEmpty() || otp.getText().toString().isEmpty()) {
                    //if the OTP text field is empty display a message to user to enter OTP
                    Toast.makeText(Sign_in.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                } else {
                    //if OTP field is not empty calling method to verify the OTP.
                    verifyCode(otp.getText().toString());
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        //inside this method we are checking if the code entered is correct or not.
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if the code is correct and the task is succesful we are sending our user to new activity.

                            startActivity(new Intent(Sign_in.this, DashBoard.class));
                            finish();

                        } else {
                            //if the code is not correct then we are displaying an error message to the user.
                            Toast.makeText(Sign_in.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            //initializing our callbacks for on verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //below method is used when OTP is sent from Firebase
        @Override

        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            progressDialog.dismiss();
            super.onCodeSent(s, forceResendingToken);
            //when we recieve the OTP it contains a unique id wich we are storing in our string which we have already created.
            verificationId = s;
        }

        //this method is called when user recieve OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //below line is used for getting OTP code which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            //checking if the code is null or not.
            if (code != null) {
                //if the code is not null then we are setting that code to our OTP edittext field.
                otp.setText(code);
                //after setting this code to OTP edittext field we are calling our verifycode method.
                verifyCode(code);

            }

        }

        //thid method is called when firebase doesnot sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            //displaying error message with firebase exception.
            Toast.makeText(Sign_in.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
}
