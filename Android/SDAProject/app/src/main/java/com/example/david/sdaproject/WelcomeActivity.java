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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author David Honer
 * @version 1.0
 *
 * This class handles the different actions for the user to take.
 * They can access the developers details, provide feedback and
 * view previous feedback.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://firebase.google.com/docs/database/android/read-and-write
 * Permission: Apache licence
 * Retrieved on: 12th April 2019
 * Url: https://stackoverflow.com/a/49252750
 * Permission: MIT Licence
 * Retrieved on: 12th April 2019
 */
public class WelcomeActivity extends AppCompatActivity
{
    private static final String TAG = "Welcome Activity";

    Button button;
    FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;
    Button viewFeedback;

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Method to load the Welcome activity. Initialise buttons to handle user
     * actions into the developer details activity, provide feedback activity,
     * and view previous feedback activity. The view feedback button is disabled
     * until new users have provided feedback.
     * Code adapted from https://firebase.google.com/docs/database/android/read-and-write
     * Code adapted from method onDataChange() described at https://stackoverflow.com/a/49252750
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i(TAG,"Welcome Activity has been launched");

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            currentUserID = mAuth.getCurrentUser().getUid();
        }

        // Get database reference
        RootRef = FirebaseDatabase.getInstance().getReference();

        // Added button and click listener to handle user signout request
        button = (Button) findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                sendUserToSignInActivity();
            }
        });

        // Button to handle users transition to game activity.
        // Contains Intent for when click listener is called.
        final Button feedbackButton = (Button) findViewById(R.id.provide_feedback);
        feedbackButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.i(TAG, "User has chose Game Activity from Welcome Activity");
                Intent feedback = new Intent(WelcomeActivity.this, GameActivity.class);
                startActivity(feedback);
            }
        });

        // Button to handle users transition to GameDeveloperDetails activity.
        // Contains Intent for when click listener is called.
        final Button developerButton = (Button) findViewById(R.id.developer_details);
        developerButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.i(TAG, "User has chose Developer Activity from Welcome Activity");
                Intent developer = new Intent(WelcomeActivity.this, DeveloperActivity.class);
                startActivity(developer);
            }
        });

        // Create database reference to verify user has previously
        // left feedback so they can view it. Verifies if feedback text
        // exists in the database that is associated with the current user.
        viewFeedback = (Button) findViewById(R.id.view_feedback);
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("text")))
                {
                    viewFeedback.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            Log.i(TAG, "ViewFeedback Activity started from Welcome Activity");
                            Intent view = new Intent(WelcomeActivity.this, ViewFeedback.class);
                            startActivity(view);
                        }
                    });
                }
                else
                    {
                    viewFeedback.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            // Button is disabled and user is displayed a message until they do.
                            Toast.makeText(WelcomeActivity.this, "Please provide some feedback", Toast.LENGTH_SHORT).show();
                            viewFeedback.setEnabled(false);
                        }
                    });
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    /**
     * Method containing intent to send user back to Sign in activity
     * upon signing out.
     * Code adapted from https://developer.android.com/reference/android/content/Intent
     */
    private void sendUserToSignInActivity()
    {
        Intent signinIntent = new Intent(WelcomeActivity.this, SignInActivity.class);
        signinIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signinIntent);
    }
}
