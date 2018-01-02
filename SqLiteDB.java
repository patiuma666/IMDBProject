package com.example.iis5.imdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.iis5.imdb.Models.MyFavrotiesModel;
import com.example.iis5.imdb.Models.MyWatchlistModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 18-11-2017.
 */

public class SqLiteDB extends SQLiteOpenHelper {
  //creating a  class called SqLiteDB which extends the SQLiteOpenHelper
  //SQLite is to storing user data. SQLite is a very light weight database which comes with Android OS
 // After extending the class from SQLiteOpenHelper we need to override two methods onCreate() and onUpgrage()
  Cursor cursor;
    // declaring All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;
    // Database Name
    private static final String DATABASE_NAME = "MovieDatabase";
    //  table names
    private static final String TABLE_MOVIEDETAILS = "Movies";
    private static final String WATCHLIST_TABLE = "watchlist";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    // MovieDetails Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_POSTER_PATH = "poster_path";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_VOTE_COUNT = "vote_count";
    private static final String COLUMN_IS_FAVORITE = "favorite";
    private static final String COLUMN_IS_WATCHLIST = "watchlist";
    private static final String DATATYPE_NUMERIC = " INTEGER";
    private static final String DATATYPE_VARCHAR = " TEXT";
    Context context;
    // Creating Tables
    private static final String CREATE_TABLE_MOVIEDETAILS = CREATE_TABLE + TABLE_MOVIEDETAILS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TITLE + " DATATYPE_VARCHAR," + COLUMN_RELEASE_DATE + " DATATYPE_VARCHAR," +
            COLUMN_POSTER_PATH + " DATATYPE_VARCHAR," + COLUMN_VOTE_AVERAGE + " DATATYPE_VARCHAR, " + COLUMN_VOTE_COUNT + " DATATYPE_VARCHAR, " + COLUMN_IS_FAVORITE + " DATATYPE_NUMERIC, " +
            COLUMN_IS_WATCHLIST + " DATATYPE_VARCHAR," + " UNIQUE(" + COLUMN_ID + ") ON CONFLICT REPLACE);";
    private static final String CREATE_WATCHLIST_TABLE = CREATE_TABLE + WATCHLIST_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TITLE + " TEXT," + COLUMN_POSTER_PATH + " VARCHAR);";

    public SqLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
        this.context = context;
    }
   // onCreate() – These is where we need to write create table statements. This is called when database is created.
    //that will creates the table in onCreate method
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIEDETAILS);
        sqLiteDatabase.execSQL(CREATE_WATCHLIST_TABLE);

    }
   //onUpgrade() – This method is called when database is upgraded like modifying the table structure, adding constraints to database etc.,
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIEDETAILS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WATCHLIST_TABLE);
        // Create tables again
        onCreate(sqLiteDatabase);

    }
    //creating a method called addMovtoFav which takes movie details as params
    public  void addMovtoFav(String name,String release_date,String poster_path,double vote_avg,int vote_count,int favorite){
        // getWritableDataBase() Create or open a database that will be used for reading and writing.
        SQLiteDatabase database =this.getWritableDatabase();
       // ContentValues is a name value pair, used to insert or update values into database tables.
        // ContentValues object will be passed to SQLiteDataBase objects insert() and update() functions
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,name);
        values.put(COLUMN_RELEASE_DATE,release_date);
        values.put(COLUMN_POSTER_PATH,poster_path);
        values.put(COLUMN_VOTE_AVERAGE,vote_avg);
        values.put(COLUMN_VOTE_COUNT,vote_count);
        values.put(COLUMN_IS_FAVORITE,favorite);
//here inserting the data
        long id = database.insert(TABLE_MOVIEDETAILS,null,values);
        if (id!=-1){
            Toast.makeText(context," Inserted Successfully", Toast.LENGTH_SHORT).show();
        }

    }
    //creating a method called isMovFav
    public  boolean isMovFav(String name) {
        boolean isMovFav = false;
        SqLiteDB sqLiteDB = new SqLiteDB(context);
        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase sqLiteDatabase = sqLiteDB.getReadableDatabase();
        //Cursor ia a pointer which we gets instance of an ReadableDatabase

           cursor = sqLiteDatabase.query(TABLE_MOVIEDETAILS, new String[]{COLUMN_TITLE}, COLUMN_TITLE + "=?", new String[]{name}, null, null, null, "1");
           Log.d("cusor", String.valueOf(cursor.getCount()));
           //if the cursor size is equal to 1 then it returns the isMovFav else it shows empty

            if (cursor.getCount() == 1) {
                isMovFav = true;
                cursor.close();
                sqLiteDB.close();
                Log.d("addedcusor", String.valueOf(cursor.getCount()));
            } else {
                isMovFav = false;
        }
        return isMovFav;
    }
    // getting moviefavorites
    public List<MyFavrotiesModel> getMovFav() {
        List<MyFavrotiesModel> favList = new ArrayList<MyFavrotiesModel>();
        // Selecting All Query
        String selectQuery = "SELECT * FROM " + TABLE_MOVIEDETAILS;
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor on the other hand, is a kind of a pointer which we get after calling the query() method on an instance of a ReadableDatabase of the SQLiteDatabase class.
       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        Cursor cursor = db.rawQuery(selectQuery, null);
       // looping through all rows and adding to list
        if (cursor!=null&&cursor.moveToFirst()) {
            do {
                MyFavrotiesModel favrotiesModel = new MyFavrotiesModel();
                favrotiesModel.setImage(cursor.getString(3));
                favrotiesModel.setReleaseDate(cursor.getString(2));
                favrotiesModel.setTitle(cursor.getString(1));
                // Adding favorites to list
                favList.add(favrotiesModel);
            } while (cursor.moveToNext());
        }
          //return favlist
        return favList;
    }

    //creating a method called addMovtoFav which takes movie details of title and image
    public  void addMovWatchlist(String name,String poster_path){
        // getWritableDataBase() Create or open a database that will be used for reading and writing.
        SQLiteDatabase database =this.getWritableDatabase();
        // ContentValues is a name value pair, used to insert or update values into database tables.
        // ContentValues object will be passed to SQLiteDataBase objects insert() and update() functions
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,name);
        values.put(COLUMN_POSTER_PATH,poster_path);

        long id = database.insert(WATCHLIST_TABLE,null,values);
        //inserts data if id  notequals to null then  it  displays the toast
        if (id!=-1){
            Toast.makeText(context," Inserted Successfully", Toast.LENGTH_SHORT).show();
        }
    }
     //getting Watchlist method
    public List<MyWatchlistModel> getMovWatchlist() {
        //arraylist
        List<MyWatchlistModel> watchlist = new ArrayList<>();
        // Selecting All Query
        String selectQuery = "SELECT * FROM " + WATCHLIST_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor on the other hand, is a kind of a pointer which we get after calling the query() method on an instance of a ReadableDatabase of the SQLiteDatabase class.
       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        Cursor cursor1 = db.rawQuery(selectQuery, null);
        if (cursor1!=null&&cursor1.moveToFirst()) {
            do {
                MyWatchlistModel watchlistModel = new MyWatchlistModel();
                watchlistModel.setImage(cursor1.getString(2));
                watchlistModel.setTitle(cursor1.getString(1));
                watchlist.add(watchlistModel);
            } while (cursor1.moveToNext());
        }
        return watchlist;
    }
    public  boolean isMovWatchlist(Context context,String name){
        boolean isMovWatchlist;
        SqLiteDB sqLiteDB = new SqLiteDB(context);
        SQLiteDatabase sqLiteDatabase =sqLiteDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(WATCHLIST_TABLE,new String[]{COLUMN_TITLE},COLUMN_TITLE +" =?",new String[]{name},null,null,null,"1");
        Log.d("cusor",String.valueOf(cursor.getCount()));
        if (cursor.getCount()==1){
            isMovWatchlist=true;
        }else{
            isMovWatchlist=false;
        }
        return isMovWatchlist;
    }

    public int getColumnCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIEDETAILS;
             SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
