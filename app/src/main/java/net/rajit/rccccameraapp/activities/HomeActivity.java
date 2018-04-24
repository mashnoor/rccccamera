package net.rajit.rccccameraapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.models.ClassRoom;
import net.rajit.rccccameraapp.models.Department;
import net.rajit.rccccameraapp.utils.AppUrls;
import net.rajit.rccccameraapp.utils.Geson;
import net.rajit.rccccameraapp.utils.LoginUtils;
import net.rajit.rccccameraapp.utils.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.spnrDepartments)
    Spinner spnrDepartments;

    @BindView(R.id.spnrClass)
    Spinner spnrClass;


    ProgressDialog dialog;
    Department[] departments;
    ClassRoom[] classRooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        TopBar.attach(this, "Live View");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please Wait...");
        getDepartments();


    }


    private void getDepartments() {
        AsyncHttpClient client = new AsyncHttpClient();
        String token = LoginUtils.getLoginToken(this);
        client.addHeader("Authorization", "Bearer " + token);
        client.post(AppUrls.GET_DEPARTMENTS, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                String response = new String(responseBody);
                departments = Geson.g().fromJson(response, Department[].class);
                List<String> departmentNames = new ArrayList<>();
                departmentNames.add("Select department...");
                for (Department d : departments) {
                    departmentNames.add(d.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, departmentNames);

                spnrDepartments.setAdapter(adapter);
                registerSpinnerOnItemSelected();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong");
                dialog.dismiss();

            }
        });


    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private int getDeptId(String s) {
        for (Department d : departments) {
            if (d.getName().equals(s)) {
                return d.getId();
            }
        }
        return 0;
    }

    private int getClassId(String s) {
        for (ClassRoom c : classRooms) {
            if (c.getClassroomName().equals(s)) {
                return c.getId();
            }
        }
        return 0;
    }

    public void goViewCameras(View v) {
        int deptId = getDeptId(spnrDepartments.getSelectedItem().toString());
        int classId = getClassId(spnrClass.getSelectedItem().toString());
        if (deptId == 0 || classId == 0) {
            showToast("Invalid selection");
        } else

        {
            Intent i = new Intent(this, CameraListActivity.class);
            i.putExtra("deptid", deptId);
            i.putExtra("classid", classId);
            i.putExtra("deptname", spnrDepartments.getSelectedItem().toString());
            i.putExtra("classname", spnrClass.getSelectedItem().toString());
            startActivity(i);
        }


    }


    private void getClassrooms(int deptid) {
        String url = AppUrls.getClassroomUrl(deptid);
        AsyncHttpClient client = new AsyncHttpClient();
        String token = LoginUtils.getLoginToken(this);
        client.addHeader("Authorization", "Bearer " + token);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                classRooms = Geson.g().fromJson(response, ClassRoom[].class);
                List<String> classroomNames = new ArrayList<>();
                classroomNames.add("Select Room...");
                for (ClassRoom c : classRooms) {
                    classroomNames.add(c.getClassroomName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, classroomNames);

                spnrClass.setAdapter(adapter);
                dialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong");
                dialog.dismiss();

            }
        });
    }


    private void registerSpinnerOnItemSelected() {
        spnrDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    getClassrooms(getDeptId(spnrDepartments.getSelectedItem().toString().trim()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
