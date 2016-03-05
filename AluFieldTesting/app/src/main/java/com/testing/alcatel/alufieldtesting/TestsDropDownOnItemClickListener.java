package com.testing.alcatel.alufieldtesting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestsDropDownOnItemClickListener implements OnItemClickListener {

    String TAG = "TestsDropDownOnItemClickListener.java";
    public static String test;
    public static String site;
    public static String dlFile;
    Testing testin = new Testing();

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
        testing.popupWindowTests.dismiss();

        // get the text and set it as the button text
        String selectedItemText = ((TextView) v).getText().toString();
        testing.typee.setText(selectedItemText);
        test = testing.typee.getText().toString();
       /// get the id
        //selectedItemTag = ((TextView) v).getTag().toString();

       // Toast.makeText(mContext, "Dog ID is: " + test, Toast.LENGTH_SHORT).show();

    }

    // public static void initListener(){
     //   Testing testin = new Testing();
   public static final View.OnClickListener onclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 final Context mContext = view.getContext();
                 final MyDBHelper db = new MyDBHelper(mContext);
                 final Testing testing = ((Testing) mContext);

                final String testname = testing.testnamee.getText().toString();
                final String comments = testing.commentes.getText().toString();

                final String number = testing.number;
                final int duration =  testing.duration;
                final int iteration =  testing.iteration;
                final String link = testing.link;
                final String dllink = testing.dllink;
                final String host =  testing.host;
                //final String apn = testing.apn;
                final String netType = testing.netType;

             if(test != null) {
                 if (test.equalsIgnoreCase("Stationary Test")) {

                     site = SitesDropDownOnItemClickListener.sitename;
                     String test = "stationary";

                     System.out.print("COMMENTS" + comments);
                     Info info = new Info(testname, site, test, comments, dllink, test, number,
                             duration, iteration, netType, link, host);
                     db.saveTestInfo(info);
                     Log.d("Tag Count", "Tag Count: " + db.getAllTestsInfo().size());

                     Toast.makeText(mContext, " Getting Site Location..." + test, Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(mContext, RFmeasurements.class);
                     intent.putExtra("makeCall", 0);
                     intent.putExtra("ftpul", 1);
                     intent.putExtra("tests", test);
                     mContext.startActivity(intent);

                 } else if (test.equalsIgnoreCase("Mobility")) {

                     site = SitesDropDownOnItemClickListener.sitename;

                     final CharSequence[] itemss = {"Idle Mode", "Dedicated Mode"};

                     AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                     builder.setTitle("Which mobility test do you want to make?");
                     builder.setItems(itemss, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int item) {


                             String testname = testing.testnamee.getText().toString();
                             String comments = testing.commentes.getText().toString();

                             if (item == 0) {
                                 String kind = "idle mode";
                                 Intent intent = new Intent(mContext, RFmeasurements.class);
                                 System.out.print("THE SITE NAMEEEEEEEEEEEEE IS" + site);

                                 Info info = new Info(testname, site, test, comments, dllink, kind, number,
                                         duration, iteration, netType, link, host);

                                 db.saveTestInfo(info);
                                 Log.d("Tag Count", "Tag Count: " + db.getAllTestsInfo().size());
                                 try {
                                     intent.putExtra("test", item);
                                     intent.putExtra("makeCall", 0);
                                     intent.putExtra("canSave", 1);
                                     intent.putExtra("ftpul", 0);
                                     intent.putExtra("tests", "mobility");
                                     Toast.makeText(mContext, " Getting Site Location..." + test, Toast.LENGTH_LONG).show();
                                     mContext.startActivity(intent);


                                 } catch (Exception e) {
                                     e.printStackTrace();
                                     Toast.makeText(mContext, " Fill in all the information about the test..." + test, Toast.LENGTH_LONG).show();
                                 }

                             } else if (item == 1) {
                                 try {

                                     Intent intent = new Intent(mContext, MobilityTests.class);
                                     String kind = "dedicated mode";
                                     intent.putExtra("testname", testname);
                                     intent.putExtra("comments", comments);
                                     intent.putExtra("sitename", testname);
                                     intent.putExtra("netType", netType);
                                     intent.putExtra("kind", kind);
                                     intent.putExtra("test", test);
                                     intent.putExtra("ftpul", 0);
                                     intent.putExtra("canSave", 1);
                                     intent.putExtra("makeCall", 1);
                                     intent.putExtra("tests", "mobility");
                                     mContext.startActivity(intent);

                                 } catch (Exception e) {

                                     e.printStackTrace();
                                     Toast.makeText(mContext, " Fill in all the information about the test..." + test, Toast.LENGTH_LONG).show();
                                 }
                             }

                             dialog.dismiss();

                         }
                     }).show();

                 } else {

                     //Intent intent = new Intent(mContext, Testing.class);
                     //mContext.startActivity(intent);
                     Toast.makeText(mContext, "Please choose a test from the options given ", Toast.LENGTH_SHORT).show();

                 }

             }else{

                 Toast.makeText(mContext, "Please choose a test from the options given ", Toast.LENGTH_SHORT).show();
             }
            }
        };
    //}
}