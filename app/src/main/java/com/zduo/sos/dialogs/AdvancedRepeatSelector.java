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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;

import com.zduo.sos.R;

public class AdvancedRepeatSelector extends DialogFragment {

    AdvancedRepeatSelectionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdvancedRepeatSelectionListener) context;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.number_picker, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog);
        builder.setTitle(R.string.repeat_every);

        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.picker1);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(999);
        numberPicker.setWrapSelectorWheel(false);

        final NumberPicker repeatPicker = (NumberPicker) view.findViewById(R.id.picker2);
        repeatPicker.setMinValue(0);
        repeatPicker.setMaxValue(4);
        repeatPicker.setWrapSelectorWheel(false);
        repeatPicker.setDisplayedValues(getRepeatValues(numberPicker.getValue()));

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                repeatPicker.setDisplayedValues(getRepeatValues(newVal));
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatType = repeatPicker.getDisplayedValues()[repeatPicker.getValue()];
                String text = getString(R.string.repeats_every, numberPicker.getValue(), repeatType);
                listener.onAdvancedRepeatSelection(repeatPicker.getValue() + 1, numberPicker.getValue(), text);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public String[] getRepeatValues(int number) {
        String[] values = new String[5];
        values[0] = getResources().getQuantityString(R.plurals.hour, number);
        values[1] = getResources().getQuantityString(R.plurals.day, number);
        values[2] = getResources().getQuantityString(R.plurals.week, number);
        values[3] = getResources().getQuantityString(R.plurals.month, number);
        values[4] = getResources().getQuantityString(R.plurals.year, number);
        return values;
    }

    public interface AdvancedRepeatSelectionListener {
        void onAdvancedRepeatSelection(int type, int interval, String repeatText);
    }
}
