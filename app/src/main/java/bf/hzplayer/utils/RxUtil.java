package bf.hzplayer.utils;

import java.io.File;

import io.reactivex.Flowable;

/**
 * Created by danxingxi
 * Observable创建工具类
 */
public class RxUtil {

    /**
     * rxjava递归查询内存中的视频文件
     * @param f
     * @return
     */
    public static Flowable<File> listFiles(final File f){
        /**filter操作符过滤视频文件,是视频文件就通知观察者**/
        return Flowable.fromArray(f.listFiles()).filter(file -> file.exists() && file.canRead() && FileUtils.isVideo(file));
    }

}
