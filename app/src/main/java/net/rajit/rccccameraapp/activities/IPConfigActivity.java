package net.rajit.rccccameraapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.utils.IPHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IPConfigActivity extends AppCompatActivity {

    @BindView(R.id.etIP)
    EditText etIp;

    @BindView(R.id.etPort)
    EditText etPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipconfig);
        ButterKnife.bind(this);

    }

    public void goSaveIp(View v)
    {
        String ip = etIp.getText().toString().trim();
        String port = etPort.getText().toString().trim();
        if(ip.isEmpty() || port.isEmpty())
        {
            Toast.makeText(this, "IP and Port both must be given", Toast.LENGTH_LONG).show();
        }
        else
        {
            IPHelper.saveIpandPort(this, ip, port);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }
}
