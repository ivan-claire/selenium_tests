package com.testing.alcatel.alufieldtesting;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * <h1>Performing CS Voice Call tests</h1>
 * Clicking on CheckBox moc should begin making a call
 * When call is over PhoneStateBReceiver is listening to
 *change of phone state from IDLE to ACTIVE  and the duration
 *  and number of the call are gotten and displayed using mocReults
 *  and mtcResults text views
 * <p>
 * <b>MainActivty Class:</b> Home screen to display the different options on a  gridView
 * that can be chosen to go to different parts of the application
 *
 * @author  Ngong Ivan-Clare Wirdze
 * @version 1.0
 * @since   08/02/2016.
 */

public class CsVoiceCalls extends AppCompatActivity {

    CheckBox net;
    public CheckBox ftpul;
    public CheckBox ftpdl;
    CheckBox ping;
    CheckBox moc;
    CheckBox mtc;
    CheckBox sms;

    public LinearLayout lay5;
    public LinearLayout lay6;
    public LinearLayout lay7;
    public LinearLayout lay8;
    public LinearLayout lay9;
    public LinearLayout lay10;

    public TextView mocResults;
    public TextView mtcResults;
    public ProgressBar mtcPb;
    public static TextView size;

    /*********
     * work only for Dedicated IP
     ***********/
    static String FTP_HOST = "";
    /*********
     * FTP USERNAME
     ***********/
    static String FTP_USER = "";//"alu_dt1";

    /*********
     * FTP PASSWORD
     ***********/
    static String FTP_PASS = "";//"alu_dt123";

    public ProgressBar progressBar;
    //public int percent = 0;
    private TextView textView;
    private Handler handler = new Handler();
    long duration;
    public String PATH = "/home/alu_dt1/films/";
    public FTPClient ftpClient;
    public TextView bytes;
    public ProgressBar pb;
    private Handler mHandler;
    BufferedInputStream buffIn = null;

    ArrayList<Double> bytes_read;//save the amount of bytes read
    // at all times and saving the peak value in the databas
    ArrayList<Double> time_taken;
    File file;
    File f;
    String fourGNumber = ""/*"674744751"*/;
    String threeGNumber = "";
    String twoGNumber = ""/*"674744751"*/;
    String fixedNumber = ""/*"674744751"*/;
    String smsBody = "This is an SMS sent from ALUFIELDTESTING field force team after integration!";
    String SMS_SENT = "SMS_SENT";
    String SMS_DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPendingIntent;
    PendingIntent deliveredPendingIntent;
    checkNet checkNNet;
    DecimalFormat df;

    public String pingResult = "";
    public double uupeakRate;
    public double ddpeakRate;
    public double ddataSize = 6033190; // to be changed if upload file size changes
    public double udataSize = 10027008; // to be changed if upload file size changes
    public double dspeeds = 0.0;
    public double uspeeds = 0.0;
    public Boolean dSuccess = false;
    public Boolean uSuccess = false;
    public Boolean pingSuccess = false;
    public Boolean smsSuccess = false;
    MyDBHelper db;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    PhoneStateReceiver phoneState;
    public String phNumber;
    public String callDuration;
    public Boolean mocSuccess;
    public Boolean mtcSuccess;
    private PhoneStateBReceiver callReceiver = null;
    static String mocResult = "";
    static String mtcResult = "";
    public int callType = 0;
    public String typeOfCall;
    public String psc;
    public int strength;
    public int lac;
    public String mnc;
    public String mcc;
    public int cid;
    public int test_id;
    int clickcount = 0;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvoice_calls);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        checkNNet = new checkNet();
        db = new MyDBHelper(this);

        net = (CheckBox) findViewById(R.id.check);
        ftpul = (CheckBox) findViewById(R.id.ftpul);
        ftpdl = (CheckBox) findViewById(R.id.ftpdl);
        ping = (CheckBox) findViewById(R.id.ping);
        moc = (CheckBox) findViewById(R.id.moc);
        mtc = (CheckBox) findViewById(R.id.mtc);
        sms = (CheckBox) findViewById(R.id.sms);

        bytes_read = new ArrayList<Double>();
        time_taken = new ArrayList<Double>();
        sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

        psc = getIntent().getExtras().getString("psc");
        strength = getIntent().getExtras().getInt("strength");
        lac = getIntent().getExtras().getInt("lac");
        mnc = getIntent().getExtras().getString("mnc");
        mcc = getIntent().getExtras().getString("mcc");
        cid = getIntent().getExtras().getInt("cid");
        test_id = getIntent().getExtras().getInt("test_id");


        try {

            FTP_HOST = db.getProps().getFtplink();
            FTP_PASS = db.getProps().getFtppass();
            FTP_USER = db.getProps().getFtpuser();
            threeGNumber = db.getProps().getThreenumber();
            fourGNumber = db.getProps().getFournumber();
            twoGNumber = db.getProps().getTwonumber();
            fixedNumber = db.getProps().getFixednumber();

        } catch (Exception e) {
            e.printStackTrace();

        }

        lay5 = (LinearLayout) findViewById(R.id.layout5);
        lay6 = (LinearLayout) findViewById(R.id.layout6);
        lay7 = (LinearLayout) findViewById(R.id.layout7);
        lay8 = (LinearLayout) findViewById(R.id.layout8);
        lay9 = (LinearLayout) findViewById(R.id.layout9);
        lay10 = (LinearLayout) findViewById(R.id.layout10);

        mocResults = (TextView) findViewById(R.id.moc_results);
        mtcResults = (TextView) findViewById(R.id.mtc_results);
        mtcPb = (ProgressBar) findViewById(R.id.mtcprogressBar);

        // path to file to be uploaded
        f = new File(getCacheDir() + "/toBeUploaded.jpg");

        if (!f.exists()) try {

            InputStream is = getAssets().open("toBeUploaded.jpg");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final String FULL_PATH_TO_LOCAL_FILE = f.getPath();
        // calling broadcast receiver for listening to phone state
        callReceiver = new PhoneStateBReceiver();
        callReceiver.setCsVoiceCallsHandler(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callReceiver, filter);

        // begin the test
        beginTest();

        //button to be clicked to choose between viewing report or emailing report
        FloatingActionButton fab_btn = (FloatingActionButton)findViewById(R.id.fabv);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String filelocation="/mnt/sdcard/contacts_sid.vcf";
                String filename = "Stationary Test Report.xls";
                final File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                final Uri path = Uri.fromFile(filelocation);
                final CharSequence[] itemss = {"View Report", "Email Report"};

                AlertDialog.Builder builder = new AlertDialog.Builder(CsVoiceCalls.this);
                builder.setTitle("Choose the action do you want to perform?");
                builder.setItems(itemss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "application/vnd.ms-excel");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(getApplicationContext(), "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
                            }

                        } else if (item == 1) {

                            //f = new File(getCacheDir() + "/toBeUploaded.jpg");
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            // set the type to 'email'
                            emailIntent.setType("vnd.android.cursor.dir/email");
                            String to[] = {"ngongivan@gmail.com"};
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                            // the attachment
                            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                            // the mail subject
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                            startActivity(Intent.createChooser(emailIntent, "Send email..."));

                        }

                        dialog.dismiss();

                    }
                }).show();
            }
        });

    }

    /**
     * Method to be called for starting test.
     * When moc is clicked, alert dialog appears to choose between 4G, 3G , 2G and fixed line
     */
    //@param clickcount used to show and hide visibility of moc results
    //@param callType used to know the kind of call that wmade, used in displaying results
    public void beginTest(){

        moc.setVisibility(View.VISIBLE);
        mocResults.setVisibility(View.VISIBLE);
        moc.setBackgroundColor(getResources().getColor(R.color.green));
        //smsResults.setVisibility(View.VISIBLE);

        moc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount = clickcount + 1;

                final CharSequence[] itemss = new CharSequence[]{"To 4G", "To 3G", "To 2G", "To Fixed Line"};

                AlertDialog.Builder builder = new AlertDialog.Builder(CsVoiceCalls.this);
                builder.setTitle("Choose number to CALL");
                builder.setItems(itemss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                    // if the first item is chosen and the number to call is not null then
                    // call intent should begin and call Type is 0
                        if (item == 0 && fourGNumber != "") {
                            if (ContextCompat.checkSelfPermission(CsVoiceCalls.this,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(CsVoiceCalls.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + fourGNumber));
                            startActivity(callIntent);
                            callType = 0;

                        } else if (item == 1 && threeGNumber != "") {
                            if (ContextCompat.checkSelfPermission(CsVoiceCalls.this,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(CsVoiceCalls.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + threeGNumber/*6747447518*/));
                            startActivity(callIntent);
                            callType = 1;

                        } else if (item == 2 && twoGNumber != "") {
                            if (ContextCompat.checkSelfPermission(CsVoiceCalls.this,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(CsVoiceCalls.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + twoGNumber));
                            startActivity(callIntent);
                            callType = 2;
                        } else if (item == 3 && fixedNumber != "") {
                            if (ContextCompat.checkSelfPermission(CsVoiceCalls.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(CsVoiceCalls.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + fixedNumber/*6747447518*/));
                            startActivity(callIntent);
                            callType = 3;
                        } else {

                            Toast.makeText(getApplicationContext(), "No Number to CALL. Set number to call", Toast.LENGTH_LONG);
                        }
                        dialog.dismiss();

                    }
                }).show();


                mtc.setVisibility(View.VISIBLE);

            }
        });
        // mtc appears immediately after and
        mtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtc.setBackgroundColor(getResources().getColor(R.color.green));
                clickcount = clickcount + 1;

                final CharSequence[] itemss = new CharSequence[]{"To 4G", "To 3G", "To 2G", "To Fixed Line"};

                AlertDialog.Builder builder = new AlertDialog.Builder(CsVoiceCalls.this);
                builder.setTitle("Choose network to receive call from.");
                builder.setItems(itemss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {

                            mtcResults.setVisibility(View.VISIBLE);
                            mtcPb.setVisibility(View.VISIBLE);
                            callType = 0;

                        } else if (item == 1 ) {

                            mtcResults.setVisibility(View.VISIBLE);
                            mtcPb.setVisibility(View.VISIBLE);
                            callType = 1;

                        } else if (item == 2 ) {

                            mtcResults.setVisibility(View.VISIBLE);
                            mtcPb.setVisibility(View.VISIBLE);
                            callType = 2;

                        } else if (item == 3 ) {

                            mtcResults.setVisibility(View.VISIBLE);
                            mtcPb.setVisibility(View.VISIBLE);
                            callType = 3;

                        } else {

                            Toast.makeText(getApplicationContext(), "No Number to CALL. Set number to call", Toast.LENGTH_LONG);
                        }
                        dialog.dismiss();

                    }

                }).show();


                // mtc.setVisibility(View.VISIBLE);

            }
        });
    }

    /**
     * Method to be called to get the last outgoing call detail
     * and display the result on the screen. This method is called
     * in the phoneStateBReceiver class when ever the phone state
     * changes from IDLE to ACTIVE mode
     */
    public void lastCall(int callType) {
        System.err.print("\n CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK" +
                "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK INCOMMING CAAAAAAAAAALLLLLLL");

        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("#.##");
        Uri contacts = CallLog.Calls.CONTENT_URI;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null,

                android.provider.CallLog.Calls.DATE + " DESC limit 1;");
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration1 = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        if (managedCursor.moveToFirst() == true) {
            phNumber = managedCursor.getString(number);
            callDuration = managedCursor.getString(duration1);
            String dir = null;
            sb.append("\nPhone Number:--- " + phNumber + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
            Log.i("*****Call Summary******", "Call Duration is:-------" + sb);
        }

        managedCursor.close();

        Toast.makeText(getApplicationContext(), "OUTCOMING CALL TO REPORT", Toast.LENGTH_LONG);
        if(callType == 0){
            typeOfCall = "4G";
        }else if(callType == 1){
            typeOfCall = "3G";
        }else if(callType == 2){
            typeOfCall = "2G";
        }else if(callType == 3){
            typeOfCall = "Fixed Line";
        }

        if (Integer.parseInt(callDuration) < 10) {

            mocSuccess = false;
            mocResult += "\n"+ typeOfCall+ ",\n---------------------------------------------,\n Phone Number:--- " + phNumber +
                    ",\n Call Duration in sec:--- " + callDuration + ",\n RESULT:---FAILED";
            mocResults.setText(mocResult);

            Idle idleTest = new Idle(psc, strength, lac, mcc, mnc, cid, test_id,
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),String.valueOf(df.format(udataSize)),uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)),String.valueOf(df.format(ddataSize)),dSuccess.toString(),
                    pingResult, pingSuccess.toString(), smsSuccess.toString(),mocResult,mtcResult,"");

            if(clickcount==1)
            {

                db.saveIdleTest(idleTest);
            }
            else
            {
                db.updateIdleTest(idleTest);

            }

        } else {
            mocSuccess = true;
            mocResult += "\n\n"+ typeOfCall+ ",\n---------------------------------------------,\n Phone Number:--- " + phNumber +
                    ",\n Call Duration in sec:--- " + callDuration + ",\n RESULT:---SUCCESFUL"+clickcount;

            Idle idleTest = new Idle(psc, strength, lac, mcc, mnc, cid, test_id,
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),String.valueOf(df.format(udataSize)),uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)),String.valueOf(df.format(ddataSize)),dSuccess.toString(),
                    pingResult, pingSuccess.toString(), smsSuccess.toString(),mocResult,mtcResult,"");

            if(clickcount==1)
            {
                db.saveIdleTest(idleTest);
            }
            else
            {
                db.updateIdleTest(idleTest);
            }
        }

    }

    /**
     * Method to be called to get the last incoming call detail
     * and display the result on the screen. This method is called
     * in the phoneStateBReceiver class when ever the phone state
     * changes from IDLE to ACTIVE mode
     */
    public void lastInCall(int callType) {

        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("#.##");
        Uri contacts = CallLog.Calls.CONTENT_URI;
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null,

                android.provider.CallLog.Calls.DATE + " DESC limit 1;");
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration1 = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        if (managedCursor.moveToFirst() == true) {
            phNumber = managedCursor.getString(number);
            callDuration = managedCursor.getString(duration1);
            String dir = null;
            sb.append("\nPhone Number:--- " + phNumber + "\nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
            Log.i("*****Call Summary******", "Call Duration is:-------" + sb);
        }

        managedCursor.close();

        if(callType == 0){
            typeOfCall = "4G";
        }else if(callType == 1){
            typeOfCall = "3G";
        }else if(callType == 2){
            typeOfCall = "2G";
        }else if(callType == 3){
            typeOfCall = "Fixed Line";
        }

        Toast.makeText(getApplicationContext(), "INCOMING CALL TO REPORT", Toast.LENGTH_LONG);
        if (Integer.parseInt(callDuration) < 10) {

            mtcPb.setVisibility(View.GONE);
            mtcSuccess = false;
            mtcResult += "\n\n"+ typeOfCall+ " ,\n---------------------------------------------,\n Phone Number:--- " + phNumber +
                    ",\n Call Duration in sec:--- " + callDuration + ",\n RESULT:---FAILED";
            mtcResults.setText(mtcResult);

            Idle idleTest = new Idle(psc, strength, lac, mcc, mnc, cid, test_id,
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),String.valueOf(df.format(udataSize)),uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)),String.valueOf(df.format(ddataSize)),dSuccess.toString(),
                    pingResult, pingSuccess.toString(), smsSuccess.toString(),mocResult,mtcResult,"");

            if(clickcount==1)
            {

                db.saveIdleTest(idleTest);
            }
            else
            {
                db.updateIdleTest(idleTest);

            }


        } else {

            mtcPb.setVisibility(View.GONE);
            mtcSuccess = false;
            mtcResult += "\n\n"+ typeOfCall+ ",\n ---------------------------------------------,\n Phone Number:--- " + phNumber +
                    ",\n Call Duration in sec:--- " + callDuration + ",\n RESULT:---SUCCESSFUL";
            mtcResults.setText(mtcResult);

            Idle idleTest = new Idle(psc, strength, lac, mcc, mnc, cid, test_id,
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),String.valueOf(df.format(udataSize)),uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)),String.valueOf(df.format(ddataSize)),dSuccess.toString(),
                    pingResult, pingSuccess.toString(), smsSuccess.toString(),mocResult,mtcResult,"");

            if(clickcount==1)
            {

                db.saveIdleTest(idleTest);
            }
            else
            {
                db.updateIdleTest(idleTest);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    /**
     * Method to be called to get the last outgoing call detail
     * and display the result on the screen. This method is called
     * in the phoneStateBReceiver class when ever the phone state
     * changes from IDLE to ACTIVE mode
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        String message = "";

        if (item.getItemId() == R.id.done) {

            new Reportings().execute();
        }
        else {
            message = "Why would you select that!?";
        }
        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    private class Reportings extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(CsVoiceCalls.this);

            dialog.setMessage("Generating Report. Please Wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                Report report = new Report(CsVoiceCalls.this);

                report.writeStationary(getApplicationContext());

            }catch (Exception e){
                e.printStackTrace();
            }
            return  "";
        }

        @Override
        protected void onPostExecute(String params) {
            dialog.dismiss();
            //result
        }
    }

    /**
     * method to choose to go to homescreen or repeat test
     */
    public void onBackPressed() {

        final CharSequence[] itemss = {"Repeat Test", "Go to HomeScreen"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CsVoiceCalls.this);
        builder.setTitle("Choose the action do you want to perform?");
        builder.setItems(itemss, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {

                    Intent intent = new Intent(getApplicationContext(), RFmeasurements.class);
                    intent.putExtra("tests", "stationary");
                    startActivity(intent);

                } else if (item == 1) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //Bundle is optional
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    //end Bundle
                    startActivity(intent);
                }

                dialog.dismiss();

            }
        }).show();
    }

}

