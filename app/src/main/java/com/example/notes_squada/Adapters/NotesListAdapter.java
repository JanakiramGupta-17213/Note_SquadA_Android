package com.example.notes_squada.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_squada.NotesDisplayActivity;
import com.example.notes_squada.R;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.viewholder> implements
        Filterable {

    List<String> notes_title;
    List<String> filternotes_title;
    List<String> date;
    List<Integer> id;

    public NotesListAdapter(List<String> notes_title,List<String> date,List<Integer> id)
    {
        this.notes_title = notes_title;
        filternotes_title = notes_title;
        this.date = date;
        this.id = id;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
        return new NotesListAdapter.viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.tv_itemtext.setText(filternotes_title.get(position));
        holder.tv_itemsubtext.setText(date.get(notes_title.indexOf(filternotes_title.get(position))));
    }

    @Override
    public int getItemCount() {
        return filternotes_title.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charseq = charSequence.toString();
                if(charseq.isEmpty())
                {
                    filternotes_title = notes_title;
                }
                else {
                    List<String> filteredtitle = new ArrayList<String>();
                    for (String title : notes_title) {
                        if (title.toLowerCase().contains(charseq.toLowerCase())) {
                            filteredtitle.add(title);
                        }
                        filternotes_title = filteredtitle;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filternotes_title;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filternotes_title = (List<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
                    intent.putExtra("title",notes_title.get(getAdapterPosition()));
                    intent.putExtra("id",id.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
