package com.testing.alcatel.alufieldtesting;

/**
 * Created by ivan-clare on 25/01/2016.
 */
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStateBroadcastReceiver";
    Context mContext;
    String incoming_nr;
    private int prev_state;
    ViewProgress viewProgress = new ViewProgress();
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    ViewProgress yourMain = null;
    Boolean incoming = false;
    int callType;

    public void PhoneStateBroadcastReceiver(Context context){
        this.mContext = context;


    }
    public void setViewProgressHandler(ViewProgress main){
        yourMain = main;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); //TelephonyManager object
        CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager

        Bundle bundle = intent.getExtras();
        String phoneNr= bundle.getString("incoming_number");
        Log.v("TAG", "phoneNr: "+phoneNr);
        mContext=context;
    }

    /* Custom PhoneStateListener */
    public class CustomPhoneStateListener  extends PhoneStateListener {

        private static final String TAG = "CustomPhoneStateListener";

        @Override
        public void onCallStateChanged(int state, String incomingNumber){

            if(incomingNumber!=null&&incomingNumber.length()>0) incoming_nr=incomingNumber;

            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:

                    Log.d("TAG", "CALL_STATE_RINGING");
                    System.err.print("Incoming call, phone ringing...... ");
                    prev_state=state;
                    incoming = true;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("TAG", "CALL_STATE_OFFHOOK");
                    System.err.print("call going on now.....");

                    prev_state=state;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:

                    Log.d("TAG", "CALL_STATE_IDLE==>" + incoming_nr);

                    if((prev_state==TelephonyManager.CALL_STATE_OFFHOOK)){

                        prev_state=state;
                        callType = yourMain.callType;
                        //Answered Call which is ended
                        System.err.print("Answered call just ended. Update the ui now");
                        final Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (incoming) {
                                    yourMain.lastInCall(callType);

                                }else {

                                    yourMain.lastCall(callType);

                                }

                            }
                        }, 2000);
                    }

                    if((prev_state==TelephonyManager.CALL_STATE_RINGING)){
                        prev_state=state;
                        //Rejected or Missed call
                        System.err.print("Missed or rejected call just ended. Update the ui now");
                        //yourMain.lastCall();
                        //yourMain.lastInCall();
                    }
                    break;

            }
        }
    }


}
