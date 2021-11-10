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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * @author David Honer
 * @version 1.0
 *
 * This activity is first called when application is launched.
 * Verifies if the user is logged in.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://firebase.google.com/docs/auth/web/manage-users
 * Permission: Apache licence
 * Retrieved on: 13th March 2019
 * URL: https://firebase.google.com/docs/database/android/read-and-write
 * Permission: Apache licence
 * Retrieved on: 13th March 2019
 */
public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    // Firebase instance variables
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    /**
     * Inflate the Main Activity.
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"Main Activity launched");

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    /**
     * Check if the user is currently logged in.
     * Code adapted from https://firebase.google.com/docs/auth/web/manage-users
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        if (currentUser == null)
        {
            // User is not signed in. Send to Sign in Activity
            Log.i(TAG, "User is not signed in");
            sendUserToSignInActivity();
            finish();
        }
        else
            {
                Log.i(TAG, "User is signed in and sent to Welcome activity");
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
            }
    }

    /** An intent to the Sign in activity is invoked when this method is called */
    private void sendUserToSignInActivity()
    {
        Log.i(TAG, "User sent to Sign in activity");
        Intent signinIntent  = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(signinIntent);
        finish();
    }
}
