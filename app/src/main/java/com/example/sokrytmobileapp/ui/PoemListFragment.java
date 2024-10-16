package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    private PoemViewModel poemViewModel;
    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poem_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        poemAdapter = new PoemAdapter(new ArrayList<>());
        recyclerView.setAdapter(poemAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == poemAdapter.getItemCount() - 1) {
                    ///загрузить еще стихи
                }
            }
        });

        poemViewModel = new ViewModelProvider(this).get(PoemViewModel.class);
        poemViewModel.getAllPoems().observe(getViewLifecycleOwner(), poems -> {
            if (poems != null && !poems.isEmpty()) {
                poemAdapter.updatePoems(poems);
                Log.d("PoemListFragment", "Загружено " + poems.size() + " стихов");
            } else {
                Toast.makeText(getContext(), "Нет доступных стихов", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}