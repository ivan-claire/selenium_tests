package com.testing.alcatel.alufieldtesting;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;

/**
 * Created by ivan-clare on 18/01/2016.
 */
public class ftpUpload {
    // get the context and main activity to access variables
    View v;
    double max;
    long duration;
    public String PATH = "/home/alu_dt1/films/";
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
    int percent;long fileSize;
    double sum;
    ArrayList<Double> bytes_read;//save the amount of bytes read
    // at all times and saving the peak value in the databas
    double dataSize = 10027008; // to be changed if upload file size changes
    double speeds = 0.0;
    ArrayList<Double> time_taken;
    int counter = 0;
    int ucounter = 0;
    int pcounter = 0;
    int scounter = 0;
    File downloadedFile;
    File file;
    File f;
    /*********  work only for Dedicated IP ***********/
    static final String FTP_HOST= "10.150.2.244";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "alu_dt1";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="alu_dt123";

    Context mContext;

    public ftpUpload(Context context){
        this.mContext = context;
    }
    ViewProgress vProgress = ((ViewProgress) mContext);

     AsyncTask<File, Void, Boolean> uploadTask = new AsyncTask<File, Void, Boolean>() {

        // bytes = (TextView)findViewById(R.id.bytee);
        @Override
        protected void onPreExecute() {

            Log.d("PreExceute", "On pre Exceute......");

            super.onPreExecute();

            vProgress.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vProgress.ftpul.setVisibility(View.VISIBLE);
                    vProgress.ftpul.setBackgroundColor(vProgress.getResources().getColor(R.color.yellow));

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
                        vProgress.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                vProgress.ftpul.setBackgroundColor(vProgress.getResources().getColor(R.color.green));

                            }
                        });
                        startTime = System.currentTimeMillis();
                        //Upload the file
                        ftpClient.upload(file, writtenBytes, new MyUploadTransferListener(vProgress.upb));
                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "secs");


                    } else {
                        vProgress.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                vProgress.ftpul.setBackgroundColor(vProgress.getResources().getColor(R.color.green));

                            }
                        });
                        System.err.println("FTP Server does NOT supports resume. Trying to upload file");

                        startTime = System.currentTimeMillis();
                        ftpClient.upload(file, new MyUploadTransferListener(vProgress.upb));
                        endTime = System.currentTimeMillis();
                        System.err.println(endTime - startTime + "secs");

                    }
                } catch (Exception e) {
                    // User Aborted operation
                    vProgress.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vProgress.ftpul.setBackgroundColor(vProgress.getResources().getColor(R.color.red));
                            Toast.makeText(mContext, "FAILED", Toast.LENGTH_LONG).show();
                            if (ftpClient.isConnected()) {
                                //mHandler = new Handler();
                                //mHandler.post(progressInput.updateTask);

                                Toast.makeText(mContext, " connected to ftp server.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(mContext, " unable to connect to ftp server.", Toast.LENGTH_LONG).show();

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
                max = (findMax(bytes_read) / 1024) * 8;
            }
            double averageTime = sum / time_taken.size();
            System.out.print("LIST" + time_taken.size() + "\nasum" + sum);
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.print("\nMAXIMUMMMMMM" + max + "\nAVERAGE" + averageTime);
            double peakRate = (max / averageTime);
            System.out.print("\nPEAK RATE" + df.format(peakRate) + "Kbps");
            //min = findMin(byteList);
            dataSize = (10027008 / 1024) * 8;
            long s = takenTime / 1000;
            if (s != 0) {
                speeds = (dataSize / s);

            }
            vProgress.utime.setText("" + s + "sec");
            vProgress.uavgRate.setText("" + df.format(speeds) + "Kbps");
            vProgress.upeakRate.setText("" + df.format(peakRate) + "Kbps");

            vProgress.lay1.setVisibility(View.GONE);
            vProgress.lay2.setVisibility(View.GONE);
            vProgress.lay3.setVisibility(View.GONE);
            vProgress.lay4.setVisibility(View.GONE);
            vProgress.lay7.setVisibility(View.GONE);
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

            vProgress.pingTest(FTP_HOST);
        }
    };


    /*******  Used to file upload and show progress  **********/

    public class MyUploadTransferListener implements FTPDataTransferListener {

        ProgressBar jp;
        int transfBytes = 0;

        public MyUploadTransferListener(ProgressBar jp) {
            this.jp = jp;
        }

        public void started() {

            vProgress.runOnUiThread(new Runnable() {
                public void run() {
                            /*unumBytes.setVisibility(View.VISIBLE);
                            uavgRate.setVisibility(View.VISIBLE);
                            upeakRate.setVisibility(View.VISIBLE);
                            upb.setVisibility(View.VISIBLE);
                            utime.setVisibility(View.VISIBLE);
                            utime_text.setVisibility(View.VISIBLE);
                            unumBytes_text.setVisibility(View.VISIBLE);
                            uavgRate_text.setVisibility(View.VISIBLE);
                            upeakRate_text.setVisibility(View.VISIBLE);
                            uprogress_text.setVisibility(View.VISIBLE);*/
                    vProgress.lay1.setVisibility(View.VISIBLE);
                    vProgress.lay2.setVisibility(View.VISIBLE);
                    vProgress.lay3.setVisibility(View.VISIBLE);
                    vProgress.lay4.setVisibility(View.VISIBLE);
                    vProgress.lay7.setVisibility(View.VISIBLE);

                    jp.setProgress(0);
                    Toast.makeText(mContext, " Upload Started ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" Upload Started ...");
                }
            });

        }

        public void transferred(int length) {
            transferStart = System.currentTimeMillis();
            // Yet other length bytes has been transferred since the last time this
            // method was called
            transfBytes += length;

            percent = (int) ((transfBytes* 100) /10027008);
            //System.out.println("BYTES TRANSFERED" + transfBytes + " // PERCENTAGE " + percent + "%");

            new Thread() {
                @Override
                public void run() {

                    vProgress.runOnUiThread(new Runnable() {
                        public void run() {
                            vProgress.unumBytes.setText("" + transfBytes);
                            jp.setProgress(percent);
                            vProgress.uprogress_text.setText(percent + "%");
                            //Toast.makeText(getBaseContext(), " transferred ..." + writtenBytes, Toast.LENGTH_SHORT).show();

                        }
                    });
                    System.out.println("BYTES TRANSFERED" + transfBytes + "PERCENTAGE" + percent);
                }
            }.start();

            transferEnd = System.currentTimeMillis();

            double total = (double) (transferEnd - transferStart) / 1000;
            time_taken.add(total);
            bytes_read.add((double) length);
            sum += total;
            //System.out.println("BYTES LIST" + bytes_read);
            //System.out.println(" transferred ..." + length);
        }

        public void completed() {

            // btn.setVisibility(View.VISIBLE);
            // Transfer completed
            // jp.setProgress(jp.getMax());
            //Toast.makeText(getBaseContext(), " completed ...", Toast.LENGTH_SHORT).show();

            //System.out.println(" completed ..." );
        }

        public void aborted() {

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

}
