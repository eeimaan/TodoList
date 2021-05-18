package com.example.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Todo_Adaptor extends RecyclerView.Adapter<Todo_Adaptor.TodoViewHolder> {
    List<Todo> list;
    DB_helper dbHelper;
    Context context;
    public static int position;

    String _title;

    String _task;
    private OnItemClickListener onItemClickListener;

    public void setContext(Context context) {
        this.context = context;
    }

    public Todo_Adaptor(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }



    interface OnItemClickListener {
        void OnItemClick(/*UserViewHolder viewHolder,*/ int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClick onItemClick;

    public Todo_Adaptor(List<Todo> list) {
        this.list = list;
    }

    public List<Todo> getList() {
        return list;
    }

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_todo, null);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
       Todo todo = list.get(position);
        holder.title.setText(todo.getTile());
        holder.task.setText(todo.getTask());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Todo item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }
    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView title, task;



        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title2);
            task = itemView.findViewById(R.id.task2);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    onItemClickListener.OnItemClick(position);
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem update = menu.add(Menu.NONE, 1, 1, "Update");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "delete");
            update.setOnMenuItemClickListener(onEditData);
            delete.setOnMenuItemClickListener(onEditData);

            position = getAdapterPosition();
            _title = list.get(position).getTile();
            _task = list.get(position).getTask();
            /*_rollNo = list.get(position).getUserRollNo();*/

            if ( position != RecyclerView.NO_POSITION ) {
                _title = list.get(position).getTile();
            }
        }



    }

    private final MenuItem.OnMenuItemClickListener onEditData = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            dbHelper = DB_helper.getInstance(context);
            switch (item.getItemId()) {
                case 1:
                    Log.d("LOG", "LOG before success");
                    Intent updateIntent = new Intent(context,Update_activity.class);
                    /*Bundle updateBundle = new Bundle();
                    updateBundle.putString("Name", _name);
                    updateBundle.putString("City",_description);
                    updateBundle.putString("RollNo",String.valueOf(_rollNo));*/
                    /*updateIntent.putExtra("Image",_imgResID);
                    updateIntent.putExtra("Name", _name);
                    updateIntent.putExtra("City", _description);*/
                    updateIntent.putExtra("Title", String.valueOf(list.get(position).getTile()));
                    context.startActivity(updateIntent/*, updateBundle*/);
                    Log.d("LOG", "Intent Success");
                    /*Toast.makeText(context, "Data is Updated", Toast.LENGTH_SHORT).show();*/
                    break;
                case 2:
                    DeleteData();
                    //Toast.makeText(context, "Data Deleted", Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        }
    };

    private void DeleteData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete")
                .setMessage("Do you Want to Delete this Item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ( dbHelper.deleteUser(new Todo(_task,_title)) ) {
                            list.remove(position);
                            notifyDataSetChanged();
                            //Toast.makeText(context, "Data is Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyDataSetChanged();
                        //Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

    }
}
