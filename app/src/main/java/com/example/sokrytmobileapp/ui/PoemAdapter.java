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

public class PoemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Poem> poems;
    private boolean isLoadingData;

    private final int VIEW_TYPE_EMPTY = 0;
    private final int VIEW_TYPE_POEM = 1;
    private final String NO_POEMS_IN_DB = "Нажмите на кнопку загрузки стихов\n\nРекомендуется перезагрузить приложение после первой загрузки";

    public PoemAdapter(List<Poem> poems) {
        this.poems = poems;
        this.isLoadingData = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.emtpy_list_titles, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.poem_list_titles, parent, false);
            return new PoemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PoemViewHolder) {
            Poem poem = poems.get(position);
            PoemViewHolder poemViewHolder = (PoemViewHolder) holder;

            poemViewHolder.title.setText(poem.getTitle());
            setOnTitleClickListener(poemViewHolder, poem);
        } else if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder)holder).title.setText(NO_POEMS_IN_DB);
        }
    }

    @Override
    public int getItemCount() {
        return poems.isEmpty() && !isLoadingData ? 1 : poems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return poems.isEmpty() && !isLoadingData ? VIEW_TYPE_EMPTY : VIEW_TYPE_POEM;
    }

    static class PoemViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public PoemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.poemTitle);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.empty_title);
        }
    }

    public void updatePoemList(List<Poem> newPoemList) {
        this.poems.clear();
        this.poems.addAll(newPoemList);
        notifyDataSetChanged();
    }

    public void addPoems(List<Poem> newPoems) {
        if (poems.isEmpty()) {
            poems.clear();
            notifyDataSetChanged();
        }

        int initialSize = this.poems.size();
        this.poems.addAll(newPoems);
        notifyItemRangeInserted(initialSize, newPoems.size());
    }

    private void setOnTitleClickListener(@NonNull PoemViewHolder holder, Poem poem) {
        holder.title.setOnClickListener(view -> openPoemBodyFragment(view, poem));
    }

    private void openPoemBodyFragment(View view, Poem poem) {
        Fragment bodyFragment = BodyFragment.newInstance(poem);

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