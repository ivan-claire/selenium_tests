package com.testing.alcatel.alufieldtesting;


import java.util.Date;
/*
* Model for getting basic info about test to be performed on site
* */
public class Info {

    int id;
    String testname;
    String sitename;
    String type;
    String comments;
    String kind;
    String number;
    int duration;
    int iteration;
    String netType;
    String link;
    String host;
    String dlFile;
    String created_at;

    // constructors
    public Info() {
    }

    public Info(String testname,String sitename,String type,String comments, String dlFile,
                String kind, String number,int duration, int iteration, String netType, String link, String host ) {
        this.testname = testname;
        this.sitename = sitename;
        this.type = type;
        this.comments = comments;
        this.kind = kind;
        this.number = number;
        this.duration = duration;
        this.iteration = iteration;
        this.netType = netType;
        this.link = link;
        this.host = host;
        this.dlFile = dlFile;
    }

    public Info(int id, String testname,String sitename,String type,String comments, String dlFile,
                String kind, String number,int duration, int iteration, String netType, String link, String host) {
        this.id = id;
        this.testname = testname;
        this.sitename = sitename;
        this.type = type;
        this.comments = comments;
        this.kind = kind;
        this.number = number;
        this.duration = duration;
        this.iteration = iteration;
        this.netType = netType;
        this.link = link;
        this.host = host;
        this.dlFile = dlFile;
    }

    // setters
    public void setId(int id) {this.id = id;}

    public void setTestname(String testname) {this.testname = testname;}

    public void setSitename(String sitename) {this.sitename = sitename;}

    public void setTypes(String type) {this.type = type;}

    public void setComments(String comments) {this.comments = comments;}

    public void setKind(String kind) {this.kind = kind;}

    public void setNumber(String number) {this.number = number;}

    public void setDuration(int duration) {this.duration = duration;}

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public void setLink(String link) {this.link = link; }

    public void setHost(String host) {this.host = host;}

    public void setDlFile(String dlFile) { this.dlFile = dlFile; }

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }

    // getters
    public long getId() { return this.id; }

    public String getSitename() { return this.sitename;}

    public String getTestname() {
        return this.testname;
    }

    public String getTypes() {
        return this.type;
    }

    public String getComments() {return this.comments; }

    public String getKind() {
        return this.kind;
    }

    public String getNumber() {return this.number; }

    public String getNetType() {
        return this.netType;
    }

    public int getIteration() {
        return this.iteration;
    }

    public int getDuration() { return this.duration; }

    public String getHost() { return this.host; }

    public String getLink() { return this.link; }

    public String getCreatedAt(){ return this.created_at; }


}
