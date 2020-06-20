package com.example.etickett.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.etickett.Model.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="Etickett.db";
    public static final String TABLE_NAME="sign_up";

    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";;
    public static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_CREATE =  " CREATE TABLE IF NOT EXISTS " + TABLE_NAME +  "("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME +" TEXT NOT NULL, " +
            COLUMN_EMAIL +" TEXT NOT NULL," +
            COLUMN_PHONE + " TEXT," +
            COLUMN_PASSWORD + " TEXT NOT NULL)";

    private static final String[] allColumns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_EMAIL,
            COLUMN_PHONE,
            COLUMN_PASSWORD
    };

    private SQLiteDatabase database;

    public Helper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //insert user Register...
    public User add_new_user(User users)
    {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, users.getName());
        values.put(COLUMN_EMAIL, users.getEmail());
        values.put(COLUMN_PHONE, users.getPhoneNumber());
        values.put(COLUMN_PASSWORD, users.getPassword().trim());

        SQLiteDatabase db = getWritableDatabase();
        long inserted = db.insert(TABLE_NAME,null, values);
        db.close();
        if(inserted != -1)
        {
            users.setId(inserted);
            users.setMessage("success");
            Log.i("Element", "Inserted new row :"+inserted);

        }
        else{
            users.setId(inserted);
            users.setMessage("An Error Occurred");
            Log.i("Element", "no row was inserted :"+inserted);
        }

        return users;
    }

    // Getting single User
    public User getUser(User users)
    {
        //Open database on read mode..
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        try{
            cursor = db.query(TABLE_NAME, allColumns, "email=?", new String[]{users.getEmail()},null,null,null);

            if (cursor != null && cursor.getCount() > 0 ) {
                cursor.moveToFirst();
                Long id = null;
                String Name = "";
                String Email = "";
                String Phone = "";
                String Password = "";
                String message = "";

                Log.i("Element", "database queried 4 : " + cursor.getString(0));
                id = Long.parseLong(cursor.getInt(0) + "");
                Name = cursor.getString(1);
                Email = cursor.getString(2);
                Phone = cursor.getString(3);
                Password = cursor.getString(4);
                message = "Success";

                //close database connection
                db.close();
                return new User (id, Name, Email, Password, message);
            }
        }
        catch (Exception ex){
            Log.e("Element", ex.getMessage());
            users.setMessage(ex.getMessage());
            //
            db.close();
            return users;
        }

        // return User
        return users;
    }
    public List<User> getAllUsers()
    {
        Cursor cursor = database.query(TABLE_NAME,allColumns,null,null,null, null, null);
        List<User> User_list = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                User user = new User ();
                user.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                User_list.add(user);
            }
        }
        // return All Users
        return User_list;
    }
    }

