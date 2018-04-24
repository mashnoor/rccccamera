package net.rajit.rccccameraapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.rccccameraapp.R;
import net.rajit.rccccameraapp.models.CamFile;
import net.rajit.rccccameraapp.utils.AppUrls;
import net.rajit.rccccameraapp.utils.Geson;
import net.rajit.rccccameraapp.utils.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.PlayerListener;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class StreamActivity extends AppCompatActivity {

    @BindView(R.id.player)
    VideoView player;

    ProgressDialog dialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        String camName = getIntent().getStringExtra("camname");
        TopBar.attach(this, "Stream (" + camName + ")");
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please Wait...");
        Logger.addLogAdapter(new AndroidLogAdapter());
        int camid = getIntent().getIntExtra("camid", 0);
        if (camid == 0) {
            showToast("Some error occured");
            finish();
        } else {
            loadVideo(camid);
        }


    }


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void loadVideo(final int camId) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(AppUrls.getCameraFile(camId), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                CamFile file = Geson.g().fromJson(response, CamFile.class);

                player.setVideoPath(file.getFilename()).getPlayer().start();
                player.getVideoInfo().setTitle(file.getCameraName()).setShowTopBar(false);
                player.setPlayerListener(new PlayerListener() {
                    @Override
                    public void onPrepared(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public void onBufferingUpdate(GiraffePlayer giraffePlayer, int percent) {

                    }

                    @Override
                    public boolean onInfo(GiraffePlayer giraffePlayer, int what, int extra) {
                        return false;
                    }

                    @Override
                    public void onCompletion(GiraffePlayer giraffePlayer) {
                        loadVideo(camId);

                    }

                    @Override
                    public void onSeekComplete(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public boolean onError(GiraffePlayer giraffePlayer, int what, int extra) {
                        return false;
                    }

                    @Override
                    public void onPause(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public void onRelease(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public void onStart(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public void onTargetStateChange(int oldState, int newState) {

                    }

                    @Override
                    public void onCurrentStateChange(int oldState, int newState) {

                    }

                    @Override
                    public void onDisplayModelChange(int oldModel, int newModel) {

                    }

                    @Override
                    public void onPreparing(GiraffePlayer giraffePlayer) {

                    }

                    @Override
                    public void onTimedText(GiraffePlayer giraffePlayer, IjkTimedText text) {

                    }

                    @Override
                    public void onLazyLoadProgress(GiraffePlayer giraffePlayer, int progress) {

                    }

                    @Override
                    public void onLazyLoadError(GiraffePlayer giraffePlayer, String message) {

                    }
                });
                Logger.d("Refresehd");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Logger.d("Refresh Error. Try Again");
                loadVideo(camId);


            }
        });
    }


}
