package com.testing.alcatel.alufieldtesting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Performing tests on Integrated Site</h1>
 * The Alufieldtesting program implements an application that
 * will be used to perform enhanced call tests on 3G sites of
 * nokia Cameroon and generates reports at the end of tests
 * <p>
 * <b>MainActivty Class:</b> Home screen to display the different options on a  gridView
 * that can be chosen to go to different parts of the application
 *
 * @author  Ngong Ivan-Clare Wirdze
 * @version 1.0
 * @since   03/11/2015
 */

public class MainActivity extends ActionBarActivity {


    Button btnNewTest;
    ListView tests_made;
    ArrayList<String> allTestsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));

    }

   /**
    * This private class is used for creating the different options
    * Note that clicking on Outdoor takes you to RF measurements
    * and on RF measurement taked you to Cell Information class
    */

    private class MyAdapter extends BaseAdapter
    {
        public List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);

            items.add(new Item("Tests", R.drawable.test));
            items.add(new Item("Settings", R.drawable.lock));
            items.add(new Item("RF Measurement", R.drawable.rf));
            items.add(new Item("Outdoor", R.drawable.poses));

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.texts, v.findViewById(R.id.texts));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.texts);

            Item item = (Item)getItem(i);
            picture.setImageResource(item.drawableId);
            name.setText(item.name);
            // System.out.println("THE NAME IS "+name);

            GridView gridView = (GridView)findViewById(R.id.gridview);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View v,
                                        int i, long arg3) {
                    // Send intent to SingleViewActivity
                    Item item = (Item) getItem(i);
                    ImageView picture = (ImageView) v.getTag(R.id.picture);
                    TextView name = (TextView) v.getTag(R.id.texts);

                    BitmapDrawable drawable = (BitmapDrawable) picture.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    String title = (String) name.getText();

                    if (item.name == "Outdoor") {
                        Intent intent = new Intent(getBaseContext(), RFmeasurements.class);
                        intent.putExtra("details", bitmap);
                        intent.putExtra("title", title);
                        intent.putExtra("canSave", 0);
                        intent.putExtra("tests", "mobility");
                        Toast.makeText(getApplicationContext(), " Getting Site Location...", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    } else if (item.name == "Tests") {

                        Intent intent = new Intent(getBaseContext(),Testing.class);
                        intent.putExtra("details", bitmap);
                        intent.putExtra("title", title);
                        startActivity(intent);


                }else if(item.name == "Settings"){

                        Intent intent = new Intent(getApplicationContext(), Forcing.class);
                    //intent.putExtra("details", bitmap);
                    //intent.putExtra("title", title);
                       startActivity(intent);

                    }else if(item.name == "RF Measurement"){
                    Intent intent = new Intent(getApplicationContext(), CellInformation.class);
                    startActivity(intent);
                }

                }

            });
            return v;
        }

        /**
         * private class for assigning each item in the gridview
         * to a particular image(drawable)
         */
        private class Item
        {
            final String name;
            final int drawableId;
            Item(String name, int drawableId)
            {
                this.drawableId = drawableId;
                this.name = name;
            }
        }
    }

}
