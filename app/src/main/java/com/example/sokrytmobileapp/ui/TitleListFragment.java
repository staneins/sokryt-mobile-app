package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

import java.util.ArrayList;
import java.util.List;

public class TitleListFragment extends Fragment {
    private RecyclerView recyclerView;
    private PoemAdapter poemAdapter;
    private PoemRepository poemRepository;
    private boolean isLoading = false;
    private int limit = 20;
    private int offset = 0;
    private boolean allDataLoaded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poem_list, container, false);

        poemRepository = new PoemRepository(requireActivity().getApplication());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        poemAdapter = new PoemAdapter(new ArrayList<>());
        recyclerView.setAdapter(poemAdapter);

        AppCompatImageButton loadPoemsButton = view.findViewById(R.id.load_poems_button);
        loadPoemsButton.setOnClickListener(v -> {
            poemRepository.loadPoems();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == poemAdapter.getItemCount() - 1) {
                    isLoading = true;
                    loadPoems();
                }

                if (!isLoading && layoutManager != null && layoutManager.findFirstVisibleItemPosition() == 0) {
                    isLoading = true;
                    loadPreviousPoems();
                }
            }
        });

//        poemRepository.loadPoems();
        loadPoems();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPoems();
    }

    private void loadPoems() {
        if (allDataLoaded) return;
        poemRepository.getPoemsWithPagination(offset, limit).observe(getViewLifecycleOwner(), this::handlePoemData);
    }

    private void loadPreviousPoems() {
        if (allDataLoaded) return;
        poemRepository.getPoemsBeforeOffset(offset).observe(getViewLifecycleOwner(), this::handlePreviousPoemData);
    }

    private void handlePoemData(List<Poem> poems) {
        if (poems != null && !poems.isEmpty()) {
            poemAdapter.addPoems(poems);
            Log.d("PoemListFragment", "Загружено " + poems.size() + " стихов");
            offset += limit;
        } else {
            allDataLoaded = true;
        }
        isLoading = false;
    }

    private void handlePreviousPoemData(List<Poem> poems) {
        if (poems != null && !poems.isEmpty()) {
            poemAdapter.addPoemsToTop(poems);
            Log.d("PoemListFragment", "Загружено " + poems.size() + " предыдущих стихов");
            offset -= poems.size();
        }
        isLoading = false;
    }
}


