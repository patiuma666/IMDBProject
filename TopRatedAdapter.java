package com.example.iis5.imdb.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.iis5.imdb.Models.TopratedModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;
import com.example.iis5.imdb.R;
import com.example.iis5.imdb.SqLiteDB;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 06-12-2017.
 */

public class TopRatedAdapter extends ArrayAdapter<TopratedModel> {
       //creating an Adapter called TopRatedAdapter which extends ArrayAdapter which handles
    // the  array type of data and initialising TopratedModel with in the ArrayAdapter
    //Adapter Provides the data and responsible for creating the views for the individual entry


    Context context;
    int resource;
    List<TopratedModel>topratedmovieModels= new ArrayList<>();
    TopratedModel movieModel;
    //creating a constructor with suitable params which takes context, resource ,toprated model list objects
    public TopRatedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TopratedModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        topratedmovieModels = objects;
    }
    //this method is responsible for creating views
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Instantiates a layout XML file into its corresponding View objects
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
            //inflater.inflate is used to create the view from our xml file
            convertView= inflater.inflate(R.layout.upcoming_movies_layout,parent,false);
        ImageView imageView= (ImageView)convertView.findViewById(R.id.imageView);
        CardView cardview = (CardView)convertView.findViewById(R.id.cardView);
        TextView textView1=(TextView)convertView.findViewById(R.id.releaseddate);
        TextView textView2=(TextView)convertView.findViewById(R.id.voted);
        RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar);
        TextView textView =(TextView)convertView.findViewById(R.id.title);
        final ImageView imageView2=(ImageView)convertView.findViewById(R.id.fav);

        movieModel = topratedmovieModels.get(position);
        Picasso.with(context).load(movieModel.getImage()).into(imageView);
        cardview.setBackgroundResource(R.drawable.background);
        textView.setText(movieModel.getTitle());
        textView1.setText(movieModel.getReleaseDate());
        textView2.setText(String.valueOf(movieModel.getVoteCount()));
        ratingBar.setRating((float)movieModel.getVoteAvg());
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder d = new AlertDialog.Builder(context);
                d.setTitle("Do you want to add to favourites?");
                d.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SqLiteDB db = new SqLiteDB(context);
                        String pp=topratedmovieModels.get(position).getImage();
                        String rd =topratedmovieModels.get(position).getReleaseDate();
                        int vc =topratedmovieModels.get(position).getVoteCount();
                        double va =topratedmovieModels.get(position).getVoteAvg();
                        db.addMovtoFav(topratedmovieModels.get(position).getTitle(),rd,pp,va,vc,1);
                        imageView2.setImageResource(R.drawable.fav_black);
                    }
                });
                d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = d.create();
                dialog.show();
            }
        });
        return convertView;


    }
    // Return the size of the dataset
    @Override
    public int getCount() {
        return topratedmovieModels.size();
    }
}
