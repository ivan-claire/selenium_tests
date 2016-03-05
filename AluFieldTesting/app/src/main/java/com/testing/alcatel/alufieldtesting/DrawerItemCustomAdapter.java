package com.testing.alcatel.alufieldtesting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *Class for displaying images and names on the navigation bar in RFmeasurements class
 * @author  Ngong Ivan-Clare Wirdze
 * @version 1.0
 * @since   03/11/2015
 */

public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {

    Context mContext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView icon = (ImageView) listItem.findViewById(R.id.icon);
        TextView  title = (TextView) listItem.findViewById(R.id.title);
        ProgressBar progress = (ProgressBar)listItem.findViewById(R.id.progressBar);
        TextView scramble = (TextView) listItem.findViewById(R.id.scramble);

        if(position == 0){
            progress.setVisibility(listItem.GONE);
        }else{
            scramble.setVisibility(listItem.GONE);
        }

        ObjectDrawerItem folder = data[position];

        icon.setImageResource(folder.icon);
        title.setText(folder.name);
        progress.setProgress(folder.progress);
        //scramble.setText(folder.scramble);

        return listItem;
    }

}
