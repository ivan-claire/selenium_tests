package com.testing.alcatel.alufieldtesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ivan-clare on 09/11/2015.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "MyDatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Results";

    // Table Names
    private static final String TABLE_INFO = "info";
    private static final String TABLE_DATAIDLE = "idle";
    private static final String TABLE_PROPERTIES = "properties";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_TESTNAME = "testname";
    private static final String KEY_SITENAME = "sitename";
    private static final String KEY_TYPE = "type";
    private static final String KEY_COMMENTS = "comments";
    private static final String FKEY_TEST = "test_id";

    private static final String KEY_TWONUMBER = "twonumber";
    private static final String KEY_THREENUMBER = "threenumber";
    private static final String KEY_FTPPASS = "ftppass";
    private static final String NETWORK_TYPE = "networkType";
    private static final String KEY_FOURNUMBER = "fournumber";
    private static final String KEY_FTPLINK = "ftplink";
    private static final String KEY_KIND = "kind";
    private static final String KEY_FTPUSER = "ftpuser";
    private static final String KEY_FIXEDNUMBER = "fixednumber";
    // TAGS Table - column names
    private static final String KEY_PSC = "psc";
    private static final String KEY_STRENGTH = "strength";
    private static final String KEY_LAC = "lac";
    private static final String KEY_MNC = "mnc";
    private static final String KEY_MCC = "mcc";
    private static final String KEY_CID = "cid";
    private static final String KEY_AVGUPLOAD = "averageUpload";
    private static final String KEY_PEAKUPLOAD = "peakUpload";
    private static final String KEY_UFILESIZE = "uploadFileSize";
    private static final String KEY_UPLOADSUCCESS = "uploadSuccess";
    private static final String KEY_AVGDOWNLOAD = "averageDownload";
    private static final String KEY_PEAKDOWNLOAD = "peakDownload";
    private static final String KEY_DFILESIZE = "downloadFileSize";
    private static final String KEY_DOWNLOADSUCCESS= "downloadSuccess";
    private static final String KEY_PINGRESULTS = "pingRes";
    private static final String KEY_PINGSUCCESS = "pingSuccess";
    private static final String KEY_SMSSUCCESS = "smsSuccess";
    private static final String KEY_MOCRESULTS = "mtcResults";
    private static final String KEY_MTCRESULTS = "mocResults";
    private static final String KEY_DOWNLOADLINK = "downloadLink";

    private static final String KEY_DEDICDURATION = "callDuration";
    // NOTE_TAGS Table - column names
    private static final String KEY_INFO_ID = "info_id";
    private static final String KEY_IDLE_ID = "idle_id";

    // Table Create Statements
    private static final String CREATE_TABLE_INFO = "CREATE TABLE "
            + TABLE_INFO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TESTNAME + " TEXT,"
            + KEY_SITENAME + " TEXT,"
            + KEY_TYPE + " TEXT,"
            + KEY_COMMENTS + " TEXT,"
            + KEY_KIND + " TEXT,"
            + KEY_TWONUMBER + " TEXT,"
            + KEY_FTPPASS + " INTEGER,"
            + KEY_FOURNUMBER + " TEXT,"
            + NETWORK_TYPE + " TEXT,"
            + KEY_FTPLINK + " TEXT,"
            + KEY_FIXEDNUMBER + " TEXT,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_DATAIDLE = "CREATE TABLE "
            + TABLE_DATAIDLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PSC + " TEXT,"
            + KEY_STRENGTH + " INTEGER,"
            + KEY_LAC + " INTEGER,"
            + KEY_MNC + " TEXT,"
            + KEY_MCC + " TEXT,"
            + KEY_CID + " INTERGER,"
            + FKEY_TEST + " INTERGER,"
            + KEY_AVGDOWNLOAD + " TEXT,"
            + KEY_PEAKDOWNLOAD + " TEXT,"
            + KEY_DFILESIZE + " TEXT,"
            + KEY_DOWNLOADSUCCESS + " TEXT,"
            + KEY_AVGUPLOAD + " TEXT,"
            + KEY_PEAKUPLOAD + " TEXT,"
            + KEY_UFILESIZE + " TEXT,"
            + KEY_UPLOADSUCCESS +" TEXT,"
            + KEY_PINGRESULTS + " TEXT,"
            + KEY_PINGSUCCESS + " TEXT,"
            + KEY_SMSSUCCESS + " TEXT,"
            + KEY_MOCRESULTS + " TEXT,"
            + KEY_MTCRESULTS + " TEXT,"
            + KEY_DEDICDURATION + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    // todo_tag table create statement
    private static final String CREATE_TABLE_PROPERTIES = "CREATE TABLE "
            + TABLE_PROPERTIES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TWONUMBER + " TEXT,"
            + KEY_THREENUMBER + " TEXT,"
            + KEY_FIXEDNUMBER + " TEXT,"
            + KEY_FOURNUMBER + " TEXT,"
            + KEY_FTPPASS + " TEXT,"
            + KEY_FTPLINK + " TEXT,"
            + KEY_FTPUSER + " TEXT,"
            + KEY_DOWNLOADLINK + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_INFO);
        db.execSQL(CREATE_TABLE_DATAIDLE);
        db.execSQL(CREATE_TABLE_PROPERTIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAIDLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a TEST and assigning it to a particular type of test(mobility_idle, conected, stability)
 */
    public long saveTestInfo(Info info/*, long[] test_ids*/) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TESTNAME, info.getTestname());
        values.put(KEY_SITENAME, info.getSitename());
        values.put(KEY_TYPE, info.getTypes());
        values.put(KEY_KIND, info.getKind());
        values.put(KEY_TWONUMBER, info.getNumber());
        values.put(KEY_FTPPASS, info.getIteration());
        values.put(KEY_FOURNUMBER, info.getHost());
        values.put(KEY_FTPLINK, info.getLink());
        values.put(KEY_COMMENTS, info.getComments());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long info_id = db.insert(TABLE_INFO, null, values);

       Log.d("INSERTTTINGG SUCCESFUL", "database");
       /* // assigning tags to todo
        for (long test_id : test_ids) {
            createTodoTag(info_id, test_id);
        }
        */
        return info_id;
    }

 /* get single test info */
    public Info getInfo() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ TABLE_INFO  ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        try{
        if (c != null) {
            c.moveToLast();
            Info td = new Info();
            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            td.setTestname((c.getString(c.getColumnIndex(KEY_TESTNAME))));
            td.setSitename((c.getString(c.getColumnIndex(KEY_SITENAME))));
            td.setTypes((c.getString(c.getColumnIndex(KEY_TYPE))));
            td.setKind((c.getString(c.getColumnIndex(KEY_KIND))));
            td.setComments((c.getString(c.getColumnIndex(KEY_COMMENTS))));
            td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

            return td;
        }

        }catch (Exception e){
            System.out.print("not working");

        }

        return null;
    }

    /*
 * getting all todos
 * */
    public List<Info> getAllTestsInfo() {
        List<Info> list = new ArrayList<Info>();
        String selectQuery = "SELECT  * FROM " + TABLE_INFO ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Info td = new Info();
                td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setTestname((c.getString(c.getColumnIndex(KEY_TESTNAME))));
                td.setSitename((c.getString(c.getColumnIndex(KEY_SITENAME))));
                td.setTypes((c.getString(c.getColumnIndex(KEY_TYPE))));
                td.setComments((c.getString(c.getColumnIndex(KEY_COMMENTS))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                list.add(td);
            } while (c.moveToNext());
        }

        return list;
    }

    /*
 * Updating a info
 */
    public int updateInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TESTNAME, info.getTestname());
        values.put(KEY_SITENAME, info.getSitename());
        values.put(KEY_SITENAME, info.getTypes());
        values.put(KEY_SITENAME, info.getComments());
        values.put(KEY_CREATED_AT, getDateTime());
        // updating row
        return db.update(TABLE_INFO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(info.getId()) });
    }

    /*
 * Deleting a info may not be working
 */
    public void deleteInfo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
    }

    /*
 * Creating tag
 */
    public long saveIdleTest(Idle idle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PSC, idle.getPsc());
        values.put(KEY_STRENGTH, idle.getStrength());
        values.put(KEY_LAC, idle.getLac());
        values.put(KEY_MCC, idle.getMcc());
        values.put(KEY_MNC, idle.getMnc());
        values.put(KEY_CID, idle.getCid());
        values.put(FKEY_TEST, idle.getTestid());
        values.put(KEY_AVGDOWNLOAD, idle.getAvgDownload());
        values.put(KEY_PEAKDOWNLOAD, idle.getPeakDownload());
        values.put(KEY_DOWNLOADSUCCESS, idle.getdSuccess());
        values.put(KEY_DFILESIZE, idle.getDfileSize());
        values.put(KEY_AVGUPLOAD, idle.getAvgUpload());
        values.put(KEY_PEAKUPLOAD, idle.getPeakUpload());
        values.put(KEY_UFILESIZE, idle.getUfileSize());
        values.put(KEY_UPLOADSUCCESS, idle.getuSuccess());
        values.put(KEY_PINGRESULTS, idle.getPingResults());
        values.put(KEY_PINGSUCCESS, idle.getPingSuccess());
        values.put(KEY_SMSSUCCESS, idle.getSmsResult());
        values.put(KEY_MOCRESULTS, idle.getMocResult());
        values.put(KEY_MTCRESULTS, idle.getMtcResult());
        values.put(KEY_DEDICDURATION, idle.getDedicDuration());
        // insert row
        long idle_id = db.insert(TABLE_DATAIDLE, null, values);
        System.err.print("INFO INSERTED INTO DATABASE. VERIFYYYYYYYYYY");

        return idle_id;
    }

    /* get single idle test info */
    public Idle getTest(int testId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DATAIDLE + " WHERE " + FKEY_TEST + "=" + testId;

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        // try{
        if (c != null) {
            c.moveToFirst();
            Idle td = new Idle();
            td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            td.setPsc(c.getString((c.getColumnIndex(KEY_PSC))));
            td.setStrength(c.getInt((c.getColumnIndex(KEY_STRENGTH))));
            td.setLac(c.getInt((c.getColumnIndex(KEY_LAC))));
            td.setMnc(c.getString((c.getColumnIndex(KEY_MNC))));
            td.setMcc(c.getString((c.getColumnIndex(KEY_MCC))));
            td.setCid(c.getInt((c.getColumnIndex(KEY_CID))));
            td.setTestid(c.getInt((c.getColumnIndex(FKEY_TEST))));

            td.setAvgUpload(c.getString((c.getColumnIndex(KEY_AVGUPLOAD))));
            td.setPeakUpload(c.getString((c.getColumnIndex(KEY_PEAKUPLOAD))));
            td.setUfileSize(c.getString((c.getColumnIndex(KEY_UFILESIZE))));
            td.setuSuccess(c.getString((c.getColumnIndex(KEY_UPLOADSUCCESS))));

            td.setAvgDownload(c.getString((c.getColumnIndex(KEY_AVGDOWNLOAD))));
            td.setPeakDownload(c.getString((c.getColumnIndex(KEY_PEAKDOWNLOAD))));
            td.setDfileSize(c.getString((c.getColumnIndex(KEY_DFILESIZE))));
            td.setdSuccess(c.getString((c.getColumnIndex(KEY_DOWNLOADSUCCESS))));

            td.setPingResults(c.getString((c.getColumnIndex(KEY_PINGRESULTS))));
            td.setPingSuccess(c.getString((c.getColumnIndex(KEY_PINGSUCCESS))));

            td.setsmsSuccess(c.getString((c.getColumnIndex(KEY_SMSSUCCESS))));
            td.setMocResult(c.getString((c.getColumnIndex(KEY_MOCRESULTS))));
            td.setMtcResult(c.getString((c.getColumnIndex(KEY_MTCRESULTS))));
            td.setDedicDuration(c.getString((c.getColumnIndex(KEY_DEDICDURATION))));
            System.out.print("CURSORRRRRR\n" + c);
            System.out.print("TDDDDDDDDDDD\n" + td);
            c.close();
            return td;
        }
        return null;
    }

         /* get single idle test info of idleandmobility test
    public List<Idle> getParticularTestResults(int testId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DATAIDLE + " WHERE " + FKEY_TEST + "=" + testId;

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        List<Idle> idles = new ArrayList<Idle>();
        // try{
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                Idle td = new Idle();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setPsc(c.getString((c.getColumnIndex(KEY_PSC))));
                td.setStrength(c.getInt((c.getColumnIndex(KEY_STRENGTH))));
                td.setLac(c.getInt((c.getColumnIndex(KEY_LAC))));
                td.setMnc(c.getString((c.getColumnIndex(KEY_MNC))));
                td.setMcc(c.getString((c.getColumnIndex(KEY_MCC))));
                td.setCid(c.getInt((c.getColumnIndex(KEY_CID))));
                td.setTestid(c.getInt((c.getColumnIndex(FKEY_TEST))));

                System.out.print("CURSORRRRRR\n" + c);
                System.out.print("TDDDDDDDDDDD\n" + td);
                //c.close();
                    idles.add(td);
                } while (c.moveToNext());
            }
            System.err.print("IDDDDDDDDDDDLEEEEEEEEEEEE CONTENTSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" + idles);
                return idles;

        }

       /* }catch (Exception e){
            System.out.print("not working");

        }

        return null;
    }

    /**
     * getting all tags
     * */
         public List<Idle> getParticularTestResults(int testId) {
        List<Idle> idles = new ArrayList<Idle>();
        String selectQuery = "SELECT * FROM " + TABLE_DATAIDLE + " WHERE " + FKEY_TEST + "=" + testId;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Idle t = new Idle();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setPsc(c.getString((c.getColumnIndex(KEY_PSC))));
                t.setLac(c.getInt((c.getColumnIndex(KEY_LAC))));
                t.setMnc(c.getString((c.getColumnIndex(KEY_MNC))));
                t.setMcc(c.getString((c.getColumnIndex(KEY_MCC))));
                t.setStrength(c.getInt((c.getColumnIndex(KEY_STRENGTH))));
                t.setCid(c.getInt((c.getColumnIndex(KEY_CID))));
                t.setDedicDuration(c.getString((c.getColumnIndex(KEY_DEDICDURATION))));

                // adding to tags list
                idles.add(t);
            } while (c.moveToNext());
        }
             System.out.println("FROM DATABASE"+idles);
        return idles;
    }

    /*
 * Updating a tag: Probably the most important
 */
    public long updateIdleTest(Idle idle) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PSC, idle.getPsc());
        values.put(KEY_STRENGTH, idle.getStrength());
        values.put(KEY_LAC, idle.getLac());
        values.put(KEY_MCC, idle.getMcc());
        values.put(KEY_MNC, idle.getMnc());
        values.put(KEY_CID, idle.getCid());
        values.put(FKEY_TEST, idle.getTestid());
        values.put(KEY_AVGDOWNLOAD, idle.getAvgDownload());
        values.put(KEY_PEAKDOWNLOAD, idle.getPeakDownload());
        values.put(KEY_DOWNLOADSUCCESS, idle.getdSuccess());
        values.put(KEY_DFILESIZE, idle.getDfileSize());
        values.put(KEY_AVGUPLOAD, idle.getAvgUpload());
        values.put(KEY_PEAKUPLOAD, idle.getPeakUpload());
        values.put(KEY_UFILESIZE, idle.getUfileSize());
        values.put(KEY_UPLOADSUCCESS, idle.getuSuccess());
        values.put(KEY_PINGRESULTS, idle.getPingResults());
        values.put(KEY_PINGSUCCESS, idle.getPingSuccess());
        values.put(KEY_SMSSUCCESS, idle.getSmsResult());
        values.put(KEY_MOCRESULTS, idle.getMocResult());
        values.put(KEY_MTCRESULTS, idle.getMtcResult());
        // insert row
        return db.update(TABLE_DATAIDLE, values, KEY_ID + " = (SELECT max(" + KEY_ID + ") FROM " + TABLE_DATAIDLE + ")", null);
    }

    /*
* Updating a tag: Probably the most important
*/
    public long updateSingle(String newValue) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // insert row
        ContentValues cv = new ContentValues();
        cv.put(KEY_DEDICDURATION, newValue);

        return db.update(TABLE_DATAIDLE, values, KEY_DEDICDURATION + "= ?", new String[]{KEY_ID + " =" +
                " (SELECT max(" + KEY_ID + ") FROM " + TABLE_DATAIDLE});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


     //* Creating todo_tag

    public long saveProperties(Properties props) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TWONUMBER, props.getTwonumber());
        values.put(KEY_FIXEDNUMBER, props.getFixednumber());
        values.put(KEY_FTPPASS, props.getFtppass());
        values.put(KEY_FOURNUMBER, props.getFournumber());
        values.put(KEY_FTPLINK, props.getFtplink());
        values.put(KEY_FTPUSER, props.getFtpuser());
        values.put(KEY_THREENUMBER, props.getThreenumber());
        values.put(KEY_DOWNLOADLINK, props.getDownlink());
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_PROPERTIES, null, values);
        return id;
    }

    public int updateProperties(Properties props) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TWONUMBER, props.getTwonumber());
        values.put(KEY_FIXEDNUMBER, props.getFixednumber());
        values.put(KEY_FTPPASS, props.getFtppass());
        values.put(KEY_FOURNUMBER, props.getFournumber());
        values.put(KEY_FTPLINK, props.getFtplink());
        values.put(KEY_FTPUSER, props.getFtpuser());
        values.put(KEY_THREENUMBER, props.getThreenumber());
        values.put(KEY_DOWNLOADLINK, props.getDownlink());
        values.put(KEY_CREATED_AT, getDateTime());

        // updating row
        return db.update(TABLE_DATAIDLE, values, KEY_ID + " = 1",
                new String[]{String.valueOf(props.getId())});
    }

    /* get single test info */
    public Properties getProps() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ TABLE_PROPERTIES  ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        try{
            if (c != null) {
                c.moveToLast();
                Properties props = new Properties();
                props.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                props.setTwonumber((c.getString(c.getColumnIndex(KEY_TWONUMBER))));
                props.setFixednumber((c.getString(c.getColumnIndex(KEY_FIXEDNUMBER))));
                props.setFtppass((c.getString(c.getColumnIndex(KEY_FTPPASS))));
                props.setFournumber((c.getString(c.getColumnIndex(KEY_FOURNUMBER))));
                props.setFtplink((c.getString(c.getColumnIndex(KEY_FTPLINK))));
                props.setFtpuser((c.getString(c.getColumnIndex(KEY_FTPUSER))));
                props.setThreenumber((c.getString(c.getColumnIndex(KEY_THREENUMBER))));
                props.setDownlink((c.getString(c.getColumnIndex(KEY_DOWNLOADLINK))));
                props.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                return props;
            }

        }catch (Exception e){
            System.out.print("not working");

        }

        return null;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }



}

