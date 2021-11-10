/*
 * Copyright 2013 The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.david.sdaproject;

/**
 * @author David Honer
 * @version 1.0
 *
 * {@link DeveloperDetails} represents a single game developer.
 * Each object has 3 properties: name, address, and email.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * Permission: Apache licence
 * Retrieved on: 13th March 2019
 * Created by David Honer
 */
public class DeveloperDetails
{
    // Name of developer
    private String mDeveloperName;

    // Address of developer
    private String mDeveloperAddress;

    // Developer email
    private String  mDeveloperEmail;

    /**
     * Create a new DeveloperDetails object.
     * Code adapted from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * @param name name of the developer
     * @param address address of developer
     * @param email email of the developer
     */
    public DeveloperDetails(String  name, String  address, String  email)
    {
        mDeveloperName = name;
        mDeveloperAddress = address;
        mDeveloperEmail = email;
    }

    /** Get the name of the developer */
    public String getDeveloperName()
    {
        return mDeveloperName;
    }

    /** Get the address of the developer */
    public String getDeveloperAddress()
    {
        return mDeveloperAddress;
    }

    /** Get the email of the developer */
    public String getDeveloperEmail()
    {
        return mDeveloperEmail;
    }
}
