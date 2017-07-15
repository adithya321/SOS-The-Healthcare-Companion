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

package com.zduo.sos.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.zduo.sos.R;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.dialogs.AdvancedRepeatSelector;
import com.zduo.sos.dialogs.DaysOfWeekSelector;
import com.zduo.sos.dialogs.IconPicker;
import com.zduo.sos.dialogs.RepeatSelector;
import com.zduo.sos.models.Colour;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.receivers.AlarmReceiver;
import com.zduo.sos.utils.AlarmUtil;
import com.zduo.sos.utils.AnimationUtil;
import com.zduo.sos.utils.DateAndTimeUtil;
import com.zduo.sos.utils.TextFormatUtil;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEditActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback,
        IconPicker.IconSelectionListener, AdvancedRepeatSelector.AdvancedRepeatSelectionListener,
        DaysOfWeekSelector.DaysOfWeekSelectionListener, RepeatSelector.RepeatSelectionListener {

    @BindView(R.id.create_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.notification_title)
    EditText titleEditText;
    @BindView(R.id.notification_content)
    EditText contentEditText;
    @BindView(R.id.time)
    TextView timeText;
    @BindView(R.id.date)
    TextView dateText;
    @BindView(R.id.repeat_day)
    TextView repeatText;
    @BindView(R.id.switch_toggle)
    SwitchCompat foreverSwitch;
    @BindView(R.id.show_times_number)
    EditText timesEditText;
    @BindView(R.id.forever_row)
    LinearLayout foreverRow;
    @BindView(R.id.bottom_row)
    LinearLayout bottomRow;
    @BindView(R.id.bottom_view)
    View bottomView;
    @BindView(R.id.show)
    TextView showText;
    @BindView(R.id.times)
    TextView timesText;
    @BindView(R.id.select_icon_text)
    TextView iconText;
    @BindView(R.id.select_colour_text)
    TextView colourText;
    @BindView(R.id.colour_icon)
    ImageView imageColourSelect;
    @BindView(R.id.selected_icon)
    ImageView imageIconSelect;
    @BindView(R.id.error_time)
    ImageView imageWarningTime;
    @BindView(R.id.error_date)
    ImageView imageWarningDate;
    @BindView(R.id.error_show)
    ImageView imageWarningShow;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dosage)
    EditText dosageEditText;
    @BindView(R.id.quantity)
    EditText quantityEditText;

    private String icon;
    private String colour;
    private Calendar calendar;
    private boolean[] daysOfWeek = new boolean[7];
    private int timesShown = 0;
    private int timesToShow = 1;
    private int repeatType;
    private int id;
    private int interval = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(null);

        calendar = Calendar.getInstance();
        icon = getString(R.string.default_icon_value);
        repeatType = Reminder.DAILY;
        id = getIntent().getIntExtra("NOTIFICATION_ID", 0);

        DatabaseHelper database = DatabaseHelper.getInstance(this);
        int[] colours = database.getColoursArray();
        int random = new Random().nextInt(colours.length);
        colour = "#" + Integer.toHexString(colours[random]);
        imageColourSelect.setColorFilter(Color.parseColor(colour));

        // Check whether to edit or create a new notification
        if (id == 0) id = database.getLastNotificationId() + 1;
        else assignReminderValues();
        database.close();
    }

    public void assignReminderValues() {
        // Prevent keyboard from opening automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DatabaseHelper database = DatabaseHelper.getInstance(this);
        Reminder reminder = database.getNotification(id);
        database.close();

        timesShown = reminder.getNumberShown();
        repeatType = reminder.getRepeatType();
        interval = reminder.getInterval();
        icon = reminder.getIcon();
        colour = reminder.getColour();

        calendar = DateAndTimeUtil.parseDateAndTime(reminder.getDateAndTime());

        showText.setText(getString(R.string.times_shown_edit, reminder.getNumberShown()));
        titleEditText.setText(reminder.getTitle());
        contentEditText.setText(reminder.getContent());
        dateText.setText(DateAndTimeUtil.toStringReadableDate(calendar));
        timeText.setText(DateAndTimeUtil.toStringReadableTime(calendar, this));
        timesEditText.setText(String.valueOf(reminder.getNumberToShow()));
        dosageEditText.setText(String.valueOf(reminder.getDosage()));
        quantityEditText.setText(String.valueOf(reminder.getQuantity()));
        colourText.setText(colour);
        imageColourSelect.setColorFilter(Color.parseColor(colour));
        timesText.setVisibility(View.VISIBLE);

        if (!getString(R.string.default_icon).equals(icon)) {
            imageIconSelect.setImageResource(getResources().getIdentifier(reminder.getIcon(), "drawable", getPackageName()));
            iconText.setText(R.string.custom_icon);
        }

        if (reminder.getInterval() > 1) {
            repeatText.setText(TextFormatUtil.formatAdvancedRepeatText(this, repeatType, interval));
        } else {
            repeatText.setText(getResources().getStringArray(R.array.repeat_array)[reminder.getRepeatType()]);
        }

        if (reminder.getRepeatType() == Reminder.SPECIFIC_DAYS) {
            daysOfWeek = reminder.getDaysOfWeek();
            repeatText.setText(TextFormatUtil.formatDaysOfWeekText(this, daysOfWeek));
        }

        if (Boolean.parseBoolean(reminder.getForeverState())) {
            foreverSwitch.setChecked(true);
            bottomRow.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.time_row)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(CreateEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                timeText.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }

    @OnClick(R.id.date_row)
    public void datePicker(View view) {
        DatePickerDialog DatePicker = new DatePickerDialog(CreateEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker DatePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }

    @OnClick(R.id.icon_select)
    public void iconSelector() {
        DialogFragment dialog = new IconPicker();
        dialog.show(getSupportFragmentManager(), "IconPicker");
    }

    @Override
    public void onIconSelection(DialogFragment dialog, String iconName, String iconType, int iconResId) {
        icon = iconName;
        iconText.setText(iconType);
        imageIconSelect.setImageResource(iconResId);
        dialog.dismiss();
    }

    @OnClick(R.id.colour_select)
    public void colourSelector() {
        DatabaseHelper database = DatabaseHelper.getInstance(this);
        int[] colours = database.getColoursArray();
        database.close();

        new ColorChooserDialog.Builder(this, R.string.select_colour)
                .allowUserColorInputAlpha(false)
                .customColors(colours, null)
                .show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColour) {
        colour = String.format("#%06X", (0xFFFFFF & selectedColour));
        imageColourSelect.setColorFilter(selectedColour);
        colourText.setText(colour);
        DatabaseHelper database = DatabaseHelper.getInstance(this);
        database.addColour(new Colour(selectedColour, DateAndTimeUtil.toStringDateTimeWithSeconds(Calendar.getInstance())));
        database.close();
    }

    @OnClick(R.id.repeat_row)
    public void repeatSelector() {
        DialogFragment dialog = new RepeatSelector();
        dialog.show(getSupportFragmentManager(), "RepeatSelector");
    }

    @Override
    public void onRepeatSelection(DialogFragment dialog, int which, String repeatText) {
        interval = 1;
        repeatType = which;
        this.repeatText.setText(repeatText);
    }

    @Override
    public void onDaysOfWeekSelected(boolean[] days) {
        repeatText.setText(TextFormatUtil.formatDaysOfWeekText(this, days));
        daysOfWeek = days;
        repeatType = Reminder.SPECIFIC_DAYS;
    }

    @Override
    public void onAdvancedRepeatSelection(int type, int interval, String repeatText) {
        repeatType = type;
        this.interval = interval;
        this.repeatText.setText(repeatText);
    }

    public void saveReminder() {
        DatabaseHelper database = DatabaseHelper.getInstance(this);
        Reminder reminder = new Reminder()
                .setId(id)
                .setTitle(titleEditText.getText().toString())
                .setContent(contentEditText.getText().toString())
                .setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar))
                .setRepeatType(repeatType)
                .setForeverState(Boolean.toString(foreverSwitch.isChecked()))
                .setNumberToShow(timesToShow)
                .setNumberShown(timesShown)
                .setDosage(Double.parseDouble(dosageEditText.getText().toString()))
                .setQuantity(Double.parseDouble(quantityEditText.getText().toString()))
                .setIcon(icon)
                .setColour(colour)
                .setInterval(interval);

        database.addNotification(reminder);

        if (repeatType == Reminder.SPECIFIC_DAYS) {
            reminder.setDaysOfWeek(daysOfWeek);
            database.addDaysOfWeek(reminder);
        }

        database.close();
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        calendar.set(Calendar.SECOND, 0);
        AlarmUtil.setAlarm(this, alarmIntent, reminder.getId(), calendar);
        finish();
    }

    @OnClick(R.id.forever_row)
    public void toggleSwitch() {
        foreverSwitch.toggle();
        if (foreverSwitch.isChecked()) {
            bottomRow.setVisibility(View.GONE);
        } else {
            bottomRow.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.switch_toggle)
    public void switchClicked() {
        if (foreverSwitch.isChecked()) {
            bottomRow.setVisibility(View.GONE);
        } else {
            bottomRow.setVisibility(View.VISIBLE);
        }
    }

    public void validateInput() {
        imageWarningShow.setVisibility(View.GONE);
        imageWarningTime.setVisibility(View.GONE);
        imageWarningDate.setVisibility(View.GONE);
        Calendar nowCalendar = Calendar.getInstance();

        if (timeText.getText().equals(getString(R.string.time_now))) {
            calendar.set(Calendar.HOUR_OF_DAY, nowCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, nowCalendar.get(Calendar.MINUTE));
        }

        if (dateText.getText().equals(getString(R.string.date_today))) {
            calendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, nowCalendar.get(Calendar.DAY_OF_MONTH));
        }

        // Check if the number of times to show notification is empty
        if (timesEditText.getText().toString().isEmpty()) {
            timesEditText.setText("1");
        }

        timesToShow = Integer.parseInt(timesEditText.getText().toString());

        // Check if selected date is before today's date
        if (DateAndTimeUtil.toLongDateAndTime(calendar) < DateAndTimeUtil.toLongDateAndTime(nowCalendar)) {
            Snackbar.make(coordinatorLayout, R.string.toast_past_date, Snackbar.LENGTH_SHORT).show();
            imageWarningTime.setVisibility(View.VISIBLE);
            imageWarningDate.setVisibility(View.VISIBLE);

            // Check if title is empty
        } else if (titleEditText.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, R.string.toast_title_empty, Snackbar.LENGTH_SHORT).show();
            AnimationUtil.shakeView(titleEditText, this);

            // Check if times to show notification is too low
        } else if (timesToShow <= timesShown && !foreverSwitch.isChecked()) {
            Snackbar.make(coordinatorLayout, R.string.toast_higher_number, Snackbar.LENGTH_SHORT).show();
            imageWarningShow.setVisibility(View.VISIBLE);
        } else if (dosageEditText.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, R.string.dosage_error, Snackbar.LENGTH_SHORT).show();
            AnimationUtil.shakeView(dosageEditText, this);
        } else if (quantityEditText.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, R.string.quantity_error, Snackbar.LENGTH_SHORT).show();
            AnimationUtil.shakeView(quantityEditText, this);
        } else {
            saveReminder();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                validateInput();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}