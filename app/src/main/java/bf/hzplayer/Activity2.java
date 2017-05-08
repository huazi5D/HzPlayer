package bf.hzplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Activity2 extends Activity implements SurfaceHolder.Callback{

    private SurfaceView mSurfaceView;
    private HzPlayer mHzPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        EventBus.getDefault().register(this);

        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);
        mSurfaceView.getHolder().addCallback(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void nextActivity(HzPlayer hzPlayer) {
        this.mHzPlayer = hzPlayer;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mHzPlayer.setDisplay(mSurfaceView.getHolder());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
