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

package com.zduo.sos.dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.zduo.sos.R;

public class PreferenceNagTimePicker extends DialogPreference {

    public static final int MAX_VALUE = 60;
    public static final int MIN_VALUE = 0;

    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private SharedPreferences sharedPreferences;

    public PreferenceNagTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.number_picker);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        setPersistent(false);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        setUpMinutePicker(view);
        setUpSecondPicker(view);
    }

    protected void setUpMinutePicker(View view) {
        minutePicker = (NumberPicker) view.findViewById(R.id.picker1);
        minutePicker.setMaxValue(MAX_VALUE);
        minutePicker.setMinValue(MIN_VALUE);
        minutePicker.setValue(sharedPreferences.getInt("nagMinutes", getContext().getResources().getInteger(R.integer.default_nag_minutes)));

        String[] minuteValues = new String[61];
        for (int i = 0; i < minuteValues.length; i++) {
            minuteValues[i] = String.format(getContext().getResources().getQuantityString(R.plurals.time_minute, i), i);
        }
        minutePicker.setDisplayedValues(minuteValues);
    }

    protected void setUpSecondPicker(View view) {
        secondPicker = (NumberPicker) view.findViewById(R.id.picker2);
        secondPicker.setMaxValue(MAX_VALUE);
        secondPicker.setMinValue(MIN_VALUE);
        secondPicker.setValue(sharedPreferences.getInt("nagSeconds", getContext().getResources().getInteger(R.integer.default_nag_seconds)));

        String[] secondValues = new String[61];
        for (int i = 0; i < secondValues.length; i++) {
            secondValues[i] = String.format(getContext().getResources().getQuantityString(R.plurals.time_second, i), i);
        }
        secondPicker.setDisplayedValues(secondValues);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("nagMinutes", minutePicker.getValue());
            editor.putInt("nagSeconds", secondPicker.getValue());
            editor.apply();
        }
    }
}