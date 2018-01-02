package com.example.iis5.imdb.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.iis5.imdb.Models.PosterModel;
import com.example.iis5.imdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 07-12-2017.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.MyViewHoler> {

    Context context;
    List<PosterModel> posterlist =new ArrayList<>();
    PosterModel posterModel;


    public PosterAdapter(Context context, List<PosterModel> posterlist) {
        this.context = context;
        this.posterlist = posterlist;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_layout,parent,false);

        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        posterModel= posterlist.get(position);
        Picasso.with(context).load(posterModel.getImage()).placeholder(R.drawable.profileicon).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return posterlist.size();
    }

    protected  class MyViewHoler extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MyViewHoler(View itemView) {
            super(itemView);
            imageView= (ImageView)itemView.findViewById(R.id.posteriv);
        }
    }

}
