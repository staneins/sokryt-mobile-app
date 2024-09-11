package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
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

import java.util.List;

public class PoemListFragment extends Fragment {
    private RecyclerView recyclerView;
    private PoemAdapter poemAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poem_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // данные из репо
        PoemRepository repository = new PoemRepository(getActivity().getApplication());
        List<Poem> poems = repository.getAllPoems();

        // ставим адаптер
        poemAdapter = new PoemAdapter(poems);
        recyclerView.setAdapter(poemAdapter);

        return view;
    }
}