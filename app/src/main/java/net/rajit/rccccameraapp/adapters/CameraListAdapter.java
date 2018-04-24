package net.rajit.rccccameraapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.activities.StreamActivity;
import net.rajit.rccccameraapp.models.CamFile;

/**
 * Created by Nowfel Mashnoor on 3/31/2018.
 */

public class CameraListAdapter extends BaseAdapter {

    CamFile[] camFiles;
    Activity activity;

    public CameraListAdapter(Activity activity, CamFile[] camFiles) {
        this.camFiles = camFiles;
        this.activity = activity;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    @Override
    public int getCount() {
        return camFiles.length;
    }

    @Override
    public CamFile getItem(int i) {
        return camFiles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.row_camera_btn, null);
        }
        final CamFile currFile = getItem(i);
        Button b = view.findViewById(R.id.btnCamera);
        b.setText(currFile.getCameraName());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int camid = currFile.getCameraId();
                Intent i = new Intent(activity, StreamActivity.class);
                i.putExtra("camid", camid);
                i.putExtra("camname", currFile.getCameraName());
                activity.startActivity(i);
            }
        });
        return view;
    }


}
