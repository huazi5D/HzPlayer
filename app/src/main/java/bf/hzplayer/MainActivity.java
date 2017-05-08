package bf.hzplayer;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import bf.hzplayer.Message.EventMessage;
import bf.hzplayer.utils.SDCardUtils;

public class MainActivity extends Activity implements SurfaceHolder.Callback{

    public HzPlayer mHzPlayer = null;
    private VideoViewSurfaceView mVideoViewSurfaceView = null;
    private UIControler uiControler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        // ��???����SDCardȨ��
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        mVideoViewSurfaceView = (VideoViewSurfaceView) findViewById(R.id.videoView);
        mVideoViewSurfaceView.getHolder().addCallback(this);
        mHzPlayer = new HzPlayer(this);
        uiControler = (UIControler) findViewById(R.id.uiControler);
        uiControler.setPlayer(mHzPlayer);
    }

    public void start(View v) {
        mHzPlayer.setDataSource(SDCardUtils.getSDCardPath() + "韩国.mp4");
//        mHzPlayer.setDataSource("http://movie.lemon95.com/rhlz-1.mp4");
        //mHzPlayer.setSurface(mVideoViewSurfaceView.getHolder().getSurface());
        mHzPlayer.start();
    }

    public void nextActivity(View v) {
        VideoWrapper videoWrapper = new VideoWrapper();
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(videoWrapper, "width", 0, 1100),
                ObjectAnimator.ofFloat(videoWrapper, "height", 0, 1100)
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        set.setDuration(500).start();
    }

    @Override
    protected void onResume() {
        mHzPlayer.setSurface(mVideoViewSurfaceView.getHolder().getSurface());
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
//        mHzPlayer.runInBackground();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHzPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.msg) {
            case EventMessage.EVENT_VIDEO_SIZE_CHANGEED:
                mVideoViewSurfaceView.setRatio(eventMessage.ratio);
                break;
        }
    }

    class VideoWrapper {
        private ViewGroup.LayoutParams params;
        VideoWrapper() {
            params = mVideoViewSurfaceView.getLayoutParams();
        }

        public int getWidth() {
            return params.width;
        }

        public int getHeight() {
            return params.height;
        }

        public void setWidth(float w) {
            params.width = (int) w;
            mVideoViewSurfaceView.setLayoutParams(params);
        }

        public void setHeight(float h) {
            params.height = (int) h;
            mVideoViewSurfaceView.setLayoutParams(params);
        }
    }
}
