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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.iis5.imdb.Models.MyWatchlistModel;
import com.example.iis5.imdb.Models.TopratedModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;
import com.example.iis5.imdb.R;
import com.example.iis5.imdb.SqLiteDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IIS 5 on 11-11-2017.
 */

public class UpcomingMoviesAdapter extends ArrayAdapter<UpcomingMovieModel> {

 //Adapter Provides the data and responsible for creating the views for the individual entry
 // Provide a reference to the views for each data item
 // Complex data items may need more than one view per item, and
 // we provide access to all the views for a data item in a view holder
    Context context;
    int resource;
    ViewHolder  viewHolder;
    //declaring  an arraylist for UpcomingMovieModel
    List<UpcomingMovieModel>upcomingMovieModels= new ArrayList<>();
    UpcomingMovieModel movieModel;
//creating a constructor with params caled context,resourcem,list objects
    public UpcomingMoviesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UpcomingMovieModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.upcomingMovieModels=objects;

    }
    protected  class ViewHolder {
        //A ViewHolder object stores each of the component views inside the tag field of the Layout, so you can
        // immediately access them without the need to look them up repeatedly.
        ImageView imageView,imageView2;
        TextView textView,textView1,textView2;
        CardView cardView;
        RatingBar ratingBar;

    }

    @NonNull
    @Override
    //this method is responsible for creating views
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       //creating viewHolder object
      viewHolder = new ViewHolder();
        //Instantiates a layout XML file into its corresponding View objects
        LayoutInflater  inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null) {
            //inflater.inflate is used to create the view from our xml file
            convertView = inflater.inflate(R.layout.upcoming_movies_layout, null);
            //declaring ids for image and text
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.releaseddate);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.voted);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.title);
            viewHolder.imageView2 = (ImageView) convertView.findViewById(R.id.fav);
           convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        //creating SqLiteDB object which takes the context
        SqLiteDB sqLiteDB =new SqLiteDB(context);

        if (sqLiteDB.isMovFav(upcomingMovieModels.get(position).getTitle())){
            viewHolder.imageView2.setImageResource(R.drawable.fav_black);
            viewHolder.imageView2.setEnabled(false);
        }else {
            viewHolder.imageView2.setImageResource(R.drawable.fav_white);
        }
        //refers the list of moviemodels and get the pos and declaring it in the model class obj
        movieModel = upcomingMovieModels.get(position);
        viewHolder.imageView2.setEnabled(true);
       // This button click invokes the most basic feature of Picasso i.e. Loading A drawable image into an ImageView
            Picasso.with(context).load(movieModel.getImage()).into(viewHolder.imageView);
            viewHolder.cardView.setBackgroundResource(R.drawable.background);
        //sets texts to model obj
            viewHolder.textView.setText(movieModel.getTitle());
            viewHolder.textView1.setText(movieModel.getReleaseDate());
            viewHolder.textView2.setText(String.valueOf(movieModel.getVoteCount()));
            viewHolder.ratingBar.setRating((float) movieModel.getVoteAvg());

        //here on cicking the favoritesImageview it shows an Alertdialog that do u want to add to fav for this we sets to buttons

        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder d = new AlertDialog.Builder(context);
                    d.setTitle("Do you want to add to favourites?");
                    //the positive button if yes onclicking it it add the data to the database of  myfav list
                    d.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewHolder.imageView2.setImageResource(R.drawable.fav_black);
                            SqLiteDB db = new SqLiteDB(context);
                            String pp = upcomingMovieModels.get(position).getImage();
                            String rd = upcomingMovieModels.get(position).getReleaseDate();
                            int vc = upcomingMovieModels.get(position).getVoteCount();
                            double va = upcomingMovieModels.get(position).getVoteAvg();
                            //here using a condition to for the isMovFav if we want add the movie to our favorites or not
                            // if set the imagesource as fav_black that means it the movie image and title are added to myfavorites if not the image source will be disabled
                            if (db.isMovFav(upcomingMovieModels.get(position).getTitle())){
                                viewHolder.imageView2.setImageResource(R.drawable.fav_black);
                            }else {
                                viewHolder.imageView2.setImageResource(R.drawable.fav_white);
                                db.addMovtoFav(upcomingMovieModels.get(position).getTitle(), rd, pp, va, vc, 1);
                            }
                        }
                    });//if on clicking no button it cancel the dialogInterfacde
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
        //it returns  the convertview
        return convertView;


    }
    // Return the size of the dataset
    @Override
    public int getCount() {
                  return upcomingMovieModels.size();
        }



}


