package bf.hzplayer;

import android.content.Context;
import android.content.Intent;
import android.view.Surface;
import android.view.SurfaceHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import bf.hzplayer.Message.EventMessage;
import bf.hzplayer.Message.UIMessage;
import bf.hzplayer.utils.L;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by zhxue on 2016/12/23.
 */

public class HzPlayer {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext = null;
    private IjkMediaPlayer mMediaPlayer = null;
    private String mCurrentDataSource = null;
    public enum STATE{
        IDLE, PLAYING, PAUSED;
    }
    private STATE mCurrentState = STATE.IDLE;

    public HzPlayer(Context context) {
        mContext = context;

        mMediaPlayer = createMediaPlayer();
//        EventBus.getDefault().register(this);
    }

    public void reset() {
        mMediaPlayer.reset();
        setOptions(mMediaPlayer, 1, 1, 1, 0, IjkMediaPlayer.SDL_FCC_RV32, 1, 0, 0, 48);
        setListeners(mMediaPlayer);
    }

    public void start() {
        mCurrentState = STATE.PLAYING;
        try {
            mMediaPlayer.setDataSource(mCurrentDataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    public void pause() {
        mMediaPlayer.pause();

        mCurrentState = STATE.PAUSED;
    }

    public void resume() {
        mMediaPlayer.start();

        mCurrentState = STATE.PLAYING;
    }

    public void stop() {
        mMediaPlayer.stop();
        mCurrentState = STATE.IDLE;
    }

    public STATE getState() {
        return mCurrentState;
    }

    public void setDataSource(String path) {
        mCurrentDataSource = path;
    }

    public void setSurface(Surface surface) {
        mMediaPlayer.setSurface(surface);
    }

    public void setDisplay(SurfaceHolder sh) {
        mMediaPlayer.setDisplay(sh);
    }

    public void runInBackground() {
        Intent intent = new Intent(mContext, MyService.class);
        mContext.startService(intent);

        MyService.setMediaPlayer(mMediaPlayer);
    }

    private IjkMediaPlayer createMediaPlayer() {
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
//        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        setOptions(ijkMediaPlayer, 1, 1, 1, 0, IjkMediaPlayer.SDL_FCC_RV32, 1, 0, 0, 48);
        setListeners(ijkMediaPlayer);

        return ijkMediaPlayer;
    }

    private void setOptions(IjkMediaPlayer ijkMediaPlayer,int codec, int autoRotate, int codecChange, int opengles, int overlay,
                            int framedrop, int startOnPrepared, int httpDetectRangeSupport, int skipLoopFilter) {
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", codec);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", autoRotate);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", codecChange);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", opengles);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", overlay);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", framedrop);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", startOnPrepared);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", httpDetectRangeSupport);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", skipLoopFilter);
    }

    private void setListeners(IjkMediaPlayer ijkMediaPlayer) {
        ijkMediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
        ijkMediaPlayer.setOnCompletionListener(onCompletionListener);
        ijkMediaPlayer.setOnErrorListener(onErrorListener);
        ijkMediaPlayer.setOnInfoListener(onInfoListener);
        ijkMediaPlayer.setOnPreparedListener(onPreparedListener);
        ijkMediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
        ijkMediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = (mp, percent) -> {

    };

    IMediaPlayer.OnCompletionListener onCompletionListener = mp -> {

    };

    IMediaPlayer.OnErrorListener onErrorListener = (mp, what, extra) -> {
        return false;
    };

    IMediaPlayer.OnInfoListener onInfoListener = (mp, what, extra) -> {
        switch (what) {
            case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                L.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                break;
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                L.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                EventBus.getDefault().post(new UIMessage(IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START));
                break;
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                L.d(TAG, "MEDIA_INFO_BUFFERING_START:");
                break;
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                L.d(TAG, "MEDIA_INFO_BUFFERING_END:");
                break;
            case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                L.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + extra);
                break;
            case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                L.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                break;
            case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                L.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                break;
            case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                L.d(TAG, "MEDIA_INFO_METADATA_UPDATE:");
                break;
            case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                L.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                break;
            case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                L.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                break;
            case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                L.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + extra);
                break;
            case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                L.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                break;
        }
        return true;
    };

    IMediaPlayer.OnPreparedListener onPreparedListener = mp -> {
        mp.start();
    };

    IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener = mp -> {

    };

    IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = (mp, width, height, sar_num, sar_den) -> {
        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_VIDEO_SIZE_CHANGEED, (float) width / height));
    };

}
