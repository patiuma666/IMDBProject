package com.example.iis5.imdb.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.iis5.imdb.Models.CastAndCrewModel;
import com.example.iis5.imdb.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by IIS 5 on 01-12-2017.
 */

public class CastandCrewAdapter extends RecyclerView.Adapter<CastandCrewAdapter.MyViewHolder> {

    Context context;
    private List<CastAndCrewModel> crewModels;
    CastAndCrewModel crewModel;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cast_crew_layout, parent, false);

        return new MyViewHolder(v);
    }
    public CastandCrewAdapter(Context context, List<CastAndCrewModel> myDataset) {
        this.context =context;
        this.crewModels = myDataset;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        crewModel = crewModels.get(position);
        //  here in picasso using A placeholder is commonly used to display a drawable image while the main image gets loaded into the imageview.
        // This is essential in cases when the image takes time to load from the web.
        Picasso.with(context).load(crewModel.getProfile_path()).placeholder(R.drawable.profileicon).into(holder.imageView);
        holder.name.setText(crewModel.getName());
        holder.character.setText(crewModel.getCharacter());

    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView character;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
             name = (TextView)view.findViewById(R.id.name);
            character= (TextView)view.findViewById(R.id.character);
            imageView = (ImageView)view.findViewById(R.id.imageView);

        }
    }
    @Override
    public int getItemCount() {
        return crewModels.size();
    }

}