package com.example.sokrytmobileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchTitleListFragment extends Fragment {
    private static final String ARG_QUERY = "search_query";
    private String query;
    private RecyclerView recyclerView;
    private PoemAdapter poemAdapter;
    private PoemRepository poemRepository;
    private boolean isLoading = false;
    private int limit = 20;
    private int offset = 0;
    private boolean allDataLoaded = false;

    public static SearchTitleListFragment newInstance(String query, PoemAdapter adapter) {
        SearchTitleListFragment fragment = new SearchTitleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        fragment.poemAdapter = adapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(ARG_QUERY);
        }
        poemRepository = new PoemRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_results, container, false);

        poemRepository = new PoemRepository(requireActivity().getApplication());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

                if (!isLoading && layoutManager != null && layoutManager.findFirstVisibleItemPosition() == 0) {
                    isLoading = true;
                    loadPreviousPoems();
                }
            }
        });
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
        poemRepository.getSearchedPoemsWithPagination(offset, limit, query).observe(getViewLifecycleOwner(), this::handlePoemData);
    }

    private void loadPreviousPoems() {
        if (allDataLoaded) return;
        poemRepository.getSearchedPoemsBeforeOffset(offset, query).observe(getViewLifecycleOwner(), this::handlePreviousPoemData);
    }

    private void handlePoemData(List<Poem> poems) {
        if (poems != null && !poems.isEmpty()) {
            poemAdapter.addPoems(poems);
            offset += poems.size();
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