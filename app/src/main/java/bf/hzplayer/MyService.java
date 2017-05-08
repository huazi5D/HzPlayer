package bf.hzplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MyService extends Service {

    private static IjkMediaPlayer mIjkMediaPlayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void setMediaPlayer(IjkMediaPlayer mp) {
        if (mIjkMediaPlayer != null && mIjkMediaPlayer != mp) {
            if (mIjkMediaPlayer.isPlaying())
                mIjkMediaPlayer.stop();

            mIjkMediaPlayer.release();
            mIjkMediaPlayer = null;
        }
        mIjkMediaPlayer = mp;
    }

    public static IjkMediaPlayer getMediaPlayer() {
        return mIjkMediaPlayer;
    }
}
