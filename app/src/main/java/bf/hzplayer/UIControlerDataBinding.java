package bf.hzplayer;

import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;

/**
 * 作者： xuezhenhua
 * 日期： 2017/5/8.
 * <p>
 * 功能描述：
 */

public class UIControlerDataBinding extends BaseObservable {

    private Drawable back;
    private Drawable start_or_stop;
    private Drawable next;

    public UIControlerDataBinding(Drawable back, Drawable start_or_stop, Drawable next) {
        this.back = back;
        this.start_or_stop = start_or_stop;
        this.next = next;
    }

    public Drawable getBack() {
        return back;
    }

    public void setBack(Drawable back) {
        this.back = back;
        notifyChange();
    }

    public Drawable getStart_or_stop() {
        return start_or_stop;
    }

    public void setStart_or_stop(Drawable start_or_stop) {
        this.start_or_stop = start_or_stop;
        notifyChange();
    }

    public Drawable getNext() {
        return next;
    }

    public void setNext(Drawable next) {
        this.next = next;
        notifyChange();
    }
}

