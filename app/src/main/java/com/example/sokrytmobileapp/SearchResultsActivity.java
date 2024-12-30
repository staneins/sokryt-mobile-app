package com.example.sokrytmobileapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sokrytmobileapp.repository.PoemRepository;
import com.example.sokrytmobileapp.ui.PoemAdapter;
import com.example.sokrytmobileapp.ui.SearchTitleListFragment;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private PoemRepository poemRepository;
    private PoemAdapter poemAdapter;
    private SearchTitleListFragment searchTitleListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        poemAdapter = new PoemAdapter(new ArrayList<>());

        String query = getIntent().getStringExtra(SearchManager.QUERY);
        if (query != null) {
            searchTitleListFragment = SearchTitleListFragment.newInstance(query, poemAdapter);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, searchTitleListFragment)
                    .commit();

            poemRepository = new PoemRepository(getApplication());

            performSearch(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String query = intent.getStringExtra(SearchManager.QUERY);
        performSearch(query);
    }

    private void performSearch(String query) {
        poemRepository.searchPoems(query).observe(this, poems -> {
            Log.d("SearchResultsActivity", "Received poems: " + poems);
            poemAdapter.updatePoemList(poems);
        });
    }
}


