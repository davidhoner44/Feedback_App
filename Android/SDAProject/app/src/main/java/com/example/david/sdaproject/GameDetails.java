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
 * {@link GameDetails} represents a single game entry.
 * Each object has 3 properties: an image, a name, and developers name.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * Permission: Apache licence
 * Retrieved on: 31st March 2019
 */
public class GameDetails
{
    // Image resource
    private int mImageResource;

    // Name of game
    private String mGameName;

    // Name of developer
    private String mGameDeveloper;

    /**
     * Create a new Game object
     * Code adapted from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * @param imageResource games image
     * @param name name of game
     * @param developer name of games developer
     */
    public GameDetails(int imageResource, String name, String developer)
    {
        mImageResource = imageResource;
        mGameName = name;
        mGameDeveloper = developer;
    }

    /** Get the games image */
    public int getImageResource()
    {
        return mImageResource;
    }

    /** Get the name of the game */
    public String getText1()
    {
        return mGameName;
    }

    /** Get the name of the games developer */
    public String getText2()
    {
        return mGameDeveloper;
    }
}
