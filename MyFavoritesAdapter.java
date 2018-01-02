package com.example.iis5.imdb.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.iis5.imdb.Models.MyFavrotiesModel;
import com.example.iis5.imdb.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 24-11-2017.
 */

public class MyFavoritesAdapter extends RecyclerView.Adapter<MyFavoritesAdapter.ViewHolder> {

    Context context;
    private List<MyFavrotiesModel> favrotiesModels=new ArrayList<>();
    MyFavrotiesModel favModel;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView releasedate;
        ImageView iv;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.text_view_title);
            releasedate = (TextView) v.findViewById(R.id.favreleasedate);
            iv= (ImageView)v.findViewById(R.id.image_view);
            cardView = (CardView)v.findViewById(R.id.imagecard);
        }
    }

    public MyFavoritesAdapter(Context context, List<MyFavrotiesModel> myDataset) {
        this.context =context;
        this.favrotiesModels = myDataset;
    }

    @Override
    public MyFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.myfavouriteslist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        favModel = favrotiesModels.get(position);
        Picasso.with(context).load(favrotiesModels.get(position).getImage()).into(holder.iv);
        holder.title.setText(favModel.getTitle());
        holder.releasedate.setText(favModel.getReleaseDate());


    }
    @Override
    public int getItemCount () {
        return favrotiesModels.size();
    }

    }
