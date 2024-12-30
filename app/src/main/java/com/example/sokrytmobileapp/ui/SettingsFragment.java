package com.example.sokrytmobileapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import com.example.sokrytmobileapp.MainActivity;
import com.example.sokrytmobileapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String FONT_SIZE_KEY = "font_size";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference backButtonPreference = findPreference("back_button_preference");

        SeekBarPreference fontSizePreference = findPreference(FONT_SIZE_KEY);
        if (fontSizePreference != null) {
            fontSizePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                int fontSize = (int) newValue;
                SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
                sharedPreferences.edit().putInt(FONT_SIZE_KEY, fontSize).apply();
                return true;
            });
        }

        if (backButtonPreference != null) {
            backButtonPreference.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finishAffinity();
                return true;
            });
        }
    }
}
