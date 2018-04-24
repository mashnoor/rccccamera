package net.rajit.rccccameraapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.activities.LoginActivity;

/**
 * Created by Nowfel Mashnoor on 3/25/2018.
 */

public class TopBar {
    public static void attach(final Activity activity, String name) {


        TextView activityName = activity.findViewById(R.id.tvActivityName);
        activityName.setText(name);

        ImageView logout = activity.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUtils.setLoginToken(activity, null);
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        });

    }
}
