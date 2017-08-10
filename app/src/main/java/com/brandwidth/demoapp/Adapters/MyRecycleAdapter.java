package com.brandwidth.demoapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.brandwidth.demoapp.Model.BeanClass;
import com.brandwidth.demoapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;


public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>
{

    private Context mContext;
    private List<BeanClass> beanClassList;

    private onRecyclerViewItemClickListener mItemClickListener;

    public MyRecycleAdapter(Context mContext, List<BeanClass> beanClassList) {
        this.mContext = mContext;
        this.beanClassList = beanClassList;
    }

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView title;
        public ImageView thumbnail;
        public RatingBar rating1;

        public MyViewHolder(final View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
           // count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            rating1 = (RatingBar) view.findViewById(R.id.ratingBar1);

            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClickListener(v, getAdapterPosition());
            }
        }
    }


    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycustom_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BeanClass beanClass = beanClassList.get(position);
        holder.title.setText(beanClass.getName());
       // holder.count.setText(beanClass.getId());

        String imgUrl = "http://image.tmdb.org/t/p/w185//" + beanClass.getPath();

        Glide.with(mContext).load(imgUrl)
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.thumbnail);

        Float flRating= Float.parseFloat(beanClass.getRating());

        holder.rating1.setRating((float)flRating/2);


    }

    @Override
    public int getItemCount() {
        return beanClassList.size();
    }

    public void setFilter(List<BeanClass> paramBeanClass){
        beanClassList = new ArrayList<>();
        beanClassList.addAll(paramBeanClass);
        notifyDataSetChanged();
    }
}
