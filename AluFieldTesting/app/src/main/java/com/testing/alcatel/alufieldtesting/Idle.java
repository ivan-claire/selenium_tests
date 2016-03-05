package com.testing.alcatel.alufieldtesting;
/**
 * Created by ivan-clare on 07/12/2015.
 * Model for getting all info necessary during mobility test in idle mode
 */
public class Idle {

    int id;
    String psc;
    int strength;
    int lac;
    String mnc;
    String mcc;
    int cid;
    String time;
    int test_id;

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
    String mocResult;
    String mtcResult;
    String duration;

    public Idle() {

    }

    public Idle(String psc, int strength, int lac, String mnc, String mcc, int cid, int test_id,
                    String avgUpload, String peakUpload,String ufileSize, String uSuccess,
                    String avgDownload, String peakDownload, String dfileSize, String dSuccess,
                    String pingResults, String pingSuccess, String smsResult, String mtcResult, String mocResult, String duration) {
        this.psc = psc;
        this.strength = strength;
        this.lac = lac;
        this.mnc = mnc;
        this.mcc = mcc;
        this.cid = cid;
        this.test_id = test_id;
        this.avgUpload = avgUpload;
        this.peakUpload = peakUpload;
        this.ufileSize = ufileSize;
        this.uSuccess = uSuccess;
        this.avgDownload = avgDownload;
        this.peakDownload = peakDownload;
        this.dfileSize = dfileSize;
        this.dSuccess = dSuccess;
        this.pingResults = pingResults;
        this.pingSuccess = pingSuccess;
        this.smsResult = smsResult;
        this.mocResult = mocResult;
        this.mtcResult = mtcResult;
        this.duration = duration;

    }

    public Idle(int id,String psc, int strength, int lac, String mnc, String mcc, int cid, int test_id,
                String avgUpload, String peakUpload,String ufileSize, String uSuccess,
                String avgDownload, String peakDownload, String dfileSize, String dSuccess,
                String pingResults, String pingSuccess, String smsResult, String mtcResult, String mocResult, String duration) {
        this.id = id;
        this.psc = psc;
        this.strength = strength;
        this.lac = lac;
        this.mnc = mnc;
        this.mcc = mcc;
        this.cid = cid;
        this.test_id = test_id;
        this.avgUpload = avgUpload;
        this.peakUpload = peakUpload;
        this.ufileSize = ufileSize;
        this.uSuccess = uSuccess;
        this.avgDownload = avgDownload;
        this.peakDownload = peakDownload;
        this.dfileSize = dfileSize;
        this.dSuccess = dSuccess;
        this.pingResults = pingResults;
        this.pingSuccess = pingSuccess;
        this.smsResult = smsResult;
        this.mocResult = mocResult;
        this.mtcResult = mtcResult;
        this.duration = duration;

    }

    // setter
    public void setId(int id) {this.id = id;}

    public void setPsc(String psc) {this.psc = psc;}

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setTestid(int test_id) {this.test_id = test_id;}

    public void setAvgUpload(String avgUpload) { this.avgUpload = avgUpload;}

    public void setPeakUpload(String peakUpload) {this.peakUpload = peakUpload;}

    public void setUfileSize(String ufileSize) {this.ufileSize = ufileSize;}

    public void setuSuccess(String uSuccess) {this.uSuccess = uSuccess;}

    public void setAvgDownload(String avgDownload) {this.avgDownload = avgDownload;}

    public void setPeakDownload(String peakDownload) {this.peakDownload = peakDownload;}

    public void setDfileSize(String dfileSize) {this.dfileSize = dfileSize;}

    public void setdSuccess(String dSuccess) {this.dSuccess = dSuccess;}

    public void setPingResults(String pingResults) {this.pingResults = pingResults;}

    public void setPingSuccess(String pingSuccess) {this.pingSuccess = pingSuccess;}

    public void setsmsSuccess(String smsResult) {this.smsResult = smsResult;}

    public void setMocResult(String mocResult) {this.mocResult = mocResult;}

    public void setMtcResult(String mtcResult) {this.mtcResult = mtcResult;}

    public void setTime(String time){
        this.time = time;
    }

    public void setDedicDuration(String duration){
        this.duration = duration;
    }
    // getter
    public int getId() {
        return this.id;
    }

    public String getPsc() {
        return this.psc;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getLac() {
        return this.lac;
    }

    public int getCid() {
        return this.cid;
    }

    public int getTestid() {
        return this.test_id;
    }

    public String getMnc() {
        return this.mnc;
    }

    public String getMcc() {
        return this.mcc;
    }

    public String getAvgUpload() {
        return this.avgUpload;
    }

    public String getPeakUpload() {
        return this.peakUpload;
    }

    public String getUfileSize() {
        return this.ufileSize;
    }

    public String getuSuccess() {
        return this.uSuccess;
    }

    public String getAvgDownload() {
        return this.avgDownload;
    }

    public String getPeakDownload() {
        return this.peakDownload;
    }

    public String getDfileSize() {
        return this.dfileSize;
    }

    public String getdSuccess() {
        return this.dSuccess;
    }

    public String getPingResults(){
        return this.pingResults;
    }

    public String getPingSuccess(){
        return this.pingSuccess;
    }

    public String getSmsResult() {
        return this.smsResult;
    }

    public String getMocResult(){ return this.mocResult;}

    public String getMtcResult(){ return this.mtcResult;}

    public String getDedicDuration(){ return this.duration;}

}
