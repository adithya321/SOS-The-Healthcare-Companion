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

package com.zduo.sos.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.zduo.sos.R;
import com.zduo.sos.db.PressureReading;
import com.zduo.sos.presenter.AddPressurePresenter;
import com.zduo.sos.tools.FormatDateTime;

import java.util.Calendar;

public class AddPressureActivity extends AddReadingActivity {

    private TextView minPressureTextView;
    private TextView maxPressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pressure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
        }

        this.retrieveExtra();

        AddPressurePresenter presenter = new AddPressurePresenter(this);
        setPresenter(presenter);
        presenter.setReadingTimeNow();

        minPressureTextView = (TextView) findViewById(R.id.pressure_add_value_min);
        maxPressureTextView = (TextView) findViewById(R.id.pressure_add_value_max);

        this.createDateTimeViewAndListener();
        this.createFANViewAndListener();

        // Initialize value
        FormatDateTime formatDateTime = new FormatDateTime(getApplicationContext());
        if (this.isEditing()) {
            // set edit title
            setTitle(R.string.title_activity_add_pressure_edit);
            PressureReading readingToEdit = presenter.getPressureReadingById(this.getEditId());

            // set reading values
            minPressureTextView.setText(readingToEdit.getMinReading() + "");
            maxPressureTextView.setText(readingToEdit.getMaxReading() + "");

            // set reading time
            Calendar cal = Calendar.getInstance();
            cal.setTime(readingToEdit.getCreated());
            this.getAddDateTextView().setText(formatDateTime.getDate(cal));
            this.getAddTimeTextView().setText(formatDateTime.getTime(cal));
            presenter.updateReadingSplitDateTime(readingToEdit.getCreated());
        } else {
            this.getAddDateTextView().setText(formatDateTime.getCurrentDate());
            this.getAddTimeTextView().setText(formatDateTime.getCurrentTime());
        }

    }

    @Override
    protected void dialogOnAddButtonPressed() {
        AddPressurePresenter presenter = (AddPressurePresenter) getPresenter();
        // If an id is passed, open the activity in edit mode
        if (this.isEditing()) {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), minPressureTextView.getText().toString(), maxPressureTextView.getText().toString(), this.getEditId());
        } else {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), minPressureTextView.getText().toString(), maxPressureTextView.getText().toString());
        }
    }

    public void showErrorMessage() {
        Toast.makeText(getApplicationContext(), getString(R.string.dialog_error2), Toast.LENGTH_SHORT).show();
    }
}
