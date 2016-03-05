package com.testing.alcatel.alufieldtesting;

/**
 * Created by ivan-clare on 16/12/2015.
 */
public class Properties {

    int id;
    String twonumber;
    String threenumber;
    String ftpuser;
    String downlink;
    String ftplink;
    String ftppass;
    String fournumber;
    String fixednumber;
    String created_at;

    // constructors
    public Properties() {
    }

    public Properties( String fixednumber,String twonumber,String threenumber, String ftpuser, String ftplink,
                       String fournumber,String ftppass, String downlink ) {

        this.twonumber = twonumber;
        this.threenumber = threenumber;
        this.ftpuser = ftpuser;
        this.downlink = downlink;
        this.ftplink = ftplink;
        this.fournumber = fournumber;
        this.fixednumber = fixednumber;
        this.ftppass = ftppass;
    }

    public Properties(int id,String fixednumber, String twonumber,String threenumber, String ftpuser, String ftplink,
                      String fournumber, String ftppass, String downlink) {
        this.id = id;

        this.twonumber = twonumber;
        this.threenumber = threenumber;
        this.ftpuser = ftpuser;
        this.downlink = downlink;
        this.ftplink = ftplink;
        this.fournumber = fournumber;
        this.fixednumber = fixednumber;
        this.ftppass = ftppass;
    }

    // setters
    public void setId(int id) {this.id = id;}

    public void setTwonumber(String twonumber) {this.twonumber = twonumber;}

    public void setThreenumber(String threenumber) {this.threenumber = threenumber;}

    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser;
    }

    public void setDownlink(String downlink) {
        this.downlink = downlink;
    }

    public void setFtplink(String ftplink) {this.ftplink = ftplink; }

    public void setFournumber(String fournumber) {this.fournumber = fournumber;}

    public void setFixednumber(String fixednumber) { this.fixednumber = fixednumber; }

    public void setFtppass(String ftppass) { this.ftppass = ftppass; }

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }

    // getters
    public long getId() { return this.id; }

    public String getTwonumber() {return this.twonumber; }

    public String getDownlink() {
        return this.downlink;
    }

    public String getFtpuser() { return this.ftpuser;  }

    public String getThreenumber() { return this.threenumber; }

    public String getFournumber() { return this.fournumber; }

    public String getFtplink() { return this.ftplink; }

    public String getFtppass() { return this.ftppass; }

    public String getFixednumber() { return this.fixednumber; }

    public  String getCreated_at() {return this.created_at;}
}
