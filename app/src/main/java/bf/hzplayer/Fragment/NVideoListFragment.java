package bf.hzplayer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import bf.hzplayer.Adapter.RcAdapter;
import bf.hzplayer.HzConst;
import bf.hzplayer.Json.ImageJson;
import bf.hzplayer.Json.RetrofitInterface;
import bf.hzplayer.R;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NVideoListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mVideoListView;
    private RcAdapter mRcAdapter;
    private ArrayList<Object> videos = new ArrayList<>();
    private GridLayoutManager mLayoutManager;

    public NVideoListFragment() {
    }

    public static NVideoListFragment newInstance(/*String param1, String param2*/) {
        NVideoListFragment fragment = new NVideoListFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nvideo_list, container, false);
        mVideoListView = (RecyclerView) view.findViewById(R.id.video_list_view);
        mLayoutManager=new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL, false);//设置为一个3列的纵向网格布局
        mVideoListView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface repo = retrofit.create(RetrofitInterface.class);
        repo.getData();
        Call<ResponseBody> call = repo.getData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Gson gson = new Gson();
                String result = "";
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageJson imageJson = gson.fromJson(result, ImageJson.class);
                ArrayList<Object> urls = new ArrayList<>();
                for (ImageJson.ResultBean.DataBean data : imageJson.getResult().getData()) {
                    urls.add(data.getThumbnail_pic_s());
                }
                mVideoListView.setAdapter(new RcAdapter(urls, HzConst.VideoType.NET));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/

        //使用Gson
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface repo = retrofit.create(RetrofitInterface.class);
        Call<ImageJson> call = repo.getData();
        call.enqueue(new Callback<ImageJson>() {
            @Override
            public void onResponse(Call<ImageJson> call, Response<ImageJson> response) {
                ArrayList<Object> urls = new ArrayList<>();
                for (ImageJson.ResultBean.DataBean data : response.body().getResult().getData()) {
                    urls.add(data.getThumbnail_pic_s());
                }
                mVideoListView.setAdapter(new RcAdapter(urls, HzConst.VideoType.NET));
            }

            @Override
            public void onFailure(Call<ImageJson> call, Throwable t) {

            }
        });*/

        //使用Gson与RxJava
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitInterface repo = retrofit.create(RetrofitInterface.class);
        repo.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageJson -> updataRcView(imageJson));
    }

    public void updataRcView(ImageJson imageJson) {
        Flowable.fromArray(imageJson.getResult().getData().toArray())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    Subscription sub;
                    int i = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        mVideoListView.setAdapter(mRcAdapter = new RcAdapter(videos, HzConst.VideoType.NET));
                        sub = s;
                        sub.request(1);
                    }

                    @Override
                    public void onNext(Object o) {
                        videos.add(((ImageJson.ResultBean.DataBean)o).getThumbnail_pic_s());
                        mRcAdapter.notifyItemInserted(i);
                        i ++;
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
