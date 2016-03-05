package com.testing.alcatel.alufieldtesting;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Propertiess extends Activity {

    Button edit_btn;
    Button save_btn;
    public static EditText twonumbere;
    public static EditText threenumbere;
    public static EditText fournumbere;
    public static EditText ftplinke;
    public static EditText fixednumbere;
    public static EditText downloadlinke;
    public static EditText ftpusere;
    public static EditText ftppasse;
    public static EditText datee;
    public static String kind;
    public static String twonumber;
    public static String threenumber;
    public static String ftpuser;
    public static String netType;
    public static String ftplink;
    public static String fixednumber;
    public static String date;
    public static String ftppass;
    public static String fournumber;
    public static String downloadlink;
    public Properties props;
    public MyDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertiess);

        db = new MyDBHelper(getApplicationContext());

        edit_btn = (Button) findViewById(R.id.btn_edit);
        save_btn = (Button) findViewById(R.id.btn_save);
        twonumbere = (EditText) findViewById(R.id.twonumbere);
        threenumbere = (EditText) findViewById(R.id.threenumbere);
        fournumbere = (EditText) findViewById(R.id.fournumbere);
        ftplinke = (EditText) findViewById(R.id.ftplinke);
        fixednumbere = (EditText) findViewById(R.id.fixednumbere);
        ftpusere = (EditText) findViewById(R.id.ftpusere);
        ftppasse = (EditText) findViewById(R.id.ftppasse);
        downloadlinke = (EditText) findViewById(R.id.downloadlinke);
        datee = (EditText) findViewById(R.id.datee);

        twonumbere.setEnabled(false);
        twonumbere.setFocusable(false);
        twonumbere.setFocusableInTouchMode(false);

        threenumbere.setEnabled(false);
        threenumbere.setFocusable(false);
        threenumbere.setFocusableInTouchMode(false);

        fournumbere.setEnabled(false);
        fournumbere.setFocusableInTouchMode(false);
        fournumbere.setFocusable(false);

        ftplinke.setEnabled(false);
        ftplinke.setFocusable(false);
        ftplinke.setFocusableInTouchMode(false);

        fixednumbere.setEnabled(false);
        fixednumbere.setFocusable(false);
        fixednumbere.setFocusableInTouchMode(false);

        ftpusere.setEnabled(false);
        ftpusere.setFocusable(false);
        ftpusere.setFocusableInTouchMode(false);

        ftppasse.setEnabled(false);
        ftppasse.setFocusable(false);;
        ftppasse.setFocusableInTouchMode(false);

        downloadlinke.setEnabled(false);
        downloadlinke.setFocusable(false);;
        downloadlinke.setFocusableInTouchMode(false);

        datee.setEnabled(false);
        datee.setFocusable(false);
        datee.setFocusableInTouchMode(false);

        /*final String testname = getIntent().getExtras().getString("testname");
        final String site = getIntent().getExtras().getString("sitename");
        final String comments = getIntent().getExtras().getString("comments");
        final String netType = getIntent().getExtras().getString("netType");
        final String kind = getIntent().getExtras().getString("kind");
        final String test = getIntent().getExtras().getString("test");*/



        //try {
            props = db.getProps();

            if (props != null) {

                twonumber = props.getTwonumber();
                threenumber = props.getThreenumber();
                fournumber = props.getFournumber();
                ftplink = props.getFtplink();
                ftpuser = props.getFtpuser();
                fixednumber = props.getFixednumber();
                date = props.getCreated_at();
                ftppass = props.getFtppass();
                downloadlink = props.getDownlink();

                twonumbere = (EditText) findViewById(R.id.twonumbere);
                twonumbere.setText(twonumber);

                threenumbere = (EditText) findViewById(R.id.threenumbere);
                threenumbere.setText(String.valueOf(threenumber));

                fournumbere = (EditText) findViewById(R.id.fournumbere);
                fournumbere.setText(fournumber);

                ftplinke = (EditText) findViewById(R.id.ftplinke);
                ftplinke.setText(ftplink);

                fixednumbere = (EditText) findViewById(R.id.fixednumbere);
                fixednumbere.setText(fixednumber);

                ftpusere = (EditText) findViewById(R.id.ftpusere);
                ftpusere.setText(String.valueOf(ftpuser));

                ftppasse = (EditText) findViewById(R.id.ftppasse);
                ftppasse.setText(ftppass);

                downloadlinke = (EditText) findViewById(R.id.downloadlinke);
                downloadlinke.setText(downloadlink);

                datee = (EditText) findViewById(R.id.datee);
                datee.setText(String.valueOf(date));

            } else {

                twonumbere = (EditText) findViewById(R.id.twonumbere);

                threenumbere = (EditText) findViewById(R.id.threenumbere);

                fournumbere = (EditText) findViewById(R.id.fournumbere);

                ftplinke = (EditText) findViewById(R.id.ftplinke);

                fixednumbere = (EditText) findViewById(R.id.fixednumbere);

                ftpusere = (EditText) findViewById(R.id.ftpusere);

                ftppasse = (EditText) findViewById(R.id.ftppasse);

                downloadlinke = (EditText) findViewById(R.id.downloadlinke);

                datee = (EditText) findViewById(R.id.datee);

                Toast.makeText(getApplicationContext(), "No default settings found", Toast.LENGTH_LONG).show();

            }
       // }catch (Exception e){
         //      System.out.print("ERROR");
        //}

            edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    twonumbere.setEnabled(true);
                    twonumbere.setFocusableInTouchMode(true);
                    twonumbere.setFocusable(true);

                    threenumbere.setEnabled(true);
                    //threenumber.setClickable(true);
                    threenumbere.setFocusableInTouchMode(true);
                    threenumbere.setFocusable(true);

                    fournumbere.setEnabled(true);
                    fournumbere.setFocusableInTouchMode(true);
                    fournumbere.setFocusable(true);

                    ftplinke.setEnabled(true);
                    fournumbere.setFocusable(true);
                    ftplinke.setFocusableInTouchMode(true);

                    fixednumbere.setEnabled(true);
                    fournumbere.setFocusable(true);
                    fixednumbere.setFocusableInTouchMode(true);

                    ftpusere.setEnabled(true);
                    ftpusere.setFocusable(true);
                    ftpusere.setFocusableInTouchMode(true);

                    ftppasse.setEnabled(true);
                    ftppasse.setFocusable(true);;
                    ftppasse.setFocusableInTouchMode(true);

                    downloadlinke.setEnabled(true);
                    downloadlinke.setFocusable(true);;
                    downloadlinke.setFocusableInTouchMode(true);


                    datee.setEnabled(true);
                    datee.setFocusable(true);
                    datee.setFocusableInTouchMode(true);

                    save_btn.setVisibility(View.VISIBLE);
                    edit_btn.setVisibility(View.GONE);

                }
            });

            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   try {

                     twonumber = twonumbere.getText().toString();
                     threenumber = threenumbere.getText().toString();
                       ftpuser = ftpusere.getText().toString();
                       ftplink = ftplinke.getText().toString();
                     fixednumber = fixednumbere.getText().toString();
                     fournumber = fournumbere.getText().toString();
                     ftppass = ftppasse.getText().toString();
                     downloadlink = downloadlinke.getText().toString();

                    Properties updated_props = new Properties(fixednumber,twonumber,threenumber,ftpuser,
                            ftplink,fournumber,ftppass,downloadlink);
                      try {

                       // db.updateProperties(updated_props);
                          db.saveProperties(updated_props);

                      }catch(Exception e){
                         System.out.println("Error");
                    }

                } catch (Exception e) {
                   System.out.print("something isn't right");
                }

                Intent intent = new Intent(getApplicationContext(),Forcing.class);

                    Toast.makeText(getApplicationContext(),"Settings Saved", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

    }

            public void onBackPressed(){
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Bundle is optional
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
        //end Bundle
                startActivity(intent);
    }
}
