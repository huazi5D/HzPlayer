package bf.hzplayer.Message;

/**
 * 作者： xuezhenhua
 * 日期： 2017/2/17.
 * <p>
 * 功能描述：
 */

public class EventMessage {

    public static final int EVENT_VIDEO_SIZE_CHANGEED = 10001;
    public static final int EVENT_VIDEO_PREPARED       = 10002;
    public static final int EVENT_SEEK_COMPLETE        = 10003;
    public static final int EVENT_BUFFERING_UPDATE     = 10004;
    public static final int EVENT_COMPLETION            = 10005;

    public int msg = -1;
    public float ratio = -1;

    public EventMessage(int msg) {
        this.msg = msg;
    }

    public EventMessage(int msg, float ratio) {
        this.msg = msg;
        this.ratio = ratio;
    }
}
