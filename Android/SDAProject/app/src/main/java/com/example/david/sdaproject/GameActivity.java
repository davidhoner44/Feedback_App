package com.example.david.sdaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author David Honer
 * @version 1.0
 *
 * This class builds the RecyclerView and populates it with game
 * related information.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://stackoverflow.com/a/40584425
 * Permission: MIT licence
 * Retrieved on: 2nd April 2019
 */
public class GameActivity extends AppCompatActivity
{
    private static final String TAG = "GameActivity";
    private ArrayList<GameDetails> mGameDetails;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Launch the game activity containing RecyclerView.
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.i(TAG,"Game Activity has been launched");

        // EnableUp button
        getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Call both the Array data and RecyclerView
        createExampleList();
        buildRecyclerView();
    }

    /**
     * Data found in this method will populate the RecyclerView.
     * Code adapted from method onCreate() described at https://stackoverflow.com/a/40584425
     */
    public void createExampleList()
    {
        mGameDetails = new ArrayList<>();
        mGameDetails.add(new GameDetails(R.drawable.war, getResources().getString(R.string.game_one), getResources().getString(R.string.developer_one)));
        mGameDetails.add(new GameDetails(R.drawable.world, getResources().getString(R.string.game_two), getResources().getString(R.string.developer_two)));
        mGameDetails.add(new GameDetails(R.drawable.superhero, getResources().getString(R.string.game_three), getResources().getString(R.string.developer_three)));
        mGameDetails.add(new GameDetails(R.drawable.plumber, getResources().getString(R.string.game_four), getResources().getString(R.string.developer_four)));
        mGameDetails.add(new GameDetails(R.drawable.shooter, getResources().getString(R.string.game_five), getResources().getString(R.string.developer_five)));
    }

    /**
     * Method to set up the RecyclerView.
     * Code adapted from method onCreate() described at https://stackoverflow.com/a/40584425
     */
    public void buildRecyclerView()
    {
        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GameDetailsAdapter(mGameDetails);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
