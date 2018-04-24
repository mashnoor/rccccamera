package net.rajit.rccccameraapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.models.LoginToken;
import net.rajit.rccccameraapp.utils.AppUrls;
import net.rajit.rccccameraapp.utils.Geson;
import net.rajit.rccccameraapp.models.LoginError;
import net.rajit.rccccameraapp.utils.IPHelper;
import net.rajit.rccccameraapp.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.etID)
    EditText etId;

    @BindView(R.id.etPassword)
    EditText etPassword;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing In. Please Wait...");
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (LoginUtils.getLoginToken(this) != null) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void goLogin(View v) {
        String id = etId.getText().toString();
        String password = etPassword.getText().toString();
        if (id.isEmpty() || password.isEmpty()) {
            showToast("Fill inputs properly");
            return;
        }
        Logger.d(AppUrls.GET_TOKEN);

        RequestParams params = new RequestParams();
        params.put("grant_type", "password");
        params.put("username", id);
        params.put("password", password);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppUrls.GET_TOKEN, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                String response = new String(responseBody);
                if (response.contains("error")) {
                    LoginError loginError = Geson.g().fromJson(response, LoginError.class);
                    showToast(loginError.getErrorDescription());
                } else {
                    LoginToken loginToken = Geson.g().fromJson(response, LoginToken.class);
                    LoginUtils.setLoginToken(LoginActivity.this, loginToken.getAccessToken());
                    showToast("Success");
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong. Try again");
                dialog.dismiss();

            }
        });

    }
}
