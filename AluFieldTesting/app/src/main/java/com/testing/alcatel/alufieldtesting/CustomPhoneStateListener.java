package com.testing.alcatel.alufieldtesting;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ivan-clare on 25/01/2016.
 */
public class CustomPhoneStateListener  extends PhoneStateListener {

    private static final String TAG = "CustomPhoneStateListener";
    Context mContext;
    String incoming_nr;
    private int prev_state;
    ViewProgress viewProgress = new ViewProgress();


    @Override
    public void onCallStateChanged(int state, String incomingNumber){

        if(incomingNumber!=null&&incomingNumber.length()>0) incoming_nr=incomingNumber;

        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("TAG", "CALL_STATE_RINGING");
                System.err.print("Incoming call, phone ringing...... ");
                prev_state=state;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("TAG", "CALL_STATE_OFFHOOK");
                System.err.print("call going on now.....");
                prev_state=state;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("TAG", "CALL_STATE_IDLE==>"+incoming_nr);
                if((prev_state==TelephonyManager.CALL_STATE_OFFHOOK)){
                    prev_state=state;
                    //Answered Call which is ended
                    System.err.print("Answered call just ended. Update the ui now");
                   // viewProgress.lastCall();

                }
                if((prev_state==TelephonyManager.CALL_STATE_RINGING)){
                    prev_state=state;
                    //Rejected or Missed call
                    System.err.print("Missed or rejected call just ended. Update the ui now");
                    //viewProgress.lastCall();
                }
                break;

        }
    }
}
