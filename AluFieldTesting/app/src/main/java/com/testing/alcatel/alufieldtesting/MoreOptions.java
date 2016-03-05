package com.testing.alcatel.alufieldtesting;

public class MoreOptions {

	private String sitename;
	private String local_cell_id;
	private String psc;



    public MoreOptions(){}


    public MoreOptions(String sitename, String local_cell_id, String psc){
        this.sitename = sitename;
        this.local_cell_id = local_cell_id;
        this.psc = psc;


    }
     
    public String getSitename(){return this.sitename;}
     
    public String getCellId(){
        return this.local_cell_id;
    }
     
    public  String getPSC(){
        return this.psc;
    }
     


}
