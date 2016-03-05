package com.testing.alcatel.alufieldtesting;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.testing.alcatel.alufieldtesting.Testing;

public class SitesDropDownOnItemClickListener implements OnItemClickListener {

    String TAG = "SitesDropDownOnItemClickListener.java";
    public static String sitename;

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

        // get the context and main activity to access variables
        Context mContext = v.getContext();
        Testing testing = ((Testing) mContext);

        // add some animation when a list item was clicked
        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        v.startAnimation(fadeInAnimation);

        // dismiss the pop up
        testing.popupWindowSites.dismiss();

        // get the text and set it as the button text
        String selectedItemText = ((TextView) v).getText().toString();
        testing.sitename.setText(selectedItemText);
        sitename = testing.sitename.getText().toString();

       // get the id
        String selectedItemTag = ((TextView) v).getTag().toString();
        Toast.makeText(mContext, "Dog ID is: " + sitename, Toast.LENGTH_SHORT).show();

    }


}