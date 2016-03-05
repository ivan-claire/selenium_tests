package com.testing.alcatel.alufieldtesting;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

public class Testing extends AppCompatActivity {

    private static final String TAG_SITE_NAME = "Site_Name";
    private static final String TAG_SITE_ID = "Site_Id";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";
    private static final String TAG_ALTITUDE= "Altitude";
    private static final String TAG_AZIMUTH = "Azimuth";
    private static final String TAG_BTS_ID = "bts_id";
    //JSON Node names for getting data from database
    private static final String TAG_NODEB_NAME = "node_b";
    private static final String TAG_PSC = "PSC";
    private static final String TAG_CID = "local_cell_id";

    ProgressBar pbs;
    private ArrayList<HashMap<String, String>>  sitename_list;
    //private ArrayList<HashMap<String, String>>  sitename_listdisplay;
    checkNet checknet;
    ListView lv;
    ArrayAdapter adapter;
    String popUpContents[];
    PopupWindow popupWindowSites;
    PopupWindow popupWindowTests;
    EditText sitename;
    EditText typee;
    EditText datee;
    public static EditText testnamee;
    public static EditText commentes;
    Button ok_btn;
    String strArr[];
    String type;
    public static String testname;
    public static String number;
    public static int duration;
    public static int iteration;
    public static String host;
    public static String link;
    public static String dllink;
    public static String apn;
    public static String netType;

    //private View.OnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String TAG = "Testing.java";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int mins = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);

        Propertiess properties = new Propertiess();
        CellInformation infor = new CellInformation();

        try {

            netType = infor.mCurNetwork;

        }catch(Exception e){
            out.print("Error getting data. may be null");
        }

        List<String> testsList = new ArrayList<String>();
        testsList.add("Stationary Test");
        testsList.add("Mobility");


        // convert to simple array
        popUpContents = new String[testsList.size()];
        testsList.toArray(popUpContents);

        new GetData().execute("http://192.168.43.197/test.php?getData");
        // initialize pop up window
        popupWindowTests = popupWindowTests();
        // button on click listener
        typee = (EditText)findViewById(R.id.typee);
        testnamee = (EditText)findViewById(R.id.test_namee);
        commentes = (EditText)findViewById(R.id.commente);

        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.typee:
                        // show the list view as dropdown
                        popupWindowTests.showAsDropDown(v, -5, 0);

                        break;
                }
            }
        };

        datee = (EditText) findViewById(R.id.datee);
        typee.setOnClickListener(handler);

        sitename_list = new ArrayList<HashMap<String, String>>();
        datee.setText(hour+":"+mins+":"+seconds+"---"+day+"/"+month+"/"+year);

        typee.setText(TestsDropDownOnItemClickListener.test);

        ok_btn = (Button) findViewById(R.id.btnn_ok);
        //This command causes problem
        ok_btn.setOnClickListener(TestsDropDownOnItemClickListener.onclickListener);


    }

    private class GetData extends AsyncTask<String, Void,Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Show progress dialog
            pbs = (ProgressBar)findViewById(R.id.loading);
            pbs.setIndeterminate(true);
            pbs.setProgressDrawable(getResources().getDrawable(R.drawable.pb_color));
            int progressbarstatus = 0;
            EditText sitename;
         //   sitename_list.clear();
        }

        //Here,the makeServiceCall() method is called to get the json from url,
        //parse the JSON and add to HashMap to show the results in List View.
        @Override
        protected Void doInBackground(String... params) {

            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];

            Log.d("URLLLLLLLLLL ", urll);
            //sharedlist.clear();
            checknet = new checkNet();
            if(checknet.checkInternetConenction(getApplication())) {

                // Get the string from the intent
                try {
                    String jsonStr = sh.makeServiceCall(urll, ServiceHandler.GET);
                    //System.out.println(jsonStr);
                    Log.d("Response: ", "> " + jsonStr);

                    if (jsonStr != null) {
                        try {
                            JSONArray jsonarr = new JSONArray(jsonStr);

                            //looping thro all comments
                            for (int i = 0; i < jsonarr.length(); i++) {

                                JSONObject c = jsonarr.getJSONObject(i);
                                String gottenName = c.getString(TAG_SITE_NAME);
                                HashMap<String, String> comm = new HashMap<String, String>();
                                comm.put(TAG_SITE_NAME, gottenName);

                                sitename_list.add(comm);
                                //sitename_listdisplay.add(comm);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.print("========================================================");
            System.out.print("\nLISTTTTTTTT OFFFFF OPTIONSSSSS" + sitename_list.size());
            //getCellInfo();
            pbs.setVisibility(View.GONE);
            // Dismiss the progress dialog
            checknet = new checkNet();
            if(checknet.checkInternetConenction(getApplication())) {
                int i=0;
                 strArr = new String[sitename_list.size()];
                for (HashMap<String, String> hashMap : sitename_list) {
                    for (String value : hashMap.values()) {
                        strArr[i] = value;
                        i++;
                    }
                        try {

                            popUpContents = strArr; ;//new String[sitename_list.size()];
                            //sitename_list.toArray(popUpContents);

                            sitename = (EditText) findViewById(R.id.site_namee);
                            View.OnClickListener handler = new View.OnClickListener() {
                                public void onClick(View v) {

                                    switch (v.getId()) {

                                        case R.id.site_namee:
                                            // show the list view as dropdown
                                            adapter = SitesAdapter(popUpContents);
                                            // initialize pop up window
                                            popupWindowSites = popupWindowSites(adapter);
                                            popupWindowSites.showAsDropDown(v, -5, 0);

                                            // set the item click listener
                                            sitename.addTextChangedListener(new TextWatcher() {

                                                @Override
                                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                                    System.out.print("Textwatcher is working oh");

                                                    Testing.this.adapter.getFilter().filter(cs);


                                                }

                                                ;


                                                @Override
                                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                                              int arg3) {
                                                    // TODO Auto-generated method stub
                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {

                                                }

                                            });

                                            break;
                                    }
                                }
                            };
                            // our button

                            sitename.setOnClickListener(handler);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    //i++;
                //}
                }else{
                    Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }

            }

            }


    public PopupWindow popupWindowSites(ArrayAdapter adapter) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        final ListView listViewSites = new ListView(this);


        // set our adapter and pass our pop up window contents
        listViewSites.setAdapter(adapter);

        listViewSites.setOnItemClickListener(new SitesDropDownOnItemClickListener());

        // some other visual settings
        //popupWindow.setFocusable(true);
        popupWindow.setWidth(400);
        popupWindow.setHeight(350);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
       // popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // set the list view as pop up window content
        popupWindow.setContentView(listViewSites);

        return popupWindow;
    }

    public PopupWindow popupWindowTests() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        final ListView listViewSites = new ListView(this);


        // set our adapter and pass our pop up window contents
        listViewSites.setAdapter(SitesAdapter(popUpContents));

        listViewSites.setOnItemClickListener(new TestsDropDownOnItemClickListener());

        // some other visual settings
        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // set the list view as pop up window content
        popupWindow.setContentView(listViewSites);

        return popupWindow;
    }

    /* adapter where the list values will be set
    */
    private ArrayAdapter<String> SitesAdapter(String SitesArray[]) {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SitesArray) {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[0];

                // visual settings for the list item
                TextView listItem = new TextView(Testing.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(15);
                listItem.setMinWidth(150);
                listItem.setPadding(10, 10, 10, 10);


                return listItem;
            }


        };

        return adapter;
    }


}

