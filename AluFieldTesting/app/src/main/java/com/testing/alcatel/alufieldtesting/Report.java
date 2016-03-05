package com.testing.alcatel.alufieldtesting;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Colour;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by ivan-clare on 27/01/2016.
 */
public class Report {

    Context context;
    MyDBHelper db;

    public Report(Context context) {

        this.context = context;

    }


        private WritableCellFormat timesBoldUnderline;
        private WritableCellFormat timesBold;
        private WritableCellFormat times;
        private String inputFile;

    String testname;
    String sitename;
    String type;
    String date ;
    int id ;
    // try {
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
    String kind;
    String duration;
    ProgressDialog dialog;
    Boolean showDialog = false;
    int i;
    StringBuffer sb;
    StringBuffer sb1;
    String prevPsc ;


    public void writeStationary(Context ctx) throws IOException, WriteException {

            db= new MyDBHelper(ctx);

            //    List<Info> generalInfo = new ArrayList<Info>();
            Info info = db.getInfo();
            testname = info.getTestname();
            sitename = info.getSitename();
            type = info.getTypes();
             date = info.getCreatedAt();
            id = (int)info.getId();

            Idle idle = db.getTest(id);

            psc = idle.getPsc();

            strength = idle.getStrength();
            lac = idle.getLac();
            mnc = idle.getMnc();
            mcc = idle.getMcc();
            cid = idle.getCid();
            avgUpload = idle.getAvgUpload();
            peakUpload = idle.getPeakUpload();
            ufileSize = idle.getUfileSize();
            uSuccess = idle.getuSuccess();
            avgDownload = idle.getAvgDownload();
            peakDownload = idle.getPeakDownload();
            dfileSize = idle.getDfileSize();
            dSuccess = idle.getdSuccess();
            pingResults = idle.getPingResults();
            pingSuccess = idle.getPingSuccess();
            smsResult = idle.getSmsResult();
            mocResult = idle.getMocResult();
            mtcResult = idle.getMtcResult();
            //File file = new File(inputFile);
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            File file = new File(exportDir, "Stationary Test Report.xls");
            WorkbookSettings wbSettings = new WorkbookSettings();

            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
            sheetAutoFitColumns(workbook.createSheet("Report", 0));
            WritableSheet excelSheet = workbook.getSheet(0);
            createLabel(excelSheet);
            //createContent(excelSheet);
            //test.setOutputFile("c:/temp/lars.xls");
            workbook.write();
            workbook.close();
        }

        private void createLabel(WritableSheet sheet)
                throws WriteException,RowsExceededException {
            // Lets create a times font
            WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
            // Define the cell format
            times = new WritableCellFormat(times10pt);
            // Lets automatically wrap the cells
            times.setWrap(false);

            // create create a bold font with unterlines
            WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                    UnderlineStyle.SINGLE);
            WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);

            timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
            timesBold = new WritableCellFormat(times10ptBold);
            //WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
            // Lets automatically wrap the cells
            timesBoldUnderline.setWrap(false);
            timesBold.setWrap(true);

            CellView cv = new CellView();
            cv.setFormat(times);
            cv.setFormat(timesBoldUnderline);
            cv.setFormat(timesBold);
            cv.setAutosize(true);
            // splitting moc and mtc results
            String[] moc_array = mtcResult.split(",");
            String[] mtc_array = mocResult.split(",");
            sb = new StringBuffer();
            sb1 = new StringBuffer();
            // Write a few headers,COLS , ROWS
            addHeading(sheet, 3, 0, "Stationary Test Report");
            addLabel(sheet, 0, 2, "Test Name");
            addLabel(sheet, 1, 2, "Site Name");
            addLabel(sheet, 2, 2, "PSC");
            addLabel(sheet, 3, 2, "Siganal Strength");
            addLabel(sheet, 4, 2, "LAC");
            addLabel(sheet, 5, 2, "MNC");
            addLabel(sheet, 6, 2, "MCC");
            addLabel(sheet, 7, 2, "CID");
            addLabel(sheet, 8, 2, "DATE");
            addLabel(sheet, 0, 3, testname);
            addLabel(sheet, 1, 3, sitename);
            addLabel(sheet, 2, 3, psc);
            addLabel(sheet, 3, 3, String.valueOf(strength));
            addLabel(sheet, 4, 3, String.valueOf(lac));
            addLabel(sheet, 5, 3, mnc);
            addLabel(sheet, 6, 3, mcc);
            addLabel(sheet, 7, 3, String.valueOf(cid));
            addLabel(sheet, 8, 3, date);
            //NEXT ROW
            addCaption(sheet, 0, 4, "FTP DOWNLOAD");
            addLabel(sheet, 0, 5, "DL File Size/Mbytes");
            addLabel(sheet, 1, 5, "Average FTP DL Rate/Mbps");
            addLabel(sheet, 2, 5, "Peak FTP DL Rate/Mbps");
            addLabel(sheet, 3, 5, "DL Status");
            addLabel(sheet, 0, 6, dfileSize);
            addLabel(sheet, 1, 6, avgDownload);
            addLabel(sheet, 2, 6, peakDownload);
            addLabel(sheet, 3, 6, dSuccess);
            //
            addCaption(sheet, 0, 7, "FTP UPLOAD ");
            addLabel(sheet, 0, 8, "UL File Size");
            addLabel(sheet, 1, 8, "Average FTP UL Rate");
            addLabel(sheet, 2, 8, "Peak FTP UL Rate");
            addLabel(sheet, 3, 8, "UL Status");
            addLabel(sheet, 0, 9, ufileSize);
            addLabel(sheet, 1, 9, avgUpload);
            addLabel(sheet, 2, 9, peakUpload);
            addLabel(sheet, 3, 9, uSuccess);

            addCaption(sheet, 0, 10, "PING TEST AND SMS");
            addLabel(sheet, 0, 11, "Ping Results");
            addLabel(sheet, 1, 11, "Ping Status");
            addLabel(sheet, 2, 11, "SMS Status");
            addLabel(sheet, 0, 12, pingResults);
            addLabel(sheet, 1, 12, pingSuccess);
            addLabel(sheet, 2, 12, smsResult);

            addCaption(sheet, 0, 13, "MOC AND MTC");
            addLabel(sheet, 0, 14, "MOC Results");
            addLabel(sheet, 1, 14, "MTC Results");
            for (i = 0; i < moc_array.length; i++){
                sb.append(moc_array[i]) ;
            }
            addLabel(sheet, 0, 14, sb.toString());
            for (int i = 0; i < mtc_array.length; i++) {
                sb1.append(mtc_array[i]) ;
            }
            addLabel(sheet, 1, 14, sb1.toString());

        }


    public void writeIdleReport(Context ctx) throws IOException, WriteException {

            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            File file = new File(exportDir, "Mobility Test.xls");
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
        sheetAutoFitColumns(workbook.createSheet("Report", 0));
            WritableSheet excelSheet = workbook.getSheet(0);
            createLabelIdle(excelSheet, ctx);
            //createContent(excelSheet);
            //test.setOutputFile("c:/temp/lars.xls");
            workbook.write();
            workbook.close();
        showDialog = false;
        dialog.dismiss();

    }

    public void writeDedicatedReport(Context ctx) throws IOException, WriteException {

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        File file = new File(exportDir, "MobilityTest.xls");
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        //workbook.createSheet("Report", 0);
        sheetAutoFitColumns(workbook.createSheet("Report", 0));
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabelDedic(excelSheet, ctx);
        //createContent(excelSheet);
        //test.setOutputFile("c:/temp/lars.xls");
        workbook.write();
        workbook.close();

    }



    private Boolean createLabelIdle(WritableSheet sheet, Context ctx)
            throws WriteException,RowsExceededException {
        // Lets create a times font
        showDialog = true;
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(false);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);

        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        timesBold = new WritableCellFormat(times10ptBold);
        //WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(false);
        timesBold.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setFormat(timesBold);

        cv.setAutosize(true);
        // splitting moc and mtc results

        // Write a few headers,COLS , ROWS
        addHeading(sheet, 3, 0, "Mobility Idle Test Report");
        addLabel(sheet, 0, 1, "Test Name");
        addLabel(sheet, 1, 1, "Site Name");
        addLabel(sheet, 2, 1, "Kind");
        addLabel(sheet, 3, 1, "PSC");
        addLabel(sheet, 4, 1, "Siganal Strength");
        addLabel(sheet, 5, 1, "LAC");
        addLabel(sheet, 6, 1, "MNC");
        addLabel(sheet, 7, 1, "MCC");
        addLabel(sheet, 8, 1, "CID");
        addLabel(sheet, 9, 1, "DATE");

        List<Idle> testResults = new ArrayList<Idle>();
        //Idle idle = db.getTest(id);
        db = new MyDBHelper(ctx);
        Info info = db.getInfo();
        id = (int) info.getId();
        testResults = db.getParticularTestResults(id);
        int i = 2;
        //Toast.makeText(ctx,"ABOUT TO BEGIN"+testResults.size(),Toast.LENGTH_LONG).show();

        //while (i <= testResults.size()) {
        for(Idle idle: testResults) {

            System.err.println("GENERAL\n" + testname + "," + sitename + "," + psc + "," + String.valueOf(strength) + "," +
                    "," + String.valueOf(lac) + "," + mnc + "," + mcc + "," + String.valueOf(cid) + "" + date);

            //Toast.makeText(ctx,"FIRST EXPORT ",Toast.LENGTH_LONG).show();

                testname = info.getTestname();
            sitename = info.getSitename();
                type = info.getTypes();
                date = info.getCreatedAt();
                kind = info.getKind();
              /*if(psc != null) {
                  prevPsc = psc;
              }*/
                psc = idle.getPsc();
                strength = idle.getStrength();
                lac = idle.getLac();
                mnc = idle.getMnc();
                mcc = idle.getMcc();
                cid = idle.getCid();
            //Toast.makeText(ctx,"LAST EXPORT ",Toast.LENGTH_LONG).show();
                addLabel(sheet, 0, i, testname);
                addLabel(sheet, 1, i, sitename);
                addLabel(sheet, 2, i, kind);
            // if(prevPsc.equals(psc)) {
            addLabel(sheet, 3, i, psc);
            //  Toast.makeText(context,"INSIDE STRINGS OH",Toast.LENGTH_LONG);
            //}else{
            // addLabels(sheet, 3, i, psc, getCellFormatByCondition(true));
            //sheet.addCell(new jxl.write.Label(3,i,psc, createFormatCellStatus(true)));
            // }
                addLabel(sheet, 4, i, String.valueOf(strength));
                addLabel(sheet, 5, i, String.valueOf(lac));
                addLabel(sheet, 6, i, mnc);
                addLabel(sheet, 7, i, mcc);
                addLabel(sheet, 8, i, String.valueOf(cid));
                addLabel(sheet, 9, i, date);
           // Toast.makeText(ctx,"INCREMENTING AND FINISHING ",Toast.LENGTH_LONG).show();
                i++;
            }
        //}
        return true;
    }

    private void createLabelDedic(WritableSheet sheet, Context ctx)
            throws WriteException,RowsExceededException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(false);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);

        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        timesBold = new WritableCellFormat(times10ptBold);
        //timesBold = getCellFormatByCondition(false);
        //WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(false);
        //timesBold.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setFormat(timesBold);
        cv.setAutosize(true);
        // splitting moc and mtc results

        // Write a few headers,COLS , ROWS
        addHeading(sheet, 3, 0, "Mobility Dedicated Test Report");
        addLabel(sheet, 0, 1, "Test Name");
        addLabel(sheet, 1, 1, "Site Name");
        addLabel(sheet, 2, 1, "Kind");
        addLabel(sheet, 3, 1, "PSC");
        addLabel(sheet, 4, 1, "Siganal Strength");
        addLabel(sheet, 5, 1, "LAC");
        addLabel(sheet, 6, 1, "MNC");
        addLabel(sheet, 7, 1, "MCC");
        addLabel(sheet, 8, 1, "CID");
        addLabel(sheet, 9, 1, "Call Duration");
        addLabel(sheet, 10, 1, "DATE");

        List<Idle> testResults = new ArrayList<Idle>();
        //Idle idle = db.getTest(id);
        db = new MyDBHelper(ctx);
        Info info = db.getInfo();
        id = (int) info.getId();
        testResults = db.getParticularTestResults(id);
        int i = 2;
        Toast.makeText(ctx,"ABOUT TO BEGIN"+testResults.size(),Toast.LENGTH_LONG).show();

        //while (i <= testResults.size()) {
        for(Idle idle: testResults) {

            System.err.println("GENERAL\n" + testname + "," + sitename + "," + psc + "," + String.valueOf(strength) + "," +
                    "," + String.valueOf(lac) + "," + mnc + "," + mcc + "," + String.valueOf(cid) + "" + date);

            //Toast.makeText(ctx,"FIRST EXPORT ",Toast.LENGTH_LONG).show();
            testname = info.getTestname();
            sitename = info.getSitename();
            type = info.getTypes();
            date = info.getCreatedAt();
            kind = info.getKind();

            psc = idle.getPsc();
            strength = idle.getStrength();
            lac = idle.getLac();
            mnc = idle.getMnc();
            mcc = idle.getMcc();
            cid = idle.getCid();
            duration = idle.getDedicDuration();
           // Toast.makeText(ctx,"LAST EXPORT ",Toast.LENGTH_LONG).show();
            addLabel(sheet, 0, i, testname);
            addLabel(sheet, 1, i, sitename);
            addLabel(sheet, 2, i, kind);
           // if(prevPsc.equals(psc)) {
            addLabel(sheet, 3, i, psc);
              //  Toast.makeText(context,"INSIDE STRINGS OH",Toast.LENGTH_LONG);
            //}else{
               // addLabels(sheet, 3, i, psc, getCellFormatByCondition(true));
                //sheet.addCell(new jxl.write.Label(3,i,psc, createFormatCellStatus(true)));
           // }
            addLabel(sheet, 4, i, String.valueOf(strength));
            addLabel(sheet, 5, i, String.valueOf(lac));
            addLabel(sheet, 6, i, mnc);
            addLabel(sheet, 7, i, mcc);
            addLabel(sheet, 8, i, String.valueOf(cid));
            addLabel(sheet, 9, i, duration);
            addLabel(sheet, 10, i, date);
           // Toast.makeText(ctx,"INCREMENTING AND FINISHING ",Toast.LENGTH_LONG).show();
            i++;
        }
        //}

    }


        /*private void createContent(WritableSheet sheet) throws WriteException,
                RowsExceededException {

            // Lets calculate the sum of it
            StringBuffer buf = new StringBuffer();
            buf.append("SUM(A2:A10)");
            Formula f = new Formula(0, 10, buf.toString());
//            sheet.addCell(f);
            buf = new StringBuffer();
            buf.append("SUM(B2:B10)");
            f = new Formula(1, 10, buf.toString());
            sheet.addCell(f);

            // now a bit of text
           // for (int i = 12; i < 20; i++) {
                // First column
                addLabel(sheet, 0, i, "Boring text " + i);
                // Second column
                addLabel(sheet, 1, i, "Another text");
            //}
        }*/

        public WritableCellFormat createFormatCellStatus(boolean b) throws WriteException{
            //Colour colour = (b == true) ? Colour.GREEN : Colour.RED;
            if(b = true) {
                WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
                WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);

                fCellstatus.setWrap(true);
                fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
                fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                fCellstatus.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2);
                return fCellstatus;
            }else{
                WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
                WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);

                fCellstatus.setWrap(true);
                fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
                fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                fCellstatus.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2);
                return fCellstatus;

            }

        }

        private void addCaption(WritableSheet sheet, int column, int row, String s)
                throws RowsExceededException, WriteException {
            Label label;
            WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
            label = new Label(column, row, s, timesBold);
            sheet.addCell(label);
            cellFont.setColour(Colour.BLUE);
        }

        private void addHeading(WritableSheet sheet, int column, int row, String s)
                throws RowsExceededException, WriteException {
            WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
            Label label;
            label = new Label(column, row, s, timesBoldUnderline);
            sheet.addCell(label);
            cellFont.setColour(Colour.DARK_GREEN);
        }

       /* private void addNumber(WritableSheet sheet, int column, int row,
                               Integer integer) throws WriteException, RowsExceededException {
            Number number;
            number = new Number(column, row, integer, times);
            sheet.addCell(number);
        }*/
       public WritableCellFormat getCellFormatByCondition(boolean condition) {
           WritableFont wfontStatus;
           if(condition == true){
               wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
           }else{
               wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
           }

           WritableCellFormat result = new WritableCellFormat(wfontStatus);
           try {
               result.setWrap(true);
               result.setAlignment(jxl.format.Alignment.CENTRE);
               result.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
               result.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2);
               return result;
           }catch (Exception e){
               e.printStackTrace();
               return null;
           }
       }

        private void addLabel(WritableSheet sheet, int column, int row, String s)
                throws WriteException, RowsExceededException {
            Label label;
            WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
            label = new Label(column, row, s, times);
            sheet.addCell(label);

        }

    private void addLabels(WritableSheet sheet, int column, int row, String s, WritableCellFormat format)
                throws WriteException, RowsExceededException {
            Label label;
            WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
            label = new Label(column, row, s, format);
            sheet.addCell(label);
        }


    private void sheetAutoFitColumns(WritableSheet sheet) {
        for (int i = 0; i < sheet.getColumns(); i++) {
            Cell[] cells = sheet.getColumn(i);
            int longestStrLen = -1;

            if (cells.length == 0)
                continue;

        /* Find the widest cell in the column. */
            for (int j = 0; j < cells.length; j++) {
                if ( cells[j].getContents().length() > longestStrLen ) {
                    String str = cells[j].getContents();
                    if (str == null || str.isEmpty())
                        continue;
                    longestStrLen = str.trim().length();
                }
            }

        /* If not found, skip the column. */
            if (longestStrLen == -1)
                continue;

        /* If wider than the max width, crop width */
            if (longestStrLen > 255)
                longestStrLen = 255;

            CellView cv = sheet.getColumnView(i);
            cv.setSize(longestStrLen * 256 + 100); /* Every character is 256 units wide, so scale it. */
            sheet.setColumnView(i, cv);
        }
    }


   
}