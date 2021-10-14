package com.example.notes_squada.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_squada.NotesDisplayActivity;
import com.example.notes_squada.R;

import java.util.Date;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.viewholder> {

    List<String> notes_title;
    List<String> date;
    public NotesListAdapter(List<String> notes_title, List<String> date)
    {
        this.notes_title = notes_title;
        this.date = date;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
        return new NotesListAdapter.viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notes_title.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CardView tv_item;
        TextView tv_itemtext, tv_itemsubtext;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_itemtext = itemView.findViewById(R.id.tv_itemtext);
            tv_itemsubtext = itemView.findViewById(R.id.tv_itemsubtext);
            tv_item = itemView.findViewById(R.id.tv_item);

            tv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), NotesDisplayActivity.class);

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
