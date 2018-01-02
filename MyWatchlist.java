package com.example.iis5.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.iis5.imdb.Adapters.UpcomingMoviesAdapter;
import com.example.iis5.imdb.Adapters.WatchlistAdapter;
import com.example.iis5.imdb.Models.MyWatchlistModel;

import java.util.ArrayList;
import java.util.List;

public class MyWatchlist extends AppCompatActivity {

    private List<MyWatchlistModel> watchlistModelList ;
    private RecyclerView recyclerView;
    WatchlistAdapter watchlistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watchlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyWatchlist.this,MainActivity.class);
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.watchlistrv);
        watchlistModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyWatchlist.this,LinearLayoutManager.VERTICAL,false));
        SqLiteDB sqLiteDB = new SqLiteDB(MyWatchlist.this);
        watchlistModelList= sqLiteDB.getMovWatchlist();
        if (watchlistModelList.size()!=0){
            watchlistAdapter =new WatchlistAdapter(MyWatchlist.this,watchlistModelList);
            recyclerView.setAdapter(watchlistAdapter);
        }
        else{
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
        }



    }

}
