package com.example.sokrytmobileapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sokrytmobileapp.ui.FavouriteListFragment;

public class FavouriteResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavouriteListFragment())
                    .commit();
        }
    }
}
