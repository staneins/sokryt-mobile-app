package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

public class BodyFragment extends Fragment {
    private Poem currentPoem;
    private PoemRepository poemRepository;

    private static final String ARG_POEM = "poem";

    public static BodyFragment newInstance(Poem poem) {
        BodyFragment fragment = new BodyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POEM, poem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poem_body_fragment, container, false);
        TextView poemBodyTextView = view.findViewById(R.id.poemBodyTextView);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);

        if (getArguments() != null) {
            currentPoem = (Poem) getArguments().getSerializable(ARG_POEM);
            poemBodyTextView.setText(currentPoem.getBody());
            favoriteButton.setImageResource(currentPoem.getFavourite() ? R.drawable.ic_favourite : R.drawable.ic_favourite_border);
        }
        poemRepository = new PoemRepository(requireActivity().getApplication());

        favoriteButton.setOnClickListener(v -> {
            currentPoem.setFavourite(!currentPoem.getFavourite());
            poemRepository.insert(currentPoem);
            favoriteButton.setImageResource(currentPoem.getFavourite() ? R.drawable.ic_favourite : R.drawable.ic_favourite_border);
        });

        return view;
    }
}
