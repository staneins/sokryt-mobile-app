package com.example.sokrytmobileapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sokrytmobileapp.R;

public class BodyFragment extends Fragment {

    private static final String ARG_BODY = "poem_body";

    public static BodyFragment newInstance(String body) {
        BodyFragment fragment = new BodyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poem_body_fragment, container, false);
        TextView poemBodyTextView = view.findViewById(R.id.poemBodyTextView);

        if (getArguments() != null) {
            String body = getArguments().getString(ARG_BODY);
            poemBodyTextView.setText(body);
        }

        return view;
    }
}
