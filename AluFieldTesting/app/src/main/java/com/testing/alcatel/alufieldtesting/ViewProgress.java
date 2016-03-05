package com.testing.alcatel.alufieldtesting;

import android.Manifest;
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
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;

public class ViewProgress extends AppCompatActivity {

    CheckBox net;
    public CheckBox ftpul;
    public CheckBox ftpdl;
    CheckBox ping;
    CheckBox moc;
    CheckBox mtc;
    CheckBox sms;
    public LinearLayout lay1;
    public LinearLayout lay2;
    public LinearLayout lay3;
    public LinearLayout lay4;
    public LinearLayout lay5;
    public LinearLayout lay6;
    public LinearLayout lay7;
    public LinearLayout lay8;
    public LinearLayout lay9;
    public LinearLayout lay10;

    public TextView dnumBytes;
    public TextView davgRate;
    public TextView dpeakRate;
    public ProgressBar dpb;
    public TextView dnumBytes_text;
    public TextView davgRate_text;
    public TextView dpeakRate_text;
    public TextView dprogress_text;
    public TextView dtime;
    public TextView dtime_text;


    public TextView unumBytes;
    public TextView uavgRate;
    public TextView upeakRate;
    public ProgressBar upb;
    public TextView unumBytes_text;
    public TextView uavgRate_text;
    public TextView upeakRate_text;
    public TextView uprogress_text;
    public TextView utime;
    public TextView utime_text;

    public TextView pingResults;
    public TextView smsResults;
    public TextView mocResults;
    public TextView mtcResults;
    public ProgressBar mtcPb;

    public String inNumber;
    public Date inStart;
    public Date inEnd;
    public long inDuration;

    public int sizes = 0;
    public long byteSizes = 0;

    public static TextView size;
    TextView error;
    double max;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    Handler tick_Handler;
    MyThread tick_thread;

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
    public FTPClient ftpClient;
    public TextView bytes;
    public ProgressBar pb;
    private Handler mHandler;
    BufferedInputStream buffIn = null;
    long sizess = (long) 3.3 * (1024 ^ 3);
    boolean result = false;
    int MAX_ATTEMPTS = 1;
    long writtenBytes;
    long endTime;
    long startTime;
    long takenTime;
    long transferStart; //getting time when bytes were read
    long transferEnd; // getting time when they stopped and
    // saved in array list to be used to cal peakrate
    int percent;
    long fileSize;
    double sum;
    ArrayList<Double> bytes_read;//save the amount of bytes read
    // at all times and saving the peak value in the databas
    ArrayList<Double> time_taken;
    ArrayList<Double> bytes_persec;
    int counter = 0;
    int ucounter = 0;
    int pcounter = 0;
    int scounter = 0;
    File downloadedFile;
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
    public double ddataSize = 0; // to be changed if upload file size changes
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
    private PhoneStateBroadcastReceiver callReceiver = null;
    static String mocResult = "";
    static String mtcResult = "";
    public int callType = 0;
    public String typeOfCall;
    public String psc;
    public int strength;
    public int lac ;
    public String mnc;
    public String mcc;
    public int cid;
    public int test_id;
    int clickcount=0;
    String filePath;
    int utransfBytes = 0;
    Boolean smsDelivered = false;
    int transfBytes = 0;
    //private CStateListener phoneStateListener = new MyPhoneStateListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress);

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
        bytes_persec = new ArrayList<Double>();

        sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
        tick_Handler = new Handler();
        tick_thread = new MyThread();

        try {

            FTP_HOST = db.getProps().getFtplink();
            FTP_PASS = db.getProps().getFtppass();
            FTP_USER = db.getProps().getFtpuser();
            threeGNumber = db.getProps().getThreenumber();
            fourGNumber = db.getProps().getFournumber();
            twoGNumber = db.getProps().getTwonumber();
            fixedNumber = db.getProps().getFixednumber();

        }catch(Exception e){
            e.printStackTrace();

        }


        lay1 = (LinearLayout) findViewById(R.id.layout1);
        lay2 = (LinearLayout) findViewById(R.id.layout2);
        lay3 = (LinearLayout) findViewById(R.id.layout3);
        lay4 = (LinearLayout) findViewById(R.id.layout4);
        lay5 = (LinearLayout) findViewById(R.id.layout5);
        lay6 = (LinearLayout) findViewById(R.id.layout6);
        lay7 = (LinearLayout) findViewById(R.id.layout7);
        lay8 = (LinearLayout) findViewById(R.id.layout8);
        lay9 = (LinearLayout) findViewById(R.id.layout9);
        lay10 = (LinearLayout) findViewById(R.id.layout10);
        dnumBytes = (TextView) findViewById(R.id.dnum_bytes);
        davgRate = (TextView) findViewById(R.id.davg_rate);
        dpeakRate = (TextView) findViewById(R.id.dpeak_rate);
        dpb = (ProgressBar) findViewById(R.id.dprogressBar);
        dtime = (TextView) findViewById(R.id.dtime);
        dtime_text = (TextView) findViewById(R.id.dnum_time);
        dnumBytes_text = (TextView) findViewById(R.id.bytesd);
        davgRate_text = (TextView) findViewById(R.id.avg_rated);
        dpeakRate_text = (TextView) findViewById(R.id.peak_rated);
        dprogress_text = (TextView) findViewById(R.id.progressd);
        Drawable drawable = getResources().getDrawable(R.drawable.pb_background);
        dpb.setProgressDrawable(drawable);

        unumBytes = (TextView) findViewById(R.id.unum_bytes);
        uavgRate = (TextView) findViewById(R.id.uavg_rate);
        upeakRate = (TextView) findViewById(R.id.upeak_rate);
        upb = (ProgressBar) findViewById(R.id.uprogressBar);
        unumBytes_text = (TextView) findViewById(R.id.num_bytes);
        uavgRate_text = (TextView) findViewById(R.id.avg_rate);
        upeakRate_text = (TextView) findViewById(R.id.peak_rate);
        uprogress_text = (TextView) findViewById(R.id.progress);
        utime = (TextView) findViewById(R.id.unum_time);
        utime_text = (TextView) findViewById(R.id.utime);
        upb.setProgressDrawable(drawable);

        pingResults = (TextView) findViewById(R.id.ping_results);
        smsResults = (TextView) findViewById(R.id.sms_results);
        mocResults = (TextView) findViewById(R.id.moc_results);
        mtcResults = (TextView) findViewById(R.id.mtc_results);
        mtcPb = (ProgressBar) findViewById(R.id.mtcprogressBar);

        downloadedFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DOWNLOADED.jpg");

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

        // mapView.setMapFile(f.getPath())

        final String FULL_PATH_TO_LOCAL_FILE = f.getPath();

        // new downloads().execute();

        ftpdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter++;
                if (counter % 2 == 0) {

                    lay5.setVisibility(View.VISIBLE);
                    lay6.setVisibility(View.VISIBLE);
                    lay8.setVisibility(View.VISIBLE);
                    lay9.setVisibility(View.VISIBLE);
                    lay10.setVisibility(View.VISIBLE);
                    dnumBytes.setVisibility(View.VISIBLE);
                    davgRate.setVisibility(View.VISIBLE);
                    dpeakRate.setVisibility(View.VISIBLE);
                    dpb.setVisibility(View.VISIBLE);
                    dtime.setVisibility(View.VISIBLE);
                    dtime_text.setVisibility(View.VISIBLE);
                    dnumBytes_text.setVisibility(View.VISIBLE);
                    davgRate_text.setVisibility(View.VISIBLE);
                    dpeakRate_text.setVisibility(View.VISIBLE);
                    dprogress_text.setVisibility(View.VISIBLE);

                } else {

                    lay5.setVisibility(View.GONE);
                    lay6.setVisibility(View.GONE);
                    lay8.setVisibility(View.GONE);
                    lay9.setVisibility(View.GONE);
                    lay10.setVisibility(View.GONE);
                    dnumBytes.setVisibility(View.GONE);
                    davgRate.setVisibility(View.GONE);
                    dpeakRate.setVisibility(View.GONE);
                    dpb.setVisibility(View.GONE);
                    dtime.setVisibility(View.GONE);
                    dtime_text.setVisibility(View.GONE);
                    dnumBytes_text.setVisibility(View.GONE);
                    davgRate_text.setVisibility(View.GONE);
                    dpeakRate_text.setVisibility(View.GONE);
                    dprogress_text.setVisibility(View.GONE);

                }
            }
        });

        ftpul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ucounter++;
                if (ucounter % 2 == 0) {
                    lay1.setVisibility(View.VISIBLE);
                    lay2.setVisibility(View.VISIBLE);
                    lay3.setVisibility(View.VISIBLE);
                    lay4.setVisibility(View.VISIBLE);
                    lay7.setVisibility(View.VISIBLE);
                    unumBytes.setVisibility(View.VISIBLE);
                    uavgRate.setVisibility(View.VISIBLE);
                    upeakRate.setVisibility(View.VISIBLE);
                    upb.setVisibility(View.VISIBLE);
                    utime.setVisibility(View.VISIBLE);
                    utime_text.setVisibility(View.VISIBLE);
                    unumBytes_text.setVisibility(View.VISIBLE);
                    uavgRate_text.setVisibility(View.VISIBLE);
                    upeakRate_text.setVisibility(View.VISIBLE);
                    uprogress_text.setVisibility(View.VISIBLE);

                } else {

                    lay4.setVisibility(View.GONE);
                    lay3.setVisibility(View.GONE);
                    lay7.setVisibility(View.GONE);
                    lay2.setVisibility(View.GONE);
                    lay1.setVisibility(View.GONE);
                    unumBytes.setVisibility(View.GONE);
                    uavgRate.setVisibility(View.GONE);
                    upeakRate.setVisibility(View.GONE);
                    upb.setVisibility(View.GONE);
                    utime.setVisibility(View.GONE);
                    utime_text.setVisibility(View.GONE);
                    unumBytes_text.setVisibility(View.GONE);
                    uavgRate_text.setVisibility(View.GONE);
                    upeakRate_text.setVisibility(View.GONE);
                    uprogress_text.setVisibility(View.GONE);


                }
            }
        });

        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pcounter++;

                if (pcounter % 2 == 0) {
                    pingResults.setVisibility(View.VISIBLE);
                } else {

                    pingResults.setVisibility(View.GONE);
                }
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scounter++;

                if (scounter % 2 == 0) {
                    smsResults.setVisibility(View.VISIBLE);
                } else {

                    smsResults.setVisibility(View.GONE);
                }
            }
        });

        downloadTask.execute(f);
       //pingTest(FTP_HOST);

        // For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                        smsResults.setText("SMS sent successfully");
                        smsSuccess = true;
                        sms.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        smsSuccess = false;
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        sms.setBackgroundColor(getResources().getColor(R.color.red));
                        smsResults.setText("Generic failure cause");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        smsSuccess = false;
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        sms.setBackgroundColor(getResources().getColor(R.color.red));
                        smsResults.setText("Service is currently unavailable");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        smsSuccess = false;
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        sms.setBackgroundColor(getResources().getColor(R.color.red));
                        smsResults.setText("No pdu provided");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        smsSuccess = false;
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        sms.setBackgroundColor(getResources().getColor(R.color.red));
                        smsResults.setText("Radio was explicitly turned off");
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

        // For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        smsDelivered = true;
                        smsSuccess = true;
                        smsResults.setText("SMS sent successfully. \n SMS Delivered");
                        Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        smsDelivered = false;
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

        callReceiver = new PhoneStateBroadcastReceiver();
        callReceiver.setViewProgressHandler(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callReceiver, filter);

        FloatingActionButton fab_btn = (FloatingActionButton)findViewById(R.id.fabs);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String filelocation="/mnt/sdcard/contacts_sid.vcf";
                String filename = "Stationary Test Report.xls";
                final File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                final Uri path = Uri.fromFile(filelocation);
                final CharSequence[] itemss = {"View Report", "Email Report"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProgress.this);
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

    AsyncTask<File, Void, Boolean> downloadTask = new AsyncTask<File, Void, Boolean>() {

        // bytes = (TextView)findViewById(R.id.bytee);
        @Override
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");

            super.onPreExecute();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ftpdl.setVisibility(View.VISIBLE);
                    ftpdl.setBackgroundColor(getResources().getColor(R.color.yellow));

                }
            });
        }

        protected Boolean doInBackground(File... param) {
            file = param[0];
            String fileName = file.getName();
            fileSize = file.getTotalSpace();
            ftpClient = new FTPClient();

            boolean isCompletedStartingDelete = false; // Our policy is overwrite at first
            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {

                try {

                    ftpClient.connect(FTP_HOST, 21);
                    ftpClient.login(FTP_USER, FTP_PASS);
                    ftpClient.setType(FTPClient.TYPE_BINARY);
                    ftpClient.setPassive(true);
                    filePath = db.getProps().getDownlink();//"/home/alu_dt1/60 Mo for DL";

                    if (!isCompletedStartingDelete) { // Our policy is overwrite at first
                        try {
                            ftpClient.deleteFile(fileName);
                            isCompletedStartingDelete = true;
                        } catch (FTPException e) {
                            // Maybe you should check if this exception is really thrown for file not existing.
                            e.printStackTrace();
                            isCompletedStartingDelete = true;
                        }
                    }

                    if (ftpClient.isResumeSupported()) {
                        System.err.println("FTP Server supports resume. Trying to upload file");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ftpdl.setBackgroundColor(getResources().getColor(R.color.green));

                            }
                        });
                        startTime = System.currentTimeMillis();
                        // Download the file
                        ftpClient.download(filePath, downloadedFile, new MyDownloadListener(dpb));
                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "S");


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ftpdl.setBackgroundColor(getResources().getColor(R.color.green));

                            }
                        });
                        System.err.println("FTP Server does NOT supports resume. Trying to upload file");

                        startTime = System.currentTimeMillis();
                        // Download the file
                        ftpClient.download(filePath, downloadedFile, new MyDownloadListener(dpb));

                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "secs");

                    }
                } catch (Exception e) {
                    // User Aborted operation
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ftpdl.setBackgroundColor(getResources().getColor(R.color.red));
                            Toast.makeText(getApplicationContext(), "Download FAILED", Toast.LENGTH_LONG).show();
                            if (ftpClient.isConnected()) {
                                //mHandler = new Handler();
                                //mHandler.post(progressInput.updateTask);

                                Toast.makeText(getApplicationContext(), " connected to ftp server.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), " unable to connect to ftp server.Check your internet connection", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                    e.printStackTrace();
                    Log.e("TAG", e.getStackTrace().toString());

                    e.printStackTrace();
                    break;
                } finally {
                    if (ftpClient != null && ftpClient.isConnected()) {
                        try {
                            ftpClient.disconnect(true);
                        } catch (Throwable t) {/* LOG */ }
                    }
                }

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean value) {

            takenTime = endTime - startTime;
            //if (bytes_read.size() == 0) {
            Collections.sort(bytes_read);
            max = (findMax(bytes_read) / 1024) * 8;
            //}
            double averageTime = sum / time_taken.size();

            // System.out.print("LIST" + time_taken.size() + "\nasum" + sum);

            DecimalFormat df = new DecimalFormat("#.##");
            System.out.print("\nMAXIMUMMMMMM" + max + "\nAVERAGE" + averageTime);
            //if (bytes_read.size() == 0) {
            Collections.sort(bytes_read);
            max = (findMax(bytes_read) / 1024) * 8;
            //}
             averageTime = sum / time_taken.size();

            // System.out.print("LIST" + time_taken.size() + "\nasum" + sum);

             df = new DecimalFormat("#.##");
            System.out.print("\nMAXIMUMMMMMM" + max + "\nAVERAGE" + averageTime);

            ddpeakRate = (max / averageTime) / 1048576;
            //System.out.print("\nPEAK RATE" + df.format(peakRate) + "Kbps");
            //min = findMin(byteList);
            ddataSize = (double) (transfBytes) / 1048576;

            //ddataSize = (60331906 * 8) / 1048576;
            long s = takenTime / 1000;
            if (s != 0) {
                dspeeds = ((ddataSize *8) / s);

            }

            dtime_text.setText("" + s + "S");
            davgRate.setText("" + df.format(dspeeds) + "Mbps");
            dpeakRate.setText("" + df.format(ddpeakRate) + "Mbps");

            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);
            /* dnumBytes.setVisibility(View.GONE);
               avgRate.setVisibility(View.GONE);
               dpeakRate.setVisibility(View.GONE);
               dpb.setVisibility(View.GONE);
               dtime.setVisibility(View.GONE);
               dtime_text.setVisibility(View.GONE);
               dnumBytes_text.setVisibility(View.GONE);
               davgRate_text.setVisibility(View.GONE);
               dpeakRate_text.setVisibility(View.GONE);
                dprogress_text.setVisibility(View.GONE);*/

            uploadTask.execute(file);

               /*// new uploadTask().execute(file);
                private Handler handler = new Handler();
                private Runnable runnable = new Runnable() {
                    uploadTask.execute(file);
                };*/
        }
    };

    /*******
     * Used to file upload and show progress
     **********/
    public class MyDownloadListener implements FTPDataTransferListener {

        ProgressBar jp;

        public MyDownloadListener(ProgressBar jp) {
            this.jp = jp;
        }

        public void started() {

            ViewProgress.this.runOnUiThread(new Runnable() {
                public void run() {

                    lay5.setVisibility(View.VISIBLE);
                    lay6.setVisibility(View.VISIBLE);
                    lay8.setVisibility(View.VISIBLE);
                    lay9.setVisibility(View.VISIBLE);
                    lay10.setVisibility(View.VISIBLE);

                    dnumBytes.setVisibility(View.VISIBLE);
                    davgRate.setVisibility(View.VISIBLE);
                    dpeakRate.setVisibility(View.VISIBLE);
                    dpb.setVisibility(View.VISIBLE);
                    dtime.setVisibility(View.VISIBLE);
                    dtime_text.setVisibility(View.VISIBLE);
                    dnumBytes_text.setVisibility(View.VISIBLE);
                    davgRate_text.setVisibility(View.VISIBLE);
                    dpeakRate_text.setVisibility(View.VISIBLE);
                    dprogress_text.setVisibility(View.VISIBLE);
                    jp.setProgress(0);
                    Toast.makeText(getBaseContext(), " Download Started ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" Upload Started ...");
                }
            });

        }

        public void transferred(int length) {

            transferStart = System.currentTimeMillis();
            // Yet other length bytes has been transferred since the last time this
            // method was called
            transfBytes += length;
            //.out.println("BYTES TRANSFERED" + transfBytes + " // PERCENTAGE " + percent + "%");

            final int progress = (int) ((transfBytes * 100L) / 6033190);
            final int percent = progress / 10;

            new Thread() {
                @Override
                public void run() {

                    ViewProgress.this.runOnUiThread(new Runnable() {
                        public void run() {

                            df = new DecimalFormat("#.##");
                            double bytes = (double)transfBytes/1048576;
                            //dnumBytes.setText("" + df.format(transfBytes/1048576) +"Mbytes");
                            dnumBytes.setText("" + df.format(bytes) + "Mbytes");
                            jp.setProgress(percent);
                            dprogress_text.setText(percent + "%");
                            //Toast.makeText(getBaseContext(), "PERCENTAGE" + percent, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //System.out.println("BYTES TRANSFERED" + transfBytes + "PERCENTAGE" + percent);
                }
            }.start();

            transferEnd = System.currentTimeMillis();

            double time_diff = (double) (transferEnd - transferStart) / 100;
            time_taken.add(time_diff);
            //double lengthKbits = (length*8)/1024;
            bytes_read.add((double) length);
            sum += time_diff;

            // System.out.println("BYTES LIST" + bytes_read+"\nTIME TAKEN"+time_taken+"SUMM"+sum);
            //System.out.println(" transferred ..." + length);
        }

        public void completed() {

            dSuccess = true;
            ViewProgress.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), transfBytes+" completed ..." + ddataSize, Toast.LENGTH_LONG).show();
                }
            });

            // btn.setVisibility(View.VISIBLE);
            // Transfer completed
            // jp.setProgress(jp.getMax());
            //Toast.makeText(getBaseContext(), " completed ...", Toast.LENGTH_SHORT).show();

            //System.out.println(" completed ..." );
        }

        public void aborted() {
            dSuccess = false;
            //btn.setVisibility(View.VISIBLE);
            // Transfer aborted
            /*Toast.makeText(getBaseContext()," transfer aborted ," +
                    "please try again...", Toast.LENGTH_SHORT).show();*/
            //System.out.println(" aborted ..." );
        }

        public void failed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer failed
            // System.out.println(" failed ..." );
        }

    }

    AsyncTask<File, Void, Boolean> uploadTask = new AsyncTask<File, Void, Boolean>() {

        // bytes = (TextView)findViewById(R.id.bytee);
        @Override
        protected void onPreExecute() {

            Log.d("PreExceute", "On pre Exceute......");

            super.onPreExecute();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ftpul.setVisibility(View.VISIBLE);
                    ftpul.setBackgroundColor(getResources().getColor(R.color.yellow));

                }
            });
        }

        protected Boolean doInBackground(File... param) {
            File file = param[0];
            String fileName = file.getName();
            fileSize = file.getTotalSpace();
            ftpClient = new FTPClient();

            boolean isCompletedStartingDelete = false; // Our policy is overwrite at first
            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {


                try {

                    ftpClient.connect(FTP_HOST, 21);
                    ftpClient.login(FTP_USER, FTP_PASS);
                    ftpClient.setType(FTPClient.TYPE_BINARY);
                    ftpClient.setPassive(true);
                    if (!isCompletedStartingDelete) { // Our policy is overwrite at first
                        try {
                            ftpClient.deleteFile(fileName);
                            isCompletedStartingDelete = true;
                        } catch (FTPException e) {
                            // Maybe you should check if this exception is really thrown for file not existing.
                            e.printStackTrace();
                            isCompletedStartingDelete = true;
                        }
                    }

                    if (ftpClient.isResumeSupported()) {
                        System.err.println("FTP Server supports resume. Trying to upload file");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ftpul.setBackgroundColor(getResources().getColor(R.color.green));

                            }
                        });
                        startTime = System.currentTimeMillis();
                        //Upload the file
                        ftpClient.upload(file, writtenBytes, new MyUploadTransferListener(upb));
                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "secs");


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ftpul.setBackgroundColor(getResources().getColor(R.color.green));

                            }
                        });
                        System.err.println("FTP Server does NOT supports resume. Trying to upload file");

                        startTime = System.currentTimeMillis();
                        ftpClient.upload(file, new MyUploadTransferListener(upb));
                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "secs");

                    }
                } catch (Exception e) {
                    // User Aborted operation
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ftpul.setBackgroundColor(getResources().getColor(R.color.red));
                            pingResults.setText("Unable to reach server. Check internet Connection");
                            pingSuccess = false;
                            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
                            if (ftpClient.isConnected()) {
                                //mHandler = new Handler();
                                //mHandler.post(progressInput.updateTask);

                                Toast.makeText(getApplicationContext(), " connected to ftp server.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), " unable to connect to ftp server.", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                    e.printStackTrace();
                    Log.e("TAG", e.getStackTrace().toString());

                    e.printStackTrace();
                    break;
                } finally {
                    if (ftpClient != null && ftpClient.isConnected()) {
                        try {
                            ftpClient.disconnect(true);
                        } catch (Throwable t) {/* LOG */ }
                    }
                }

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean value) {

            takenTime = endTime - startTime;
            if (bytes_read.size() == 0) {
                Collections.sort(bytes_read);
                max = (findMax(bytes_read) / 1048576) * 8;
            }
            double averageTime = sum / time_taken.size();
            //System.out.print("LIST" + time_taken.size() + "\nasum" + sum);
            DecimalFormat df = new DecimalFormat("#.##");
            //System.out.print("\nMAXIMUMMMMMM" + max + "\nAVERAGE" + averageTime);
            uupeakRate = (max / averageTime);
            //System.out.print("\nPEAK RATE" + df.format(peakRate) + "Kbps");
            //min = findMin(byteList);
            udataSize = (double) (utransfBytes) / 1048576;
            long s = takenTime / 1000;
            if (s != 0) {
                uspeeds = (udataSize / s);

            }
            utime.setText("" + s + "sec");
            uavgRate.setText("" + df.format(uspeeds) + "Mbps");
            upeakRate.setText("" + df.format(uupeakRate) + "Mbps");

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
                    /*unumBytes.setVisibility(View.GONE);
                    uavgRate.setVisibility(View.GONE);
                    upeakRate.setVisibility(View.GONE);
                    upb.setVisibility(View.GONE);
                    utime.setVisibility(View.GONE);
                    utime_text.setVisibility(View.GONE);
                    unumBytes_text.setVisibility(View.GONE);
                    uavgRate_text.setVisibility(View.GONE);
                    upeakRate_text.setVisibility(View.GONE);
                    uprogress_text.setVisibility(View.GONE);*/

            pingTest(FTP_HOST);

        }
    };

    public static double findMax(ArrayList<Double> a) {
        double max = 0;

        for (int i = 0; i < a.size(); i++) {
            if (i == 0) max = a.get(i);
            if (a.get(i) > max) {
                max = a.get(i);
            }
        }
        return max;
    }

    /*******
     * Used to file upload and show progress
     **********/

    public class MyUploadTransferListener implements FTPDataTransferListener {

        ProgressBar jp;

        public MyUploadTransferListener(ProgressBar jp) {
            this.jp = jp;
        }

        public void started() {

            ViewProgress.this.runOnUiThread(new Runnable() {
                public void run() {
                    unumBytes.setVisibility(View.VISIBLE);
                    uavgRate.setVisibility(View.VISIBLE);
                    upeakRate.setVisibility(View.VISIBLE);
                    upb.setVisibility(View.VISIBLE);
                    utime.setVisibility(View.VISIBLE);
                    utime_text.setVisibility(View.VISIBLE);
                    unumBytes_text.setVisibility(View.VISIBLE);
                    uavgRate_text.setVisibility(View.VISIBLE);
                    upeakRate_text.setVisibility(View.VISIBLE);
                    uprogress_text.setVisibility(View.VISIBLE);
                    lay1.setVisibility(View.VISIBLE);
                    lay2.setVisibility(View.VISIBLE);
                    lay3.setVisibility(View.VISIBLE);
                    lay4.setVisibility(View.VISIBLE);
                    lay7.setVisibility(View.VISIBLE);

                    jp.setProgress(0);
                    Toast.makeText(getBaseContext(), " Upload Started ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" Upload Started ...");
                }
            });

        }

        public void transferred(int length) {
            transferStart = System.currentTimeMillis();
            // Yet other length bytes has been transferred since the last time this
            // method was called
            utransfBytes += length;
            percent = (int) ((utransfBytes * 100) / 10027008);
            //System.out.println("BYTES TRANSFERED" + transfBytes + " // PERCENTAGE " + percent + "%");

            new Thread() {
                @Override
                public void run() {

                    ViewProgress.this.runOnUiThread(new Runnable() {
                        public void run() {

                            df = new DecimalFormat("#.##");
                            double bytes = (double)utransfBytes/1048576;
                            //dnumBytes.setText("" + df.format(transfBytes/1048576) +"Mbytes");
                            unumBytes.setText("" + df.format(bytes)+"Mbytes");
                            jp.setProgress(percent);
                            uprogress_text.setText(percent + "%");
                            //Toast.makeText(getBaseContext(), " transferred ..." + writtenBytes, Toast.LENGTH_SHORT).show();
                            tick_Handler.post(tick_thread);
                        }
                    });
                    //System.out.println("BYTES TRANSFERED" + transfBytes + "PERCENTAGE" + percent);
                }
            }.start();

            transferEnd = System.currentTimeMillis();

            double total = (double) (transferEnd - transferStart) / 1000;
            time_taken.add(total);
            bytes_read.add((double) length);
            sum += total;
            System.out.println("BYTES LIST" + bytes_read.size());
            //System.out.println(" transferred ..." + length);
        }

        public void completed() {
            uSuccess = true;
            // Transfer completed
            // jp.setProgress(jp.getMax());
            //Toast.makeText(getBaseContext(), " completed ...", Toast.LENGTH_SHORT).show();

            //System.out.println(" completed ..." );
        }

        public void aborted() {

            // Transfer aborted
            /*Toast.makeText(getBaseContext()," transfer aborted ," +
                    "please try again...", Toast.LENGTH_SHORT).show();*/
            //System.out.println(" aborted ..." );
        }

        public void failed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer failed
            // System.out.println(" failed ..." );
        }

    }

    /***
     * Method for performing ping test to be called in UploadTask class onPostExecute method
     * To be performed immediately upload is finished
     *
     * @param host is the ip address or domain name of host to be pinged
     */

    public void pingTest(final String host) {

        ping.setVisibility(View.VISIBLE);
        if (checkNNet.checkInternetConenction(this)) {

            ping.setBackgroundColor(getResources().getColor(R.color.green));

        } else {
            ping.setBackgroundColor(getResources().getColor(R.color.red));
        }

        pingResults.setVisibility(View.VISIBLE);

        try {
           /* final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {*/

                    try {
                        String pingCmd = "ping -c 5 " + host;
                        Runtime r = Runtime.getRuntime();
                        Process p = r.exec(pingCmd);
                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(p.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            pingResults.setText("\n\n" + inputLine + "\n\n");
                            pingResult += "\n" + inputLine;
                            pingResults.setText(pingResult);
                        }
                        in.close();
                        Toast.makeText(getApplicationContext(), "PING TEST COMPLETED\n", Toast.LENGTH_LONG);
                        pingSuccess = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            /***
             * SENDING SMS TO ANOTHER PHONE, USING PENDING INTENTS TO SEND BACK ACKNOWLEDGEMENT
             * making the checkbox visible, changing its color, sending sms and waiting for response.
             * set response result to text view
             ***/
            sms.setVisibility(View.VISIBLE);
            sms.setBackgroundColor(getResources().getColor(R.color.green));
            smsResults.setVisibility(View.VISIBLE);
            //smsSuccess = true;
            // Get the default instance of SmsManager
            final SmsManager smsManager = SmsManager.getDefault();
            // Send a text based SMS

            smsManager.sendTextMessage(threeGNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);

             psc = getIntent().getExtras().getString("psc");
             strength = getIntent().getExtras().getInt("strength");
             lac = getIntent().getExtras().getInt("lac");
             mnc = getIntent().getExtras().getString("mnc");
             mcc = getIntent().getExtras().getString("mcc");
             cid = getIntent().getExtras().getInt("cid");
             test_id = getIntent().getExtras().getInt("test_id");

            moc.setVisibility(View.VISIBLE);
            mocResults.setVisibility(View.VISIBLE);
            moc.setBackgroundColor(getResources().getColor(R.color.green));
            //smsResults.setVisibility(View.VISIBLE);

            moc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickcount = clickcount + 1;

                    final CharSequence[] itemss = new CharSequence[]{"To 4G", "To 3G", "To 2G", "To Fixed Line"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewProgress.this);
                    builder.setTitle("Choose number to CALL");
                    builder.setItems(itemss, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            if (item == 0 && fourGNumber != "") {
                                if (ContextCompat.checkSelfPermission(ViewProgress.this,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(ViewProgress.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            MY_PERMISSION_ACCESS_COARSE_LOCATION);
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + fourGNumber));
                                startActivity(callIntent);
                                callType = 0;

                            } else if (item == 1 && threeGNumber != "") {
                                if (ContextCompat.checkSelfPermission(ViewProgress.this,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(ViewProgress.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            MY_PERMISSION_ACCESS_COARSE_LOCATION);
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + threeGNumber/*6747447518*/));
                                startActivity(callIntent);
                                callType = 1;

                            } else if (item == 2 && twoGNumber != "") {
                                if (ContextCompat.checkSelfPermission(ViewProgress.this,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(ViewProgress.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            MY_PERMISSION_ACCESS_COARSE_LOCATION);
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + twoGNumber));
                                startActivity(callIntent);
                                callType = 2;
                            } else if (item == 3 && fixedNumber != "") {
                                if (ContextCompat.checkSelfPermission(ViewProgress.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(ViewProgress.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
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


            mtc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickcount = clickcount + 1;
                    moc.setBackgroundColor(getResources().getColor(R.color.green));
                    final CharSequence[] itemss = new CharSequence[]{"To 4G", "To 3G", "To 2G", "To Fixed Line"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewProgress.this);
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


                    mtc.setVisibility(View.VISIBLE);

                }
            });


        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void lastCall(int callType) {

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
        if(callType == 0){
            typeOfCall = "4G";
        }else if(callType == 1){
            typeOfCall = "3G";
        }else if(callType == 2){
            typeOfCall = "2G";
        }else if(callType == 3){
            typeOfCall = "Fixed Line";
        }
        Toast.makeText(getApplicationContext(), "OUTGOING CALL TO REPORT", Toast.LENGTH_LONG);
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
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),df.format(udataSize),uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)),df.format(ddataSize),dSuccess.toString(),
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
                    String.valueOf(df.format(uspeeds)), String.valueOf(df.format(uupeakRate)),df.format(udataSize)/*String.valueOf(df.format(udataSize))*/,uSuccess.toString(),
                    String.valueOf(df.format(dspeeds)),String.valueOf(df.format(ddpeakRate)), df.format(ddataSize)/*String.valueOf(df.format(ddataSize))*/,dSuccess.toString(),
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

            dialog = new ProgressDialog(ViewProgress.this);

            dialog.setMessage("Generating Report. Please Wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                Report report = new Report(ViewProgress.this);

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

    private class MyThread implements Runnable {

        public void run() {
            String txt = "Vlakno id:" + Thread.currentThread().getId() + " THREAD";
            Log.v("MyActivity", txt);
            double bytesperSec = (double)transfBytes;
            bytes_persec.add(bytesperSec);
            System.out.println("BYTES PER SECOND "+ bytes_persec.size()+"\nCONTENTS"+bytes_persec);
            tick_Handler.postDelayed(tick_thread, 10000);
        }
    }

    public void onBackPressed(){

        final CharSequence[] itemss = {"Repeat Test", "Go to HomeScreen"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProgress.this);
        builder.setTitle("Choose the action do you want to perform?");
        builder.setItems(itemss, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {
                    Intent intent = new Intent(ViewProgress.this, RFmeasurements.class);
                    intent.putExtra("tests", "stationary");
                    startActivity(intent);



                } else if (item == 1) {
                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
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