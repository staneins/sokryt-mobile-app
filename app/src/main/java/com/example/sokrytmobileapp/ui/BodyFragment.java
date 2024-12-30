package com.example.sokrytmobileapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import com.example.sokrytmobileapp.R;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

public class BodyFragment extends Fragment {
    private Poem currentPoem;
    private PoemRepository poemRepository;
    private int fontSize;
    private static final String ARG_POEM = "poem";

    public static BodyFragment newInstance(Poem poem) {
        BodyFragment fragment = new BodyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POEM, poem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPoem = getArguments().getParcelable("poem");
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        fontSize = sharedPreferences.getInt("font_size", 18);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poem_body_fragment, container, false);
        TextView poemBodyTextView = view.findViewById(R.id.poemBodyTextView);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);

        applyFontSizeToAllViews(poemBodyTextView);

        if (getArguments() != null) {
            currentPoem = (Poem) getArguments().getSerializable(ARG_POEM);
            poemBodyTextView.setText(currentPoem.getBody());
            poemBodyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
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

    private void applyGlobalFontSize(TextView textView) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int savedFontSize = preferences.getInt("font_size", 18);
        textView.setTextSize(savedFontSize);
    }

    private void applyFontSizeToAllViews(View root) {
        if (root instanceof TextView) {
            applyGlobalFontSize((TextView) root);
        } else if (root instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                applyFontSizeToAllViews(child);
            }
        }
    }
}
