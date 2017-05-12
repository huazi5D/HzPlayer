package bf.hzplayer.Json;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * 作者： xuezhenhua
 * 日期： 2017/5/9.
 * <p>
 * 功能描述：
 */

public interface RetrofitInterface {
    @GET("toutiao/index?type=top&key=8c4e06d1f7a4830117377ab85835d62b")
//    Call<ResponseBody> getData();//不使用Gson与RxJava
//    Call<ImageJson> getData();//使用Gson
    Flowable<ImageJson> getData();//使用Gson与RxJava
}
