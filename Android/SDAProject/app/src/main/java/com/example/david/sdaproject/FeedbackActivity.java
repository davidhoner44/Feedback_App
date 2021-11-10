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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * @author David Honer
 * @version 1.0
 *
 * This class handles feedback provided by the user and submits
 * it to the Firebase Realtime database.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://firebase.google.com/docs/database/android/read-and-write
 * URL: https://developer.android.com/reference/android/os/Bundle#summary
 * Permission: Apache licence
 * Retrieved on: 1st April 2019
 */
public class FeedbackActivity extends AppCompatActivity
{
    private static final String TAG = "FeedbackActivity";
    String recommend;

    // FireBase instance variables
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String getPlatform;
    private String getGame;
    private String getRecommendation;
    private String getGenre;
    private String message;

    /**
     * Method to load the feedback activity. Declare FireBase authentication
     * and database reference.
     * Code adapted from https://firebase.google.com/docs/database/android/read-and-write
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Log.i(TAG,"Feedback Activity has been launched");

        // EnableUp button
        getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Keep keyboard hidden until user clicks EditText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            currentUserID = mAuth.getCurrentUser().getUid();
        }

        // Get database reference
        RootRef = FirebaseDatabase.getInstance().getReference();

        // Initialise submit feedback button
        Button sendFeedback = (Button) findViewById(R.id.submit_feedback);
        sendFeedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SubmitFeedback();
            }
        });
    }

    /**
     *  This method collates the users input and inserts it into FireBase
     *  Code adapted from https://firebase.google.com/docs/database/android/read-and-write
     *  Code adapted from https://developer.android.com/reference/android/os/Bundle#summary
     */
    private void SubmitFeedback()
    {
        Log.i(TAG,"Feedback button has been pressed");

        // Initialise EditText for user feedback
        EditText suggestion = (EditText) findViewById(R.id.feedback);
        String getFeedback = suggestion.getText().toString();

        // Retrieve user data from other activities
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            getPlatform = bundle.getString("platform");
            getGame = bundle.getString("game");
            getRecommendation = bundle.getString("recommend");
            getGenre = bundle.getString("genre");
        }

        // If the suggestion/feedback field is empty, then display a message to the user. If
        // it is not empty, then create HashMap and pass variables into it.
        if (TextUtils.isEmpty(getFeedback))
        {
            Toast.makeText(this, "Please enter some feedback", Toast.LENGTH_SHORT).show();
        }
        // Create instance of database and find current logged in user.
        // Set the completed HashMap into the specific Firebase fields.
        else
            {
            HashMap<String, String> feedback = new HashMap<>();
            feedback.put("uid", currentUserID);
            feedback.put("text", getFeedback);
            feedback.put("platform", getPlatform);
            feedback.put("game", getGame);
            feedback.put("recommend", getRecommendation);
            feedback.put("genre", getGenre);

            RootRef.child("Users").child(currentUserID).setValue(feedback).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    Log.d(TAG, "addedToDatabase:onComplete:" + task.isSuccessful());

                    // If the data is added to the database successfully, send the user to the welcome activity
                    // and display a message. If the data is unsuccessful, display a message and capture exception.
                    if (task.isSuccessful())
                    {
                        Log.i(TAG,"User has provided feedback");
                        sendUserToWelcomeActivity();
                        Toast.makeText(FeedbackActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            Log.w(TAG, "addedToDatabase", task.getException());
                            if (task.getException() != null){
                                 message = task.getException().toString();
                            }
                            Toast.makeText(FeedbackActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                }
            });
            }
    }

    /** Method containing intent to send user back to welcome activity
     *  Code adapted from https://developer.android.com/reference/android/content/Intent
     * */
    private void sendUserToWelcomeActivity()
    {
        Intent mainIntent = new Intent(FeedbackActivity.this, WelcomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}
