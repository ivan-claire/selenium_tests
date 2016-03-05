package com.testing.alcatel.alufieldtesting;

public class Options {
	
	private String id;
	private String site_name;
	private String latitude;
	
	private String longitude;
	private String altitude;
	private String azimuth;
    private String bts_id;
 
     
    public Options(){}
 
     
    public Options(String id, String site_name,String latitude,String longitude,String altitude,String azimuth,String bts_id){
        this.id = id;
        this.site_name = site_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.azimuth = azimuth;
        this.bts_id = bts_id;


    }
     
    public String getId(){return this.id;}
     
    public String getSiteName(){
        return this.site_name;
    }
     
    public  String getLatitude(){
        return this.latitude;
    }
     
    public String getLongitude(){
        return this.longitude;
    }
    public String getAltitude(){
        return this.altitude;
    }
     
    public String getAzimuth(){return this.azimuth;}
    public String bts_id(){
        return this.bts_id;
    }


}
