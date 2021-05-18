package com.example.todolist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Update_activity extends AppCompatActivity {
    EditText et_update_title, et_update_task;
    Button btn_update_save;

    DB_helper dbHelper;
    String title;
    List<Todo> todoList;
    Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_todo);

        todo = new Todo();
        dbHelper = DB_helper.getInstance(Update_activity.this);
        todoList = dbHelper.getAllUsers();
        todo = todoList.get(Todo_Adaptor.position);
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        et_update_title = findViewById(R.id.up_title);
        et_update_task = findViewById(R.id.up_task);
        btn_update_save = findViewById(R.id.save2);
        Log.d("LOG", "Success");
        et_update_title.setText(todo.getTile());
        et_update_task.setText(todo.getTask());

        btn_update_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = DB_helper.getInstance(Update_activity.this);
                if(et_update_title.getText().toString().isEmpty()){
                    Toast.makeText(Update_activity.this, "Please enter Title ", Toast.LENGTH_LONG).show();
                }else if(et_update_task.getText().toString().isEmpty()){
                    Toast.makeText(Update_activity.this, "Please enter Task ", Toast.LENGTH_LONG).show();

                }else {
                    if (dbHelper.updateUser(new Todo(et_update_title.getText().toString(), et_update_task.getText().toString()))) {
                        Toast.makeText(Update_activity.this, "Task inserted Successfully ", Toast.LENGTH_LONG).show();
                        Intent backIntent = new Intent(Update_activity.this, MainActivity.class);
                        startActivity(backIntent);
                        Update_activity.this.finish();
                        Log.d("LOG", "Success");
                    }else {
                            /*userData().add(new User(R.drawable.ic_launcher_background,dlg_et_name.getText().toString(),dlg_et_description.getText().toString()
                            ,Integer.parseInt(dlg_et_rollNo.getText().toString())));*/
                        Toast.makeText(Update_activity.this, "Task not inserted ", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }


   /* @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null){
                try {
                    InputStream stream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    iv.setImageBitmap(bitmap);
                    imagePath = uri.toString();
                    Glide
                            .with(UpdateUser_Activity.this)
                            .load(imagePath)
                            .centerCrop()
                            .placeholder(R.drawable.pf7)
                            .into(iv);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }*/
}
