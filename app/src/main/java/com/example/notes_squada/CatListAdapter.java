package com.example.notes_squada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.viewholder> {

    ArrayList<String> catnames = new ArrayList<String>();
    public CatListAdapter(ArrayList<String> catnames)
    {
        this.catnames = catnames;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent,false);
        return new viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.tv_catitem.setText(catnames.get(position));
    }

    @Override
    public int getItemCount() {
        return catnames.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        //Creating References for TextView

        TextView tv_catitem;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_catitem = itemView.findViewById(R.id.tv_catitem);
        }
    }
}
