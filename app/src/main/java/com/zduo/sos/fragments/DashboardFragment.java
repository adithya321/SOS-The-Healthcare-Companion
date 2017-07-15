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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zduo.sos.R;
import com.zduo.sos.adapters.ReminderAdapter;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.models.Reminder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    CardView cardView;

    @BindView(R.id.activity_layout)
    LinearLayout activityLayout;
    @BindView(R.id.activity_factor)
    Spinner activityFactor;
    @BindView(R.id.bmi)
    TextView bmi;
    @BindView(R.id.bmr)
    TextView bmr;
    @BindView(R.id.cal)
    TextView cal;
    @BindView(R.id.header_vitals)
    TextView headerVitals;
    @BindView(R.id.header_dashboard)
    TextView headerDashboard;
    @BindView(R.id.feeling_today)
    CardView feelingToday;
    @BindView(R.id.feeling_today_title)
    TextView feelingTodayTitle;
    @BindView(R.id.pro_tip_title)
    TextView proTipTitle;
    @BindView(R.id.pro_tip_content)
    TextView proTipContent;
    @BindView(R.id.feeling_today_circle)
    ImageView feelingTodayCircle;
    @BindView(R.id.feeling_today_icon)
    ImageView feelingTodayIcon;
    @BindView(R.id.feeling_today_content)
    TextView feelingTodayContent;
    @BindView(R.id.header_separator)
    TextView headerSeparator;
    @BindView(R.id.notification_circle)
    ImageView notificationCircle;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.notification_title)
    TextView notificationTitle;
    @BindView(R.id.buy_more_notification_content)
    TextView buyMoreNotificationContent;
    @BindView(R.id.vitals)
    CardView vitals;
    @BindView(R.id.great_thanks)
    Button greatThanks;
    @BindView(R.id.pro_circle)
    ImageView proCircle;

    private SharedPreferences sharedPreferences;
    private ReminderAdapter reminderAdapter;
    private List<Reminder> reminderList;
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        GradientDrawable bgShape = (GradientDrawable) proCircle.getDrawable();
        bgShape.setColor(getResources().getColor(R.color.glucosio_reading_ok));
        GradientDrawable fireShape = (GradientDrawable) notificationCircle.getDrawable();
        fireShape.setColor(getResources().getColor(R.color.shortcut_fire));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);

        feelingTodayTitle.setText("Hey there, " + sharedPreferences.getString(getString(R.string.your_name), "You") + "!");
        GradientDrawable bgShape = (GradientDrawable) feelingTodayCircle.getDrawable();
        bgShape.setColor(getResources().getColor(R.color.glucosio_reading_high));

        reminderList = getListData();
        reminderAdapter = new ReminderAdapter(getContext(), reminderList);
        recyclerView.setAdapter(reminderAdapter);

        if (reminderAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_factor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityFactor.setAdapter(adapter);
        activityFactor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("activity_factor", parent.getItemAtPosition(position).toString());
                editor.apply();
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calculate();

        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.tips));
        Collections.shuffle(stringList);
        proTipContent.setText(stringList.get(0));

        greatThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation lToR = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right);
                lToR.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        feelingToday.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                feelingToday.startAnimation(lToR);
            }
        });
    }

    public List<Reminder> getListData() {
        DatabaseHelper database = DatabaseHelper.getInstance(getContext().getApplicationContext());
        List<Reminder> reminderList = database.getNotificationList(Reminder.ACTIVE);
        database.close();
        return reminderList;
    }

    public void updateList() {
        reminderList.clear();
        reminderList.addAll(getListData());
        reminderAdapter.notifyDataSetChanged();

        if (reminderAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }
    }

    private void calculate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            double weight = Double.parseDouble(sharedPreferences.getString(getString(R.string.weight), "0"));
            double height = Double.parseDouble(sharedPreferences.getString(getString(R.string.height), "0"));
            double age = Double.parseDouble(sharedPreferences.getString(getString(R.string.age), "0"));
            double bodyMassIndex = weight / (height * height / 10000);

            bmi.setText(String.format("%.1f", bodyMassIndex));
            editor.putString("bmi", String.format("%.1f", bodyMassIndex));

            double basalMetabolicRate;
            if (sharedPreferences.getString("gender", "Male").equals("Male"))
                basalMetabolicRate = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
            else
                basalMetabolicRate = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
            bmr.setText(String.format("%.1f", basalMetabolicRate));
            editor.putString("bmr", String.format("%.1f", basalMetabolicRate));

            double caloriesNeeded = 0;
            switch (sharedPreferences.getString("activity_factor", "Sedentary")) {
                case "Sedentary":
                    caloriesNeeded = basalMetabolicRate * 1.2;
                    break;
                case "Lightly active":
                    caloriesNeeded = basalMetabolicRate * 1.375;
                    break;
                case "Moderately active":
                    caloriesNeeded = basalMetabolicRate * 1.55;
                    break;
                case "Very active":
                    caloriesNeeded = basalMetabolicRate * 1.725;
                    break;
                case "Extra active":
                    caloriesNeeded = basalMetabolicRate * 1.9;
                    break;
            }
            cal.setText(String.format("%.1f", caloriesNeeded));
            editor.putString("cal", String.format("%.1f", caloriesNeeded));
        } catch (Exception e) {
            Log.e("BMI", e.toString());
        }

        editor.apply();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter("BROADCAST_REFRESH"));
        updateList();
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiver);
        super.onPause();
    }
}