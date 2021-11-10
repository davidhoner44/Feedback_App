/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.david.sdaproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author David Honer
 * @version 1.0
 *
 * This class presents a list of game developers and their information.
 * For each developer, their name, address and email is present.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://developer.android.com/reference/java/util/ArrayList.html
 * Permission: Apache licence
 * Retrieved on: 13th March 2019
 */
public class DeveloperActivity extends AppCompatActivity
{
    private static final String TAG = "DeveloperActivity";

    /**
     * This method is responsible for loading the Developer activity layout.
     * Code adapted from https://developer.android.com/reference/java/util/ArrayList.html
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        Log.i(TAG,"Developer Activity has been launched");

        // EnableUp button
        getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Create an ArrayList of developers
        final ArrayList<DeveloperDetails> developerDetails = new ArrayList<>();
        developerDetails.add(new DeveloperDetails(getResources().getString(R.string.developer_one),  getResources().getString(R.string.address_one)+
                getResources().getString(R.string.country_ireland), getResources().getString(R.string.developer_one_email)));
        developerDetails.add(new DeveloperDetails(getResources().getString(R.string.developer_two), getResources().getString(R.string.address_two),
                getResources().getString(R.string.developer_two_email)));
        developerDetails.add(new DeveloperDetails(getResources().getString(R.string.developer_three), getResources().getString(R.string.address_three),
                getResources().getString(R.string.developer_three_email)));
        developerDetails.add(new DeveloperDetails(getResources().getString(R.string.developer_four), getResources().getString(R.string.address_four),
                getResources().getString(R.string.developer_four_email)));
        developerDetails.add(new DeveloperDetails(getResources().getString(R.string.developer_five), getResources().getString(R.string.address_five),
                getResources().getString(R.string.developer_five_email)));

        // Create an {@link DeveloperDetailsAdapter}, whose data source is a list of
        // {@link DeveloperDetails}. The adapter knows how to create list item views for each item
        // in the list.
        DeveloperDetailsAdapter developerDetailsAdapter = new DeveloperDetailsAdapter(this, developerDetails);
        ListView listViewChange = (ListView) findViewById(R.id.list);
        listViewChange.setAdapter(developerDetailsAdapter);
    }
}
