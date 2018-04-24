package net.rajit.rccccameraapp.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.adapters.CameraListAdapter;
import net.rajit.rccccameraapp.models.CamFile;
import net.rajit.rccccameraapp.utils.AppUrls;
import net.rajit.rccccameraapp.utils.Geson;
import net.rajit.rccccameraapp.utils.LoginUtils;
import net.rajit.rccccameraapp.utils.TopBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CameraListActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    GridView gridView;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);
        ButterKnife.bind(this);
        String deptName = getIntent().getStringExtra("deptname");
        String className = getIntent().getStringExtra("classname");
        TopBar.attach(this, "Cam List (" + className + ")");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");
        getCameras();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void getCameras() {
        AsyncHttpClient client = new AsyncHttpClient();
        int deptid = getIntent().getIntExtra("deptid", 0); //2
        int classid = getIntent().getIntExtra("classid", 0); //2, 3

        String token = LoginUtils.getLoginToken(this);
        client.addHeader("Authorization", "Bearer " + token);
        client.post(AppUrls.getClassCameras(deptid, classid), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                CamFile[] camFiles = Geson.g().fromJson(response, CamFile[].class);


                CameraListAdapter adapter = new CameraListAdapter(CameraListActivity.this, camFiles);
                gridView.setAdapter(adapter);
                Logger.d(response);
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong");
                Logger.d(new String(responseBody));
                dialog.dismiss();

            }
        });


    }
}
