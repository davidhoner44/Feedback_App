/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.david.sdaproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author David Honer
 * @version 1.0
 *
 * This class handles Sign in for the application.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://firebase.google.com/docs/auth/android/password-auth
 * Permission: Apache licence
 * Retrieved on: 14th March 2019
 * URL: https://stackoverflow.com/a/46734351
 * Permission: MIT licence
 * Retrieved on: 16th March 2019
 */
public class SignInActivity extends AppCompatActivity
{
    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private Button SignInButton;
    private TextInputEditText UserEmail, UserPassword;
    private TextView RegisterAccount;
    private String message;
    ProgressDialog mBar;

    /**
     * FireBase variable initialised when activity created.
     * onClickListeners handle when user wishes to register or when
     * sign in is permitted.
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.i(TAG, "SignIn Activity started");

        // Keep keyboard hidden until user clicks EditText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Call method assigning variables
        initialiseFields();

        // Set click listener for sending to Register activity
        RegisterAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                sendUserToRegisterActivity();
            }
        });

        // Set click listener for allowing Sign in
        SignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                allowSignIn();
            }
        });
    }


    /**
     * Method to verify if user has entered email and password.
     * Code adapted from https://firebase.google.com/docs/auth/android/password-auth
     * Code adapted from https://stackoverflow.com/a/46734351
     */
    private void allowSignIn()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        // Display a message to user if email or password field are left empty.
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else
            {
            mBar.setTitle("Sign In");
            mBar.setMessage("Please Wait");
            mBar.setCanceledOnTouchOutside(true);
            mBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // then the user will be sent to the WelcomeActivity.
                            if (task.isSuccessful())
                            {
                                Log.i(TAG, "User has been signed in successfully");
                                sendUserToWelcomeActivity();
                                Toast.makeText(SignInActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                mBar.dismiss();
                            }
                            else
                                {
                                    Log.w(TAG, "signedInApplication", task.getException());
                                    if (task.getException() != null)
                                    {
                                        message = task.getException().toString();
                                    }
                                    Toast.makeText(SignInActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    mBar.dismiss();
                                }
                        }
                    });
            }
    }

    /** Method to initialise all appropriate views */
    private void initialiseFields()
    {
        SignInButton = (Button) findViewById(R.id.signin_button);
        UserEmail = (TextInputEditText) findViewById(R.id.signin_email);
        UserPassword = (TextInputEditText) findViewById(R.id.signin_password);
        RegisterAccount = (TextView) findViewById(R.id.signin_register);
        mBar = new ProgressDialog(this);
    }

    /**
     *  Method containing intent to send user to welcome activity
     *  Code adapted from https://developer.android.com/reference/android/content/Intent
     */
    private void sendUserToWelcomeActivity()
    {
        Intent mainIntent = new Intent(SignInActivity.this, WelcomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    /**
    *  Method containing intent to send user to register activity
    *  Code adapted from https://developer.android.com/reference/android/content/Intent
    */
    private void sendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(SignInActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
