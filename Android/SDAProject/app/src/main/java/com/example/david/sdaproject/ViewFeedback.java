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

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
 * This class handles the users ability to view their
 * previous feedback on a chosen game. This activity is
 * contingent on whether they have given feedback previously.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://firebase.google.com/docs/database/android/read-and-write
 * Permission: Apache licence
 * Retrieved on: 14th April 2019
 * Url: https://stackoverflow.com/a/49252750
 * Permission: MIT Licence
 * Retrieved on: 14th April 2019
 */
public class ViewFeedback extends AppCompatActivity
{
    private static final String TAG = "FeedbackActivity";
    private TextView game;
    private TextView feedback;

    // FireBase instance variables
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    /**
     * Method to load the ViewFeedback activity. Declare FireBase authentication
     * and database reference. Initialise views in preparation for database
     * retrieval.
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        Log.i(TAG,"ViewFeedback Activity has been launched");

        // EnableUp button
        getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            currentUserID = mAuth.getCurrentUser().getUid();
        }

        // Get database reference
        RootRef = FirebaseDatabase.getInstance().getReference();

        feedback = (TextView) findViewById(R.id.feedback_text);
        game = (TextView) findViewById(R.id.game_title);

        // Call method to retrieve feedback upon opening activity
        RetrieveFeedback();
    }

    /**
     * Invoked when the activity starts, this method checks for the current user,
     * and returns their previous feedback, but only if they done so previously.
     * Code adapted from https://firebase.google.com/docs/database/android/read-and-write
     * Code adapted from method onDataChange() described at https://stackoverflow.com/a/49252750
     */
    private void RetrieveFeedback()
    {
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("text")) && (dataSnapshot.hasChild("game")))
                {
                    // Variables declared to store feedback and game title from database
                    String suggestion = dataSnapshot.child("text").getValue().toString();
                    String gameReturn = dataSnapshot.child("game").getValue().toString();

                    // Set the resulting data to textViews
                    feedback.setText(suggestion);
                    game.setText(gameReturn);
                }
                else
                {
                    Toast.makeText(ViewFeedback.this, "Please provide feedback first", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
}
