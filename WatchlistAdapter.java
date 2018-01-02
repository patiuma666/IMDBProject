package com.example.iis5.imdb.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.iis5.imdb.Models.MyWatchlistModel;
import com.example.iis5.imdb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by IIS 5 on 07-12-2017.
 */

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.MyViewHolder> {

    //  Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    Context context;
    static List<MyWatchlistModel> wlmodels;
    MyWatchlistModel watchlistModel;
    //creating a constructor with params called context,list objects
    public WatchlistAdapter(Context context,List<MyWatchlistModel> myDataset) {
        this.wlmodels = myDataset;
        this.context =context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WatchlistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        // set the view's size, margins, paddings and layout parameters
        View view =inflater.inflate(R.layout.watclist,parent,false);
       return  new MyViewHolder(view);
    }
    @Override
    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(WatchlistAdapter.MyViewHolder holder, int position) {
        watchlistModel =wlmodels.get(position);
        Picasso.with(context).load(watchlistModel.getImage()).placeholder(R.drawable.profileicon).into(holder.imageView);
        holder.title.setText(watchlistModel.getTitle());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
      public ImageView imageView;
      public TextView title;
      public MyViewHolder(View itemView) {
          super(itemView);
          //declaring id's for image and title
          imageView= (ImageView)itemView.findViewById(R.id.imageView);
          title =(TextView)itemView.findViewById(R.id.title);
      }
  }


    @Override
    public int getItemCount() {
        return wlmodels.size();
    }

}
