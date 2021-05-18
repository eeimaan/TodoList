package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB_helper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DB_WS";
    private static final int DB_VERSION = 2;
    private static final String TAG ="TODO" ;
Context context;
    private static DB_helper instance;

    public static DB_helper getInstance(Context context){
        if (instance == null){
          return   instance = new DB_helper(context);
        }
        return instance;
    }

    public DB_helper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Todo.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL(Todo.DROP_TABLE);
            onCreate(db);
            //db.execSQL(User.CREATE_TABLE);
        }
    }



    public long insertUser(Todo todo){
        long rowId =-1;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Todo.KEY_TITLE, todo.getTile());
        contentValues.put(Todo.KEY_TASK, todo.getTask());


        try {
            rowId = db.insert(Todo.TABLE_NAME, Todo.KEY_TITLE, contentValues);
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
            return rowId;
        }
        return rowId;
    }



    public boolean updateUser(Todo todo){
        boolean effectedRows = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Todo.KEY_TITLE,todo.getTile());
        contentValues.put(Todo.KEY_TASK, todo.getTask());


        try {
            effectedRows=1!= db.update(Todo.TABLE_NAME, contentValues, todo.KEY_TITLE+"=?",new String[]{todo.getTile()+""});
        }catch (Exception e){
            return false;
        }
        return effectedRows;
    }



    public boolean deleteUser(Todo todo){
        boolean effectRows = false;
        SQLiteDatabase db = getWritableDatabase();


        try{
            effectRows= -1 != db.delete(Todo.TABLE_NAME,todo.KEY_TITLE+"=?",new String[]{todo.getTile()+""});
        }catch (Exception e){
            return false;
        }
        return effectRows;
    }



    public List<Todo> getAllUsers (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Todo.SELECT_ALL_USERS, null);
        List<Todo> list = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Todo newTodo = new Todo();
                newTodo.setTile(cursor.getString(cursor.getColumnIndex(Todo.KEY_TITLE)));
                newTodo.setTask(cursor.getString(cursor.getColumnIndex(Todo.KEY_TASK)));

                list.add(newTodo);
            }while (cursor.moveToNext());
        }return list;
    }
}
