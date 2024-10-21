package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

import java.util.ArrayList;
import java.util.List;

public class PoemListFragment extends Fragment {
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == poemAdapter.getItemCount() - 1) {
                    isLoading = true;
                    loadPoems();
                }
            }
        });

        poemRepository.loadPoems();
        loadPoems();

        return view;
    }

    private void loadPoems() {
        if (allDataLoaded) return;
        poemRepository.getPoemsWithPagination(offset, limit).observe(getViewLifecycleOwner(), this::handlePoemData);
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
}


