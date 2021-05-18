package com.example.todolist;

public class Todo {
    public String Tile;
    public  String Task;


    public static final String TABLE_NAME = "Task";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_TASK = "Task";
        public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INT PRIMARY KEY, %s TEXT)", TABLE_NAME, KEY_TITLE, KEY_TASK);
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_USERS = "SELECT * FROM "+TABLE_NAME;
    public Todo(String tile, String task) {
        Tile = tile;
        Task = task;
    }

    public Todo() {

    }




    public String getTile() {
        return Tile;
    }

    public void setTile(String tile) {
        Tile = tile;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }
}
