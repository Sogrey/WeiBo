package org.sogrey.weibo.pro.modular.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.sina.weibo.sdk.openapi.models.Status;

import org.sogrey.views.ninegridview.NineGridAdapter;
import org.sogrey.views.ninegridview.NineGridlayout;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.home.view.adapter.HomeAdapter.ViewHolder;
import org.sogrey.weibo.utils.GlideUtils;
import org.sogrey.weibo.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream on 16/6/4.
 */
public class HomeAdapter extends BaseRecyclerAdapter<ViewHolder> {

    private Context      context;
    private List<Status> list;

    public HomeAdapter(Context context,List<Status> list) {
        this.context=context;
        this.list=list;
    }

    /**
     * 配置ViewHoder
     *
     * @param view
     *
     * @return
     */
    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    //创建布局
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType,boolean isItem) {
        View v=LayoutInflater.from(context).inflate(
                R.layout.item_home,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    //给我们的视图绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder,int position,boolean isItem) {
        //头像
        Status status=list.get(position);
        GlideUtils.setImage(context,holder.iv_header,status.user.profile_image_url);
        //昵称
        holder.tv_name.setText(status.user.screen_name);
        //发布时间
        holder.tv_time.setText(Tools.getTimeStr(status.created_at));

        holder.tv_content.setText(status.text);

        setText(holder.tv_forword,R.string.status_forword_text,status.reposts_count);
        setText(holder.tv_comment,R.string.status_comment_text,status.comments_count);
        setText(holder.tv_dislike,R.string.status_like_text,status.attitudes_count);

        //图片列表
        ArrayList<String> pic_urls=status.pic_urls;
        if (pic_urls!=null&&pic_urls.size()>0) {
            holder.img_grid.setVisibility(View.VISIBLE);
            ImageAdapter imageAdapter=new ImageAdapter(context,status.pic_urls);
            holder.img_grid.setAdapter(imageAdapter);
        } else {
            holder.img_grid.setVisibility(View.GONE);
        }
    }

    private void setText(TextView textView,int textRes,int count) {
        if (count==0) {
            textView.setText(textRes);
        } else {
            textView.setText(String.valueOf(count));
        }
    }

    @Override
    public int getAdapterItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_header;
        public TextView  tv_name;
        public TextView  tv_time;
        public TextView  tv_content;

        public NineGridlayout img_grid;


        public LinearLayout ll_dislike;
        public TextView     tv_dislike;

        public LinearLayout ll_forword;
        public TextView     tv_forword;

        public LinearLayout ll_comment;
        public TextView     tv_comment;

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_header=(ImageView)itemView
                    .findViewById(R.id.iv_header);
            tv_name=(TextView)itemView
                    .findViewById(R.id.tv_name);
            tv_time=(TextView)itemView
                    .findViewById(R.id.tv_time);
            tv_content=(TextView)itemView
                    .findViewById(R.id.tv_content);

            img_grid=(NineGridlayout)itemView.findViewById(R.id.iv_ngrid_layout);

            ll_dislike=(LinearLayout)itemView
                    .findViewById(R.id.ll_like);
            tv_dislike=(TextView)itemView
                    .findViewById(R.id.tv_like);

            ll_forword=(LinearLayout)itemView
                    .findViewById(R.id.ll_forword);
            tv_forword=(TextView)itemView
                    .findViewById(R.id.tv_forword);

            ll_comment=(LinearLayout)itemView
                    .findViewById(R.id.ll_comment);
            tv_comment=(TextView)itemView
                    .findViewById(R.id.tv_comment);
        }
    }


    class ImageAdapter extends NineGridAdapter {

        public ImageAdapter(Context context,List<String> list) {
            super(context,list);
        }

        @Override
        public int getCount() {
            return (list==null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position) {
            return getItem(position)==null ? null : getItem(position);// ((Image)getItem
            // (position)).getUrl();
        }

        @Override
        public String getItem(int position) {
            return (list==null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i,View view) {
            ImageView iv=null;
            if (view!=null&&view instanceof ImageView) {
                iv=(ImageView)view;
            } else {
                iv=new ImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url=getUrl(i).replace("thumbnail","bmiddle");
            GlideUtils.setImage(context,iv,url);
            //            "thumbnail_pic": "http://ww4.sinaimg
            // .cn/thumbnail/3bf0abaajw1f4wve0kluvj20qo0qodj9.jpg",
            //            "bmiddle_pic": "http://ww4.sinaimg
            // .cn/bmiddle/3bf0abaajw1f4wve0kluvj20qo0qodj9.jpg",
            //            "original_pic": "http://ww4.sinaimg.cn/large/3bf0abaajw1f4wve0kluvj20qo0qodj9.jpg",
            //            Picasso.with(context).load(getUrl(i)).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(iv);
            if (!TextUtils.isEmpty(url)) {
                iv.setTag(url);
            }
            return iv;
        }
    }
}
