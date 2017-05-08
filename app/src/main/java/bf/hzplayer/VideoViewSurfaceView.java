package bf.hzplayer;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhxue on 2016/12/22.
 */

public class VideoViewSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private float mVideoRatio = -1;

    public VideoViewSurfaceView(Context context) {
        this(context, null);
    }

    public VideoViewSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoViewSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VideoViewSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRatio(float ratio) {
        this.mVideoRatio = ratio;
        new Handler().post(() -> requestLayout());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mVideoRatio != -1) {
            float ratio = (float) getMeasuredWidth() / getMeasuredHeight();

            int width = mVideoRatio > ratio ? getMeasuredWidth() : (int) (getMeasuredHeight() * mVideoRatio);
            int height = mVideoRatio > ratio ? (int) (getMeasuredWidth() / mVideoRatio) : getMeasuredHeight();
            setMeasuredDimension(width, height);
        }
    }

}
