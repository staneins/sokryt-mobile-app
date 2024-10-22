package com.example.sokrytmobileapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;

import java.util.List;

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.PoemViewHolder> {

    private List<Poem> poems;

    public PoemAdapter(List<Poem> poems) {
        this.poems = poems;
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poem_list_titles, parent, false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        Poem poem = poems.get(position);
        holder.title.setText(poem.title);

        setOnTitleClickListener(holder, poem);
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }

    static class PoemViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public PoemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.poemTitle);
        }
    }

    public void addPoems(List<Poem> newPoems) {
        int initialSize = this.poems.size();
        this.poems.addAll(newPoems);
        notifyItemRangeInserted(initialSize, newPoems.size());
    }

    private void setOnTitleClickListener(@NonNull PoemViewHolder holder, Poem poem) {
        holder.title.setOnClickListener(view -> openPoemBodyFragment(view, poem.body));
    }

    private void openPoemBodyFragment(View view, String poemBody) {
        Fragment bodyFragment = BodyFragment.newInstance(poemBody);

        ((FragmentActivity) view.getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, bodyFragment)
                .addToBackStack(null)
                .commit();
    }
    public void addPoemsToTop(List<Poem> newPoems) {
        this.poems.addAll(0, newPoems);
        notifyItemRangeInserted(0, newPoems.size());
    }
}