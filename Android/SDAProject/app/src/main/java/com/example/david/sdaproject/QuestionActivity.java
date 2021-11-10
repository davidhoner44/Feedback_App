package com.example.david.sdaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * @author David Honer
 * @version 1.0
 *
 * This activity handles multiple radio button question. The first question
 * is a mandatory question.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://stackoverflow.com/a/34412111
 * URL: https://stackoverflow.com/a/21203563
 * Permission: MIT licence
 * Retrieved on: 4th April 2019
 */
public class QuestionActivity extends AppCompatActivity
{
    private static final String TAG = "QuestionActivity";

    // Variables to store users selections
    private String recommend;
    private String platform;
    private String genre;

    // RadioGroup and RadioButton variables
    private RadioGroup r_g_a;
    private RadioGroup r_g_b;
    private RadioGroup r_g_c;
    private RadioButton r_b_a;
    private RadioButton r_b_b;
    private RadioButton r_b_c;

    private String receiveGame;

    /**
     * Method to load the Question Activity.
     * Code adapted from https://stackoverflow.com/a/34412111
     * Code adapted from https://stackoverflow.com/a/21203563
     * @param savedInstanceState Bundle containing state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Log.i(TAG,"Question Activity has been launched");

        // EnableUp button
        getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve the users game selection from previous activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            receiveGame = bundle.getString("game");
        }

        // RadioGroups to activity_question.xml views

        r_g_a = (RadioGroup) findViewById(R.id.group1);
        r_g_b = (RadioGroup) findViewById(R.id.group2);
        r_g_c = (RadioGroup) findViewById(R.id.group3);

        r_g_a.setOnCheckedChangeListener
                (new RadioGroup.OnCheckedChangeListener()
                 {
                     @Override
                     public void onCheckedChanged(RadioGroup group, int checkedId)
                     {
                         r_b_a = (RadioButton) findViewById(checkedId);
                         recommend = r_b_a.getText().toString();
                     }
                 }
                );

        r_g_b.setOnCheckedChangeListener
                (new RadioGroup.OnCheckedChangeListener()
                 {
                     @Override
                     public void onCheckedChanged(RadioGroup group, int checkedId)
                     {
                         r_b_b = (RadioButton) findViewById(checkedId);
                         platform = r_b_b.getText().toString();
                     }
                 }
                );

        r_g_c.setOnCheckedChangeListener
                (new RadioGroup.OnCheckedChangeListener()
                 {
                     @Override
                     public void onCheckedChanged(RadioGroup group, int checkedId)
                     {
                         r_b_c = (RadioButton) findViewById(checkedId);
                         genre = r_b_c.getText().toString();
                     }
                 }
                );

        // Button to collate and send the users selections to the feedbackActivity.
        // This is only true if the first mandatory question is answered.
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(r_g_a.getCheckedRadioButtonId() != -1)
                {
                    Intent pass = new Intent(QuestionActivity.this, FeedbackActivity.class);
                    pass.putExtra("game", receiveGame);
                    pass.putExtra("recommend", recommend);
                    pass.putExtra("platform", platform);
                    pass.putExtra("genre", genre);
                    startActivity(pass);
                    Log.i(TAG,"Question have been answered");
                }
                else
                    {
                    Toast.makeText(QuestionActivity.this, "Please answer the mandatory question", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
