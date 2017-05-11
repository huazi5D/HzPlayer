package bf.hzplayer.Fragment;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bf.hzplayer.Adapter.RcAdapter;
import bf.hzplayer.HzConst;
import bf.hzplayer.R;
import bf.hzplayer.utils.FileUtils;
import bf.hzplayer.utils.SDCardUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LVideoListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mVideoListView;
    private RcAdapter mRcAdapter;
    private GridLayoutManager mLayoutManager;

    public LVideoListFragment() {
    }

    public static LVideoListFragment newInstance(/*String param1, String param2*/) {
        LVideoListFragment fragment = new LVideoListFragment();
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
        List<Object> videos = new ArrayList<>();
        String path = SDCardUtils.getSDCardPath();
        File file = new File(path);
        File[] files = file.listFiles();
        Flowable.fromArray(files)
                .observeOn(Schedulers.io())
                .filter(file1 -> file1.exists() && file1.canRead() && FileUtils.isVideo(file1))
                .flatMap(file12 -> Flowable.fromArray(getVideoThumbnail(file12.getAbsolutePath())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    Subscription sub;
                    int i = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        mVideoListView.setAdapter(mRcAdapter = new RcAdapter(videos, HzConst.VideoType.LOC));
                        sub=s;
                        sub.request(1);
                    }

                    @Override
                    public void onNext(Object o) {
                        videos.add(o);
                        mRcAdapter.notifyItemInserted(i);
                        i ++;
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) { }

                    @Override
                    public void onComplete() {
                        Log.d("onActivityCreated:",videos.toString());
                    }
                });
    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
