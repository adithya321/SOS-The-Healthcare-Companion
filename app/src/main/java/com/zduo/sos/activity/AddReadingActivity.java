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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zduo.sos.R;
import com.zduo.sos.activities.MainActivity;
import com.zduo.sos.presenter.AddReadingPresenter;
import com.zduo.sos.tools.AnimationTools;
import com.zduo.sos.tools.FormatDateTime;

import java.text.DecimalFormat;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class AddReadingActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private final String INTENT_EXTRA_EDIT = "editing";
    private final String INTENT_EXTRA_EDIT_ID = "edit_id";
    private final String INTENT_EXTRA_PAGER = "pager";
    private final String INTENT_EXTRA_DROPDOWN = "history_dropdown";
    private AddReadingPresenter presenter;

    private TextView addTimeTextView;
    private TextView addDateTextView;
    private FloatingActionButton doneFAB;
    private Runnable fabAnimationRunnable;

    private int pagerPosition;
    private int dropdownPosition;
    private long editId = 0;
    private boolean editing = false;

    protected void retrieveExtra() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            pagerPosition = b.getInt(INTENT_EXTRA_PAGER);
            editId = b.getLong(INTENT_EXTRA_EDIT_ID);
            editing = b.getBoolean(INTENT_EXTRA_EDIT);
            dropdownPosition = b.getInt(INTENT_EXTRA_DROPDOWN);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int seconds) {
        TextView addTime = (TextView) findViewById(R.id.dialog_add_time);
        DecimalFormat df = new DecimalFormat("00");

        presenter.setReadingHour(df.format(hourOfDay));
        presenter.setReadingMinute(df.format(minute));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        FormatDateTime formatDateTime = new FormatDateTime(getApplicationContext());
        addTime.setText(formatDateTime.getTime(cal));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        TextView addDate = (TextView) findViewById(R.id.dialog_add_date);
        DecimalFormat df = new DecimalFormat("00");

        presenter.setReadingYear(year + "");
        presenter.setReadingMonth(df.format(monthOfYear + 1));
        presenter.setReadingDay(df.format(dayOfMonth));

        String date = +dayOfMonth + "/" + presenter.getReadingMonth() + "/" + presenter.getReadingYear();
        addDate.setText(date);
    }

    public void createDateTimeViewAndListener() {
        addTimeTextView = (TextView) findViewById(R.id.dialog_add_time);
        addDateTextView = (TextView) findViewById(R.id.dialog_add_date);

        addDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddReadingActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                dpd.setMaxDate(now);
            }
        });

        addTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(getApplicationContext());
                AddReadingActivity addReadingActivity = AddReadingActivity.this;
                AddReadingPresenter addReadingPresenter = addReadingActivity.getPresenter();
                Calendar cal = addReadingPresenter.getReadingCal();
                if (addReadingActivity.isEditing()) {
                    cal.setTime(addReadingPresenter.getReadingTime());
                }
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddReadingActivity.this,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        is24HourFormat);
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }

    public void createFANViewAndListener() {

        doneFAB = (FloatingActionButton) findViewById(R.id.done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOnAddButtonPressed();
            }
        });
        fabAnimationRunnable = new Runnable() {
            @Override
            public void run() {
                AnimationTools.startCircularReveal(doneFAB);
            }
        };
        doneFAB.postDelayed(fabAnimationRunnable, 600);
    }

    protected abstract void dialogOnAddButtonPressed();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void finishActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("glucosio", true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doneFAB.removeCallbacks(fabAnimationRunnable);
    }

    public AddReadingPresenter getPresenter() {
        return this.presenter;
    }

    // Getter and Setter
    public void setPresenter(AddReadingPresenter newPresenter) {
        this.presenter = newPresenter;
    }

    public int getPagerPosition() {
        return pagerPosition;
    }

    public int getDropdownPosition() {
        return dropdownPosition;
    }

    public long getEditId() {
        return editId;
    }

    public boolean isEditing() {
        return editing;
    }

    public TextView getAddTimeTextView() {
        return addTimeTextView;
    }

    public void setAddTimeTextView(TextView addTimeTextView) {
        this.addTimeTextView = addTimeTextView;
    }

    public TextView getAddDateTextView() {
        return addDateTextView;
    }

    public void setAddDateTextView(TextView addDateTextView) {
        this.addDateTextView = addDateTextView;
    }

}
