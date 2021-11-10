package com.example.david.sdaproject;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author David Honer
 * @version 1.0
 *
 * {@link DeveloperDetailsAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link DeveloperDetails} objects.
 *
 * Citation:
 * Class contains code adapted from
 * URL: https://stackoverflow.com/a/8166802
 * Permission: MIT Licence
 * Retrieved on: 14th March 2019
 * Created by David Honer
 */
public class DeveloperDetailsAdapter extends ArrayAdapter<DeveloperDetails>
{
    private static final String TAG = "DeveloperDetailsAdapter";

    /**
     * A custom constructor. The context is used to launch the layout file,
     * and the list is the data we want to populate into the lists.
     * Code adapted from method ListAdapter() described at https://stackoverflow.com/a/8166802
     * @param context The current context.
     * @param DeveloperDetails A list of DeveloperDetail objects to display in a list.
     */
    public DeveloperDetailsAdapter(Activity context, ArrayList<DeveloperDetails> DeveloperDetails)
    {
        super(context, 0, DeveloperDetails);
    }

    /**
     * Provides a view for an AdapterView.
     * Code adapted from method getView() described at https://stackoverflow.com/a/8166802
     * @param position Position in the list of data.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_developer_details, parent, false);
        }

        // Get the {@link DeveloperDetails} object located at this position in the list
        DeveloperDetails currentDeveloperDetail = getItem(position);

        Log.i(TAG,"Developer detail object loaded");

        // Find the TextView in the activity_developer_details.xml layout with the ID developer_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.developer_name);
        // Get the developer name and set this text to the TextView
        nameTextView.setText(currentDeveloperDetail.getDeveloperName());

        // Find the TextView in the activity_developer_details.xml layout with the ID developer_address
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.developer_address);
        // Get the developers address and set it to the TextView
        addressTextView.setText(currentDeveloperDetail.getDeveloperAddress());

        // Find the TextView in the activity_developer_details.xml layout with the ID developer_email
        TextView emailTextView = (TextView) listItemView.findViewById(R.id.developer_email);
        // Get the developers email and set it to the TextView
        emailTextView.setText(currentDeveloperDetail.getDeveloperEmail());

        // Return the whole list item layout
        return listItemView;
    }
}
