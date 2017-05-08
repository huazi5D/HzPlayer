package bf.hzplayer;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import bf.hzplayer.databinding.UiControlerBinding;

/**
 * 作者： xuezhenhua
 * 日期： 2017/5/8.
 * <p>
 * 功能描述：
 */

public class UIControler extends RelativeLayout {

    private UiControlerBinding uiControler;
    private UIControlerDataBinding uiControlerdatabinding;
    private HzPlayer player;
    private Context mContext;

    public UIControler(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        uiControler = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ui_controler, this, true);
        uiControlerdatabinding = new UIControlerDataBinding(
                context.getDrawable(R.drawable.back_white),
                context.getDrawable(R.drawable.back_white),
                context.getDrawable(R.drawable.back_white)
        );

        uiControler.setUiControler(uiControlerdatabinding);
        uiControler.setUiClick(v -> {
            switch (v.getId()) {
                case R.id.back:
                    break;
                case R.id.start_or_stop:
                    if (player.getState() == HzPlayer.STATE.PLAYING) {
                        uiControlerdatabinding.setStart_or_stop(mContext.getDrawable(R.drawable.stop));
                        player.pause();
                    } else {
                        uiControlerdatabinding.setStart_or_stop(mContext.getDrawable(R.drawable.start));
                        player.resume();
                    }
                    break;
            }
        });
    }

    public void setPlayer(HzPlayer player) {
        this.player = player;
    }
}