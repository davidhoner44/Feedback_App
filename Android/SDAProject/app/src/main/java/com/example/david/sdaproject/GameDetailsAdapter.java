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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author David Honer
 * @version 1.0
 *
 * {@link DeveloperDetailsAdapter} is an adapter that can provide the layout for each list
 * based on a data source, which is a list of {@link GameDetails} objects.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://stackoverflow.com/a/40584425
 * Permission: MIT Licence
 * Retrieved on: 20th April 2019
 * URL: https://guides.codepath.com/android/Using-the-RecyclerView
 * Permission: Apache Licence
 * Retrieved on: 20th April 2019
 */
public class GameDetailsAdapter extends RecyclerView.Adapter<GameDetailsAdapter.GameViewHolder>
{
    private ArrayList<GameDetails> mGame;
    private OnItemClickListener mListener;

    /**
     * Parent activity will implement this method in response to click events
     * Code adapted from method ItemClickListener() described at https://stackoverflow.com/a/40584425
     */
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    /**
     * All for click events to be caught
     * Code adapted from method setClickListener() described at https://stackoverflow.com/a/40584425
     */
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    /**
     * Creates a direct reference to each of the views within a data item
     * Code adapted from method ViewHolder() described at https://stackoverflow.com/a/40584425
     * Code adapted from https://guides.codepath.com/android/Using-the-RecyclerView
     */
    public static class GameViewHolder extends RecyclerView.ViewHolder
    {
        // Variables for any view that will be set as a row is rendered
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout relativeLayout;

        // Constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public GameViewHolder(final View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            mImageView = itemView.findViewById(R.id.game_image);
            mTextView1 = itemView.findViewById(R.id.game_line1);
            mTextView2 = itemView.findViewById(R.id.game_line2);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    /**
     * Data is passed into the constructor
     * Code adapted from method MyRecyclerView() described at https://stackoverflow.com/a/40584425
     */
    public GameDetailsAdapter(ArrayList<GameDetails> exampleGame)
    {
        mGame = exampleGame;
    }

    /**
     * Inflate the row layout from appropriate xml
     * Code adapted from method onCreateViewHolder() described at https://stackoverflow.com/a/40584425
     */
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_details, parent, false);
        GameViewHolder evh = new GameViewHolder(v, mListener);
        return evh;
    }

    /**
     * Retrieves data from GameDetails and binds it to
     * appropriate fields in each row. Captures users selected
     * game and starts the next activity when a choice has been made.
     * Code adapted from method onBindViewHolder() described at https://stackoverflow.com/a/40584425
     * Code adapted from https://guides.codepath.com/android/Using-the-RecyclerView
     */
    @Override
    public void onBindViewHolder(GameViewHolder holder, final int position)
    {
        final GameDetails currentItem = mGame.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String selectedGame = currentItem.getText1();
                Intent intent = new Intent(view.getContext(), QuestionActivity.class);
                intent.putExtra("game", selectedGame);
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Get the total number of rows
     * Code adapted from method getItemCount() described at https://stackoverflow.com/a/40584425
     */
    @Override
    public int getItemCount()
    {
        return mGame.size();
    }
}
