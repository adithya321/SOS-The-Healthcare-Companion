/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;

import com.zduo.sos.MyApplication;
import com.zduo.sos.R;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int REQUEST_CONTACT = 0;
    private SharedPreferences sharedPreferences, s1;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        s1 = getActivity().getSharedPreferences("Profile", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkBoxSOS", s1.getBoolean("sos", true));
        editor.apply();
        CheckBoxPreference sosPreference = (CheckBoxPreference) getPreferenceScreen().findPreference("checkBoxSOS");
        sosPreference.setChecked(sharedPreferences.getBoolean("checkBoxSOS", true));

        updatePreferenceSummary();
    }

    public void updatePreferenceSummary() {
        CheckBoxPreference bloodPreference = (CheckBoxPreference) findPreference("checkBoxBlood");
        bloodPreference.setChecked(sharedPreferences.getBoolean("checkBoxBlood", true));

        // Set nagging preference summary
        int nagMinutes = sharedPreferences.getInt("nagMinutes", getResources().getInteger(R.integer.default_nag_minutes));
        int nagSeconds = sharedPreferences.getInt("nagSeconds", getResources().getInteger(R.integer.default_nag_seconds));
        Preference nagPreference = findPreference("nagInterval");
        String nagMinutesText = String.format(getActivity().getResources().getQuantityString(R.plurals.time_minute, nagMinutes), nagMinutes);
        String nagSecondsText = String.format(getActivity().getResources().getQuantityString(R.plurals.time_second, nagSeconds), nagSeconds);
        nagPreference.setSummary(String.format("%s %s", nagMinutesText, nagSecondsText));

        SharedPreferences.Editor editor = s1.edit();
        editor.putBoolean("sos", sharedPreferences.getBoolean("checkBoxSOS", s1.getBoolean("sos", true)));
        editor.apply();

        ((MyApplication) getActivity().getApplication()).createSOSNotification();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreferenceSummary();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}