package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.repository.PoemRepository;

import java.util.ArrayList;

public class FavouriteListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PoemAdapter poemAdapter;
    private PoemRepository poemRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poem_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        poemRepository = new PoemRepository(requireActivity().getApplication());
        poemAdapter = new PoemAdapter(new ArrayList<>());

        recyclerView.setAdapter(poemAdapter);

        loadFavouritePoems();

        return view;
    }

    private void loadFavouritePoems() {
        poemRepository.getFavouritePoems().observe(getViewLifecycleOwner(), poems -> {
            if (poems != null) {
                poemAdapter.updatePoemList(poems);
            }
        });
    }
}
