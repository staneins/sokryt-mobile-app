package com.example.sokrytmobileapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;

import java.util.List;

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.PoemViewHolder> {

    private final List<Poem> poems;

    public PoemAdapter(List<Poem> poems) {
        this.poems = poems;
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poem_list_item, parent, false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        Poem poem = poems.get(position);
        holder.title.setText(poem.title);
        holder.text.setText(poem.text);
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }

    static class PoemViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView text;

        public PoemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.poemTitle);
            text = itemView.findViewById(R.id.poemText);
        }
    }
}