package com.testing.alcatel.alufieldtesting;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import it.sauronsoftware.ftp4j.FTPClient;

import static java.lang.System.*;

/**
 *  Class for displaying cell information; mnc, mcc, curCID
 * that can be chosen to go to different parts of the application
 *
 * @author  Ngong Ivan-Clare Wirdze
 * @version 1.0
 * @since   03/11/2015
 */

public class CellInformation extends AppCompatActivity {

    /* This variables need to be global, so we can used them onResume and onPause method to
       stop the listener */
    double dBmlevel;
    double asulevel;
    public static final String TAG = "TelephonyDemoActivity";

    TelephonyManager mTelephonyManager = null;
    /**
     * Mobile Network Code
     */
    public static String mMNC;
    /**
     * Mobile Country Code
     */
    public static String mMCC;
    /**
     * Current Location Area Code
     */
    public static int mCurLAC;
    /**
     * Current Location Cell ID
     */
    public static int mCurCID;
    /**
     * Current Location Scrambling code
     */
    int mCurPsc;
    int mNetwork;
    public static String mCurNetwork = "NO NETWORK FOUND";

    List<NeighboringCellInfo> mNeighboringCellInfo;

    TextView mTelephonyInfo;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cell_information);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        mTelephonyInfo = (TextView) findViewById(R.id.info);
        //btn = (Button) findViewById(R.id.button);

        makeActionOverflowMenuShown();


        /*Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        //TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(MyListener, PhoneStateListener.LISTEN_CELL_INFO );// Requires API 17;)

        GsmCellLocation cellLoc = (GsmCellLocation)mTelephonyManager.getCellLocation();
        cellLoc.getCid();
        Toast.makeText(getApplicationContext(), "CELL Id = "+cellLoc.getCid(), Toast.LENGTH_SHORT).show();*/

    }


    /**
     * Class for listening to the signal strength
     * <brNote><br/>
     * Has not been used
     */
    PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCellLocationChanged(CellLocation location) {
            // TODO Auto-generated method stub
            super.onCellLocationChanged(location);
            CellInformation.this.refreshStationInfo();
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            // TODO Auto-generated method stub
            super.onSignalStrengthsChanged(signalStrength);

           /* int rssi = -113 + 2 * signalStrength.getGsmSignalStrength();
             Toast.makeText(getApplicationContext(), " RSSI dbm= "
              + rssi+" Quality dbm ="+String.valueOf(signalStrength.getCdmaEcio()), Toast.LENGTH_SHORT).show();*/

            //+"  CDMA dbm ="+String.valueOf(signalStrength.getCdmaDbm()), Toast.LENGTH_SHORT).show();
            //  +"  CDMA dbm ="+String.valueOf(signalStrength.getCdmaEcio()), Toast.LENGTH_SHORT).show();

            CellInformation.this.refreshStationInfo();

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
        }

    };

    /**
     * Method for getting cell information and displaying on screen
     */
    public void refreshStationInfo() {
        StringBuilder telephonyInfo = new StringBuilder();

        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_CELL_LOCATION);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_DATA_ACTIVITY);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_CELL_INFO);
            mTelephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }

        mMCC = mTelephonyManager.getNetworkOperator().substring(0, 3);
        mMNC = mTelephonyManager.getNetworkOperator().substring(mMCC.length());

        switch (mTelephonyManager.getNetworkType()) {
            case 0:
                mCurNetwork = "HSPA";
                break;
            case 1:
                mCurNetwork = "GPRS";
                break;
            case 2:
                mCurNetwork = "EDGE";
                break;
            case 3:
                mCurNetwork = "UMTS";
                break;
            case 4:
                mCurNetwork = "CDMA";
                break;
            case 5:
                mCurNetwork = "EVDO_0";
                break;
            case 6:
                mCurNetwork = "EVDO_A";
                break;
            case 7:
                mCurNetwork = "1xRTT";
                break;
            case 8:
                mCurNetwork = "HSDPA";
                break;
            case 9:
                mCurNetwork = "HSUPA";
                break;
            case 10:
                mCurNetwork = "HSPA";
                break;
            case 11:
                mCurNetwork = "iDen";
                break;
            case 12:
                mCurNetwork = "EVDO_B";
                break;
            case 13:
                mCurNetwork = "LTE";
                break;
            case 14:
                mCurNetwork = "eHRPD";
                break;
            case 15:
                mCurNetwork = "HSPA+";
                break;
        }

        Log.i(TAG, mMCC + " " + mMNC);

        telephonyInfo.append("Network Type: " + mCurNetwork + "\r\n\n");
        telephonyInfo.append("Mobile Country Code: " + mMCC + "\r\n");
        telephonyInfo.append("Mobile Network Code: " + mMNC + "\r\n\n");

        if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            GsmCellLocation location = (GsmCellLocation) mTelephonyManager
                    .getCellLocation();
            mCurLAC = location.getLac();
            mCurCID = location.getCid() & 0xffff;
            mCurPsc = location.getPsc();


            Log.i(TAG, "Current Location LAC:" + mCurLAC + ", CID:" + mCurCID);

            telephonyInfo.append("Current Location Area Code: " + mCurLAC
                    + "\r\n");
            telephonyInfo.append("Current Location Cell ID: " + mCurCID
                    + "\r\n");
            telephonyInfo.append("Current Scrambling Code: " + mCurPsc
                    + "\r\n\n");


        } else
            telephonyInfo.append("It's not a GSM phone!\r\n");

        mNeighboringCellInfo = mTelephonyManager.getNeighboringCellInfo();

        if (!mNeighboringCellInfo.isEmpty()) {
            int i = 0;
            for (NeighboringCellInfo cellInfo : mNeighboringCellInfo) {
                Log.i(TAG,
                        "Neighbor CellInfo No." + i + " LAC:"
                                + cellInfo.getLac() + ", CID:"
                                + cellInfo.getCid() + ", RSSI:"
                                + cellInfo.getRssi());
                i++;
                telephonyInfo.append("\nNeighbor CellInfo No." + i
                        + ",\n Location Area Code: " + cellInfo.getLac()
                        + ", \nCell ID: " + cellInfo.getCid()
                        + ", \nSignal Strength: " + cellInfo.getRssi() + "\r\n");
               /* Intent intent = new Intent(getApplicationContext(),RFmeasurements.class);
                intent.putExtra("curlac", mCurLAC);
                intent.putExtra("curcid", mCurCID);
                intent.putExtra("curpsc", mCurPsc);
                //intent.putExtra("rssi", rssi);
                startActivity(intent);*/
            }
        } else
            telephonyInfo.append("No Neighbor Cell Info!");

        mTelephonyInfo.setText(telephonyInfo.toString());
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        refreshStationInfo();
        super.onResume();
    }

    /* Called when the application is minimized */
    @Override
    protected void onPause() {
        super.onPause();
        //Telp.listen(MyListener, PhoneStateListener.LISTEN_NONE);
    }

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
}


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        String message = "";
        if (item.getItemId() == R.id.start) {
            message = "You selected option 1!";
        }
        else if (item.getItemId() == R.id.view) {
            Intent intent = new Intent(getApplicationContext(),ViewProgress.class);
            startActivity(intent)     ;

        }else if (item.getItemId() == R.id.stop) {
            message = "You selected option 2!";
        }else {
            message = "Why would you select that!?";
        }

        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
        return true;
    }*/




// getting AllcellInfo works only for CDMA/LTE devices (not for GSM/LTE phones)
// using getCellLocation() works for GSM/UMTS and CDMA phones but not for samsung phones
// Using getNeighbouringCellInfo works for GSM/UMTS phones/cells only
/*List<CellInfo> cellList = Telp.getAllCellInfo();
        CellInfoWcdma cellinfoWcdma = null;
        if(cellList!=null && !cellList.isEmpty()){
            cellinfoWcdma = (CellInfoWcdma)Telp.getAllCellInfo().get(0);
            CellSignalStrengthWcdma cellSignalStrengthWcdma = cellinfoWcdma.getCellSignalStrength();
            if(Build.VERSION.SDK_INT>=18){
                int wcdmastrength = cellSignalStrengthWcdma.getDbm();

               // Toast.makeText(getApplicationContext(), "WCDMA dbm = \n"
                        //+wcdmastrength, Toast.LENGTH_SHORT).show();
            }
        }

        CellInfoWcdma wcdma;

        if (Telp.getAllCellInfo().get(0).getClass().equals(CellInfoWcdma.class)){
            wcdma = (CellInfoWcdma)Telp.getAllCellInfo().get(0);
            String s = "UMTS (3G): ASU = " + wcdma.getCellSignalStrength().getAsuLevel() + ", RSSI = " + wcdma.getCellSignalStrength().getDbm();
            Toast.makeText(getApplicationContext(), "WCDMA dbm = "+s, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please switch your network to " +
                    "3G in Android's settings", Toast.LENGTH_SHORT).show();
        }

        //retrieve a reference to an instance of TelephonyManager
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();

        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();
        int psc = cellLocation.getPsc();

        int phoneType = Telp.getPhoneType();
        //Toast.makeText(getApplicationContext(), "Please switch your network to "+phoneType, Toast.LENGTH_SHORT).show();

        /*List<CellInfo> cellInfoList = Telp.getAllCellInfo();
        //Checking if list values are not null
        //if (cellInfoList != null) {
            for (final CellInfo info : cellInfoList) {
                if (info instanceof CellInfoGsm) {
                    //GSM Network
                    CellSignalStrengthGsm cellSignalStrength = ((CellInfoGsm)info).getCellSignalStrength();
                    dBmlevel = cellSignalStrength.getDbm();
                    asulevel = cellSignalStrength.getAsuLevel();
                    Toast.makeText(getApplicationContext(), "GSM dbm = "+dBmlevel, Toast.LENGTH_SHORT).show();
                }
                else if (info instanceof CellInfoCdma) {
                    //CDMA Network
                    CellSignalStrengthCdma cellSignalStrength = ((CellInfoCdma)info).getCellSignalStrength();
                    dBmlevel = cellSignalStrength.getDbm();
                    asulevel = cellSignalStrength.getAsuLevel();
                    Toast.makeText(getApplicationContext(), "WCDMA dbm = "+dBmlevel, Toast.LENGTH_SHORT).show();
                }
                else if (info instanceof CellInfoLte) {
                    //LTE Network
                    CellSignalStrengthLte cellSignalStrength = ((CellInfoLte)info).getCellSignalStrength();
                    dBmlevel = cellSignalStrength.getDbm();
                    asulevel = cellSignalStrength.getAsuLevel();
                    Toast.makeText(getApplicationContext(), "WCDMA dbm = "+dBmlevel, Toast.LENGTH_SHORT).show();
                }
                else if  (info instanceof CellInfoWcdma) {
                    //WCDMA Network
                    CellSignalStrengthWcdma cellSignalStrength = ((CellInfoWcdma)info).getCellSignalStrength();
                    dBmlevel = cellSignalStrength.getDbm();
                    asulevel = cellSignalStrength.getAsuLevel();
                    Toast.makeText(getApplicationContext(), "WCDMA dbm = "+dBmlevel, Toast.LENGTH_SHORT).show();
                }
                else{
                    //Developed as a Cordova plugin, that's why I'm using callbackContext
                    out.print("Unknown type of cell signal.");
                }
            }
        //}*/








