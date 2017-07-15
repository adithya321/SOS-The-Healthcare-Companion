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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zduo.sos.R;
import com.zduo.sos.activities.BuyMedsActivity;
import com.zduo.sos.adapters.ReminderAdapter;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.models.Reminder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InventoryFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.buy_more_button)
    Button buyMoreButton;
    @BindView(R.id.header_separator)
    TextView headerSeparator;
    @BindView(R.id.buy_more_notification_circle)
    ImageView buyMoreNotificationCircle;
    @BindView(R.id.buy_more_notification_icon)
    ImageView buyMoreNotificationIcon;
    @BindView(R.id.buy_more_notification_title)
    TextView buyMoreNotificationTitle;
    @BindView(R.id.notification_content)
    TextView notificationContent;
    @BindView(R.id.buy_more_notification_card)
    CardView buyMoreNotificationCard;

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
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, view);
        buyMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BuyMedsActivity.class));
            }
        });

        buyMoreNotificationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateList();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        reminderList = getListData();
        reminderAdapter = new ReminderAdapter(getContext(), reminderList);
        recyclerView.setAdapter(reminderAdapter);

        if (reminderAdapter.getItemCount() == 0) recyclerView.setVisibility(View.GONE);
        else recyclerView.setVisibility(View.VISIBLE);
    }

    public List<Reminder> getListData() {
        DatabaseHelper database = DatabaseHelper.getInstance(getContext().getApplicationContext());
        List<Reminder> reminderList = database.getNotificationList(Reminder.INACTIVE);
        database.close();
        return reminderList;
    }

    public void updateList() {
        reminderList.clear();
        reminderList.addAll(getListData());
        reminderAdapter.notifyDataSetChanged();

        if (reminderAdapter.getItemCount() == 0) recyclerView.setVisibility(View.GONE);
        else recyclerView.setVisibility(View.VISIBLE);
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