package com.testing.alcatel.alufieldtesting;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import com.google.android.gms.location.LocationListener;

import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.CallLog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import static java.lang.System.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;



public class RFmeasurements extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener,LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public GoogleMap mMap;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    ProgressDialog pDialog;
    /* This variables need to be global, so we can used them onResume and onPause method to
       stop the listener */
    double dBmlevel;
    double asulevel;
    Boolean showPolys = false;
    TelephonyManager mTelephonyManager = null;
    String mMNC;
    String mMCC;
    int mCurLAC;
    int mCurCID;
    int mCurPsc;
    static int rssi;
    int mNetwork;
    String mCurNetwork = "NO NETWORK FOUND";
    int myProgress = 0;
    TextProgressBar pb;
    private Handler myHandler = new Handler();
    public String psc;
    public Polyline poly1;
    public Polyline poly2;
    public Polyline poly3;
    public Marker pscMarker;
    public String number;
    ViewProgress vProgress;
    String avgUpload;
    String peakUpload;
    String ufileSize;
    String uSuccess;
    String avgDownload;
    String peakDownload;
    String dfileSize;
    String dSuccess;
    String pingResults;
    String pingSuccess;
    String smsResult;
    String mobility_duration;
    int duration = 0;
    public String callDuration = "";
    String phNumber;
    int test_id;
    ProgressDialog dialog;
    //public static boolean canSave = false;

    List<NeighboringCellInfo> mNeighboringCellInfo;
    TextView mTelephonyInfo;
    MyPhoneStateListener  MyListener;
    Marker mapMarker;
    Marker mapMarker2;
    LatLng currentLatLng;
    LatLng prevLatLng;
    String test;
    Double bts_azi1;
    Double bts_azi2;
    Double bts_azi3;
    LatLng ll1;
    LatLng lll2;
    LatLng llll3;
    //JSON Node names for getting data from database
    private static final String TAG_SITE_NAME = "Site_Name";
    private static final String TAG_SITE_ID = "Site_Id";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";
    private static final String TAG_ALTITUDE= "Altitude";
    private static final String TAG_AZIMUTH = "Azimuth";
    private static final String TAG_BTS_ID = "bts_id";
    //JSON Node names for getting data from database
    private static final String TAG_NODEB_NAME = "node_b";
    private  final String TAG_PSC = "PSC";
    private static final String TAG_CID = "local_cell_id";

    private static final long INTERVAL = 1000 * 5 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 5 * 1; // 1 minute


    CellInformation info = new CellInformation();

    private ArrayList<Options> options;
    private ArrayList<Options> site_info;
    private ArrayList<MoreOptions> moptions;
    TextView psc_value;
    MyDBHelper db;
    public int PrevCid;
    public Location lastLoc;
    public Marker firstLoc;
    public ArrayList<Location> listLocs;
    public LocationListener mLocationListener;
    TextView network;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    public int makeCall;
    public int canSave;
    public int ftpul;
    List<Polyline> mPolylines;
    Double bts_azi;
    LatLng ll;
    LatLng lll;
    LatLng llll;
    Marker centerOneMarker;
    FloatingActionButton fabm;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfmeasurement);
        //creating the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        db = new MyDBHelper(getApplicationContext());
        vProgress = new ViewProgress();
        setSupportActionBar(toolbar);
        // making sure overflow button shows
        makeActionOverflowMenuShown();
       // poly1 = new Polyline();
        mPolylines = new ArrayList<Polyline>();

        options = new ArrayList<Options>();       // list of all content in the sites table(lat,long, sitename, azim, bts_id ...)
        site_info = new ArrayList<Options>();     // contains info abt the (current)particular site with the current cell id
        moptions = new ArrayList<MoreOptions>();  // gets the diff sectors of a particular site using the current cell id

        psc_value = (TextView)findViewById(R.id.psc_value);
        network = (TextView)findViewById(R.id.network1);
        fabm = (FloatingActionButton)findViewById(R.id.fabm);
        //show error dialog if GoolglePlayServices not available
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //creating the navigation drawer when a button is clicked
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Button nav_btn = (Button) findViewById(R.id.nav_btn);
        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(Gravity.LEFT);
                mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

                mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {


                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                        //getActionBar().show();

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });

            }
        });/// end of navigation drawer creation

          /* Update the listener to get info abt the phone's state, and start it */
        MyListener   = new MyPhoneStateListener();
        mTelephonyManager = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        mTelephonyManager.listen(MyListener, PhoneStateListener.LISTEN_CALL_STATE);

    }


    @Override
    public void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        // Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.
                requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        //Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        firstLoc.remove();
       // mapMarker2 = mapMarker;
        //mapMarker.remove();
        addMarker();
       // mMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    private void addMarker() {
        MarkerOptions options = new MarkerOptions();
        prevLatLng = currentLatLng;

         currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        options.position(currentLatLng);
        //Marker mapMarker = mMap.addMarker(options);

        mapMarker =mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your" +
                " Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.location /*circle_purple*/)));

         //mapMarker2 =mMap.addMarker(new MarkerOptions().position(prevLatLng).title("Your" +
             //    " Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circle_purple)));

        //CameraPosition cameraPosition = new CameraPosition(currentLatLng, 18, 30, 112.5F);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLng)       // Sets the center of the map to Mountain View
                .zoom(mMap.getCameraPosition().zoom)                   // Sets the zoom
                        // Sets the orientation of the camera to east
                        // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder*/
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            firstLoc =mMap.addMarker(new MarkerOptions().position(loc).title("Your" +
                    " Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circle_purple)));

            if(mMap != null){
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(loc)       // Sets the center of the map to Mountain View
                        .zoom(16)
                        //.zoom(mMap.getCameraPosition().zoom)                   // Sets the zoom
                                // Sets the orientation of the camera to east
                                // Sets the tilt of the camera to 30 degrees
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                ;//.newLatLngZoom(loc, 16.0f));
            }
        }
    };


    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }catch(Exception e){
            e.printStackTrace();
        }
        //Log.d(TAG, "Location update stopped .......................");
    }

    // abstract method in phoneStateListener
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        refreshStationInfo();
        mTelephonyManager.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            // Log.d(TAG, "Location update resumed .....................");
        }
        super.onResume();
    }

    /* abstract method in phoneStateListener, Called when the application is minimized */
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        mTelephonyManager.listen(MyListener, PhoneStateListener.LISTEN_NONE);
    }

    // getting info about the phones state
    private class MyPhoneStateListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";
        @Override
        public void onCellLocationChanged(CellLocation location) {
            // TODO Auto-generated method stub
            super.onCellLocationChanged(location);
            refreshStationInfo();
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            // TODO Auto-generated method stub
            super.onSignalStrengthsChanged(signalStrength);

            PrevCid = mCurCID;
            refreshStationInfo();

            Toast.makeText(getApplicationContext(),"Cell ID  "
                    + mCurCID+"\nPrevCid "+PrevCid, Toast.LENGTH_LONG).show();

            // if the cell id is the same there's no need to take all info from the database
            // again to display sectors and their locations
            if(PrevCid != mCurCID){

                if(centerOneMarker.isVisible()){
                    centerOneMarker.remove();
                }
                new GetMoreData().execute("http://192.168.43.197/test.php?getMData&cellid=" + mCurCID);

                TextProgressBar text =new TextProgressBar(getApplicationContext());
                int rssi = -113 + 2 * signalStrength.getGsmSignalStrength();

                myProgress= rssi;
                //System.out.println("PROGRESS BARRRRRRRRRRRR PRINTS" + myProgress);
                doSomeWork(rssi);
                //Toast.makeText(getApplicationContext(),"Cell ID has changed, so update"
                  //      + mCurCID+"\nPSC"+psc+"\nPrevCid"+PrevCid, Toast.LENGTH_LONG).show();
                //try {

                //db = new MyDBHelper(getApplicationContext());
                Info info =  db.getInfo();

                try {
                    test_id = (int) info.getId();
                }catch(Exception e){
                    e.printStackTrace();
                }

                        if (canSave == 1 && duration == 0) {

                            final Handler handler2 = new Handler();
                            handler2.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                            String pscValue = psc; // psc_value.getText().toString();

                            avgUpload = String.valueOf(vProgress.uspeeds);
                            peakUpload = String.valueOf(vProgress.uupeakRate);
                            ufileSize = String.valueOf(vProgress.udataSize);
                            uSuccess = String.valueOf(vProgress.uSuccess);
                            avgDownload = String.valueOf(vProgress.dspeeds);
                            peakDownload = String.valueOf(vProgress.ddpeakRate);
                            dSuccess = String.valueOf(vProgress.dSuccess);
                            dfileSize = String.valueOf(vProgress.ddataSize);
                            smsResult = String.valueOf(vProgress.smsResults);
                            pingResults = String.valueOf(vProgress.pingResults);
                            pingSuccess = String.valueOf(vProgress.pingSuccess);


                            Idle idleTest = new Idle(pscValue, myProgress, mCurLAC, mMCC, mMNC, mCurCID, test_id,
                                    avgUpload, peakUpload, ufileSize, uSuccess,
                                    avgDownload, peakDownload, dfileSize, dSuccess,
                                    pingResults, pingSuccess, smsResult, "", "", callDuration);
                            db.saveIdleTest(idleTest);
                    }

                    }, 2000);
                                          //Toast.makeText(getApplicationContext(), " 1st CanSave= "
                     //       + canSave, Toast.LENGTH_LONG).show();

                }else if(canSave == 1 && duration == 1){

                    lastCall();

                    String pscValue = psc_value.getText().toString();

                    avgUpload = String.valueOf(vProgress.uspeeds);
                    peakUpload = String.valueOf(vProgress.uupeakRate);
                    ufileSize = String.valueOf(vProgress.udataSize);
                    uSuccess = String.valueOf(vProgress.uSuccess);
                    avgDownload = String.valueOf(vProgress.dspeeds);
                    peakDownload = String.valueOf(vProgress.ddpeakRate);
                    dSuccess = String.valueOf(vProgress.dSuccess);
                    dfileSize = String.valueOf(vProgress.ddataSize);
                    smsResult = String.valueOf(vProgress.smsResults);
                    pingResults = String.valueOf(vProgress.pingResults);
                    pingSuccess = String.valueOf(vProgress.pingSuccess);


                    Idle idleTest = new Idle(pscValue, myProgress, mCurLAC, mMCC, mMNC, mCurCID, test_id,
                            avgUpload, peakUpload,ufileSize,uSuccess,
                            avgDownload,peakDownload,dfileSize,dSuccess,
                            pingResults,pingSuccess,smsResult,"","",callDuration);

                    db.saveIdleTest(idleTest);

                   // Toast.makeText(getApplicationContext(), "2nd CanSave= "
                    //        + canSave, Toast.LENGTH_LONG).show();

                }
                //}catch (Exception e){

                System.out.print("No way. id is null");
                //  }
            }else{

                //new GetMoreData().execute("http://192.168.43.197/test.php?getMData&cellid=" + mCurCID);
                TextProgressBar text =new TextProgressBar(getApplicationContext());
                int rssi = -113 + 2 * signalStrength.getGsmSignalStrength();

                myProgress= rssi;
                System.out.println("PROGRESS BARRRRRRRRRRRR PRINTS" + myProgress);
                doSomeWork(rssi);
                //Toast.makeText(getApplicationContext(),"Cell ID has not changed don't update"
                  //      + mCurCID+"\nPSC"+psc+"PrevCid"+PrevCid, Toast.LENGTH_LONG).show();

                try {

                // db = new MyDBHelper(getApplicationContext());
                Info info =  db.getInfo();
                 test_id = (int) info.getId();

                    if ( canSave == 1&& duration == 0) {
                        String pscValue = psc_value.getText().toString();

                        avgUpload = String.valueOf(vProgress.uspeeds);
                        peakUpload = String.valueOf(vProgress.uupeakRate);
                        ufileSize = String.valueOf(vProgress.udataSize);
                        uSuccess = String.valueOf(vProgress.uSuccess);
                        avgDownload = String.valueOf(vProgress.dspeeds);
                        peakDownload = String.valueOf(vProgress.ddpeakRate);
                        dSuccess = String.valueOf(vProgress.dSuccess);
                        dfileSize = String.valueOf(vProgress.ddataSize);
                        smsResult = String.valueOf(vProgress.smsResults);
                        pingResults = String.valueOf(vProgress.pingResults);
                        pingSuccess = String.valueOf(vProgress.pingSuccess);


                        Idle idleTest = new Idle(pscValue, myProgress, mCurLAC, mMCC, mMNC, mCurCID, test_id,
                                avgUpload, peakUpload,ufileSize,uSuccess,
                                avgDownload,peakDownload,dfileSize,dSuccess,
                                pingResults,pingSuccess,smsResult,"","",callDuration);

                        db.saveIdleTest(idleTest);
                        /*Toast.makeText(getApplicationContext(), "3rd CanSave= "
                                + canSave, Toast.LENGTH_LONG).show();*/

                    }else if(canSave == 1 && duration == 1){
                        lastCall();
                        String pscValue = psc_value.getText().toString();

                        avgUpload = String.valueOf(vProgress.uspeeds);
                        peakUpload = String.valueOf(vProgress.uupeakRate);
                        ufileSize = String.valueOf(vProgress.udataSize);
                        uSuccess = String.valueOf(vProgress.uSuccess);
                        avgDownload = String.valueOf(vProgress.dspeeds);
                        peakDownload = String.valueOf(vProgress.ddpeakRate);
                        dSuccess = String.valueOf(vProgress.dSuccess);
                        dfileSize = String.valueOf(vProgress.ddataSize);
                        smsResult = String.valueOf(vProgress.smsResults);
                        pingResults = String.valueOf(vProgress.pingResults);
                        pingSuccess = String.valueOf(vProgress.pingSuccess);

                        Idle idleTest = new Idle(pscValue, myProgress, mCurLAC, mMCC, mMNC, mCurCID, test_id,
                                avgUpload, peakUpload,ufileSize,uSuccess,
                                avgDownload,peakDownload,dfileSize,dSuccess,
                                pingResults,pingSuccess,smsResult,"","",callDuration);
                        db.saveIdleTest(idleTest);
                       // Toast.makeText(getApplicationContext(), " 4th CanSave= "
                         //       + canSave, Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e){

                  System.out.print("No way. id is null");
                   // showPolys = false;
                }

            }
            //refreshStationInfo();
        }

        public void onDataActivity(int direction) {
        }

        public void onDataConnectionStateChanged(int state) {
        }

        public void onMessageWaitIndicatorChanged(boolean mwi) {
        }

        public void onServiceStateChanged(ServiceState serviceState) {

        }

        public void onCallForwardingIndicatorChanged(boolean cfi) {
        }


        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
                //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
                isPhoneCalling = true;

            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }

            }
        }


        //};
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //called here to get info(mainly lat and long) of all sites and display them on map
        new GetData().execute("http://192.168.43.197/test.php?getData");
        new GetMoreData().execute("http://192.168.43.197/test.php?getMData&cellid=" + mCurCID);

       ////getting your location
        LocationService gps = new LocationService(this);
        if(gps.canGetLocation()) { // gps enabled} // return boolean true/false
            ///// get GPS coord
            double latitude = gps.getLatitude(); // returns latitude
            double longitude = gps.getLongitude(); // returns longitude

            // Add a marker in Sydney and move the camera
            LatLng loc = new LatLng(latitude, longitude);
            firstLoc = mMap.addMarker(new MarkerOptions().position(loc).title("Your" +
                    " Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(loc)       // Sets the center of the map to Mountain View
                    .zoom(16)                   // Sets the zoom
                            // Sets the orientation of the camera to east
                            // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(bonapris));
        //mMap.setOnMyLocationChangeListener(myLocationChangeListener);



    }else{
            //// ALERT DIALOG
            gps.showSettingsAlert();}
        /////stop using GPS
        gps.stopUsingGPS();
    }


    /*
    Private class to get all data(site) about 3G sites from the database and puts markers for each site on the map
     also calls GetMoreData to get all data(sectors) from nodebfddcell table for a site with a parcticular cell_id and the PSC for the sector
     and GetMoreData class calls ParticularSite class which uses the the sitename from GetMoreData to get lats,longs,azim of
     the sectors and displays them on the map
      */
    private class GetData extends AsyncTask<String, Void,Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Show progress dialog

            pDialog = new ProgressDialog(RFmeasurements.this);
            //pDialog.setMessage("Loading data...");
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        //Here,the makeServiceCall() method is called to get the json from url,
        //parse the JSON and add to HashMap to show the results in List View.
        @Override
        protected Void doInBackground(String... params) {

            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];
            // String url2 = params[1];//new GetComment().execute("http://192.168.43.197/recipes.php?detSharedComment&recipe_id="+recipe_id);
            // System.out.println("THE UUUUURRRRRRRRRRLL IS "+urll);
            Log.d("URLLLLLLLLLL ", urll);
            //sharedlist.clear();

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

                            JSONObject jo = jsonarr.getJSONObject(i);

                            options.add(new Options(jo.getString(TAG_SITE_ID), jo.getString(TAG_SITE_NAME), jo.getString(TAG_LATITUDE)
                                    , jo.getString(TAG_LONGITUDE), jo.getString(TAG_ALTITUDE), jo.getString(TAG_AZIMUTH), jo.getString(TAG_BTS_ID)));

                        }

                        runOnUiThread(new Runnable() {

                            public void run() {


                                for(int i=1; i< options.size(); i++) {
                                    try {

                                        if (options.get(i).getLatitude() != "null" && options.get(i).getLongitude() != "null") {

                                            Double bts_lat = Double.parseDouble(options.get(i).getLatitude());
                                            Double bts_long = Double.parseDouble(options.get(i).getLongitude());
                                            String site_name = options.get(i).getSiteName();

                                            // Add a marker in Sydney and move the camera
                                            LatLng site_loc = new LatLng(bts_lat, bts_long);
                                            //out.println("" + bts_lat + "," + bts_long);
                                            mMap.addMarker(new MarkerOptions().position(site_loc).title(options.get(i).getSiteName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                            //new GetMoreData(site_name).execute("http://192.168.43.197/test.php?getMData&cellid=" + mCurCID);

                                        }

                                    }
                                    catch(Exception e){
                                        e.printStackTrace();
                                    }

                                }

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.print("========================================================");
            System.out.print("\nLISTTTTTTTT OFFFFF OPTIONSSSSS" + options.size());
            //getCellInfo();
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

    //Private class to get all data( psc, site(nodeb) name) from the node_b_fddcell table with the current cell id
    // and ParticulasSite uses the sitename to get the lats and longs of the diff sectors
    private class GetMoreData extends AsyncTask<String, Void,Void> {

        public GetMoreData() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Show progress dialog
           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {*/

           //     }
           // });
            pDialog = new ProgressDialog(RFmeasurements.this);

        }

        //Here,the makeServiceCall() method is called to get the json from url,
        //parse the JSON and add to HashMap to show the results in List View.
        @Override
        protected Void doInBackground(String... params) {

            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];
            //new GetComment().execute("http://192.168.43.197/recipes.php?detSharedComment&recipe_id="+recipe_id);
            // System.out.println("THE UUUUURRRRRRRRRRLL IS "+urll);
            Log.d("URLLLLLLLLLL ", urll);
            //sharedlist.clear();
            moptions = new ArrayList<MoreOptions>();
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

                            JSONObject jo = jsonarr.getJSONObject(i);
                            moptions.add(new MoreOptions(jo.optString(TAG_NODEB_NAME), jo.optString(TAG_CID), jo.optString(TAG_PSC)));

                            runOnUiThread(new Runnable() {

                                public void run() {

                                    /*refreshStationInfo();
                                    out.println("CEEEELLLLL IIIIDDDDD" + mCurCID);

                                    Toast.makeText(getApplicationContext(), " CELL ID = "
                                            + mCurCID + " PrevCId =" + PrevCid, Toast.LENGTH_LONG).show();*/

                                    //new GetMoreData().execute("http://192.168.43.197/test.php?getMData&cellid=" + mCurCID);
                                    for (int i = 0; i <= moptions.size(); i++) {
                                        try {

                                            String sitename = moptions.get(i).getSitename();
                                            //Double psc = Double.parseDouble(moptions.get(i).getPSC());
                                            psc = moptions.get(i).getPSC();
                                            System.out.println("SSSSSSSSSSITTTTTEEEENNNAMMMMMMMMMMMMEEEEEE " + sitename);
                                            //if (sitename == site_name) {
                                            // System.out.println("PPPPPSSSSSCCCCCCCCCCCCCCCC "+psc);
                                            new ParticularSiteData(psc).execute("http://192.168.43.197/test.php?getSite&sitename=" + sitename);
                                            //write another asynctask getting info from nodeb fdd cell table and display the results on the gmap
                                            // new ParticularSiteData(psc).execute("http://192.168.43.197/test.php?getSite&sitename=" + sitename);
                                            //}

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }

                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.print("========================================================");
            System.out.print("\nLISTTTTTTTT of more DATA" + moptions.size());

            //getCellInfo();
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }


    //Private class to get all data from the node_b_fddcell table
    private class ParticularSiteData extends AsyncTask<String, Void,Void> {
        public String value;

        public ParticularSiteData(String value) {
            this.value = value;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Show progress dialog
            psc_value = (TextView) findViewById(R.id.psc_value);
            pDialog = new ProgressDialog(RFmeasurements.this);

        }

        @Override
        protected Void doInBackground(String... params) {

            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];
            //new GetComment().execute("http://192.168.43.197/recipes.php?detSharedComment&recipe_id="+recipe_id);
            // System.out.println("THE UUUUURRRRRRRRRRLL IS "+urll);
            Log.d("URLLLLLLLLLL ", urll);
            //sharedlist.clear();

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

                            JSONObject jo = jsonarr.getJSONObject(i);
                            site_info.add(new Options(jo.getString(TAG_SITE_ID), jo.getString(TAG_SITE_NAME), jo.getString(TAG_LATITUDE)
                                    , jo.getString(TAG_LONGITUDE), jo.getString(TAG_ALTITUDE), jo.getString(TAG_AZIMUTH), jo.getString(TAG_BTS_ID)));
                        }

                        runOnUiThread(new Runnable() {

                            public void run() {

                                if (value != null) {

                                    psc_value.setText(value);

                                } else {

                                    psc_value.setText("");
                                }

                                for (int i = 0; i < site_info.size(); i++)
                                    if (site_info.get(i).getLatitude() != "null" && site_info.get(i).getLongitude() != "null") {

                                        Double bts_lati = Double.parseDouble(site_info.get(i).getLatitude());
                                        Double bts_longi = Double.parseDouble(site_info.get(i).getLongitude());
                                        Double bts_azi = Double.parseDouble(site_info.get(i).getAzimuth());
                                         bts_azi1 = Double.parseDouble(site_info.get(site_info.size() - 3).getAzimuth());
                                         bts_azi2 = Double.parseDouble(site_info.get(site_info.size() - 2).getAzimuth());
                                         bts_azi3 = Double.parseDouble(site_info.get(site_info.size() - 1).getAzimuth());

                                        LatLng site_loc = new LatLng(bts_lati, bts_longi);
                                         ll = translate(site_loc, 250, bts_azi);
                                         lll = translate(site_loc, 250, bts_azi);
                                         llll = translate(site_loc, 250, bts_azi);

                                         ll1 = translate(site_loc, 250, bts_azi1);
                                         lll2 = translate(site_loc, 250, bts_azi2);
                                         llll3 = translate(site_loc, 250, bts_azi3);

                                        if(mPolylines.size()>0){

                                            for(Polyline polyline: mPolylines){

                                            polyline.remove();

                                        }
                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(ll1).color(Color.rgb(155, 12, 130)).width(3)));
                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(lll2).color(Color.rgb(155, 12, 130)).width(3)));
                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(llll3).color(Color.rgb(155, 12, 130)).width(3)));
                                            String sitenames = site_info.get(site_info.size()-1).getSiteName();

                                            new GetPscs(ll1,lll2,llll3).execute("http://192.168.43.197/test.php?getPscs&sitename=" + sitenames);

                                        }else {

                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(ll).color(Color.rgb(155, 12, 130)).width(3)));
                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(lll).color(Color.rgb(155, 12, 130)).width(3)));
                                            mPolylines.add(mMap.addPolyline(new PolylineOptions().add(site_loc).add(llll).color(Color.rgb(155, 12, 130)).width(3)));
                                            String sitename = site_info.get(site_info.size()-1).getSiteName();
                                            new GetPscs(ll,lll,llll).execute("http://192.168.43.197/test.php?getPscs&sitename=" + sitename);

                                        }

                                    }

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.print("========================================================");
            System.out.print("\nLISTTTTTTTT OFFFFF SITENAMMMMEEEEE" + site_info.size());
            out.print("PSCCCCCCCCCCCCCCCCCCCCCC: " + value);


            //getCellInfo();
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

    //Private class to get all the pscs of the different sectors of the serving site
    private class GetPscs extends AsyncTask<String, Void,Void> {
        LatLng firstSector;
        LatLng secondSector;
        LatLng thirdSector;
        public GetPscs(LatLng firstSector, LatLng secondSector, LatLng thirdSector) {

             this.firstSector= firstSector;
             this.secondSector= secondSector;
             this.thirdSector=thirdSector;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            moptions = new ArrayList<MoreOptions>();
            // Get the string from the intent
            try {
                String jsonStr = sh.makeServiceCall(urll, ServiceHandler.GET);

                Log.d("Response: ", "> " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray jsonarr = new JSONArray(jsonStr);

                        //looping thro all comments
                        for (int i = 0; i < jsonarr.length(); i++) {

                            JSONObject jo = jsonarr.getJSONObject(i);
                            moptions.add(new MoreOptions(jo.optString(TAG_NODEB_NAME), jo.optString(TAG_CID), jo.optString(TAG_PSC)));

                            runOnUiThread(new Runnable() {

                                public void run() {


                                        for (int i = 0; i <= 3; i++) {
                                        try {

                                            String sitename = moptions.get(i).getSitename();
                                            //Double psc = Double.parseDouble(moptions.get(i).getPSC());
                                            psc = moptions.get(i).getPSC();
                                            System.out.println("SEERRRRRRRRRRRRRRRRRRRRRRRVVVVVVVVVVVVVVVVVRRRR " + firstSector);

                                            LinearLayout distanceMarkerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.distance_marker, null);

                                            distanceMarkerLayout.setDrawingCacheEnabled(true);
                                            distanceMarkerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                            distanceMarkerLayout.layout(0, 0, distanceMarkerLayout.getMeasuredWidth(), distanceMarkerLayout.getMeasuredHeight());
                                            distanceMarkerLayout.buildDrawingCache(true);

                                            TextView positionDistance = (TextView) distanceMarkerLayout.findViewById(R.id.psc_marker);

                                            positionDistance.setText(psc);

                                            Bitmap flagBitmap = Bitmap.createBitmap(distanceMarkerLayout.getDrawingCache());
                                            distanceMarkerLayout.setDrawingCacheEnabled(false);
                                            BitmapDescriptor flagBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(flagBitmap);
                                            if(i==0) {
                                                centerOneMarker = mMap.addMarker(new MarkerOptions()
                                                        .position(firstSector)
                                                        .icon(flagBitmapDescriptor));
                                                System.out.println("HHHHHHHHHHEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRREEEEEEEEEEEE\n" +
                                                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE AREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                                            }else if(i==1){
                                                centerOneMarker = mMap.addMarker(new MarkerOptions()
                                                        .position(secondSector)
                                                        .icon(flagBitmapDescriptor));
                                            }else if(i==2){
                                                 centerOneMarker = mMap.addMarker(new MarkerOptions()
                                                        .position(thirdSector)
                                                        .icon(flagBitmapDescriptor));
                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }

                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.print("========================================================");
            System.out.print("\nLISTTTTTTTT of more DATA" + moptions.size());

            //getCellInfo();
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }



    // getting all info about the cell
    protected  void refreshStationInfo() {
        StringBuilder telephonyInfo = new StringBuilder();

        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_CELL_LOCATION);
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_DATA_ACTIVITY);
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_CELL_INFO );
            mTelephonyManager.listen(MyListener,
                    PhoneStateListener.LISTEN_CALL_STATE);

        }

        mMCC = mTelephonyManager.getNetworkOperator().substring(0, 3);
        mMNC = mTelephonyManager.getNetworkOperator().substring(mMCC.length());

        switch(mTelephonyManager.getNetworkType()){
            case 0: mCurNetwork = "HSPA";
                    network.setText(mCurNetwork);
                    break;
            case 1:  mCurNetwork = "GPRS";
                    network.setText(mCurNetwork);
                    break;
            case 2:  mCurNetwork = "EDGE";
                    network.setText(mCurNetwork);
                    break;
            case 3:  mCurNetwork = "UMTS";
                    network.setText(mCurNetwork);
                    break;
            case 4:  mCurNetwork = "CDMA";
                    network.setText(mCurNetwork);
                     break;
            case 5:  mCurNetwork = "EVDO_0";
                    network.setText(mCurNetwork);
                    break;
            case 6:  mCurNetwork = "EVDO_A";
                    network.setText(mCurNetwork);
                    break;
            case 7:  mCurNetwork = "1xRTT";
                    network.setText(mCurNetwork);
                    break;
            case 8:  mCurNetwork = "HSDPA";
                    network.setText(mCurNetwork);
                    break;
            case 9:  mCurNetwork = "HSUPA";
                     network.setText(mCurNetwork);
                    break;
            case 10:  mCurNetwork = "HSPA";
                    network.setText(mCurNetwork);
                    break;
            case 11:  mCurNetwork = "iDen";
                    network.setText(mCurNetwork);
                    break;
            case 12:  mCurNetwork = "EVDO_B";
                    network.setText(mCurNetwork);
                    break;
            case 13:  mCurNetwork= "LTE";
                    network.setText(mCurNetwork);
                    break;
            case 14:  mCurNetwork = "eHRPD";
                    network.setText(mCurNetwork);
                    break;
            case 15:  mCurNetwork = "HSPA+";
                    network.setText(mCurNetwork);
                    break;
        }

        Log.i("LOG OF", mMCC + " " + mMNC);

        //telephonyInfo.append("Network Type: " + mCurNetwork + "\r\n\n");
        //telephonyInfo.append("Mobile Country Code: " + mMCC + "\r\n");
        //telephonyInfo.append("Mobile Network Code: " + mMNC + "\r\n\n");

        if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            GsmCellLocation location = (GsmCellLocation) mTelephonyManager
                    .getCellLocation();
            mCurLAC = location.getLac();
            mCurCID = location.getCid()& 0xffff;
            mCurPsc = location.getPsc();
            Log.i( "LOG OF ","Current Location LAC:" + mCurLAC + ", CID:" + mCurCID);

        } else
            telephonyInfo.append("It's not a GSM phone!\r\n");


    }


    // method for drawing lines at a particular angle /heading, dist is how long shld the line be
    public static LatLng translate(LatLng loc, double distance, double heading){
        double EARTH_RADIUS = 6378100.0;
        heading = Math.toRadians(heading);
        distance = distance/EARTH_RADIUS;
        // http://williams.best.vwh.net/avform.htm#LL
        double fromLat = Math.toRadians(loc.latitude);
        double fromLng = Math.toRadians(loc.longitude);
        double cosDistance = Math.cos(distance);
        double sinDistance = Math.sin(distance);
        double sinFromLat = Math.sin(fromLat);
        double cosFromLat = Math.cos(fromLat);
        double sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * Math.cos(heading);
        double dLng = Math.atan2(
                sinDistance * cosFromLat * Math.sin(heading),
                cosDistance - sinFromLat * sinLat);
        return new LatLng(Math.toDegrees(Math.asin(sinLat)), Math.toDegrees(fromLng + dLng));
    }

    //method for showing overflow menu
    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem itemsStart = menu.findItem(R.id.start);
        MenuItem itemsStop = menu.findItem(R.id.stop);
        MenuItem itemsBoth = menu.findItem(R.id.both);
        MenuItem itemsPS = menu.findItem(R.id.data);
        MenuItem itemsCS = menu.findItem(R.id.voice);
        test = getIntent().getExtras().getString("tests");

        if(test.equals("stationary")){
            itemsStart.setVisible(false);
            itemsStop.setVisible(false);

        }else{

            itemsBoth.setVisible(false);
            itemsPS.setVisible(false);
            itemsCS.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        String message = "";
        if (item.getItemId() == R.id.start) {

            db = new MyDBHelper(getApplicationContext());
            Info info =  db.getInfo();
            int test_id = (int) info.getId();
            Toast.makeText(getApplicationContext(),"Mobility" +
                    " Test Started", Toast.LENGTH_LONG).show();

            canSave = getIntent().getExtras().getInt("canSave");
            makeCall = getIntent().getExtras().getInt("makeCall");
            number = getIntent().getExtras().getString("number");
            ftpul = getIntent().getExtras().getInt("ftpul");
            duration = getIntent().getExtras().getInt("duration");

            if(ftpul == 1){

                WifiManager wifiManager ;
                wifiManager  = (WifiManager)this.getSystemService(this.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);        //True - to enable WIFI connectivity .
                String pscValue = psc_value.getText().toString();
                Intent intent = new Intent(getApplicationContext(),ViewProgress.class);
                intent.putExtra("psc", pscValue);
                intent.putExtra("lac", mCurLAC);
                intent.putExtra("mnc",mMNC);
                intent.putExtra("mcc", mMCC);
                intent.putExtra("strength",myProgress);
                intent.putExtra("cid", mCurCID);
                intent.putExtra("test_id", test_id);
                startActivity(intent);

            }

        }else if (item.getItemId() == R.id.both) {
            String pscValue = psc_value.getText().toString();
            Intent intent = new Intent(getApplicationContext(),ViewProgress.class);
            intent.putExtra("psc", pscValue);
            intent.putExtra("lac", mCurLAC);
            intent.putExtra("mnc",mMNC);
            intent.putExtra("mcc", mMCC);
            intent.putExtra("strength",myProgress);
            intent.putExtra("cid", mCurCID);
            intent.putExtra("test_id", test_id);
            startActivity(intent);

        }else if (item.getItemId() == R.id.data) {
            String pscValue = psc_value.getText().toString();
        Intent intent = new Intent(getApplicationContext(),PsDataCalls.class);
            intent.putExtra("psc", pscValue);
            intent.putExtra("lac", mCurLAC);
            intent.putExtra("mnc",mMNC);
            intent.putExtra("mcc", mMCC);
            intent.putExtra("strength",myProgress);
            intent.putExtra("cid", mCurCID);
            intent.putExtra("test_id", test_id);
        startActivity(intent);

        }else if (item.getItemId() == R.id.voice) {
            String pscValue = psc_value.getText().toString();
            Intent intent = new Intent(getApplicationContext(), CsVoiceCalls.class);
            intent.putExtra("psc", pscValue);
            intent.putExtra("lac", mCurLAC);
            intent.putExtra("mnc",mMNC);
            intent.putExtra("mcc", mMCC);
            intent.putExtra("strength",myProgress);
            intent.putExtra("cid", mCurCID);
            intent.putExtra("test_id", test_id);
            startActivity(intent);

        }else if (item.getItemId() == R.id.stop) {

            new Reporting().execute();

        }else if (item.getItemId() == R.id.db) {
            Intent dbmanager = new Intent(getApplicationContext() ,AndroidDatabaseManager.class);
            startActivity(dbmanager);
        }
        else {
            message = "Why would you select that!?";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.show();
        }
        // show message via toast


        return true;
    }

    // abstract method when mplementing navigation drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // makes text to appear on progress bar and shapes the progress bar
    public void doSomeWork(int rssi){

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (myProgress < 0) {
                    //myProgress += 1;
                    // Update the progress bar
                    myHandler.post(new Runnable() {
                        public void run() {
                            //System.out.println("My Progess bar"+myProgress);
                            Resources res = getResources();
                            if(myProgress>-92) {
                                Drawable drawable = res.getDrawable(R.drawable.pb_background);
                                TextProgressBar pb = (TextProgressBar) findViewById(R.id.progressBar);
                                pb.setProgress(myProgress+113);   // Main Progress
                                pb.setSecondaryProgress(myProgress+113); // Secondary Progress
                                pb.setMax(62); // Maximum Progress
                                pb.setProgressDrawable(drawable);

                                pb.setText(myProgress + "");
                            }else if(myProgress<-92 && myProgress>-102){
                                Drawable drawable = res.getDrawable(R.drawable.less_background);
                                TextProgressBar pb = (TextProgressBar) findViewById(R.id.progressBar);
                                pb.setProgress(myProgress+113);   // Main Progress
                                pb.setSecondaryProgress(myProgress+113); // Secondary Progress
                                pb.setMax(62); // Maximum Progress
                                pb.setProgressDrawable(drawable);

                                pb.setText(myProgress + "");
                            }else if(myProgress<-102){
                                Drawable drawable = res.getDrawable(R.drawable.lesser_background);
                                TextProgressBar pb = (TextProgressBar) findViewById(R.id.progressBar);
                                pb.setProgress(myProgress+113);   // Main Progress
                                pb.setSecondaryProgress(myProgress+113); // Secondary Progress
                                pb.setMax(62); // Maximum Progress
                                pb.setProgressDrawable(drawable);

                                pb.setText(myProgress + "");
                            }

                        }
                    });
                    try {
                        //Display progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    public static float distBetween(LatLng pos1, LatLng pos2) {
        return distBetween(pos1.latitude, pos1.longitude, pos2.latitude,
                pos2.longitude);
    }

    /** distance in meters **/
    public static float distBetween(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return (float) (dist * meterConversion);
    }

    public void lastOutCall() {

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
            String phNumber = managedCursor.getString(number);
            String callDuration = managedCursor.getString(duration1);
            String dir = null;
            sb.append("\nPhone Number:--- " + phNumber + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
            Log.i("*****Call Summary******", "Call Duration is:-------" + sb);
            mobility_duration += "\n\nPhone Number:--- " + phNumber +
                    "\n Call Duration in sec:--- " + callDuration;
        }

       // db.updateSingle(mobility_duration);
        System.out.println("MOBLITY DURATION"+mobility_duration);

        managedCursor.close();

        }

    public void lastCall() {

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


    }

    private class Reporting extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(RFmeasurements.this);

            dialog.setMessage("Generating Report. Please Wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                /*runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });*/
                Report report = new Report(RFmeasurements.this);
                duration = getIntent().getExtras().getInt("duration");
                if (duration == 1) {
                    try {
                        report.writeDedicatedReport(getApplicationContext());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    try {

                        //report. new IdleReport(getApplicationContext()).execute();
                        report.writeIdleReport(getApplicationContext());
                        dialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                canSave = 0;


            }catch (Exception e){
                e.printStackTrace();
            }
            return  "";
        }
            @Override
            protected void onPostExecute(String params) {
                dialog.dismiss();
                fabm.setVisibility(View.VISIBLE);
                fabm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String filename = "Mobility Test.xls";
                        final File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                        final Uri path = Uri.fromFile(filelocation);
                        final CharSequence[] itemss = {"View Report", "Email Report"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(RFmeasurements.this);
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
        }


    public void onBackPressed(){

        final CharSequence[] itemss = {"Repeat Test", "Go to HomeScreen"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RFmeasurements.this);
        builder.setTitle("Are you sure you want to leave?");
        builder.setItems(itemss, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {

                    Intent intent = new Intent(getApplicationContext() , RFmeasurements.class);
                    intent.putExtra("tests", test);
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











