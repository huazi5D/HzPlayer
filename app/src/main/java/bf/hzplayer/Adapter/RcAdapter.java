package bf.hzplayer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bf.hzplayer.HzConst;
import bf.hzplayer.R;

/**
 * 作者： xuezhenhua
 * 日期： 2017/5/10.
 * <p>
 * 功能描述：
 */


public class RcAdapter extends RecyclerView.Adapter<RcAdapter.MyViewHolder> {

    private Context mContext;
    private List<Object> datas;
    private HzConst.VideoType mVideoType;

    public RcAdapter(List<Object> datas, HzConst.VideoType type) {
        this.datas = datas;
        mVideoType = type;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.rc_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RcAdapter.MyViewHolder holder, int position) {
        if (mVideoType == HzConst.VideoType.NET)
            Picasso.with(mContext).load((String) datas.get(position)).into(holder.image);//加载网络图片
        else if (mVideoType == HzConst.VideoType.LOC)
            holder.image.setImageBitmap((Bitmap) datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_item);
        }

    }
}
