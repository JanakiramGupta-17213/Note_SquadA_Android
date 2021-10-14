package com.example.notes_squada.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_squada.NotesListActivity;
import com.example.notes_squada.R;

import java.util.List;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.viewholder> {

    List<String> categories;
    public CatListAdapter(List<String> categories)
    {
        this.categories = categories;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent,false);
        return new viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.tv_catitemtext.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        //Creating References for TextView

        CardView tv_catitem;
        TextView tv_catitemtext,tv_catitemsubtext;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_catitemtext = itemView.findViewById(R.id.tv_catitemtext);
            tv_catitemsubtext = itemView.findViewById(R.id.tv_catitemsubtext);
            tv_catitem = itemView.findViewById(R.id.tv_catitem);

            tv_catitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), NotesListActivity.class);
                    intent.putExtra("Category",categories.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
