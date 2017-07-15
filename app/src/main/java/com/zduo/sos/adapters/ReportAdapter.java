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

package com.zduo.sos.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zduo.sos.R;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.fragments.ReportsFragment;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.models.Report;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context context;
    private List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_report_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Report report = reportList.get(position);

        if (reportList.size() == 0)
            ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.VISIBLE);
        else ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.GONE);

        viewHolder.reportDocName.setText("Dr. " + report.getDocName());
        viewHolder.reportDocAddress.setText(report.getDocAddress());
        viewHolder.reportPatientName.setText(report.getPatientName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        viewHolder.recyclerView.setAdapter(new ReminderAdapter(context, report.getPrescriptions()));

        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Reminder r : report.getPrescriptions())
                    saveReminder(r);
                reportList.remove(report);
                notifyItemRemoved(position);
                if (reportList.size() == 0)
                    ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.VISIBLE);
                else ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.GONE);
            }
        });
    }

    public void saveReminder(Reminder reminder) {
        DatabaseHelper database = DatabaseHelper.getInstance(context);
        database.addNotification(reminder);

        if (reminder.getRepeatType() == Reminder.SPECIFIC_DAYS) {
            reminder.setDaysOfWeek(reminder.getDaysOfWeek());
            database.addDaysOfWeek(reminder);
        }

        database.close();
        //Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.SECOND, 0);
        //AlarmUtil.setAlarm(context, alarmIntent, reminder.getId(), calendar);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header_separator)
        TextView headerSeparator;
        @BindView(R.id.report_doc_name)
        TextView reportDocName;
        @BindView(R.id.report_doc_address)
        TextView reportDocAddress;
        @BindView(R.id.report_patient_name)
        TextView reportPatientName;
        @BindView(R.id.prescription_recycler_view)
        RecyclerView recyclerView;
        @BindView(R.id.report_card)
        CardView reportCard;
        @BindView(R.id.add_btn)
        Button addButton;
        private View view;

        public ViewHolder(final View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}