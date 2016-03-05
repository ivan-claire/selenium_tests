package com.testing.alcatel.alufieldtesting;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

public class MobilityTests extends Activity {

    Button ok_btn;
    TextView not_ok;
    EditText dialme;
    EditText durationme;
    EditText iterationme;
    MyDBHelper db;
    Long last;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobility_tests);

        db = new MyDBHelper(getApplicationContext());

        ok_btn = (Button)findViewById(R.id.btn_ok);

        dialme = (EditText)findViewById(R.id.diale);
        durationme = (EditText)findViewById(R.id.durationme);
        iterationme = (EditText)findViewById(R.id.iterationme);

        not_ok = (TextView)findViewById(R.id.back);


            Propertiess properties = new Propertiess();
            Testing testing = new Testing();
            db = new MyDBHelper(getApplicationContext());

            if(db.getProps() != null) {

                dialme.setText(db.getProps().getThreenumber());

            }
            //db.getInfo();
           // Log.e("HERE IS THE INFO", db.getInfo().toString());
            final String testname = getIntent().getExtras().getString("testname");
            final String site = getIntent().getExtras().getString("sitename");
            final String comments = getIntent().getExtras().getString("comments");
            final String netType = getIntent().getExtras().getString("netType");
            final String kind = getIntent().getExtras().getString("kind");
            final String test = getIntent().getExtras().getString("test");

            final String number = testing.number;
            final int duration =  testing.duration;
            final int iteration =  testing.iteration;
            final String link = testing.link;
            final String dllink = testing.dllink;
            final String host =  testing.host;
            final String apn = testing.apn;


        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Info info = new Info(testname, site,test,comments,dllink,kind,number,
                        duration,iteration,netType,link,host);
                 db.saveTestInfo(info);
                try {
                    String number = db.getProps().getThreenumber();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                if (ContextCompat.checkSelfPermission(MobilityTests.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MobilityTests.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }
                startActivity(callIntent);
               /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:674744751"));*/
                PhoneStateReceiver callReceiver = new PhoneStateReceiver();
                //callReceiver.setMobilityTestHandler(MobilityTests.this);
                IntentFilter filter = new IntentFilter();
                filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
                registerReceiver(callReceiver, filter);

                }catch(Exception e){
                    System.out.print("eroor");
                }

                /*//makecall(v);
                Intent intent = new Intent(getApplicationContext(),RFmeasurements.class);
                intent.putExtra("makeCall", 1);
                startActivity(intent);*/

            }
        });

        not_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Propertiess.class);
                intent.putExtra("testname", testname);
                intent.putExtra("comments", comments);
                intent.putExtra("sitename", testname);
                intent.putExtra("netType", netType);
                intent.putExtra("kind", kind);
                intent.putExtra("test", test);

                startActivity(intent);
            }
        });


    }
    public void makecall(final View view){
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:674744751"));

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            startActivity(callIntent);
            Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();

            Runnable showDialogRun = new Runnable() {
                public void run(){
                 //   String number = db.getProps().getNumber();
                    Intent intent = new Intent(view.getContext(),RFmeasurements.class);
                    intent.putExtra("makeCall", 1);
                    intent.putExtra("duration", 1);
                    intent.putExtra("tests", "mobility");
                    Toast.makeText(getApplicationContext(), " Getting Site Location...", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            };
            Handler h = new Handler();
            h.postDelayed(showDialogRun, 10000);
        } catch (ActivityNotFoundException activityException) {
            Throwable e = null;
            Log.e(" dialing example", "Callfailed", e);
        }
    }


}
